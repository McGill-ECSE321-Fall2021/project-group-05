package ca.mcgill.ecse321.onlinelibrary.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.onlinelibrary.model.Room;

public interface RoomRepository extends CrudRepository<Room, Integer> {
	/**
	 * Finds a room by using its unique ID.
	 * @param id the id of the room
	 * @return the room instance
	 */
	Room findRoomById(int id);
}