package ca.mcgill.ecse321.onlinelibrary.dao;

import ca.mcgill.ecse321.onlinelibrary.model.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Integer> {
	/**
	 * Finds a room by using its unique ID.
	 * @param id the id of the room
	 * @return the room instance
	 */
	Room findRoomById(int id);
}