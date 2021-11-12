package ca.mcgill.ecse321.onlinelibrary.model;

import ca.mcgill.ecse321.onlinelibrary.dto.ArchiveInfoDto;

import javax.persistence.Entity;
import java.sql.Date;

@Entity
public class ArchiveInfo extends LibraryItemInfo {

	private String title;
	private String description;
	private Date publicationDate;

	public void setTitle(String aTitle) {
		title = aTitle;
	}

	public void setDescription(String aDescription) {
		description = aDescription;
	}

	public void setPublicationDate(Date aPublicationDate) {
		publicationDate = aPublicationDate;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public ArchiveInfoDto convertToDto() {
		return new ArchiveInfoDto(this.getId(), this.title, this.description, this.publicationDate);
	}

}
