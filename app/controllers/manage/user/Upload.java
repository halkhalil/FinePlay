package controllers.manage.user;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.security.auth.login.AccountException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.supercsv.prefs.CsvPreference;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import common.data.validation.groups.Create;
import common.data.validation.groups.Update;
import common.system.MessageKeys;
import common.utils.CSVs;
import common.utils.Strings;
import controllers.user.UserService;
import models.manage.user.UploadFormContent;
import models.manage.user.UploadFormContent.Operation;
import models.system.System.Permission;
import models.system.System.PermissionsAllowed;
import models.user.User;
import play.api.PlayException.ExceptionSource;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.filters.csrf.RequireCSRFCheck;
import play.i18n.Lang;
import play.i18n.MessagesApi;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import javax.annotation.Nonnull;
import play.i18n.Messages;
import play.mvc.Http.Request;
import play.mvc.Security.Authenticated;

public class Upload extends Controller {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Inject
	private MessagesApi messagesApi;

	@Inject
	private JPAApi jpaApi;

	@Inject
	private FormFactory formFactory;

	@Inject
	private UserService userService;

	@Authenticated(common.core.Authenticator.class)
	@PermissionsAllowed(value = { Permission.MANAGE })
	@BodyParser.Of(value = BodyParser.MultipartFormData.class)
	@RequireCSRFCheck
	public CompletionStage<Result> upload(@Nonnull final Request request) {

		final Messages messages = messagesApi.preferred(request);
		final Lang lang = messages.lang();

		final Result result = jpaApi.withTransaction(manager -> {

			final MultipartFormData<File> multipartFormData = request.body().asMultipartFormData();
			final Form<UploadFormContent> uploadForm = formFactory.form(UploadFormContent.class).bindFromRequestData(lang, request.attrs(), multipartFormData.asFormUrlEncoded());

			if (!uploadForm.hasErrors()) {

				final UploadFormContent uploadFormContent = uploadForm.get();

				final Operation operation = uploadFormContent.getOperation();

				try {

					final FilePart<File> uploadFilePart = multipartFormData.getFile(User.UPLOADFILE);
					if (uploadFilePart.getFilename().isEmpty()) {

						throw new IllegalStateException(messages.at(MessageKeys.ERROR_PATH_EMPTY));
					}
					final Path uploadPath = uploadFilePart.getFile().toPath();

					final String csv = Files.readAllLines(uploadPath, StandardCharsets.UTF_8).stream().collect(Collectors.joining(CsvPreference.STANDARD_PREFERENCE.getEndOfLineSymbols()));
					final List<models.user.User> uploadUsers = CSVs.toBeans(models.user.User.getHeaders(), models.user.User.getReadCellProcessors(), csv, models.user.User.class);
					uploadUsers.stream().forEach(user -> user.afterRead(messages));

					final UploadProcess process = createUploadProcess(operation);
					process.validate(uploadUsers, messages);
					process.execute(manager, uploadUsers, messages);
				} catch (final ExceptionSource e) {

					manager.getTransaction().setRollbackOnly();

					final Form<UploadFormContent> failureUploadForm = formFactory.form(UploadFormContent.class).fill(uploadFormContent);
					failureUploadForm.withGlobalError(messages.at(MessageKeys.SYSTEM_ERROR_X__CASE__DATA_ILLEGAL, e.line()) + ": " + e.getLocalizedMessage());
					return failureUpload(failureUploadForm, request, lang, messages);
				} catch (final Exception e) {

					manager.getTransaction().setRollbackOnly();

					final Form<UploadFormContent> failureUploadForm = formFactory.form(UploadFormContent.class).fill(uploadFormContent);
					failureUploadForm.withGlobalError(e.getLocalizedMessage());
					return failureUpload(failureUploadForm, request, lang, messages);
				}

				final ObjectMapper mapper = new ObjectMapper();
				final ObjectNode response = mapper.createObjectNode();
				response.put("status", "success");

				return ok(response);
			} else {

				return failureUpload(uploadForm, request, lang, messages);
			}
		});

		return CompletableFuture.supplyAsync(() -> {

			return result;
		});

	}

	private Result failureUpload(final Form<UploadFormContent> uploadForm, final Request request, final Lang lang, final Messages messages) {

		final ObjectMapper mapper = new ObjectMapper();
		final ObjectNode result = mapper.createObjectNode();
		result.put("status", "error");

		final ArrayNode globalErrorsNode = mapper.createArrayNode();
		uploadForm.globalErrors().forEach(validationError -> {

			globalErrorsNode.add(messages.at(validationError.message()));
		});
		result.set("globalErrors", globalErrorsNode);

		final ObjectNode errorsNode = mapper.createObjectNode();
		uploadForm.errors().stream().forEach(error -> {

			final String property = error.key();

			final ArrayNode propertyErrorNode = mapper.createArrayNode();
			error.messages().forEach(message -> propertyErrorNode.add(messages.at(message)));

			errorsNode.set(property, propertyErrorNode);
		});
		result.set("errors", errorsNode);

		return badRequest(result);
	}

	private UploadProcess createUploadProcess(final Operation operation) {

		switch (operation) {
		case CREATE:

			return new CreateUploadProcess();
		case UPDATE:

			return new UpdateUploadProcess();
		default:

			throw new IllegalArgumentException("Operation: " + operation);
		}
	}

	interface UploadProcess {

		Class<?>[] getGroups();

		default void validate(final List<models.user.User> uploadUsers, final Messages messages) {

			uploadUsers.stream().forEach(user -> {

				final Set<ConstraintViolation<models.user.User>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(user, getGroups());
				if (1 <= violations.size()) {

					final StringBuilder builder = new StringBuilder();
					builder.append(user.getUserId()).append(" - ");
					violations.stream().forEach(violation -> {

						builder.append(violation.getPropertyPath().toString()).append("<br>").append(messages.at(violation.getMessage()));
					});
					throw new IllegalStateException(builder.toString());
				}
			});
		}

		void execute(final EntityManager manager, final List<models.user.User> uploadUsers, final Messages messages) throws AccountException;
	}

	private class CreateUploadProcess implements UploadProcess {

		private final Class<?>[] groups = new Class<?>[] { Create.class };

		@Override
		public Class<?>[] getGroups() {

			return groups;
		}

		@Override
		public void execute(final EntityManager manager, final List<models.user.User> uploadUsers, final Messages messages) throws AccountException {

			for (int i = 0; i < uploadUsers.size(); i++) {

				final models.user.User uploadUser = uploadUsers.get(i);

				uploadUser.setPassword(Strings.randomAlphanumeric(8) + "1!aA");
				if (userService.isExist(manager, messages, uploadUser.getUserId())) {

					throw new AccountException(messages.at(MessageKeys.SYSTEM_ERROR_USERID_EXIST) + ": " + uploadUser.getUserId());
				}

				try {

					userService.create(manager, messages, uploadUser);
				} catch (final EntityExistsException e) {

					throw e;
				}
			}
		}
	}

	private class UpdateUploadProcess implements UploadProcess {

		private final Class<?>[] groups = new Class<?>[] { Update.class };

		@Override
		public Class<?>[] getGroups() {

			return groups;
		}

		@Override
		public void execute(final EntityManager manager, final List<models.user.User> uploadUsers, final Messages messages) throws AccountException {

			for (int i = 0; i < uploadUsers.size(); i++) {

				final models.user.User uploadUser = uploadUsers.get(i);

				final models.user.User storedUser;
				if (!userService.isExist(manager, messages, uploadUser.getUserId())) {

					throw new AccountException(messages.at(MessageKeys.SYSTEM_ERROR_USERID_NOTEXIST) + ": " + uploadUser.getUserId());
				}

				try {

					storedUser = userService.read(manager, messages, uploadUser.getUserId());
				} catch (final AccountException e) {

					throw e;
				}

				storedUser.setRoles(uploadUser.getRoles());
				storedUser.setExpireDateTime(uploadUser.getExpireDateTime());
				storedUser.setSignInDateTime(uploadUser.getSignInDateTime());
				storedUser.setSignOutDateTime(uploadUser.getSignOutDateTime());

				try {

					userService.update(manager, messages, storedUser);
				} catch (final IllegalArgumentException e) {

					throw e;
				}
			}
		}
	}
}
