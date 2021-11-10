package ca.mcgill.ecse321.onlinelibrary.service;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.onlinelibrary.dao.MemberRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.OnlineAccountRepository;
import ca.mcgill.ecse321.onlinelibrary.dto.CreateMemberRequestDto;
import ca.mcgill.ecse321.onlinelibrary.dto.CreateOnlineAccountRequestDto;
import ca.mcgill.ecse321.onlinelibrary.model.Member;
import ca.mcgill.ecse321.onlinelibrary.model.OnlineAccount;

@Service
public class MemberService {

	// TODO Get this from application.properties
	private final int REGISTRATION_FEE = 1000;
	private final int MIN_PASSWD_LENGTH = 8;

	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private OnlineAccountRepository onlineAccountRepository;

	@Transactional
	public Member registerMember(CreateMemberRequestDto newMember) {
		if (newMember == null) {
			throw new IllegalArgumentException("Member information not provided");
		}

		ArrayList<String> errors = validateNewMember(newMember);
		if (errors.size() > 0) {
			throw new IllegalArgumentException(String.join(" ", errors));
		}

		// Create member
		int registrationFee = newMember.isCitizen() ? 0 : REGISTRATION_FEE;
		Member savedMember = new Member(newMember.getAddress().trim(), newMember.getFullName().trim(), registrationFee);

		// Create online account if applicable
		CreateOnlineAccountRequestDto onlineAccount = newMember.getOnlineAccount();
		if (onlineAccount != null) {
			OnlineAccount savedOnlineAccount = new OnlineAccount(onlineAccount.getPassword().trim(),
					onlineAccount.getUsername().trim(), onlineAccount.getEmailAddress().trim(), savedMember);
			savedMember.setOnlineAccount(savedOnlineAccount);
		}

		savedMember = memberRepository.save(savedMember);
		return savedMember;
	}

	@Transactional
	public OnlineAccount createOnlineAccount(int memberId, CreateOnlineAccountRequestDto accountDto) {
		Member member = memberRepository.findMemberById(memberId);
		if (member == null) {
			throw new IllegalArgumentException("Member with ID \"" + memberId + "\" not found.");
		}

		if (member.getOnlineAccount() != null) {
			throw new IllegalArgumentException("Member with ID \"" + memberId + "\" already has an online account.");
		}

		ArrayList<String> errors = validateNewOnlineAccount(accountDto);
		if (errors.size() > 0) {
			throw new IllegalArgumentException(String.join(" ", errors));
		}

		OnlineAccount createdOnlineAccount = new OnlineAccount(accountDto.getPassword().trim(),
				accountDto.getUsername().trim(), accountDto.getEmailAddress().trim(), member);
		member.setOnlineAccount(createdOnlineAccount);
		// Only need to save member and the update operation will cascade to the
		// online account (right?)
		memberRepository.save(member);

		return createdOnlineAccount;
	}

	@Transactional
	public Member getMemberById(int id) {
		Member member = memberRepository.findMemberById(id);

		if (member == null) {
			throw new IllegalArgumentException("Member with ID \"" + id + "\" not found.");
		}

		return member;
	}

	// ========================================================================
	// Helpers
	// ========================================================================

	private ArrayList<String> validateNewMember(CreateMemberRequestDto newMember) {
		ArrayList<String> errors = new ArrayList<String>();

		if (newMember.getFullName() == null || newMember.getFullName().trim().length() == 0) {
			errors.add("Full name cannot be empty.");
		}
		if (newMember.getAddress() == null || newMember.getAddress().trim().length() == 0) {
			errors.add("Address cannot be empty.");
		}

		if (newMember.getOnlineAccount() != null) {
			ArrayList<String> onlineAccountErrors = validateNewOnlineAccount(newMember.getOnlineAccount());
			errors.addAll(onlineAccountErrors);
		}

		return errors;
	}

	private ArrayList<String> validateNewOnlineAccount(CreateOnlineAccountRequestDto onlineAccount) {
		ArrayList<String> errors = new ArrayList<String>();

		if (onlineAccount.getUsername() == null) {
			errors.add("Username cannot be empty.");
		} else {
			String trimmedUsername = onlineAccount.getUsername().trim();
			if (trimmedUsername.length() == 0) {
				errors.add("Username cannot be empty.");
			}
			if (onlineAccountRepository.existsOnlineAccountByUsername(trimmedUsername)) {
				errors.add("Username already taken.");
			}
		}
		if (onlineAccount.getEmailAddress() == null) {
			errors.add("Email address cannot be empty.");
		} else {
			String trimmedEmailAddress = onlineAccount.getEmailAddress().trim();
			if (onlineAccount.getEmailAddress().trim().length() == 0) {
				errors.add("Email address cannot be empty.");
			}
			if (!isValidEmailAddress(trimmedEmailAddress)) {
				errors.add("Invalid email address.");
			}
		}
		if (onlineAccount.getPassword() == null || onlineAccount.getPassword().trim().length() < MIN_PASSWD_LENGTH) {
			errors.add("Password must be at least " + MIN_PASSWD_LENGTH + " characters in length.");
		}

		return errors;
	}

	private boolean isValidEmailAddress(String emailAddress) {
		// Check that email address is in the format '.@.'
		int indexOfAtSymbol = emailAddress.indexOf('@');
		if (indexOfAtSymbol <= 0 || indexOfAtSymbol == emailAddress.length() - 1) {
			return false;
		}
		return true;
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
}
