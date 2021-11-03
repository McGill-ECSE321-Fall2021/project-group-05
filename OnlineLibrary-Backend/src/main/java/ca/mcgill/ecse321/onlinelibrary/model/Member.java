package ca.mcgill.ecse321.onlinelibrary.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Member {

	// Attributes
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String address;
	private String fullName;
	private MemberStatus status;
	public enum MemberStatus {
		INACTIVE, GREEN, YELLOW, RED, BLACKLISTED
	}
	// Account fees (e.g. registration fee, late fees) in cents
	private int totalFee;

	// Associations
	@OneToOne(optional = true, cascade = {CascadeType.PERSIST})
	private OnlineAccount onlineAccount;
	@OneToMany(cascade = {CascadeType.PERSIST})
	private List<Loan> loans;
	@ManyToMany
	private List<ReservableItemInfo> reservedItems;

	// Constructors
	protected Member() {
	}

	public Member(String address, String fullName) {
		this(address, fullName, 0);
	}

	public Member(String address, String fullName, int registrationFee) {
		this.address = address;
		this.fullName = fullName;
		this.status = MemberStatus.INACTIVE;
		this.totalFee = registrationFee;

		this.loans = new ArrayList<Loan>();
		this.reservedItems = new ArrayList<ReservableItemInfo>();
	}

	// Interface
	public Integer getId() {
		return this.id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String newAddress) {
		this.address = newAddress;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String newFullName) {
		this.fullName = newFullName;
	}

	public MemberStatus getStatus() {
		return this.status;
	}

	/**
	 * If the account is inactive, sets the status to "Green." If the account is
	 * not currently inactive, this method has no effect.
	 *
	 * @return true if the activation was successful and false otherwise
	 */
	public boolean activate() {
		if (this.status == MemberStatus.INACTIVE) {
			this.status = MemberStatus.GREEN;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Applies a penalty to this account. "Green" goes to "Yellow," "Yellow"
	 * goes to "Red," and "Red" goes to "Blacklisted." In any other case, this
	 * method has no effect.
	 *
	 * @return true if the penalty was applied successfully and false otherwise
	 */
	public boolean applyStatusPenalty() {
		switch (this.status) {
			case GREEN :
				this.status = MemberStatus.YELLOW;
				return true;
			case YELLOW :
				this.status = MemberStatus.RED;
				return true;
			case RED :
				this.status = MemberStatus.BLACKLISTED;
				return true;
			default :
				return false;
		}
	}

	/**
	 * Removes a penalty point from this account. "Blacklisted" goes to "Red,"
	 * "Red" goes to "Yellow," and "Yellow" goes to "Green." In any other case,
	 * the method has no effect.
	 *
	 * @return true if the penalty was successfully removed and false otherwise
	 */
	public boolean removeStatusPenalty() {
		switch (this.status) {
			case INACTIVE :
				return false;
			case BLACKLISTED :
				this.status = MemberStatus.RED;
				return true;
			case RED :
				this.status = MemberStatus.YELLOW;
				return true;
			default :
				this.status = MemberStatus.GREEN;
				return true;
		}
	}

	public int getTotalFee() {
		return this.totalFee;
	}

	public void setTotalFee(int newTotalFee) {
		this.totalFee = newTotalFee;
	}

	public OnlineAccount getOnlineAccount() {
		return this.onlineAccount;
	}

	public void setOnlineAccount(OnlineAccount newOnlineAccount) {
		this.onlineAccount = newOnlineAccount;
	}

	public List<Loan> getLoans() {
		return Collections.unmodifiableList(this.loans);
	}

	public void addLoan(Loan newLoan) {
		this.loans.add(newLoan);
	}

	public void removeLoan(Loan loanToRemove) {
		this.loans.remove(loanToRemove);
	}

	public List<ReservableItemInfo> getReservedItems(){
		return this.reservedItems;
	}

	public void addReservation(ReservableItemInfo reservableItemInfo){
		if(this.reservedItems == null)
			this.reservedItems = new ArrayList<ReservableItemInfo>();
		if(reservedItems.contains(reservableItemInfo))
			return;
		this.reservedItems.add(reservableItemInfo);

		reservableItemInfo.addMember(this);
	}
}