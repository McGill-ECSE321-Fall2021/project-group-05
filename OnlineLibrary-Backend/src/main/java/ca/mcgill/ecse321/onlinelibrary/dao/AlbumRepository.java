package ca.mcgill.ecse321.onlinelibrary.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.onlinelibrary.model.Album;

public interface AlbumRepository extends CrudRepository<Album, Integer> {
	/**
	 * Finds a copy of an album using its unique ID.
	 * @param id the id of the album copy
	 * @return the album copy instance
	 */
	Album findAlbumById(Integer id);
}