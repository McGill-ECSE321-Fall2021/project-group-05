package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.*;

@Entity
public class MovieInfo extends ReservableItemInfo {
	private String genre;
	private String director;
	private int length;

	public boolean setGenre(String aGenre) {
		boolean wasSet = false;
		genre = aGenre;
		wasSet = true;
		return wasSet;
	}

	public boolean setDirector(String aDirector) {
		boolean wasSet = false;
		director = aDirector;
		wasSet = true;
		return wasSet;
	}

	public boolean setLength(int aLength) {
		boolean wasSet = false;
		length = aLength;
		wasSet = true;
		return wasSet;
	}

	public String getGenre() {
		return genre;
	}

	public String getDirector() {
		return director;
	}

	public int getLength() {
		return length;
	}

}
