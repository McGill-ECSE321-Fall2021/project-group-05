package ca.mcgill.ecse321.onlinelibrary.dto;

public abstract class LibraryItemDto {
	private int id;
	
	public LibraryItemDto(int id) {
		this.id=id;
	}
	
	public int getId() {
		return this.id;
	}
}
