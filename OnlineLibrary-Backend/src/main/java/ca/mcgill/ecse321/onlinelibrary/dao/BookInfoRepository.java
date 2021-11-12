package ca.mcgill.ecse321.onlinelibrary.dao;

import ca.mcgill.ecse321.onlinelibrary.model.BookInfo;
import org.springframework.data.repository.CrudRepository;

public interface BookInfoRepository extends CrudRepository<BookInfo, Integer> {
    BookInfo findBookInfoById(Integer id);
}
