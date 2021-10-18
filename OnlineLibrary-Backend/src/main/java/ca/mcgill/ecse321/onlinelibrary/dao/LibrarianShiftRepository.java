package ca.mcgill.ecse321.onlinelibrary.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.onlinelibrary.model.LibrarianShift;

public interface LibrarianShiftRepository extends CrudRepository<LibrarianShift, Integer> {

	public LibrarianShift findLibrarianShiftById(int id);
}
