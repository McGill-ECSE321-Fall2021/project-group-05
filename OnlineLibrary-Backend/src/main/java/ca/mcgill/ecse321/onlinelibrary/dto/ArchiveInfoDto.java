package ca.mcgill.ecse321.onlinelibrary.dto;

import java.sql.Date;

import ca.mcgill.ecse321.onlinelibrary.model.ArchiveInfo;

public class ArchiveInfoDto extends LibraryItemInfoDto{
	private String title;
	private String description;
	private Date publicationDate;

	public ArchiveInfoDto(int id, String title, String description, Date publicationDate) {
		super(id);
		this.title=title;
		this.description=description;
		this.publicationDate=publicationDate;
	}

	public static ArchiveInfoDto fromArchiveInfo(ArchiveInfo archiveInfo) {
		if (archiveInfo == null) {
			throw new IllegalArgumentException("There is no such archiveInfo.");
		}
		return new ArchiveInfoDto(archiveInfo.getId(), archiveInfo.getTitle(), archiveInfo.getDescription(),
				archiveInfo.getPublicationDate());
	}

	public String getTitle() {
		return this.title;
	}

	public String getDescription() {
		return this.description;
	}

	public Date publicationDate() {
		return this.publicationDate;
	}
}
