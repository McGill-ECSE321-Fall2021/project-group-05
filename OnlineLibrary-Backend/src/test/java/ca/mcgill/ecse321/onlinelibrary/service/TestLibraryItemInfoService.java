package ca.mcgill.ecse321.onlinelibrary.service;

import ca.mcgill.ecse321.onlinelibrary.dao.*;
import ca.mcgill.ecse321.onlinelibrary.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class TestLibraryItemInfoService {
	@Mock
	private BookInfoRepository bookInfoDao;
	@Mock
	private MovieInfoRepository movieInfoDao;
	@Mock
	private AlbumInfoRepository albumInfoDao;
	@Mock
	private NewspaperInfoRepository newspaperInfoDao;
	@Mock
	private ArchiveInfoRepository archiveInfoDao;
	@Mock
	private LibraryItemInfoRepository libraryItemInfoDao;
	@Mock
	private ReservationRepository reservationDao;

	@InjectMocks
	private LibraryItemInfoService libraryItemInfoService;
	@InjectMocks
	private LibraryItemService libraryItemService;

	private static final int BOOK_INFO_KEY = 1;
	private static final int BOOK_INFO_BAD_KEY = 2;
	private static final int MOVIE_INFO_KEY = 3;
	private static final int MOVIE_INFO_BAD_KEY = 4;
	private static final int ALBUM_INFO_KEY = 5;
	private static final int ALBUM_INFO_BAD_KEY = 6;
	private static final int NEWSPAPER_INFO_KEY = 7;
	private static final int NEWSPAPER_INFO_BAD_KEY = 8;
	private static final int ARCHIVE_INFO_KEY = 9;
	private static final int ARCHIVE_INFO_BAD_KEY = 10;
	private static final int RESERVATION_KEY = 11;
	private static final int RESERVATION_BAD_KEY = 12;
	private static final int MEMBER_KEY = 13;
	private static final int MEMBER_BAD_KEY = 14;
	private static final BookInfo ALREADY_RESERVED_BOOK_INFO = new BookInfo();
	

	@BeforeEach
	public void setMockOuput() {
		lenient().when(reservationDao.findReservationByReservationId(any(Integer.class))).thenAnswer( (InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(RESERVATION_KEY)){
				Reservation reservation = new Reservation(new Member("123 Main Street", "Seb"), new BookInfo(), new Date(200));
				reservation.setId(RESERVATION_KEY);
				return reservation;
			} else {
				return null;
			}
		});

		lenient().when(reservationDao.findReservationByMember(any(Member.class))).thenAnswer( (InvocationOnMock invocation) -> {
			if (((Member) invocation.getArgument(0)).getId().equals(MEMBER_KEY)){
				Member member = new Member("123 Main Street", "Seb");
				member.setId(MEMBER_KEY);
				List<Reservation> reservation = new ArrayList<Reservation>();
				reservation.add(new Reservation(member, new BookInfo(), new Date(200)));
				reservation.add(new Reservation(member, ALREADY_RESERVED_BOOK_INFO, new Date(300)));
				return reservation;
			} else {
				return null;
			}
		});

		lenient().when(reservationDao.findReservationByReservedItemOrderByDateAsc(any(ReservableItemInfo.class))).thenAnswer( (InvocationOnMock invocation) -> {
			if (((BookInfo) invocation.getArgument(0)).getId().equals(BOOK_INFO_KEY)){
				BookInfo bookInfo = new BookInfo();
				bookInfo.setId(BOOK_INFO_KEY);
				List<Reservation> reservation = new ArrayList<Reservation>();
				reservation.add(new Reservation(new Member("123 Main Street", "Seb"), bookInfo, new Date(200)));
				return reservation;
			} else {
				return null;
			}
		});

		lenient().when(bookInfoDao.findBookInfoById(any(Integer.class))).thenAnswer( (InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(BOOK_INFO_KEY)) {
				BookInfo bookInfo = new BookInfo();
				bookInfo.setId(BOOK_INFO_KEY);
				return bookInfo;
			} else {
				return null;
			}
		});
		lenient().when(movieInfoDao.findMovieInfoById(any(Integer.class))).thenAnswer( (InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(MOVIE_INFO_KEY)) {
				MovieInfo movieInfo = new MovieInfo();
				movieInfo.setId(MOVIE_INFO_KEY);
				return movieInfo;
			} else {
				return null;
			}
		});
		lenient().when(albumInfoDao.findAlbumInfoById(any(Integer.class))).thenAnswer( (InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(ALBUM_INFO_KEY)) {
				AlbumInfo albumInfo = new AlbumInfo();
				albumInfo.setId(ALBUM_INFO_KEY);
				return albumInfo;
			} else {
				return null;
			}
		});
		lenient().when(newspaperInfoDao.findNewspaperInfoById(any(Integer.class))).thenAnswer( (InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(NEWSPAPER_INFO_KEY)) {
				NewspaperInfo newspaperInfo = new NewspaperInfo();
				newspaperInfo.setId(NEWSPAPER_INFO_KEY);
				return newspaperInfo;
			} else {
				return null;
			}
		});
		lenient().when(archiveInfoDao.findArchiveInfoById(any(Integer.class))).thenAnswer( (InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(ARCHIVE_INFO_KEY)) {
				ArchiveInfo archiveInfo = new ArchiveInfo();
				archiveInfo.setId(ARCHIVE_INFO_KEY);
				return archiveInfo;
			} else {
				return null;
			}
		});
		lenient().when(libraryItemInfoDao.findAll()).thenAnswer( (InvocationOnMock invocation) -> {
			List<LibraryItemInfo> listInfo = new ArrayList<LibraryItemInfo>();
			ArchiveInfo archiveInfo = new ArchiveInfo();
			archiveInfo.setId(ARCHIVE_INFO_KEY);
			NewspaperInfo newspaperInfo = new NewspaperInfo();
			newspaperInfo.setId(NEWSPAPER_INFO_KEY);
			AlbumInfo albumInfo = new AlbumInfo();
			albumInfo.setId(ALBUM_INFO_KEY);
			MovieInfo movieInfo = new MovieInfo();
			movieInfo.setId(MOVIE_INFO_KEY);
			BookInfo bookInfo = new BookInfo();
			bookInfo.setId(BOOK_INFO_KEY);

			listInfo.add(archiveInfo);
			listInfo.add(newspaperInfo);
			listInfo.add(albumInfo);
			listInfo.add(movieInfo);
			listInfo.add(bookInfo);

			return listInfo;
		});
		
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(bookInfoDao.save(any(BookInfo.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(movieInfoDao.save(any(MovieInfo.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(albumInfoDao.save(any(AlbumInfo.class))).then(returnParameterAsAnswer);
		lenient().when(newspaperInfoDao.save(any(NewspaperInfo.class))).then(returnParameterAsAnswer);
		lenient().when(archiveInfoDao.save(any(ArchiveInfo.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(reservationDao.save(any(Reservation.class))).thenAnswer(returnParameterAsAnswer);
	}

	@Test
	public void testReserveItem(){
		Reservation reservation = null;
		Member member = new Member("123 Main Street", "Seb");
		member.setId(MEMBER_KEY);
		member.activate();
		BookInfo bookInfo = new BookInfo();
		try {
			reservation = libraryItemInfoService.reserveItem(member, bookInfo);
		} catch (IllegalArgumentException e){
			fail();
		}
		assertNotNull(reservation);
		assertEquals(reservation.getMember(), member);
		assertEquals(reservation.getReservableItemInfo(), bookInfo);
	}

	@Test
	public void testReserveItemMemberNull(){
		String error = "";
		Reservation reservation = null;
		Member member = null;
		BookInfo bookInfo = new BookInfo();
		try {
			reservation = libraryItemInfoService.reserveItem(member, bookInfo);
		} catch (IllegalArgumentException e){
			error += e.getMessage();
		}
		assertNull(reservation);
		assertTrue(error.contains("A member needs to be assigned to a reservation."));
	}

	@Test
	public void testReserveItemMemberInactive(){
		String error = "";
		Reservation reservation = null;
		Member member = new Member("123 Main Street", "Seb");
		member.setId(MEMBER_KEY);
		//member.activate();
		BookInfo bookInfo = new BookInfo();
		try {
			reservation = libraryItemInfoService.reserveItem(member, bookInfo);
		} catch (IllegalArgumentException e){
			error += e.getMessage();
		}
		assertNull(reservation);
		assertTrue(error.contains("Member account is inactive."));
	}

	@Test
	public void testReserveItemMemberBlacklisted(){
		String error = "";
		Reservation reservation = null;
		Member member = new Member("123 Main Street", "Seb");
		member.setId(MEMBER_KEY);
		member.activate();
		member.applyStatusPenalty(); //yellow
		member.applyStatusPenalty(); //red
		member.applyStatusPenalty(); //blacklisted
		BookInfo bookInfo = new BookInfo();
		try {
			reservation = libraryItemInfoService.reserveItem(member, bookInfo);
		} catch (IllegalArgumentException e){
			error += e.getMessage();
		}
		assertNull(reservation);
		assertTrue(error.contains("Member is blacklisted."));
	}

	@Test
	public void testReserveItemReservableItemInfoNull(){
		String error = "";
		Reservation reservation = null;
		Member member = new Member("123 Main Street", "Seb");
		member.setId(MEMBER_KEY);
		member.activate();
		BookInfo bookInfo = null;
		try {
			reservation = libraryItemInfoService.reserveItem(member, bookInfo);
		} catch (IllegalArgumentException e){
			error += e.getMessage();
		}
		assertNull(reservation);
		assertTrue(error.contains("An item needs to be assigned to a reservation"));
	}

	@Test
	public void testReserveItemDuplicateReservation() {
		Member member = new Member("123 Main Street", "Seb");
		member.setId(MEMBER_KEY);
		member.activate();
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			libraryItemInfoService.reserveItem(member, ALREADY_RESERVED_BOOK_INFO);
		});
		assertEquals("Member already has a reservation for this item.", e.getMessage());
	}

	@Test
	public void testReserveItemAllNull(){
		String error = "";
		Reservation reservation = null;
		Member member = null;
		//member.activate();
		BookInfo bookInfo = null;
		try {
			reservation = libraryItemInfoService.reserveItem(member, bookInfo);
		} catch (IllegalArgumentException e){
			error += e.getMessage();
		}
		assertNull(reservation);
		assertTrue(error.contains("A member needs to be assigned to a reservation."));
		assertTrue(error.contains("An item needs to be assigned to a reservation"));
	}

	@Test
	public void testGetReservationByReservationId(){
		Reservation reservation = null;
		try {
			reservation = libraryItemInfoService.getReservationsByReservationId(RESERVATION_KEY);
		} catch (IllegalArgumentException e){
			fail();
		}
		assertNotNull(reservation);
		assertEquals(RESERVATION_KEY, reservation.getId());
	}

	@Test
	public void testGetReservationByReservationBadId(){
		String error = "";
		Reservation reservation = null;
		try {
			reservation = libraryItemInfoService.getReservationsByReservationId(RESERVATION_BAD_KEY);
		} catch (IllegalArgumentException e){
			error += e.getMessage();
		}
		assertNull(reservation);
		assertTrue(error.contains("The reservation with id was not found in the database."));
	}

	@Test
	public void testGetReservationByMember(){
		List<Reservation> reservation = null;
		Member member = new Member("123 Road Street", "Seb");
		member.setId(MEMBER_KEY);
		try {
			reservation = libraryItemInfoService.getReservationsByMember(member);
		} catch (IllegalArgumentException e){
			fail();
		}
		assertNotNull(reservation);
		assertEquals(MEMBER_KEY, reservation.get(0).getMember().getId());
	}

	@Test
	public void testGetReservationByBadMember(){
		String error = "";
		List<Reservation> reservation = null;
		Member member = new Member("123 Road Street", "Seb");
		member.setId(MEMBER_BAD_KEY);
		try {
			reservation = libraryItemInfoService.getReservationsByMember(member);
		} catch (IllegalArgumentException e){
			error += e.getMessage();
		}
		assertNull(reservation);
		assertTrue(error.contains("The reservation with member was not found in the database."));
	}

	@Test
	public void testGetReservationByReservableItemInfo(){
		List<Reservation> reservation = null;
		BookInfo bookInfo = new BookInfo();
		bookInfo.setId(BOOK_INFO_KEY);
		try {
			reservation = libraryItemInfoService.getReservationsByReservableItemInfo(bookInfo);
		} catch (IllegalArgumentException e){
			fail();
		}
		assertNotNull(reservation);
		assertEquals(BOOK_INFO_KEY, reservation.get(0).getReservableItemInfo().getId());
	}

	@Test
	public void testGetReservationByBadReservableItemInfo(){
		String error = "";
		List<Reservation> reservation = null;
		BookInfo bookInfo = new BookInfo();
		bookInfo.setId(BOOK_INFO_BAD_KEY);
		try {
			reservation = libraryItemInfoService.getReservationsByReservableItemInfo(bookInfo);
		} catch (IllegalArgumentException e){
			error += e.getMessage();
		}
		assertNull(reservation);
		assertTrue(error.contains("The reservation with the reservable item info was not found in the database."));
	}

	@Test
	public void testDeleteReservation(){
		Reservation reservation = null;
		Member member = new Member("123 Main Street", "Seb");
		member.setId(MEMBER_KEY);
		member.activate();
		BookInfo bookInfo = new BookInfo();
		reservation = libraryItemInfoService.reserveItem(member, bookInfo);
		reservation.setId(RESERVATION_KEY);
		try {
			libraryItemInfoService.deleteReservationbyId(RESERVATION_KEY);
		} catch (IllegalArgumentException e){
			fail();
		}
	}

	@Test
	public void testDeleteReservationBad(){
		String error = "";
		Reservation reservation = null;
		Member member = new Member("123 Main Street", "Seb");
		member.setId(MEMBER_KEY);
		member.activate();
		BookInfo bookInfo = new BookInfo();
		reservation = libraryItemInfoService.reserveItem(member, bookInfo);
		reservation.setId(RESERVATION_KEY);
		try {
			libraryItemInfoService.deleteReservationbyId(RESERVATION_BAD_KEY);
		} catch (IllegalArgumentException e){
			error += e.getMessage();
		}
		assertTrue(error.contains("Reservation with ID was not found."));
	}

	@Test
	public void testCreateBookInfo() {
		String title = "Title";
		Integer numberOfPage = 10;
		String author = "Author";
		long isbn = 1;
		BookInfo bookInfo = null;
		try {
			bookInfo = libraryItemInfoService.createBookInfo(title, numberOfPage, author, isbn);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(bookInfo);
		assertEquals(bookInfo.getTitle(), title);
		assertEquals(bookInfo.getNumberOfPage(), numberOfPage);
		assertEquals(bookInfo.getAuthor(), author);
		assertEquals(bookInfo.getIsbn(), isbn);
	}

	@Test
	public void testCreateBookInfoTitleNull() {
		String error="";
		String title = null;
		int numberOfPage = 10;
		String author = "Author";
		long isbn = 1;
		BookInfo bookInfo = null;
		try {
			bookInfo = libraryItemInfoService.createBookInfo(title, numberOfPage, author, isbn);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(bookInfo);
		assertTrue(error.contains("Title can't be empty."));
	}

	@Test
	public void testCreateBookInfoTitleIsEmpty() {
		String error="";
		String title = "   ";
		int numberOfPage = 10;
		String author = "Author";
		long isbn = 1;
		BookInfo bookInfo = null;
		try {
			bookInfo = libraryItemInfoService.createBookInfo(title, numberOfPage, author, isbn);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(bookInfo);
		assertTrue(error.contains("Title can't be empty."));
	}

	@Test
	public void testCreateBookInfoNumberOfPageIs0() {
		String error="";
		String title = "title";
		int numberOfPage = 0;
		String author = "Author";
		long isbn = 1;
		BookInfo bookInfo = null;
		try {
			bookInfo = libraryItemInfoService.createBookInfo(title, numberOfPage, author, isbn);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(bookInfo);
		assertTrue(error.contains("Number of page can't be 0."));
	}

	@Test
	public void testCreateBookInfoAuthorIsNull() {
		String error="";
		String title = "title";
		int numberOfPage = 10;
		String author = null;
		long isbn = 1;
		BookInfo bookInfo = null;
		try {
			bookInfo = libraryItemInfoService.createBookInfo(title, numberOfPage, author, isbn);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(bookInfo);
		assertTrue(error.contains("Author can't be empty."));
	}

	@Test
	public void testCreateBookInfoAuthorIsEmpty() {
		String error="";
		String title = "title";
		int numberOfPage = 10;
		String author = "";
		long isbn = 1;
		BookInfo bookInfo = null;
		try {
			bookInfo = libraryItemInfoService.createBookInfo(title, numberOfPage, author, isbn);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(bookInfo);
		assertTrue(error.contains("Author can't be empty."));
	}

	@Test
	public void testCreateBookInfoAllNulls() {
		String error="";
		String title = null;
		int numberOfPage = 0;
		String author = null;
		long isbn = 0;
		BookInfo bookInfo = null;
		try {
			bookInfo = libraryItemInfoService.createBookInfo(title, numberOfPage, author, isbn);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(bookInfo);
		assertTrue(error.contains("Title can't be empty."));
		assertTrue(error.contains("Number of page can't be 0."));
		assertTrue(error.contains("Author can't be empty."));
	}
	
	@Test
	public void testGetBookInfo() {
		BookInfo bookInfo = null;
		try {
			bookInfo = libraryItemInfoService.getBookInfo(BOOK_INFO_KEY);
		} catch (IllegalArgumentException e){
			fail();
		}
		assertNotNull(bookInfo);
		assertEquals(BOOK_INFO_KEY, bookInfo.getId());
	}

	@Test
	public void testGetBookInfoBadId() {
		String error = "";
		BookInfo bookInfo = null;
		try {
			bookInfo = libraryItemInfoService.getBookInfo(BOOK_INFO_BAD_KEY);
		} catch (IllegalArgumentException e) {
			error += e.getMessage();
		}
		assertNull(bookInfo);
		assertTrue(error.contains("The bookInfo with id " + BOOK_INFO_BAD_KEY + " was not found in the database."));
	}
	
	@Test
	public void testUpdateBookInfo() {
		BookInfo bookInfo = libraryItemInfoService.createBookInfo("Title", 123, "An author", 12345);
		//int id = bookInfo.getId();
		String newTitle = "Title2";
		int newNumberOfPage = 321;
		String newAuthor= "Author";
		long newIsbn = 54321;
		try {
			bookInfo = libraryItemInfoService.updateBookInfo(bookInfo,newTitle, newNumberOfPage, newAuthor, newIsbn);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(bookInfo);
		assertEquals(bookInfo.getTitle(), newTitle);
		assertEquals(bookInfo.getNumberOfPage(), newNumberOfPage);
		assertEquals(bookInfo.getAuthor(), newAuthor);
		assertEquals(bookInfo.getIsbn(), newIsbn);
	}

	@Test
	public void testCreateMovieInfo() {
		String title = "Title";
		String genre = "Horror";
		String director = "Author";
		int length = 100;
		MovieInfo movieInfo = null;
		try {
			movieInfo = libraryItemInfoService.createMovieInfo(title, genre, director, length);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(movieInfo);
		assertNotNull(movieInfo.getTitle());
		assertEquals(movieInfo.getGenre(), genre);
		assertEquals(movieInfo.getDirector(), director);
		assertEquals(movieInfo.getLength(), length);
	}
	
	@Test
	public void testCreateMovieInfoTitleNull() {
		String error="";
		String title = null;
		String genre = "aGenre";
		String director = "Author";
		int length = 100;
		MovieInfo movieInfo = null;
		try {
			movieInfo = libraryItemInfoService.createMovieInfo(title, genre, director, length);
		} catch (IllegalArgumentException e) {
			error=e.getMessage();
		}
		assertNull(movieInfo);
		assertTrue(error.contains("Title can't be empty."));
	}

	@Test
	public void testCreateMovieInfoGenreNull() {
		String error="";
		String title = "aTitle";
		String genre = null;
		String director = "Author";
		int length = 100;
		MovieInfo movieInfo = null;
		try {
			movieInfo = libraryItemInfoService.createMovieInfo(title, genre, director, length);
		} catch (IllegalArgumentException e) {
			error=e.getMessage();
		}
		assertNull(movieInfo);
		assertTrue(error.contains("Genre can't be empty."));
	}

	@Test
	public void testCreateMovieInfoGenreEmpty() {
		String error="";
		String title = "aTitle";
		String genre = "   ";
		String director = "Author";
		int length = 100;
		MovieInfo movieInfo = null;
		try {
			movieInfo = libraryItemInfoService.createMovieInfo(title, genre, director, length);
		} catch (IllegalArgumentException e) {
			error=e.getMessage();
		}
		assertNull(movieInfo);
		assertTrue(error.contains("Genre can't be empty."));
	}

	@Test
	public void testCreateMovieInfoDirectorNull() {
		String error="";
		String title = "aTitle";
		String genre = "aGenre";
		String director = null;
		int length = 100;
		MovieInfo movieInfo = null;
		try {
			movieInfo = libraryItemInfoService.createMovieInfo(title, genre, director, length);
		} catch (IllegalArgumentException e) {
			error=e.getMessage();
		}
		assertNull(movieInfo);
		assertTrue(error.contains("Director can't be empty."));
	}

	@Test
	public void testCreateMovieInfoDirectorEmpty() {
		String error="";
		String title = "aTitle";
		String genre = "aGenre";
		String director = "  ";
		int length = 100;
		MovieInfo movieInfo = null;
		try {
			movieInfo = libraryItemInfoService.createMovieInfo(title, genre, director, length);
		} catch (IllegalArgumentException e) {
			error=e.getMessage();
		}
		assertNull(movieInfo);
		assertTrue(error.contains("Director can't be empty."));
	}

	@Test
	public void testCreateMovieInfoLength0() {
		String error="";
		String title = "aTitle";
		String genre = "aGenre";
		String director = "Director";
		int length = 0;
		MovieInfo movieInfo = null;
		try {
			movieInfo = libraryItemInfoService.createMovieInfo(title, genre, director, length);
		} catch (IllegalArgumentException e) {
			error=e.getMessage();
		}
		assertNull(movieInfo);
		assertTrue(error.contains("Length can't be 0."));
	}

	@Test
	public void testCreateMovieAllEmpty() {
		String error="";
		String title = "";
		String genre = "";
		String director = "";
		int length = 0;
		MovieInfo movieInfo = null;
		try {
			movieInfo = libraryItemInfoService.createMovieInfo(title, genre, director, length);
		} catch (IllegalArgumentException e) {
			error=e.getMessage();
		}
		assertNull(movieInfo);
		assertTrue(error.contains("Genre can't be empty."));
		assertTrue(error.contains("Director can't be empty."));
		assertTrue(error.contains("Length can't be 0."));
	}
	@Test
	public void testGetMovieInfo() {
		MovieInfo movieInfo = null;
		try {
			movieInfo = libraryItemInfoService.getMovieInfo(MOVIE_INFO_KEY);
		} catch (IllegalArgumentException e){
			fail();
		}
		assertNotNull(movieInfo);
		assertEquals(MOVIE_INFO_KEY, movieInfo.getId());
	}

	@Test
	public void testGetMovieInfoBadId() {
		String error = "";
		MovieInfo movieInfo = null;
		try {
			movieInfo = libraryItemInfoService.getMovieInfo(MOVIE_INFO_BAD_KEY);
		} catch (IllegalArgumentException e) {
			error += e.getMessage();
		}
		assertNull(movieInfo);
		assertTrue(error.contains("The movieInfo with id " + MOVIE_INFO_BAD_KEY + " was not found in the database."));
	}
	
	@Test
	public void testUpdateMovienfo() {
		MovieInfo movieInfo = libraryItemInfoService.createMovieInfo("aTitle","AGenre", "aDirector", 123);
		String newTitle = "Title2";
		String newGenre = "Genre2";
		String newDirector = "Director2";
		int length = 321;
		try {
			movieInfo = libraryItemInfoService.updateMovieInfo(movieInfo, newTitle, newGenre, newDirector, length);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(movieInfo);
		assertEquals(movieInfo.getTitle(), newTitle);
		assertEquals(movieInfo.getGenre(), newGenre);
		assertEquals(movieInfo.getDirector(), newDirector);
		assertEquals(movieInfo.getLength(), length);
	}
	
	@Test
	public void testCreateAlbumInfo() {
		String title = "Title";
		String composerPerformer = "Author";
		String genre = "Genre";
		AlbumInfo albumInfo = null;
		try {
			albumInfo = libraryItemInfoService.createAlbumInfo(title, composerPerformer, genre);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(albumInfo);
		assertEquals(albumInfo.getTitle(), title);
		assertEquals(albumInfo.getComposerPerformer(), composerPerformer);
		assertEquals(albumInfo.getGenre(), genre);
	}

	@Test
	public void testCreateAlbumInfoTitleNull() {
		String error="";
		String title = null;
		String composerPerformer = "Composer/Performer";
		String genre = "Genre";
		AlbumInfo albumInfo = null;
		try {
			albumInfo = libraryItemInfoService.createAlbumInfo(title, composerPerformer, genre);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(albumInfo);
		assertTrue(error.contains("Title can't be empty."));
	}

	@Test
	public void testCreateAlbumInfoTitleEmpty() {
		String error="";
		String title = " ";
		String composerPerformer = "Composer/Performer";
		String genre = "Genre";
		AlbumInfo albumInfo = null;
		try {
			albumInfo = libraryItemInfoService.createAlbumInfo(title, composerPerformer, genre);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(albumInfo);
		assertTrue(error.contains("Title can't be empty."));
	}

	@Test
	public void testCreateAlbumInfoComposerNull() {
		String error="";
		String title = "Title";
		String composerPerformer = null;
		String genre = "Genre";
		AlbumInfo albumInfo = null;
		try {
			albumInfo = libraryItemInfoService.createAlbumInfo(title, composerPerformer, genre);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(albumInfo);
		assertTrue(error.contains("composerPerformer can't be empty."));
	}

	@Test
	public void testCreateAlbumInfoComposerEmpty() {
		String error="";
		String title = "Title";
		String composerPerformer = " ";
		String genre = "Genre";
		AlbumInfo albumInfo = null;
		try {
			albumInfo = libraryItemInfoService.createAlbumInfo(title, composerPerformer, genre);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(albumInfo);
		assertTrue(error.contains("composerPerformer can't be empty."));
	}

	@Test
	public void testCreateAlbumInfoGenreNull() {
		String error="";
		String title = "Title";
		String composerPerformer = "Composer / Performer";
		String genre = null;
		AlbumInfo albumInfo = null;
		try {
			albumInfo = libraryItemInfoService.createAlbumInfo(title, composerPerformer, genre);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(albumInfo);
		assertTrue(error.contains("Genre can't be empty."));
	}

	@Test
	public void testCreateAlbumInfoGenreEmpty() {
		String error="";
		String title = "Title";
		String composerPerformer = "Composer / Performer";
		String genre = " ";
		AlbumInfo albumInfo = null;
		try {
			albumInfo = libraryItemInfoService.createAlbumInfo(title, composerPerformer, genre);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(albumInfo);
		assertTrue(error.contains("Genre can't be empty."));
	}

	@Test
	public void testCreateeAlbumInfoAllEmpty() {
		String error="";
		String title = " ";
		String composerPerformer = null;
		String genre = " ";
		AlbumInfo albumInfo = null;
		try {
			albumInfo = libraryItemInfoService.createAlbumInfo(title, composerPerformer, genre);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(albumInfo);
		assertTrue(error.contains("Genre can't be empty."));
		assertTrue(error.contains("composerPerformer can't be empty."));
		assertTrue(error.contains("Title can't be empty."));
	}
	
	@Test
	public void testGetAlbumInfo() {
		AlbumInfo albumInfo = null;
		try {
			albumInfo = libraryItemInfoService.getAlbumInfo(ALBUM_INFO_KEY);
		} catch (IllegalArgumentException e){
			fail();
		}
		assertNotNull(albumInfo);
		assertEquals(ALBUM_INFO_KEY, albumInfo.getId());
	}

	@Test
	public void testGetAlbumInfoInfoBadId() {
		String error = "";
		AlbumInfo albumInfo = null;
		try {
			albumInfo = libraryItemInfoService.getAlbumInfo(ALBUM_INFO_BAD_KEY);
		} catch (IllegalArgumentException e) {
			error += e.getMessage();
		}
		assertNull(albumInfo);
		assertTrue(error.contains("The albumInfo with id " + ALBUM_INFO_BAD_KEY + " was not found in the database."));
	}
	
	@Test
	public void testUpdateAlbumInfo() {
		AlbumInfo albumInfo = libraryItemInfoService.createAlbumInfo("ATitle", "aComposer", "aGenre");
		String newTitle = "Title2";
		String newComposerPerformer = "ComposerPerformer2";
		String newGenre = "Genre2";
		try {
			albumInfo = libraryItemInfoService.updateAlbumInfo(albumInfo, newTitle, newComposerPerformer, newGenre);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(albumInfo);
		assertEquals(albumInfo.getTitle(), newTitle);
		assertEquals(albumInfo.getComposerPerformer(), newComposerPerformer);
		assertEquals(albumInfo.getGenre(), newGenre);
	}

	@Test
	public void testCreateNewspaperInfo() {
		String periodicalTitle = "The New Yorker";
		LocalDate publication = LocalDate.parse("2021-10-31");
		String frequency = "Frequency";
		int number = 123;
		NewspaperInfo newspaperInfo = null;
		try {
			newspaperInfo = libraryItemInfoService.createNewspaperInfo(periodicalTitle, publication, frequency, number);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(newspaperInfo);
		assertEquals(newspaperInfo.getPublication().getTime(), Date.valueOf(publication).getTime());
		assertEquals(newspaperInfo.getFrequency(), frequency);
		assertEquals(newspaperInfo.getNumber(), number);
	}

	@Test
	public void testCreateNewspaperInfoPeriodicalTitleIsNull() {
		String error = "";
		String periodicalTitle = null;
		LocalDate publication = LocalDate.parse("2021-10-31");
		String frequency = "Frequency";
		int number = 123;
		NewspaperInfo newspaperInfo = null;
		try {
			newspaperInfo = libraryItemInfoService.createNewspaperInfo(periodicalTitle, publication, frequency, number);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(newspaperInfo);
		assertTrue(error.contains("Periodical title can't be empty."));
	}

	@Test
	public void testCreateNewspaperInfoPublicationIsNull() {
		String error="";
		String periodicalTitle = "The New Yorker";
		LocalDate publication = null;
		String frequency = "Frequency";
		int number = 5;
		NewspaperInfo newspaperInfo = null;
		try {
			newspaperInfo = libraryItemInfoService.createNewspaperInfo(periodicalTitle, publication, frequency, number);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(newspaperInfo);
		assertTrue(error.contains("Date can't be empty."));
	}

	@Test
	public void testCreateNewspaperInfoFrequencyIsNull() {
		String error = "";
		String periodicalTitle = "The New Yorker";
		LocalDate publication = LocalDate.parse("2021-10-31");
		String frequency = null;
		int number = 5;
		NewspaperInfo newspaperInfo = null;
		try {
			newspaperInfo = libraryItemInfoService.createNewspaperInfo(periodicalTitle, publication, frequency, number);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(newspaperInfo);
		assertTrue(error.contains("Frequency can't be empty."));
	}

	@Test
	public void testCreateNewspaperInfoFrequencyIsEmpty() {
		String error = "";
		String periodicalTitle = "The New Yorker";
		LocalDate publication = LocalDate.parse("2021-10-31");
		String frequency = " ";
		int number = 5;
		NewspaperInfo newspaperInfo = null;
		try {
			newspaperInfo = libraryItemInfoService.createNewspaperInfo(periodicalTitle, publication, frequency, number);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(newspaperInfo);
		assertTrue(error.contains("Frequency can't be empty."));
	}

	@Test
	public void testCreateNewspaperInfoNumberIsNegative() {
		String error="";
		String periodicalTitle = "The New Yorker";
		LocalDate publication = LocalDate.parse("2021-10-31");
		String frequency = "Everyday";
		int number = -1;
		NewspaperInfo newspaperInfo = null;
		try {
			newspaperInfo = libraryItemInfoService.createNewspaperInfo(periodicalTitle, publication, frequency, number);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(newspaperInfo);
		assertTrue(error.contains("Number can't be negative."));
	}

	@Test
	public void testCreateNewspaperInfoAllEmpty() {
		String error="";
		String periodicalTitle = "";
		LocalDate publication = null;
		String frequency = " ";
		int number = -1;
		NewspaperInfo newspaperInfo = null;
		try {
			newspaperInfo = libraryItemInfoService.createNewspaperInfo(periodicalTitle, publication, frequency, number);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(newspaperInfo);
		assertTrue(error.contains("Periodical title can't be empty."));
		assertTrue(error.contains("Number can't be negative."));
		assertTrue(error.contains("Frequency can't be empty."));
		assertTrue(error.contains("Date can't be empty."));
	}
	
	@Test
	public void testGetNewspaperInfo() {
		NewspaperInfo newspaperInfo = null;
		try {
			newspaperInfo = libraryItemInfoService.getNewspaperInfo(NEWSPAPER_INFO_KEY);
		} catch (IllegalArgumentException e){
			fail();
		}
		assertNotNull(newspaperInfo);
		assertEquals(NEWSPAPER_INFO_KEY, newspaperInfo.getId());
	}

	@Test
	public void testGetNewspaperInfoBadId() {
		String error = "";
		NewspaperInfo newspaperInfo = null;
		try {
			newspaperInfo = libraryItemInfoService.getNewspaperInfo(NEWSPAPER_INFO_BAD_KEY);
		} catch (IllegalArgumentException e) {
			error += e.getMessage();
		}
		assertNull(newspaperInfo);
		assertTrue(error.contains("The newspaperInfo with id " + NEWSPAPER_INFO_BAD_KEY + " was not found in the database."));
	}
	
	@Test
	public void testUpdateNewspaperInfo() {
		NewspaperInfo newspaper = libraryItemInfoService.createNewspaperInfo("The New Yorker", LocalDate.parse("2020-11-11"), "AFrequency", 123);
		LocalDate newPublicationDate = LocalDate.parse("2021-11-11");
		String newFrequency = "Frequency2";
		int newNumber = 321;
		NewspaperInfo newspaperInfo = null;
		try {
			newspaperInfo = libraryItemInfoService.updateNewspaperInfo(newspaper, "The New Yorker",  newPublicationDate, newFrequency, newNumber);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(newspaperInfo);
		assertEquals(newspaperInfo.getPublication(), Date.valueOf(newPublicationDate));
		assertEquals(newspaperInfo.getFrequency(), newFrequency);
		assertEquals(newspaperInfo.getNumber(), newNumber);
	}

	@Test
	public void testCreateArchiveInfo() {
		String title = "Title";
		String description = "Description";
		LocalDate publicationDate = LocalDate.parse("2021-10-31");
		ArchiveInfo archiveInfo = null;
		try {
			archiveInfo = libraryItemInfoService.createArchiveInfo(title, description, publicationDate);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(archiveInfo);
		assertEquals(archiveInfo.getTitle(), title);
		assertEquals(archiveInfo.getDescription(), description);
		assertEquals(archiveInfo.getPublicationDate(), Date.valueOf(publicationDate));
	}

	@Test
	public void testCreateArchiveInfoTitleIsNull() {
		String error="";
		String title = null;
		String description = "Description";
		LocalDate publicationDate = LocalDate.parse("2021-10-31");
		ArchiveInfo archiveInfo = null;
		try {
			archiveInfo = libraryItemInfoService.createArchiveInfo(title, description, publicationDate);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(archiveInfo);
		assertTrue(error.contains("Title can't be empty."));
	}

	@Test
	public void testCreateArchiveInfoTitleIsEmpty() {
		String error="";
		String title = " ";
		String description = "Description";
		LocalDate publicationDate = LocalDate.parse("2021-10-31");
		ArchiveInfo archiveInfo = null;
		try {
			archiveInfo = libraryItemInfoService.createArchiveInfo(title, description, publicationDate);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(archiveInfo);
		assertTrue(error.contains("Title can't be empty."));
	}

	@Test
	public void testCreateArchiveInfoDescriptionIsNull() {
		String error="";
		String title = "Title";
		String description = null;
		LocalDate publicationDate = LocalDate.parse("2021-10-31");
		ArchiveInfo archiveInfo = null;
		try {
			archiveInfo = libraryItemInfoService.createArchiveInfo(title, description, publicationDate);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(archiveInfo);
		assertTrue(error.contains("Description can't be empty."));
	}

	@Test
	public void testCreateArchiveInfoPublicationDateIsNull() {
		String error="";
		String title = "Title";
		String description = "Description";
		LocalDate publicationDate = null;
		ArchiveInfo archiveInfo = null;
		try {
			archiveInfo = libraryItemInfoService.createArchiveInfo(title, description, publicationDate);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(archiveInfo);
		assertTrue(error.contains("Publication date can't be empty."));
	}

	@Test
	public void testCreateArchiveInfoAllEmpty() {
		String error="";
		String title = " ";
		String description = null;
		LocalDate publicationDate = null;
		ArchiveInfo archiveInfo = null;
		try {
			archiveInfo = libraryItemInfoService.createArchiveInfo(title, description, publicationDate);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(archiveInfo);
		assertTrue(error.contains("Title can't be empty."));
		assertTrue(error.contains("Description can't be empty."));
		assertTrue(error.contains("Publication date can't be empty."));
	}
	
	@Test
	public void testGetArchiveInfo() {
		ArchiveInfo archiveInfo = null;
		try {
			archiveInfo = libraryItemInfoService.getArchiveInfo(ARCHIVE_INFO_KEY);
		} catch (IllegalArgumentException e){
			fail();
		}
		assertNotNull(archiveInfo);
		assertEquals(ARCHIVE_INFO_KEY, archiveInfo.getId());
	}

	@Test
	public void testGetArchiveInfoBadId() {
		String error = "";
		ArchiveInfo archiveInfo = null;
		try {
			archiveInfo = libraryItemInfoService.getArchiveInfo(ARCHIVE_INFO_BAD_KEY);
		} catch (IllegalArgumentException e) {
			error += e.getMessage();
		}
		assertNull(archiveInfo);
		assertTrue(error.contains("The archiveInfo with id " + ARCHIVE_INFO_BAD_KEY + " was not found in the database."));
	}
	
	@Test
	public void testUpdateArchivenfo() {
		ArchiveInfo archiveInfo = libraryItemInfoService.createArchiveInfo("ATitle", "aDescription", LocalDate.parse("2020-11-11"));
		String newTitle = "Title2";
		String newDescription = "Description2";
		Date newPublicationDate = Date.valueOf("2021-11-11");
		try {
			archiveInfo = libraryItemInfoService.updateArchiveInfo(archiveInfo, newTitle, newDescription, newPublicationDate);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(archiveInfo);
		assertEquals(archiveInfo.getTitle(), newTitle);
		assertEquals(archiveInfo.getDescription(), newDescription);
		assertEquals(archiveInfo.getPublicationDate(), newPublicationDate);
  }
  @Test
	public void testBrowseService(){

		Iterable<LibraryItemInfo> expectedIterable = libraryItemInfoDao.findAll();
		List<LibraryItemInfo> expected = new ArrayList<LibraryItemInfo>();

		for (LibraryItemInfo l: expectedIterable)
			expected.add(l);

		List<LibraryItemInfo> actual = libraryItemInfoService.browseAllLibraryItemInfos();

		for(int i = 0; i< expected.size(); i++){
			
			assertEquals(expected.get(i).getId(), actual.get(i).getId());
			
		}
	}
}
