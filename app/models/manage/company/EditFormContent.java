package models.manage.company;

import java.time.LocalDateTime;

import common.data.validation.groups.Create;
import common.data.validation.groups.Delete;
import common.data.validation.groups.Update;
import play.data.validation.Constraints;

public class EditFormContent {

	// Play
	@Constraints.Required(groups = { Update.class, Delete.class })
	private long id;

	// Play
	@Constraints.Required(groups = { Create.class, Update.class })
	private String name;

	private String localName;

	// Play
	@Constraints.Required(groups = { Update.class, Delete.class })
	private LocalDateTime updateDateTime;

	public long getId() {

		return id;
	}

	public void setId(long id) {

		this.id = id;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getLocalName() {

		return localName;
	}

	public void setLocalName(String localName) {

		this.localName = localName;
	}

	public LocalDateTime getUpdateDateTime() {

		return updateDateTime;
	}

	public void setUpdateDateTime(LocalDateTime updateDateTime) {

		this.updateDateTime = updateDateTime;
	}
}
