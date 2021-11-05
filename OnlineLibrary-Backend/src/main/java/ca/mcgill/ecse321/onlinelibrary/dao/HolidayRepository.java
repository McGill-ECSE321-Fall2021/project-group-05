package ca.mcgill.ecse321.onlinelibrary.dao;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.onlinelibrary.model.Holiday;

public interface HolidayRepository extends CrudRepository<Holiday, Integer> {
	/**
	 * Finds a holiday by using its unique ID.
	 * @param id the id of the holiday
	 * @return the holiday instance
	 */
	Holiday findHolidayById(int id);
	
	/**
	 * Finds holidays containing a given date range.
	 * (Useful for checking if a given date range contains holidays.)
	 * @param startDate of the range
	 * @param endDate of the range
	 * @return ArrayList of holidays that fully contain the range.
	 */
	ArrayList<Holiday> findHolidayByStartDateLessThanEqualAndEndDateGreaterThanEqual(Date startDate, Date endDate);
	
	/*
	 * Finds holidays at specific date
	 * @param startDate of Holiday
	 * @param endDate of Holiday
	 * @return Arraylist of holidays at date
	 */
	ArrayList<Holiday> findHolidayByStartDateOrEndDate(Date startDate, Date endDate);
	
	/**
	 * Finds specific holiday by dates
	 * @param startDate of Holiday
	 * @param endDate of Holiday
	 * @return Arraylist of holidays at date
	 */
	ArrayList<Holiday> findHolidayByStartDateAndEndDate(Date startDate, Date endDate);
	
	/**
	 * Finds holidays contained in a given date range
	 * @param start date of the range 
	 * @param end date of the range
	 * @param start date of the range 
	 * @param end date of the range
	 * @return ArrayList of holidays contained in the range.
	 */
	ArrayList<Holiday> findHolidayByStartDateBetweenOrEndDateBetween(Date startDate1, Date startDate2, Date endDate1, Date endDate2);

	/**
	 * Deletes Holiday with unique id
	 * @param unique integer id
	 */
	void deleteById(int id);
}