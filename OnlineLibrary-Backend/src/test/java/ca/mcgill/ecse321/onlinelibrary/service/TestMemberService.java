package ca.mcgill.ecse321.onlinelibrary.service;

import static ca.mcgill.ecse321.onlinelibrary.service.TestHelper.assertContains;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.mockito.invocation.InvocationOnMock;
import ca.mcgill.ecse321.onlinelibrary.dao.MemberRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.OnlineAccountRepository;
import ca.mcgill.ecse321.onlinelibrary.dto.CreateMemberRequestDto;
import ca.mcgill.ecse321.onlinelibrary.dto.CreateOnlineAccountRequestDto;
import ca.mcgill.ecse321.onlinelibrary.model.Member;
import ca.mcgill.ecse321.onlinelibrary.model.OnlineAccount;

import org.mockito.InjectMocks;
import org.mockito.Mock;

@ExtendWith(MockitoExtension.class)
public class TestMemberService {

	@Mock
	private MemberRepository memberDao;
	@Mock
	private OnlineAccountRepository onlineAccountDao;
	@InjectMocks
	private MemberService memberService;

	// TODO Get this from application.properties
	private final int REGISTRATION_FEE = 1000;
	private final int MIN_PASSWD_LENGTH = 8;

	// Existing member
	private static final int OLD_MEMBER_ID = 42;
	private static final String OLD_MEMBER_FULL_NAME = "John Doe";
	private static final String OLD_MEMBER_ADDRESS = "123 Main Street";
	private static final String OLD_MEMBER_USERNAME = "john.doe";
	private static final String OLD_MEMBER_EMAIL_ADDRESS = "john.doe@gmail.com";
	private final String OLD_MEMBER_PASSWD = "m".repeat(MIN_PASSWD_LENGTH);

	// New member
	private static final String NEW_MEMBER_FULL_NAME = "Obi-Wan Kenobi";
	private static final String NEW_MEMBER_ADDRESS = "212 McGill Street";
	private static final String NEW_MEMBER_USERNAME = "obi-wan.kenobi";
	private static final String NEW_MEMBER_EMAIL_ADDRESS = "obi-wan.kenobi@mail.mcgill.ca";
	private final String NEW_MEMBER_PASSWD = "o".repeat(MIN_PASSWD_LENGTH);

	// Invalid credentials
	private static final int INVALID_MEMBER_ID = 999999;

	/**
	 * Assume the database already contains one inactive member with ID
	 * MEMBER_ID, full name MEMBER_FULL_NAME, address MEMBER_ADDRESS, username
	 * MEMBER_USERNAME, email address MEMBER_EMAIL_ADDRESS, and password
	 * MEMBER_PASSWD.
	 */
	@BeforeEach
	public void setMockOuput() {
		lenient().when(memberDao.findMemberById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (OLD_MEMBER_ID == (int) invocation.getArgument(0)) {
				Member member = new Member(OLD_MEMBER_ADDRESS, OLD_MEMBER_FULL_NAME);
				OnlineAccount onlineAccount = new OnlineAccount(OLD_MEMBER_PASSWD, OLD_MEMBER_USERNAME,
						OLD_MEMBER_EMAIL_ADDRESS, member);
				member.setOnlineAccount(onlineAccount);
				return member;
			} else {
				return null;
			}
		});

		lenient().when(onlineAccountDao.existsOnlineAccountByUsername(any(String.class)))
		.thenAnswer((InvocationOnMock invocation) -> {
			if (OLD_MEMBER_USERNAME.equals(invocation.getArgument(0))) {
				return true;
			} else {
				return false;
			}
		});

		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(memberDao.save(any(Member.class))).thenAnswer(returnParameterAsAnswer);
	}

	@Test
	public void testGetMemberByIdSuccessful() {
		Member member = memberService.getMemberById(OLD_MEMBER_ID);

		assertNotNull(member);
		assertEquals(OLD_MEMBER_FULL_NAME, member.getFullName());
		assertEquals(OLD_MEMBER_ADDRESS, member.getAddress());
		assertEquals(Member.MemberStatus.INACTIVE, member.getStatus());

		OnlineAccount onlineAccount = member.getOnlineAccount();
		assertNotNull(onlineAccount);
		assertEquals(OLD_MEMBER_USERNAME, onlineAccount.getUsername());
		assertEquals(OLD_MEMBER_EMAIL_ADDRESS, onlineAccount.getEmailAddress());
		// TODO Update this if/when password hashing is implemented
		assertEquals(OLD_MEMBER_PASSWD, onlineAccount.getPasswordHash());
	}

	@Test
	public void testGetMemberByIdInexistent() {
		Exception error = assertThrows(IllegalArgumentException.class,
				() -> memberService.getMemberById(INVALID_MEMBER_ID));
		assertContains("Member with ID \"" + INVALID_MEMBER_ID + "\" not found.", error.getMessage());
	}

	@Test
	public void testActivateMemberAccount() {
		Member member = memberService.getMemberById(OLD_MEMBER_ID);
		assert (member.getStatus() == Member.MemberStatus.INACTIVE);
		member = memberService.activateAccount(member);
		assertNotNull(member);
		assert (member.getStatus() == Member.MemberStatus.GREEN);
	}

	@Test
	public void testRegisterCitizenInPerson() {
		CreateMemberRequestDto memberDto = new CreateMemberRequestDto("  " + NEW_MEMBER_FULL_NAME + "  ",
				"  " + NEW_MEMBER_ADDRESS + "  ", true, null);
		Member member = memberService.registerMember(memberDto);

		assertNotNull(member);
		assertEquals(NEW_MEMBER_FULL_NAME, member.getFullName());
		assertEquals(NEW_MEMBER_ADDRESS, member.getAddress());
		assertEquals(Member.MemberStatus.INACTIVE, member.getStatus());
		assertEquals(0, member.getTotalFee());
		assertNull(member.getOnlineAccount());
	}

	@Test
	public void testRegisterCitizenOnline() {
		CreateOnlineAccountRequestDto onlineAccountDto = new CreateOnlineAccountRequestDto("  " + NEW_MEMBER_USERNAME + "  ",
				"  " + NEW_MEMBER_EMAIL_ADDRESS + "  ", "  " + NEW_MEMBER_PASSWD + "  ");
		CreateMemberRequestDto memberDto = new CreateMemberRequestDto("  " + NEW_MEMBER_FULL_NAME + "  ",
				"  " + NEW_MEMBER_ADDRESS + "  ", true, onlineAccountDto);
		Member member = memberService.registerMember(memberDto);

		assertNotNull(member);
		assertEquals(NEW_MEMBER_FULL_NAME, member.getFullName());
		assertEquals(NEW_MEMBER_ADDRESS, member.getAddress());
		assertEquals(Member.MemberStatus.INACTIVE, member.getStatus());
		assertEquals(0, member.getTotalFee());

		OnlineAccount onlineAccount = member.getOnlineAccount();
		assertNotNull(onlineAccount);
		assertEquals(NEW_MEMBER_USERNAME, onlineAccount.getUsername());
		assertEquals(NEW_MEMBER_EMAIL_ADDRESS, onlineAccount.getEmailAddress());
		// TODO Update this if/when we implement pasword hashing
		assertEquals(NEW_MEMBER_PASSWD, onlineAccount.getPasswordHash());
	}

	@Test
	public void testRegisterNonCitizenInPerson() {
		CreateMemberRequestDto memberDto = new CreateMemberRequestDto("  " + NEW_MEMBER_FULL_NAME + "  ",
				"  " + NEW_MEMBER_ADDRESS + "  ", false, null);
		Member member = memberService.registerMember(memberDto);

		assertNotNull(member);
		assertEquals(NEW_MEMBER_FULL_NAME, member.getFullName());
		assertEquals(NEW_MEMBER_ADDRESS, member.getAddress());
		assertEquals(Member.MemberStatus.INACTIVE, member.getStatus());
		assertEquals(REGISTRATION_FEE, member.getTotalFee());
		assertNull(member.getOnlineAccount());
	}

	@Test
	public void testRegisterNonCitizenOnline() {
		CreateOnlineAccountRequestDto onlineAccountDto = new CreateOnlineAccountRequestDto("  " + NEW_MEMBER_USERNAME + "  ",
				"  " + NEW_MEMBER_EMAIL_ADDRESS + "  ", "  " + NEW_MEMBER_PASSWD + "  ");
		CreateMemberRequestDto memberDto = new CreateMemberRequestDto("  " + NEW_MEMBER_FULL_NAME + "  ",
				"  " + NEW_MEMBER_ADDRESS + "  ", false, onlineAccountDto);
		Member member = memberService.registerMember(memberDto);

		assertNotNull(member);
		assertEquals(NEW_MEMBER_FULL_NAME, member.getFullName());
		assertEquals(NEW_MEMBER_ADDRESS, member.getAddress());
		assertEquals(Member.MemberStatus.INACTIVE, member.getStatus());
		assertEquals(REGISTRATION_FEE, member.getTotalFee());

		OnlineAccount onlineAccount = member.getOnlineAccount();
		assertNotNull(onlineAccount);
		assertEquals(NEW_MEMBER_USERNAME, onlineAccount.getUsername());
		assertEquals(NEW_MEMBER_EMAIL_ADDRESS, onlineAccount.getEmailAddress());
		// TODO Update this if/when we implement pasword hashing
		assertEquals(NEW_MEMBER_PASSWD, onlineAccount.getPasswordHash());
	}
}
