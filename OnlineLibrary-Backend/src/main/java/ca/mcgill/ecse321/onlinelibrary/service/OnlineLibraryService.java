package ca.mcgill.ecse321.onlinelibrary.service;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ca.mcgill.ecse321.onlinelibrary.dao.*;
import ca.mcgill.ecse321.onlinelibrary.model.*;
import ca.mcgill.ecse321.onlinelibrary.model.ReservableItem.ItemStatus;


@Service
public class OnlineLibraryService {

	@Autowired
	BookInfoRepository bookInfoRepository;
	@Autowired
	MovieInfoRepository movieInfoRepository;
	@Autowired
	BookRepository bookRepository;
	@Autowired
	AlbumInfoRepository albumInfoRepository;
	@Autowired
	ArchiveInfoRepository archiveInfoRepository;
	@Autowired
	NewsPaperInfoRepository newsPaperInfoRepository;
	@Autowired
	LibrarianRepository librarianRepository;

	@Transactional
	public BookInfo createBookInfo(String title, int numberOfPage, String author, long isbn) {
		ArrayList<String> errorMessage = new ArrayList<String>();
		int errorCount=0;
		if (title == null || title.trim().length() == 0) {
			errorMessage.add("Title can't be empty.");
			errorCount++;
		}

		if (numberOfPage == 0) {
			errorMessage.add("Number of page can't be 0.");
			errorCount++;
		}

		if (author == null || author.trim().length() == 0) {
			errorMessage.add("Author can't be empty.");
			errorCount++;
		}

		if (errorCount > 0) {
			throw new IllegalArgumentException(String.join(" ", errorMessage));
		}

		BookInfo bookInfo = new BookInfo();
		bookInfo.setTitle(title);
		bookInfo.setNumberOfPage(numberOfPage);
		bookInfo.setAuthor(author);
		bookInfo.setIsbn(isbn);
		bookInfoRepository.save(bookInfo);
		return bookInfo;
	}

	@Transactional
	public MovieInfo createMovieInfo(String genre, String director, int length) {
		ArrayList<String> errorMessage = new ArrayList<String>();
		int errorCount=0;
		if (genre == null || genre.trim().length() == 0) {
			errorMessage.add("Genre can't be empty.");
			errorCount++;
		}

		if (director == null || director.trim().length() == 0) {
			errorMessage.add("Director can't be empty.");
			errorCount++;
		}

		if (length == 0) {
			errorMessage.add("Length can't be 0.");
			errorCount++;
		}
		if (errorCount > 0) {
			throw new IllegalArgumentException(String.join(" ", errorMessage));
		}

		MovieInfo movieInfo = new MovieInfo();
		movieInfo.setGenre(genre);
		movieInfo.setDirector(director);
		movieInfo.setLength(length);
		movieInfoRepository.save(movieInfo);
		return movieInfo;
	}

	@Transactional
	public Book createBook(BookInfo bookInfo) {
		ArrayList<String> errorMessage = new ArrayList<String>();
		int errorCount=0;
		if (bookInfo == null) {
			errorMessage.add("BookInfo can't be empty");
			errorCount++;
		}
		if (errorCount > 0) {
			throw new IllegalArgumentException(String.join(" ", errorMessage));
		}

		Book book = new Book(bookInfo);
		book.setStatus(ItemStatus.Available);
		bookRepository.save(book);
		return book;
	}

	@Transactional
	public BookInfo getBookInfo(int id) {
		if (id == 0) {
			throw new IllegalArgumentException("BookInfo id can't be 0.");
		}
		BookInfo bookInfo = bookInfoRepository.findBookInfoById(id);
		if (bookInfo == null) {
			throw new IllegalArgumentException("The bookInfo with id " + id + " was not found in the database.");
		}
		return bookInfo;
	}

	@Transactional
	public NewsPaperInfo createNewsPaperInfo(Date publicationDate, String frequency, int number) {
		ArrayList<String> errorMessage = new ArrayList<String>();
		int errorCount=0;

		if (publicationDate == null) {
			errorMessage.add("Date can't be empty.");
			errorCount++;
		}

		if (frequency == null || frequency.trim().length() == 0) {
			errorMessage.add("Frequency can't be empty.");
			errorCount++;
		}

		if (number < 0) {
			errorMessage.add("Number can't be negative.");
			errorCount++;
		}

		if (errorCount > 0) {
			throw new IllegalArgumentException(String.join(" ", errorMessage));
		}

		NewsPaperInfo newsPaperInfo = new NewsPaperInfo();
		newsPaperInfo.setPublication(publicationDate);
		newsPaperInfo.setFrequency(frequency);
		newsPaperInfo.setNumber(number);
		newsPaperInfoRepository.save(newsPaperInfo);
		return newsPaperInfo;
	}

	@Transactional
	public AlbumInfo createAlbumInfo(String title, String composerPerformer, String genre) {
		ArrayList<String> errorMessage = new ArrayList<String>();
		int errorCount=0;

		if (title == null || title.trim().length() == 0) {
			errorMessage.add("Title can't be empty.");
			errorCount++;
		}

		if (composerPerformer == null || composerPerformer.trim().length() == 0) {
			errorMessage.add("composerPerformer can't be empty.");
			errorCount++;
		}

		if (genre == null || genre.trim().length() == 0) {
			errorMessage.add("Genre can't be empty.");
			errorCount++;
		}

		if (errorCount > 0) {
			throw new IllegalArgumentException(String.join(" ", errorMessage));
		}

		AlbumInfo albumInfo = new AlbumInfo();
		albumInfo.setTitle(title);
		albumInfo.setComposerPerformer(composerPerformer);
		albumInfo.setGenre(genre);
		albumInfoRepository.save(albumInfo);
		return albumInfo;
	}

	@Transactional
	public ArchiveInfo createArchiveInfo(String title, String description, Date publicationDate) {
		ArrayList<String> errorMessage = new ArrayList<String>();
		int errorCount=0;
		if (title == null || title.trim().length() == 0) {
			errorMessage.add("Title can't be empty.");
			errorCount++;
		}

		if (description == null) {
			errorMessage.add("Description can't be empty.");
			errorCount++;
		}

		if (publicationDate == null) {
			errorMessage.add("Publication date can't be empty.");
			errorCount++;
		}

		if (errorCount > 0) {
			throw new IllegalArgumentException(String.join(" ", errorMessage));
		}

		ArchiveInfo archiveInfo = new ArchiveInfo();
		archiveInfo.setTitle(title);
		archiveInfo.setDescription(description);
		archiveInfo.setPublicationDate(publicationDate);
		archiveInfoRepository.save(archiveInfo);
		return archiveInfo;
	}

	@Transactional
	public Librarian createLibrarian(String fullName, String username, String password) {
		ArrayList<String> errorMessage = new ArrayList<String>();
		// Full name not empty
		if (fullName == null || fullName.trim().length() == 0) {
			errorMessage.add("Full name cannot be empty.");
		}
		// Username not empty and unique
		if (username == null) {
			errorMessage.add("Username cannot be empty");
		}
		else {
			username = username.trim();
			if (username.length() == 0) {
				errorMessage.add("Username cannot be empty");
			}
			if (librarianRepository.hasWithUsername(username)) {
				errorMessage.add("Username already taken.");
			}
		}
		// Password at least 8 characters
		// TODO Move this to configuration file
		int MIN_PASS_LENGTH = 8;
		if (password == null) {
			errorMessage.add("Password must be at least " + MIN_PASS_LENGTH + " characters in length.");
		}
		else {
			password = password.trim();
			if (password.length() < MIN_PASS_LENGTH) {
				errorMessage.add("Password must be at least " + MIN_PASS_LENGTH + " characters in length.");
			}
		}
		// Throw exception
		if (errorMessage.size() > 0) {
			throw new IllegalArgumentException(String.join(" ", errorMessage));
		}

		// TODO Hash password before saving

		Librarian librarian = new Librarian(fullName, username, password, false);
		librarianRepository.save(librarian);
		return librarian;
	}
}
