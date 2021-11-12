package ca.mcgill.ecse321.onlinelibrary.dao;

import ca.mcgill.ecse321.onlinelibrary.model.Archive;
import org.springframework.data.repository.CrudRepository;

public interface ArchiveRepository extends CrudRepository<Archive, Integer>{
	/**
	 * Finds a copy of an archive document using its unique ID.
	 * @param id the id of the archive document copy
	 * @return the archive document copy instance
	 */
	Archive findArchiveById(Integer id);
}
