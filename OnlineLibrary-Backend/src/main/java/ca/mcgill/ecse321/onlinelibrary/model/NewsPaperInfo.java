import java.sql.Date;

public class NewsPaperInfo extends LibraryItemInfo{
	private Date publication;
	private String frequency;
	private int number;

	public NewsPaperInfo(int aId, Date aPublication, String aFrequency, int aNumber) {
	super(aId);
	publication = aPublication;
	frequency = aFrequency;
	number = aNumber;
	}

	public boolean setPublication(Date aPublication) {
	boolean wasSet = false;
	publication = aPublication;
	wasSet = true;
	return wasSet;
	}
	
	public boolean setFrequency(String aFrequency) {
	boolean wasSet = false;
	frequency = aFrequency;
	wasSet = true;
	return wasSet;
	}
	 
	public boolean setNumber(int aNumber) {
	boolean wasSet = false;
	number = aNumber;
	wasSet = true;
	return wasSet;
	}
	 
	public Date getPublication() {
	return publication;
	}
	
	public String getFrequency() {
	return frequency;
	}
	 
	public int getNumber() {
	return number;
	}
	
	public void delete() {
	super.delete();
	}

	public String toString() {
	return super.toString() + "["+
	"frequency" + ":" + getFrequency()+ "," +
	"number" + ":" + getNumber()+ "]" + System.getProperties().getProperty("line.separator") +
	"  " + "publication" + "=" + (getPublication() != null ? !getPublication().equals(this)  ? getPublication().toString().replaceAll("  ","    ") : "this" : "null");
	}
}
