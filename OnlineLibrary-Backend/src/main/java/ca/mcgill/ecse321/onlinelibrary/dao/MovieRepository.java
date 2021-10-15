package ca.mcgill.ecse321.onlinelibrary.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.onlinelibrary.model.Movie;

public interface MovieRepository extends CrudRepository<Movie, Integer> {
	Movie findMovieById(Integer id);
}