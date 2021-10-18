package ca.mcgill.ecse321.onlinelibrary.dao;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.onlinelibrary.model.MovieInfo;

public interface MovieInfoRepository extends CrudRepository<MovieInfo, Integer> {
    MovieInfo findMovieInfoById(int id);
}
