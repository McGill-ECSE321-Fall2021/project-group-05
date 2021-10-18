package ca.mcgill.ecse321.onlinelibrary.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.onlinelibrary.model.LibraryOpeningHours;

public interface LibraryOpeningHoursRepository extends CrudRepository<LibraryOpeningHours, Integer> {
	/**
	 * Finds library opening hours specification by using its unique ID
	 * @param id the id of the library opening hours specification
	 * @return the library opening hours instance
	 */
	LibraryOpeningHours findLibraryOpeningHoursById(int id);
}