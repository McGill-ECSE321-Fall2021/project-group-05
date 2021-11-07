package ca.mcgill.ecse321.onlinelibrary.dto;

import ca.mcgill.ecse321.onlinelibrary.model.LibraryItemInfo;

public abstract class LibraryItemInfoDto {
	private Integer id;
	
	public LibraryItemInfoDto(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
}
