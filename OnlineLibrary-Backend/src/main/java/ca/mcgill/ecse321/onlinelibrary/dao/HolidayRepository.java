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
}