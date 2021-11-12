package ca.mcgill.ecse321.onlinelibrary.service;

import ca.mcgill.ecse321.onlinelibrary.dao.HolidayRepository;
import ca.mcgill.ecse321.onlinelibrary.model.Holiday;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestHolidayService {
	@Mock
	private HolidayRepository holidayRepository;
	
	@InjectMocks
	private HolidayService service;

	private static final LocalDate L_START_DATE = LocalDate.of(2020, Month.JANUARY, 01);
	private static final LocalDate L_ALTERNATIVE_START_DATE = LocalDate.of(2020, Month.JANUARY, 02);
	private static final LocalDate L_END_DATE = LocalDate.of(2020, Month.JANUARY, 31);
	private static final LocalDate L_INVALID_DATE = LocalDate.of(2020, Month.MAY, 28);
	private static final Date START_DATE = Date.valueOf(L_START_DATE);
	private static final Date ALTERNATIVE_START_DATE = Date.valueOf(L_ALTERNATIVE_START_DATE);
	private static final Date END_DATE = Date.valueOf(L_END_DATE);
	private static final String EXISTING_NAME = "Easter";
	private static final String NEW_NAME = "Christmas";
	private static final int VALID_ID = 1;
	
	@BeforeEach
	public void setMockOuput() {
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(holidayRepository.save(any(Holiday.class))).then(returnParameterAsAnswer);
		lenient().when(holidayRepository.findHolidayByStartDateOrEndDate(any(Date.class), any(Date.class)))
		.thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(START_DATE) && invocation.getArgument(1).equals(START_DATE)) {
				ArrayList<Holiday> holidays = new ArrayList<Holiday>();
				holidays.add(new Holiday(EXISTING_NAME, START_DATE, END_DATE));
				return holidays;
			} else {
				return new ArrayList<Holiday>();
			}
		});
		lenient().when(holidayRepository.findHolidayByStartDateBetweenOrEndDateBetween(any(Date.class), any(Date.class), any(Date.class), any(Date.class)))
		.thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(START_DATE) && invocation.getArgument(1).equals(END_DATE)
					&& invocation.getArgument(2).equals(START_DATE) && invocation.getArgument(3).equals(END_DATE)) {
				ArrayList<Holiday> holidays = new ArrayList<Holiday>();
				holidays.add(new Holiday(EXISTING_NAME, START_DATE, END_DATE));
				return holidays;
			} else {
				return new ArrayList<Holiday>();
			}
		});
		lenient().when(holidayRepository.findHolidayByStartDateAndEndDate(any(Date.class), any(Date.class)))
		.thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(START_DATE) && invocation.getArgument(1).equals(END_DATE)) {
				ArrayList<Holiday> holidays = new ArrayList<Holiday>();
				holidays.add(new Holiday(EXISTING_NAME, START_DATE, END_DATE));
				return holidays;
			} else {
				return new ArrayList<Holiday>();
			}
		});
	}
	
	
	@Test
	public void testGetHolidayExisting() {
		ArrayList<Holiday> holidays = null;

		try {
			holidays = service.getHolidays(L_START_DATE);
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		}

		assertNotNull(holidays);
		assertEquals(1, holidays.size());
		assertEquals(START_DATE, holidays.get(0).getStartDate());
	}	
	
	@Test
	public void testGetHolidayNonExisting() {
		ArrayList<Holiday> holidays = null;

		try {
			holidays = service.getHolidays(L_INVALID_DATE);
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		}

		assertNotNull(holidays);
		assertEquals(0, holidays.size());
	}
	
	@Test
	public void testGetHolidayEmptyDate() {
		ArrayList<Holiday> holidays = null;
		String error = null;
		
		try {
			holidays = service.getHolidays(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(holidays);
		assertNotNull(error);
		assertTrue(error.contains("A date parameters is required."));
	}
	
	@Test
	public void testGetHolidayByRangeExisting() {
		ArrayList<Holiday> holidays = null;

		try {
			holidays = service.getHolidays(L_START_DATE, L_END_DATE);
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		}
		
		assertNotNull(holidays);
		assertEquals(1, holidays.size());
		assertEquals(START_DATE, holidays.get(0).getStartDate());
	}	

	@Test
	public void testGetHolidayByRangeNonExisting() {
		ArrayList<Holiday> holidays = null;

		try {
			holidays = service.getHolidays(L_INVALID_DATE, L_INVALID_DATE);
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		}

		assertNotNull(holidays);
		assertEquals(0, holidays.size());
	}

	@Test
	public void testGetHolidayByRangeEmptyDate() {
		ArrayList<Holiday> holidays = null;
		String error = null;
		
		try {
			holidays = service.getHolidays(null, L_END_DATE);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(holidays);
		assertNotNull(error);
		assertTrue(error.contains("Two date parameters are required."));
	}
	
	@Test
	public void testGetHolidayByRangeInvertedDates() {
		ArrayList<Holiday> holidays = null;
		String error = null;
		
		try {
			holidays = service.getHolidays(L_END_DATE, L_START_DATE);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(holidays);
		assertNotNull(error);
		assertTrue(error.contains("The start date can't be after the end date."));
	}

	@Test
	public void testCreateHoliday() {
		Holiday holiday = null;

		try {
			holiday = service.createHoliday(NEW_NAME, L_ALTERNATIVE_START_DATE, L_END_DATE);
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		}

		verify(holidayRepository, times(1)).save(any(Holiday.class));
		assertNotNull(holiday);
		assertEquals(NEW_NAME, holiday.getName());
		assertEquals(ALTERNATIVE_START_DATE, holiday.getStartDate());
		assertEquals(END_DATE, holiday.getEndDate());
	}
	
	@Test
	public void testCreateHolidayEmptyName() {
		Holiday holiday = null;
		String error = null;

		try {
			holiday = service.createHoliday(null, L_ALTERNATIVE_START_DATE, L_END_DATE);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		verify(holidayRepository, never()).save(any(Holiday.class));
		assertNull(holiday);
		assertNotNull(error);
		assertTrue(error.contains("Name can't be empty."));
	}
	
	@Test
	public void testCreateHolidayEmptyStartDate() {
		Holiday holiday = null;
		String error = null;

		try {
			holiday = service.createHoliday(NEW_NAME, null, L_END_DATE);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		verify(holidayRepository, never()).save(any(Holiday.class));
		assertNull(holiday);
		assertNotNull(error);
		assertTrue(error.contains("Start Date can't be empty."));
	}
	
	@Test
	public void testCreateHolidayEmptyEndDate() {
		Holiday holiday = null;
		String error = null;

		try {
			holiday = service.createHoliday(NEW_NAME, L_ALTERNATIVE_START_DATE, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		verify(holidayRepository, never()).save(any(Holiday.class));
		assertNull(holiday);
		assertNotNull(error);
		assertTrue(error.contains("End Date can't be empty."));
	}
	
	@Test
	public void testCreateHolidayInvertedDates() {
		Holiday holiday = null;
		String error = null;

		try {
			holiday = service.createHoliday(NEW_NAME, L_END_DATE, L_ALTERNATIVE_START_DATE);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		verify(holidayRepository, never()).save(any(Holiday.class));
		assertNull(holiday);
		assertNotNull(error);
		assertTrue(error.contains("Start Date can't be after End Date."));
	}
	
	@Test
	public void testCreateHolidayDuplicate() {
		Holiday holiday = null;
		String error = null;

		try {
			holiday = service.createHoliday(EXISTING_NAME, L_START_DATE, L_END_DATE);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		verify(holidayRepository, never()).save(any(Holiday.class));
		assertNull(holiday);
		assertNotNull(error);
		assertTrue(error.contains("An identical holiday already exists."));
	}
	
	@Test
	public void testDeleteHoliday() {
		try {
			service.deleteHoliday(VALID_ID);
		} catch(Exception e) {
			fail(e.getMessage());
		}
		
		verify(holidayRepository, times(1)).deleteById(VALID_ID);
	}
}
