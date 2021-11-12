package ca.mcgill.ecse321.onlinelibrary.dao;

import ca.mcgill.ecse321.onlinelibrary.model.Member;
import ca.mcgill.ecse321.onlinelibrary.model.ReservableItemInfo;
import ca.mcgill.ecse321.onlinelibrary.model.Reservation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, Integer> {
    /**
     * Finds a copy of a reservable library item using its unique ID.
     * @param id the id of the reservable library item copy
     * @return the reservable library item copy instance
     */
    Reservation findReservationByReservationId(Integer id);
    List<Reservation> findReservationByMember(Member member);
    List<Reservation> findReservationByReservedItemOrderByDateAsc(ReservableItemInfo reservableItemInfo);
}