package ca.mcgill.ecse321.onlinelibrary.service;

import ca.mcgill.ecse321.onlinelibrary.dao.*;
import ca.mcgill.ecse321.onlinelibrary.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
	private NewspaperInfoRepository newspaperInfoRepository;

	@Autowired
	private LibraryItemInfoRepository libraryItemInfoRespository;

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private ReservableItemInfoRepository reservableItemInfoRepository;

	@Transactional
	public LibraryItemInfo getLibraryItemInfoById(int id) {
		LibraryItemInfo libraryItemInfo = libraryItemInfoRespository.findLibraryItemInfoById(id);
		if (libraryItemInfo == null) {
			throw new IllegalArgumentException(
					"The library item info with id " + id + " was not found in the database.");
		}
		return libraryItemInfo;
	}

	@Transactional
	public ReservableItemInfo getReservableItemInfo(int id){
		ReservableItemInfo reservableItemInfo = reservableItemInfoRepository.findReservableItemInfoById(id);
		if (reservableItemInfo == null){
			throw new IllegalArgumentException("The reservation item info with id was not found in the database.");
		}
		return reservableItemInfo;
	}

	@Transactional
	public Reservation reserveItem(Member member, ReservableItemInfo reservableItem){
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

		if (errorMessage.size() > 0) {
			throw new IllegalArgumentException(String.join(" ", errorMessage));
		}

		if (reservationRepository.findReservationByMember(member).stream()
				.anyMatch(reservation -> reservation.getReservableItemInfo().equals(reservableItem))) {
			errorMessage.add("Member already has a reservation for this item.");
		}

		if (errorMessage.size() > 0) {
			throw new IllegalArgumentException(String.join(" ", errorMessage));
		}

		Reservation reservation = new Reservation(member, reservableItem, Date.valueOf(LocalDate.now()));
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
	public MovieInfo createMovieInfo(String title, String genre, String director, int length) {
		checkArgumentsMovie(title, genre, director, length);
		MovieInfo movieInfo = new MovieInfo();
		movieInfo.setTitle(title);
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
	public MovieInfo updateMovieInfo(MovieInfo movieInfo, String title,String genre, String director, int length) {
		checkArgumentsMovie(title, genre, director, length);
		movieInfo.setTitle(title);
		movieInfo.setGenre(genre);
		movieInfo.setDirector(director);
		movieInfo.setLength(length);
		movieInfoRepository.save(movieInfo);
		return movieInfo;
	}

	@Transactional
	public NewspaperInfo createNewspaperInfo(String periodicalTitle, LocalDate publicationDate, String frequency, int number) {
		//checkArgumentsNewspaperInfo(periodicalTitle, Date.valueOf(publicationDate), frequency, number);
		ArrayList<String> errorMessage = new ArrayList<String>();

		if (periodicalTitle == null || periodicalTitle.trim().length() == 0) {
			errorMessage.add("Periodical title can't be empty.");
		}

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
		NewspaperInfo newspaperInfo = new NewspaperInfo();
		newspaperInfo.setPeriodicalTitle(periodicalTitle);
		newspaperInfo.setPublication(Date.valueOf(publicationDate));
		newspaperInfo.setFrequency(frequency);
		newspaperInfo.setNumber(number);
		newspaperInfoRepository.save(newspaperInfo);
		return newspaperInfo;
	}

	@Transactional
	public NewspaperInfo getNewspaperInfo(int id) {
		NewspaperInfo newspaperInfo = newspaperInfoRepository.findNewspaperInfoById(id);
		if (newspaperInfo == null) {
			throw new IllegalArgumentException("The newspaperInfo with id " + id + " was not found in the database.");
		}
		return newspaperInfo;
	}

	@Transactional
	public NewspaperInfo updateNewspaperInfo(NewspaperInfo newspaperInfo, String periodicalTitle, LocalDate publicationDate, String frequency, int number) {
		checkArgumentsNewspaperInfo(periodicalTitle, Date.valueOf(publicationDate), frequency, number);
		newspaperInfo.setPeriodicalTitle(periodicalTitle);
		newspaperInfo.setPublication(Date.valueOf(publicationDate));
		newspaperInfo.setFrequency(frequency);
		newspaperInfo.setNumber(number);
		newspaperInfoRepository.save(newspaperInfo);
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
	public ArchiveInfo createArchiveInfo(String title, String description, LocalDate publicationDate) {
		//checkArgumentsArchive(title,description,Date.valueOf(publicationDate));
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
		ArchiveInfo archiveInfo = new ArchiveInfo();
		archiveInfo.setTitle(title);
		archiveInfo.setDescription(description);
		archiveInfo.setPublicationDate(Date.valueOf(publicationDate));
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

	private void checkArgumentsMovie(String title, String genre, String director, int length) {
		ArrayList<String> errorMessage = new ArrayList<String>();
		if (title == null || title.trim().length()== 0) {
			errorMessage.add("Title can't be empty.");
		}
		
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

	private void checkArgumentsNewspaperInfo(String periodicalTitle, Date publicationDate, String frequency, int number) {
		ArrayList<String> errorMessage = new ArrayList<String>();

		if (periodicalTitle == null || periodicalTitle.trim().length() == 0) {
			errorMessage.add("Periodical title can't be empty.");
		}

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
