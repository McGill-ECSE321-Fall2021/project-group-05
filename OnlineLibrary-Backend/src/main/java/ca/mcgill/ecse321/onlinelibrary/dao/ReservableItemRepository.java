package ca.mcgill.ecse321.onlinelibrary.dao;

import ca.mcgill.ecse321.onlinelibrary.model.ReservableItem;
import org.springframework.data.repository.CrudRepository;

public interface ReservableItemRepository extends CrudRepository<ReservableItem, Integer> {
    /**
     * Finds a copy of a reservable library item using its unique ID.
     * @param id the id of the reservable library item copy
     * @return the reservable library item copy instance
     */
    ReservableItem findReservableItemById(Integer id);
}
