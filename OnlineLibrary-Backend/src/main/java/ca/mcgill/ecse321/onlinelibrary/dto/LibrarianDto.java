package ca.mcgill.ecse321.onlinelibrary.dto;

public class LibrarianDto {

	private Integer id;
	private String fullName;
	private String username;
	private boolean isHead;

	public LibrarianDto(int id, String fullName, String username, boolean isHead) {
		this.id = id;
		this.fullName = fullName;
		this.username = username;
		this.isHead = isHead;
	}

	public int getId() {
		return this.id;
	}

	public String getFullName() {
		return this.fullName;
	}

	public String getUsername() {
		return this.username;
	}

	public boolean isHead() {
		return this.isHead;
	}
}
