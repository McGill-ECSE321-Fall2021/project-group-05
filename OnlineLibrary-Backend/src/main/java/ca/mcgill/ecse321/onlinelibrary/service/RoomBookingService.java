package ca.mcgill.ecse321.onlinelibrary.service;

import ca.mcgill.ecse321.onlinelibrary.dao.RoomBookingRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.RoomRepository;
import ca.mcgill.ecse321.onlinelibrary.model.Member;
import ca.mcgill.ecse321.onlinelibrary.model.Room;
import ca.mcgill.ecse321.onlinelibrary.model.RoomBooking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@Service
public class RoomBookingService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomBookingRepository roomBookingRepository;

    @Transactional
    public Room createRoom(int capacity, String name) {
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }

        Room room = new Room(capacity, name.trim());
        roomRepository.save(room);
        return room;
    }

    @Transactional
    public Room getRoomById(int id) {
        Room room = roomRepository.findRoomById(id);

        if (room == null) {
            throw new NoSuchElementException("No room with id " + id + " exists.");
        }

        return room;
    }

    @Transactional
    public void deleteRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null.");
        }

        roomRepository.delete(room);
    }

    @Transactional
    public RoomBooking createRoomBooking(LocalDate date, LocalTime startTime, LocalTime endTime, Member member, Room room) {
        ArrayList<String> errorMessages = new ArrayList<>();
        if (date == null) {
            errorMessages.add("Date cannot be empty.");
        }
        if (startTime == null || endTime == null) {
            errorMessages.add("Start and end times cannot be empty.");
        }
        if (member == null) {
            errorMessages.add("Member cannot be empty.");
        }
        if (room == null) {
            errorMessages.add("Room cannot be empty.");
        }
        if (errorMessages.size() > 0) {
            throw new IllegalArgumentException(String.join(" ", errorMessages));
        }
        RoomBooking roomBooking = new RoomBooking(Date.valueOf(date), Time.valueOf(startTime), Time.valueOf(endTime), member, room);
        roomBookingRepository.save(roomBooking);
        return roomBooking;
    }

    @Transactional
    public RoomBooking getRoomBookingById(int id) {
        RoomBooking roomBooking = roomBookingRepository.findRoomBookingById(id);

        if (roomBooking == null) {
            throw new NoSuchElementException("No room booking with id " + id + " exists.");
        }

        return roomBooking;
    }
    
    @Transactional
	public ArrayList<RoomBooking> getRoomBooking(LocalDate startDate, LocalDate endDate) {
		if(startDate == null || endDate == null) 
			throw new IllegalArgumentException("Two date parameters are required.");
		
		if(startDate.compareTo(endDate) > 0) 
			throw new IllegalArgumentException("The start date can't be after the end date.");
		
		ArrayList<RoomBooking> roomBookings = 
				roomBookingRepository.findRoomBookingtByDateBetween(Date.valueOf(startDate), Date.valueOf(endDate));
		
		return roomBookings;
	}

    @Transactional
    public void deleteRoomBooking(RoomBooking roomBooking) {
        if (roomBooking == null) {
            throw new IllegalArgumentException("Room booking cannot be null.");
        }
        roomBookingRepository.delete(roomBooking);
    }

    @Transactional
    public Iterable<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Transactional
    public Iterable<RoomBooking> getAllRoomBookings() {
        return roomBookingRepository.findAll();
    }
}
