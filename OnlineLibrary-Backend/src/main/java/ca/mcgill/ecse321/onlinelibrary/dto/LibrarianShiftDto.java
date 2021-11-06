package ca.mcgill.ecse321.onlinelibrary.dto;

import java.sql.Date;
import java.sql.Time;
import ca.mcgill.ecse321.onlinelibrary.model.LibrarianShift;

public class LibrarianShiftDto {
	private Integer id;
	private Date date;
	private Time startTime;
	private Time endTime;

	// Association
	private LibrarianDto librarian;

	// Constructors
	public LibrarianShiftDto(int id, Date date, Time startTime, Time endTime, LibrarianDto librarian) {
		this.id = id;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;

		if (librarian == null)
			throw new IllegalArgumentException("A Librarian is required for every LibrarianShift.");
		this.librarian = librarian;
		librarian.addShift(this);
	}

	// Interface
	public Integer getId() {
		return this.id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date newDate) {
		this.date = newDate;
	}

	public Time getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Time newStartTime) {
		this.startTime = newStartTime;
	}

	public Time getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Time newEndTime) {
		this.endTime = newEndTime;
	}

	public LibrarianDto getLibrarian() {
		return this.librarian;
	}

	public void setLibrarian(LibrarianDto newLibrarian) {
		if (newLibrarian == null)
			throw new IllegalArgumentException("A Librarian is required for every LibrarianShift.");
		if (newLibrarian == this.librarian)
			return;

		LibrarianDto existingLibrarian = this.librarian;
		this.librarian = newLibrarian;

		if (existingLibrarian != null)
			existingLibrarian.removeShift(this);
		this.librarian.addShift(this);
	}
	
	public static LibrarianShiftDto fromLibrarianShift(LibrarianShift shift) {
		if(shift == null) {
			throw new IllegalArgumentException("There is no such Librarian Shift.");
		}
		if(shift.getLibrarian() == null) {
			throw new IllegalArgumentException("The shift does not correspond to any librarian."); // Though librarian is non-optional for shifts
		}

		return new LibrarianShiftDto(shift.getId(), shift.getDate(), shift.getStartTime(), shift.getEndTime(), LibrarianDto.fromLibrarian(shift.getLibrarian()) );
	}
}
