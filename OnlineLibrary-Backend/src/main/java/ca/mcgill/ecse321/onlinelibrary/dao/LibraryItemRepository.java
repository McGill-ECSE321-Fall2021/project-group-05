package ca.mcgill.ecse321.onlinelibrary.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.onlinelibrary.model.LibraryItem;

public interface LibraryItemRepository extends CrudRepository<LibraryItem, Integer> {
	LibraryItem findLibraryItemById(Integer id);
}


