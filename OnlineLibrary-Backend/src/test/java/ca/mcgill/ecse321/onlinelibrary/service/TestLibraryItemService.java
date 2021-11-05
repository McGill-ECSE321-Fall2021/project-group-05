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
import ca.mcgill.ecse321.onlinelibrary.dao.AlbumRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.ArchiveInfoRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.ArchiveRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.BookInfoRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.BookRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.MovieInfoRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.MovieRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.NewsPaperInfoRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.NewspaperRepository;
import ca.mcgill.ecse321.onlinelibrary.model.Album;
import ca.mcgill.ecse321.onlinelibrary.model.AlbumInfo;
import ca.mcgill.ecse321.onlinelibrary.model.Archive;
import ca.mcgill.ecse321.onlinelibrary.model.ArchiveInfo;
import ca.mcgill.ecse321.onlinelibrary.model.Book;
import ca.mcgill.ecse321.onlinelibrary.model.BookInfo;
import ca.mcgill.ecse321.onlinelibrary.model.Movie;
import ca.mcgill.ecse321.onlinelibrary.model.MovieInfo;
import ca.mcgill.ecse321.onlinelibrary.model.NewsPaperInfo;
import ca.mcgill.ecse321.onlinelibrary.model.Newspaper;
import ca.mcgill.ecse321.onlinelibrary.model.ReservableItem.ItemStatus;

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

	@InjectMocks
	private LibraryItemInfoService libraryItemInfoService;
	@InjectMocks
	private LibraryItemService libraryItemService;

	@BeforeEach
	public void setMockOuput() {
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(bookDao.save(any(Book.class))).then(returnParameterAsAnswer);
		lenient().when(bookInfoDao.save(any(BookInfo.class))).then(returnParameterAsAnswer);
		lenient().when(movieDao.save(any(Movie.class))).then(returnParameterAsAnswer);
		lenient().when(movieInfoDao.save(any(MovieInfo.class))).then(returnParameterAsAnswer);
		lenient().when(albumDao.save(any(Album.class))).then(returnParameterAsAnswer);
		lenient().when(albumInfoDao.save(any(AlbumInfo.class))).then(returnParameterAsAnswer);
		lenient().when(newspaperDao.save(any(Newspaper.class))).then(returnParameterAsAnswer);
		lenient().when(newspaperInfoDao.save(any(NewsPaperInfo.class))).then(returnParameterAsAnswer);
		lenient().when(archiveDao.save(any(Archive.class))).then(returnParameterAsAnswer);
		lenient().when(archiveInfoDao.save(any(ArchiveInfo.class))).then(returnParameterAsAnswer);
		
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
		assertTrue(error.contains("BookInfo can't be empty"));
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
		assertTrue(error.contains("MovieInfo can't be empty"));
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
		assertTrue(error.contains("AlbumInfo can't be empty"));
	}
	
	@Test
	public void testCreateNewsPaper() {
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
	public void testCreateNewsPaperNullNewsPaperInfo() {
		String error = "";
		NewsPaperInfo newspaperInfo = null;
		Newspaper newspaper = null;
		try {
			newspaper = libraryItemService.createNewspaper(newspaperInfo);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(newspaper);
		assertTrue(error.contains("NewspaperInfo can't be empty"));
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
		assertEquals(archive.getArchiveInfo().getTitle(),title);
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
		assertTrue(error.contains("archiveInfo can't be empty"));
	}
}
