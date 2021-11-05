package ca.mcgill.ecse321.onlinelibrary.dto;

import java.sql.Date;

import ca.mcgill.ecse321.onlinelibrary.model.Holiday;

public class HolidayDto {
	private Integer id;
	private String name;
	private Date startDate;
	private Date endDate;
	
	// Constructor
	public HolidayDto(int id, String name, Date startDate, Date endDate) {
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}


	// Getters
	/**
	 * Returns name of the holiday
	 * @return the name of the holiday
	 */
	public String getName() {
        return this.name;
    }
	/**
	 * Returns the unique ID of the holiday
	 * @return the id of the holiday
	 */
    public Integer getId() {
        return this.id;
    }

	/**
	 * Returns start date of the holiday
	 * @return the start date of the holiday
	 */
	public Date getStartDate() {
        return this.startDate;
    }
	/**
	 * Returns end date of the holiday
	 * @return the end date of the holiday
	 */
	public Date getEndDate() {
        return this.endDate;
    }
	
	// Setters
	/**
    * Sets the name of the holiday
    * @param status the name of the holiday
    */
	public void setName(String name) {
		this.name = name;
	}
	/**
    * Sets the start date of the holiday
    * @param status the start date of the holiday
    */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
    * Sets the end date of the holiday
    * @param status the end date of the holiday
    */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * Create a transfer object from holiday
	 * @param holiday to be transfered
	 * @return holiday transfer object
	 * @throws IllegalArgumentException on null holiday
	 */
	public static HolidayDto fromHoliday(Holiday holiday) throws IllegalArgumentException {
		if(holiday == null) {
			throw new IllegalArgumentException("There is no such library opening hours.");
		}
		
		return new HolidayDto(holiday.getId(), holiday.getName(), holiday.getStartDate(), holiday.getEndDate());
	}
}
