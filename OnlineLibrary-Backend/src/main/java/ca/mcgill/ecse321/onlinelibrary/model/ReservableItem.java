package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.OneToOne;

public abstract class ReservableItem extends LibraryItem{
	public enum ItemStatus {Available, CheckedOut, Reserved}
	
	private ItemStatus status;
	private Loan loan;
	
	@Autowired
	public final void setStatus (ItemStatus status) {
		this.status = status;
	}
	
	public ItemStatus getStatus() {
		return this.status;
	}
	
	@OneToOne(cascade = { CascadeType.ALL })
	public Loan getLoan(){
		return this.loan;
	}
	
	@Autowired
	public final void setLoan (Loan loan) {
		this.loan=loan;
	}
	
}
