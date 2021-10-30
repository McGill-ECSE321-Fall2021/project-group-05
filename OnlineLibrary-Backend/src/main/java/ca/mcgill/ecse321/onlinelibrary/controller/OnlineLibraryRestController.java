package ca.mcgill.ecse321.onlinelibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.onlinelibrary.dto.BookInfoDto;
import ca.mcgill.ecse321.onlinelibrary.dto.MovieInfoDto;
import ca.mcgill.ecse321.onlinelibrary.model.BookInfo;
import ca.mcgill.ecse321.onlinelibrary.model.MovieInfo;
import ca.mcgill.ecse321.onlinelibrary.service.OnlineLibraryService;

@CrossOrigin(origins = "*")
@RestController
public class OnlineLibraryRestController {

	@Autowired
	private OnlineLibraryService service;
	
	@PostMapping(value = { "/bookInfo/{title}"})
	public BookInfoDto createBookInfo(@PathVariable("title") String title, @RequestParam int numberOfPage,  
			@RequestParam String author, @RequestParam long isbn) throws IllegalArgumentException {
		BookInfo bookInfo = service.createBookInfo(title, numberOfPage, author, isbn);
		return convertToDto(bookInfo);
	}
	
	@PostMapping(value = { "/movieInfo", "/movieInfo/" })
	public MovieInfoDto createMovieInfo(@RequestParam String genre, @RequestParam String director, @RequestParam int length) 
			throws IllegalArgumentException {
		MovieInfo movieInfo = service.createMovieInfo(genre, director, length);
		return convertToDto(movieInfo);
	}
	
	private BookInfoDto convertToDto (BookInfo bookInfo) {
		if (bookInfo == null) {
			throw new IllegalArgumentException("There is no such bookInfo");
		}
		return new BookInfoDto(bookInfo.getTitle(),bookInfo.getNumberOfPage(),bookInfo.getAuthor(),bookInfo.getIsbn(), bookInfo.getId());
	}
	
	private MovieInfoDto convertToDto (MovieInfo movieInfo) {
		if (movieInfo == null) {
			throw new IllegalArgumentException("There is no such movieInfo");
		}
		return new MovieInfoDto(movieInfo.getId(),movieInfo.getGenre(), movieInfo.getDirector(), movieInfo.getLength());
	}
	
	
	
}
