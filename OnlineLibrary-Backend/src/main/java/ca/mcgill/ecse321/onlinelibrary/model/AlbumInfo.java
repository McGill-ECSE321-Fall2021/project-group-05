package ca.mcgill.ecse321.onlinelibrary.model;

import ca.mcgill.ecse321.onlinelibrary.dto.AlbumInfoDto;

import javax.persistence.Entity;

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

	public AlbumInfoDto convertToDto() {
		return new AlbumInfoDto(this.getId(), this.title, this.composerPerformer, this.genre);
	}

}
