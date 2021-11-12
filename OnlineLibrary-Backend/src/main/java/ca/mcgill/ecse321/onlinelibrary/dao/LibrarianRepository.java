package ca.mcgill.ecse321.onlinelibrary.dao;

import ca.mcgill.ecse321.onlinelibrary.model.Librarian;
import org.springframework.data.repository.CrudRepository;

public interface LibrarianRepository extends CrudRepository<Librarian, Integer> {

	public Librarian findLibrarianById(int id);

	public Librarian findLibrarianByUsername(String username);

	public boolean existsLibrarianByUsername(String username);
}
