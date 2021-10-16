package ca.mcgill.ecse321.onlinelibrary.dao;

import ca.mcgill.ecse321.onlinelibrary.model.Book;
import ca.mcgill.ecse321.onlinelibrary.model.Loan;
import ca.mcgill.ecse321.onlinelibrary.model.ReservableItem.ItemStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;

import ca.mcgill.ecse321.onlinelibrary.model.Album;
import ca.mcgill.ecse321.onlinelibrary.model.Movie;
import ca.mcgill.ecse321.onlinelibrary.model.Archive;
import ca.mcgill.ecse321.onlinelibrary.model.Newspaper;
import ca.mcgill.ecse321.onlinelibrary.model.ReservableItem;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestOnlineLibraryPersistence {

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private MovieRepository movieRepository;
	@Autowired

	private AlbumRepository albumRepository;
	@Autowired
	private ArchiveRepository archiveRepository;
	@Autowired
	private NewspaperRepository newspaperRepository;
	@Autowired
	private LoanRepository loanRepository;
	
	@AfterEach
	public void clearDatabase() {
		loanRepository.deleteAll();
		bookRepository.deleteAll();
		movieRepository.deleteAll();
		albumRepository.deleteAll();
		archiveRepository.deleteAll();
		newspaperRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadBook() {
		Book book = new Book();
		book.setStatus(ItemStatus.CheckedOut);
		bookRepository.save(book);
		int id = book.getId();
		book = null;
		book = bookRepository.findBookById(id);
		assertEquals(id, book.getId());
		assertEquals(ItemStatus.CheckedOut, book.getStatus());
	}

	@Test
	public void testPersistAndLoadMovie() {
		Movie movie = new Movie();
		movie.setStatus(ItemStatus.Available);
		movieRepository.save(movie);
		int id = movie.getId();
		movie = null;
		movie = movieRepository.findMovieById(id);
		assertEquals(id, movie.getId());
		assertEquals(ItemStatus.Available, movie.getStatus());
	}

	@Test
	public void testPersistAndLoadAlbum() {
		Album album = new Album();
		album.setStatus(ItemStatus.Reserved);
		albumRepository.save(album);
		int id = album.getId();
		album = null;
		album = albumRepository.findAlbumById(id);
		assertEquals(id, album.getId());
		assertEquals(ItemStatus.Reserved, album.getStatus());
	}

	@Test
	public void testPersistAndLoadArchive() {
		Archive archive = new Archive();
		archiveRepository.save(archive);
		int id = archive.getId();
		archive = null;
		archive = archiveRepository.findArchiveById(id);
		assertNotNull(archive);
		assertEquals(id, archive.getId());
	}

	@Test
	public void testPersistAndLoadNewsPaper() {
		Newspaper newspaper = new Newspaper();
		newspaperRepository.save(newspaper);
		int id = newspaper.getId();
		newspaper = null;
		newspaper = newspaperRepository.findNewspaperById(id);
		assertNotNull(newspaper);
		assertEquals(id, newspaper.getId());
	}
	
	@Test
	public void testPersistAndLoadLoan() {
		
		ReservableItem reservableItem = new Book();
		Loan loan = new Loan();
		loan.setReservableItem(reservableItem);
		long dateMS = 11;
		Date date = new Date(dateMS);
		loan.setReturnDate(date);
		int numberOfRenewals = 2;
		loan.setNumberOfRenewals(numberOfRenewals);
		loanRepository.save(loan);
		int id = loan.getId();
		loan = null;
		loan = loanRepository.findLoanById(id);
		assertNotNull(loan);
		assertEquals(id, loan.getId());
		assertEquals(reservableItem.getId(), loan.getReservableItem().getId());
		assertEquals(numberOfRenewals, loan.getNumberOfRenewals());
		assertEquals(date.toString(), loan.getReturnDate().toString());
		
	}
	
	
}