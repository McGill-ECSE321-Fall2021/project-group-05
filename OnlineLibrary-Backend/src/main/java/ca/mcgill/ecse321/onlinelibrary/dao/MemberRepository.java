package ca.mcgill.ecse321.onlinelibrary.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.onlinelibrary.model.Member;

public interface MemberRepository extends CrudRepository<Member, Integer> {
	public Member findMemberById(int id);

	@Query(value = "delete from member_reservable_item_info; delete from member_info", nativeQuery = true)
    public void deleteAll();
	
}
