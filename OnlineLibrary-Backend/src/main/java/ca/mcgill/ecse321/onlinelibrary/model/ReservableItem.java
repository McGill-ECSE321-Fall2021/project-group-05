package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.Entity;

@Entity
public abstract class ReservableItem extends LibraryItem {

	private ItemStatus status;

	/**
	 * Returns the status of this reservable library item copy.
	 *
	 * The status could be any of Available, CheckedOut and Reserved.
	 * @return the current status of this reservable library item
	 */
	public ItemStatus getStatus() {
		return this.status;
	}

	/**
	 * Sets the new status of this reservable library item copy.
	 * @param status the new status of this reservable item
	 */
	public void setStatus(ItemStatus status) {
		this.status = status;
	}

	public enum ItemStatus {Available, CheckedOut, Reserved}
}
