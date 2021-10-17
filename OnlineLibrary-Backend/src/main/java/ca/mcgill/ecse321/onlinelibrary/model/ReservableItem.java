package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public abstract class ReservableItem extends LibraryItem {

    private ItemStatus status;

    public ItemStatus getStatus() {
        return this.status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }
    
    @OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "loan_id")
    private Loan loan;
    public Loan getLoan() {
    	return this.loan;
    }
    
    public void setLoan(Loan loan) {
    	this.loan = loan;
    }

    public enum ItemStatus {Available, CheckedOut, Reserved}

}
