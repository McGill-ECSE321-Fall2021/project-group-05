package ca.mcgill.ecse321.onlinelibrary.service;

import java.util.ArrayList;
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
		ArrayList<String> errors = new ArrayList<String>();

		Librarian librarianToDelete = librarianRepository.findLibrarianById(id);

		if (librarianToDelete == null) {
			errors.add("Librarian with ID " + id + " not found.");
		}
		if (librarianToDelete.isHead()) {
			errors.add("Cannot delete head librarian.");
		}
		if (errors.size() > 0) {
			throw new IllegalArgumentException(String.join(" ", errors));
		}

		librarianRepository.delete(librarianToDelete);
	}

	@Transactional
	public void deleteLibrarianByUsername(String username) {
		ArrayList<String> errors = new ArrayList<String>();

		Librarian librarianToDelete = librarianRepository.findLibrarianByUsername(username);

		if (librarianToDelete == null) {
			errors.add("Librarian with username \"" + username + "\" not found.");
		}
		if (librarianToDelete.isHead()) {
			errors.add("Cannot delete head librarian.");
		}
		if (errors.size() > 0) {
			throw new IllegalArgumentException(String.join(" ", errors));
		}

		librarianRepository.delete(librarianToDelete);
	}
}
