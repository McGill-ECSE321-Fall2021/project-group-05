package ca.mcgill.ecse321.onlinelibrary.dao;

import ca.mcgill.ecse321.onlinelibrary.model.OnlineAccount;
import org.springframework.data.repository.CrudRepository;

public interface OnlineAccountRepository extends CrudRepository<OnlineAccount, Integer> {

	public OnlineAccount findOnlineAccountById(int id);

	public boolean existsOnlineAccountByUsername(String username);
}
