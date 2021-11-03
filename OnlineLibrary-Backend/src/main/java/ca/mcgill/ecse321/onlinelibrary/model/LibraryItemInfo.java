package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.*;

@Entity
public abstract class LibraryItemInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	public Integer getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id=id;
	}

}
