package ca.mcgill.ecse321.onlinelibrary.model;

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
public class User {

	// Attributes
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String address;
	private String fullName;

	// Associations
	@OneToOne(optional = true, cascade = CascadeType.ALL)
	private OnlineAccount onlineAccount;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Loan> loans;

	// Constructors
	protected User() {
	}

	public User(String address, String fullName) {
		this.address = address;
		this.fullName = fullName;
	}

	// Interface
	public void setId(Integer newId) {
		this.id = newId;
	}

	public void setAddress(String newAddress) {
		this.address = newAddress;
	}

	public void setFullName(String newFullName) {
		this.fullName = newFullName;
	}

	public Integer getId() {
		return id;
	}

	public String getAddress() {
		return address;
	}

	public String getFullName() {
		return fullName;
	}

	public OnlineAccount getOnlineAccount() {
		return onlineAccount;
	}

	public List<Loan> getLoans() {
		List<Loan> newLoans = Collections.unmodifiableList(loans);
		return newLoans;
	}

	public boolean setOnlineAccount(OnlineAccount aNewOnlineAccount) {
		boolean wasSet = false;
		if (onlineAccount != null && !onlineAccount.equals(aNewOnlineAccount)
				&& equals(onlineAccount.getAccountOwner())) {
			// Unable to setOnlineAccount, as existing onlineAccount would
			// become an orphan
			return wasSet;
		}

		onlineAccount = aNewOnlineAccount;
		User anOldAccountOwner = aNewOnlineAccount != null ? aNewOnlineAccount.getAccountOwner() : null;

		if (!this.equals(anOldAccountOwner)) {
			if (anOldAccountOwner != null) {
				anOldAccountOwner.onlineAccount = null;
			}
			if (onlineAccount != null) {
				onlineAccount.setAccountOwner(this);
			}
		}
		wasSet = true;
		return wasSet;
	}

	public boolean addLoan(Loan aLoan) {
		boolean wasAdded = false;
		if (loans.contains(aLoan)) {
			return false;
		}
		// TODO Add back check for too many loans?

		User existingUser = aLoan.getUser();
		boolean isNewUser = existingUser != null && !this.equals(existingUser);
		if (isNewUser) {
			aLoan.setUser(this);
		} else {
			loans.add(aLoan);
		}
		wasAdded = true;
		return wasAdded;
	}

	public boolean removeLoan(Loan aLoan) {
		boolean wasRemoved = false;
		// Unable to remove aLoan, as it must always have a user
		if (!this.equals(aLoan.getUser())) {
			loans.remove(aLoan);
			wasRemoved = true;
		}
		return wasRemoved;
	}

	@Override
	public String toString() {
		return super.toString() + "[" + "id" + ":" + getId() + "," + "address" + ":" + getAddress() + "," + "fullName"
				+ ":" + getFullName() + "]" + System.getProperties().getProperty("line.separator") + "  "
				+ "onlineAccount = "
				+ (getOnlineAccount() != null
				? Integer.toHexString(System.identityHashCode(getOnlineAccount()))
						: "null");
	}
}
