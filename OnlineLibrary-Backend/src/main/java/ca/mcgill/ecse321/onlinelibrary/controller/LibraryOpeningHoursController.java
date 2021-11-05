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
import ca.mcgill.ecse321.onlinelibrary.dto.LibraryOpeningHoursDto;
import ca.mcgill.ecse321.onlinelibrary.model.LibraryOpeningHours;
import ca.mcgill.ecse321.onlinelibrary.service.LibraryOpeningHoursService;

@CrossOrigin(origins = "*")
@RestController
public class LibraryOpeningHoursController {
	@Autowired
	private LibraryOpeningHoursService service;
	
	@GetMapping(value = { "/libraryOpeningHours/{date}", "/persons/{date}/" })
	public List<LibraryOpeningHoursDto> getLibraryOpeningHours(@PathVariable("date") Date date)throws IllegalArgumentException {
		return service.getLibraryOpeningHours(date).stream()
				.map(LOH -> LibraryOpeningHoursDto.fromLibraryOpeningHours(LOH)).collect(Collectors.toList());
	}
	
	@GetMapping(value = { "/libraryOpeningHours/{startDate}/{endDate}", "/persons/{startDate}/{endDate}/" })
	public List<LibraryOpeningHoursDto> getLibraryOpeningHours(@PathVariable("startDate") Date startDate, @PathVariable("endDate") Date endDate) throws IllegalArgumentException {
		return service.getLibraryOpeningHours(startDate, endDate).stream()
				.map(LOH -> LibraryOpeningHoursDto.fromLibraryOpeningHours(LOH)).collect(Collectors.toList());
	}
	
	@PostMapping(value = { "/libraryOpeningHours", "/libraryOpeningHours/"})
	public LibraryOpeningHoursDto createLibraryOpeningHours(@RequestParam Date date, @RequestParam Time startTime, @RequestParam Time endTime)
	throws IllegalArgumentException {
		LibraryOpeningHours libraryOpeningHours = service.createLibraryOpeningHours(date, startTime, endTime);
		return LibraryOpeningHoursDto.fromLibraryOpeningHours(libraryOpeningHours);
	}
	
	@DeleteMapping(value = { "/libraryOpeningHours/{id}", "/libraryOpeningHours/{id}/"})
	public void deleteLibraryOpeningHours(@PathVariable("id") int id) throws IllegalArgumentException {
		service.deleteLibraryOpeningHours(id);
	}
}
