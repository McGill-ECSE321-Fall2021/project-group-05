package ca.mcgill.ecse321.onlinelibrary.dto;

import ca.mcgill.ecse321.onlinelibrary.model.OnlineAccount;

public class OnlineAccountDto {

	private String username;
	private String emailAddress;

	public OnlineAccountDto(String username, String emailAddress) {
		this.username = username;
		this.emailAddress = emailAddress;
	}

	public static OnlineAccountDto fromOnlineAccount(OnlineAccount onlineAccount) {
		return new OnlineAccountDto(onlineAccount.getUsername(), onlineAccount.getEmailAddress());
	}

	public String getUsername() {
		return this.username;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}
}
