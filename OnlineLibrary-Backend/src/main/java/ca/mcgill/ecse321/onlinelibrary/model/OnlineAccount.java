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
	private Member accountOwner;

	// Constructors
	protected OnlineAccount() {
	}

	public OnlineAccount(String passwordHash, String username, String emailAddress, Member accountOwner) {
		this.passwordHash = passwordHash;
		this.username = username;
		this.emailAddress = emailAddress;

		if (accountOwner == null)
			throw new IllegalArgumentException("An account owner is required for every online account");
		this.accountOwner = accountOwner;
	}

	// Interface
	public Integer getId() {
		return this.id;
	}

	public String getPasswordHash() {
		return this.passwordHash;
	}

	public void setPasswordHash(String newPasswordHash) {
		this.passwordHash = newPasswordHash;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String newUsername) {
		this.username = newUsername;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String newEmailAddress) {
		this.emailAddress = newEmailAddress;
	}

	public Member getAccountOwner() {
		return this.accountOwner;
	}
}
