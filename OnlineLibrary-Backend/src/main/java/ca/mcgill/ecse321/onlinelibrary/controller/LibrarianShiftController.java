package ca.mcgill.ecse321.onlinelibrary.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.onlinelibrary.dto.LibrarianShiftDto;
import ca.mcgill.ecse321.onlinelibrary.model.Librarian;
import ca.mcgill.ecse321.onlinelibrary.model.LibrarianShift;
import ca.mcgill.ecse321.onlinelibrary.service.LibrarianShiftService;

@CrossOrigin(origins = "*")
@RestController
public class LibrarianShiftController {
	@Autowired
	private LibrarianShiftService service;
	
	@GetMapping(value = { "/librarianShift/{date}", "/librarianShift/{date}/" })
	public List<LibrarianShiftDto> getLibrarianShift(@PathVariable("date") Date date) throws IllegalArgumentException {
		return service.getLibrarianShift(date).stream()
				.map(LS -> LibrarianShiftDto.fromLibrarianShift(LS)).collect(Collectors.toList());
	}
	
	@GetMapping(value = { "/librarianShift/{librarianId}", "/librarianShift/{librarianId}/" })
	public List<LibrarianShiftDto> getLibraryOpeningHours(@PathVariable("librarianId") int librarianId) throws IllegalArgumentException {
		return service.getLibrarianShift(librarianId).stream()
				.map(LS -> LibrarianShiftDto.fromLibrarianShift(LS)).collect(Collectors.toList());
	}
	
	@GetMapping(value = { "/librarianShift/{librarianId}/{date}", "/librarianShift/{librarianId}/{date}/" })
	public List<LibrarianShiftDto> getLibraryOpeningHours(@PathVariable("librarianId") int librarianId, @PathVariable("date") Date date) throws IllegalArgumentException {
		return service.getLibrarianShift(librarianId, date).stream()
				.map(LS -> LibrarianShiftDto.fromLibrarianShift(LS)).collect(Collectors.toList());
	}
	
	@PostMapping(value = { "/librarianShift", "/librarianShift/"})
	public LibrarianShiftDto createLibrarianShift(@RequestParam Date date, @RequestParam Time startTime, @RequestParam Time endTime, @RequestParam int librarianId) throws IllegalArgumentException{
		LibrarianShift librarianShift = service.createLibrarianShift(date, startTime, endTime, librarianId);
		return LibrarianShiftDto.fromLibrarianShift(librarianShift);
	}
	
	@DeleteMapping(value = { "/librarianShift/{id}", "/librarianShift/{id}/"})
	public void deleteLibrarianShift(@PathVariable("id") int id) throws IllegalArgumentException {
		service.deleteLibrarianShift(id);
	}
}
