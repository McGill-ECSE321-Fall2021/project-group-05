package ca.mcgill.ecse321.onlinelibrary.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.onlinelibrary.dao.BookInfoRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.MovieInfoRepository;
import ca.mcgill.ecse321.onlinelibrary.model.BookInfo;
import ca.mcgill.ecse321.onlinelibrary.model.MovieInfo;

@Service
public class OnlineLibraryService {
	
	@Autowired
	BookInfoRepository bookInfoRepository;
	
	@Autowired
	MovieInfoRepository movieInfoRepository;
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
	public MovieInfo createMovieInfo(String genre, String director, int length) {
		ArrayList<String> errorMessage = new ArrayList<String>();
		int errorCount=0;
		if (genre == null || genre.trim().length() == 0) {
			errorMessage.add("Genre can't be empty.");
			errorCount++;
		}
		
		if (director == null || director.trim().length() == 0) {
			errorMessage.add("Director can't be empty.");
			errorCount++;
		}
		
		if (length == 0) {
			errorMessage.add("Length can't be 0.");
			errorCount++;
		}
		
		if (errorCount > 0) {
			throw new IllegalArgumentException(String.join(" ", errorMessage));
		}
		
		MovieInfo movieInfo = new MovieInfo();
		movieInfo.setGenre(genre);
		movieInfo.setDirector(director);
		movieInfo.setLength(length);
		movieInfoRepository.save(movieInfo);
		return movieInfo;
	}
	
}
