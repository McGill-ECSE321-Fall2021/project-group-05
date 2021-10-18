package ca.mcgill.ecse321.onlinelibrary.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.onlinelibrary.model.Newspaper;

public interface NewspaperRepository extends CrudRepository<Newspaper, Integer>{
	/**
	 * Finds a copy of a newspaper using its unique ID.
	 * @param id the id of the newspaper copy
	 * @return the newspaper copy instance
	 */
	Newspaper findNewspaperById(Integer id);
}
