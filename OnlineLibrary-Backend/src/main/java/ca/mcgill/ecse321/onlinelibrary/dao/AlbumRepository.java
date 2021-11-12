package ca.mcgill.ecse321.onlinelibrary.dao;

import ca.mcgill.ecse321.onlinelibrary.model.Album;
import org.springframework.data.repository.CrudRepository;

public interface AlbumRepository extends CrudRepository<Album, Integer> {
	/**
	 * Finds a copy of an album using its unique ID.
	 * @param id the id of the album copy
	 * @return the album copy instance
	 */
	Album findAlbumById(Integer id);
}