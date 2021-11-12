package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Holiday {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	private String name;
	private Date startDate;
	private Date endDate;
	
	// Constructors
	public Holiday() {}
	
	public Holiday(String name, Date startDate, Date endDate) {
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
}
