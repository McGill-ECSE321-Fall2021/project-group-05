package ca.mcgill.ecse321.onlinelibrary.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import ca.mcgill.ecse321.onlinelibrary.dao.LibrarianRepository;
import ca.mcgill.ecse321.onlinelibrary.model.Librarian;

public class TestLibrarianService extends TestService {

	@Mock
	private LibrarianRepository librarianRepository;

	@InjectMocks
	private LibrarianService librarianService;

	private static final String VALID_FULL_NAME = "Jocasta Nu";
	private static final String VALID_USERNAME = "jocasta.nu";
	private static final String TAKEN_USERNAME = "head.librarian";
	private static final String VALID_PASSWORD = "totallysecurepassword12345";
	private static final String SHORT_PASSWORD = "pass123";

	@BeforeEach
	public void setMockOuput() {
		lenient().when(librarianRepository.existsLibrarianByUsername(any(String.class)))
		.thenAnswer((InvocationOnMock invocation) -> {
			if (TAKEN_USERNAME.equals(invocation.getArgument(0))) {
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
		Librarian librarian = librarianService.createLibrarian(VALID_FULL_NAME, VALID_USERNAME, VALID_PASSWORD);

		assertNotNull(librarian);
		assertEquals(VALID_FULL_NAME, librarian.getFullName());
		assertEquals(VALID_USERNAME, librarian.getUsername());
		// TODO Update this check if/when password hashing is implemented
		assertEquals(VALID_PASSWORD, librarian.getPasswordHash());
		assertEquals(false, librarian.isHead());
	}

	/**
	 * We expect the LibrarianService to return the error "Full name cannot be
	 * empty." when a null full name is provided.
	 */
	@Test
	public void testCreateLibrarianNullFullName() {
		Exception error = assertThrows(IllegalArgumentException.class,
				() -> librarianService.createLibrarian(null, VALID_USERNAME, VALID_PASSWORD));
		assertContains("Full name cannot be empty.", error.getMessage());
	}

	/**
	 * We expect the LibrarianService to return the error "Full name cannot be
	 * empty." when an empty full name is provided.
	 */
	@Test
	public void testCreateLibrarianEmptyFullName() {
		Exception error = assertThrows(IllegalArgumentException.class,
				() -> librarianService.createLibrarian("", VALID_USERNAME, VALID_PASSWORD));
		assertContains("Full name cannot be empty.", error.getMessage());
	}

	/**
	 * We expect the LibrarianService to return the error "Username cannot be
	 * empty." when a null username is provided.
	 */
	@Test
	public void testCreateLibrarianNullUsername() {
		Exception error = assertThrows(IllegalArgumentException.class,
				() -> librarianService.createLibrarian(VALID_FULL_NAME, null, VALID_PASSWORD));
		assertContains("Username cannot be empty.", error.getMessage());
	}

	/**
	 * We expect the LibrarianService to return the error "Username cannot be
	 * empty." when an empty username is provided.
	 */
	@Test
	public void testCreateLibrarianEmptyUsername() {
		Exception error = assertThrows(IllegalArgumentException.class,
				() -> librarianService.createLibrarian(VALID_FULL_NAME, "", VALID_PASSWORD));
		assertContains("Username cannot be empty.", error.getMessage());
	}

	/**
	 * We expect the LibrarianService to return the error "Username already
	 * taken." when the username belongs to an existing librarian.
	 */
	@Test
	public void testCreateLibrarianDuplicateUsername() {
		Exception error = assertThrows(IllegalArgumentException.class,
				() -> librarianService.createLibrarian(VALID_FULL_NAME, TAKEN_USERNAME, VALID_PASSWORD));
		assertContains("Username already taken.", error.getMessage());
	}

	/**
	 * We expect the LibrarianService to return the error "Password must be at
	 * least 8 characters in length." when the password is null.
	 */
	@Test
	public void testCreateLibrarianNullPassword() {
		Exception error = assertThrows(IllegalArgumentException.class,
				() -> librarianService.createLibrarian(VALID_FULL_NAME, VALID_USERNAME, null));
		assertContains("Password must be at least 8 characters in length.", error.getMessage());
	}

	/**
	 * We expect the LibrarianService to return the error "Password must be at
	 * least 8 characters in length." when the password is empty.
	 */
	@Test
	public void testCreateLibrarianEmptyPassword() {
		Exception error = assertThrows(IllegalArgumentException.class,
				() -> librarianService.createLibrarian(VALID_FULL_NAME, VALID_USERNAME, ""));
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
				() -> librarianService.createLibrarian(VALID_FULL_NAME, VALID_USERNAME, SHORT_PASSWORD));
		assertContains("Password must be at least 8 characters in length.", error.getMessage());
	}
}
