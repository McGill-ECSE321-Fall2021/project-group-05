package ca.mcgill.ecse321.onlinelibrary.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.onlinelibrary.dto.CreateMemberRequestDto;
import ca.mcgill.ecse321.onlinelibrary.dto.CreateOnlineAccountRequestDto;
import ca.mcgill.ecse321.onlinelibrary.dto.MemberDto;
import ca.mcgill.ecse321.onlinelibrary.dto.OnlineAccountDto;
import ca.mcgill.ecse321.onlinelibrary.model.Member;
import ca.mcgill.ecse321.onlinelibrary.model.OnlineAccount;
import ca.mcgill.ecse321.onlinelibrary.service.MemberService;

@CrossOrigin(origins = "*")
@RestController
public class MemberController {

	@Autowired
	private MemberService memberService;

	/**
	 * Register a new member with or without an online account.
	 *
	 * The full name and address are required. If an online account is provided,
	 * the username, password, and email address are all required.
	 *
	 * @param newMemberDto
	 *            The registration information for the new member
	 * @return The created member
	 */
	@PostMapping(value = {"/member", "/member/"})
	public MemberDto registerMember(@RequestBody CreateMemberRequestDto newMemberDto) {
		Member member = memberService.registerMember(newMemberDto);
		return MemberDto.fromMember(member);
	}

	/**
	 * Adds an online account to an existing member that does not have an online
	 * account yet.
	 *
	 * The username, password, and email address are all required.
	 *
	 * @param id Primary key of an existing member
	 * @param newOnlineAccountDto Online account information
	 * @return The created online account
	 */
	@PostMapping(value = {"/member/{id}/onlineAccount", "/member/{id}/onlineAccount/"})
	public OnlineAccountDto createOnlineAccount(@PathVariable("id") int id,
			@RequestBody CreateOnlineAccountRequestDto newOnlineAccountDto) {
		OnlineAccount createdAccount = memberService.createOnlineAccount(id, newOnlineAccountDto);
		return OnlineAccountDto.fromOnlineAccount(createdAccount);
	}

	@GetMapping(value = {"/member/{id}", "/member/{id}/"})
	public MemberDto getMemberById(@PathVariable("id") int id) {
		Member member = memberService.getMemberById(id);
		return MemberDto.fromMember(member);
	}

	@GetMapping(value = {"/member/all", "/member/all/"})
	public List<MemberDto> getAllMembers() {
		Iterable<Member> members = memberService.getAllMembers();
		return StreamSupport.stream(members.spliterator(), true).map((Member m) -> MemberDto.fromMember(m))
				.collect(Collectors.toList());
	}

	@PutMapping(value = {"/member/{id}/activate", "/member/{id}/activate"})
	public MemberDto activateMemberAccount(@PathVariable("id") int id) {
		Member member = memberService.getMemberById(id);
		member = memberService.activateAccount(member);
		return MemberDto.fromMember(member);
	}

	@PutMapping(value = {"/member/{id}/applyPenalty", "/member/{id}/applyPenalty/"})
	public MemberDto applyPenalty(@PathVariable("id") int id) {
		Member member = memberService.getMemberById(id);
		member = memberService.applyStatusPenalty(member);
		return MemberDto.fromMember(member);
	}

	@PutMapping(value = {"/member/{id}/removePenalty", "/member/{id}/removePenalty/"})
	public MemberDto removePenalty(@PathVariable("id") int id) {
		Member member = memberService.getMemberById(id);
		member = memberService.removeStatusPenalty(member);
		return MemberDto.fromMember(member);
	}
}
