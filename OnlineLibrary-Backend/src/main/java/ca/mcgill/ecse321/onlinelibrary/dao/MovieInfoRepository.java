package ca.mcgill.ecse321.onlinelibrary.dao;

import ca.mcgill.ecse321.onlinelibrary.model.MovieInfo;
import org.springframework.data.repository.CrudRepository;

public interface MovieInfoRepository extends CrudRepository<MovieInfo, Integer> {
	MovieInfo findMovieInfoById(int id);
}
