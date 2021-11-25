package ca.mcgill.ecse321.onlinelibrary.dto;

public abstract class LibraryItemInfoDto {
	private Integer id;
	
	public LibraryItemInfoDto(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}

	public abstract String getType();
}
