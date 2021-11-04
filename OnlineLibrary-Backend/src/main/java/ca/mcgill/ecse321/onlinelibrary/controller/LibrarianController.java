package ca.mcgill.ecse321.onlinelibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
}
