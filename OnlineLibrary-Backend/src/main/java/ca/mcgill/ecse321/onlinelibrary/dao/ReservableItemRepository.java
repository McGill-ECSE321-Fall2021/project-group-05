package ca.mcgill.ecse321.onlinelibrary.dao;

import ca.mcgill.ecse321.onlinelibrary.model.ReservableItem;
import org.springframework.data.repository.CrudRepository;

public interface ReservableItemRepository extends CrudRepository<ReservableItem, Integer> {
    ReservableItem findReservableItemById(Integer id);
}
