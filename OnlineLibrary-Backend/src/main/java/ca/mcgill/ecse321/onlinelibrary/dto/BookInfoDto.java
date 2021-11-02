package ca.mcgill.ecse321.onlinelibrary.dto;



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