package ca.mcgill.ecse321.onlinelibrary.model;

import ca.mcgill.ecse321.onlinelibrary.dto.NewspaperInfoDto;

import javax.persistence.Entity;
import java.sql.Date;

@Entity
public class NewspaperInfo extends LibraryItemInfo {
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


	public NewspaperInfoDto convertToDto() {
		return new NewspaperInfoDto(this.getId(), publication, frequency, number);
	}

}