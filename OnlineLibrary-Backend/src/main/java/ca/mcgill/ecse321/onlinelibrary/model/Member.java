package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
		GREEN, YELLOW, RED, BLACKLISTED, INACTIVE
	}

	// Account fees (e.g. registration fee, late fees) in cents
	private int totalFee;

	// Associations
	@OneToOne(optional = true, cascade = {CascadeType.PERSIST})
	private OnlineAccount onlineAccount;
	@OneToMany(cascade = {CascadeType.PERSIST})
	private List<Loan> loans;

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
	}

	// Interface
	public Integer getId() {
		return this.id;
	}

	/**
	 * Strictly for testing members with no online account. DO NOT USE EXCEPT FOR TESTING.
	 */
	public void setId(Integer id){
		this.id  = id;
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
	 */
	public void activate() {
		this.status = switch (this.status) {
			case INACTIVE -> MemberStatus.GREEN;
			default -> throw new IllegalStateException("Cannot activate an account that is already active.");
		};

	}

	public void applyStatusPenalty() {
		this.status = switch (this.status) {
			case GREEN -> MemberStatus.YELLOW;
			case YELLOW -> MemberStatus.RED;
			case RED, BLACKLISTED -> MemberStatus.BLACKLISTED;
			case INACTIVE -> throw new IllegalStateException("Cannot apply a status penalty to an inactive member account.");
		};
	}

	public void removeStatusPenalty() {
		this.status = switch (this.status) {
			case BLACKLISTED -> MemberStatus.RED;
			case RED -> MemberStatus.YELLOW;
			case YELLOW, GREEN -> MemberStatus.GREEN;
			case INACTIVE -> throw new IllegalStateException("Cannot remove a status penalty from an inactive member account.");
		};
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
}
