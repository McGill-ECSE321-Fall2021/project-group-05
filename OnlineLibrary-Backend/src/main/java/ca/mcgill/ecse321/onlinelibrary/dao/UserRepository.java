package ca.mcgill.ecse321.onlinelibrary.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.onlinelibrary.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	public User findUserById(int id);
}
