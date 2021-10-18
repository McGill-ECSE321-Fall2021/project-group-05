package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.*;

@Entity
public class MovieInfo extends ReservableItemInfo {
	private String genre;
	private String director;
	private int length;

	public void setGenre(String aGenre) {
		genre = aGenre;
	}

	public void setDirector(String aDirector) {
		director = aDirector;
	}

	public void setLength(int aLength) {
		length = aLength;
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
