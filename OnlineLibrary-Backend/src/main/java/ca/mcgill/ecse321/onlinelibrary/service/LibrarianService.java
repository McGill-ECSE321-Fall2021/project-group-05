package ca.mcgill.ecse321.onlinelibrary.service;

import ca.mcgill.ecse321.onlinelibrary.dao.LibrarianRepository;
import ca.mcgill.ecse321.onlinelibrary.model.Librarian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Service to create, read, update, delete, and log in librarians.
 */
@Service
public class LibrarianService {

	// TODO Get this from application.properties
	private Integer MIN_PASSWD_LENGTH = 8;

	@Autowired
	LibrarianRepository librarianRepository;

	/**
	 * Deletes the librarian with the given ID.
	 *
	 * Throws an IllegalArgumentException if there is no librarian with the
	 * given ID or if the librarian is the head.
	 *
	 * @param id
	 *            Primary key of the librarian to delete
	 */
	@Transactional
	public void deleteLibrarianById(int id) {
		Librarian librarianToDelete = getNonNullLibrarianFromRepo(id);

		if (librarianToDelete.isHead()) {
			throw new IllegalArgumentException("Cannot delete head librarian.");
		}

		librarianRepository.delete(librarianToDelete);
	}

	/**
	 * Deletes the librarian with the given username.
	 *
	 * Throws an IllegalArgumentException if there is no librarian with the
	 * given username or if the librarian is the head.
	 *
	 * @param username
	 *            Username of the librarian to delete
	 */
	@Transactional
	public void deleteLibrarianByUsername(String username) {
		Librarian librarianToDelete = getNonNullLibrarianFromRepo(username);

		if (librarianToDelete.isHead()) {
			throw new IllegalArgumentException("Cannot delete head librarian.");
		}

		librarianRepository.delete(librarianToDelete);
	}

	/**
	 * Registers a new librarian.
	 *
	 * The full name, username, and password are trimmed before being saved.
	 *
	 * Throws an IllegalArgumentException if the full name is empty, if the
	 * username is empty, if the username is already taken by another librarian,
	 * or if the password is less than 8 characters in length.
	 *
	 * @param fullName
	 *            Full name of the new librarian
	 * @param username
	 *            Username of the new librarian
	 * @param password
	 *            Password of the new librarian
	 * @return
	 */
	@Transactional
	public Librarian createLibrarian(String fullName, String username, String password) {
		ArrayList<String> errorMessage = validateNewLibrarianCredentials(fullName, username, password);

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

	/**
	 * Returns the account information for a librarian if the given username and
	 * password are valid. If the username or password is invalid, throws an
	 * IllegalArgumentException.
	 *
	 * @param username
	 *            Username of the librarian that wishes to log in
	 * @param password
	 *            Password of the librarian that wishes to log in
	 * @return The librarian's account information
	 */
	public Librarian logIn(String username, String password) {
		// Get librarian by username
		Librarian librarian = librarianRepository.findLibrarianByUsername(username);
		if (librarian == null) {
			throw new IllegalArgumentException("Invalid username.");
		}

		// Check password
		if (librarian.getPasswordHash().equals(password)) {
			return librarian;
		} else {
			throw new IllegalArgumentException("Invalid password.");
		}
	}

	/**
	 * Returns the account information for the librarian with the given
	 * username.
	 *
	 * Throws an IllegalArgumentException if there is no librarian with the
	 * given username.
	 *
	 * @param username
	 *            Username of an existing librarian
	 * @return Account information for the librarian
	 */
	@Transactional
	public Librarian getLibrarianByUsername(String username) {
		return getNonNullLibrarianFromRepo(username);
	}

	/**
	 * Returns the account information for the librarian with the given ID.
	 *
	 * Throws an IllegalArgumentException if there is no librarian with the
	 * given ID.
	 *
	 * @param id
	 *            Primary key of an existing librarian
	 * @return Account information for the librarian
	 */
	@Transactional
	public Librarian getLibrarianById(int id) {
		return getNonNullLibrarianFromRepo(id);
	}

	/**
	 * Returns the account information for all librarians.
	 *
	 * @return List of all librarians
	 */
	@Transactional
	public Iterable<Librarian> getAllLibrarians() {
		return librarianRepository.findAll();
	}

	/**
	 * Updates the account information for the librarian with the given ID.
	 *
	 * Throws an IllegalArgumentException if there is no librarian with the
	 * given ID, if the full name is empty, if the username is empty, if the
	 * username is already taken by another librarian, or if the password is
	 * less than 8 characters in length.
	 *
	 * @param id
	 *            Primary key of an existing librarian
	 * @param newFullName
	 *            New full name for the librarian
	 * @param newUsername
	 *            New username for the librarian
	 * @param newPasswordHash
	 *            New password for the librarian
	 * @return Updated account information
	 */
	@Transactional
	public Librarian updateLibrarian(Integer id, String newFullName, String newUsername, String newPasswordHash) {
		Librarian librarian = getNonNullLibrarianFromRepo(id);
		List<String> errorMessage = validateUpdatedLibrarianCredentials(id, newFullName, newUsername, newPasswordHash);
		if (errorMessage.size() > 0) {
			throw new IllegalArgumentException(String.join(" ", errorMessage));
		}

		librarian.setFullName(newFullName);
		librarian.setUsername(newUsername);
		librarian.setPasswordHash(newPasswordHash);
		librarianRepository.save(librarian);

		return librarian;
	}

	/**
	 * Validates the credentials for a librarian that wishes to update their
	 * account information.
	 *
	 * Throws an IllegalArgumentException if the full name is empty, if the
	 * username is empty, if the username is already taken by another librarian,
	 * or if the password is less than 8 characters in length.
	 *
	 * @param id
	 *            Primary key of an existing librarian
	 * @param fullName
	 *            New full name
	 * @param username
	 *            New username
	 * @param password
	 *            New password
	 * @return List of error messages (an empty list if there are none)
	 */
	private ArrayList<String> validateUpdatedLibrarianCredentials(Integer id, String fullName, String username,
			String password) {
		ArrayList<String> errorMessage = new ArrayList<String>();
		// Full name not empty
		if (fullName == null || fullName.isBlank()) {
			throw new IllegalArgumentException("Full name cannot be empty.");
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
			Librarian librarianWithSameUsername = librarianRepository.findLibrarianByUsername(username);
			if (librarianWithSameUsername != null && librarianWithSameUsername.getId() != id) {
				errorMessage.add("Username already taken.");
			}

		}

		return errorMessage;
	}

	/**
	 * Validates the credentials for a librarian that wishes to register.
	 *
	 * Throws an IllegalArgumentException if the full name is empty, if the
	 * username is empty, if the username is already taken by another librarian,
	 * or if the password is less than 8 characters in length.
	 *
	 * @param fullName
	 *            Full name of the new librarian
	 * @param username
	 *            Username of the new librarian
	 * @param password
	 *            Password of the new librarian
	 * @return List of error messages (an empty list if there are none)
	 */
	private ArrayList<String> validateNewLibrarianCredentials(String fullName, String username, String password) {
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

	/**
	 * Retrieves the librarian with the given ID. If there is no such librarian,
	 * throws an IllegalArgumentException.
	 *
	 * @param id
	 *            Primary key of an existing librarian
	 * @return Account information for the librarian
	 */
	private Librarian getNonNullLibrarianFromRepo(int id) {
		Librarian librarian = librarianRepository.findLibrarianById(id);

		if (librarian == null) {
			throw new IllegalArgumentException("Librarian with ID \"" + id + "\" not found.");
		}
		return librarian;
	}

	/**
	 * Retrieves the librarian with the given username. If there is no such
	 * librarian, throws an IllegalArgumentException.
	 *
	 * @param username
	 *            Username of an existing librarian
	 * @return Account information for the librarian
	 */
	private Librarian getNonNullLibrarianFromRepo(String username) {
		Librarian librarian = librarianRepository.findLibrarianByUsername(username);

		if (librarian == null) {
			throw new IllegalArgumentException("Librarian with username \"" + username + "\" not found.");
		}
		return librarian;
	}
}
