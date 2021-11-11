package ca.mcgill.ecse321.onlinelibrary.controller;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.onlinelibrary.dto.AlbumInfoDto;
import ca.mcgill.ecse321.onlinelibrary.dto.ArchiveInfoDto;
import ca.mcgill.ecse321.onlinelibrary.dto.BookInfoDto;
import ca.mcgill.ecse321.onlinelibrary.dto.LibraryItemInfoDto;
import ca.mcgill.ecse321.onlinelibrary.dto.MovieInfoDto;
import ca.mcgill.ecse321.onlinelibrary.dto.NewsPaperInfoDto;
import ca.mcgill.ecse321.onlinelibrary.dto.ReservationDto;
import ca.mcgill.ecse321.onlinelibrary.model.AlbumInfo;
import ca.mcgill.ecse321.onlinelibrary.model.ArchiveInfo;
import ca.mcgill.ecse321.onlinelibrary.model.BookInfo;
import ca.mcgill.ecse321.onlinelibrary.model.Member;
import ca.mcgill.ecse321.onlinelibrary.model.MovieInfo;
import ca.mcgill.ecse321.onlinelibrary.model.NewsPaperInfo;
import ca.mcgill.ecse321.onlinelibrary.model.ReservableItemInfo;
import ca.mcgill.ecse321.onlinelibrary.model.Reservation;
import ca.mcgill.ecse321.onlinelibrary.service.LibraryItemInfoService;
import ca.mcgill.ecse321.onlinelibrary.service.MemberService;

@CrossOrigin(origins = "*")
@RestController
public class LibraryItemInfoController {

	@Autowired
	private LibraryItemInfoService libraryItemInfoService;
	
	@Autowired
	private MemberService memberService;

	@GetMapping(value = { "/browse", "/browse/" })
    public List<LibraryItemInfoDto> getAllLibraryItemInfos(){
        return libraryItemInfoService.browseAllLibraryItemInfos().stream().map(p -> p.convertToDto()).collect(Collectors.toList());
    }

	@PostMapping(value = { "/reservation", "/reservation/"})
	public ReservationDto reserveItem(@RequestParam int memberId, @RequestParam int reservableItemId, @RequestParam Date date) throws IllegalArgumentException {
		Member member = memberService.getMemberById(memberId);
		ReservableItemInfo reservableItem = libraryItemInfoService.getReservableItemInfo(reservableItemId);
		Reservation reservation = libraryItemInfoService.reserveItem(member, reservableItem, date);
		return ReservationDto.fromReservation(reservation);
	}

	@GetMapping(value = {"/reservation/{reservationId}", "/reservation/{reservationId}/"})
	public ReservationDto getReservationsById(@PathVariable("reservationId") int Id) throws IllegalArgumentException {
		Reservation reservation = libraryItemInfoService.getReservationsByReservationId(Id);
		return ReservationDto.fromReservation(reservation);
	}

	@GetMapping(value = {"/member/{memberId}/reservation", "/reservableItemInfo/{reservableItemInfoId}/reservation/"})
	public List<ReservationDto> getReservationsByMember(@PathVariable int memberId) throws IllegalArgumentException{
		Member member = memberService.getMemberById(memberId);
		return libraryItemInfoService.getReservationsByMember(member).stream().map(reservation -> ReservationDto.fromReservation(reservation)).collect(Collectors.toList());
	}

	@GetMapping(value = {"/reservableItemInfo/{reservableItemInfoId}/reservation", "/reservableItemInfo/{reservableItemInfoId}/reservation/"})
	public List<ReservationDto> getReservationsByReservableItemInfo(@RequestParam int reservableItemInfoId) throws IllegalArgumentException{
		ReservableItemInfo reservableItemInfo = libraryItemInfoService.getReservableItemInfo(reservableItemInfoId);
		return libraryItemInfoService.getReservationsByReservableItemInfo(reservableItemInfo).stream().map(reservation -> ReservationDto.fromReservation(reservation)).collect(Collectors.toList());
	}

	@DeleteMapping(value = {"/reservation/{reservationId}", "/reservation/{reservationId}/"})
	public void deleteReservation(@PathVariable("reservationId") int reservationId) throws IllegalArgumentException{
		libraryItemInfoService.deleteReservationbyId(reservationId);
	}

	@PostMapping(value = { "/bookInfo/{title}", "/bookInfo/{title}/"})
	public BookInfoDto createBookInfo(@PathVariable("title") String title, @RequestParam int numberOfPage,
			@RequestParam String author, @RequestParam long isbn) throws IllegalArgumentException {
		BookInfo bookInfo = libraryItemInfoService.createBookInfo(title, numberOfPage, author, isbn);
		return BookInfoDto.fromBookInfo(bookInfo);
	}
	
	@PutMapping(value = {"/bookInfo/{id}" ,"/bookInfo/{id}/"})
	public BookInfoDto updateBookInfo(@PathVariable int id, @RequestParam String title,@RequestParam int numberOfPage, 
			@RequestParam String author, @RequestParam long isbn) throws IllegalArgumentException {
		BookInfo bookInfo = libraryItemInfoService.getBookInfo(id);
		return BookInfoDto.fromBookInfo(libraryItemInfoService.updateBookInfo
			(bookInfo, title, numberOfPage, author, isbn));
	}

	@PostMapping(value = { "/movieInfo", "/movieInfo/" })
	public MovieInfoDto createMovieInfo(@RequestParam String genre, @RequestParam String director, @RequestParam int length)
			throws IllegalArgumentException {
		MovieInfo movieInfo = libraryItemInfoService.createMovieInfo(genre, director, length);
		return MovieInfoDto.fromMovieInfo(movieInfo);
	}
	
	@PutMapping(value = {"/movieInfo/{id}" ,"/movieInfo/{id}/"})
	public MovieInfoDto updateMovieInfo(@PathVariable int id, @RequestParam String genre, @RequestParam String director, 
			@RequestParam int length) throws IllegalArgumentException {
		MovieInfo movieInfo = libraryItemInfoService.getMovieInfo(id);
		return MovieInfoDto.fromMovieInfo(libraryItemInfoService.updateMovieInfo
			(movieInfo, genre, director, length));
	}

	@PostMapping(value = { "/archiveInfo/{title}", "/archiveInfo/{title}/"})
	public ArchiveInfoDto createArchiveInfo(@PathVariable("title") String title, @RequestParam String description, 
			@RequestParam Date publicationDate) throws IllegalArgumentException{
		ArchiveInfo archiveInfo = libraryItemInfoService.createArchiveInfo(title, description, publicationDate);
		return ArchiveInfoDto.fromArchiveInfo(archiveInfo);
	}
	
	@PutMapping(value = {"/archiveInfo/{id}", "/archiveInfo/{id}/"})
	public ArchiveInfoDto updateArchiveInfo(@PathVariable("id") int id, @RequestParam String title, 
			@RequestParam String description, @RequestParam Date publicationDate) throws IllegalArgumentException{
		ArchiveInfo archiveInfo = libraryItemInfoService.getArchiveInfo(id);
		return ArchiveInfoDto.fromArchiveInfo(libraryItemInfoService.updateArchiveInfo(archiveInfo, title, description, publicationDate));
	}

	@PostMapping (value = { "/newsPaperInfo", "/newsPaperInfo/"})
	public NewsPaperInfoDto createNewsPaperInfo(@RequestParam Date publication, @RequestParam String frequency, @RequestParam int number)
			throws IllegalArgumentException {
		NewsPaperInfo newsPaperInfo = libraryItemInfoService.createNewspaperInfo(publication, frequency, number);
		return NewsPaperInfoDto.fromNewsPaperInfo(newsPaperInfo);
	}
	
	@PutMapping(value = {"/newspaperInfo/{id}", "/newspaperInfo/{id}/"})
	public NewsPaperInfoDto updateNewspaperInfo(@PathVariable("id") int id, @RequestParam Date publicationDate, 
			@RequestParam String frequency, @RequestParam int number) throws IllegalArgumentException{
		NewsPaperInfo newspaperInfo = libraryItemInfoService.getNewspaperInfo(id);
		return NewsPaperInfoDto.fromNewsPaperInfo(libraryItemInfoService.updateNewspaperInfo(newspaperInfo, publicationDate, frequency, number));
	}

	@PostMapping(value = {"/albumInfo/{title}", "/albumInfo/{title}/"})
	public AlbumInfoDto createAlbumInfo(@PathVariable("title") String title, @RequestParam String composerPerformer, String genre)
			throws IllegalArgumentException {
		AlbumInfo albumInfo = libraryItemInfoService.createAlbumInfo(title, composerPerformer, genre);
		return AlbumInfoDto.fromAlbumInfo(albumInfo);
	}
	
	@PutMapping(value = {"/albumInfo/{id}", "/albumInfo/{id}/"})
	public AlbumInfoDto updateAlbumInfo(@PathVariable("id") int id, @RequestParam String title, 
			@RequestParam String composerPerformer, @RequestParam String genre) throws IllegalArgumentException {
		AlbumInfo albumInfo = libraryItemInfoService.getAlbumInfo(id);
		return AlbumInfoDto.fromAlbumInfo(libraryItemInfoService.updateAlbumInfo(albumInfo, title, composerPerformer, genre));
	}
}
