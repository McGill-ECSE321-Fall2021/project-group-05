package ca.mcgill.ecse321.onlinelibrary.dao;

import ca.mcgill.ecse321.onlinelibrary.model.RoomBooking;
import org.springframework.data.repository.CrudRepository;

public interface RoomBookingRepository extends CrudRepository<RoomBooking, Integer> {
	/**
	 * Finds a room booking instance by using its unique ID.
	 * @param id the id of the room booking
	 * @return the room booking instance
	 */
	RoomBooking findRoomBookingById(int id);
}