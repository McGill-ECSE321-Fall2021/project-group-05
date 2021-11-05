package ca.mcgill.ecse321.onlinelibrary.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

	private final int HEAD_ID = 1;
	private final int REG_ID = 2;
	private final int INVALID_ID = 999;
	private final String HEAD_FULL_NAME = "Jocasta Nu";
	private final String REG_FULL_NAME = "Alfred Pennyworth";
	private final String HEAD_USERNAME = "jocasta.nu";
	private final String REG_USERNAME = "alfred.pennyworth";
	private final String INVALID_USERNAME = "nonexistent member";
	private final String HEAD_PASSWD = "j".repeat(MIN_PASSWD_LENGTH);
	private final String REG_PASSWD = "a".repeat(MIN_PASSWD_LENGTH);

	/**
	 * Assume the database contains
	 *
	 * <ul>
	 * <li>one head librarian with ID HEAD_ID, full name HEAD_FULL_NAME,
	 * username HEAD_USERNAME, and password HEAD_PASSWD
	 * <li>one regular librarian with ID REG_ID, full name REG_FULL_NAME,
	 * username REG_USERNAME, and password REG_PASSWD
	 * </ul>
	 */
	@BeforeEach
	public void setMockOuput() {
		lenient().when(librarianRepository.findLibrarianByUsername(any(String.class)))
		.thenAnswer((InvocationOnMock invocation) -> {
			Librarian librarian = null;
			if (HEAD_USERNAME.equals(invocation.getArgument(0))) {
				librarian = new Librarian(HEAD_FULL_NAME, HEAD_USERNAME, HEAD_PASSWD, true);
			} else if (REG_USERNAME.equals(invocation.getArgument(0))) {
				librarian = new Librarian(REG_FULL_NAME, REG_USERNAME, REG_PASSWD, false);
			}
			return librarian;
		});

		lenient().when(librarianRepository.findLibrarianById(any(int.class)))
		.thenAnswer((InvocationOnMock invocation) -> {
			Librarian librarian = null;
			if (HEAD_ID == (int) invocation.getArgument(0)) {
				librarian = new Librarian(HEAD_FULL_NAME, HEAD_USERNAME, HEAD_PASSWD, true);
			} else if (REG_ID == (int) invocation.getArgument(0)) {
				librarian = new Librarian(REG_FULL_NAME, REG_USERNAME, REG_PASSWD, false);
			}
			return librarian;
		});
	}

	@Test
	public void testDeleteLibrarianByUsername() {
		// TEST
		// assertEquals(8, MIN_PASSWD_LENGTH);

		librarianService.deleteLibrarianByUsername(REG_USERNAME);

		verify(librarianRepository, times(1)).delete(argThat((Librarian l) -> REG_USERNAME.equals(l.getUsername())));
		verify(librarianRepository, times(0)).delete(argThat((Librarian l) -> !REG_USERNAME.equals(l.getUsername())));
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
		librarianService.deleteLibrarianById(REG_ID);

		// Verify the username because the findLibrarianById() stub does not set the ID
		verify(librarianRepository, times(1)).delete(argThat((Librarian l) -> REG_USERNAME.equals(l.getUsername())));
		verify(librarianRepository, times(0)).delete(argThat((Librarian l) -> !REG_USERNAME.equals(l.getUsername())));
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

	public void assertContains(String expected, String actual) {
		if (!actual.contains(expected)) {
			String msg = String.format("Expected message containing \"%s\" but received message \"%s\"", expected,
					actual);
			fail(msg);
		}
	}
}
