package ca.mcgill.ecse321.onlinelibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.onlinelibrary.dto.BookDto;
import ca.mcgill.ecse321.onlinelibrary.model.Book;
import ca.mcgill.ecse321.onlinelibrary.model.BookInfo;
import ca.mcgill.ecse321.onlinelibrary.service.LibraryItemInfoService;
import ca.mcgill.ecse321.onlinelibrary.service.LibraryItemService;

@CrossOrigin(origins = "*")
@RestController
public class LibraryItemController {

	@Autowired
	private LibraryItemInfoService libraryItemInfoService;
	@Autowired
	private LibraryItemService libraryItemService;

	@PostMapping(value = {"/book/{bookInfoId}", "/book/{bookInfoId}/"})
	public BookDto createBookDto(@PathVariable("bookInfoId") int bookInfoId) throws IllegalArgumentException {
		BookInfo bookInfo = libraryItemInfoService.getBookInfo(bookInfoId);
		Book book = libraryItemService.createBook(bookInfo);
		return BookDto.fromBook(book);
	}
}
