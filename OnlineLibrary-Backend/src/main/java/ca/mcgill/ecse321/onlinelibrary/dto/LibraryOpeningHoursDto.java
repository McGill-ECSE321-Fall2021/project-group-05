package ca.mcgill.ecse321.onlinelibrary.dto;


import ca.mcgill.ecse321.onlinelibrary.model.LibraryOpeningHours;

import java.sql.Date;
import java.sql.Time;

public class LibraryOpeningHoursDto {
    private Integer id;
	private Date date;
	private Time startTime;
	private Time endTime;
	
	// Constructors
	public LibraryOpeningHoursDto() {}
	
	public LibraryOpeningHoursDto(Integer id, Date date, Time startTime, Time endTime) {
		this.id = id;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	// Getters
	/**
	 * Returns the unique ID of the LibraryOpeningHours
	 * @return the id of the LibraryOpeningHours
	 */
    public Integer getId() {
        return this.id;
    }
	/**
	 * Returns date of the LibraryOpeningHours
	 * @return the date of the LibraryOpeningHours
	 */
	public Date getDate() {
        return this.date;
    }
	/**
	 * Returns start time of the LibraryOpeningHours
	 * @return the start time of the LibraryOpeningHours
	 */
	public Time getStartTime() {
        return this.startTime;
    }
	/**
	 * Returns start time of the LibraryOpeningHours
	 * @return the start time of the LibraryOpeningHours
	 */
	public Time getEndTime() {
        return this.endTime;
    }
	
	// Setters
	/**
    * Sets the date of the LibraryOpeningHours
    * @param the date of the LibraryOpeningHours
    */
	public void setDate(Date date) {
    	this.date = date;
	}
	/**
    * Sets the start time of the LibraryOpeningHours
    * @param the start time of the LibraryOpeningHours
    */
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	/**
    * Sets the end time of the LibraryOpeningHours
    * @param the end time of the LibraryOpeningHours
    */
	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}
	
	public static LibraryOpeningHoursDto fromLibraryOpeningHours(LibraryOpeningHours libraryOpeningHours) {
		if (libraryOpeningHours == null) {
			throw new IllegalArgumentException("There is no such library opening hours.");
		}
		return new LibraryOpeningHoursDto(libraryOpeningHours.getId(), libraryOpeningHours.getDate(), 
				libraryOpeningHours.getStartTime(), libraryOpeningHours.getEndTime());
	}
}
