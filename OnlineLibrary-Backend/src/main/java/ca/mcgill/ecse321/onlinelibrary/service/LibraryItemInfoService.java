package ca.mcgill.ecse321.onlinelibrary.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.onlinelibrary.dao.AlbumInfoRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.ArchiveInfoRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.BookInfoRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.LibraryItemInfoRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.MovieInfoRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.NewsPaperInfoRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.ReservableItemInfoRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.ReservationRepository;
import ca.mcgill.ecse321.onlinelibrary.model.AlbumInfo;
import ca.mcgill.ecse321.onlinelibrary.model.ArchiveInfo;
import ca.mcgill.ecse321.onlinelibrary.model.BookInfo;
import ca.mcgill.ecse321.onlinelibrary.model.LibraryItemInfo;
import ca.mcgill.ecse321.onlinelibrary.model.MovieInfo;
import ca.mcgill.ecse321.onlinelibrary.model.NewsPaperInfo;
import ca.mcgill.ecse321.onlinelibrary.model.Reservation;
import ca.mcgill.ecse321.onlinelibrary.model.Member;
import ca.mcgill.ecse321.onlinelibrary.model.ReservableItemInfo;

@Service
public class LibraryItemInfoService {

	@Autowired
	private BookInfoRepository bookInfoRepository;

	@Autowired
	private MovieInfoRepository movieInfoRepository;

	@Autowired
	private AlbumInfoRepository albumInfoRepository;

	@Autowired
	private ArchiveInfoRepository archiveInfoRepository;

	@Autowired
	private NewsPaperInfoRepository newsPaperInfoRepository;
	
	@Autowired
    private LibraryItemInfoRepository libraryItemInfoRespository;


	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private ReservableItemInfoRepository reservableItemInfoRepository;

	@Transactional
	public ReservableItemInfo getReservableItemInfo(int id){
		ReservableItemInfo reservableItemInfo = reservableItemInfoRepository.findReservableItemInfoById(id);
		if (reservableItemInfo == null){
			throw new IllegalArgumentException("The reservation item info with id was not found in the database.");
		}
		return reservableItemInfo;
	}

	@Transactional
	public Reservation reserveItem(Member member, ReservableItemInfo reservableItem, Date date){
		ArrayList<String> errorMessage = new ArrayList<String>();

		if (member == null){
			errorMessage.add("A member needs to be assigned to a reservation.");
		} else {
			if (member.getStatus() == Member.MemberStatus.INACTIVE){
				errorMessage.add("Member account is inactive.");
			}

			if (member.getStatus() == Member.MemberStatus.BLACKLISTED) {
				errorMessage.add("Member is blacklisted.");
			}
		}

		if (reservableItem == null){
			errorMessage.add("An item needs to be assigned to a reservation");
		}

		if (date == null){
			errorMessage.add("Date cannot be null");
		}

		if (errorMessage.size() > 0) {
			throw new IllegalArgumentException(String.join(" ", errorMessage));
		}
		Reservation reservation = new Reservation(member, reservableItem, date);
		reservationRepository.save(reservation);
		return reservation;
	}

	@Transactional
	public Reservation getReservationsByReservationId(Integer Id){
		Reservation reservation = reservationRepository.findReservationByReservationId(Id);
		if (reservation == null){
			throw new IllegalArgumentException("The reservation with id was not found in the database.");
		}
		return reservation;
	}

	@Transactional
	public List<Reservation> getReservationsByMember(Member member){
		List<Reservation> reservation = reservationRepository.findReservationByMember(member);
		if (reservation == null){
			throw new IllegalArgumentException("The reservation with member was not found in the database.");
		}
		return reservation;
	}

	@Transactional
	public List<Reservation> getReservationsByReservableItemInfo(ReservableItemInfo reservableItemInfo){
		List<Reservation> reservation = reservationRepository.findReservationByReservedItemOrderByDateAsc(reservableItemInfo);
		if (reservation == null){
			throw new IllegalArgumentException("The reservation with the reservable item info was not found in the database.");
		}
		return reservation;
	}

	@Transactional
	public void deleteReservationbyId(Integer id){
		Reservation reservationToDelete = reservationRepository.findReservationByReservationId(id);

		if (reservationToDelete == null){
			throw new IllegalArgumentException("Reservation with ID was not found.");
		}
		reservationRepository.delete(reservationToDelete);
	}

	@Transactional
	public BookInfo createBookInfo(String title, int numberOfPage, String author, long isbn) {
		checkArgumentsBook(title, numberOfPage, author, isbn);

		BookInfo bookInfo = new BookInfo();
		bookInfo.setTitle(title);
		bookInfo.setNumberOfPage(numberOfPage);
		bookInfo.setAuthor(author);
		bookInfo.setIsbn(isbn);
		bookInfoRepository.save(bookInfo);
		return bookInfo;
	}
	
	@Transactional
	public BookInfo getBookInfo(int id) {
		BookInfo bookInfo = bookInfoRepository.findBookInfoById(id);
		if (bookInfo == null) {
			throw new IllegalArgumentException("The bookInfo with id " + id + " was not found in the database.");
		}
		return bookInfo;
	}
	
	@Transactional
	public BookInfo updateBookInfo(BookInfo bookInfo, String title, int numberOfPage, String author, long isbn) {
		checkArgumentsBook(title, numberOfPage, author, isbn);
		bookInfo.setTitle(title);
		bookInfo.setNumberOfPage(numberOfPage);
		bookInfo.setAuthor(author);
		bookInfo.setIsbn(isbn);
		bookInfoRepository.save(bookInfo);
		return bookInfo;
	}

	@Transactional
	public MovieInfo createMovieInfo(String genre, String director, int length) {
		checkArgumentsMovie(genre, director, length);
		MovieInfo movieInfo = new MovieInfo();
		movieInfo.setGenre(genre);
		movieInfo.setDirector(director);
		movieInfo.setLength(length);
		movieInfoRepository.save(movieInfo);
		return movieInfo;
	}
	
	@Transactional
    public List<LibraryItemInfo> browseAllLibraryItemInfos(){

        List<LibraryItemInfo> lii = new ArrayList<LibraryItemInfo>();
        Iterable<LibraryItemInfo> iterativeItems = libraryItemInfoRespository.findAll();
        for (LibraryItemInfo item : iterativeItems){
            lii.add(item);
        }
        
        return lii;
    }
	
	@Transactional
	public MovieInfo getMovieInfo(int id) {
		MovieInfo movieInfo = movieInfoRepository.findMovieInfoById(id);
		if (movieInfo == null) {
			throw new IllegalArgumentException("The movieInfo with id " + id + " was not found in the database.");
		}
		return movieInfo;
	}
	
	@Transactional
	public MovieInfo updateMovieInfo(MovieInfo movieInfo, String genre, String director, int length) {
		checkArgumentsMovie(genre,director,length);
		movieInfo.setGenre(genre);
		movieInfo.setDirector(director);
		movieInfo.setLength(length);
		movieInfoRepository.save(movieInfo);
		return movieInfo;
	}

	@Transactional
	public NewsPaperInfo createNewspaperInfo(Date publicationDate, String frequency, int number) {
		checkArgumentsNewspaperInfo(publicationDate, frequency, number);
		NewsPaperInfo newsPaperInfo = new NewsPaperInfo();
		newsPaperInfo.setPublication(publicationDate);
		newsPaperInfo.setFrequency(frequency);
		newsPaperInfo.setNumber(number);
		newsPaperInfoRepository.save(newsPaperInfo);
		return newsPaperInfo;
	}
	
	@Transactional
	public NewsPaperInfo getNewspaperInfo(int id) {
		NewsPaperInfo newsPaperInfo = newsPaperInfoRepository.findNewsPaperInfoById(id);
		if (newsPaperInfo == null) {
			throw new IllegalArgumentException("The newsPaperInfo with id " + id + " was not found in the database.");
		}
		return newsPaperInfo;
	}
	
	@Transactional
	public NewsPaperInfo updateNewspaperInfo(NewsPaperInfo newspaperInfo, Date publicationDate, String frequency, int number) {
		checkArgumentsNewspaperInfo(publicationDate, frequency, number);
		newspaperInfo.setPublication(publicationDate);
		newspaperInfo.setFrequency(frequency);
		newspaperInfo.setNumber(number);
		newsPaperInfoRepository.save(newspaperInfo);
		return newspaperInfo;
	}

	@Transactional
	public AlbumInfo createAlbumInfo(String title, String composerPerformer, String genre) {
		checkArgumentsAlbum(title,composerPerformer,genre);
		AlbumInfo albumInfo = new AlbumInfo();
		albumInfo.setTitle(title);
		albumInfo.setComposerPerformer(composerPerformer);
		albumInfo.setGenre(genre);
		albumInfoRepository.save(albumInfo);
		return albumInfo;
	}
	
	@Transactional
	public AlbumInfo getAlbumInfo(int id) {
		AlbumInfo albumInfo = albumInfoRepository.findAlbumInfoById(id);
		if (albumInfo == null) {
			throw new IllegalArgumentException("The albumInfo with id " + id + " was not found in the database.");
		}
		return albumInfo;
	}
	
	@Transactional
	public AlbumInfo updateAlbumInfo(AlbumInfo albumInfo, String title, String composerPerformer, String genre) {
		checkArgumentsAlbum(title, composerPerformer, genre);
		albumInfo.setTitle(title);
		albumInfo.setComposerPerformer(composerPerformer);
		albumInfo.setGenre(genre);
		albumInfoRepository.save(albumInfo);
		return albumInfo;
	}

	@Transactional
	public ArchiveInfo createArchiveInfo(String title, String description, Date publicationDate) {
		checkArgumentsArchive(title,description,publicationDate);
		ArchiveInfo archiveInfo = new ArchiveInfo();
		archiveInfo.setTitle(title);
		archiveInfo.setDescription(description);
		archiveInfo.setPublicationDate(publicationDate);
		archiveInfoRepository.save(archiveInfo);
		return archiveInfo;
	}
	
	@Transactional
	public ArchiveInfo getArchiveInfo(int id) {
		ArchiveInfo archiveInfo = archiveInfoRepository.findArchiveInfoById(id);
		if (archiveInfo == null) {
			throw new IllegalArgumentException("The archiveInfo with id " + id + " was not found in the database.");
		}
		return archiveInfo;
	}
	
	@Transactional
	public ArchiveInfo updateArchiveInfo(ArchiveInfo archiveInfo, String title, String description, Date publicationDate) {
		checkArgumentsArchive(title,description,publicationDate);
		archiveInfo.setTitle(title);
		archiveInfo.setDescription(description);
		archiveInfo.setPublicationDate(publicationDate);
		archiveInfoRepository.save(archiveInfo);
		return archiveInfo;
	}
	
	private void checkArgumentsBook(String title, int numberOfPage, String author, long isbn) {
		ArrayList<String> errorMessage = new ArrayList<String>();
		if (title == null || title.trim().length() == 0) {
			errorMessage.add("Title can't be empty.");
		}

		if (numberOfPage == 0) {
			errorMessage.add("Number of page can't be 0.");
		}

		if (author == null || author.trim().length() == 0) {
			errorMessage.add("Author can't be empty.");
		}

		if (errorMessage.size() > 0) {
			throw new IllegalArgumentException(String.join(" ", errorMessage));
		}
	}
	
	private void checkArgumentsMovie(String genre, String director, int length) {
		ArrayList<String> errorMessage = new ArrayList<String>();
		if (genre == null || genre.trim().length() == 0) {
			errorMessage.add("Genre can't be empty.");
		}

		if (director == null || director.trim().length() == 0) {
			errorMessage.add("Director can't be empty.");
		}

		if (length == 0) {
			errorMessage.add("Length can't be 0.");
		}
		if (errorMessage.size() > 0) {
			throw new IllegalArgumentException(String.join(" ", errorMessage));
		}
	}
	
	private void checkArgumentsAlbum(String title, String composerPerformer, String genre) {
		ArrayList<String> errorMessage = new ArrayList<String>();

		if (title == null || title.trim().length() == 0) {
			errorMessage.add("Title can't be empty.");
		}

		if (composerPerformer == null || composerPerformer.trim().length() == 0) {
			errorMessage.add("composerPerformer can't be empty.");
		}

		if (genre == null || genre.trim().length() == 0) {
			errorMessage.add("Genre can't be empty.");
		}

		if (errorMessage.size() > 0) {
			throw new IllegalArgumentException(String.join(" ", errorMessage));
		}
	}
	
	private void checkArgumentsNewspaperInfo(Date publicationDate, String frequency, int number) {
		ArrayList<String> errorMessage = new ArrayList<String>();

		if (publicationDate == null) {
			errorMessage.add("Date can't be empty.");
		}

		if (frequency == null || frequency.trim().length() == 0) {
			errorMessage.add("Frequency can't be empty.");
		}

		if (number < 0) {
			errorMessage.add("Number can't be negative.");
		}

		if (errorMessage.size() > 0) {
			throw new IllegalArgumentException(String.join(" ", errorMessage));
		}
	}
	
	private void checkArgumentsArchive(String title, String description, Date publicationDate) {
		ArrayList<String> errorMessage = new ArrayList<String>();
		if (title == null || title.trim().length() == 0) {
			errorMessage.add("Title can't be empty.");
		}

		if (description == null) {
			errorMessage.add("Description can't be empty.");
		}

		if (publicationDate == null) {
			errorMessage.add("Publication date can't be empty.");
		}

		if (errorMessage.size() > 0) {
			throw new IllegalArgumentException(String.join(" ", errorMessage));
		}
	}
}
