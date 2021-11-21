package ca.mcgill.ecse321.onlinelibrary.controller;

import ca.mcgill.ecse321.onlinelibrary.dto.LibrarianDto;
import ca.mcgill.ecse321.onlinelibrary.dto.LibrarianLoginResponseDto;
import ca.mcgill.ecse321.onlinelibrary.model.Librarian;
import ca.mcgill.ecse321.onlinelibrary.model.LibrarianShift;
import ca.mcgill.ecse321.onlinelibrary.service.LibrarianService;
import ca.mcgill.ecse321.onlinelibrary.service.LibrarianShiftService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class LibrarianController {

	@Autowired
	private LibrarianService librarianService;
	@Autowired
	private LibrarianShiftService librarianShiftService;

	@PostMapping(value = {"/librarian", "/librarian/"})
	public LibrarianDto createLibarian(@RequestParam String fullName, @RequestParam String username,
			@RequestParam String password) throws IllegalArgumentException {
		Librarian librarian = librarianService.createLibrarian(fullName, username, password);
		return LibrarianDto.fromLibrarian(librarian, new ArrayList<LibrarianShift>());
	}

	@GetMapping(value = {"/librarian/login", "/librarian/login/"})
	public LibrarianLoginResponseDto logIn(@RequestParam String username, @RequestParam String password) {
		Librarian librarian = librarianService.logIn(username, password);
		return new LibrarianLoginResponseDto(librarian);
	}

	@GetMapping(value = {"/librarian", "/librarian/"})
	public LibrarianDto getLibrarianByUsername(@RequestParam String username) {
		Librarian librarian = librarianService.getLibrarianByUsername(username);
		Iterable<LibrarianShift> shifts = librarianShiftService.getLibrarianShift(librarian.getId());
		return LibrarianDto.fromLibrarian(librarian, shifts);
	}

	@GetMapping(value = {"/librarian/{id}", "/librarian/{id}/"})
	public LibrarianDto getLibrarianById(@PathVariable int id) {
		Librarian librarian = librarianService.getLibrarianById(id);
		Iterable<LibrarianShift> shifts = librarianShiftService.getLibrarianShift(id);
		return LibrarianDto.fromLibrarian(librarian, shifts);
	}

	@GetMapping(value = {"/librarian/all", "/librarian/all/"})
	public List<LibrarianDto> getAllLibrarians() {
		Iterable<Librarian> librarians = librarianService.getAllLibrarians();
		List<LibrarianDto> librarianDtos = new ArrayList<LibrarianDto>();
		for (Librarian l : librarians) {
			Iterable<LibrarianShift> shifts = librarianShiftService.getLibrarianShift(l.getId());
			librarianDtos.add(LibrarianDto.fromLibrarian(l, shifts));
		}
		return librarianDtos;
	}

	@PutMapping(value = {"/librarian/{id}", "/librarian/{id}/"})
	public LibrarianDto updateLibrarian(@RequestParam String fullName, @RequestParam String username,
			@RequestParam String password, @PathVariable Integer id) {
		Librarian updatedLibrarian = librarianService.updateLibrarian(id, fullName, username, password);
		Iterable<LibrarianShift> shifts = librarianShiftService.getLibrarianShift(id);

		return LibrarianDto.fromLibrarian(updatedLibrarian, shifts);
	}

	@DeleteMapping(value = {"/librarian/delete", "/librarian/delete"})
	public void deleteLibrarianByUsername(@RequestParam String username) {
		librarianService.deleteLibrarianByUsername(username);
	}

	@DeleteMapping(value = {"/librarian/{id}", "/librarian/{id}/"})
	public void deleteLibrarianById(@PathVariable("id") int id) {
		librarianService.deleteLibrarianById(id);
	}
}
