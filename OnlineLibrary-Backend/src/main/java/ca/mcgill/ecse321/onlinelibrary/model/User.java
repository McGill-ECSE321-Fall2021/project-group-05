package ca.mcgill.ecse321.onlinelibrary.model;

import java.util.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

@Entity
public class User {

	// Attributes
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String address;
	private String fullName;

	// Associations
	@OneToOne(optional = true)
	private OnlineAccount onlineAccount;
	@OneToMany
	private List<Loan> loans;

	@Autowired
	Environment env;

	// Constructor
	public User(String address, String fullName) {
		this.address = address;
		this.fullName = fullName;

		this.loans = new ArrayList<Loan>();
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

	public Loan getLoan(int index) {
		Loan aLoan = loans.get(index);
		return aLoan;
	}

	public List<Loan> getLoans() {
		List<Loan> newLoans = Collections.unmodifiableList(loans);
		return newLoans;
	}

	public int numberOfLoans() {
		int number = loans.size();
		return number;
	}

	public boolean hasLoans() {
		boolean has = loans.size() > 0;
		return has;
	}

	public int indexOfLoan(Loan aLoan) {
		int index = loans.indexOf(aLoan);
		return index;
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
		User anOldAccountOwner = aNewOnlineAccount != null
				? aNewOnlineAccount.getAccountOwner()
						: null;

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

	public int maximumNumberOfLoans() {
		int defaultMaxNumLoans = 5;
		String maxNumLoansStr = env.getProperty("library.MAX_NUM_LOANS");

		// Property not found: default to 5
		if (StringUtils.isEmpty(maxNumLoansStr)) {
			return defaultMaxNumLoans;
		}

		// Parse to int and return
		try {
			return Integer.parseInt(maxNumLoansStr);
		} catch (NumberFormatException e) {
			return defaultMaxNumLoans;
		}
	}

	public boolean addLoan(Loan aLoan) {
		boolean wasAdded = false;
		if (loans.contains(aLoan)) {
			return false;
		}
		if (numberOfLoans() >= maximumNumberOfLoans()) {
			return wasAdded;
		}

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
		return super.toString() + "[" + "id" + ":" + getId() + "," + "address"
				+ ":" + getAddress() + "," + "fullName" + ":" + getFullName()
				+ "]" + System.getProperties().getProperty("line.separator")
				+ "  " + "onlineAccount = "
				+ (getOnlineAccount() != null
				? Integer.toHexString(
						System.identityHashCode(getOnlineAccount()))
						: "null");
	}
}
