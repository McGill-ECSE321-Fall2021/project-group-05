package ca.mcgill.ecse321.onlinelibrary.model;

import java.sql.Date;
import javax.persistence.*;

@Entity
public class ArchiveInfo extends LibraryItemInfo {

	private String title;
	private String description;
	private Date publicationDate;

	public boolean setTitle(String aTitle) {
		boolean wasSet = false;
		title = aTitle;
		wasSet = true;
		return wasSet;
	}

	public boolean setDescription(String aDescription) {
		boolean wasSet = false;
		description = aDescription;
		wasSet = true;
		return wasSet;
	}

	public boolean setPublicationDate(Date aPublicationDate) {
		boolean wasSet = false;
		publicationDate = aPublicationDate;
		wasSet = true;
		return wasSet;
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

}
