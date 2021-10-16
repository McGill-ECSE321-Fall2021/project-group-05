package ca.mcgill.ecse321.onlinelibrary.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.onlinelibrary.model.LibraryOpeningHours;

public interface LibraryOpeningHoursRepository extends CrudRepository<LibraryOpeningHours, Integer> {
	LibraryOpeningHours findLibraryOpeningHoursById(int id);
}