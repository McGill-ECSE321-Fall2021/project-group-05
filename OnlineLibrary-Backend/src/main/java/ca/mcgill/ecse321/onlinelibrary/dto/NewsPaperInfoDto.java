package ca.mcgill.ecse321.onlinelibrary.dto;

import java.sql.Date;
import ca.mcgill.ecse321.onlinelibrary.model.NewsPaperInfo;

public class NewsPaperInfoDto extends LibraryItemInfoDto {

	private Date publication;
	private String frequency;
	private int number;

	public NewsPaperInfoDto(int id, Date publication, String frequency, int number) {
		super(id);
		this.publication=publication;
		this.frequency = frequency;
		this.number=number;
	}

	public static NewsPaperInfoDto fromNewsPaperInfo(NewsPaperInfo newsPaperInfo) {
		if (newsPaperInfo == null) {
			throw new IllegalArgumentException("There is no such newsPaperInfo");
		}
		return new NewsPaperInfoDto(newsPaperInfo.getId(), newsPaperInfo.getPublication(), newsPaperInfo.getFrequency(),
				newsPaperInfo.getNumber());
	}

	public Date getDate() {
		return this.publication;
	}

	public String getFrequency() {
		return this.frequency;
	}

	public int getNumber() {
		return this.number;
	}
}
