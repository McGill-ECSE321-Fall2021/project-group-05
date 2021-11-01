package ca.mcgill.ecse321.onlinelibrary.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.onlinelibrary.dao.BookInfoRepository;
import ca.mcgill.ecse321.onlinelibrary.model.BookInfo;

@Service
public class OnlineLibraryService {
	
	@Autowired
	BookInfoRepository bookInfoRepository;
	
	@Transactional
	public BookInfo createBookInfo(String title, int numberOfPage, String author, long isbn) {
		ArrayList<String> errorMessage = new ArrayList<String>();
		int errorCount=0;
		if (title == null || title.trim().length() == 0) {
			errorMessage.add("Title can't be empty.");
			errorCount++;
		}
		
		if (numberOfPage == 0) {
			errorMessage.add("Number of page can't be 0.");
			errorCount++;
		}
		
		if (author == null || author.trim().length() == 0) {
			errorMessage.add("Author can't be empty.");
			errorCount++;
		}
		
		if (errorCount > 0) {
			throw new IllegalArgumentException(String.join(" ", errorMessage));
		}
		
		BookInfo bookInfo = new BookInfo();
		bookInfo.setTitle(title);
		bookInfo.setNumberOfPage(numberOfPage);
		bookInfo.setAuthor(author);
		bookInfo.setIsbn(isbn);
		bookInfoRepository.save(bookInfo);
		return bookInfo;
	}
	
	@Transactional
	public void deleteBookInfo(int id) {
		if (bookInfoRepository.findBookInfoById(id) == null) {
			throw new IllegalArgumentException("This bookInfo item doesn't exist.");
		} else {
			bookInfoRepository.deleteById(id);
		}
	}
	
}
