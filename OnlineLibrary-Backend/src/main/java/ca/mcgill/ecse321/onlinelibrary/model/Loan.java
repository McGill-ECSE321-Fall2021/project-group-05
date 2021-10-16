package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Loan {

	// Attributes
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	// Associations
	@ManyToOne
	private User user;

	// Constructor
	public Loan(User user) {
		boolean didAddUser = setUser(user);
		if (!didAddUser) {
			throw new RuntimeException(
					"Unable to create loan due to user. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
	}

	// Interface
	public void setId(Integer newId) {
		this.id = newId;
	}

	public Integer getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public boolean setUser(User aUser) {
		boolean wasSet = false;
		// Must provide user to loan
		if (aUser == null) {
			return wasSet;
		}
		// TODO Add back check for too many loans?

		User existingUser = user;
		user = aUser;
		if (existingUser != null && !existingUser.equals(aUser)) {
			boolean didRemove = existingUser.removeLoan(this);
			if (!didRemove) {
				user = existingUser;
				return wasSet;
			}
		}
		user.addLoan(this);
		wasSet = true;
		return wasSet;
	}

	public void delete() {
		User placeholderUser = user;
		this.user = null;
		if (placeholderUser != null) {
			placeholderUser.removeLoan(this);
		}
	}

	@Override
	public String toString() {
		return super.toString() + "[" + "id" + ":" + getId() + "]"
				+ System.getProperties().getProperty("line.separator") + "  "
				+ "user = "
				+ (getUser() != null
				? Integer
						.toHexString(System.identityHashCode(getUser()))
						: "null");
	}
}
