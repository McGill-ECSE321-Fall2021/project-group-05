package ca.mcgill.ecse321.onlinelibrary.dto;

import ca.mcgill.ecse321.onlinelibrary.model.AlbumInfo;

public class AlbumInfoDto extends ReservableItemInfoDto{
	private String title;
	private String composerPerformer;
	private String genre;

	public AlbumInfoDto(int id, String title, String composerPerformer, String genre) {
		super(id);
		this.title=title;
		this.composerPerformer=composerPerformer;
		this.genre=genre;
	}

	public static AlbumInfoDto fromAlbumInfo(AlbumInfo albumInfo) {
		if (albumInfo == null) {
			throw new IllegalArgumentException("There is no such albumInfo.");
		}
		return new AlbumInfoDto(albumInfo.getId(), albumInfo.getTitle(), albumInfo.getComposerPerformer(),
				albumInfo.getGenre());
	}

	public String getTitle() {
		return this.title;
	}

	public String getComposerPerformer() {
		return this.composerPerformer;
	}

	public String getGenre() {
		return this.genre;
	}
}
