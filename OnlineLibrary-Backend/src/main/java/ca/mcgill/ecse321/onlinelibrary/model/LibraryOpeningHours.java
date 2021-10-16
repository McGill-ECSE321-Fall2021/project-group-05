package ca.mcgill.ecse321.onlinelibrary.model;


import java.sql.Date;
import java.sql.Time;

import javax.persistence.*;

@Entity
public class LibraryOpeningHours {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	private Date date;
	private Time startTime;
	private Time endTime;
	
	// Getters
    public Integer getId() {
        return this.id;
    }
	public Date getDate() {
        return this.date;
    }
	public Time getStartTime() {
        return this.startTime;
    }
	public Time getEndTime() {
        return this.endTime;
    }
	
	// Setters
	public boolean setDate(Date date) {
		boolean wasSet = false;
        	this.date = date;
	    	wasSet = true;
	    	return wasSet;	
    	}
	public boolean setStartTime(Time startTime) {
		boolean wasSet = false;
		this.startTime = startTime;
	    	wasSet = true;
	    	return wasSet;	
	}
	public boolean setEndTime(Time endTime) {
		boolean wasSet = false;
		this.endTime = endTime;
	    	wasSet = true;
	    	return wasSet;	
	}
}
