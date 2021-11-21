package ca.mcgill.ecse321.onlinelibrary.dto;

import ca.mcgill.ecse321.onlinelibrary.model.Librarian;

public class LibrarianLoginResponseDto {

	private LibrarianDto librarian;

	public LibrarianLoginResponseDto(Librarian librarian) {
		this.librarian = LibrarianDto.fromLibrarian(librarian, null);
	}

	public LibrarianDto getLibrarian() {
		return this.librarian;
	}
}
