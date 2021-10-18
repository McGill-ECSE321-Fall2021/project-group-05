package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.*;

@Entity
public class AlbumInfo extends ReservableItemInfo {
	private String title;
	private String composerPerformer;
	private String genre;

	public void setTitle(String aTitle) {
		title = aTitle;
	}

	public void setComposerPerformer(String aComposerPerformer) {
		composerPerformer = aComposerPerformer;
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
