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
	public void setDate(Date date) {
        this.date = date;
    }
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}
}
