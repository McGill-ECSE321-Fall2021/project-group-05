package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class OnlineAccount {

	// Attributes
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String passwordHash;
	private String username;
	private String emailAddress;

	// Associations
	@OneToOne(optional = false)
	private User accountOwner;

	// Constructor
	protected OnlineAccount() {

	}

	public OnlineAccount(String aPasswordHash, String aUsername,
			String aEmailAddress, User aAccountOwner) {
		passwordHash = aPasswordHash;
		username = aUsername;
		emailAddress = aEmailAddress;
		boolean didAddAccountOwner = setAccountOwner(aAccountOwner);
		if (!didAddAccountOwner) {
			throw new RuntimeException(
					"Unable to create onlineAccount due to accountOwner. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
	}

	// Interface
	public void setId(Integer newId) {
		this.id = newId;
	}

	public void setPasswordHash(String newPasswordHash) {
		this.passwordHash = newPasswordHash;
	}

	public void setUsername(String newUsername) {
		this.username = newUsername;
	}

	public void setEmailAddress(String newEmailAddress) {
		this.emailAddress = newEmailAddress;
	}

	public Integer getId() {
		return id;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public String getUsername() {
		return username;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public User getAccountOwner() {
		return accountOwner;
	}

	public boolean setAccountOwner(User aNewAccountOwner) {
		boolean wasSet = false;
		if (aNewAccountOwner == null) {
			// Unable to setAccountOwner to null, as onlineAccount must always
			// be associated to a accountOwner
			return wasSet;
		}

		OnlineAccount existingOnlineAccount = aNewAccountOwner
				.getOnlineAccount();
		if (existingOnlineAccount != null && !equals(existingOnlineAccount)) {
			// Unable to setAccountOwner, the current accountOwner already has a
			// onlineAccount, which would be orphaned if it were re-assigned
			return wasSet;
		}

		User anOldAccountOwner = accountOwner;
		accountOwner = aNewAccountOwner;
		accountOwner.setOnlineAccount(this);

		if (anOldAccountOwner != null) {
			anOldAccountOwner.setOnlineAccount(null);
		}
		wasSet = true;
		return wasSet;
	}

	public void delete() {
		User existingAccountOwner = accountOwner;
		accountOwner = null;
		if (existingAccountOwner != null) {
			existingAccountOwner.setOnlineAccount(null);
		}
	}

	@Override
	public String toString() {
		return super.toString() + "[" + "id" + ":" + getId() + ","
				+ "passwordHash" + ":" + getPasswordHash() + "," + "username"
				+ ":" + getUsername() + "," + "emailAddress" + ":"
				+ getEmailAddress() + "]"
				+ System.getProperties().getProperty("line.separator") + "  "
				+ "accountOwner = "
				+ (getAccountOwner() != null
				? Integer.toHexString(
						System.identityHashCode(getAccountOwner()))
						: "null");
	}
}
