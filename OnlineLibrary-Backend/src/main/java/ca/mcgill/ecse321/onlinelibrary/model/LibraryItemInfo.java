
public abstract class LibraryItemInfo {
	
	private int id;
	
	//CONSTRUCTOR
	public LibraryItemInfo(int aId) {
		id = aId;
	}
	
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
