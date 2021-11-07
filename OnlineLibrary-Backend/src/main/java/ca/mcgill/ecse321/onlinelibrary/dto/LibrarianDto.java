package ca.mcgill.ecse321.onlinelibrary.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.mcgill.ecse321.onlinelibrary.model.Librarian;
import ca.mcgill.ecse321.onlinelibrary.model.LibrarianShift;

public class LibrarianDto {

	private Integer id;
	private String fullName;
	private String username;
	private boolean isHead;

	private List<LibrarianShiftDto> shifts;
	
	public LibrarianDto(int id, String fullName, String username, boolean isHead) {
		this.id = id;
		this.fullName = fullName;
		this.username = username;
		this.isHead = isHead;
		this.shifts = new ArrayList<LibrarianShiftDto>();
	}

	public static LibrarianDto fromLibrarian(Librarian librarian) {
		if (librarian == null) {
			throw new IllegalArgumentException("Librarian cannot be null.");
		}
		LibrarianDto librarianDto = new LibrarianDto(librarian.getId(), librarian.getFullName(), librarian.getUsername(),
				librarian.isHead());

		for(LibrarianShift shift : librarian.getShifts()) {
			librarianDto.shifts.add(LibrarianShiftDto.fromLibrarianShift(shift));
		}
		
		return librarianDto;
	}

	public int getId() {
		return this.id;
	}

	public String getFullName() {
		return this.fullName;
	}

	public String getUsername() {
		return this.username;
	}

	public boolean isHead() {
		return this.isHead;
	}
	

	public List<LibrarianShiftDto> getShifts() {
		return Collections.unmodifiableList(this.shifts);
	}
}
