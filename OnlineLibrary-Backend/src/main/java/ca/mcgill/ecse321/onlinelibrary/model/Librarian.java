package ca.mcgill.ecse321.onlinelibrary.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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

	// Associations
	@OneToMany
	private Set<LibrarianShift> shifts;

	// Constructors
	protected Librarian() {
	}

	public Librarian(String fullName, String username, String passwordHash, boolean isHead) {
		this.fullName = fullName;
		this.username = username;
		this.passwordHash = passwordHash;
		this.isHead = isHead;

		this.shifts = new HashSet<LibrarianShift>();
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

	public Set<LibrarianShift> getShifts() {
		return Collections.unmodifiableSet(this.shifts);
	}

	public boolean addShift(LibrarianShift newShift) {
		if (newShift == null || shifts.contains(newShift))
			return false;

		boolean isNewLibrarian = this.equals(newShift.getLibrarian());
		if (isNewLibrarian)
			newShift.setLibrarian(this);
		else
			shifts.add(newShift);

		return true;
	}

	public boolean removeShift(LibrarianShift shiftToRemove) {
		if (this.equals(shiftToRemove.getLibrarian()))
			return false;

		this.shifts.remove(shiftToRemove);
		return true;
	}
}
