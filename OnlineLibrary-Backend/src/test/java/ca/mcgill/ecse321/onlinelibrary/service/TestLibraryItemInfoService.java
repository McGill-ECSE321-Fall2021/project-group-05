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

import ca.mcgill.ecse321.onlinelibrary.dao.AlbumInfoRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.ArchiveInfoRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.BookInfoRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.MovieInfoRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.NewsPaperInfoRepository;
import ca.mcgill.ecse321.onlinelibrary.model.AlbumInfo;
import ca.mcgill.ecse321.onlinelibrary.model.ArchiveInfo;
import ca.mcgill.ecse321.onlinelibrary.model.BookInfo;
import ca.mcgill.ecse321.onlinelibrary.model.MovieInfo;
import ca.mcgill.ecse321.onlinelibrary.model.NewsPaperInfo;

@ExtendWith(MockitoExtension.class)
public class TestLibraryItemInfoService {
	@Mock
	private BookInfoRepository bookInfoDao;
	@Mock
	private MovieInfoRepository movieInfoDao;
	@Mock
	private AlbumInfoRepository albumInfoDao;
	@Mock
	private NewsPaperInfoRepository newspaperInfoDao;
	@Mock
	private ArchiveInfoRepository archiveInfoDao;

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
		lenient().when(newspaperInfoDao.findNewsPaperInfoById(any(Integer.class))).thenAnswer( (InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(NEWSPAPER_INFO_KEY)) {
				NewsPaperInfo newspaperInfo = new NewsPaperInfo();
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
	
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(bookInfoDao.save(any(BookInfo.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(movieInfoDao.save(any(MovieInfo.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(albumInfoDao.save(any(AlbumInfo.class))).then(returnParameterAsAnswer);
		lenient().when(newspaperInfoDao.save(any(NewsPaperInfo.class))).then(returnParameterAsAnswer);
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
		String genre = "Horror";
		String director = "Author";
		int length = 100;
		MovieInfo movieInfo = null;
		try {
			movieInfo = libraryItemInfoService.createMovieInfo(genre, director, length);
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
			movieInfo = libraryItemInfoService.createMovieInfo(genre, director, length);
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
			movieInfo = libraryItemInfoService.createMovieInfo(genre, director, length);
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
			movieInfo = libraryItemInfoService.createMovieInfo(genre, director, length);
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
			movieInfo = libraryItemInfoService.createMovieInfo(genre, director, length);
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
			movieInfo = libraryItemInfoService.createMovieInfo(genre, director, length);
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
			movieInfo = libraryItemInfoService.createMovieInfo(genre, director, length);
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
		MovieInfo movieInfo = libraryItemInfoService.createMovieInfo("AGenre", "aDirector", 123);
		String newGenre = "Genre2";
		String newDirector = "Director2";
		int length = 321;
		try {
			movieInfo = libraryItemInfoService.updateMovieInfo(movieInfo, newGenre, newDirector, length);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(movieInfo);
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
	public void testCreateNewsPaperInfo() {
		Date publication = Date.valueOf("2021-10-31");
		String frequency = "Frequency";
		int number = 123;
		NewsPaperInfo newsPaperInfo = null;
		try {
			newsPaperInfo = libraryItemInfoService.createNewspaperInfo(publication, frequency, number);
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
			newsPaperInfo = libraryItemInfoService.createNewspaperInfo(publication, frequency, number);
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
			newsPaperInfo = libraryItemInfoService.createNewspaperInfo(publication, frequency, number);
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
			newsPaperInfo = libraryItemInfoService.createNewspaperInfo(publication, frequency, number);
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
			newsPaperInfo = libraryItemInfoService.createNewspaperInfo(publication, frequency, number);
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
			newsPaperInfo = libraryItemInfoService.createNewspaperInfo(publication, frequency, number);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(newsPaperInfo);
		assertTrue(error.contains("Number can't be negative."));
		assertTrue(error.contains("Frequency can't be empty."));
		assertTrue(error.contains("Date can't be empty."));
	}
	
	@Test
	public void testGetNewsPaperInfo() {
		NewsPaperInfo newspaperInfo = null;
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
		NewsPaperInfo newspaperInfo = null;
		try {
			newspaperInfo = libraryItemInfoService.getNewspaperInfo(NEWSPAPER_INFO_BAD_KEY);
		} catch (IllegalArgumentException e) {
			error += e.getMessage();
		}
		assertNull(newspaperInfo);
		assertTrue(error.contains("The newsPaperInfo with id " + NEWSPAPER_INFO_BAD_KEY + " was not found in the database."));
	}
	
	@Test
	public void testUpdateNewspaperInfo() {
		NewsPaperInfo newspaper = libraryItemInfoService.createNewspaperInfo(Date.valueOf("2020-11-11"), "AFrequency", 123);
		Date newPublicationDate = Date.valueOf("2021-11-11");
		String newFrequency = "Frequency2";
		int newNumber = 321;
		NewsPaperInfo newspaperInfo = null;
		try {
			newspaperInfo = libraryItemInfoService.updateNewspaperInfo(newspaper, newPublicationDate, newFrequency, newNumber);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(newspaperInfo);
		assertEquals(newspaperInfo.getPublication(), newPublicationDate);
		assertEquals(newspaperInfo.getFrequency(), newFrequency);
		assertEquals(newspaperInfo.getNumber(), newNumber);
	}

	@Test
	public void testCreateArchiveInfo() {
		String title = "Title";
		String description = "Description";
		Date publicationDate = Date.valueOf("2021-10-31");
		ArchiveInfo archiveInfo = null;
		try {
			archiveInfo = libraryItemInfoService.createArchiveInfo(title, description, publicationDate);
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
		Date publicationDate = Date.valueOf("2021-10-31");
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
		Date publicationDate = Date.valueOf("2021-10-31");
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
		Date publicationDate = null;
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
		Date publicationDate = null;
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
		ArchiveInfo archiveInfo = libraryItemInfoService.createArchiveInfo("ATitle", "aDescription", Date.valueOf("2020-11-11"));
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
}
