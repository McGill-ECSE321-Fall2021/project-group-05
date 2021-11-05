package ca.mcgill.ecse321.onlinelibrary.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.onlinelibrary.model.LibraryOpeningHours;
import java.sql.Date;
import java.util.ArrayList;

public interface LibraryOpeningHoursRepository extends CrudRepository<LibraryOpeningHours, Integer> {
	/**
	 * Finds library opening hours specification by using its unique ID
	 * @param id the id of the library opening hours specification
	 * @return the library opening hours instance
	 */
	LibraryOpeningHours findLibraryOpeningHoursById(int id);
	
	/**
	 * Finds library opening hours at a particular date  
	 * @param date of the library opening hours
	 * @return ArrayList of library opening hours at the date
	 */
	ArrayList<LibraryOpeningHours> findLibraryOpeningHoursByDate(Date date);
	
	/**
	 * Finds library opening hours contained in a date range 
	 * @param startDate of the date range
	 * @param endDate of the date range
	 * @return ArrayList of library opening hours in the range
	 */
	ArrayList<LibraryOpeningHours> findLibraryOpeningHoursByDateBetween(Date startDate, Date endDate);
	
	/**
	 * Deletes library opening hours by id
	 * @param unique ID of the library opening hours 
	 */
	void deleteById(int id);
}