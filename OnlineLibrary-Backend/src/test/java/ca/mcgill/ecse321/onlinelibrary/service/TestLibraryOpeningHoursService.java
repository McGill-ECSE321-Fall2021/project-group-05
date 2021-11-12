package ca.mcgill.ecse321.onlinelibrary.service;

import ca.mcgill.ecse321.onlinelibrary.dao.HolidayRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.LibraryOpeningHoursRepository;
import ca.mcgill.ecse321.onlinelibrary.model.Holiday;
import ca.mcgill.ecse321.onlinelibrary.model.LibraryOpeningHours;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
public class TestLibraryOpeningHoursService {
	@Mock
	private HolidayRepository holidayRepository;
	@Mock
	private LibraryOpeningHoursRepository libraryOpeningHoursRepository;

	@InjectMocks
	private LibraryOpeningHoursService service;
	
	private static final LocalDate L_OPENING_DATE = LocalDate.of(2020, Month.JANUARY, 01);
	private static final LocalDate L_ENDING_DATE = LocalDate.of(2020, Month.JANUARY, 31);
	private static final LocalDate L_INVALID_DATE = LocalDate.of(2020, Month.MAY, 28);
	private static final LocalDate L_HOLIDAY_DATE = LocalDate.of(2020, Month.JANUARY, 01);
	private static final LocalTime L_START_TIME = LocalTime.of(11, 35);
	private static final LocalTime L_END_TIME = LocalTime.of(12, 38);
	private static final Date OPENING_DATE = Date.valueOf(L_OPENING_DATE);
	private static final Date ENDING_DATE = Date.valueOf(L_ENDING_DATE);
	private static final Date HOLIDAY_DATE = Date.valueOf(L_HOLIDAY_DATE);
	private static final Time START_TIME = Time.valueOf(L_START_TIME);
	private static final Time END_TIME = Time.valueOf(L_END_TIME);
	private static final int VALID_ID = 1;

	@BeforeEach
	public void setMockOuput() {
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(libraryOpeningHoursRepository.save(any(LibraryOpeningHours.class))).then(returnParameterAsAnswer);
		lenient().when(libraryOpeningHoursRepository.findLibraryOpeningHoursByDate(any(Date.class)))
		.thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(OPENING_DATE)) {
				ArrayList<LibraryOpeningHours> openingHours = new ArrayList<LibraryOpeningHours>();
				openingHours.add(new LibraryOpeningHours(OPENING_DATE, START_TIME, END_TIME));
				return openingHours;
			} else {
				return new ArrayList<LibraryOpeningHours>();
			}
		});
		lenient().when(libraryOpeningHoursRepository.findLibraryOpeningHoursByDateBetween(any(Date.class), any(Date.class)))
		.thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(OPENING_DATE) && invocation.getArgument(1).equals(ENDING_DATE)) {
				ArrayList<LibraryOpeningHours> openingHours = new ArrayList<LibraryOpeningHours>();
				openingHours.add(new LibraryOpeningHours(OPENING_DATE, START_TIME, END_TIME));
				openingHours.add(new LibraryOpeningHours(ENDING_DATE, START_TIME, END_TIME));
				return openingHours;
			} else {
				return new ArrayList<LibraryOpeningHours>();
			}
		});
		lenient().when(holidayRepository.findHolidayByStartDateLessThanEqualAndEndDateGreaterThanEqual(any(Date.class), any(Date.class)))
		.thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(HOLIDAY_DATE) && invocation.getArgument(1).equals(HOLIDAY_DATE)) {
				ArrayList<Holiday> openingHours = new ArrayList<Holiday>();
				openingHours.add(new Holiday("Easter", HOLIDAY_DATE, HOLIDAY_DATE));
				return openingHours;
			} else {
				return new ArrayList<Holiday>();
			}
		});
	}
	
	@Test
	public void testGetLibraryOpeningHoursExisting() {
		ArrayList<LibraryOpeningHours> openingHoursOfDate = null;
		
		try {
			openingHoursOfDate = service.getLibraryOpeningHours(L_OPENING_DATE);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		assertNotNull(openingHoursOfDate);
		assertEquals(1, openingHoursOfDate.size());
		assertEquals(OPENING_DATE, openingHoursOfDate.get(0).getDate());
	}
	
	@Test
	public void testGetLibraryOpeningHoursNonExisting() {
		ArrayList<LibraryOpeningHours> openingHoursOfDate = null;
		
		try {
			openingHoursOfDate = service.getLibraryOpeningHours(L_INVALID_DATE);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		assertNotNull(openingHoursOfDate);
		assertEquals(0, openingHoursOfDate.size());
	}
	
	@Test
	public void testGetLibraryOpeningHoursEmptyDate() { 
		ArrayList<LibraryOpeningHours> openingHoursOfDate = null;
		String error = null;
		
		try {
			openingHoursOfDate = service.getLibraryOpeningHours(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(openingHoursOfDate);
		assertNotNull(error);
		assertTrue(error.contains("A date is required."));
	}
	
	@Test
	public void testGetLibraryOpeningHoursByRangeExisting() {
		ArrayList<LibraryOpeningHours> openingHoursOfDate = null;
		
		try {
			openingHoursOfDate = service.getLibraryOpeningHours(L_OPENING_DATE, L_ENDING_DATE);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		assertNotNull(openingHoursOfDate);
		assertEquals(2, openingHoursOfDate.size());
		assertEquals(OPENING_DATE, openingHoursOfDate.get(0).getDate());
		assertEquals(ENDING_DATE, openingHoursOfDate.get(1).getDate());
	}
	
	@Test
	public void testGetLibraryOpeningHoursByRangeNonExisting() {
		ArrayList<LibraryOpeningHours> openingHoursOfDate = null;
		
		try {
			openingHoursOfDate = service.getLibraryOpeningHours(L_ENDING_DATE, L_INVALID_DATE);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		assertNotNull(openingHoursOfDate);
		assertEquals(0, openingHoursOfDate.size());
	}
	
	@Test
	public void testGetLibraryOpeningHoursByRangeEmptyDate() { 
		ArrayList<LibraryOpeningHours> openingHoursOfDate = null;
		String error = null;
		
		try {
			openingHoursOfDate = service.getLibraryOpeningHours(null, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(openingHoursOfDate);
		assertNotNull(error);
		assertTrue(error.contains("Two date parameters are required."));
	}
	
	@Test
	public void testGetLibraryOpeningHoursByRangeInvertedDates() { 
		ArrayList<LibraryOpeningHours> openingHoursOfDate = null;
		String error = null;
		
		try {
			openingHoursOfDate = service.getLibraryOpeningHours(L_ENDING_DATE, L_OPENING_DATE);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		System.out.println(L_OPENING_DATE);
		System.out.println(L_ENDING_DATE);
		assertNull(openingHoursOfDate);
		assertNotNull(error);
		assertTrue(error.contains("The start date can't be after the end date."));
	}
	
	@Test
	public void testCreateLibraryOpeningHours() { 
		LibraryOpeningHours openingHoursOfDate = null;
		
		try {
			openingHoursOfDate = service.createLibraryOpeningHours(L_ENDING_DATE, L_START_TIME, L_END_TIME);
		} catch (IllegalArgumentException e) {
			fail();
		}

		verify(libraryOpeningHoursRepository, times(1)).save(any(LibraryOpeningHours.class));
		assertNotNull(openingHoursOfDate);
		assertEquals(ENDING_DATE, openingHoursOfDate.getDate());
		assertEquals(START_TIME, openingHoursOfDate.getStartTime());
		assertEquals(END_TIME, openingHoursOfDate.getEndTime());
	}
	
	@Test
	public void testCreateLibraryOpeningHoursEmptyDate() { 
		LibraryOpeningHours openingHoursOfDate = null;
		String error = null;
		
		try {
			openingHoursOfDate = service.createLibraryOpeningHours(null, L_START_TIME, L_END_TIME);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		verify(libraryOpeningHoursRepository, never()).save(any(LibraryOpeningHours.class));
		assertNull(openingHoursOfDate);
		assertNotNull(error);
		assertTrue(error.contains("Date can't be empty."));
	}
	
	@Test
	public void testCreateLibraryOpeningHoursEmptyStartTime() { 
		LibraryOpeningHours openingHoursOfDate = null;
		String error = null;
		
		try {
			openingHoursOfDate = service.createLibraryOpeningHours(L_ENDING_DATE, null, L_END_TIME);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		verify(libraryOpeningHoursRepository, never()).save(any(LibraryOpeningHours.class));
		assertNull(openingHoursOfDate);
		assertNotNull(error);
		assertTrue(error.contains("Start Time can't be empty."));
	}
	
	@Test
	public void testCreateLibraryOpeningHoursEmptyEndTime() { 
		LibraryOpeningHours openingHoursOfDate = null;
		String error = null;
		
		try {
			openingHoursOfDate = service.createLibraryOpeningHours(L_ENDING_DATE, L_START_TIME, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		verify(libraryOpeningHoursRepository, never()).save(any(LibraryOpeningHours.class));
		assertNull(openingHoursOfDate);
		assertNotNull(error);
		assertTrue(error.contains("End Time can't be empty."));
	}
	
	@Test
	public void testCreateLibraryOpeningHoursEmptyInvertedTimes() { 
		LibraryOpeningHours openingHoursOfDate = null;
		String error = null;
		
		try {
			openingHoursOfDate = service.createLibraryOpeningHours(L_ENDING_DATE, L_END_TIME, L_START_TIME);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		verify(libraryOpeningHoursRepository, never()).save(any(LibraryOpeningHours.class));
		assertNull(openingHoursOfDate);
		assertNotNull(error);
		assertTrue(error.contains("Start Time must be before End Time."));
	}
	
	@Test
	public void testCreateLibraryOpeningHoursOverlapHoliday() { 
		LibraryOpeningHours openingHoursOfDate = null;
		String error = null;
		
		try {
			openingHoursOfDate = service.createLibraryOpeningHours(L_OPENING_DATE, L_START_TIME, L_END_TIME);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		verify(libraryOpeningHoursRepository, never()).save(any(LibraryOpeningHours.class));
		assertNull(openingHoursOfDate);
		assertNotNull(error);
		assertTrue(error.contains("Date coincides with a federal holiday."));
	}
	
	@Test
	public void testCreateLibraryOpeningHoursOverlapExisting() { 
		LibraryOpeningHours openingHoursOfDate = null;
		String error = null;
		
		try {
			openingHoursOfDate = service.createLibraryOpeningHours(L_OPENING_DATE, L_START_TIME, L_END_TIME);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		verify(libraryOpeningHoursRepository, never()).save(any(LibraryOpeningHours.class));
		assertNull(openingHoursOfDate);
		assertNotNull(error);
		assertTrue(error.contains("Opening hours have already been set for this date and time."));
	}
	
	@Test
	public void testDeleteLibraryOpeningHoursExisting() {
		try {
			service.deleteLibraryOpeningHours(VALID_ID);
		} catch(Exception e) {
			fail();
		}

		verify(libraryOpeningHoursRepository, times(1)).deleteById(VALID_ID);
	}
}
