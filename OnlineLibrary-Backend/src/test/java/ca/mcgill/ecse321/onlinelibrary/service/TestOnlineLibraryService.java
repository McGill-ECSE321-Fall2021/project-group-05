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
	private ArchiveInfoRepository archiveInfoDao;
	
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
		lenient().when(archiveInfoDao.save(any(ArchiveInfo.class))).thenAnswer(returnParameterAsAnswer);
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
	
	public void testCreateArchiveInfo() {
		String title = "Title";
		String description = "Description";
		Date publicationDate = Date.valueOf("2021-10-31");
		ArchiveInfo archiveInfo = null;
		try {
			archiveInfo = service.createArchiveInfo(title, description, publicationDate);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(archiveInfo);
		assertEquals(archiveInfo.getTitle(), title);
		assertEquals(archiveInfo.getDescription(), description);
		assertEquals(archiveInfo.getPublicationDate(), publicationDate);
	}
	
	@Test
	public void testCreateArchiveInfoTitleIsNull() {
		String error="";
		String title = null;
		String description = "Description";
		Date publicationDate = Date.valueOf("2021-10-31");
		ArchiveInfo archiveInfo = null;
		try {
			archiveInfo = service.createArchiveInfo(title, description, publicationDate);
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
		Date publicationDate = Date.valueOf("2021-10-31");
		ArchiveInfo archiveInfo = null;
		try {
			archiveInfo = service.createArchiveInfo(title, description, publicationDate);
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
		Date publicationDate = Date.valueOf("2021-10-31");
		ArchiveInfo archiveInfo = null;
		try {
			archiveInfo = service.createArchiveInfo(title, description, publicationDate);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(archiveInfo);
		assertTrue(error.contains("Description can't be empty."));
	}
	
	@Test
	public void testCreateArchiveInfoDescriptionIsEmpty() {
		String error="";
		String title = "Title";
		String description =" ";
		Date publicationDate = Date.valueOf("2021-10-31");
		ArchiveInfo archiveInfo = null;
		try {
			archiveInfo = service.createArchiveInfo(title, description, publicationDate);
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
		Date publicationDate = null;
		ArchiveInfo archiveInfo = null;
		try {
			archiveInfo = service.createArchiveInfo(title, description, publicationDate);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(archiveInfo);
		assertTrue(error.contains("Publication date can't be empty."));
	}
	
	public void testCreateArchiveInfoAllEmpty() {
		String error="";
		String title = " ";
		String description = null;
		Date publicationDate = null;
		ArchiveInfo archiveInfo = null;
		try {
			archiveInfo = service.createArchiveInfo(title, description, publicationDate);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(archiveInfo);
		assertTrue(error.contains("Title can't be empty."));
		assertTrue(error.contains("Description can't be empty."));
		assertTrue(error.contains("Publication date can't be empty."));
	}
}




