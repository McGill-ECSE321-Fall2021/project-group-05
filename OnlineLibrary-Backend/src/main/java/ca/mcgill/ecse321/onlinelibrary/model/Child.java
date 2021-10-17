package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Child {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@OneToOne(optional = false)
	private Parent parent;

	public Integer getId() {
		return this.id;
	}

	public Parent getParent() {
		return this.parent;
	}

	public void setParent(Parent newParent) {
		this.parent = newParent;
	}
}
