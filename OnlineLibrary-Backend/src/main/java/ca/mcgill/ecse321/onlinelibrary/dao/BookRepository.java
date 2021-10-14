package ca.mcgill.ecse321.onlinelibrary.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.onlinelibrary.model.Book;

public interface BookRepository extends CrudRepository<Book, Integer>{
	Book findBookById(Integer id);
}
