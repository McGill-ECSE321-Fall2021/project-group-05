package ca.mcgill.ecse321.onlinelibrary.dto;

import ca.mcgill.ecse321.onlinelibrary.model.Book;

public class BookDto extends ReservableItemDto {
	private BookInfoDto bookInfo;

	public BookDto(int id, ItemStatusDto itemStatus ,BookInfoDto bookInfo) {
		super(id, itemStatus);
		this.bookInfo = bookInfo;
	}

	public static BookDto fromBook(Book book) {
		if (book == null) {
			throw new IllegalArgumentException("There is no such book.");
		}
		return new BookDto(book.getId(), convertToDto(book.getStatus()), BookInfoDto.fromBookInfo(book.getBookInfo()));
	}

	public BookInfoDto getBookInfo() {
		return this.bookInfo;
	}
}
