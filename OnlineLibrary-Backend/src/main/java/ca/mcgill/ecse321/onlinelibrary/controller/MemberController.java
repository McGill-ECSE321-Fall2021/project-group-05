package ca.mcgill.ecse321.onlinelibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.onlinelibrary.dto.CreateMemberRequestDto;
import ca.mcgill.ecse321.onlinelibrary.dto.MemberDto;
import ca.mcgill.ecse321.onlinelibrary.model.Member;
import ca.mcgill.ecse321.onlinelibrary.service.MemberService;

@CrossOrigin(origins = "*")
@RestController
public class MemberController {

	@Autowired
	private MemberService memberService;

	@PostMapping(value = {"/member", "/member/"})
	public MemberDto registerMember(@RequestBody CreateMemberRequestDto newMemberDto) {
		Member member = memberService.registerMember(newMemberDto);
		return MemberDto.fromMember(member);
	}

	@PostMapping(value = {"/activate/{id}", "/activate/{id}/"})
	public MemberDto activateMemberAccount(@PathVariable("id") int id) {
		Member member = memberService.getMemberById(id);
		member = memberService.activateAccount(member);
		return MemberDto.fromMember(member);
	}
}
