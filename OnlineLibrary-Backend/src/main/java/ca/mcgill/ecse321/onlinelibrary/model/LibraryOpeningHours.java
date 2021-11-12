package ca.mcgill.ecse321.onlinelibrary.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Time;

@Entity
public class LibraryOpeningHours {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	private Date date;
	private Time startTime;
	private Time endTime;
	
	// Constructors
	public LibraryOpeningHours() {}
	
	public LibraryOpeningHours(Date date, Time startTime, Time endTime) {
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
}
