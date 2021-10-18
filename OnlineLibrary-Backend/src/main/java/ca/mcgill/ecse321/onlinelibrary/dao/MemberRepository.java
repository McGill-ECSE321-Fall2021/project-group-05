package ca.mcgill.ecse321.onlinelibrary.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.onlinelibrary.model.Member;

public interface MemberRepository extends CrudRepository<Member, Integer> {
	public Member findMemberById(int id);

	@Override
	@Modifying
	@Query(value = "delete from library_item_info_waitlist; delete from member_reserved_items;"
			+ "delete from member_loans; delete from member", nativeQuery = true)
	public void deleteAll();
}
