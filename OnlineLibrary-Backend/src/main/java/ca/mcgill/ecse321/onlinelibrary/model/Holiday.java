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
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
