package ca.mcgill.ecse321.onlinelibrary.dao;

import ca.mcgill.ecse321.onlinelibrary.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Integer> {
    /**
     * Finds a copy of a book using its unique ID.
     * @param id the id of the book copy
     * @return the book copy instance
     */
    Book findBookById(Integer id);
}
