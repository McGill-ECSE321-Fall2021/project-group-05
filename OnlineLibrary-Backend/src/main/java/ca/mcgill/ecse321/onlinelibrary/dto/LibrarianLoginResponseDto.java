package ca.mcgill.ecse321.onlinelibrary.dto;

import java.util.ArrayList;

import ca.mcgill.ecse321.onlinelibrary.model.Librarian;
import ca.mcgill.ecse321.onlinelibrary.model.LibrarianShift;

public class LibrarianLoginResponseDto {

	// TODO Add JWT token

	private LibrarianDto librarian;

	public LibrarianLoginResponseDto(Librarian librarian) {
		this.librarian = LibrarianDto.fromLibrarian(librarian, new ArrayList<LibrarianShift>());
	}

	public LibrarianDto getLibrarian() {
		return this.librarian;
	}
}
