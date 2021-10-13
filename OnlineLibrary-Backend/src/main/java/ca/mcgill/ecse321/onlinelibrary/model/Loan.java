package ca.mcgill.ecse321.onlinelibrary.model;
//
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "loan")
public class Loan {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	public void setId (int value) {
		this.id=value;
	}
	
	public int getId() {
		return this.id;
	}
	
	private Date returnDate;
	public void setReturnDate(Date date) {
		this.returnDate = date;
	}
	
	public Date getReturnDate() {
		return this.returnDate;
	}
	
	private int numberOfRenewals;
	
	public void setNumberOfRenewals (int numberOfRenewals) {
	this.numberOfRenewals = numberOfRenewals;
	}

	public int getNumberOfRenewals () {
		return this.numberOfRenewals;
	}
	
	
	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "library_item_id")
	private ReservableItem item;
	public ReservableItem getReservableItem () {
		return this.item;
	}
	
	public void setReservableItem (ReservableItem item) {
		this.item = item;
	}
	
	//private User user;
	


	
}
