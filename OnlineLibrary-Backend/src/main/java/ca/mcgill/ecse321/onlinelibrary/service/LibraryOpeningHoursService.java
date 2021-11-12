package ca.mcgill.ecse321.onlinelibrary.service;

import ca.mcgill.ecse321.onlinelibrary.dao.HolidayRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.LibraryOpeningHoursRepository;
import ca.mcgill.ecse321.onlinelibrary.model.Holiday;
import ca.mcgill.ecse321.onlinelibrary.model.LibraryOpeningHours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

@Service
public class LibraryOpeningHoursService {
	@Autowired
	LibraryOpeningHoursRepository libraryOpeningHoursRepository;

	@Autowired
	HolidayRepository holidayRepository;

	@Transactional
	public void deleteLibraryOpeningHours(int id) {
		libraryOpeningHoursRepository.deleteById(id);
	}
	
	@Transactional
	public ArrayList<LibraryOpeningHours> getLibraryOpeningHours(LocalDate date) {
		if(date == null) 
			throw new IllegalArgumentException("A date is required.");
		
		
		ArrayList<LibraryOpeningHours> openingHoursOfDate = libraryOpeningHoursRepository.findLibraryOpeningHoursByDate(Date.valueOf(date));
		
		return openingHoursOfDate;
	}
	
	@Transactional
	public ArrayList<LibraryOpeningHours> getLibraryOpeningHours(LocalDate startDate, LocalDate endDate) {
		if(startDate == null || endDate == null) 
			throw new IllegalArgumentException("Two date parameters are required.");
		
		if(startDate.compareTo(endDate) > 0) 
			throw new IllegalArgumentException("The start date can't be after the end date.");
		
		ArrayList<LibraryOpeningHours> openingHoursOfDate = 
				libraryOpeningHoursRepository.findLibraryOpeningHoursByDateBetween(Date.valueOf(startDate), Date.valueOf(endDate));
		
		return openingHoursOfDate;
	}
	
	@Transactional
	public LibraryOpeningHours createLibraryOpeningHours(LocalDate date, LocalTime startTime, LocalTime endTime) {
		ArrayList<String> errorMessage = new ArrayList<String>();
		int errorCount=0;
		if (date == null) {
			errorMessage.add("Date can't be empty.");
			errorCount++;
		} else {
			ArrayList<Holiday> holidays = 
					holidayRepository.findHolidayByStartDateLessThanEqualAndEndDateGreaterThanEqual(Date.valueOf(date), Date.valueOf(date));
			
			if(holidays != null && holidays.size() != 0) {
				errorMessage.add("Date coincides with a federal holiday.");
				errorCount++;
			}		
		}
		
		if (startTime == null) {
			errorMessage.add("Start Time can't be empty.");
			errorCount++;
		}
		
		if (endTime == null) {
			errorMessage.add("End Time can't be empty.");
			errorCount++;
		}
		
		if(startTime != null && endTime != null && startTime.compareTo(endTime) >= 0) {
			errorMessage.add("Start Time must be before End Time.");
			errorCount++;
		} else if (date != null) {
			ArrayList<LibraryOpeningHours> openingHoursOfDate = 
					libraryOpeningHoursRepository.findLibraryOpeningHoursByDate(Date.valueOf(date));

			if (openingHoursOfDate != null) {
				for(LibraryOpeningHours openingHours : openingHoursOfDate) {
					if(isOverlapping(Time.valueOf(startTime), Time.valueOf(endTime), openingHours.getStartTime(), openingHours.getEndTime())) {    
						errorMessage.add("Opening hours have already been set for this date and time.");
						errorCount++;
						break;
					}
				}
			}
		}
		
		if (errorCount > 0) {
			throw new IllegalArgumentException(String.join(" ", errorMessage));
		}
		
		LibraryOpeningHours libraryOpeningHours = new LibraryOpeningHours(Date.valueOf(date), Time.valueOf(startTime), Time.valueOf(endTime));
		libraryOpeningHoursRepository.save(libraryOpeningHours);
		
		return libraryOpeningHours;
	}
	
	private boolean isOverlapping(Time start1, Time end1, Time start2, Time end2) {
		return (start2.compareTo(start1) == -1 && end2.compareTo(start1) == 1)
				|| (end2.compareTo(end1) == 1 && start2.compareTo(end1) == -1)
				|| (start1.compareTo(start2) == 0 && end1.compareTo(end2) == 0);
	}
}
