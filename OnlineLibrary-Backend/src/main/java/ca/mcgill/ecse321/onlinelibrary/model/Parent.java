package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Parent {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@OneToOne(optional = true, cascade = CascadeType.ALL)
	private Child child;

	public Integer getId() {
		return this.id;
	}

	public Child getChild() {
		return this.child;
	}

	public void setChild(Child newChild) {
		this.child = newChild;
	}
}
