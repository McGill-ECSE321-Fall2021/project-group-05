package ca.mcgill.ecse321.onlinelibrary.model;

import ca.mcgill.ecse321.onlinelibrary.dto.BookInfoDto;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class BookInfo extends ReservableItemInfo {
	private String title;
	private int numberOfPage;
	private String author;
	@Column(unique = true)
	private long isbn;
	public void setTitle(String aTitle) {
		title = aTitle;
	}

	public void setNumberOfPage(int aNumberOfPage) {
		numberOfPage = aNumberOfPage;
	}

	public void setAuthor(String aAuthor) {
		author = aAuthor;
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

	public long getIsbn() {
		return this.isbn;
	}

	public void setIsbn(long isbn) {
		this.isbn = isbn;
	}

	public BookInfoDto convertToDto() {
		return new BookInfoDto(title, numberOfPage, author, isbn, this.getId());
	}
}
