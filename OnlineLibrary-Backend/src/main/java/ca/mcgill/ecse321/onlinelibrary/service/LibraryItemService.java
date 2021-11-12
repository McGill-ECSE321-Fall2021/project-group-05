package ca.mcgill.ecse321.onlinelibrary.service;

import ca.mcgill.ecse321.onlinelibrary.dao.*;
import ca.mcgill.ecse321.onlinelibrary.model.*;
import ca.mcgill.ecse321.onlinelibrary.model.ReservableItem.ItemStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryItemService {

	// TODO: Get this value from application.properties or similar
	public static final int MAX_LOANS_PER_MEMBER = 5;
	// TODO: Get this value from application.properties or associated ReservableItemInfo
	public static final int NUMBER_OF_DAYS_BEFORE_RENEWAL = 15;

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
		ReservableItem reservableItem = reservableItemRepository.findReservableItemById(id);
		if (reservableItem == null) {
			throw new IllegalArgumentException("The reservable item with id " + id + " does not exist.");
		}
		return reservableItem;
	}

	@Transactional
	public LibraryItemInfo getAssociatedItemInfo(LibraryItem libraryItem) {
		if (libraryItem == null) {
			throw new IllegalArgumentException("Library item cannot be null.");
		}
		return libraryItem.getItemInfo();
	}

	@Transactional
	public List<LibraryItem> getAssociatedCopies(LibraryItemInfo libraryItemInfo) {
        List<LibraryItem> result = new ArrayList<>();
		for (LibraryItem libraryItem : libraryItemRepository.findAll()) {
			if (getAssociatedItemInfo(libraryItem).equals(libraryItemInfo)) {
				result.add(libraryItem);
			}
		}
		return result;
	}

	@Transactional
	public Loan createLoan(ReservableItem reservableItem, Member member) {
		ArrayList<String> errorMessages = new ArrayList<>();
		if (reservableItem == null) {
			errorMessages.add("Reservable item cannot be null.");
		}
		if (member == null) {
			errorMessages.add("Member cannot be null.");
		}
		// Throw errors here first if any of the arguments are null
		if (!errorMessages.isEmpty()) {
			throw new IllegalArgumentException(String.join(" ", errorMessages));
		}

		if (member.getLoans().size() >= MAX_LOANS_PER_MEMBER) {
			errorMessages.add("Member cannot have more than 5 loans.");
		}
		if (reservableItem.getLoan() != null) {
			errorMessages.add("Item is already loaned.");
		}
		if (member.getStatus() == Member.MemberStatus.BLACKLISTED || member.getStatus() == Member.MemberStatus.INACTIVE) {
			errorMessages.add("Member account is inactive or blacklisted.");
		}

		// Get associated item info
		ReservableItemInfo itemInfo = (ReservableItemInfo) getAssociatedItemInfo(reservableItem);
		// Get reservations for associated item info
		List<Reservation> reservations = reservationRepository.findReservationByReservedItemOrderByDateAsc(itemInfo);
		// Does the member have a reservation for the item info?
		Optional<Reservation> reservation = reservations.stream().filter(r -> r.getMember().equals(member)).findFirst();
		// How many reservations are there for the item info?
		int numberOfReservationsForThisItem = reservations.size();
		// How many copies are available for the item info?
		int numberOfAvailableCopiesForThisItem = (int) getAssociatedCopies(itemInfo).stream().filter((copy) -> (((ReservableItem) copy).getLoan() == null)).count();
		// Can the user borrow this item using their reservation?
		boolean hasReservation = reservation.isPresent() && reservations.indexOf(reservation.get()) < numberOfAvailableCopiesForThisItem;
		// User cannot borrow the item.
		if (!hasReservation && numberOfAvailableCopiesForThisItem <= numberOfReservationsForThisItem) {
			errorMessages.add("Not enough copies available.");
		}
		if (!errorMessages.isEmpty()) {
			throw new IllegalArgumentException(String.join(" ", errorMessages));
		}

		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.add(Calendar.DATE, NUMBER_OF_DAYS_BEFORE_RENEWAL);
		Date date = new Date(today.getTimeInMillis());

		Loan loan = new Loan(date, reservableItem, member);
		loanRepository.save(loan);
		if (hasReservation) {
			reservationRepository.delete(reservation.get());
		}
		return loan;
	}

	@Transactional
	public void returnItem(Loan loan) {
        if (loan == null) {
            throw new IllegalArgumentException("Loan cannot be null.");
        }
        loanRepository.delete(loan);
    }
}
