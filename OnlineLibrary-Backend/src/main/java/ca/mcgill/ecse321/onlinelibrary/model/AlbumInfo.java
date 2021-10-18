package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.*;

@Entity
public class AlbumInfo extends ReservableItemInfo {
	private String title;
	private String composerPerformer;
	private String genre;

	public boolean setTitle(String aTitle) {
		boolean wasSet = false;
		title = aTitle;
		wasSet = true;
		return wasSet;
	}

	public boolean setComposerPerformer(String aComposerPerformer) {
		boolean wasSet = false;
		composerPerformer = aComposerPerformer;
		wasSet = true;
		return wasSet;
	}

	public void setGenre(String aGenre) {
		genre = aGenre;
	}

	public String getTitle() {
		return title;
	}

	public String getComposerPerformer() {
		return composerPerformer;
	}

	public String getGenre() {
		return genre;
	}

}
