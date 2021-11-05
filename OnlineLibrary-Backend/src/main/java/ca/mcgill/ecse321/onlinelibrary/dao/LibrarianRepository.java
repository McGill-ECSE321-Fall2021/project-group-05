package ca.mcgill.ecse321.onlinelibrary.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.onlinelibrary.model.Librarian;

public interface LibrarianRepository extends CrudRepository<Librarian, Integer> {

	public Librarian findLibrarianById(int id);

	public boolean existsLibrarianByUsername(String username);
}
