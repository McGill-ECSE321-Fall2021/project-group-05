package ca.mcgill.ecse321.onlinelibrary.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
		GREEN, YELLOW, RED, BLACKLISTED
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
		this.status = MemberStatus.GREEN;
		this.totalFee = registrationFee;
    
		this.loans = new ArrayList<Loan>();
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

	public void applyStatusPenalty() {
		switch (this.status) {
			case GREEN :
				this.status = MemberStatus.YELLOW;
				break;
			case YELLOW :
				this.status = MemberStatus.RED;
				break;
			default :
				this.status = MemberStatus.BLACKLISTED;
		}
	}

	public void removeStatusPenalty() {
		switch (this.status) {
			case BLACKLISTED :
				this.status = MemberStatus.RED;
				break;
			case RED :
				this.status = MemberStatus.YELLOW;
				break;
			default :
				this.status = MemberStatus.GREEN;
				break;
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

}