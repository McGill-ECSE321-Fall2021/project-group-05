package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class LibraryItem {
	
	private int id;
	@Id
	public int getId() {
		return this.id;
	}
	
	public void setId(int value) {
		this.id = value;
	}
	
	
}
