package ca.mcgill.ecse321.onlinelibrary.service;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ca.mcgill.ecse321.onlinelibrary.dao.LibrarianRepository;
import ca.mcgill.ecse321.onlinelibrary.model.Librarian;

@Service
public class LibrarianService {

	@Autowired
	LibrarianRepository librarianRepository;

	@Transactional
	public void deleteLibrarianById(int id) {
		Librarian librarianToDelete = librarianRepository.findLibrarianById(id);

		if (librarianToDelete == null) {
			throw new IllegalArgumentException("Librarian with ID \"" + id + "\" not found.");
		}
		if (librarianToDelete.isHead()) {
			throw new IllegalArgumentException("Cannot delete head librarian.");
		}

		librarianRepository.delete(librarianToDelete);
	}

	@Transactional
	public void deleteLibrarianByUsername(String username) {
		Librarian librarianToDelete = librarianRepository.findLibrarianByUsername(username);

		if (librarianToDelete == null) {
			throw new IllegalArgumentException("Librarian with username \"" + username + "\" not found.");
		}
		if (librarianToDelete.isHead()) {
			throw new IllegalArgumentException("Cannot delete head librarian.");
		}

		librarianRepository.delete(librarianToDelete);
	}
}
