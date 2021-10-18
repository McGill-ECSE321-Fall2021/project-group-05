package ca.mcgill.ecse321.onlinelibrary.model;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.*;

@Entity
public class Holiday {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	private Date startDate;
	private Date endDate;
	
	// Constructors
	public Holiday() {}
	
	public Holiday(Date startDate, Date endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}


	// Getters
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
}
