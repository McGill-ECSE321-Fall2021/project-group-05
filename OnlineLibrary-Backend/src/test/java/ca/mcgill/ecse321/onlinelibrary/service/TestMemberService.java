package ca.mcgill.ecse321.onlinelibrary.service;

import static ca.mcgill.ecse321.onlinelibrary.service.TestHelper.assertContains;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

	// Existing member with online account
	private static final int OLD_MEMBER_ID = 42;
	private static final String OLD_MEMBER_FULL_NAME = "John Doe";
	private static final String OLD_MEMBER_ADDRESS = "123 Main Street";
	private static final String OLD_MEMBER_USERNAME = "john.doe";
	private static final String OLD_MEMBER_EMAIL_ADDRESS = "john.doe@gmail.com";
	private final String OLD_MEMBER_PASSWD = "m".repeat(MIN_PASSWD_LENGTH);

	// Existing member without online account (but who wishes to create one)
	private static final int OFFLINE_MEMBER_ID = 43;
	private static final String OFFLINE_MEMBER_FULL_NAME = "Yoda";
	private static final String OFFLINE_MEMBER_ADDRESS = "900 McGill Street";
	private static final String OFFLINE_MEMBER_USERNAME = "yoda900";
	private static final String OFFLINE_MEMBER_EMAIL_ADDRESS = "yoda@mail.mcgill.ca";
	private final String OFFLINE_MEMBER_PASSWD = "y".repeat(MIN_PASSWD_LENGTH);

	// New member
	private static final String NEW_MEMBER_FULL_NAME = "Obi-Wan Kenobi";
	private static final String NEW_MEMBER_ADDRESS = "212 McGill Street";
	private static final String NEW_MEMBER_USERNAME = "obi-wan.kenobi";
	private static final String NEW_MEMBER_EMAIL_ADDRESS = "obi-wan.kenobi@mail.mcgill.ca";
	private final String NEW_MEMBER_PASSWD = "o".repeat(MIN_PASSWD_LENGTH);

	// Invalid credentials
	private static final int INVALID_MEMBER_ID = 999999;
	private static final String INVALID_EMAIL_ADDRESS = "blah.blah";
	private final String SHORT_PASSWD = "p".repeat(MIN_PASSWD_LENGTH - 1);

	/**
	 * Assume the database already contains
	 *
	 * <ul>
	 * <li>one inactive member with ID OLD_MEMBER_ID, full name
	 * OLD_MEMBER_FULL_NAME, address OLD_MEMBER_ADDRESS, username
	 * OLD_MEMBER_USERNAME, email address OLD_MEMBER_EMAIL_ADDRESS, and password
	 * OLD_MEMBER_PASSWD
	 * <li>one inactive member with ID OFFLINE_MEMBER_ID, full name
	 * OFFLINE_MEMBER_FULL_NAME, address OFFLINE_MEMBER_ADDRESS, and no online
	 * account
	 * </ul>
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
			} else if (OFFLINE_MEMBER_ID == (int) invocation.getArgument(0)) {
				Member member = new Member(OFFLINE_MEMBER_ADDRESS, OFFLINE_MEMBER_FULL_NAME);
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
		CreateOnlineAccountRequestDto onlineAccountDto = new CreateOnlineAccountRequestDto(
				"  " + NEW_MEMBER_USERNAME + "  ", "  " + NEW_MEMBER_EMAIL_ADDRESS + "  ",
				"  " + NEW_MEMBER_PASSWD + "  ");
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
		CreateOnlineAccountRequestDto onlineAccountDto = new CreateOnlineAccountRequestDto(
				"  " + NEW_MEMBER_USERNAME + "  ", "  " + NEW_MEMBER_EMAIL_ADDRESS + "  ",
				"  " + NEW_MEMBER_PASSWD + "  ");
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

	@Test
	public void testRegisterInPersonNullFullName() {
		CreateMemberRequestDto memberDto = new CreateMemberRequestDto(null, NEW_MEMBER_ADDRESS, true, null);

		Exception error = assertThrows(IllegalArgumentException.class, () -> memberService.registerMember(memberDto));
		assertContains("Full name cannot be empty.", error.getMessage());
	}

	@Test
	public void testRegisterInPersonEmptyFullName() {
		CreateMemberRequestDto memberDto = new CreateMemberRequestDto("  ", NEW_MEMBER_ADDRESS, true, null);

		Exception error = assertThrows(IllegalArgumentException.class, () -> memberService.registerMember(memberDto));
		assertContains("Full name cannot be empty.", error.getMessage());
	}

	@Test
	public void testRegisterInPersonNullAddress() {
		CreateMemberRequestDto memberDto = new CreateMemberRequestDto(NEW_MEMBER_FULL_NAME, null, true, null);

		Exception error = assertThrows(IllegalArgumentException.class, () -> memberService.registerMember(memberDto));
		assertContains("Address cannot be empty.", error.getMessage());
	}

	@Test
	public void testRegisterInPersonEmptyAddress() {
		CreateMemberRequestDto memberDto = new CreateMemberRequestDto(NEW_MEMBER_FULL_NAME, "  ", true, null);

		Exception error = assertThrows(IllegalArgumentException.class, () -> memberService.registerMember(memberDto));
		assertContains("Address cannot be empty.", error.getMessage());
	}

	@Test
	public void testRegisterOnlineNullUsername() {
		CreateOnlineAccountRequestDto onlineAccountDto = new CreateOnlineAccountRequestDto(null,
				NEW_MEMBER_EMAIL_ADDRESS, NEW_MEMBER_PASSWD);
		CreateMemberRequestDto memberDto = new CreateMemberRequestDto(NEW_MEMBER_FULL_NAME, NEW_MEMBER_ADDRESS, true,
				onlineAccountDto);

		Exception error = assertThrows(IllegalArgumentException.class, () -> memberService.registerMember(memberDto));
		assertContains("Username cannot be empty.", error.getMessage());
	}

	@Test
	public void testRegisterOnlineEmptyUsername() {
		CreateOnlineAccountRequestDto onlineAccountDto = new CreateOnlineAccountRequestDto("  ",
				NEW_MEMBER_EMAIL_ADDRESS, NEW_MEMBER_PASSWD);
		CreateMemberRequestDto memberDto = new CreateMemberRequestDto(NEW_MEMBER_FULL_NAME, NEW_MEMBER_ADDRESS, true,
				onlineAccountDto);

		Exception error = assertThrows(IllegalArgumentException.class, () -> memberService.registerMember(memberDto));
		assertContains("Username cannot be empty.", error.getMessage());
	}

	@Test
	public void testRegisterOnlineDuplicateUsername() {
		CreateOnlineAccountRequestDto onlineAccountDto = new CreateOnlineAccountRequestDto(OLD_MEMBER_USERNAME,
				NEW_MEMBER_EMAIL_ADDRESS, NEW_MEMBER_PASSWD);
		CreateMemberRequestDto memberDto = new CreateMemberRequestDto(NEW_MEMBER_FULL_NAME, NEW_MEMBER_ADDRESS, true,
				onlineAccountDto);

		Exception error = assertThrows(IllegalArgumentException.class, () -> memberService.registerMember(memberDto));
		assertContains("Username already taken.", error.getMessage());
	}

	@Test
	public void testRegisterOnlineNullEmailAddress() {
		CreateOnlineAccountRequestDto onlineAccountDto = new CreateOnlineAccountRequestDto(NEW_MEMBER_USERNAME, null,
				NEW_MEMBER_PASSWD);
		CreateMemberRequestDto memberDto = new CreateMemberRequestDto(NEW_MEMBER_FULL_NAME, NEW_MEMBER_ADDRESS, true,
				onlineAccountDto);

		Exception error = assertThrows(IllegalArgumentException.class, () -> memberService.registerMember(memberDto));
		assertContains("Email address cannot be empty.", error.getMessage());
	}

	@Test
	public void testRegisterOnlineEmptyEmailAddress() {
		CreateOnlineAccountRequestDto onlineAccountDto = new CreateOnlineAccountRequestDto(NEW_MEMBER_USERNAME, "  ",
				NEW_MEMBER_PASSWD);
		CreateMemberRequestDto memberDto = new CreateMemberRequestDto(NEW_MEMBER_FULL_NAME, NEW_MEMBER_ADDRESS, true,
				onlineAccountDto);

		Exception error = assertThrows(IllegalArgumentException.class, () -> memberService.registerMember(memberDto));
		assertContains("Email address cannot be empty.", error.getMessage());
	}

	@Test
	public void testRegisterOnlineInvalidEmailAddress() {
		CreateOnlineAccountRequestDto onlineAccountDto = new CreateOnlineAccountRequestDto(NEW_MEMBER_USERNAME,
				INVALID_EMAIL_ADDRESS, NEW_MEMBER_PASSWD);
		CreateMemberRequestDto memberDto = new CreateMemberRequestDto(NEW_MEMBER_FULL_NAME, NEW_MEMBER_ADDRESS, true,
				onlineAccountDto);

		Exception error = assertThrows(IllegalArgumentException.class, () -> memberService.registerMember(memberDto));
		assertContains("Invalid email address.", error.getMessage());
	}

	@Test
	public void testRegisterOnlineNullPassword() {
		CreateOnlineAccountRequestDto onlineAccountDto = new CreateOnlineAccountRequestDto(NEW_MEMBER_USERNAME,
				NEW_MEMBER_EMAIL_ADDRESS, null);
		CreateMemberRequestDto memberDto = new CreateMemberRequestDto(NEW_MEMBER_FULL_NAME, NEW_MEMBER_ADDRESS, true,
				onlineAccountDto);

		Exception error = assertThrows(IllegalArgumentException.class, () -> memberService.registerMember(memberDto));
		assertContains("Password must be at least " + MIN_PASSWD_LENGTH + " characters in length.", error.getMessage());
	}

	@Test
	public void testRegisterOnlineShortPassword() {
		CreateOnlineAccountRequestDto onlineAccountDto = new CreateOnlineAccountRequestDto(NEW_MEMBER_USERNAME,
				NEW_MEMBER_EMAIL_ADDRESS, SHORT_PASSWD);
		CreateMemberRequestDto memberDto = new CreateMemberRequestDto(NEW_MEMBER_FULL_NAME, NEW_MEMBER_ADDRESS, true,
				onlineAccountDto);

		Exception error = assertThrows(IllegalArgumentException.class, () -> memberService.registerMember(memberDto));
		assertContains("Password must be at least " + MIN_PASSWD_LENGTH + " characters in length.", error.getMessage());
	}

	@Test
	public void testRegisterOnlineMultipleErrors() {
		CreateOnlineAccountRequestDto onlineAccountDto = new CreateOnlineAccountRequestDto(NEW_MEMBER_USERNAME, "  ",
				NEW_MEMBER_PASSWD);
		CreateMemberRequestDto memberDto = new CreateMemberRequestDto(null, NEW_MEMBER_ADDRESS, true, onlineAccountDto);

		Exception error = assertThrows(IllegalArgumentException.class, () -> memberService.registerMember(memberDto));
		assertContains("Email address cannot be empty.", error.getMessage());
		assertContains("Full name cannot be empty.", error.getMessage());
	}

	@Test
	public void testCreateOnlineAccount() {
		CreateOnlineAccountRequestDto newOnlineAccount = new CreateOnlineAccountRequestDto(
				"  " + OFFLINE_MEMBER_USERNAME + "  ", "  " + OFFLINE_MEMBER_EMAIL_ADDRESS + "  ",
				"  " + OFFLINE_MEMBER_PASSWD + "  ");

		OnlineAccount createdAccount = memberService.createOnlineAccount(OFFLINE_MEMBER_ID, newOnlineAccount);

		// Check properties of returned online account
		assertNotNull(createdAccount);
		assertEquals(OFFLINE_MEMBER_USERNAME, createdAccount.getUsername());
		assertEquals(OFFLINE_MEMBER_EMAIL_ADDRESS, createdAccount.getEmailAddress());
		// TODO Update this if/when password hashing is implemented
		assertEquals(OFFLINE_MEMBER_PASSWD, createdAccount.getPasswordHash());

		// Check that online account was saved to database
		verify(memberDao, times(1)).save(argThat((Member m) -> m.getOnlineAccount() != null
				&& OFFLINE_MEMBER_USERNAME.equals(m.getOnlineAccount().getUsername())));
	}
}
