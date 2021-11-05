package ca.mcgill.ecse321.onlinelibrary.service;

import java.util.ArrayList;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ca.mcgill.ecse321.onlinelibrary.dao.LibrarianRepository;
import ca.mcgill.ecse321.onlinelibrary.model.Librarian;

@Service
public class LibrarianService {

	// TODO Get this from application.properties
	private Integer MIN_PASSWD_LENGTH = 8;

	@Autowired
	LibrarianRepository librarianRepository;

	@Transactional
	public Librarian createLibrarian(String fullName, String username, String password) {
		ArrayList<String> errorMessage = new ArrayList<String>();
		// Full name not empty
		if (fullName == null || fullName.trim().length() == 0) {
			errorMessage.add("Full name cannot be empty.");
		}
		// Username not empty and unique
		if (username == null) {
			errorMessage.add("Username cannot be empty.");
		} else {
			username = username.trim();
			if (username.length() == 0) {
				errorMessage.add("Username cannot be empty.");
			}
			if (librarianRepository.existsLibrarianByUsername(username)) {
				errorMessage.add("Username already taken.");
			}
		}
		// Password long enough
		if (password == null) {
			errorMessage.add("Password must be at least " + MIN_PASSWD_LENGTH + " characters in length.");
		} else {
			password = password.trim();
			if (password.length() < MIN_PASSWD_LENGTH) {
				errorMessage.add("Password must be at least " + MIN_PASSWD_LENGTH + " characters in length.");
			}
		}
		// Throw exception
		if (errorMessage.size() > 0) {
			throw new IllegalArgumentException(String.join(" ", errorMessage));
		}

		// TODO Hash password before saving

		Librarian librarian = new Librarian(fullName, username, password, false);
		librarianRepository.save(librarian);
		return librarian;
	}

	@Transactional
	public Librarian getLibrarianByUsername(String username) {
		Librarian librarian = librarianRepository.findLibrarianByUsername(username);

		if (librarian == null) {
			throw new IllegalArgumentException("Librarian with username \"" + username + "\" not found.");
		}

		return librarian;
	}

	@Transactional
	public Librarian getLibrarianById(int id) {
		Librarian librarian = librarianRepository.findLibrarianById(id);

		if (librarian == null) {
			throw new IllegalArgumentException("Librarian with ID \"" + id + "\" not found.");
		}

		return librarian;
	}

	@Transactional
	public Iterable<Librarian> getAllLibrarians() {
		return librarianRepository.findAll();
	}
}
