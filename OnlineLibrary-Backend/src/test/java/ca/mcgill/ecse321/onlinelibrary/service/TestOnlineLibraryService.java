package ca.mcgill.ecse321.onlinelibrary.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.onlinelibrary.dao.BookInfoRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.*;
import ca.mcgill.ecse321.onlinelibrary.model.*;
import ca.mcgill.ecse321.onlinelibrary.model.ReservableItem.ItemStatus;


@ExtendWith(MockitoExtension.class)
public class TestOnlineLibraryService {
	@Mock
	private BookInfoRepository bookInfoDao;

	@Mock
	private BookRepository bookDao;
	
	@Mock
	private MovieInfoRepository movieInfoDao;
	
	@Mock 
	private NewsPaperInfoRepository newsPaperInfoDao;
	
	@InjectMocks
	private OnlineLibraryService service;
	
	private static final int BOOK_INFO_KEY = 1;
	private static final int BOOK_INFO_NOT_A_KEY = 2;
	
	@BeforeEach
	public void setMockOuput() {
		lenient().when(bookInfoDao.findBookInfoById(any(Integer.class))).thenAnswer( (InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(BOOK_INFO_KEY)) {
				BookInfo bookInfo = new BookInfo();
				bookInfo.setId(BOOK_INFO_KEY);
				return bookInfo;
			} else {
				return null;
			}
		});
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(bookInfoDao.save(any(BookInfo.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(movieInfoDao.save(any(MovieInfo.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(bookDao.save(any(Book.class))).then(returnParameterAsAnswer);
		lenient().when(newsPaperInfoDao.save(any(NewsPaperInfo.class))).then(returnParameterAsAnswer);
	}
	
	@Test
	public void testCreateBookInfo() {
		String title = "Title";
		Integer numberOfPage = 10;
		String author = "Author";
		long isbn = 1;
		BookInfo bookInfo = null;
		try {
			bookInfo = service.createBookInfo(title, numberOfPage, author, isbn);
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
			bookInfo = service.createBookInfo(title, numberOfPage, author, isbn);
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
			bookInfo = service.createBookInfo(title, numberOfPage, author, isbn);
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
			bookInfo = service.createBookInfo(title, numberOfPage, author, isbn);
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
			bookInfo = service.createBookInfo(title, numberOfPage, author, isbn);
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
			bookInfo = service.createBookInfo(title, numberOfPage, author, isbn);
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
			bookInfo = service.createBookInfo(title, numberOfPage, author, isbn);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(bookInfo);
		assertTrue(error.contains("Title can't be empty."));
		assertTrue(error.contains("Number of page can't be 0."));
		assertTrue(error.contains("Author can't be empty."));
	}
	
	@Test
	public void testCreateMovieInfo() {
		String genre = "Horror";
		String director = "Author";
		int length = 100;
		MovieInfo movieInfo = null;
		try {
			movieInfo = service.createMovieInfo(genre, director, length);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(movieInfo);
		assertEquals(movieInfo.getGenre(), genre);
		assertEquals(movieInfo.getDirector(), director);
		assertEquals(movieInfo.getLength(), length);
	}
	
	@Test
	public void testCreateMovieInfoGenreNull() {
		String error="";
		String genre = null;
		String director = "Author";
		int length = 100;
		MovieInfo movieInfo = null;
		try {
			movieInfo = service.createMovieInfo(genre, director, length);
		} catch (IllegalArgumentException e) {
			error=e.getMessage();
		}
		assertNull(movieInfo);
		assertTrue(error.contains("Genre can't be empty."));
	}
	
	@Test
	public void testCreateMovieInfoGenreEmpty() {
		String error="";
		String genre = "   ";
		String director = "Author";
		int length = 100;
		MovieInfo movieInfo = null;
		try {
			movieInfo = service.createMovieInfo(genre, director, length);
		} catch (IllegalArgumentException e) {
			error=e.getMessage();
		}
		assertNull(movieInfo);
		assertTrue(error.contains("Genre can't be empty."));
	}
	
	@Test
	public void testCreateMovieInfoDirectorNull() {
		String error="";
		String genre = "aGenre";
		String director = null;
		int length = 100;
		MovieInfo movieInfo = null;
		try {
			movieInfo = service.createMovieInfo(genre, director, length);
		} catch (IllegalArgumentException e) {
			error=e.getMessage();
		}
		assertNull(movieInfo);
		assertTrue(error.contains("Director can't be empty."));
	}
	
	@Test
	public void testCreateMovieInfoDirectorEmpty() {
		String error="";
		String genre = "aGenre";
		String director = "  ";
		int length = 100;
		MovieInfo movieInfo = null;
		try {
			movieInfo = service.createMovieInfo(genre, director, length);
		} catch (IllegalArgumentException e) {
			error=e.getMessage();
		}
		assertNull(movieInfo);
		assertTrue(error.contains("Director can't be empty."));
	}
	
	@Test
	public void testCreateMovieInfoLength0() {
		String error="";
		String genre = "aGenre";
		String director = "Director";
		int length = 0;
		MovieInfo movieInfo = null;
		try {
			movieInfo = service.createMovieInfo(genre, director, length);
		} catch (IllegalArgumentException e) {
			error=e.getMessage();
		}
		assertNull(movieInfo);
		assertTrue(error.contains("Length can't be 0."));
	}
	
	@Test 
	public void testCreateMovieAllEmpty() {
		String error="";
		String genre = "";
		String director = "";
		int length = 0;
		MovieInfo movieInfo = null;
		try {
			movieInfo = service.createMovieInfo(genre, director, length);
		} catch (IllegalArgumentException e) {
			error=e.getMessage();
		}
		assertNull(movieInfo);
		assertTrue(error.contains("Genre can't be empty."));
		assertTrue(error.contains("Director can't be empty."));
		assertTrue(error.contains("Length can't be 0."));
	}
	@Test
	public void testCreateBook() {
		BookInfo bookInfo = null;
		String title = "Title";
		int numberOfPage = 10;
		String author = "Author";
		int isbn = 10;
		bookInfo = service.createBookInfo(title, numberOfPage, author, isbn);
		Book book = null;
		try {
			book = service.createBook(bookInfo);
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
		String error="";
		BookInfo bookInfo = null;
		Book book = null;
		try {
			book = service.createBook(bookInfo);
		}
		catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(book);
		assertTrue(error.contains("BookInfo can't be empty"));
	}
	@Test
	public void testGetBookInfo() {
		BookInfo bookInfo = null;
		try {
			bookInfo = service.getBookInfo(BOOK_INFO_KEY);
		} catch (IllegalArgumentException e){
			fail();
		}
		assertNotNull(bookInfo);
		assertEquals(BOOK_INFO_KEY, bookInfo.getId());
	}
	
	@Test 
	public void testGetBookInfoIdIs0 () {
		String error = "";
		BookInfo bookInfo = null;
		try {
			bookInfo = service.getBookInfo(0);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(bookInfo);
		assertTrue(error.contains("BookInfo id can't be 0."));
	}
	
	@Test 
	public void testGetBookBadId() {
		String error = "";
		BookInfo bookInfo = null;
		try {
			bookInfo = service.getBookInfo(BOOK_INFO_NOT_A_KEY);
		} catch (IllegalArgumentException e) {
				error+=e.getMessage();
		}
		assertNull(bookInfo);
		assertTrue(error.contains("The bookInfo with id " + BOOK_INFO_NOT_A_KEY + " was not found in the database."));
	}
	
	@Test
	public void testCreateNewsPaperInfo() {
		Date publication = Date.valueOf("2021-10-31");
		String frequency = "Frequency";
		int number = 123;
		NewsPaperInfo newsPaperInfo = null;
		try {
			newsPaperInfo = service.createNewsPaperInfo(publication, frequency, number);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(newsPaperInfo);
		assertEquals(newsPaperInfo.getPublication().getTime(), publication.getTime());
		assertEquals(newsPaperInfo.getFrequency(), frequency);
		assertEquals(newsPaperInfo.getNumber(), number);
	}
	
	@Test
	public void testCreateNewsPaperInfoPublicationIsNull() {
		String error="";
		Date publication = null;
		String frequency = "Frequency";
		int number = 5;
		NewsPaperInfo newsPaperInfo = null;
		try {
			newsPaperInfo = service.createNewsPaperInfo(publication, frequency, number);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(newsPaperInfo);
		assertTrue(error.contains("Date can't be empty."));
	}
	
	@Test
	public void testCreateNewsPaperInfoFrequencyIsNull() {
		String error="";
		Date publication = Date.valueOf("2021-10-31");
		String frequency = null;
		int number = 5;
		NewsPaperInfo newsPaperInfo = null;
		try {
			newsPaperInfo = service.createNewsPaperInfo(publication, frequency, number);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(newsPaperInfo);
		assertTrue(error.contains("Frequency can't be empty."));
	}
	
	@Test
	public void testCreateNewsPaperInfoFrequencyIsEmpty() {
		String error="";
		Date publication = Date.valueOf("2021-10-31");
		String frequency = " ";
		int number = 5;
		NewsPaperInfo newsPaperInfo = null;
		try {
			newsPaperInfo = service.createNewsPaperInfo(publication, frequency, number);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(newsPaperInfo);
		assertTrue(error.contains("Frequency can't be empty."));
	}
	
	@Test
	public void testCreateNewsPaperInfoNumberIsNegative() {
		String error="";
		Date publication = Date.valueOf("2021-10-31");
		String frequency = "Everyday";
		int number = -1;
		NewsPaperInfo newsPaperInfo = null;
		try {
			newsPaperInfo = service.createNewsPaperInfo(publication, frequency, number);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(newsPaperInfo);
		assertTrue(error.contains("Number can't be negative."));
	}
	
	@Test
	public void testCreateNewsPaperInfoAllEmpty() {
		String error="";
		Date publication = null;
		String frequency = " ";
		int number = -1;
		NewsPaperInfo newsPaperInfo = null;
		try {
			newsPaperInfo = service.createNewsPaperInfo(publication, frequency, number);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(newsPaperInfo);
		assertTrue(error.contains("Number can't be negative."));
		assertTrue(error.contains("Frequency can't be empty."));
		assertTrue(error.contains("Date can't be empty."));
	}
}




