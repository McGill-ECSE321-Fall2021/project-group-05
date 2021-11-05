package ca.mcgill.ecse321.onlinelibrary.controller;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.onlinelibrary.dto.HolidayDto;
import ca.mcgill.ecse321.onlinelibrary.model.Holiday;
import ca.mcgill.ecse321.onlinelibrary.service.HolidayService;

@CrossOrigin(origins = "*")
@RestController
public class HolidayController {
	@Autowired
	private HolidayService service;
	
	@GetMapping(value = { "/holiday/{date}", "/holiday/{date}/" })
	public List<HolidayDto> getHolidays(@PathVariable("date") Date date) throws IllegalArgumentException {
		return service.getHolidays(date).stream()
				.map(holiday -> HolidayDto.fromHoliday(holiday)).collect(Collectors.toList());
	}
	
	@GetMapping(value = { "/holiday/{startDate}/{endDate}", "/holiday/{startDate}/{endDate}/" })
	public List<HolidayDto> getHolidays(@PathVariable("startDate") Date startDate, @PathVariable("endDate") Date endDate) throws IllegalArgumentException {
		return service.getHolidays(startDate, endDate).stream()
				.map(holiday -> HolidayDto.fromHoliday(holiday)).collect(Collectors.toList());
	}
	
	@PostMapping(value = { "/holiday", "/holiday/"})
	public HolidayDto createHoliday(@RequestParam String name, @RequestParam Date startDate, @RequestParam Date endDate) throws IllegalArgumentException{
		Holiday holiday = service.createHoliday(name, startDate, endDate);
		return HolidayDto.fromHoliday(holiday);
	}
	
	@DeleteMapping(value = { "/holiday/{id}", "/holiday/{id}/"})
	public void deleteHoliday(@PathVariable("id") int id) throws IllegalArgumentException {
		service.deleteHoliday(id);
	}
}
