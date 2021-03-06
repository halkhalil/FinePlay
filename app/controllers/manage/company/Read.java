package controllers.manage.company;

import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.utils.Binaries;
import common.utils.CSVs;
import models.base.EntityDao;
import models.company.Company;
import models.manage.company.ReadFormContent;
import models.system.System.Permission;
import models.system.System.PermissionsAllowed;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.filters.csrf.RequireCSRFCheck;
import play.i18n.Messages;
import play.i18n.Lang;
import play.i18n.MessagesApi;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import javax.annotation.Nonnull;
import play.mvc.Http.Request;
import play.mvc.Security.Authenticated;

public class Read extends Controller {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Inject
	private MessagesApi messagesApi;

	@Inject
	private JPAApi jpaApi;

	@Inject
	private FormFactory formFactory;

	private final EntityDao<Company> companyDao = new EntityDao<Company>() {
	};

	@Authenticated(common.core.Authenticator.class)
	@PermissionsAllowed(value = { Permission.MANAGE })
	public Result index(@Nonnull final Request request) {

		final Messages messages = messagesApi.preferred(request);
		final Lang lang = messages.lang();

		return jpaApi.withTransaction(manager -> {

			final ReadFormContent readFormContent = new ReadFormContent();
			readFormContent.setMaxResult(String.valueOf(1000));

			final List<Company> companies = readList(manager, readFormContent, 0);

			final Form<ReadFormContent> readForm = formFactory.form(ReadFormContent.class).fill(readFormContent);

			return ok(views.html.manage.company.index.render(readForm, companies, request, lang, messages));
		});
	}

	@Authenticated(common.core.Authenticator.class)
	@PermissionsAllowed(value = { Permission.MANAGE })
	@RequireCSRFCheck
	public Result read(@Nonnull final Request request) {

		final Messages messages = messagesApi.preferred(request);
		final Lang lang = messages.lang();

		return jpaApi.withTransaction(manager -> {

			final Form<ReadFormContent> readForm = formFactory.form(ReadFormContent.class).bindFromRequest(request);
			if (!readForm.hasErrors()) {

				final ReadFormContent readFormContent = readForm.get();

				final List<Company> companies = readList(manager, readFormContent, 0);

				return ok(views.html.manage.company.index.render(readForm, Collections.unmodifiableList(companies), request, lang, messages));
			} else {

				return failureRead(readForm, request, lang, messages);
			}
		});
	}

	@Authenticated(common.core.Authenticator.class)
	@PermissionsAllowed(value = { Permission.MANAGE })
	@RequireCSRFCheck
	public Result download(@Nonnull final Request request) {

		final Messages messages = messagesApi.preferred(request);
		final Lang lang = messages.lang();

		final Result result = jpaApi.withTransaction(manager -> {

			final Form<ReadFormContent> downloadForm = formFactory.form(ReadFormContent.class).bindFromRequest(request);

			if (!downloadForm.hasErrors()) {

				final ReadFormContent downloadFormContent = downloadForm.get();
				final List<Company> downloadCompanies = readList(manager, downloadFormContent, 0);
				downloadCompanies.stream().forEach(company -> company.beforeWrite(messages));
				final String csv = CSVs.toCSV(Company.getHeaders(), Company.getWriteCellProcessors(), downloadCompanies);

				return ok(Binaries.concat(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF }, csv.getBytes(StandardCharsets.UTF_8)))//
						.as(Http.MimeTypes.BINARY)//
						.withHeader(Http.HeaderNames.CONTENT_DISPOSITION, "attachment;filename=companies.csv");
			} else {

				return failureRead(downloadForm, request, lang, messages);
			}
		});

		return result;
	}

	private List<Company> readList(final EntityManager manager, final ReadFormContent readFormContent, int startPosition) {

		final String name = readFormContent.getName();
		final int maxResult = Integer.parseInt(readFormContent.getMaxResult());

		return companyDao.readList(manager, Company.class, (builder, query) -> {

			final Root<Company> root = query.from(Company.class);

			final List<Predicate> predicates = new ArrayList<>();

			if (Objects.nonNull(name) && !name.isEmpty()) {

				final Join<models.company.Company, models.company.CompanyName> namesJoin = root.join(models.company.Company_.names);
				predicates.add(builder.like(namesJoin.get(models.company.CompanyName_.name), "%" + name + "%"));
			}

			query.where(predicates.toArray(new Predicate[0])).distinct(true);
		}, parameters -> {

			parameters.setFirstResult(startPosition).setMaxResults(maxResult);
		});
	}

	private Result failureRead(final Form<ReadFormContent> searchForm, final Request request, final Lang lang, final Messages messages) {

		final List<Company> companies = Collections.emptyList();
		return badRequest(views.html.manage.company.index.render(searchForm, companies, request, lang, messages));
	}
}
