package ca.mcgill.ecse321.onlinelibrary.dao;

import ca.mcgill.ecse321.onlinelibrary.model.Book;
import ca.mcgill.ecse321.onlinelibrary.model.Loan;
import ca.mcgill.ecse321.onlinelibrary.model.ReservableItem.ItemStatus;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.onlinelibrary.model.Album;
import ca.mcgill.ecse321.onlinelibrary.model.Movie;
import ca.mcgill.ecse321.onlinelibrary.model.Archive;
import ca.mcgill.ecse321.onlinelibrary.model.Newspaper;
import ca.mcgill.ecse321.onlinelibrary.model.ReservableItem;
import ca.mcgill.ecse321.onlinelibrary.model.LibraryOpeningHours;
import ca.mcgill.ecse321.onlinelibrary.model.Holiday;

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
	@Autowired
	private LibraryOpeningHoursRepository libraryOpeningHoursRepository;
	@Autowired
	private HolidayRepository holidayRepository;

	@AfterEach
	public void clearDatabase() {
		loanRepository.deleteAll();
		bookRepository.deleteAll();
		movieRepository.deleteAll();
		albumRepository.deleteAll();
		archiveRepository.deleteAll();
		newspaperRepository.deleteAll();
		libraryOpeningHoursRepository.deleteAll();
		holidayRepository.deleteAll();
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
		Date date = Date.valueOf(LocalDate.of(2021, 10, 16));
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

	public void testPersistAndLoadLibraryOpeningHours() {
		Date date = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
		Time startTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
		Time endTime = java.sql.Time.valueOf(LocalTime.of(13, 25));

		LibraryOpeningHours libraryOpeningHours = new LibraryOpeningHours();

		libraryOpeningHours.setDate(date);
		libraryOpeningHours.setStartTime(startTime);
		libraryOpeningHours.setEndTime(endTime);

		libraryOpeningHoursRepository.save(libraryOpeningHours);
		int id = libraryOpeningHours.getId();

		libraryOpeningHours = null;
		libraryOpeningHours = libraryOpeningHoursRepository.findLibraryOpeningHoursById(id);

		assertNotNull(libraryOpeningHours);
		assertEquals(id, libraryOpeningHours.getId());
		assertEquals(date, libraryOpeningHours.getDate());
		assertEquals(startTime, libraryOpeningHours.getStartTime());
		assertEquals(endTime, libraryOpeningHours.getEndTime());
	}

	@Test
	public void testPersistAndLoadHoliday() {
		Date startDate = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 30));
		Date endDate = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));

		Holiday holiday = new Holiday();

		holiday.setStartDate(startDate);
		holiday.setEndDate(endDate);

		holidayRepository.save(holiday);
		int id = holiday.getId();

		holiday = null;
		holiday = holidayRepository.findHolidayById(id);

		assertNotNull(holiday);
		assertEquals(id, holiday.getId());
		assertEquals(startDate, holiday.getStartDate());
		assertEquals(endDate, holiday.getEndDate());
	}
}
