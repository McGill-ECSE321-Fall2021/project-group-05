package ca.mcgill.ecse321.onlinelibrary.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.onlinelibrary.model.OnlineAccount;

public interface OnlineAccountRepository extends CrudRepository<OnlineAccount, Integer> {

	public OnlineAccount findOnlineAccountById(int id);

	public boolean existsOnlineAccountByUsername(String username);
}
