package ca.mcgill.ecse321.onlinelibrary.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
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

	private final String NEW_REG_FULL_NAME = "Alfred Pennyworth";
	private final String HEAD_USERNAME = "jocasta.nu";
	private final String NEW_REG_USERNAME = "alfred.pennyworth";
	private final String NEW_REG_PASSWD = "a".repeat(MIN_PASSWD_LENGTH);
	private final String SHORT_PASSWD = "b".repeat(MIN_PASSWD_LENGTH - 1);

	/**
	 * Assume the database already contains
	 *
	 * <ul>
	 * <li>one head librarian with ID HEAD_ID, full name HEAD_FULL_NAME,
	 * username HEAD_USERNAME, and password HEAD_PASSWD
	 * </ul>
	 */
	@BeforeEach
	public void setMockOuput() {
		lenient().when(librarianRepository.existsLibrarianByUsername(any(String.class)))
		.thenAnswer((InvocationOnMock invocation) -> {
			if (HEAD_USERNAME.equals(invocation.getArgument(0))) {
				return true;
			} else {
				return false;
			}
		});
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(librarianRepository.save(any(Librarian.class))).thenAnswer(returnParameterAsAnswer);
	}

	/**
	 * Successfully create librarian.
	 */
	@Test
	public void testCreateLibrarian() {
		Librarian librarian = librarianService.createLibrarian(NEW_REG_FULL_NAME, NEW_REG_USERNAME, NEW_REG_PASSWD);

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
				() -> librarianService.createLibrarian("", NEW_REG_USERNAME, NEW_REG_PASSWD));
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
				() -> librarianService.createLibrarian(NEW_REG_FULL_NAME, "", NEW_REG_PASSWD));
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
				() -> librarianService.createLibrarian(NEW_REG_FULL_NAME, NEW_REG_USERNAME, ""));
		assertContains("Password must be at least 8 characters in length.", error.getMessage());
	}

	/**
	 * We expect the LibrarianService to return the error "Password must be at
	 * least 8 characters in length." when the password is contains 7
	 * characters.
	 */
	@Test
	public void testCreateLibrarianShortPassword() {
		Exception error = assertThrows(IllegalArgumentException.class,
				() -> librarianService.createLibrarian(NEW_REG_FULL_NAME, NEW_REG_USERNAME, SHORT_PASSWD));
		assertContains("Password must be at least 8 characters in length.", error.getMessage());
	}

	public void assertContains(String expected, String actual) {
		if (!actual.contains(expected)) {
			String msg = String.format("Expected message containing \"%s\" but received message \"%s\"", expected,
					actual);
			fail(msg);
		}
	}
}
