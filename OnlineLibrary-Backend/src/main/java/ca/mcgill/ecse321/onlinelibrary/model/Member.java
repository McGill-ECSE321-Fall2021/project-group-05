package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Member {

	// Attributes
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String address;
	private String fullName;

	// Associations
	@OneToOne(optional = true, cascade = {CascadeType.PERSIST})
	private OnlineAccount onlineAccount;
	// Association with Loan is directed (Loan -> Member) for simplicity
	// TODO Add back Loans here

	// Constructors
	protected Member() {
	}

	public Member(String address, String fullName) {
		this.address = address;
		this.fullName = fullName;
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

	public OnlineAccount getOnlineAccount() {
		return this.onlineAccount;
	}

	public void setOnlineAccount(OnlineAccount newOnlineAccount) {
		this.onlineAccount = newOnlineAccount;
	}
}
