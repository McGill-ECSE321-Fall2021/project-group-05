package ca.mcgill.ecse321.onlinelibrary.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.onlinelibrary.model.ReservableItem;

public interface ReservableItemRepository extends CrudRepository<ReservableItem, Integer> {
	ReservableItem findReservableItemById(Integer id);
}
