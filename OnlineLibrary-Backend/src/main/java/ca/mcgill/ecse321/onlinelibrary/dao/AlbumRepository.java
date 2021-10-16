package ca.mcgill.ecse321.onlinelibrary.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.onlinelibrary.model.Album;

public interface AlbumRepository extends CrudRepository<Album, Integer> {
	Album findAlbumById(Integer id);
}