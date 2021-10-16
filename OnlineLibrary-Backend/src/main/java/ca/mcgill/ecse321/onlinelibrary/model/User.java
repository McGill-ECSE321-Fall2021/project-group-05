package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	@OneToOne(optional = false, targetEntity = OnlineAccount.class)
	private OnlineAccount onlineAccount;

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
}
