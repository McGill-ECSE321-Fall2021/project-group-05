package ca.mcgill.ecse321.onlinelibrary.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321.onlinelibrary.dao.LibrarianRepository;
import ca.mcgill.ecse321.onlinelibrary.model.Librarian;

@ExtendWith(MockitoExtension.class)
public class TestLibrarianService {

	@Mock
	private LibrarianRepository librarianRepository;
	@InjectMocks
	private LibrarianService librarianService;

	// TODO Get this from application.properties
	private int MIN_PASSWD_LENGTH = 8;

	// Existing head librarian
	private final int HEAD_ID = 1;
	private final String HEAD_FULL_NAME = "Jocasta Nu";
	private final String HEAD_USERNAME = "jocasta.nu";
	private final String HEAD_PASSWD = "j".repeat(MIN_PASSWD_LENGTH);

	// Existing regular librarian
	private final int OLD_REG_ID = 2;
	private final String OLD_REG_FULL_NAME = "Alfred Pennyworth";
	private final String OLD_REG_USERNAME = "alfred.pennyworth";
	private final String OLD_REG_PASSWD = "a".repeat(MIN_PASSWD_LENGTH);

	// New regular librarian
	private final String NEW_REG_FULL_NAME = "Bilbo Baggins";
	private final String NEW_REG_USERNAME = "bilbo.baggins";
	private final String NEW_REG_PASSWD = "b".repeat(MIN_PASSWD_LENGTH);

	// Invalid credentials
	private final int INVALID_ID = 999;
	private final String INVALID_USERNAME = "nonexistent member";
	private final String SHORT_PASSWD = "c".repeat(MIN_PASSWD_LENGTH - 1);

	/**
	 * Assume the database already contains
	 *
	 * <ul>
	 * <li>one head librarian with ID HEAD_ID, full name HEAD_FULL_NAME,
	 * username HEAD_USERNAME, and password HEAD_PASSWD
	 * <li>one regular librarian with ID OLD_REG_ID, full name
	 * OLD_REG_FULL_NAME, username OLD_REG_USERNAME, and password OLD_REG_PASSWD
	 * </ul>
	 */
	@BeforeEach
	public void setMockOuput() {
		lenient().when(librarianRepository.existsLibrarianByUsername(any(String.class)))
		.thenAnswer((InvocationOnMock invocation) -> {
			String username = invocation.getArgument(0);
			return switch (username) {
				case HEAD_USERNAME, OLD_REG_USERNAME -> true;
				default -> false;
			};
		});

		lenient().when(librarianRepository.findLibrarianByUsername(any(String.class)))
		.thenAnswer((InvocationOnMock invocation) -> {
			String username = invocation.getArgument(0);
			return switch (username) {
				case HEAD_USERNAME -> new Librarian(HEAD_FULL_NAME, HEAD_USERNAME, HEAD_PASSWD, true);
				case OLD_REG_USERNAME -> new Librarian(OLD_REG_FULL_NAME, OLD_REG_USERNAME, OLD_REG_PASSWD,
						false);
				default -> null;
			};
		});

		lenient().when(librarianRepository.findLibrarianById(any(int.class)))
		.thenAnswer((InvocationOnMock invocation) -> {
			int id = (int) invocation.getArgument(0);
			return switch (id) {
				case HEAD_ID -> new Librarian(HEAD_FULL_NAME, HEAD_USERNAME, HEAD_PASSWD, true);
				case OLD_REG_ID -> new Librarian(OLD_REG_FULL_NAME, OLD_REG_USERNAME, OLD_REG_PASSWD, false);
				default -> null;
			};
		});

		lenient().when(librarianRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			List<Librarian> librarians = new ArrayList<Librarian>();
			librarians.add(new Librarian(HEAD_FULL_NAME, HEAD_USERNAME, HEAD_PASSWD, true));
			librarians.add(new Librarian(OLD_REG_FULL_NAME, OLD_REG_USERNAME, OLD_REG_PASSWD, false));
			return librarians;
		});
	}

	/**
	 * Successfully create librarian.
	 */
	@Test
	public void testCreateLibrarian() {
		Librarian librarian = librarianService.createLibrarian("  " + NEW_REG_FULL_NAME + "  ",
				"  " + NEW_REG_USERNAME + "  ", "  " + NEW_REG_PASSWD + "  ");

		assertNotNull(librarian);
		assertEquals(NEW_REG_FULL_NAME, librarian.getFullName());
		assertEquals(NEW_REG_USERNAME, librarian.getUsername());
		// TODO Update this check if/when password hashing is implemented
		assertEquals(NEW_REG_PASSWD, librarian.getPasswordHash());
		assertEquals(false, librarian.isHead());
	}

	/**
	 * We expect the LibrarianService to return the error "Full name cannot be
	 * empty." when a null full name is provided.
	 */
	@Test
	public void testCreateLibrarianNullFullName() {
		Exception error = assertThrows(IllegalArgumentException.class,
				() -> librarianService.createLibrarian(null, NEW_REG_USERNAME, NEW_REG_PASSWD));
		assertContains("Full name cannot be empty.", error.getMessage());
	}

	/**
	 * We expect the LibrarianService to return the error "Full name cannot be
	 * empty." when an empty full name is provided.
	 */
	@Test
	public void testCreateLibrarianEmptyFullName() {
		Exception error = assertThrows(IllegalArgumentException.class,
				() -> librarianService.createLibrarian("  ", NEW_REG_USERNAME, NEW_REG_PASSWD));
		assertContains("Full name cannot be empty.", error.getMessage());
	}

	/**
	 * We expect the LibrarianService to return the error "Username cannot be
	 * empty." when a null username is provided.
	 */
	@Test
	public void testCreateLibrarianNullUsername() {
		Exception error = assertThrows(IllegalArgumentException.class,
				() -> librarianService.createLibrarian(NEW_REG_FULL_NAME, null, NEW_REG_PASSWD));
		assertContains("Username cannot be empty.", error.getMessage());
	}

	/**
	 * We expect the LibrarianService to return the error "Username cannot be
	 * empty." when an empty username is provided.
	 */
	@Test
	public void testCreateLibrarianEmptyUsername() {
		Exception error = assertThrows(IllegalArgumentException.class,
				() -> librarianService.createLibrarian(NEW_REG_FULL_NAME, "  ", NEW_REG_PASSWD));
		assertContains("Username cannot be empty.", error.getMessage());
	}

	/**
	 * We expect the LibrarianService to return the error "Username already
	 * taken." when the username belongs to an existing librarian.
	 */
	@Test
	public void testCreateLibrarianDuplicateUsername() {
		Exception error = assertThrows(IllegalArgumentException.class,
				() -> librarianService.createLibrarian(NEW_REG_FULL_NAME, HEAD_USERNAME, NEW_REG_PASSWD));
		assertContains("Username already taken.", error.getMessage());
	}

	/**
	 * We expect the LibrarianService to return the error "Password must be at
	 * least 8 characters in length." when the password is null.
	 */
	@Test
	public void testCreateLibrarianNullPassword() {
		Exception error = assertThrows(IllegalArgumentException.class,
				() -> librarianService.createLibrarian(NEW_REG_FULL_NAME, NEW_REG_USERNAME, null));
		assertContains("Password must be at least 8 characters in length.", error.getMessage());
	}

	/**
	 * We expect the LibrarianService to return the error "Password must be at
	 * least 8 characters in length." when the password is empty.
	 */
	@Test
	public void testCreateLibrarianEmptyPassword() {
		Exception error = assertThrows(IllegalArgumentException.class,
				() -> librarianService.createLibrarian(NEW_REG_FULL_NAME, NEW_REG_USERNAME, "  "));
		assertContains("Password must be at least 8 characters in length.", error.getMessage());
	}

	/**
	 * We expect the LibrarianService to return the error "Password must be at
	 * least 8 characters in length." when the password contains only 7
	 * non-whitespace characters.
	 */
	@Test
	public void testCreateLibrarianShortPassword() {
		Exception error = assertThrows(IllegalArgumentException.class,
				() -> librarianService.createLibrarian(NEW_REG_FULL_NAME, NEW_REG_USERNAME, SHORT_PASSWD + "   "));
		assertContains("Password must be at least 8 characters in length.", error.getMessage());
	}

	/**
	 * Successfully get librarian by ID.
	 */
	@Test
	public void testGetLibrarianById() {
		Librarian librarian = librarianService.getLibrarianById(HEAD_ID);

		assertNotNull(librarian);
		assertEquals(HEAD_FULL_NAME, librarian.getFullName());
		assertEquals(HEAD_USERNAME, librarian.getUsername());
		assertTrue(librarian.isHead());
	}

	/**
	 * We expect the error message "Librarian with ID {id} not found." when
	 * there is no librarian with the given ID.
	 */
	@Test
	public void testGetLibrarianByIdNotFound() {
		Exception error = assertThrows(IllegalArgumentException.class,
				() -> librarianService.getLibrarianById(INVALID_ID));
		assertContains("Librarian with ID \"" + INVALID_ID + "\" not found.", error.getMessage());
	}

	/**
	 * Successfully get librarian by username.
	 */
	@Test
	public void testGetLibrarianByUsername() {
		Librarian librarian = librarianService.getLibrarianById(OLD_REG_ID);

		assertNotNull(librarian);
		assertEquals(OLD_REG_FULL_NAME, librarian.getFullName());
		assertEquals(OLD_REG_USERNAME, librarian.getUsername());
		assertFalse(librarian.isHead());
	}

	/**
	 * We expect the error message "Librarian with username {username} not
	 * found." when there is no librarian with the given username.
	 */
	@Test
	public void testGetLibrarianByUsernameNotFound() {
		Exception error = assertThrows(IllegalArgumentException.class,
				() -> librarianService.getLibrarianByUsername(INVALID_USERNAME));
		assertContains("Librarian with username \"" + INVALID_USERNAME + "\" not found.", error.getMessage());
	}

	/**
	 * Successfully get all librarians.
	 */
	@Test
	public void testGetAllLibrarians() {
		Iterable<Librarian> librarians = librarianService.getAllLibrarians();

		assertNotNull(librarians);

		// Check that list contains all and only the correct librarians
		int count = 0;
		boolean headFound = false;
		boolean regFound = false;
		for (Librarian l : librarians) {
			count++;
			if (HEAD_FULL_NAME.equals(l.getFullName()) && HEAD_USERNAME.equals(l.getUsername()) && l.isHead()) {
				headFound = true;
			}
			if (OLD_REG_FULL_NAME.equals(l.getFullName()) && OLD_REG_USERNAME.equals(l.getUsername()) && !l.isHead()) {
				regFound = true;
			}
		}
		assertEquals(2, count);
		assertTrue(headFound);
		assertTrue(regFound);
	}

	@Test
	public void testDeleteLibrarianByUsername() {
		librarianService.deleteLibrarianByUsername(OLD_REG_USERNAME);

		verify(librarianRepository, times(1))
		.delete(argThat((Librarian l) -> OLD_REG_USERNAME.equals(l.getUsername())));
		verify(librarianRepository, times(0))
		.delete(argThat((Librarian l) -> !OLD_REG_USERNAME.equals(l.getUsername())));
	}

	/**
	 * We expect the error message "Librarian with username {username} not
	 * found." when there is no librarian with the given username.
	 */
	@Test
	public void testDeleteLibrarianByUsernameNotFound() {
		Exception error = assertThrows(IllegalArgumentException.class,
				() -> librarianService.deleteLibrarianByUsername(INVALID_USERNAME));
		assertContains("Librarian with username \"" + INVALID_USERNAME + "\" not found.", error.getMessage());
		verify(librarianRepository, times(0)).delete(any(Librarian.class));
	}

	/**
	 * We expect the error message "Librarian with username null not found."
	 * when the given username is null.
	 */
	@Test
	public void testDeleteLibrarianByUsernameNull() {
		Exception error = assertThrows(IllegalArgumentException.class,
				() -> librarianService.deleteLibrarianByUsername(null));
		assertContains("Librarian with username \"" + null + "\" not found.", error.getMessage());
		verify(librarianRepository, times(0)).delete(any(Librarian.class));
	}

	/**
	 * We expect the error message "Cannot delete head librarian." when the
	 * librarian being deleted is the head librarian.
	 */
	@Test
	public void testDeleteLibrarianByUsernameHead() {
		Exception error = assertThrows(IllegalArgumentException.class,
				() -> librarianService.deleteLibrarianByUsername(HEAD_USERNAME));
		assertContains("Cannot delete head librarian.", error.getMessage());
		verify(librarianRepository, times(0)).delete(any(Librarian.class));
	}

	@Test
	public void testDeleteLibrarianById() {
		librarianService.deleteLibrarianById(OLD_REG_ID);

		// Verify the username because the findLibrarianById() stub does not set
		// the ID
		verify(librarianRepository, times(1))
		.delete(argThat((Librarian l) -> OLD_REG_USERNAME.equals(l.getUsername())));
		verify(librarianRepository, times(0))
		.delete(argThat((Librarian l) -> !OLD_REG_USERNAME.equals(l.getUsername())));
	}

	/**
	 * We expect the error message "Librarian with ID {id} not found." when
	 * there is no librarian with the given ID.
	 */
	@Test
	public void testDeleteLibrarianByIdNotFound() {
		Exception error = assertThrows(IllegalArgumentException.class,
				() -> librarianService.deleteLibrarianById(INVALID_ID));
		assertContains("Librarian with ID \"" + INVALID_ID + "\" not found.", error.getMessage());
	}

	/**
	 * We expect the error message "Cannot delete head librarian." when the
	 * librarian being deleted is the head librarian.
	 */
	@Test
	public void testDeleteLibrarianByIdHead() {
		Exception error = assertThrows(IllegalArgumentException.class,
				() -> librarianService.deleteLibrarianById(HEAD_ID));
		assertContains("Cannot delete head librarian.", error.getMessage());
		verify(librarianRepository, times(0)).delete(any(Librarian.class));
	}

	/**
	 * Helper method to assert one string contains another. If
	 * !actual.contains(expected), fails with a helpful error message. Does not
	 * check for null inputs.
	 *
	 * @param expected
	 * @param actual
	 */
	public void assertContains(String expected, String actual) {
		if (!actual.contains(expected)) {
			String msg = String.format("Expected message containing \"%s\" but received message \"%s\"", expected,
					actual);
			fail(msg);
		}
	}
}
