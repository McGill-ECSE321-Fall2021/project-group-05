package ca.mcgill.ecse321.onlinelibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ca.mcgill.ecse321.onlinelibrary.dto.LibrarianDto;
import ca.mcgill.ecse321.onlinelibrary.model.Librarian;
import ca.mcgill.ecse321.onlinelibrary.service.LibrarianService;

@CrossOrigin(origins = "*")
@RestController
public class LibrarianController {

	@Autowired
	private LibrarianService librarianService;

	@PostMapping(value = {"/librarian", "/librarian/"})
	public LibrarianDto createLibarian(@RequestParam String fullName, @RequestParam String username,
			@RequestParam String password) throws IllegalArgumentException {
		Librarian librarian = librarianService.createLibrarian(fullName, username, password);
		return LibrarianDto.fromLibrarian(librarian);
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
