package ca.mcgill.ecse321.onlinelibrary.dao;

import ca.mcgill.ecse321.onlinelibrary.model.Book;
import ca.mcgill.ecse321.onlinelibrary.model.BookInfo;
import ca.mcgill.ecse321.onlinelibrary.model.ReservableItem.ItemStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
	private BookInfoRepository bookinfoRepository;
	@Autowired 
	private MovieInfoRepository movieInfoRepository;
	@Autowired
	private AlbumInfoRepository albumInfoRepository;
	@Autowired
	private NewsPaperInfoRepository newsPaperInfoRepository;
	@Autowired
	private ArchiveInfoRepository archiveInfoRepository;

	@AfterEach
	public void clearDatabase() {
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
	public void testPersistBookInfo(){
		BookInfo bookinfo = new BookInfo();
		bookinfoRepository.save(bookinfo);
		int isbn = bookinfo.getIsbn();
		bookinfo = null;
		bookinfo = bookinfoRepository.findBookInfoByIsbn(isbn);
		assertNotNull(bookinfo);
		assertEquals(isbn, bookinfo.getIsbn());
	}

	@Test
	public void testPersistMovieInfo(){
		MovieInfo movieInfo = new MovieInfo();
		movieInfoRepository.save(movieInfo);
		int id = movieInfo.getId();
		movieInfo = null;
		movieInfo = movieInfoRepository.findMovieInfoById(id);
		assertNotNull(movieInfo);
		assertEquals(id, movieInfo.getId());
	}

	@Test
	public void testPersistAlbumInfo(){
		AlbumInfo albumInfo = new AlbumInfo();
		albumInfoRepository.save(albumInfo);
		int id = albumInfo.getId();
		albumInfo = null;
		albumInfo = albumInfoRepository.findAlbumInfoById(id);
		assertNotNull(albumInfo);
		assertEquals(id, albumInfo.getId());
	}

	@Test
	public void testPersistNewsPaperInfo(){
		NewsPaperInfo newsPaperInfo = new NewsPaperInfo();
		newsPaperInfoRepository.save(newsPaperInfo);
		int id = newsPaperInfo.getId();
		newsPaperInfo = null;
		newsPaperInfo = newsPaperInfoRepository.findNewsPaperInfoById(id);
		assertNotNull(newsPaperInfo);
		assertEquals(id, newsPaperInfo.getId());
	}

	@Test
	public void testPersistArchiveInfo(){
		ArchiveInfo archiveInfo = new ArchiveInfo();
		archiveInfoRepository.save(archiveInfo);
		int id = archiveInfo.getId();
		archiveInfo = null;
		archiveInfo = archiveInfoRepository.findArchiveInfoById(id);
		assertNotNull(archiveInfo);
		assertEquals(id, archiveInfo.getId());
	}
}