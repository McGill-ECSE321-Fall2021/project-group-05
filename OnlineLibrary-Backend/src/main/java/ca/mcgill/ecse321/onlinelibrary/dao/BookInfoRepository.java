package ca.mcgill.ecse321.onlinelibrary.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.onlinelibrary.model.BookInfo;

public interface BookInfoRepository extends CrudRepository<BookInfo, Integer> {
    BookInfo findBookInfoByIsbn(Integer isbn);
}
