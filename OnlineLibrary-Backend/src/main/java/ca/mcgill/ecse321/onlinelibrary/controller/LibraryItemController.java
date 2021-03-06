package ca.mcgill.ecse321.onlinelibrary.controller;

import ca.mcgill.ecse321.onlinelibrary.dto.*;
import ca.mcgill.ecse321.onlinelibrary.model.*;
import ca.mcgill.ecse321.onlinelibrary.service.LibraryItemInfoService;
import ca.mcgill.ecse321.onlinelibrary.service.LibraryItemService;
import ca.mcgill.ecse321.onlinelibrary.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class LibraryItemController {

	@Autowired
	private LibraryItemInfoService libraryItemInfoService;
	@Autowired
	private LibraryItemService libraryItemService;
	@Autowired
	private MemberService memberService;

	@PostMapping(value = { "/libraryItem/{libraryItemInfoId}", "/libraryItem/{libraryItemInfoId}/" })
	public LibraryItemDto createLibraryItem(@PathVariable int libraryItemInfoId) {
		LibraryItemInfo libraryItemInfo = libraryItemInfoService.getLibraryItemInfoById(libraryItemInfoId);
		if (libraryItemInfo instanceof BookInfo) {
			return libraryItemService.createBook((BookInfo) libraryItemInfo).convertToDto();
		} else if (libraryItemInfo instanceof AlbumInfo) {
			return libraryItemService.createAlbum((AlbumInfo) libraryItemInfo).convertToDto();
		} else if (libraryItemInfo instanceof MovieInfo) {
			return libraryItemService.createMovie((MovieInfo) libraryItemInfo).convertToDto();
		} else if (libraryItemInfo instanceof ArchiveInfo) {
			return libraryItemService.createArchive((ArchiveInfo) libraryItemInfo).convertToDto();
		} else if (libraryItemInfo instanceof NewspaperInfo) {
			return libraryItemService.createNewspaper((NewspaperInfo) libraryItemInfo).convertToDto();
		} else {
			throw new IllegalArgumentException("Library item has an unknown type");
		}
	}

	@PostMapping(value = {"/book/{bookInfoId}", "/book/{bookInfoId}/"})
	public BookDto createBookDto(@PathVariable("bookInfoId") int bookInfoId) throws IllegalArgumentException {
		BookInfo bookInfo = libraryItemInfoService.getBookInfo(bookInfoId);
		Book book = libraryItemService.createBook(bookInfo);
		return BookDto.fromBook(book);
	}

	@DeleteMapping(value = { "/book/{bookId}", "book/{bookId}/"})
	public void deleteBook(@PathVariable("bookId") int bookId) throws IllegalArgumentException {
		libraryItemService.deleteBook(bookId);
	}

	@PostMapping(value = {"/movie/{movieInfoId}", "/movie/{movieInfoId}/" })
	public MovieDto createMovieDto(@PathVariable("movieInfoId") int movieInfoId) throws IllegalArgumentException {
		MovieInfo movieInfo = libraryItemInfoService.getMovieInfo(movieInfoId);
		Movie movie = libraryItemService.createMovie(movieInfo);
		return MovieDto.fromMovie(movie);
	}

	@DeleteMapping(value = {"/movie/{movieId}", "/movie/{movieId/}"})
	public void deleteMovie(@PathVariable("movieId") int movieId) throws IllegalArgumentException {
		libraryItemService.deleteMovie(movieId);
	}

	@PostMapping(value = {"/album/{albumInfoId}", "/album/{albumInfoId}/"})
	public AlbumDto createAlbumDto(@PathVariable("albumInfoId") int albumInfoId) throws IllegalArgumentException {
		AlbumInfo albumInfo = libraryItemInfoService.getAlbumInfo(albumInfoId);
		Album album = libraryItemService.createAlbum(albumInfo);
		return AlbumDto.fromAlbum(album);
	}

	@DeleteMapping(value = {"/album/{albumId}", "/album/{albumId}/"})
	public void deleteAlbum(@PathVariable("albumId") int albumId) throws IllegalArgumentException {
		libraryItemService.deleteAlbum(albumId);
	}

	@PostMapping(value = {"/newspaper/{newspaperInfoId}", "/newspaper/{newspaperInfoId}/"})
	public NewspaperDto createNewspaperDto(@PathVariable("newspaperInfoId") int newspaperInfoId) throws IllegalArgumentException {
		NewspaperInfo newspaperInfo = libraryItemInfoService.getNewspaperInfo(newspaperInfoId);
		Newspaper newspaper = libraryItemService.createNewspaper(newspaperInfo);
		return NewspaperDto.fromNewspaper(newspaper);
	}

	@DeleteMapping(value = {"/newspaper/{newspaperId}", "/newspaper/{newspaperId}/"})
	public void deleteNewspaper(@PathVariable("newspaperId") int newspaperId) throws IllegalArgumentException {
		libraryItemService.deleteNewspaper(newspaperId);
	}

	@PostMapping(value = {"/archive/{archiveInfoId}", "archive/{archiveInfoId}/"})
	public ArchiveDto createArchiveDto(@PathVariable("archiveInfoId") int archiveInfoId) throws IllegalArgumentException {
		ArchiveInfo archiveInfo = libraryItemInfoService.getArchiveInfo(archiveInfoId);
		Archive archive = libraryItemService.createArchive(archiveInfo);
		return ArchiveDto.fromArchive(archive);
	}

	@DeleteMapping(value = {"/archive/{archiveId}", "/archive/{archiveId}/"})
	public void deleteArchive(@PathVariable("archiveId") int archiveId) throws IllegalArgumentException {
		libraryItemService.deleteArchive(archiveId);
	}

	@PostMapping(value = {"/reservableItem/{reservableItemId}/loan", "/reservableItem/{reservableItemId}/loan/"})
	public LoanDto createLoan(@PathVariable("reservableItemId") int reservableItemId, @RequestParam int memberId) {
		Loan loan = libraryItemService.createLoan(libraryItemService.getReservableItemById(reservableItemId), memberService.getMemberById(memberId));
		return LoanDto.fromLoan(loan);
	}

	@DeleteMapping(value = {"/reservableItem/{reservableItemId}/loan", "/reservableItem/{reservableItemId}/loan/"})
	public void returnItem(@PathVariable("reservableItemId") int reservableItemId) {
		ReservableItem item = libraryItemService.getReservableItemById(reservableItemId);
		Loan loan = libraryItemService.getLoanByReservableItem(item);
		libraryItemService.returnItem(loan);
	}

	@GetMapping(value = {"/reservableItem/{reservableItemId}", "/reservableItem/{reservableItemId}/"})
	public ReservableItemDto getReservableItemById(@PathVariable("reservableItemId") int reservableItemId) {
		return (ReservableItemDto) libraryItemService.getReservableItemById(reservableItemId).convertToDto();
	}

	@GetMapping(value = { "/reservableItem/{reservableItemId}/itemInfo",
	"/reservableItem/{reservableItemId}/itemInfo/" })
	public ReservableItemInfoDto getAssociatedItemInfo(@PathVariable("reservableItemId") int reservableItemId) {
		return (ReservableItemInfoDto) libraryItemService
				.getAssociatedItemInfo(libraryItemService.getReservableItemById(reservableItemId)).convertToDto();
	}

	@GetMapping(value = {"/reservableItem/{reservableItemId}/loan", "/reservableItem/{reservableItemId}/loan/"})
	public LoanDto getLoanFromReservableItemId(@PathVariable("reservableItemId") int reservableItemId) {
		ReservableItem item = libraryItemService.getReservableItemById(reservableItemId);
		Loan loan = libraryItemService.getLoanByReservableItem(item);
		
		if (loan == null) {
			return null;
		}

		return LoanDto.fromLoan(loan);
	}
}
