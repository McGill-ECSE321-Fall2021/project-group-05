package ca.mcgill.ecse321.onlinelibrary.service;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.onlinelibrary.dao.AlbumRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.ArchiveRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.BookRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.MovieRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.NewspaperRepository;
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
import ca.mcgill.ecse321.onlinelibrary.model.ReservableItem.ItemStatus;

@Service
public class LibraryItemService {

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private AlbumRepository albumRepository;
	
	@Autowired
	private NewspaperRepository newspaperRepository;
	
	@Autowired
	private ArchiveRepository archiveRepository;

	@Transactional
	public Book createBook(BookInfo bookInfo) {
		ArrayList<String> errorMessage = new ArrayList<String>();
		int errorCount = 0;
		if (bookInfo == null) {
			errorMessage.add("BookInfo can't be empty.");
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
	
	@Transactional 
	public Movie createMovie(MovieInfo movieInfo) {
		ArrayList<String> errorMessage = new ArrayList<String>();
		int errorCount = 0;
		if (movieInfo == null) {
			errorMessage.add("MovieInfo can't be empty.");
			errorCount++;
		}
		
		if (errorCount > 0) {
			throw new IllegalArgumentException(String.join(" ", errorMessage));
		}
		Movie movie = new Movie (movieInfo);
		movie.setStatus(ItemStatus.Available);
		movieRepository.save(movie);
		return movie;
	}
	
	@Transactional
	public Album createAlbum(AlbumInfo albumInfo) {
		ArrayList<String> errorMessage = new ArrayList<String>();
		int errorCount = 0;
		if (albumInfo == null) {
			errorMessage.add("AlbumInfo can't be empty.");
			errorCount++;
		}
		
		if (errorCount > 0) {
			throw new IllegalArgumentException(String.join(" ", errorMessage));
		}
		Album album= new Album (albumInfo);
		album.setStatus(ItemStatus.Available);
		albumRepository.save(album);
		return album;
	}
	
	@Transactional
	public Newspaper createNewspaper(NewsPaperInfo newspaperInfo) {
		ArrayList<String> errorMessage = new ArrayList<String>();
		int errorCount = 0;
		if (newspaperInfo == null) {
			errorMessage.add("NewspaperInfo can't be empty.");
			errorCount++;
		}
		
		if (errorCount > 0) {
			throw new IllegalArgumentException(String.join(" ", errorMessage));
		}
		Newspaper newspaper = new Newspaper (newspaperInfo);
		newspaperRepository.save(newspaper);
		return newspaper;
	}
	
	@Transactional
	public Archive createArchive(ArchiveInfo archiveInfo) {
		ArrayList<String> errorMessage = new ArrayList<String>();
		int errorCount = 0;
		if (archiveInfo == null) {
			errorMessage.add("archiveInfo can't be empty.");
			errorCount++;
		}
		
		if (errorCount > 0) {
			throw new IllegalArgumentException(String.join(" ", errorMessage));
		}
		Archive archive= new Archive (archiveInfo);
		archiveRepository.save(archive);
		return archive;
	}
}
