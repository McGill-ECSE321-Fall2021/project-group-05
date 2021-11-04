package ca.mcgill.ecse321.onlinelibrary.service;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestService {

	public void assertContains(String expected, String actual) {
		if (!actual.contains(expected)) {
			String msg = String.format("Expected message containing \"%s\" but received message \"%s\"", expected,
					actual);
			fail(msg);
		}
	}
}
