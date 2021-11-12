package ca.mcgill.ecse321.onlinelibrary.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class OnlineAccount {

	// Attributes
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String passwordHash;
	@Column(unique = true)
	private String username;
	private String emailAddress;

	// Associations
	@OneToOne(optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
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
