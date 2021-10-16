package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Librarian {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	public Integer getId() {
		return this.id;
	}
}
