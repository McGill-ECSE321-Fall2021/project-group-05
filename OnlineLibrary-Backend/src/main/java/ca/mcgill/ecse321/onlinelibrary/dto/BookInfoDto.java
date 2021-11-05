package ca.mcgill.ecse321.onlinelibrary.dto;

import ca.mcgill.ecse321.onlinelibrary.model.BookInfo;

public class BookInfoDto extends ReservableItemInfoDto{
	private String title;
	private int numberOfPage;
	private String author;
	private long isbn;

	public BookInfoDto(String title, Integer numberOfPage, String author, long isbn, int id) {
		super(id);
		this.title=title;
		this.numberOfPage=numberOfPage;
		this.author=author;
		this.isbn=isbn;
	}

	public static BookInfoDto fromBookInfo(BookInfo bookInfo) {
		if (bookInfo == null) {
			throw new IllegalArgumentException("There is no such bookInfo.");
		}
		return new BookInfoDto(bookInfo.getTitle(), bookInfo.getNumberOfPage(), bookInfo.getAuthor(),
				bookInfo.getIsbn(), bookInfo.getId());
	}

	public String getTitle() {
		return this.title;
	}

	public String getAuthor() {
		return this.author;
	}

	public int getNumberOfPage() {
		return this.numberOfPage;
	}

	public long getIsbn () {
		return this.isbn;
	}
}
