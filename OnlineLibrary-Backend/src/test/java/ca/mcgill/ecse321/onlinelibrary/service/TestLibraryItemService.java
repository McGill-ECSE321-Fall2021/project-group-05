package ca.mcgill.ecse321.onlinelibrary.service;

import ca.mcgill.ecse321.onlinelibrary.dao.*;
import ca.mcgill.ecse321.onlinelibrary.model.*;
import ca.mcgill.ecse321.onlinelibrary.model.ReservableItem.ItemStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestLibraryItemService {

	@Mock
	private BookInfoRepository bookInfoDao;
	@Mock
	private BookRepository bookDao;
	@Mock
	private MovieRepository movieDao;
	@Mock
	private MovieInfoRepository movieInfoDao;
	@Mock
	private AlbumRepository albumDao;
	@Mock
	private AlbumInfoRepository albumInfoDao;
	@Mock
	private NewspaperRepository newspaperDao;
	@Mock
	private NewsPaperInfoRepository newspaperInfoDao;
	@Mock
	private ArchiveRepository archiveDao;
	@Mock 
	private ArchiveInfoRepository archiveInfoDao;
	@Mock
	private LoanRepository loanDao;
	@Mock
	private ReservationRepository reservationDao;
	@Mock
	private LibraryItemRepository libraryItemDao;
	@Mock
	private Member memberWithTooManyLoans;
	@Mock
	private Book bookWithALoan;

	private static final int BOOK_KEY = 1;
	private static final int BOOK_BAD_KEY = 2;
	private static final int MOVIE_KEY = 3;
	private static final int MOVIE_BAD_KEY = 4;
	private static final int ALBUM_KEY = 5;
	private static final int ALBUM_BAD_KEY = 6;
	private static final int NEWSPAPER_KEY = 7;
	private static final int NEWSPAPER_BAD_KEY = 8;
	private static final int ARCHIVE_KEY = 9;
	private static final int ARCHIVE_BAD_KEY = 10;
	private static final BookInfo BOOK_INFO_WITH_LESS_RESERVATIONS_THAN_COPIES = new BookInfo();
	private static final BookInfo BOOK_INFO_WITH_MORE_RESERVATIONS_THAN_COPIES = new BookInfo();
	private static final Member MEMBER_WITH_RESERVATION = new Member("123 Main Street", "John Doe");
	private static final Reservation RESERVATION = new Reservation(MEMBER_WITH_RESERVATION, BOOK_INFO_WITH_MORE_RESERVATIONS_THAN_COPIES, new Date(0));

	@InjectMocks
	private LibraryItemInfoService libraryItemInfoService;
	@InjectMocks
	private LibraryItemService libraryItemService;

	@BeforeEach
	public void setMockOuput() {
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(bookDao.save(any(Book.class))).thenAnswer((InvocationOnMock invocation) -> {
			Book book = invocation.getArgument(0);
			book.setId(BOOK_KEY);
			return book;
		});
		lenient().when(bookDao.findBookById(any(Integer.class))).thenAnswer( (InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(BOOK_KEY)) {
				Book book = new Book(new BookInfo());
				book.setId(BOOK_KEY);
				return book;
			} else {
				return null;
			}
		});
		lenient().when(bookInfoDao.save(any(BookInfo.class))).then(returnParameterAsAnswer);
		lenient().when(movieDao.save(any(Movie.class))).then((InvocationOnMock invocation) -> {
			Movie movie = invocation.getArgument(0);
			movie.setId(MOVIE_KEY);
			return movie;
		});
		lenient().when(movieDao.findMovieById(any(Integer.class))).thenAnswer( (InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(MOVIE_KEY)) {
				Movie movie = new Movie(new MovieInfo());
				movie.setId(MOVIE_KEY);
				return movie;
			} else {
				return null;
			}
		});
		lenient().when(movieInfoDao.save(any(MovieInfo.class))).then(returnParameterAsAnswer);
		lenient().when(albumDao.save(any(Album.class))).then((InvocationOnMock invocation) -> {
			Album album = invocation.getArgument(0);
			album.setId(ALBUM_KEY);
			return album;
		});
		lenient().when(albumDao.findAlbumById(any(Integer.class))).thenAnswer( (InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(ALBUM_KEY)) {
				Album album = new Album(new AlbumInfo());
				album.setId(ALBUM_KEY);
				return album;
			} else {
				return null;
			}
		});
		lenient().when(albumInfoDao.save(any(AlbumInfo.class))).then(returnParameterAsAnswer);
		lenient().when(newspaperDao.save(any(Newspaper.class))).then((InvocationOnMock invocation) -> {
			Newspaper newspaper = invocation.getArgument(0);
			newspaper.setId(NEWSPAPER_KEY);
			return newspaper;
		});
		lenient().when(newspaperDao.findNewspaperById(any(Integer.class))).thenAnswer( (InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(NEWSPAPER_KEY)) {
				Newspaper newspaper = new Newspaper(new NewsPaperInfo());
				newspaper.setId(NEWSPAPER_KEY);
				return newspaper;
			} else {
				return null;
			}
		});
		lenient().when(newspaperInfoDao.save(any(NewsPaperInfo.class))).then(returnParameterAsAnswer);
		lenient().when(archiveDao.save(any(Archive.class))).then((InvocationOnMock invocation) -> {
			Archive archive = invocation.getArgument(0);
			archive.setId(ARCHIVE_KEY);
			return archive;
		});
		lenient().when(archiveDao.findArchiveById(any(Integer.class))).thenAnswer( (InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(ARCHIVE_KEY)) {
				Archive archive = new Archive(new ArchiveInfo());
				archive.setId(ARCHIVE_KEY);
				return archive;
			} else {
				return null;
			}
		});
		lenient().when(archiveInfoDao.save(any(ArchiveInfo.class))).then(returnParameterAsAnswer);
		lenient().when(loanDao.save(any(Loan.class))).then(returnParameterAsAnswer);
		// TODO: Extract this to application.properties
		lenient().when(reservationDao.findReservationByReservedItemOrderByReservationDateAsc(any(ReservableItemInfo.class))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(BOOK_INFO_WITH_MORE_RESERVATIONS_THAN_COPIES)) {
				Reservation firstReservation = RESERVATION;
				Reservation secondReservation = new Reservation(new Member("123 Main Street", "John Doe 2"), BOOK_INFO_WITH_MORE_RESERVATIONS_THAN_COPIES, new Date(System.currentTimeMillis()));
				return new ArrayList(Arrays.asList(firstReservation, secondReservation));
			} else
				return new ArrayList();
		});
		lenient().when(libraryItemDao.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			return new ArrayList<>(Arrays.asList(new Book(BOOK_INFO_WITH_LESS_RESERVATIONS_THAN_COPIES), new Book(BOOK_INFO_WITH_MORE_RESERVATIONS_THAN_COPIES)));
		});
		lenient().when(memberWithTooManyLoans.getLoans()).then((InvocationOnMock invocation) -> new ArrayList(Arrays.asList(
				new Loan(new Date(0), new Book(new BookInfo()), memberWithTooManyLoans),
				new Loan(new Date(0), new Book(new BookInfo()), memberWithTooManyLoans),
				new Loan(new Date(0), new Book(new BookInfo()), memberWithTooManyLoans),
				new Loan(new Date(0), new Book(new BookInfo()), memberWithTooManyLoans),
				new Loan(new Date(0), new Book(new BookInfo()), memberWithTooManyLoans)
		)));
		lenient().when(bookWithALoan.getBookInfo()).thenReturn(BOOK_INFO_WITH_LESS_RESERVATIONS_THAN_COPIES);
		lenient().when(bookWithALoan.getLoan()).thenReturn(new Loan(new Date(0), bookWithALoan, new Member("123 Main Street", "John Doe")));
	}

	@Test
	public void testCreateBook() {
		BookInfo bookInfo = null;
		String title = "Title";
		int numberOfPage = 10;
		String author = "Author";
		int isbn = 10;
		bookInfo = libraryItemInfoService.createBookInfo(title, numberOfPage, author, isbn);
		Book book = null;
		try {
			book = libraryItemService.createBook(bookInfo);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(book);
		assertEquals(book.getBookInfo().getTitle(), bookInfo.getTitle());
		assertEquals(book.getBookInfo().getNumberOfPage(), bookInfo.getNumberOfPage());
		assertEquals(book.getBookInfo().getAuthor(), bookInfo.getAuthor());
		assertEquals(book.getBookInfo().getIsbn(), bookInfo.getIsbn());
		assertEquals(book.getStatus(), ItemStatus.Available);
	}

	@Test
	public void testCreateBookNullBookInfo() {
		String error = "";
		BookInfo bookInfo = null;
		Book book = null;
		try {
			book = libraryItemService.createBook(bookInfo);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(book);
		assertTrue(error.contains("BookInfo can't be empty."));
	}
	
	@Test
	public void testDeleteBook() {
		BookInfo bookInfo = libraryItemInfoService.createBookInfo("Title", 167, "Author", 123818);
		Book book = libraryItemService.createBook(bookInfo);
		try {
			libraryItemService.deleteBook(BOOK_KEY);
		} catch (IllegalArgumentException e) {
			fail();
		}
	}
	
	@Test 
	public void testDeleteBookInexistant () {
		String error="";
		try {
			libraryItemService.deleteBook(BOOK_BAD_KEY);
		} catch (IllegalArgumentException e) {
			error+=e.getMessage();
		}
		assertTrue(error.contains("The book with id " +  BOOK_BAD_KEY + " doesn't exist."));
	}
	
	@Test
	public void testCreateMovie() {
		MovieInfo movieInfo = null;
		String genre = "aGenre";
		String director = "aDirector";
		int length = 50;
		movieInfo = libraryItemInfoService.createMovieInfo(genre, director, length);
		Movie movie = null;
		try {
			movie = libraryItemService.createMovie(movieInfo);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(movie);
		assertEquals(movie.getMovieInfo().getGenre(), genre);
		assertEquals(movie.getMovieInfo().getDirector(), director);
		assertEquals(movie.getMovieInfo().getLength(), length);
		assertEquals(movie.getStatus(), ItemStatus.Available);
	}
	
	@Test
	public void testCreateMovieNullMovieInfo() {
		String error = "";
		MovieInfo movieInfo = null;
		Movie movie = null;
		try {
			movie = libraryItemService.createMovie(movieInfo);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(movie);
		assertTrue(error.contains("MovieInfo can't be empty."));
	}
	
	@Test
	public void testDeleteMovie() {
		MovieInfo movieInfo = libraryItemInfoService.createMovieInfo("Genre", "Director", 125);
		Movie movie = libraryItemService.createMovie(movieInfo);
		try {
			libraryItemService.deleteMovie(MOVIE_KEY);
		} catch (IllegalArgumentException e) {
			fail();
		}
	}
	
	@Test 
	public void testDeleteMovieInexistant () {
		String error="";
		try {
			libraryItemService.deleteMovie(MOVIE_BAD_KEY);
		} catch (IllegalArgumentException e) {
			error+=e.getMessage();
		}
		assertTrue(error.contains("The movie with id " +  MOVIE_BAD_KEY + " doesn't exist."));
	}
	
	@Test
	public void testCreateAlbum() {
		AlbumInfo albumInfo = null;
		String title = "aTitle";
		String composerPerformer = "aComposerPerformer";
		String genre = "aGenre";
		albumInfo = libraryItemInfoService.createAlbumInfo(title, composerPerformer, genre);
		Album album = null;
		try {
			album = libraryItemService.createAlbum(albumInfo);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(album);
		assertEquals(album.getAlbumInfo().getTitle(), title);
		assertEquals(album.getAlbumInfo().getComposerPerformer(), composerPerformer);
		assertEquals(album.getAlbumInfo().getGenre(), genre);
		assertEquals(album.getStatus(), ItemStatus.Available);
	}
	
	@Test
	public void testCreateAlbumNullAlbumInfo() {
		String error = "";
		AlbumInfo albumInfo = null;
		Album album = null;
		try {
			album = libraryItemService.createAlbum(albumInfo);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(album);
		assertTrue(error.contains("AlbumInfo can't be empty."));
	}
	
	@Test
	public void testDeleteAlbum() {
		AlbumInfo albumInfo = libraryItemInfoService.createAlbumInfo("title", "composerPerformer", "genre");
		Album album = libraryItemService.createAlbum(albumInfo);
		try {
			libraryItemService.deleteAlbum(ALBUM_KEY);
		} catch (IllegalArgumentException e) {
			fail();
		}
	}
	
	@Test 
	public void testDeleteAlbumInexistant () {
		String error="";
		try {
			libraryItemService.deleteAlbum(ALBUM_BAD_KEY);
		} catch (IllegalArgumentException e) {
			error+=e.getMessage();
		}
		assertTrue(error.contains("The album with id " +  ALBUM_BAD_KEY + " doesn't exist."));
	}
	
	@Test
	public void testCreateNewspaper() {
		NewsPaperInfo newspaperInfo = null;
		Date publicationDate = Date.valueOf("2020-12-12");
		String frequency = "aFrequency";
		int number = 50;
		newspaperInfo = libraryItemInfoService.createNewspaperInfo(publicationDate,frequency,number);
		Newspaper newspaper = null;
		try {
			newspaper = libraryItemService.createNewspaper(newspaperInfo);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(newspaper);
		assertEquals(newspaper.getNewsPaperInfo().getPublication(), publicationDate);
		assertEquals(newspaper.getNewsPaperInfo().getFrequency(), frequency);
		assertEquals(newspaper.getNewsPaperInfo().getNumber(), number);
	}
	
	@Test
	public void testCreateNewspaperNullNewsPaperInfo() {
		String error = "";
		NewsPaperInfo newspaperInfo = null;
		Newspaper newspaper = null;
		try {
			newspaper = libraryItemService.createNewspaper(newspaperInfo);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(newspaper);
		assertTrue(error.contains("NewspaperInfo can't be empty."));
	}
	
	@Test
	public void testDeleteNewsPaper() {
		NewsPaperInfo newspaperInfo = libraryItemInfoService.createNewspaperInfo(Date.valueOf("2021-12-12"), "aFrequency", 56);
		Newspaper newspaper = libraryItemService.createNewspaper(newspaperInfo);
		try {
			libraryItemService.deleteNewspaper(NEWSPAPER_KEY);
		} catch (IllegalArgumentException e) {
			fail();
		}
	}
	
	@Test 
	public void testDeleteNewspaperInexistant () {
		String error="";
		try {
			libraryItemService.deleteNewspaper(NEWSPAPER_BAD_KEY);
		} catch (IllegalArgumentException e) {
			error+=e.getMessage();
		}
		assertTrue(error.contains("The newspaper with id " +  NEWSPAPER_BAD_KEY + " doesn't exist."));
	}
	
	@Test
	public void testCreateArchive() {
		ArchiveInfo archiveInfo = null;
		String title = "aTitle";
		String description = "aDescription";
		Date publicationDate = Date.valueOf("2021-12-12");
		archiveInfo = libraryItemInfoService.createArchiveInfo(title, description, publicationDate);
		Archive archive = null;
		try {
			archive = libraryItemService.createArchive(archiveInfo);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(archive);
		assertEquals(archive.getArchiveInfo().getTitle(), title);
		assertEquals(archive.getArchiveInfo().getDescription(), description);
		assertEquals(archive.getArchiveInfo().getPublicationDate(), publicationDate);
	}
	
	@Test
	public void testCreateArchiveNullArchiveInfo() {
		String error = "";
		ArchiveInfo archiveInfo = null;
		Archive archive = null;
		try {
			archive = libraryItemService.createArchive(archiveInfo);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(archive);
		assertTrue(error.contains("archiveInfo can't be empty."));
	}
	
	@Test
	public void testDeleteArchive() {
		ArchiveInfo archiveInfo = libraryItemInfoService.createArchiveInfo("title", "description", Date.valueOf("2021-12-12"));
		Archive archive = libraryItemService.createArchive(archiveInfo);
		try {
			libraryItemService.deleteArchive(ARCHIVE_KEY);
		} catch (IllegalArgumentException e) {
			fail();
		}
	}
	
	@Test 
	public void testDeleteArchiveInexistant () {
		String error="";
		try {
			libraryItemService.deleteArchive(ARCHIVE_BAD_KEY);
		} catch (IllegalArgumentException e) {
			error+=e.getMessage();
		}
		assertTrue(error.contains("The archive with id " +  ARCHIVE_BAD_KEY + " doesn't exist."));
	}

	@Test
	public void createLoanSuccessful() {
		Member member = new Member("123 Main Street", "John Doe");
		member.activate();
		Book book = new Book(BOOK_INFO_WITH_LESS_RESERVATIONS_THAN_COPIES);
		Loan loan = libraryItemService.createLoan(book, member);
		assertNotNull(loan);
		assertEquals(book, loan.getReservableItem());
		assertEquals(member, loan.getMember());
	}

	@Test
	public void createLoanMemberNull() {
		Member member = null;
		Book book = new Book(new BookInfo());
		Exception e = assertThrows(IllegalArgumentException.class, () -> libraryItemService.createLoan(book, member));
		assertEquals("Member cannot be null.", e.getMessage());
	}

}
