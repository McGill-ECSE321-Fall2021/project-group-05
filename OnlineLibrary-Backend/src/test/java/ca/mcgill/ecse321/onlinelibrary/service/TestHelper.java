package ca.mcgill.ecse321.onlinelibrary.service;

import static org.junit.jupiter.api.Assertions.fail;

public class TestHelper {

	/**
	 * Helper method to assert one string contains another. If
	 * !actual.contains(expected), fails with a helpful error message. Does not
	 * check for null inputs.
	 *
	 * @param expected
	 * @param actual
	 */
	public static void assertContains(String expected, String actual) {
		if (!actual.contains(expected)) {
			String msg = String.format("Expected message containing \"%s\" but received message \"%s\"", expected,
					actual);
			fail(msg);
		}
	}
}
