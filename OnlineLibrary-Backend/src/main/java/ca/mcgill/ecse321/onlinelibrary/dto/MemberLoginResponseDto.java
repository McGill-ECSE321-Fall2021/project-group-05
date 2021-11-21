package ca.mcgill.ecse321.onlinelibrary.dto;

import ca.mcgill.ecse321.onlinelibrary.model.Member;

public class MemberLoginResponseDto {

	// TODO Add JWT token

	private MemberDto member;

	public MemberLoginResponseDto(Member member) {
		this.member = MemberDto.fromMember(member);
	}

	public MemberDto getMember() {
		return this.member;
	}
}
