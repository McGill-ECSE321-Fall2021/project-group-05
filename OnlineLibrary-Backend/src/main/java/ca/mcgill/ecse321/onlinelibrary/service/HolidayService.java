package ca.mcgill.ecse321.onlinelibrary.service;

import ca.mcgill.ecse321.onlinelibrary.dao.HolidayRepository;
import ca.mcgill.ecse321.onlinelibrary.model.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class HolidayService {
	@Autowired
	HolidayRepository holidayRepository;
	
	
	@Transactional
	public void deleteHoliday(int id) {
		holidayRepository.deleteById(id);
	}
	
	@Transactional
	public ArrayList<Holiday> getHolidays(LocalDate date) {
		if(date == null) 
			throw new IllegalArgumentException("A date parameters is required.");
		
		return holidayRepository.findHolidayByStartDateOrEndDate(Date.valueOf(date), Date.valueOf(date));
	}
	
	@Transactional
	public ArrayList<Holiday> getHolidays(LocalDate startDate, LocalDate endDate) {
		if(startDate == null || endDate == null) 
			throw new IllegalArgumentException("Two date parameters are required.");

		if(startDate.compareTo(endDate) > 0) 
			throw new IllegalArgumentException("The start date can't be after the end date.");
		
		return holidayRepository.findHolidayByStartDateBetweenOrEndDateBetween
				(Date.valueOf(startDate), Date.valueOf(endDate), Date.valueOf(startDate), Date.valueOf(endDate));
	}

	@Transactional
	public Holiday createHoliday(String name, LocalDate startDate, LocalDate endDate) {
		ArrayList<String> errorMessage = new ArrayList<String>();

		if (name == null) {
			errorMessage.add("Name can't be empty.");
		}
		
		if (startDate == null) {
			errorMessage.add("Start Date can't be empty.");
		}

		if (endDate == null) {
			errorMessage.add("End Date can't be empty.");
		}
		
		if(startDate != null && endDate != null && startDate.compareTo(endDate) > 0) {
			errorMessage.add("Start Date can't be after End Date.");
		}
		
		if(errorMessage.size() == 0) {
			ArrayList<Holiday> holidays = holidayRepository.findHolidayByStartDateAndEndDate(Date.valueOf(startDate), Date.valueOf(endDate));

			if(holidays != null && holidays.size() != 0) {
				for(Holiday holiday : holidays) {
					if(holiday.getName().equals(name)) {
						errorMessage.add("An identical holiday already exists.");
						break;
					}
				}
			}	
		}

		if (errorMessage.size() > 0) {
			throw new IllegalArgumentException(String.join(" ", errorMessage));
		}

		Holiday holiday = new Holiday(name, Date.valueOf(startDate), Date.valueOf(endDate));
		holidayRepository.save(holiday);

		return holiday;
	}
	
	
}
