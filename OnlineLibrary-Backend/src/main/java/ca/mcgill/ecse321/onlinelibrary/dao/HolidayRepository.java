package ca.mcgill.ecse321.onlinelibrary.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.onlinelibrary.model.Holiday;

public interface HolidayRepository extends CrudRepository<Holiday, Integer> {
	/**
	 * Finds a holiday by using its unique ID.
	 * @param id the id of the holiday
	 * @return the holiday instance
	 */
	Holiday findHolidayById(int id);
}