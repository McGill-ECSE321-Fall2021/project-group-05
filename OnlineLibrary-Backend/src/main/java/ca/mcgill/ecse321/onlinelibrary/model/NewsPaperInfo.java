package ca.mcgill.ecse321.onlinelibrary.model;

import java.sql.Date;
import javax.persistence.*;

import ca.mcgill.ecse321.onlinelibrary.dto.NewsPaperInfoDto;

@Entity
public class NewsPaperInfo extends LibraryItemInfo {
	private Date publication;
	private String frequency;
	private int number;

	public void setPublication(Date aPublication) {
		publication = aPublication;
	}

	public void setFrequency(String aFrequency) {
		frequency = aFrequency;
	}

	public void setNumber(int aNumber) {
		number = aNumber;
	}

	public Date getPublication() {
		return publication;
	}

	public String getFrequency() {
		return frequency;
	}

	public int getNumber() {
		return number;
	}


	public NewsPaperInfoDto convertToDto() {
		return new NewsPaperInfoDto(this.getId(), publication, frequency, number);
	}

}
