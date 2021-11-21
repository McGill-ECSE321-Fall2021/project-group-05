package ca.mcgill.ecse321.onlinelibrary.dto;

import ca.mcgill.ecse321.onlinelibrary.model.Member;

public class MemberLoginResponseDto {

	private MemberDto member;

	public MemberLoginResponseDto(Member member) {
		this.member = MemberDto.fromMember(member);
	}

	public MemberDto getMember() {
		return this.member;
	}
}
