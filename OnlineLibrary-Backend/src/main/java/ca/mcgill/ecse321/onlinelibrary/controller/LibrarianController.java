package ca.mcgill.ecse321.onlinelibrary.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.onlinelibrary.dto.LibrarianDto;
import ca.mcgill.ecse321.onlinelibrary.model.Librarian;
import ca.mcgill.ecse321.onlinelibrary.service.LibrarianService;

@CrossOrigin(origins = "*")
@RestController
public class LibrarianController {

	@Autowired
	private LibrarianService librarianService;

	@PostMapping(value = {"/librarian/create", "/librarian/create/"})
	public LibrarianDto createLibarian(@RequestParam String fullName, @RequestParam String username,
			@RequestParam String password) throws IllegalArgumentException {
		Librarian librarian = librarianService.createLibrarian(fullName, username, password);
		return LibrarianDto.fromLibrarian(librarian);
	}

	@GetMapping(value = {"/librarian", "/librarian/"})
	public LibrarianDto getLibrarianByUsername(@RequestParam String username) {
		Librarian librarian = librarianService.getLibrarianByUsername(username);
		return LibrarianDto.fromLibrarian(librarian);
	}

	@GetMapping(value = {"/librarian/{id}", "/librarian/{id}/"})
	public LibrarianDto getLibrarianById(@PathVariable int id) {
		Librarian librarian = librarianService.getLibrarianById(id);
		return LibrarianDto.fromLibrarian(librarian);
	}

	@GetMapping(value = {"/librarian/all", "/librarian/all/"})
	public List<LibrarianDto> getAllLibrarians() {
		Iterable<Librarian> librarians = librarianService.getAllLibrarians();
		List<LibrarianDto> librarianDtos = new ArrayList<LibrarianDto>();
		for (Librarian l : librarians) {
			librarianDtos.add(LibrarianDto.fromLibrarian(l));
		}
		return librarianDtos;
	}
}
