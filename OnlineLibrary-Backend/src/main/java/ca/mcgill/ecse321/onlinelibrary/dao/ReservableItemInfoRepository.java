package ca.mcgill.ecse321.onlinelibrary.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.onlinelibrary.model.ReservableItemInfo;

public interface ReservableItemInfoRepository extends CrudRepository<ReservableItemInfo, Integer> {
    ReservableItemInfo findReservableItemInfoById(int id);

    public void deleteAll();

}
