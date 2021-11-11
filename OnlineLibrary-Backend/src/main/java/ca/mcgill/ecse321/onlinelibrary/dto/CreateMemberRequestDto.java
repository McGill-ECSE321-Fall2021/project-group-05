package ca.mcgill.ecse321.onlinelibrary.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Holds the information required to create a new member, possibly with an
 * online account.
 *
 * @author louis
 */
public class CreateMemberRequestDto {

	private String fullName;
	private String address;
	private boolean isCitizen;
	private CreateOnlineAccountRequestDto onlineAccount;

	@JsonCreator
	public CreateMemberRequestDto(@JsonProperty("fullName") String fullName, @JsonProperty("address") String address,
			@JsonProperty("isCitizen") boolean isCitizen, @JsonProperty("onlineAccount") CreateOnlineAccountRequestDto onlineAccount) {
		this.fullName = fullName;
		this.address = address;
		this.isCitizen = isCitizen;
		this.onlineAccount = onlineAccount;
	}

	public String getFullName() {
		return fullName;
	}

	public String getAddress() {
		return address;
	}

	public boolean isCitizen() {
		return this.isCitizen;
	}

	public CreateOnlineAccountRequestDto getOnlineAccount() {
		return this.onlineAccount;
	}
}
