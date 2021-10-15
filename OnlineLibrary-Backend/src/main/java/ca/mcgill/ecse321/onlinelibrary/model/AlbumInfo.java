import java.util.*;

public class AlbumInfo extends ReservableItemInfo{
	private String title;
	private String composerPerformer;
	private String genre;

	public AlbumInfo(int aId, String aTitle, String aComposerPerformer, String aGenre) {
	super(aId);
	title = aTitle;
	composerPerformer = aComposerPerformer;
	genre = aGenre;
	}

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
	
	public boolean setGenre(String aGenre) {
	boolean wasSet = false;
	genre = aGenre;
	wasSet = true;
	return wasSet;
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
	
	public void delete() {
	super.delete();
	}
	 	 
	public String toString() {
	return super.toString() + "["+
	"title" + ":" + getTitle()+ "," +
	"composerPerformer" + ":" + getComposerPerformer()+ "," +
	"genre" + ":" + getGenre()+ "]";
	}
}
