package ca.mcgill.ecse321.onlinelibrary.service;

import java.util.ArrayList;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ca.mcgill.ecse321.onlinelibrary.dao.BookRepository;
import ca.mcgill.ecse321.onlinelibrary.model.*;
import ca.mcgill.ecse321.onlinelibrary.model.ReservableItem.ItemStatus;

@Service
public class LibraryItemService {

	@Autowired
	private BookRepository bookRepository;

	@Transactional
	public Book createBook(BookInfo bookInfo) {
		ArrayList<String> errorMessage = new ArrayList<String>();
		int errorCount = 0;
		if (bookInfo == null) {
			errorMessage.add("BookInfo can't be empty");
			errorCount++;
		}
		if (errorCount > 0) {
			throw new IllegalArgumentException(String.join(" ", errorMessage));
		}

		Book book = new Book(bookInfo);
		book.setStatus(ItemStatus.Available);
		bookRepository.save(book);
		return book;
	}
}
