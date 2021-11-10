package ca.mcgill.ecse321.onlinelibrary.service;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.onlinelibrary.dao.HolidayRepository;
import ca.mcgill.ecse321.onlinelibrary.model.Holiday;

@Service
public class HolidayService {
	@Autowired
	HolidayRepository holidayRepository;
	
	
	@Transactional
	public void deleteHoliday(int id) {
		holidayRepository.deleteById(id);
	}
	
	@Transactional
	public ArrayList<Holiday> getHolidays(Date date) {
		if(date == null) 
			throw new IllegalArgumentException("A date parameters is required.");
		
		return holidayRepository.findHolidayByStartDateOrEndDate(date, date);
	}
	
	@Transactional
	public ArrayList<Holiday> getHolidays(Date startDate, Date endDate) {
		if(startDate == null || endDate == null) 
			throw new IllegalArgumentException("Two date parameters are required.");

		if(startDate.compareTo(endDate) == 1) 
			throw new IllegalArgumentException("The start date can't be after the end date.");
		
		return holidayRepository.findHolidayByStartDateBetweenOrEndDateBetween(startDate, endDate, startDate, endDate);
	}

	@Transactional
	public Holiday createHoliday(String name, Date startDate, Date endDate) {
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
		
		if(startDate != null && endDate != null && startDate.compareTo(endDate) == 1) {
			errorMessage.add("Start Date can't be after End Date.");
		}
		
		if(errorMessage.size() == 0) {
			ArrayList<Holiday> holidays = holidayRepository.findHolidayByStartDateAndEndDate(startDate, endDate);

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

		Holiday holiday = new Holiday(name, startDate, endDate);
		holidayRepository.save(holiday);

		return holiday;
	}
	
	
}
