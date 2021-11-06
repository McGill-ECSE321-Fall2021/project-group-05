package ca.mcgill.ecse321.onlinelibrary.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;

import org.mockito.invocation.InvocationOnMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.onlinelibrary.dao.LibrarianShiftRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.LibrarianRepository;
import ca.mcgill.ecse321.onlinelibrary.model.LibrarianShift;
import ca.mcgill.ecse321.onlinelibrary.model.Librarian;

@ExtendWith(MockitoExtension.class)
public class TestLibrarianShift {
	@Mock
	private LibrarianShiftRepository librarianShiftRepository;
	@Mock
	private LibrarianRepository librarianRepository;
	
	@InjectMocks
	private LibrarianShiftService service;
	
	private static final Date VALID_DATE = Date.valueOf(LocalDate.of(2020, Month.JANUARY, 1));
	private static final Date INVALID_DATE = Date.valueOf(LocalDate.of(2020, Month.JANUARY, 2));
	private static final Time START_TIME = Time.valueOf(LocalTime.of(11, 35));
	private static final Time END_TIME = Time.valueOf(LocalTime.of(12, 38));
	private static final Librarian LIBRARIAN = new Librarian("Bob", "Ross", "Angry", false);
	private static final int VALID_LIBRARIAN_ID = 1;
	private static final int INVALID_LIBRARIAN_ID = -1;
	private static final int VALID_ID = 1;
	
	@BeforeEach
	public void setMockOuput() {
		LIBRARIAN.setTestId(VALID_LIBRARIAN_ID);
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(librarianShiftRepository.save(any(LibrarianShift.class))).then(returnParameterAsAnswer);
		lenient().when(librarianShiftRepository.findLibrarianShiftByDate(any(Date.class)))
		.thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(VALID_DATE)) {
				ArrayList<LibrarianShift> shifts = new ArrayList<LibrarianShift>();
				shifts.add(new LibrarianShift(VALID_DATE, START_TIME, END_TIME, LIBRARIAN));
				return shifts;
			} else {
				return new ArrayList<LibrarianShift>();
			}
		});
		lenient().when(librarianShiftRepository.findLibrarianShiftByLibrarianId(any(Integer.class)))
		.thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(LIBRARIAN.getId())) {
				ArrayList<LibrarianShift> shifts = new ArrayList<LibrarianShift>();
				shifts.add(new LibrarianShift(VALID_DATE, START_TIME, END_TIME, LIBRARIAN));
				return shifts;
			} else {
				return new ArrayList<LibrarianShift>();
			}
		});
		lenient().when(librarianShiftRepository.findLibrarianShiftByDateAndLibrarianId(any(Date.class), any(Integer.class)))
		.thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(VALID_DATE) && invocation.getArgument(1).equals(LIBRARIAN.getId())) {
				ArrayList<LibrarianShift> shifts = new ArrayList<LibrarianShift>();
				shifts.add(new LibrarianShift(VALID_DATE, START_TIME, END_TIME, LIBRARIAN));
				return shifts;
			} else {
				return new ArrayList<LibrarianShift>();
			}
		});
		lenient().when(librarianRepository.findById(any(Integer.class)))
		.thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(VALID_LIBRARIAN_ID)) {
				return LIBRARIAN;
			} else {
				return null;
			}
		});
	}
	
	@Test
	public void getLibrarianShiftDateExisting() {
		ArrayList<LibrarianShift> shifts = null;

		try {
			shifts = service.getLibrarianShift(VALID_DATE);
		} catch (IllegalArgumentException e) {
			fail();
		}

		assertNotNull(shifts);
		assertEquals(1, shifts.size());
		assertEquals(VALID_DATE, shifts.get(0).getDate());
	}
	
	@Test
	public void getLibrarianShiftDateNonExisting() {
		ArrayList<LibrarianShift> shifts = null;

		try {
			shifts = service.getLibrarianShift(INVALID_DATE);
		} catch (IllegalArgumentException e) {
			fail();
		}

		assertNotNull(shifts);
		assertEquals(0, shifts.size());
	}
	
	@Test
	public void getLibrarianShiftDateEmpty() {
		ArrayList<LibrarianShift> shifts = null;
		String error = null;

		try {
			shifts = service.getLibrarianShift(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(shifts);
		assertNotNull(error);
		assertTrue(error.contains("A date parameter is required."));
	}
	
	@Test
	public void getLibrarianShiftLibrarianExisting() {
		ArrayList<LibrarianShift> shifts = null;

		try {
			shifts = service.getLibrarianShift(VALID_LIBRARIAN_ID);
		} catch (IllegalArgumentException e) {
			fail();
		}

		assertNotNull(shifts);
		assertEquals(1, shifts.size());
		assertEquals(VALID_LIBRARIAN_ID, shifts.get(0).getLibrarian().getId());
		assertEquals(VALID_DATE, shifts.get(0).getDate());
	}
	
	@Test
	public void getLibrarianShiftLibrarianNonExisting() {
		ArrayList<LibrarianShift> shifts = null;

		try {
			shifts = service.getLibrarianShift(INVALID_LIBRARIAN_ID);
		} catch (IllegalArgumentException e) {
			fail();
		}

		assertNotNull(shifts);
		assertEquals(0, shifts.size());
	}
	
	@Test
	public void getLibrarianShiftAndDateExisting() {
		ArrayList<LibrarianShift> shifts = null;

		try {
			shifts = service.getLibrarianShift(VALID_LIBRARIAN_ID, VALID_DATE);
		} catch (IllegalArgumentException e) {
			fail();
		}

		assertNotNull(shifts);
		assertEquals(1, shifts.size());
		assertEquals(VALID_LIBRARIAN_ID, shifts.get(0).getLibrarian().getId());
		assertEquals(VALID_DATE, shifts.get(0).getDate());
	}
	
	@Test
	public void getLibrarianShiftAndDateNonExistingDate() {
		ArrayList<LibrarianShift> shifts = null;

		try {
			shifts = service.getLibrarianShift(VALID_LIBRARIAN_ID, INVALID_DATE);
		} catch (IllegalArgumentException e) {
			fail();
		}

		assertNotNull(shifts);
		assertEquals(0, shifts.size());
	}
	
	@Test
	public void getLibrarianShiftAndDateNonExistingLibrarian() {
		ArrayList<LibrarianShift> shifts = null;

		try {
			shifts = service.getLibrarianShift(INVALID_LIBRARIAN_ID, VALID_DATE);
		} catch (IllegalArgumentException e) {
			fail();
		}

		assertNotNull(shifts);
		assertEquals(0, shifts.size());
	}
	
	@Test
	public void getLibrarianShiftAndDateEmpty() {
		ArrayList<LibrarianShift> shifts = null;
		String error = null;

		try {
			shifts = service.getLibrarianShift(VALID_LIBRARIAN_ID, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(shifts);
		assertNotNull(error);
		assertTrue(error.contains("A date parameter is required."));
	}
	
	@Test
	public void createLibrarianShift() {
		LibrarianShift shift = null;

		try {
			shift = service.createLibrarianShift(INVALID_DATE, START_TIME, END_TIME, LIBRARIAN);
		} catch (IllegalArgumentException e) {
			fail();
		}

		assertNotNull(shift);
		assertEquals(INVALID_DATE, shift.getDate());
		assertEquals(START_TIME, shift.getStartTime());
		assertEquals(END_TIME, shift.getEndTime());
	}
	
	@Test
	public void createLibrarianShiftEmptyDate() {
		LibrarianShift shift = null;
		String error = null;

		try {
			shift = service.createLibrarianShift(null, START_TIME, END_TIME, LIBRARIAN);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(shift);
		assertNotNull(error);
		assertTrue(error.contains("Date can't be empty."));
	}
	
	@Test
	public void createLibrarianShiftEmptyStartTime() {
		LibrarianShift shift = null;
		String error = null;

		try {
			shift = service.createLibrarianShift(INVALID_DATE, null, END_TIME, LIBRARIAN);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(shift);
		assertNotNull(error);
		assertTrue(error.contains("Start Time can't be empty."));
	}
	
	@Test
	public void createLibrarianShiftEmptyEndTime() {
		LibrarianShift shift = null;
		String error = null;

		try {
			shift = service.createLibrarianShift(INVALID_DATE, START_TIME, null, LIBRARIAN);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(shift);
		assertNotNull(error);
		assertTrue(error.contains("End Time can't be empty."));
	}
	
	@Test
	public void createLibrarianShiftEmptyLibrarian() {
		LibrarianShift shift = null;
		String error = null;

		try {
			shift = service.createLibrarianShift(INVALID_DATE, START_TIME, END_TIME, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(shift);
		assertNotNull(error);
		assertTrue(error.contains("Librarian can't be empty."));
	}
	
	@Test
	public void createLibrarianShiftInvertedTimes() {
		LibrarianShift shift = null;
		String error = null;

		try {
			shift = service.createLibrarianShift(INVALID_DATE, END_TIME, START_TIME, LIBRARIAN);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(shift);
		assertNotNull(error);
		assertTrue(error.contains("Start Time can't be after End Time."));
	}
	
	@Test
	public void createLibrarianShiftOverlappingDuplicate() {
		LibrarianShift shift = null;
		String error = null;

		try {
			shift = service.createLibrarianShift(VALID_DATE, START_TIME, END_TIME, LIBRARIAN);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(shift);
		assertNotNull(error);
		assertTrue(error.contains("An overlapping shift already exists for the assigned librarian."));
	}
	
	@Test
	public void deleteLibrarianShift() {
		try {
			service.deleteLibrarianShift(VALID_ID);
		} catch(Exception e) {
			fail();
		}
	}
}
