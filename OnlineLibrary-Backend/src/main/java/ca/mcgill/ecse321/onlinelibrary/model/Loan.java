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

	@OneToOne(optional = false)
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

		if (item == null)
			throw new IllegalArgumentException("A reservable item is required for every loan.");
		this.item = item;

		if (member == null)
			throw new IllegalArgumentException("A member is required for every loan");
		this.member = member;
	}

	public Integer getId() {
		return this.id;
	}

	public void setReturnDate(Date date) {
		this.returnDate = date;
	}

	public Date getReturnDate() {
		return this.returnDate;
	}

	public void setNumberOfRenewals (int numberOfRenewals) {
		this.numberOfRenewals = numberOfRenewals;
	}

	public int getNumberOfRenewals () {
		return this.numberOfRenewals;
	}

	public ReservableItem getReservableItem () {
		return this.item;
	}

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
