package ca.mcgill.ecse321.onlinelibrary.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Holds the information required to create an online account.
 *
 * @author louis
 */
public class CreateOnlineAccountRequestDto {

	private String username;
	private String emailAddress;
	private String password;

	@JsonCreator
	public CreateOnlineAccountRequestDto(@JsonProperty("username") String username,
			@JsonProperty("emailAddress") String emailAddress, @JsonProperty("password") String password) {
		this.username = username;
		this.emailAddress = emailAddress;
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	public String getPassword() {
		return this.password;
	}
}
