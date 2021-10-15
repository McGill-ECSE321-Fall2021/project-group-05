package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
public abstract class ReservableItem extends LibraryItem{
	
	public enum ItemStatus {Available, CheckedOut, Reserved}
	
	private ItemStatus status;
	
	
	public void setStatus (ItemStatus status) {
		this.status = status;
	}
	
	public ItemStatus getStatus() {
		return this.status;
	}
	
	

}
