package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "library_item")
public abstract class LibraryItem {
	
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
	private int id;
	@Id
	public int getId() {
		return this.id;
	}
	
	public void setId(int value) {
		this.id = value;
	}
	
	
}
