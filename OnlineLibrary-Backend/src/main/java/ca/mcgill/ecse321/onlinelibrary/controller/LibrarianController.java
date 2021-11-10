package ca.mcgill.ecse321.onlinelibrary.controller;

import java.util.ArrayList;
import java.util.List;

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

	@PutMapping(value = {"/librarian/{id}" ,"/librarian/{id}/"})
	public LibrarianDto updateLibrarian(@RequestBody Librarian librarian, @PathVariable Integer id){
		return LibrarianDto.fromLibrarian(librarianService.updateLibrarian
			(id, librarian.getFullName(), librarian.getUsername(), librarian.getPasswordHash()));
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
