package ca.mcgill.ecse321.onlinelibrary.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import ca.mcgill.ecse321.onlinelibrary.dao.*;
import ca.mcgill.ecse321.onlinelibrary.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private LibraryItemRepository libraryItemRepository;

	@Autowired
	private ReservableItemRepository reservableItemRepository;

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
	public void deleteBook(int id) {
		Book book = bookRepository.findBookById(id);
		if (book == null){
			throw new IllegalArgumentException("The book with id " +  id + " doesn't exist.");
		}
		if (book.getLoan() != null) {
			throw new IllegalArgumentException("This book is currently part of a loan, it can't be deleted.");
		} else {
			bookRepository.deleteById(id);
		}
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
	public void deleteMovie(int id) {
		Movie movie = movieRepository.findMovieById(id);
		if (movie == null){
			throw new IllegalArgumentException("The movie with id " +  id + " doesn't exist.");
		}
		if (movie.getLoan() != null) {
			throw new IllegalArgumentException("This movie is currently part of a loan, it can't be deleted.");
		} else {
			movieRepository.deleteById(id);
		}
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
	public void deleteAlbum(int id) {
		Album album = albumRepository.findAlbumById(id);
		if (album == null){
			throw new IllegalArgumentException("The album with id " +  id + " doesn't exist.");
		}
		if (album.getLoan() != null) {
			throw new IllegalArgumentException("This album is currently part of a loan, it can't be deleted.");
		} else {
			albumRepository.deleteById(id);
		}
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
	public void deleteNewspaper(int id) {
		Newspaper newspaper = newspaperRepository.findNewspaperById(id);
		if (newspaper == null){
			throw new IllegalArgumentException("The newspaper with id " +  id + " doesn't exist.");
		} else {
			newspaperRepository.deleteById(id);
		}
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
	
	@Transactional
	public void deleteArchive(int id) {
		Archive archive = archiveRepository.findArchiveById(id);
		if (archive == null){
			throw new IllegalArgumentException("The archive with id " +  id + " doesn't exist.");
		} else {
			archiveRepository.deleteById(id);
		}
	}

	@Transactional
	public ReservableItem getReservableItemById(int id) {
		return reservableItemRepository.findReservableItemById(id);
	}

	@Transactional
	public LibraryItemInfo getAssociatedItemInfo(LibraryItem libraryItem) {
		if (libraryItem instanceof Book) {
            return ((Book) libraryItem).getBookInfo();
        } else if (libraryItem instanceof Movie) {
            return ((Movie) libraryItem).getMovieInfo();
        } else if (libraryItem instanceof Album) {
            return ((Album) libraryItem).getAlbumInfo();
        } else if (libraryItem instanceof Newspaper) {
            return ((Newspaper) libraryItem).getNewsPaperInfo();
        } else if (libraryItem instanceof Archive) {
            return ((Archive) libraryItem).getArchiveInfo();
        } else {
            throw new IllegalArgumentException("Could not find concrete class for library item.");
        }
	}

	@Transactional
	public List<LibraryItem> getAssociatedCopies(LibraryItemInfo libraryItemInfoItemInfo) {
        List<LibraryItem> result = new ArrayList<>();
		for (LibraryItem libraryItem : libraryItemRepository.findAll()) {
			if (getAssociatedItemInfo(libraryItem).equals(libraryItemInfoItemInfo)) {
				result.add(libraryItem);
			}
		}
		return result;
	}


	@Transactional
	public List<Loan> getLoansByMember(Member member) {
		if (member == null) {
			throw new IllegalArgumentException("Member cannot be null.");
		}
		return member.getLoans();
	}

}
