package ca.mcgill.ecse321.onlinelibrary.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.onlinelibrary.dao.MemberRepository;
import ca.mcgill.ecse321.onlinelibrary.model.*;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	@Transactional
	public Member getMemberById(int id) {
		Member member = memberRepository.findMemberById(id);
		return member;
	}

	@Transactional
	public Member activateAccount(Member member) {
		if (member.getStatus() != Member.MemberStatus.INACTIVE) {
			throw new IllegalStateException("This member is already active.");
		}
		member.activate();
		memberRepository.save(member);
		return member;
	}

	@Transactional
	public Member updateMember(Integer id, String newAddress, String newFullName){
		Member member = memberRepository.findMemberById(id);

		if (member == null){
			throw new IllegalArgumentException("A member with the id " + id + "does not exist");
		}

		member.setAddress(newAddress);
		member.setFullName(newFullName);
		return member;
	}
}
