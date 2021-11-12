package ca.mcgill.ecse321.onlinelibrary.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.onlinelibrary.dao.LibrarianRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.LibrarianShiftRepository;
import ca.mcgill.ecse321.onlinelibrary.model.Librarian;
import ca.mcgill.ecse321.onlinelibrary.model.LibrarianShift;

@Service
public class LibrarianShiftService {
	@Autowired
	LibrarianShiftRepository librarianShiftRepository;
	

	@Autowired
	LibrarianRepository librarianRepository;

	@Transactional
	public void deleteLibrarianShift(int id) {
		librarianShiftRepository.deleteById(id);
	}
	
	@Transactional
	public ArrayList<LibrarianShift> getLibrarianShift(LocalDate date) {
		if(date == null) 
			throw new IllegalArgumentException("A date parameter is required.");
		
		return librarianShiftRepository.findLibrarianShiftByDate(Date.valueOf(date));
	}
	
	@Transactional
	public ArrayList<LibrarianShift> getLibrarianShift(int librarianId) {
		return librarianShiftRepository.findLibrarianShiftByLibrarianId(librarianId);
	}
	
	@Transactional
	public ArrayList<LibrarianShift> getLibrarianShift(int librarianId, LocalDate date) {
		if(date == null) 
			throw new IllegalArgumentException("A date parameter is required.");
		
		return librarianShiftRepository.findLibrarianShiftByDateAndLibrarianId(Date.valueOf(date), librarianId);
	}
	
	@Transactional
	public LibrarianShift createLibrarianShift(LocalDate date, LocalTime startTime, LocalTime endTime, int librarianId) {
		ArrayList<String> errorMessage = new ArrayList<String>();
		int errorCount=0;

		if (date == null) {
			errorMessage.add("Date can't be empty.");
			errorCount++;
		}

		if (startTime == null) {
			errorMessage.add("Start Time can't be empty.");
			errorCount++;
		}

		if (endTime == null) {
			errorMessage.add("End Time can't be empty.");
			errorCount++;
		}

		if(startTime != null && endTime != null && startTime.compareTo(endTime) > 0) {
			errorMessage.add("Start Time can't be after End Time.");
			errorCount++;
		}

		Librarian librarian = librarianRepository.findLibrarianById(librarianId);
		if (librarian == null) {
			errorMessage.add("Librarian does not exist.");
			errorCount++;
		}

		if(errorCount == 0) {
			ArrayList<LibrarianShift> shifts = 
					librarianShiftRepository.findLibrarianShiftByDateAndLibrarianId(Date.valueOf(date), librarian.getId());

			if(shifts != null && shifts.size() != 0) {
				for(LibrarianShift shift : shifts) {
					if(isOverlapping(Time.valueOf(startTime), Time.valueOf(endTime), shift.getStartTime(), shift.getEndTime())) {
						errorMessage.add("An overlapping shift already exists for the assigned librarian.");
						errorCount++;
						break;
					}
				}
			}	
		}

		if (errorCount > 0) {
			throw new IllegalArgumentException(String.join(" ", errorMessage));
		}

		LibrarianShift librarianShift = new LibrarianShift(Date.valueOf(date), Time.valueOf(startTime), Time.valueOf(endTime), librarian);
		librarian.addShift(librarianShift);
		librarianShiftRepository.save(librarianShift);
		librarianRepository.save(librarian);

		return librarianShift;
	}
	
	private boolean isOverlapping(Time start1, Time end1, Time start2, Time end2) {
		return (start2.compareTo(start1) == -1 && end2.compareTo(start1) == 1)
				|| (end2.compareTo(end1) == 1 && start2.compareTo(end1) == -1)
				|| (start1.compareTo(start2) == 0 && end1.compareTo(end2) == 0);
	}
}
