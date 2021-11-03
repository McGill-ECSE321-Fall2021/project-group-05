package ca.mcgill.ecse321.onlinelibrary.dto;

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

