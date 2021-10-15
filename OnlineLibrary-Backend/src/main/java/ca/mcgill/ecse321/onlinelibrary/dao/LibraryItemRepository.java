package ca.mcgill.ecse321.onlinelibrary.dao;

import ca.mcgill.ecse321.onlinelibrary.model.LibraryItem;
import org.springframework.data.repository.CrudRepository;

public interface LibraryItemRepository extends CrudRepository<LibraryItem, Integer> {
    LibraryItem findLibraryItemById(Integer id);
}


