package ca.mcgill.ecse321.onlinelibrary.dto;

public class BookDto extends ReservableItemDto {
	private BookInfoDto bookInfo;
	
	public BookDto(int id, ItemStatusDto itemStatus ,BookInfoDto bookInfo) {
		super(id, itemStatus);
		this.bookInfo = bookInfo;
	}
	
	public BookInfoDto getBookInfo() {
		return this.bookInfo;
	}
	
}
