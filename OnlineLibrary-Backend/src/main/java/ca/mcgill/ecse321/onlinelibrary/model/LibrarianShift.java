package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Date;
import java.sql.Time;

@Entity
public class LibrarianShift {

	// Attributes
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Date date;
	private Time startTime;
	private Time endTime;

	// Association
	@ManyToOne(optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Librarian librarian;

	// Constructors
	protected LibrarianShift() {
	}

	public LibrarianShift(Date date, Time startTime, Time endTime, Librarian librarian) {
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;

		if (librarian == null)
			throw new IllegalArgumentException("A Librarian is required for every LibrarianShift.");
		this.librarian = librarian;
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

	public Librarian getLibrarian() {
		return this.librarian;
	}

	public void setLibrarian(Librarian newLibrarian) {
		if (newLibrarian == null)
			throw new IllegalArgumentException("A Librarian is required for every LibrarianShift.");
		this.librarian = newLibrarian;
	}
}
