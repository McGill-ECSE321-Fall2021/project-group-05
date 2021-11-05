package ca.mcgill.ecse321.onlinelibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.onlinelibrary.service.LibrarianService;

@CrossOrigin(origins = "*")
@RestController
public class LibrarianController {

	@Autowired
	private LibrarianService librarianService;

	@DeleteMapping(value = {"/librarian/delete/{id}", "/librarian/delete/{id}/"})
	public void deleteLibrarianById(@PathVariable("id") int id) {
		librarianService.deleteLibrarianById(id);
	}

	@DeleteMapping(value = {"/librarian/delete", "/librarian/delete"})
	public void deleteLibrarianByUsername(@RequestParam String username) {
		librarianService.deleteLibrarianByUsername(username);
	}
}
