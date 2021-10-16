package ca.mcgill.ecse321.onlinelibrary.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.onlinelibrary.model.OnlineAccount;
import ca.mcgill.ecse321.onlinelibrary.model.User;

public interface OnlineAccountRepository extends CrudRepository<OnlineAccount, Integer> {

	public OnlineAccount findOnlineAccountById(int id);

	public OnlineAccount findOnlineAccountByUser(User user);
}
