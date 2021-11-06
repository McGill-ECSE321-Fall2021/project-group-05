package ca.mcgill.ecse321.onlinelibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.onlinelibrary.dto.AlbumDto;
import ca.mcgill.ecse321.onlinelibrary.dto.ArchiveDto;
import ca.mcgill.ecse321.onlinelibrary.dto.BookDto;
import ca.mcgill.ecse321.onlinelibrary.dto.MovieDto;
import ca.mcgill.ecse321.onlinelibrary.dto.NewsPaperDto;
import ca.mcgill.ecse321.onlinelibrary.model.Album;
import ca.mcgill.ecse321.onlinelibrary.model.AlbumInfo;
import ca.mcgill.ecse321.onlinelibrary.model.Archive;
import ca.mcgill.ecse321.onlinelibrary.model.ArchiveInfo;
import ca.mcgill.ecse321.onlinelibrary.model.Book;
import ca.mcgill.ecse321.onlinelibrary.model.BookInfo;
import ca.mcgill.ecse321.onlinelibrary.model.Movie;
import ca.mcgill.ecse321.onlinelibrary.model.MovieInfo;
import ca.mcgill.ecse321.onlinelibrary.model.NewsPaperInfo;
import ca.mcgill.ecse321.onlinelibrary.model.Newspaper;
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
	public NewsPaperDto createNewspaperDto(@PathVariable("newspaperInfoId") int newspaperInfoId) throws IllegalArgumentException {
		NewsPaperInfo newspaperInfo = libraryItemInfoService.getNewspaperInfo(newspaperInfoId);
		Newspaper newspaper = libraryItemService.createNewspaper(newspaperInfo);
		return NewsPaperDto.fromNewsPaper(newspaper);
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
	
}
