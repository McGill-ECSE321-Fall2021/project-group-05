package ca.mcgill.ecse321.onlinelibrary.dao;

import ca.mcgill.ecse321.onlinelibrary.model.Reservation;
import ca.mcgill.ecse321.onlinelibrary.model.Member;
import ca.mcgill.ecse321.onlinelibrary.model.ReservableItemInfo;

import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<Member, Integer> {
    /**
     * Finds a copy of a reservable library item using its unique ID.
     * @param id the id of the reservable library item copy
     * @return the reservable library item copy instance
     */
    Reservation findReservationById(Integer id);
    Reservation findReservationByMember(Member member);
    Reservation findReservationByReservableItemInfo(ReservableItemInfo reservableItemInfo);
}