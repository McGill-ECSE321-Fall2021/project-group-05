package ca.mcgill.ecse321.onlinelibrary.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.onlinelibrary.dao.MemberRepository;
import ca.mcgill.ecse321.onlinelibrary.model.Member;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@ExtendWith(MockitoExtension.class)
public class TestMemberService {

	@Mock
	private MemberRepository memberDao;

	@InjectMocks
	private MemberService memberService;

	private static final int MEMBER_ID = 42;
	private static final int INVALID_MEMBER_ID = 999999;

	@BeforeEach
	public void setMockOuput() {
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(memberDao.save(any(Member.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(memberDao.findMemberById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(MEMBER_ID)) {
				return new Member("123 Main Street", "John Doe");
			} else {
				return null;
			}
		});
	}

	@Test
	public void testGetMemberByIdSuccessful() {
		Member member = memberService.getMemberById(MEMBER_ID);
		assertNotNull(member);
	}

	@Test
	public void testGetMemberByIdInexistent() {
		Member member = memberService.getMemberById(INVALID_MEMBER_ID);
		assertNull(member);
	}

	@Test
	public void testActivateMemberAccount() {
		Member member = memberService.getMemberById(MEMBER_ID);
		assert (member.getStatus() == Member.MemberStatus.INACTIVE);
		member = memberService.activateAccount(member);
		assertNotNull(member);
		assert (member.getStatus() == Member.MemberStatus.GREEN);
	}

	@Test
	public void testUpdateMember(){

		String newAddress = "123 soleil";
		String newName = "Bob the Builder";
		Member actualMember = memberService.updateMember(this.MEMBER_ID, newAddress, newName);

		assertNotNull(actualMember);
		assertEquals(MEMBER_ID, actualMember.getId());
		assertEquals(newAddress, actualMember.getAddress());
		assertEquals(newName, actualMember.getFullName());
	}
}
