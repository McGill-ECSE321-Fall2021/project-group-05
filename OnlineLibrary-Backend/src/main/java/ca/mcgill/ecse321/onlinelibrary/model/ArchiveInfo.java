import java.sql.Date;

public class ArchiveInfo extends LibraryItemInfo {
	
	private String title;
	private String description;
	private Date publicationDate;

	// CONSTRUCTOR
	public ArchiveInfo(int aId, String aTitle, String aDescription, Date aPublicationDate) {
	super(aId);
	title = aTitle;
	description = aDescription;
	publicationDate = aPublicationDate;
	}
	
	public boolean setTitle(String aTitle) {
	boolean wasSet = false;
	title = aTitle;
	wasSet = true;
	return wasSet;
	}
	 
	public boolean setDescription(String aDescription) {
	boolean wasSet = false;
	description = aDescription;
	wasSet = true;
	return wasSet;
	}
	 
	public boolean setPublicationDate(Date aPublicationDate) {
	boolean wasSet = false;
	publicationDate = aPublicationDate;
	wasSet = true;
	return wasSet;
	}

	public String getTitle() {
	return title;
	}

	public String getDescription() {
	return description;
	}
	 
	public Date getPublicationDate() {
	return publicationDate;
	}
	 
	public void delete() {
	super.delete();
	}

	public String toString() {
	return super.toString() + "["+
	"title" + ":" + getTitle()+ "," +
	"description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
	"  " + "publicationDate" + "=" + (getPublicationDate() != null ? !getPublicationDate().equals(this)  ? getPublicationDate().toString().replaceAll("  ","    ") : "this" : "null");
	}
	
}
