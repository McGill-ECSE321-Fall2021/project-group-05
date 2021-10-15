import java.util.*;

public class MovieInfo extends ReservableItemInfo{
	private String genre;
	private String director;
	private int length;

	public MovieInfo(int aId, String aGenre, String aDirector, int aLength) {
	super(aId);
	genre = aGenre;
	director = aDirector;
	length = aLength;
	}

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
	 
	public void delete() {
	super.delete();
	}

	public String toString() {
	return super.toString() + "["+
	"genre" + ":" + getGenre()+ "," +
	"director" + ":" + getDirector()+ "," +
	"length" + ":" + getLength()+ "]";
	}
}
