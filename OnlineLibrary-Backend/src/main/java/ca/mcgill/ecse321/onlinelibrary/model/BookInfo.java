package ca.mcgill.ecse321.onlinelibrary.model;
import javax.persistence.*;

@Entity
public class BookInfo extends ReservableItemInfo{
	private String title;
	private int numberOfPage;
	private String author;
	private int isbn;

	public boolean setTitle(String aTitle) {
	boolean wasSet = false;
	title = aTitle;
	wasSet = true;
	return wasSet;
	}

	public boolean setNumberOfPage(int aNumberOfPage) {
	boolean wasSet = false;
	numberOfPage = aNumberOfPage;
	wasSet = true;
	return wasSet;
	}
	 
	public boolean setAuthor(String aAuthor) {
	boolean wasSet = false;
	author = aAuthor;
	wasSet = true;
	return wasSet;
	}
	
	public String getTitle() {
	return title;
	}
	 
	public int getNumberOfPage() {
	return numberOfPage;
	}
	 
	public String getAuthor() {
	return author;
	}
	 
	public void delete() {
	super.delete();
	}

	public String toString() {
	return super.toString() + "["+
	"title" + ":" + getTitle()+ "," +
	"numberOfPage" + ":" + getNumberOfPage()+ "," +
	"author" + ":" + getAuthor()+ "]";
	}

	public int getIsbn(){
		return this.isbn;
	}

	public Boolean setIsbn(int isbn){
		this.isbn = isbn;
		return true;
	}
}
