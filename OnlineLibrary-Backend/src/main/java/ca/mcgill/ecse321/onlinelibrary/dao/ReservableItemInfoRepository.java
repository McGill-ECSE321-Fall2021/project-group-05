package ca.mcgill.ecse321.onlinelibrary.dao;

import ca.mcgill.ecse321.onlinelibrary.model.ReservableItemInfo;
import org.springframework.data.repository.CrudRepository;

public interface ReservableItemInfoRepository extends CrudRepository<ReservableItemInfo, Integer> {
    ReservableItemInfo findReservableItemInfoById(int id);

    public void deleteAll();

}
