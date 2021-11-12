package ca.mcgill.ecse321.onlinelibrary.service;

import ca.mcgill.ecse321.onlinelibrary.dao.LibrarianRepository;
import ca.mcgill.ecse321.onlinelibrary.model.Librarian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
public class LibrarianService {

	// TODO Get this from application.properties
	private Integer MIN_PASSWD_LENGTH = 8;

	@Autowired
	LibrarianRepository librarianRepository;

	@Transactional
	public void deleteLibrarianById(int id) {
		Librarian librarianToDelete = getNonNullLibrarianFromRepo(id);
		
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

	@Transactional
	public Librarian createLibrarian(String fullName, String username, String password) {
		ArrayList<String> errorMessage = checkValidInput(fullName, username, password);

		// Throw exception
		if (errorMessage.size() > 0) {
			throw new IllegalArgumentException(String.join(" ", errorMessage));
		}

		fullName = fullName.trim();
		username = username.trim();
		password = password.trim();

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

		return getNonNullLibrarianFromRepo(id);
	}

	@Transactional
	public Iterable<Librarian> getAllLibrarians() {
		return librarianRepository.findAll();
	}

	@Transactional
	public Librarian updateLibrarian(Integer id, String newFullName, String newUsername, String newPasswordHash) {
		Librarian librarian = getNonNullLibrarianFromRepo(id);

		ArrayList<String> errorMessage = checkValidInput(newFullName, newUsername, newPasswordHash);

		if (errorMessage.size()> 0 ){
			throw new IllegalArgumentException(String.join(" ", errorMessage));
		}

		librarian.setFullName(newFullName);
		librarian.setUsername(newUsername);
		librarian.setPasswordHash(newPasswordHash);
		librarianRepository.save(librarian);
		
		return librarian;
	}

	private ArrayList<String> checkValidInput(String fullName, String username, String password){
		ArrayList<String> errorMessage = new ArrayList<String>();
		// Full name not empty
		if (fullName == null || fullName.trim().length() == 0) {
			errorMessage.add("Full name cannot be empty.");
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

		return errorMessage;
	}

	private Librarian getNonNullLibrarianFromRepo(int id){
		Librarian librarian = librarianRepository.findLibrarianById(id);

		if (librarian == null) {
			throw new IllegalArgumentException("Librarian with ID \"" + id + "\" not found.");
		}
		return librarian;
	}
}
