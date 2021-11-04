package ca.mcgill.ecse321.onlinelibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.onlinelibrary.dto.*;
import ca.mcgill.ecse321.onlinelibrary.dto.ReservableItemDto.ItemStatusDto;
import ca.mcgill.ecse321.onlinelibrary.model.*;
import ca.mcgill.ecse321.onlinelibrary.model.ReservableItem.ItemStatus;
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
	
	@PostMapping (value = { "/book/{bookInfoId}", "/book/{bookInfoId}/"})
	public BookDto createBookDto(@PathVariable("bookInfoId") int bookInfoId) throws IllegalArgumentException {
		BookInfo bookInfo = service.getBookInfo(bookInfoId);
		Book book = service.createBook(bookInfo);
		return convertToDto(book);
	}
	
	@PostMapping(value = {"/albumInfo/{title}", "/albumInfo/{title}/"})
	public AlbumInfoDto createAlbumInfo(@PathVariable("title") String title, @RequestParam String composerPerformer, String genre)
	throws IllegalArgumentException {
		AlbumInfo albumInfo = service.createAlbumInfo(title, composerPerformer, genre);
		return convertToDto(albumInfo);
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
	
	private BookDto convertToDto (Book book) {
		if (book == null) {
			throw new IllegalArgumentException("There is no such book");
		}
		return new BookDto(book.getId(), convertToDto(book.getStatus()), convertToDto(book.getBookInfo()));
	}
	
	private AlbumInfoDto convertToDto(AlbumInfo albumInfo) {
		if (albumInfo == null) {
			throw new IllegalArgumentException("There is no such albumInfo.");
		}
		return new AlbumInfoDto(albumInfo.getId(),albumInfo.getTitle(),albumInfo.getComposerPerformer(),albumInfo.getGenre());
	}
	
	private ItemStatusDto convertToDto (ItemStatus itemStatus) {
		switch (itemStatus) {
		case Available:
			return ItemStatusDto.Available;
		case CheckedOut:
			return ItemStatusDto.CheckedOut;
		default:
			return ItemStatusDto.Reserved;
		}
	}
}
