package ca.mcgill.ecse321.onlinelibrary.controller;

import ca.mcgill.ecse321.onlinelibrary.dto.*;
import ca.mcgill.ecse321.onlinelibrary.model.*;
import ca.mcgill.ecse321.onlinelibrary.service.LibraryItemInfoService;
import ca.mcgill.ecse321.onlinelibrary.service.LibraryItemService;
import ca.mcgill.ecse321.onlinelibrary.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
public class LibraryItemInfoController {

	@Autowired
	private LibraryItemInfoService libraryItemInfoService;

	@Autowired
	private LibraryItemService libraryItemService;

	@Autowired
	private MemberService memberService;

	@GetMapping(value = {"/browse", "/browse/"})
	public List<LibraryItemInfoDto> getAllLibraryItemInfos() {
		return libraryItemInfoService.browseAllLibraryItemInfos().stream().map(p -> p.convertToDto())
				.collect(Collectors.toList());
	}

	@GetMapping(value = { "/libraryItemInfo/{libraryItemInfoId}", "/libraryItemInfo/{libraryItemInfoId}/" })
	public LibraryItemInfoDto getLibraryItemInfoById(@PathVariable int libraryItemInfoId) {
		return libraryItemInfoService.getLibraryItemInfoById(libraryItemInfoId).convertToDto();
	}

	@PostMapping(value = {"/reservation", "/reservation/"})
	public ReservationDto reserveItem(@RequestParam int memberId, @RequestParam int reservableItemId) throws IllegalArgumentException {
		Member member = memberService.getMemberById(memberId);
		ReservableItemInfo reservableItem = libraryItemInfoService.getReservableItemInfo(reservableItemId);
		Reservation reservation = libraryItemInfoService.reserveItem(member, reservableItem);
		return ReservationDto.fromReservation(reservation);
	}

	@GetMapping(value = {"/reservation/{reservationId}", "/reservation/{reservationId}/"})
	public ReservationDto getReservationsById(@PathVariable("reservationId") int Id) throws IllegalArgumentException {
		Reservation reservation = libraryItemInfoService.getReservationsByReservationId(Id);
		return ReservationDto.fromReservation(reservation);
	}

	@GetMapping(value = {"/member/{memberId}/reservation", "/member/{memberId}/reservation/"})
	public List<ReservationDto> getReservationsByMember(@PathVariable int memberId) throws IllegalArgumentException {
		Member member = memberService.getMemberById(memberId);
		return libraryItemInfoService.getReservationsByMember(member).stream()
				.map(reservation -> ReservationDto.fromReservation(reservation)).collect(Collectors.toList());
	}

	@GetMapping(value = {"/reservableItemInfo/{reservableItemInfoId}/reservation",
	"/reservableItemInfo/{reservableItemInfoId}/reservation/"})
	public List<ReservationDto> getReservationsByReservableItemInfo(
			@PathVariable("reservableItemInfoId") int reservableItemInfoId) throws IllegalArgumentException {
		ReservableItemInfo reservableItemInfo = libraryItemInfoService.getReservableItemInfo(reservableItemInfoId);
		return libraryItemInfoService.getReservationsByReservableItemInfo(reservableItemInfo).stream()
				.map(reservation -> ReservationDto.fromReservation(reservation)).collect(Collectors.toList());
	}

	@DeleteMapping(value = {"/reservation/{reservationId}", "/reservation/{reservationId}/"})
	public void deleteReservation(@PathVariable("reservationId") int reservationId) throws IllegalArgumentException {
		libraryItemInfoService.deleteReservationbyId(reservationId);
	}

	@PostMapping(value = {"/bookInfo/{title}", "/bookInfo/{title}/"})
	public BookInfoDto createBookInfo(@PathVariable("title") String title, @RequestParam int numberOfPage,
			@RequestParam String author, @RequestParam long isbn) throws IllegalArgumentException {
		BookInfo bookInfo = libraryItemInfoService.createBookInfo(title, numberOfPage, author, isbn);
		return BookInfoDto.fromBookInfo(bookInfo);
	}

	@PutMapping(value = {"/bookInfo/{id}", "/bookInfo/{id}/"})
	public BookInfoDto updateBookInfo(@PathVariable int id, @RequestParam String title, @RequestParam int numberOfPage,
			@RequestParam String author, @RequestParam long isbn) throws IllegalArgumentException {
		BookInfo bookInfo = libraryItemInfoService.getBookInfo(id);
		return BookInfoDto
				.fromBookInfo(libraryItemInfoService.updateBookInfo(bookInfo, title, numberOfPage, author, isbn));
	}

	@PostMapping(value = {"/movieInfo/{title}/", "/movieInfo/{title}"})
	public MovieInfoDto createMovieInfo(@PathVariable("title") String title, @RequestParam String genre, @RequestParam String director,
			@RequestParam int length) throws IllegalArgumentException {
		MovieInfo movieInfo = libraryItemInfoService.createMovieInfo(title, genre, director, length);
		return MovieInfoDto.fromMovieInfo(movieInfo);
	}

	@PutMapping(value = {"/movieInfo/{id}", "/movieInfo/{id}/"})
	public MovieInfoDto updateMovieInfo(@PathVariable int id, @RequestParam String title, @RequestParam String genre, @RequestParam String director,
			@RequestParam int length) throws IllegalArgumentException {
		MovieInfo movieInfo = libraryItemInfoService.getMovieInfo(id);
		return MovieInfoDto.fromMovieInfo(libraryItemInfoService.updateMovieInfo(movieInfo, title, genre, director, length));
	}

	@PostMapping(value = {"/archiveInfo/{title}", "/archiveInfo/{title}/"})
	public ArchiveInfoDto createArchiveInfo(@PathVariable("title") String title, @RequestParam String description,
			@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate publicationDate) throws IllegalArgumentException {
		ArchiveInfo archiveInfo = libraryItemInfoService.createArchiveInfo(title, description, publicationDate);
		return ArchiveInfoDto.fromArchiveInfo(archiveInfo);
	}

	@PutMapping(value = {"/archiveInfo/{id}", "/archiveInfo/{id}/"})
	public ArchiveInfoDto updateArchiveInfo(@PathVariable("id") int id, @RequestParam String title,
			@RequestParam String description, @RequestParam Date publicationDate) throws IllegalArgumentException {
		ArchiveInfo archiveInfo = libraryItemInfoService.getArchiveInfo(id);
		return ArchiveInfoDto.fromArchiveInfo(
				libraryItemInfoService.updateArchiveInfo(archiveInfo, title, description, publicationDate));
	}

	@PostMapping(value = {"/newspaperInfo", "/newspaperInfo/"})
	public NewspaperInfoDto createNewspaperInfo(@RequestParam String periodicalTitle, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate publication, @RequestParam String frequency,
			@RequestParam int number) throws IllegalArgumentException {
		NewspaperInfo newspaperInfo = libraryItemInfoService.createNewspaperInfo(periodicalTitle, publication, frequency, number);
		return NewspaperInfoDto.fromNewspaperInfo(newspaperInfo);
	}

	@PutMapping(value = {"/newspaperInfo/{id}", "/newspaperInfo/{id}/"})
	public NewspaperInfoDto updateNewspaperInfo(@PathVariable("id") int id, @RequestParam String periodicalTitle, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate publicationDate,
			@RequestParam String frequency, @RequestParam int number) throws IllegalArgumentException {
		NewspaperInfo newspaperInfo = libraryItemInfoService.getNewspaperInfo(id);
		return NewspaperInfoDto.fromNewspaperInfo(
				libraryItemInfoService.updateNewspaperInfo(newspaperInfo, periodicalTitle, publicationDate, frequency, number));
	}

	@PostMapping(value = {"/albumInfo/{title}", "/albumInfo/{title}/"})
	public AlbumInfoDto createAlbumInfo(@PathVariable("title") String title, @RequestParam String composerPerformer,
			String genre) throws IllegalArgumentException {
		AlbumInfo albumInfo = libraryItemInfoService.createAlbumInfo(title, composerPerformer, genre);
		return AlbumInfoDto.fromAlbumInfo(albumInfo);
	}

	@PutMapping(value = {"/albumInfo/{id}", "/albumInfo/{id}/"})
	public AlbumInfoDto updateAlbumInfo(@PathVariable("id") int id, @RequestParam String title,
			@RequestParam String composerPerformer, @RequestParam String genre) throws IllegalArgumentException {
		AlbumInfo albumInfo = libraryItemInfoService.getAlbumInfo(id);
		return AlbumInfoDto
				.fromAlbumInfo(libraryItemInfoService.updateAlbumInfo(albumInfo, title, composerPerformer, genre));
	}

	@GetMapping(value = {"/libraryItemInfo/{libraryItemInfoId}/libraryItem", "/libraryItemInfo/{libraryItemInfoId}/libraryItem"})
	public List<LibraryItemDto> getAssociatedLibraryItems(@PathVariable int libraryItemInfoId) {
		LibraryItemInfo libraryItemInfo = libraryItemInfoService.getLibraryItemInfoById(libraryItemInfoId);
		return libraryItemService.getAssociatedCopies(libraryItemInfo).stream().map(item -> item.convertToDto()).collect(Collectors.toList());
	}
}
