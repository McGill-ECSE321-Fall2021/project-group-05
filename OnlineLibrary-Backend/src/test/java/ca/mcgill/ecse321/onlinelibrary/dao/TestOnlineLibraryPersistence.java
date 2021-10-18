package ca.mcgill.ecse321.onlinelibrary.dao;

import ca.mcgill.ecse321.onlinelibrary.model.*;
import ca.mcgill.ecse321.onlinelibrary.model.Member.MemberStatus;
import ca.mcgill.ecse321.onlinelibrary.model.ReservableItem.ItemStatus;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import javax.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
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
	private MemberRepository memberRepository;
	@Autowired
	private OnlineAccountRepository onlineAccountRepository;
	@Autowired
	private LoanRepository loanRepository;
	@Autowired
	private LibraryOpeningHoursRepository libraryOpeningHoursRepository;
	@Autowired
	private HolidayRepository holidayRepository;
	@Autowired
	private LibrarianRepository librarianRepository;
	@Autowired
	private LibrarianShiftRepository librarianShiftRepository;
	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private RoomBookingRepository roomBookingRepository;

	@AfterEach
	public void clearDatabase() {
		loanRepository.deleteAll();
		roomBookingRepository.deleteAll();
		memberRepository.deleteAll();
		bookRepository.deleteAll();
		movieRepository.deleteAll();
		albumRepository.deleteAll();
		archiveRepository.deleteAll();
		newspaperRepository.deleteAll();
		libraryOpeningHoursRepository.deleteAll();
		holidayRepository.deleteAll();
		librarianRepository.deleteAll();
		roomRepository.deleteAll();
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
		Member member = new Member("123 McGill Street", "Luke Skywalker");
		memberRepository.save(member);
		Book reservableItem = new Book();
		bookRepository.save(reservableItem);
		Date date = Date.valueOf(LocalDate.of(2021, 10, 16));
		Loan loan = new Loan(date, reservableItem, member);
		int numberOfRenewals = 2;
		loan.setNumberOfRenewals(numberOfRenewals);
		reservableItem.setLoan(loan);
		member.addLoan(loan);
		loanRepository.save(loan);

		int loanId = loan.getId();
		int bookId = reservableItem.getId();
		int memberId = member.getId();
		loan = null;
		member = null;
		reservableItem = null;

		loan = loanRepository.findLoanById(loanId);
		assertNotNull(loan);
		assertEquals(loanId, loan.getId());
		assertEquals(numberOfRenewals, loan.getNumberOfRenewals());
		assertEquals(date.toString(), loan.getReturnDate().toString());

		// Check associations
		ReservableItem retrievedItem = loan.getReservableItem();
		assertNotNull(retrievedItem);
		assertEquals(bookId, retrievedItem.getId());

		Member retrievedMember = loan.getMember();
		assertNotNull(retrievedMember);
		assertEquals(memberId, retrievedMember.getId());
	}

	public void testPersistAndLoadLibraryOpeningHours() {
		// Create opening hours
		Date date = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
		Time startTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
		Time endTime = java.sql.Time.valueOf(LocalTime.of(13, 25));

		LibraryOpeningHours libraryOpeningHours = new LibraryOpeningHours();

		libraryOpeningHours.setDate(date);
		libraryOpeningHours.setStartTime(startTime);
		libraryOpeningHours.setEndTime(endTime);

		// Persist opening hours
		libraryOpeningHoursRepository.save(libraryOpeningHours);
		int id = libraryOpeningHours.getId();

		// Forget & retrieve opening hours
		libraryOpeningHours = null;
		libraryOpeningHours = libraryOpeningHoursRepository.findLibraryOpeningHoursById(id);

		// Check atributes
		assertNotNull(libraryOpeningHours);
		assertEquals(id, libraryOpeningHours.getId());
		assertEquals(date, libraryOpeningHours.getDate());
		assertEquals(startTime, libraryOpeningHours.getStartTime());
		assertEquals(endTime, libraryOpeningHours.getEndTime());
	}

	@Test
	public void testPersistAndLoadHoliday() {
		// Create Holiday
		Date startDate = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 30));
		Date endDate = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));

		Holiday holiday = new Holiday();

		holiday.setStartDate(startDate);
		holiday.setEndDate(endDate);

		// Persist Holiday
		holidayRepository.save(holiday);
		int id = holiday.getId();

		// Forget & retrieve Holiday
		holiday = null;
		holiday = holidayRepository.findHolidayById(id);

		// Check attributes
		assertNotNull(holiday);
		assertEquals(id, holiday.getId());
		assertEquals(startDate, holiday.getStartDate());
		assertEquals(endDate, holiday.getEndDate());
	}

	@Transactional
	@Test
	public void testPersistAndLoadMember() {
		// Create and persist member with online account and 2 loans
		Member originalMember = new Member("212 McGill Street", "Obi-Wan Kenobi");
		originalMember.applyStatusPenalty();
		originalMember.setTotalFee(212);
		OnlineAccount originalAccount = new OnlineAccount("212", "obi1kenobi", "obi-wan.kenobi@mail.mcgill.ca",
				originalMember);
		originalMember.setOnlineAccount(originalAccount);
		originalMember = memberRepository.save(originalMember);
		Book book = new Book();
		book.setStatus(ItemStatus.CheckedOut);
		bookRepository.save(book);
		Movie movie = new Movie();
		movie.setStatus(ItemStatus.CheckedOut);
		movieRepository.save(movie);
		Loan originalBookLoan = new Loan(Date.valueOf("2022-10-20"), book, originalMember);
		originalMember.addLoan(originalBookLoan);
		loanRepository.save(originalBookLoan);
		Loan originalMovieLoan = new Loan(Date.valueOf("2022-10-20"), movie, originalMember);
		originalMember.addLoan(originalMovieLoan);
		loanRepository.save(originalMovieLoan);

		// Get ID and drop references
		int memberId = originalMember.getId();
		int accountId = originalAccount.getId();
		int bookLoanId = originalBookLoan.getId();
		int movieLoanId = originalMovieLoan.getId();
		originalMember = null;
		originalAccount = null;
		originalBookLoan = null;
		originalMovieLoan = null;

		Member retrievedMember = memberRepository.findMemberById(memberId);
		assertNotNull(retrievedMember);

		// Check attributes
		assertEquals("212 McGill Street", retrievedMember.getAddress());
		assertEquals("Obi-Wan Kenobi", retrievedMember.getFullName());
		assertEquals(MemberStatus.YELLOW, retrievedMember.getStatus());
		assertEquals(212, retrievedMember.getTotalFee());

		// Check associations
		OnlineAccount retrievedAccount = retrievedMember.getOnlineAccount();
		assertNotNull(retrievedAccount);
		assertEquals(accountId, retrievedAccount.getId());

		assertEquals(2, retrievedMember.getLoans().size());
		boolean bookLoanFound = false;
		boolean movieLoanFound = false;
		for (Loan l : retrievedMember.getLoans()) {
			if (bookLoanId == l.getId())
				bookLoanFound = true;
			else if (movieLoanId == l.getId())
				movieLoanFound = true;
		}
		assertTrue(bookLoanFound);
		assertTrue(movieLoanFound);
	}

	@Test
	public void testPersistAndLoadOnlineAccount() {
		// Create and persist member with online account
		Member originalMember = new Member("501 McGill Street", "Anakin Skywalker");
		OnlineAccount originalAccount = new OnlineAccount("501", "chosen1", "anakin.skywalker@mail.mcgill.ca",
				originalMember);
		originalMember.setOnlineAccount(originalAccount);
		originalMember = memberRepository.save(originalMember);

		// Get ID and drop reference
		int memberId = originalMember.getId();
		int accountId = originalAccount.getId();
		originalAccount = null;

		OnlineAccount retrievedAccount = onlineAccountRepository.findOnlineAccountById(accountId);
		assertNotNull(retrievedAccount);

		// Check attributes
		assertEquals("501", retrievedAccount.getPasswordHash());
		assertEquals("chosen1", retrievedAccount.getUsername());
		assertEquals("anakin.skywalker@mail.mcgill.ca", retrievedAccount.getEmailAddress());

		// Check association
		Member retrievedMember = retrievedAccount.getAccountOwner();
		assertNotNull(retrievedMember);
		assertEquals(memberId, retrievedMember.getId());
	}

	@Test
	@Transactional
	public void testPersistAndLoadLibrarian() {
		// Create and persist librarian with shift
		Librarian originalLibrarian = new Librarian("Jocasta Nu", "jocasta-nu", "12345", true);
		LibrarianShift originalShift = new LibrarianShift(Date.valueOf("2022-10-16"), Time.valueOf("9:00:00"),
				Time.valueOf("17:00:00"), originalLibrarian);
		originalLibrarian = librarianRepository.save(originalLibrarian);

		// Get ID and drop reference
		int librarianId = originalLibrarian.getId();
		int shiftId = originalShift.getId();
		originalShift = null;

		Librarian newLibrarian = librarianRepository.findLibrarianById(librarianId);

		// Check attributes
		assertNotNull(newLibrarian);
		assertEquals("Jocasta Nu", newLibrarian.getFullName());
		assertEquals("jocasta-nu", newLibrarian.getUsername());
		assertEquals("12345", newLibrarian.getPasswordHash());
		assertTrue(newLibrarian.isHead());

		// Check association
		assertEquals(1, newLibrarian.getShifts().size());
		LibrarianShift newShift = newLibrarian.getShifts().get(0);
		assertNotNull(newShift);
		assertEquals(shiftId, newShift.getId());
	}

	@Test
	public void testPersistAndLoadLibrarianShift() {
		// Create and persist librarian with shift
		Librarian originalLibrarian = new Librarian("Jocasta Nu", "jocasta-nu", "12345", true);
		LibrarianShift originalShift = new LibrarianShift(Date.valueOf("2022-10-16"), Time.valueOf("9:00:00"),
				Time.valueOf("17:00:00"), originalLibrarian);
		originalLibrarian = librarianRepository.save(originalLibrarian);

		// Get ID and drop reference
		int librarianId = originalLibrarian.getId();
		int shiftId = originalShift.getId();
		originalShift = null;

		LibrarianShift newShift = librarianShiftRepository.findLibrarianShiftById(shiftId);
		assertNotNull(newShift);

		// Check attributes
		assertEquals(Date.valueOf("2022-10-16"), newShift.getDate());
		assertEquals(Time.valueOf("9:00:00"), newShift.getStartTime());
		assertEquals(Time.valueOf("17:00:00"), newShift.getEndTime());

		// Check association
		Librarian newLibrarian = newShift.getLibrarian();
		assertNotNull(newLibrarian);
		assertEquals(librarianId, newLibrarian.getId());
	}

	@Test
	public void testPersistAndLoadRoom() {
		// Create Room
		int capacity = 0;
		String name = "room1";
		Room room = new Room();
		room.setCapacity(capacity);
		room.setName(name);

		// Persiste Room
		roomRepository.save(room);
		int id = room.getId();

		// Forget & Retrieve Room
		room = null;
		room = roomRepository.findRoomById(id);

		// Check attributes
		assertNotNull(room);
		assertEquals(id, room.getId());
		assertEquals(capacity, room.getCapacity());
		assertEquals(name, room.getName());
	}

	@Test
	public void testPersistAndLoadRoomBooking() {
		// Create Member
		Member originalMember = new Member("212 McGill Street", "Obi-Wan Kenobi");
		OnlineAccount originalAccount = new OnlineAccount("212", "obi1kenobi", "obi-wan.kenobi@mail.mcgill.ca",
				originalMember);
		originalMember.setOnlineAccount(originalAccount);
		originalMember = memberRepository.save(originalMember);
		Book book = new Book();
		book.setStatus(ItemStatus.CheckedOut);
		bookRepository.save(book);
		Movie movie = new Movie();
		movie.setStatus(ItemStatus.CheckedOut);
		movieRepository.save(movie);
		Loan originalBookLoan = new Loan(Date.valueOf("2022-10-20"), book, originalMember);
		originalMember.addLoan(originalBookLoan);
		loanRepository.save(originalBookLoan);
		Loan originalMovieLoan = new Loan(Date.valueOf("2022-10-20"), movie, originalMember);
		originalMember.addLoan(originalMovieLoan);
		loanRepository.save(originalMovieLoan);

		// Create room
		int capacity = 0;
		String name = "room1";
		Room room = new Room();
		room.setCapacity(capacity);
		room.setName(name);
		roomRepository.save(room);

		// Create room booking
		Date date = Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
		Time startTime = Time.valueOf(LocalTime.of(11, 35));
		Time endTime = Time.valueOf(LocalTime.of(13, 25));
		RoomBooking roomBooking = new RoomBooking();
		roomBooking.setDate(date);
		roomBooking.setStartTime(startTime);
		roomBooking.setEndTime(endTime);
		roomBooking.setRoom(room);
		roomBooking.setMember(originalMember);

		// Persist RoomBooking
		roomBookingRepository.save(roomBooking);
		int id = roomBooking.getId();

		// Forget & Retrieve RoomBooking
		roomBooking = null;
		roomBooking = roomBookingRepository.findRoomBookingById(id);

		// Check attributes
		assertNotNull(roomBooking);
		assertEquals(id, roomBooking.getId());
		assertEquals(startTime, roomBooking.getStartTime());
		assertEquals(endTime, roomBooking.getEndTime());

		// Check associations (* -> 1)
		assertEquals(room.getId(), roomBooking.getRoom().getId());
		assertEquals(originalMember.getId(), roomBooking.getMember().getId());
	}
}
