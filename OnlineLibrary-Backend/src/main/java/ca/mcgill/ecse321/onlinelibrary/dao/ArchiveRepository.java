package ca.mcgill.ecse321.onlinelibrary.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.onlinelibrary.model.Archive;

public interface ArchiveRepository extends CrudRepository<Archive, Integer>{
	Archive findArchiveById(Integer id);
}
