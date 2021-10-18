package ca.mcgill.ecse321.onlinelibrary.dao;

import ca.mcgill.ecse321.onlinelibrary.model.Book;
import ca.mcgill.ecse321.onlinelibrary.model.BookInfo;
import ca.mcgill.ecse321.onlinelibrary.model.*;
import ca.mcgill.ecse321.onlinelibrary.model.ReservableItem.ItemStatus;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

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

import ca.mcgill.ecse321.onlinelibrary.model.Album;
import ca.mcgill.ecse321.onlinelibrary.model.AlbumInfo;
import ca.mcgill.ecse321.onlinelibrary.model.Movie;

import ca.mcgill.ecse321.onlinelibrary.model.MovieInfo;
import ca.mcgill.ecse321.onlinelibrary.model.NewsPaperInfo;
import ca.mcgill.ecse321.onlinelibrary.model.Archive;
import ca.mcgill.ecse321.onlinelibrary.model.ArchiveInfo;
import ca.mcgill.ecse321.onlinelibrary.model.Newspaper;
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
	private BookInfoRepository bookinfoRepository;
	@Autowired
	private MovieInfoRepository movieInfoRepository;
	@Autowired
	private AlbumInfoRepository albumInfoRepository;
	@Autowired
	private NewsPaperInfoRepository newsPaperInfoRepository;
	@Autowired
	private ArchiveInfoRepository archiveInfoRepository;
	@Autowired
	private ReservableItemInfoRepository reservableItemInfoRepository;
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
		onlineAccountRepository.deleteAll();
		roomBookingRepository.deleteAll();
		memberRepository.deleteAll();
		bookRepository.deleteAll();
		movieRepository.deleteAll();
		albumRepository.deleteAll();
		archiveRepository.deleteAll();
		newspaperRepository.deleteAll();
		bookinfoRepository.deleteAll();
		movieInfoRepository.deleteAll();
		albumInfoRepository.deleteAll();
		newsPaperInfoRepository.deleteAll();
		archiveInfoRepository.deleteAll();
		libraryOpeningHoursRepository.deleteAll();
		holidayRepository.deleteAll();
		librarianRepository.deleteAll();
		reservableItemInfoRepository.deleteAll();
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
	public void testPersistBookInfo(){
		BookInfo bookinfo = new BookInfo();
		String title = "How to code in Java";
		bookinfo.setTitle(title);
		bookinfoRepository.save(bookinfo);
		int id = bookinfo.getId();
		bookinfo = null;
		bookinfo = bookinfoRepository.findBookInfoById(id);
		assertNotNull(bookinfo);
		assertEquals(id, bookinfo.getId());
		assertEquals(title, bookinfo.getTitle());
	}

	@Test
	public void testPersistMovieInfo(){
		MovieInfo movieInfo = new MovieInfo();
		String director = "Kiro";
		movieInfo.setDirector(director);
		movieInfoRepository.save(movieInfo);
		int id = movieInfo.getId();
		movieInfo = null;
		movieInfo = movieInfoRepository.findMovieInfoById(id);
		assertNotNull(movieInfo);
		assertEquals(id, movieInfo.getId());
		assertEquals(director, movieInfo.getDirector());
	}

	@Test
	public void testPersistAlbumInfo(){
		AlbumInfo albumInfo = new AlbumInfo();
		String title = "Studying music";
		albumInfo.setTitle(title);
		albumInfoRepository.save(albumInfo);
		int id = albumInfo.getId();
		albumInfo = null;
		albumInfo = albumInfoRepository.findAlbumInfoById(id);
		assertNotNull(albumInfo);
		assertEquals(id, albumInfo.getId());
		assertEquals(title, albumInfo.getTitle());
	}

	@Test
	public void testPersistNewsPaperInfo(){
		NewsPaperInfo newsPaperInfo = new NewsPaperInfo();
		int number = 5673;
		newsPaperInfo.setNumber(number);
		newsPaperInfoRepository.save(newsPaperInfo);
		int id = newsPaperInfo.getId();
		newsPaperInfo = null;
		newsPaperInfo = newsPaperInfoRepository.findNewsPaperInfoById(id);
		assertNotNull(newsPaperInfo);
		assertEquals(id, newsPaperInfo.getId());
		assertEquals(number, newsPaperInfo.getNumber());
	}

	@Test
	public void testPersistArchiveInfo(){
		ArchiveInfo archiveInfo = new ArchiveInfo();
		String title = "History of Java";
		archiveInfo.setTitle(title);
		archiveInfoRepository.save(archiveInfo);
		int id = archiveInfo.getId();
		archiveInfo = null;
		archiveInfo = archiveInfoRepository.findArchiveInfoById(id);
		assertNotNull(archiveInfo);
		assertEquals(id, archiveInfo.getId());
		assertEquals(title, archiveInfo.getTitle());
	}

	@Test
	public void testReservationReferentialIntegrity(){
		Member member = new Member("Lala Land", "Marcos Polo");
		Member member2 = new Member("Mimi Land", "Samourai");
		ReservableItemInfo reservableItemInfo = new MovieInfo();

		reservableItemInfo.addMember(member);
		reservableItemInfo.addMember(member2);
		List<ReservableItemInfo> expectedReservedList = member.getReservedItems();
		List<Member> expectedMemberList = reservableItemInfo.getMembers();
		member.addReservation(reservableItemInfo);	//Shouldn't do anything
		member2.addReservation(reservableItemInfo);
		List<ReservableItemInfo> actualReservedList = member.getReservedItems();
		List<Member> actualMemberList = reservableItemInfo.getMembers();

		assertEquals(expectedReservedList, actualReservedList);
		assertEquals(expectedMemberList, actualMemberList);
	}

	@Test
	@Transactional
	public void testReservationPersitence(){
		String memberAdress = "Lala Land";
		String memberName = "Marcos Polo";
		Member member = new Member(memberAdress, memberName);
		String member2Adress = "Happy Land";
		String member2Name = "Greg";
		Member member2 = new Member(member2Adress, member2Name);
		memberRepository.save(member);
		memberRepository.save(member2);
		MovieInfo movieInfo = new MovieInfo();
		String director = "Seb";
		movieInfo.setDirector(director);
		movieInfo.addMember(member);
		movieInfo.addMember(member2);
		movieInfoRepository.save(movieInfo);

		List<Member> reservations = movieInfo.getMembers();
		int id = movieInfo.getId();

		movieInfo = null;
		member = null;
		member2 = null;

		MovieInfo mInfo = movieInfoRepository.findMovieInfoById(id);

		assertNotNull(mInfo);
		assertEquals(director, mInfo.getDirector());
		assertEquals(reservations, mInfo.getMembers());

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
