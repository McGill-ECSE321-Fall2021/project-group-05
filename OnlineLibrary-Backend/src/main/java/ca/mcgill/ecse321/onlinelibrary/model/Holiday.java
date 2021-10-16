package ca.mcgill.ecse321.onlinelibrary.model;

import java.sql.Date;

import javax.persistence.*;

@Entity
public class Holiday {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	private Date startDate;
	private Date endDate;
	
	// Getters
    public Integer getId() {
        return this.id;
    }
	public Date getStartDate() {
        return this.startDate;
    }
	public Date getEndDate() {
        return this.endDate;
    }
	
	// Setters
	public boolean setStartDate(Date startDate) {
		boolean wasSet = false;
		this.startDate = startDate;
		wasSet = true;
		return wasSet;
	}
	public boolean setEndDate(Date endDate) {
		boolean wasSet = false;
		this.endDate = endDate;
		wasSet = true;
		return wasSet;
	}
}
