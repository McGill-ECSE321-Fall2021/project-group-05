package ca.mcgill.ecse321.onlinelibrary.model;
//
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import java.sql.Date;
import java.sql.Time;

//@Entity
public class Loan {
//	private int id;
//	private Date returnDate;
//	private int numberOfRenewals;
//	
//	private ReservableItem item;
//	//private User user;
//	
//	//Need to confirm what we do with constructors
//	public Loan (int value) {
//		this.id = value;
//	}
//	//If we require an id in the constructor, we probably don't want a method to setId
//	public void setId (int value) {
//		this.id=value;
//	}
//	
//	@Id
//	public int getId() {
//		return this.id;
//	}
//	
//	public void setReturnDate(Date aDate) {
//		this.returnDate = aDate;
//	}
//	
//	public Date getReturnDate() {
//		return this.returnDate;
//	}
//	
//	public void setNumberOfRenewals (int numberOfRenewals) {
//		this.numberOfRenewals = numberOfRenewals;
//	}
//	
//	public int getNumberOfRenewals () {
//		return this.numberOfRenewals;
//	}
//	
//	//@OneToOne(cascade = { CascadeType.ALL })
//	public ReservableItem getReservableItem () {
//		return this.item;
//	}
//	
//	public void setReservableItem (ReservableItem item) {
//		this.item = item;
//	}
	
}
