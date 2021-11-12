package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

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
    
    @OneToOne(optional = true, cascade = {CascadeType.ALL})
	@JoinColumn(name = "loan_id")
    private Loan loan;

    /**
     * Get the current loan associated with this item if there is one.
     * @return the current loan associated with this item
     */
    public Loan getLoan() {
    	return this.loan;
    }

    /**
     * Associate a new loan with the item.
     * @param loan the new loan too associate with the reservable library item
     */
    public void setLoan(Loan loan) {
    	this.loan = loan;
    }

    public enum ItemStatus {Available, CheckedOut, Reserved}

}
