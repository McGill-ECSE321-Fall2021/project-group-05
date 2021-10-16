package ca.mcgill.ecse321.onlinelibrary.model;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LibrarianShift {

	// Attributes
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Date date;
	private Time startTime;
	private Time endTime;

	// Constructors
	protected LibrarianShift() {
	}

	public LibrarianShift(Date date, Time startTime, Time endTime) {
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	// Interface
	public Integer getId() {
		return this.id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date newDate) {
		this.date = newDate;
	}

	public Time getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Time newStartTime) {
		this.startTime = newStartTime;
	}

	public Time getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Time newEndTime) {
		this.endTime = newEndTime;
	}
}
