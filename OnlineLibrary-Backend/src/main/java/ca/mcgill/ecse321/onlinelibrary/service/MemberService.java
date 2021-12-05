package ca.mcgill.ecse321.onlinelibrary.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.onlinelibrary.dao.LoanRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.MemberRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.OnlineAccountRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.RoomBookingRepository;
import ca.mcgill.ecse321.onlinelibrary.dto.CreateMemberRequestDto;
import ca.mcgill.ecse321.onlinelibrary.dto.CreateOnlineAccountRequestDto;
import ca.mcgill.ecse321.onlinelibrary.model.Loan;
import ca.mcgill.ecse321.onlinelibrary.model.Member;
import ca.mcgill.ecse321.onlinelibrary.model.OnlineAccount;
import ca.mcgill.ecse321.onlinelibrary.model.RoomBooking;

/**
 * Service to create, read, update, delete, or log in members.
 */
@Service
public class MemberService {

	// TODO Get this from application.properties
	private final int REGISTRATION_FEE = 1000;
	private final int MIN_PASSWD_LENGTH = 8;

	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private OnlineAccountRepository onlineAccountRepository;
	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private RoomBookingRepository roomBookingRepository;

	/**
	 * Creates a new member. If the given member DTO has a non-null online
	 * account, also creates a new online account.
	 *
	 * Throws an IllegalArgumentException if the full name or address is empty.
	 * If the online account is non-null, also validates the online account. See
	 * validateOnlineAccount() for the validation rules for an online account.
	 *
	 * @param newMember
	 *            Credentials and account information for a new member
	 * @return Account information for the newly-created member
	 */
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

	/**
	 * Creates an online account for an existing member.
	 *
	 *
	 *
	 * @param memberId
	 *            Primary key of an existing member
	 * @param accountDto
	 *            Credentials and account information for the new online account
	 * @return Account information for the newly-created online account
	 */
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

	/**
	 * Validates the given member credentials. If the credentials are valid,
	 * returns the member's account information. Otherwise, throws an
	 * IllegalArgumentException.
	 *
	 * @param username
	 *            Username of an existing member
	 * @param password
	 *            Password of the member
	 * @return Account information for the member
	 */
	public Member logIn(String username, String password) {
		// Find by username
		OnlineAccount account = onlineAccountRepository.findOnlineAccountByUsername(username);
		if (account == null) {
			throw new IllegalArgumentException("Invalid username.");
		}

		// Validate password
		// TODO Implement password hashing
		if (account.getPasswordHash().equals(password)) {
			return account.getAccountOwner();
		} else {
			throw new IllegalArgumentException("Invalid password.");
		}
	}

	/**
	 * Returns the account information for the member with the given ID.
	 *
	 * Throws an IllegalArgumentException if there is no member with the given
	 * ID.
	 *
	 * @param id
	 *            Primary key of an existing member
	 * @return Account information for the member
	 */
	@Transactional
	public Member getMemberById(int id) {
		Member member = memberRepository.findMemberById(id);

		if (member == null) {
			throw new IllegalArgumentException("Member with ID \"" + id + "\" not found.");
		}

		return member;
	}

	/**
	 * Returns the account information for all members.
	 *
	 * @return Account information for all members
	 */
	@Transactional
	public Iterable<Member> getAllMembers() {
		Iterable<Member> members = memberRepository.findAll();
		return members;
	}

	/**
	 * Marks the given member as active.
	 *
	 * Throws an IllegalStateException if the member's account is already
	 * active.
	 *
	 * @param member
	 *            Member to activate
	 * @return Activated member
	 */
	@Transactional
	public Member activateAccount(Member member) {
		member.activate();
		member = memberRepository.save(member);
		return member;
	}

	/**
	 * Applies a penalty to the given member's account.
	 *
	 * Throws an IllegalStateException if the member's account is inactive.
	 *
	 * @param member
	 *            Member to penalize
	 * @return Updated member
	 */
	@Transactional
	public Member applyStatusPenalty(Member member) {
		member.applyStatusPenalty();
		member = memberRepository.save(member);
		return member;
	}

	/**
	 * Removes a penalty from the given member's account.
	 *
	 * Throws an IllegalStateException if the member's account is inactive.
	 *
	 * @param member
	 *            Member from whom to remove a penalty
	 * @return Updated member
	 */
	@Transactional
	public Member removeStatusPenalty(Member member) {
		member.removeStatusPenalty();
		member = memberRepository.save(member);
		return member;
	}

	/**
	 * Updates the given member's account.
	 *
	 * Throws an IllegalArgumentException if there is no member with the given
	 * ID or if the new address or new full name is empty.
	 *
	 * @param id Primary key of an existing member
	 * @param newAddress New physical address for the member
	 * @param newFullName New full name for the member
	 * @return Updated member
	 */
	@Transactional
	public Member updateMember(Integer id, String newAddress, String newFullName) {
		Member member = memberRepository.findMemberById(id);

		if (member == null) {
			throw new IllegalArgumentException("A member with the id " + id + "does not exist");
		}

		if (newAddress.isBlank() || newFullName.isBlank()) {
			throw new IllegalArgumentException("address or full name cannot be blank");
		}
		member.setAddress(newAddress);
		member.setFullName(newFullName);
		return member;
	}

	/**
	 * Gets the list of loans for the given member.
	 *
	 * Throws an IllegalArgumentException if there is no member with the given ID.
	 *
	 * @param id Primary key of an existing member
	 * @return Loans for the given member
	 */
	@Transactional
	public Iterable<Loan> getLoansByMemberId(int id) {
		Member member = this.getMemberById(id);
		return loanRepository.findLoanByMember(member);
	}

	/**
	 * Gets the list of room bookings for the given member.
	 *
	 * Throws an IllegalArgumentException if there is no member with the given ID.
	 *
	 * @param memberId Primary key of an existing member
	 * @return All room bookings for the member
	 */
	@Transactional
	public List<RoomBooking> getRoomBookingsByMemberId(int memberId) {
		Member member = this.getMemberById(memberId);
		return roomBookingRepository.findRoomBookingByMember(member);
	}

	// ========================================================================
	// Helpers
	// ========================================================================

	/**
	 * Validates the credentials and account information for a new member.
	 *
	 * Throws an IllegalArgumentException if the full name or address is empty.
	 * If the online account is non-null, also validates the online account. See
	 * validateOnlineAccount() for the validation rules for an online account.
	 *
	 * @param newMember
	 *            Credentials and account information for a new member
	 * @return List of error messages (empty list if none)
	 */
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

	/**
	 * Validates the credentials and account information for a new online
	 * account.
	 *
	 * Throws an IllegalArgumentException if the username is empty, if the
	 * username is already taken, if the email address is empty, if the email
	 * address is invalid, or if the password is less than 8 characters in
	 * length.
	 *
	 * @param onlineAccount
	 *            Credentials and account information for a new online account
	 * @return Account information for the newly-created online account
	 */
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

	/**
	 * Validates the given email address. The email address should have an '@'
	 * preceded by at least one character and followed by at least one
	 * character. That is, the email address must match the regular expression
	 * '.+@.+'.
	 *
	 * @param emailAddress
	 *            Email address to validate
	 * @return True if the email address is valid and false otherwise
	 */
	private boolean isValidEmailAddress(String emailAddress) {
		// Check that email address is in the format '.+@.+'
		int indexOfAtSymbol = emailAddress.indexOf('@');
		if (indexOfAtSymbol <= 0 || indexOfAtSymbol == emailAddress.length() - 1) {
			return false;
		}
		return true;
	}
}
