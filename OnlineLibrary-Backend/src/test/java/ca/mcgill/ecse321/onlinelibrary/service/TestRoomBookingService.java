package ca.mcgill.ecse321.onlinelibrary.service;

import ca.mcgill.ecse321.onlinelibrary.dao.RoomBookingRepository;
import ca.mcgill.ecse321.onlinelibrary.dao.RoomRepository;
import ca.mcgill.ecse321.onlinelibrary.model.Member;
import ca.mcgill.ecse321.onlinelibrary.model.Room;
import ca.mcgill.ecse321.onlinelibrary.model.RoomBooking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.sql.Date;
import java.sql.Time;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TestRoomBookingService {
    @Mock
    private RoomRepository roomDao;

    @Mock
    private RoomBookingRepository roomBookingDao;

    @InjectMocks
    private RoomBookingService roomBookingService;

    private static final int ROOM_ID = 42;
    private static final int INVALID_ROOM_ID = 999999;
    private static final int BOOKING_ID = 42;
    private static final int INVALID_BOOKING_ID = 999999;

    @BeforeEach
    public void setMockOutput() {
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(roomDao.save(any(Room.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(roomBookingDao.save(any(RoomBooking.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(roomDao.findRoomById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(ROOM_ID)) {
                Room room = new Room(10, "Room " + ROOM_ID);
                room.setId(ROOM_ID);
                return room;
            } else {
                return null;
            }
        });
        lenient().when(roomBookingDao.findRoomBookingById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(BOOKING_ID)) {
                RoomBooking roomBooking = new RoomBooking(Date.valueOf("2021-12-25"), Time.valueOf("12:00:00"), Time.valueOf("23:59:59"), new Member("123 Main Street", "John Doe"), new Room(10, "Room" + ROOM_ID));
                roomBooking.setId(BOOKING_ID);
                return roomBooking;
            } else {
                return null;
            }
        });
    }

    @Test
    public void testCreateRoomSuccessful() {
        int capacity = 10;
        String name = "Room" + ROOM_ID;
        Room room = null;

        try {
            room = roomBookingService.createRoom(capacity, name);
        } catch (IllegalArgumentException e) {
            fail();
        }

        assertNotNull(room);
        assertEquals(capacity, room.getCapacity());
        assertEquals(name, room.getName());
    }

    @Test
    public void testCreateRoomEmptyName() {
        int capacity = 10;
        String name = "";
        Room room = null;
        String errorMessage = "";

        try {
            room = roomBookingService.createRoom(capacity, name);
        } catch (IllegalArgumentException e) {
            errorMessage += e.getMessage();
        }

        assertNull(room);
        assertTrue(errorMessage.contains("Name cannot be empty"));
    }
}
