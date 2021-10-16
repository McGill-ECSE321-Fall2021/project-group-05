package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Librarian {

	// Attributes
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String fullName;
	private String username;
	private String passwordHash;
	private boolean isHead;

	// Constructors
	protected Librarian() {
	}

	public Librarian(String fullName, String username, String passwordHash, boolean isHead) {
		this.fullName = fullName;
		this.username = username;
		this.passwordHash = passwordHash;
		this.isHead = isHead;
	}

	// Interface
	public Integer getId() {
		return this.id;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String newFullName) {
		this.fullName = newFullName;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String newUsername) {
		this.username = newUsername;
	}

	public String getPasswordHash() {
		return this.passwordHash;
	}

	public void setPasswordHash(String newPasswordHash) {
		this.passwordHash = newPasswordHash;
	}

	public boolean isHead() {
		return this.isHead;
	}
}
