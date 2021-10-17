package ca.mcgill.ecse321.onlinelibrary.model;

import java.sql.Date;
import javax.persistence.*;

@Entity
public class NewsPaperInfo extends LibraryItemInfo {
	private Date publication;
	private String frequency;
	private int number;

	public boolean setPublication(Date aPublication) {
		boolean wasSet = false;
		publication = aPublication;
		wasSet = true;
		return wasSet;
	}

	public boolean setFrequency(String aFrequency) {
		boolean wasSet = false;
		frequency = aFrequency;
		wasSet = true;
		return wasSet;
	}

	public boolean setNumber(int aNumber) {
		boolean wasSet = false;
		number = aNumber;
		wasSet = true;
		return wasSet;
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

}
