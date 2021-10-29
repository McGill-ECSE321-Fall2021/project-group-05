package ca.mcgill.ecse321.onlinelibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.onlinelibrary.dao.BookInfoRepository;
import ca.mcgill.ecse321.onlinelibrary.model.BookInfo;

@Service
public class OnlineLibraryService {
	public int errorCount=0;
	
	@Autowired
	BookInfoRepository bookInfoRepository;
	
	@Transactional
	public BookInfo createBookInfo(String title, int numberOfPage, String author, Integer isbn) {
		String errorMessage="";
		if (title == null || title.trim().length() == 0) {
			errorMessage+="Title can't be empty!";
			errorCount++;
		}
		
		if (numberOfPage == 0) {
			errorMessage+="Number of page can't be empty!";
			errorCount++;
		}
		
		if (author == null || author.trim().length() == 0) {
			errorMessage+="Author can't be empty!";
			errorCount++;
		}
		
		if (isbn == null) {
			errorMessage+="Isbn can't be empty!";
			errorCount++;
		}
		
		if (errorCount > 0) {
			throw new IllegalArgumentException(errorMessage.trim());
		}
		
		BookInfo bookInfo = new BookInfo();
		bookInfo.setTitle(title);
		bookInfo.setNumberOfPage(numberOfPage);
		bookInfo.setAuthor(author);
		bookInfo.setIsbn(isbn);
		bookInfoRepository.save(bookInfo);
		return bookInfo;
	}
	
}
