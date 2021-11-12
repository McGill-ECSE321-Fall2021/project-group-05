package ca.mcgill.ecse321.onlinelibrary.service;

import ca.mcgill.ecse321.onlinelibrary.dao.LibrarianRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.LibrarianShiftRepository;
import ca.mcgill.ecse321.onlinelibrary.model.Librarian;
import ca.mcgill.ecse321.onlinelibrary.model.LibrarianShift;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestLibrarianShiftService {
	@Mock
	private LibrarianShiftRepository librarianShiftRepository;
	@Mock
	private LibrarianRepository librarianRepository;

	@InjectMocks
	private LibrarianShiftService service;


	private static final LocalDate L_VALID_DATE = LocalDate.of(2020, Month.JANUARY, 1);
	private static final LocalDate L_INVALID_DATE = LocalDate.of(2020, Month.JANUARY, 2);
	private static final LocalTime L_START_TIME = LocalTime.of(11, 35);
	private static final LocalTime L_END_TIME = LocalTime.of(12, 38);
	private static final Date VALID_DATE = Date.valueOf(L_VALID_DATE);
	private static final Date INVALID_DATE = Date.valueOf(L_INVALID_DATE);
	private static final Time START_TIME = Time.valueOf(L_START_TIME);
	private static final Time END_TIME = Time.valueOf(L_END_TIME);
	private static final Librarian LIBRARIAN = Mockito.spy(new Librarian("Bob", "Ross", "Angry", false));
	private static final int VALID_LIBRARIAN_ID = 1;
	private static final int INVALID_LIBRARIAN_ID = -1;
	private static final int VALID_ID = 1;

	@BeforeEach
	public void setMockOuput() {
		Mockito.when(LIBRARIAN.getId()).thenReturn(VALID_LIBRARIAN_ID);
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
		lenient().when(librarianRepository.findLibrarianById(any(Integer.class)))
		.thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(VALID_LIBRARIAN_ID)) {
				return LIBRARIAN;
			} else {
				return null;
			}
		});
	}

	@Test
	public void testGetLibrarianShiftDateExisting() {
		ArrayList<LibrarianShift> shifts = null;

		try {
			shifts = service.getLibrarianShift(L_VALID_DATE);
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		}

		assertNotNull(shifts);
		assertEquals(1, shifts.size());
		assertEquals(VALID_DATE, shifts.get(0).getDate());
	}

	@Test
	public void testGetLibrarianShiftDateNonExisting() {
		ArrayList<LibrarianShift> shifts = null;

		try {
			shifts = service.getLibrarianShift(L_INVALID_DATE);
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		}

		assertNotNull(shifts);
		assertEquals(0, shifts.size());
	}

	@Test
	public void testGetLibrarianShiftDateEmpty() {
		Exception ex = assertThrows(IllegalArgumentException.class,
				() -> {service.getLibrarianShift(null);});
		assertTrue(ex.getMessage().contains("A date parameter is required."));
	}

	@Test
	public void testGetLibrarianShiftLibrarianExisting() {
		ArrayList<LibrarianShift> shifts = null;

		try {
			shifts = service.getLibrarianShift(VALID_LIBRARIAN_ID);
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		}

		assertNotNull(shifts);
		assertEquals(1, shifts.size());
		assertEquals(VALID_LIBRARIAN_ID, shifts.get(0).getLibrarian().getId());
		assertEquals(VALID_DATE, shifts.get(0).getDate());
	}

	@Test
	public void testGetLibrarianShiftLibrarianNonExisting() {
		ArrayList<LibrarianShift> shifts = null;

		try {
			shifts = service.getLibrarianShift(INVALID_LIBRARIAN_ID);
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		}

		assertNotNull(shifts);
		assertEquals(0, shifts.size());
	}

	@Test
	public void testGetLibrarianShiftAndDateExisting() {
		ArrayList<LibrarianShift> shifts = null;

		try {
			shifts = service.getLibrarianShift(VALID_LIBRARIAN_ID, L_VALID_DATE);
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		}

		assertNotNull(shifts);
		assertEquals(1, shifts.size());
		assertEquals(VALID_LIBRARIAN_ID, shifts.get(0).getLibrarian().getId());
		assertEquals(VALID_DATE, shifts.get(0).getDate());
	}

	@Test
	public void testGetLibrarianShiftAndDateNonExistingDate() {
		ArrayList<LibrarianShift> shifts = null;

		try {
			shifts = service.getLibrarianShift(VALID_LIBRARIAN_ID, L_INVALID_DATE);
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		}

		assertNotNull(shifts);
		assertEquals(0, shifts.size());
	}

	@Test
	public void testGetLibrarianShiftAndDateNonExistingLibrarian() {
		ArrayList<LibrarianShift> shifts = null;

		try {
			shifts = service.getLibrarianShift(INVALID_LIBRARIAN_ID, L_VALID_DATE);
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		}

		assertNotNull(shifts);
		assertEquals(0, shifts.size());
	}

	@Test
	public void testGetLibrarianShiftAndDateEmpty() {
		ArrayList<LibrarianShift> shifts = null;
		String error = null;

		try {
			shifts = service.getLibrarianShift(VALID_LIBRARIAN_ID, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(shifts);
		assertTrue(error.contains("A date parameter is required."));
	}

	@Test
	public void testCreateLibrarianShift() {
		LibrarianShift shift = null;

		try {
			shift = service.createLibrarianShift(L_INVALID_DATE, L_START_TIME, L_END_TIME, VALID_LIBRARIAN_ID);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			fail(e.getMessage());
		}

		assertNotNull(shift);
		verify(librarianShiftRepository, times(1)).save(shift);
		assertEquals(INVALID_DATE, shift.getDate());
		assertEquals(START_TIME, shift.getStartTime());
		assertEquals(END_TIME, shift.getEndTime());
	}

	@Test
	public void testCreateLibrarianShiftEmptyDate() {
		LibrarianShift shift = null;
		String error = null;

		try {
			shift = service.createLibrarianShift(null, L_START_TIME, L_END_TIME, VALID_LIBRARIAN_ID);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		verify(librarianShiftRepository, never()).save(any(LibrarianShift.class));
		verify(librarianRepository, never()).save(any(Librarian.class));
		assertNull(shift);
		assertTrue(error.contains("Date can't be empty."));
	}

	@Test
	public void testCreateLibrarianShiftEmptyStartTime() {
		LibrarianShift shift = null;
		String error = null;

		try {
			shift = service.createLibrarianShift(L_INVALID_DATE, null, L_END_TIME, VALID_LIBRARIAN_ID);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		verify(librarianShiftRepository, never()).save(any(LibrarianShift.class));
		verify(librarianRepository, never()).save(any(Librarian.class));
		assertNull(shift);
		assertTrue(error.contains("Start Time can't be empty."));
	}

	@Test
	public void testCreateLibrarianShiftEmptyEndTime() {
		LibrarianShift shift = null;
		String error = null;

		try {
			shift = service.createLibrarianShift(L_INVALID_DATE, L_START_TIME, null, VALID_LIBRARIAN_ID);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		verify(librarianShiftRepository, never()).save(any(LibrarianShift.class));
		verify(librarianRepository, never()).save(any(Librarian.class));
		assertNull(shift);
		assertTrue(error.contains("End Time can't be empty."));
	}

	@Test
	public void testCreateLibrarianShiftNonExistingLibrarian() {
		LibrarianShift shift = null;
		String error = null;

		try {
			shift = service.createLibrarianShift(L_INVALID_DATE, L_START_TIME, L_END_TIME, INVALID_LIBRARIAN_ID);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		verify(librarianShiftRepository, never()).save(any(LibrarianShift.class));
		verify(librarianRepository, never()).save(any(Librarian.class));
		assertNull(shift);
		assertTrue(error.contains("Librarian does not exist."));
	}

	@Test
	public void testCreateLibrarianShiftInvertedTimes() {
		LibrarianShift shift = null;
		String error = null;

		try {
			shift = service.createLibrarianShift(L_INVALID_DATE, L_END_TIME, L_START_TIME, VALID_LIBRARIAN_ID);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		verify(librarianShiftRepository, never()).save(any(LibrarianShift.class));
		verify(librarianRepository, never()).save(any(Librarian.class));
		assertNull(shift);
		assertTrue(error.contains("Start Time can't be after End Time."));
	}

	@Test
	public void testCreateLibrarianShiftOverlappingDuplicate() {
		LibrarianShift shift = null;
		String error = null;

		try {
			shift = service.createLibrarianShift(L_VALID_DATE, L_START_TIME, L_END_TIME, VALID_LIBRARIAN_ID);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}


		verify(librarianShiftRepository, never()).save(any(LibrarianShift.class));
		verify(librarianRepository, never()).save(any(Librarian.class));
		assertNull(shift);
		assertTrue(error.contains("An overlapping shift already exists for the assigned librarian."));
	}

	@Test
	public void testDeleteLibrarianShift() {
		try {
			service.deleteLibrarianShift(VALID_ID);
		} catch(Exception e) {
			fail(e.getMessage());
		}

		verify(librarianShiftRepository, times(1)).deleteById(VALID_ID);
	}
}
