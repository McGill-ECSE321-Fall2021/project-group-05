package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Date;

@Entity
public class Loan {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Date returnDate;
	private int numberOfRenewals;

	@OneToOne(optional = false, orphanRemoval = true)
	@JoinColumn(name = "library_item_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private ReservableItem item;
	@ManyToOne(optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Member member;

	// Constructors
	protected Loan() {
	}

	public Loan(Date returnDate, ReservableItem item, Member member) {
		this.returnDate = returnDate;
		this.numberOfRenewals = 0;

		if (item == null)
			throw new IllegalArgumentException("A reservable item is required for every loan.");
		this.item = item;
		this.item.setLoan(this);

		if (member == null)
			throw new IllegalArgumentException("A member is required for every loan");
		this.member = member;
	}

	/**
	 * Returns the unique integer ID for this loan.
	 * @return the unique ID of the loan
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * Sets the maximum return date for this loan.
	 * @param date the new maximum return date
	 */
	public void setReturnDate(Date date) {
		this.returnDate = date;
	}

	/**
	 * Returns the current maximum return date for this loan.
	 * @return the maximum return date for this loan
	 */
	public Date getReturnDate() {
		return this.returnDate;
	}

	/**
	 * Sets the maximum number of times this loan can still be renewed.
	 * @param numberOfRenewals the new maximum number of times this loan can be renewed
	 */
	public void setNumberOfRenewals (int numberOfRenewals) {
		this.numberOfRenewals = numberOfRenewals;
	}

	/**
	 * Returns the remaining number of times this loan can be renewed.
	 * @return the remaining number of times this loan can be renewed
	 */
	public int getNumberOfRenewals () {
		return this.numberOfRenewals;
	}

	/**
	 * Returns the reservable library item which has been loaned.
	 * @return the reservable library item associated with this Loan instance
	 */
	public ReservableItem getReservableItem () {
		return this.item;
	}

	/**
	 * Sets the reservable library item associated with this loan.
	 * @param item the new reservable library item associated with this loan
	 */
	public void setReservableItem (ReservableItem item) {
		this.item = item;
	}

	public Member getMember() {
		return this.member;
	}

	public void setMember(Member newMember) {
		if (newMember == null)
			throw new IllegalArgumentException("New member cannot be null.");
		if (newMember.equals(this.member))
			return;

		this.member = newMember;
	}
}
