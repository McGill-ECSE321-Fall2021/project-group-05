package ca.mcgill.ecse321.onlinelibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.onlinelibrary.dto.BookInfoDto;
import ca.mcgill.ecse321.onlinelibrary.model.BookInfo;
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
	
	@DeleteMapping(value = { "/bookInfo/{id}"})
	public void deleteBookInfo(@PathVariable("id") int id) throws IllegalArgumentException {
		service.deleteBookInfo(id);
	}
	
	private BookInfoDto convertToDto (BookInfo bookInfo) {
		if (bookInfo == null) {
			throw new IllegalArgumentException("There is no such bookInfo");
		}
		return new BookInfoDto(bookInfo.getTitle(),bookInfo.getNumberOfPage(),bookInfo.getAuthor(),bookInfo.getIsbn(), bookInfo.getId());
	}
	
	
}
