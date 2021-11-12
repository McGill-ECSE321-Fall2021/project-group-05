package ca.mcgill.ecse321.onlinelibrary.dao;

import ca.mcgill.ecse321.onlinelibrary.model.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Integer> {
	/**
	 * Finds a copy of a movie using its unique ID.
	 * @param id the id of the movie copy
	 * @return the movie copy instance
	 */
	Movie findMovieById(Integer id);
}