package ca.mcgill.ecse321.onlinelibrary.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.onlinelibrary.model.Holiday;

public interface HolidayRepository extends CrudRepository<Holiday, Integer> {
	Holiday findHolidayById(int id);
}