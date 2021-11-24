package ca.mcgill.ecse321.onlinelibrary.dto;

import ca.mcgill.ecse321.onlinelibrary.model.NewspaperInfo;

import java.sql.Date;

public class NewspaperInfoDto extends LibraryItemInfoDto {

	private Date publication;
	private String frequency;
	private int number;

	public NewspaperInfoDto(int id, Date publication, String frequency, int number) {
		super(id);
		this.publication = publication;
		this.frequency = frequency;
		this.number = number;
	}

	public static NewspaperInfoDto fromNewspaperInfo(NewspaperInfo newspaperInfo) {
		if (newspaperInfo == null) {
			throw new IllegalArgumentException("There is no such newspaperInfo");
		}
		return new NewspaperInfoDto(newspaperInfo.getId(), newspaperInfo.getPublication(), newspaperInfo.getFrequency(),
				newspaperInfo.getNumber());
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

	@Override
	public String getType() {
		return "Newspaper";
	}
}
