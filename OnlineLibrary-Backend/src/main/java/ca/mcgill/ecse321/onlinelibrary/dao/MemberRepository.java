package ca.mcgill.ecse321.onlinelibrary.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.onlinelibrary.model.Member;

public interface MemberRepository extends CrudRepository<Member, Integer> {
	public Member findMemberById(int id);

}
