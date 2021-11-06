package ca.mcgill.ecse321.onlinelibrary.dto;

import ca.mcgill.ecse321.onlinelibrary.model.Member;

public class MemberDto {
	private Integer id;
	private String address;
	private String fullName;
	private MemberDtoStatus status;
	public enum MemberDtoStatus {
		GREEN, YELLOW, RED, BLACKLISTED, INACTIVE
	}
	// Account fees (e.g. registration fee, late fees) in cents
	private int totalFee;
	private OnlineAccountDto onlineAccount;

	public MemberDto(Integer id, String address, String fullName, MemberDtoStatus status, int totalFee,
			OnlineAccountDto onlineAccount) {
		this.id = id;
		this.address = address;
		this.fullName = fullName;
		this.status = status;
		this.totalFee = totalFee;
		this.onlineAccount = onlineAccount;
	}

	public static MemberDto fromMember(Member member) {
		if (member == null) {
			throw new IllegalArgumentException("There is no such Member");
		}
		OnlineAccountDto onlineAccount = member.getOnlineAccount() == null
				? null
						: OnlineAccountDto.fromOnlineAccount(member.getOnlineAccount());
		return new MemberDto(member.getId(), member.getAddress(), member.getFullName(),
				fromMemberStatus(member.getStatus()), member.getTotalFee(), onlineAccount);
	}

	private static MemberDto.MemberDtoStatus fromMemberStatus(Member.MemberStatus memberStatus) {
		return switch (memberStatus) {
			case BLACKLISTED -> MemberDto.MemberDtoStatus.BLACKLISTED;
			case RED -> MemberDto.MemberDtoStatus.RED;
			case YELLOW -> MemberDto.MemberDtoStatus.YELLOW;
			case GREEN -> MemberDto.MemberDtoStatus.GREEN;
			case INACTIVE -> MemberDto.MemberDtoStatus.INACTIVE;
		};
	}

	public Integer getId() {
		return id;
	}

	public String getAddress() {
		return address;
	}

	public String getFullName() {
		return fullName;
	}

	public MemberDtoStatus getStatus() {
		return status;
	}

	public int getTotalFee() {
		return totalFee;
	}

	public OnlineAccountDto getOnlineAccount() {
		return this.onlineAccount;
	}
}
