package ca.mcgill.ecse321.onlinelibrary.controller;


import ca.mcgill.ecse321.onlinelibrary.dto.LibrarianShiftDto;
import ca.mcgill.ecse321.onlinelibrary.model.LibrarianShift;
import ca.mcgill.ecse321.onlinelibrary.service.LibrarianShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
public class LibrarianShiftController {
	@Autowired
	private LibrarianShiftService service;
	
	@GetMapping(value = { "/librarianShift/{date}", "/librarianShift/{date}/" })
	public List<LibrarianShiftDto> getLibrarianShift(
			@PathVariable("date") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date) throws IllegalArgumentException {
		return service.getLibrarianShift(date).stream()
				.map(LS -> LibrarianShiftDto.fromLibrarianShift(LS)).collect(Collectors.toList());
	}
	
	@GetMapping(value = { "/librarianShift", "/librarianShift/" })
	public List<LibrarianShiftDto> getLibrarianShift(@RequestParam("librarianId") int librarianId) throws IllegalArgumentException {
		return service.getLibrarianShift(librarianId).stream()
				.map(LS -> LibrarianShiftDto.fromLibrarianShift(LS)).collect(Collectors.toList());
	}
	
	@GetMapping(value = { "/librarianShift/{librarianId}/{date}", "/librarianShift/{librarianId}/{date}/" })
	public List<LibrarianShiftDto> getLibrarianShift(@PathVariable("librarianId") int librarianId, 
			@PathVariable("date") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date) throws IllegalArgumentException {
		return service.getLibrarianShift(librarianId, date).stream()
				.map(LS -> LibrarianShiftDto.fromLibrarianShift(LS)).collect(Collectors.toList());
	}
	
	@GetMapping(value = { "/librarianShift/between/{startDate}/{endDate}", "/librarianShift/between/{startDate}/{endDate}/" })
	public List<LibrarianShiftDto> getLibraryOpeningHours(
			@PathVariable("startDate") @DateTimeFormat(pattern="yyyy-MM-dd")  LocalDate startDate, 
			@PathVariable("endDate") @DateTimeFormat(pattern="yyyy-MM-dd")  LocalDate endDate) throws IllegalArgumentException {
		return service.getLibrarianShift(startDate, endDate).stream()
				.map(LS -> LibrarianShiftDto.fromLibrarianShift(LS)).collect(Collectors.toList());
	}
	
	@PostMapping(value = { "/librarianShift", "/librarianShift/"})
	public LibrarianShiftDto createLibrarianShift(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date, 
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime, 
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime, 
			@RequestParam int librarianId) throws IllegalArgumentException{
		LibrarianShift librarianShift = service.createLibrarianShift(date, startTime, endTime, librarianId);
		return LibrarianShiftDto.fromLibrarianShift(librarianShift);
	}
	
	@DeleteMapping(value = { "/librarianShift/{id}", "/librarianShift/{id}/"})
	public void deleteLibrarianShift(@PathVariable("id") int id) throws IllegalArgumentException {
		service.deleteLibrarianShift(id);
	}
}
