package ca.mcgill.ecse321.onlinelibrary.dao;

import ca.mcgill.ecse321.onlinelibrary.model.LibraryItem;
import org.springframework.data.repository.CrudRepository;

public interface LibraryItemRepository extends CrudRepository<LibraryItem, Integer> {
    /**
     * Finds a copy of a library item using its unique ID.
     * @param id the id of the library item copy
     * @return the library item copy instance
     */
    LibraryItem findLibraryItemById(Integer id);
}


