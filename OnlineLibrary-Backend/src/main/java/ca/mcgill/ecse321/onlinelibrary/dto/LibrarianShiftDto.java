package ca.mcgill.ecse321.onlinelibrary.dto;

import ca.mcgill.ecse321.onlinelibrary.model.LibrarianShift;

import java.sql.Date;
import java.sql.Time;

public class LibrarianShiftDto {
	private Integer id;
	private Date date;
	private Time startTime;
	private Time endTime;

	// Association
	private int librarianId;

	// Constructors
	public LibrarianShiftDto(int id, Date date, Time startTime, Time endTime, int librarianId) {
		this.id = id;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.librarianId = librarianId;
	}

	// Interface
	public Integer getId() {
		return this.id;
	}

	public Date getDate() {
		return this.date;
	}

	public Time getStartTime() {
		return this.startTime;
	}

	public Time getEndTime() {
		return this.endTime;
	}

	public int getLibrarianId() {
		return this.librarianId;
	}
	
	public static LibrarianShiftDto fromLibrarianShift(LibrarianShift shift) {
		if(shift == null) {
			throw new IllegalArgumentException("There is no such Librarian Shift.");
		}

		return new LibrarianShiftDto(shift.getId(), shift.getDate(), shift.getStartTime(), shift.getEndTime(), shift.getLibrarian().getId());
	}
}
