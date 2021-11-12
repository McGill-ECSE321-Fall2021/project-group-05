package ca.mcgill.ecse321.onlinelibrary.controller;

import ca.mcgill.ecse321.onlinelibrary.dto.LibraryOpeningHoursDto;
import ca.mcgill.ecse321.onlinelibrary.model.LibraryOpeningHours;
import ca.mcgill.ecse321.onlinelibrary.service.LibraryOpeningHoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
public class LibraryOpeningHoursController {
	@Autowired
	private LibraryOpeningHoursService service;
	
	@GetMapping(value = { "/libraryOpeningHours/{date}", "/libraryOpeningHours/{date}/" })
	public List<LibraryOpeningHoursDto> getLibraryOpeningHours(
			@PathVariable("date") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date)throws IllegalArgumentException {
		return service.getLibraryOpeningHours(date).stream()
				.map(LOH -> LibraryOpeningHoursDto.fromLibraryOpeningHours(LOH)).collect(Collectors.toList());
	}
	
	@GetMapping(value = { "/libraryOpeningHours/{startDate}/{endDate}", "/libraryOpeningHours/{startDate}/{endDate}/" })
	public List<LibraryOpeningHoursDto> getLibraryOpeningHours(
			@PathVariable("startDate") @DateTimeFormat(pattern="yyyy-MM-dd")  LocalDate startDate, 
			@PathVariable("endDate") @DateTimeFormat(pattern="yyyy-MM-dd")  LocalDate endDate) throws IllegalArgumentException {
		return service.getLibraryOpeningHours(startDate, endDate).stream()
				.map(LOH -> LibraryOpeningHoursDto.fromLibraryOpeningHours(LOH)).collect(Collectors.toList());
	}
	
	@PostMapping(value = { "/libraryOpeningHours", "/libraryOpeningHours/"})
	public LibraryOpeningHoursDto createLibraryOpeningHours(
			@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime, 
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime)
	throws IllegalArgumentException {
		LibraryOpeningHours libraryOpeningHours = service.createLibraryOpeningHours(date, startTime, endTime);
		return LibraryOpeningHoursDto.fromLibraryOpeningHours(libraryOpeningHours);
	}
	
	@DeleteMapping(value = { "/libraryOpeningHours/{id}", "/libraryOpeningHours/{id}/"})
	public void deleteLibraryOpeningHours(@PathVariable("id") int id) throws IllegalArgumentException {
		service.deleteLibraryOpeningHours(id);
	}
}
