package ca.mcgill.ecse321.onlinelibrary.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

	@BeforeEach
	public void setMockOuput() {
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(librarianRepository.save(any(Librarian.class))).thenAnswer(returnParameterAsAnswer);
	}

	@Test
	public void testCreateLibrarian() {
		String fullName = "Jocasta Nu";
		String username = "jocasta.nu";
		String password = "validpass123";

		Librarian librarian = librarianService.createLibrarian(fullName, username, password);

		assertNotNull(librarian);
		assertEquals(fullName, librarian.getFullName());
		assertEquals(username, librarian.getUsername());
		// TODO Update this check if/when password hashing is implemented
		assertEquals(password, librarian.getPasswordHash());
		assertEquals(false, librarian.isHead());
	}
}
