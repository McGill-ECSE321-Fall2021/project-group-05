package ca.mcgill.ecse321.onlinelibrary.dao;

import ca.mcgill.ecse321.onlinelibrary.model.Newspaper;
import org.springframework.data.repository.CrudRepository;

public interface NewspaperRepository extends CrudRepository<Newspaper, Integer>{
	/**
	 * Finds a copy of a newspaper using its unique ID.
	 * @param id the id of the newspaper copy
	 * @return the newspaper copy instance
	 */
	Newspaper findNewspaperById(Integer id);
}
