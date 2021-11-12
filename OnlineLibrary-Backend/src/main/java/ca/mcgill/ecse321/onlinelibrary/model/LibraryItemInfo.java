package ca.mcgill.ecse321.onlinelibrary.model;

import ca.mcgill.ecse321.onlinelibrary.dto.LibraryItemInfoDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public abstract class LibraryItemInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	public abstract LibraryItemInfoDto convertToDto();
	
	public Integer getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id=id;
	}

}
