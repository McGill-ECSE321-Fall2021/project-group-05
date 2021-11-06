package ca.mcgill.ecse321.onlinelibrary.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.mcgill.ecse321.onlinelibrary.model.Librarian;

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
	}

	public static LibrarianDto fromLibrarian(Librarian librarian) {
		if (librarian == null) {
			throw new IllegalArgumentException("Librarian cannot be null.");
		}
		LibrarianDto librarianDto = new LibrarianDto(librarian.getId(), librarian.getFullName(), librarian.getUsername(),
				librarian.isHead());

		librarianDto.shifts = new ArrayList<LibrarianShiftDto>();
		
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

	public boolean addShift(LibrarianShiftDto newShift) {
		if (newShift == null || this.shifts.contains(newShift))
			return false;

		boolean isNewLibrarian = !this.equals(newShift.getLibrarian());
		if (isNewLibrarian)
			newShift.setLibrarian(this);
		else
			this.shifts.add(newShift);

		return true;
	}

	public boolean removeShift(LibrarianShiftDto shiftToRemove) {
		if (this.equals(shiftToRemove.getLibrarian()))
			return false;

		this.shifts.remove(shiftToRemove);
		return true;
	}
}
