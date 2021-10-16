package ca.mcgill.ecse321.onlinelibrary.model;
import javax.persistence.*;

@Entity
public abstract class LibraryItemInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	public boolean setId(int aId) {
	boolean wasSet = false;
	id = aId;
	wasSet = true;
	return wasSet;
	}

	public int getId() {
	return id;
	}

	public void delete(){}

	public String toString() {
	return super.toString() + "["+ "id" + ":" + getId()+ "]";
	}
}
