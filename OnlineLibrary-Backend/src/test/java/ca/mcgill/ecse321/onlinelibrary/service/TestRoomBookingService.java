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
import java.util.Arrays;
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
    private static final Iterable<Room> ALL_ROOMS = Arrays.asList(
            new Room(10, "Room 1"),
            new Room(20, "Room 2")
    );
    private static final Iterable<RoomBooking> ALL_ROOM_BOOKINGS = Arrays.asList(
            new RoomBooking(new Date(0), new Time(0), new Time(10), new Member("123 Main Street", "John Doe 1"), new Room(10, "Room 1")),
            new RoomBooking(new Date(0), new Time(0), new Time(10), new Member("123 Main Street", "John Doe 2"), new Room(20, "Room 2"))
    );

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
        lenient().when(roomDao.findAll()).thenReturn(ALL_ROOMS);
        lenient().when(roomBookingDao.findAll()).thenReturn(ALL_ROOM_BOOKINGS);
    }

    @Test
    public void testCreateRoomSuccessful() {
        int capacity = 10;
        String name = "Room" + ROOM_ID;
        Room room = null;

        room = roomBookingService.createRoom(capacity, name);

        assertNotNull(room);
        assertEquals(capacity, room.getCapacity());
        assertEquals(name, room.getName());
    }

    @Test
    public void testCreateRoomEmptyName() {
        int capacity = 10;
        String name = "";

        Exception e = assertThrows(IllegalArgumentException.class, () -> roomBookingService.createRoom(capacity, name));

        assert(e.getMessage().contains("Name cannot be empty"));
    }

    @Test
    public void testCreateRoomBookingSuccessful() {
        Date date = Date.valueOf("2021-12-25");
        Time startTime = Time.valueOf("12:00:00");
        Time endTime = Time.valueOf("23:59:59");
        Member member = new Member("123 Main Street", "John Doe");
        Room room = new Room(10, "Room" + ROOM_ID);
        RoomBooking roomBooking = null;

        roomBooking = roomBookingService.createRoomBooking(date,startTime, endTime, member, room);

        assertNotNull(roomBooking);
        assertEquals(date, roomBooking.getDate());
        assertEquals(startTime, roomBooking.getStartTime());
        assertEquals(endTime, roomBooking.getEndTime());
        assertEquals(member, roomBooking.getMember());
        assertEquals(room, roomBooking.getRoom());
    }

    @Test
    public void testCreateRoomBookingNullDate() {
        Date date = null;
        Time startTime = Time.valueOf("12:00:00");
        Time endTime = Time.valueOf("23:59:59");
        Member member = new Member("123 Main Street", "John Doe");
        Room room = new Room(10, "Room" + ROOM_ID);

        Exception e = assertThrows(IllegalArgumentException.class, () -> roomBookingService.createRoomBooking(date, startTime, endTime, member, room));
        assert(e.getMessage().contains("Date cannot be empty."));
    }

    @Test
    public void testCreateRoomBookingNullStartTime() {
        Date date = Date.valueOf("2021-12-25");
        Time startTime = null;
        Time endTime = Time.valueOf("23:59:59");
        Member member = new Member("123 Main Street", "John Doe");
        Room room = new Room(10, "Room" + ROOM_ID);

        Exception e = assertThrows(IllegalArgumentException.class, () -> roomBookingService.createRoomBooking(date, startTime, endTime, member, room));
        assert(e.getMessage().contains("Start and end times cannot be empty."));
    }

    @Test
    public void testCreateRoomBookingNullEndTime() {
        Date date = Date.valueOf("2021-12-25");
        Time startTime = Time.valueOf("12:00:00");
        Time endTime = null;
        Member member = new Member("123 Main Street", "John Doe");
        Room room = new Room(10, "Room" + ROOM_ID);

        Exception e = assertThrows(IllegalArgumentException.class, () -> roomBookingService.createRoomBooking(date, startTime, endTime, member, room));
        assert(e.getMessage().contains("Start and end times cannot be empty."));
    }

    @Test
    public void testCreateRoomBookingNullMember() {
        Date date = Date.valueOf("2021-12-25");
        Time startTime = Time.valueOf("12:00:00");
        Time endTime = Time.valueOf("23:59:59");
        Member member = null;
        Room room = new Room(10, "Room" + ROOM_ID);

        Exception e = assertThrows(IllegalArgumentException.class, () -> roomBookingService.createRoomBooking(date, startTime, endTime, member, room));
        assert(e.getMessage().contains("Member cannot be empty."));
    }

    @Test
    public void testCreateRoomBookingNullRoom() {
        Date date = Date.valueOf("2021-12-25");
        Time startTime = Time.valueOf("12:00:00");
        Time endTime = Time.valueOf("23:59:59");
        Member member = new Member("123 Main Street", "John Doe");
        Room room = null;

        Exception e = assertThrows(IllegalArgumentException.class, () -> roomBookingService.createRoomBooking(date, startTime, endTime, member, room));
        assert(e.getMessage().contains("Room cannot be empty."));
    }

    @Test
    public void testCreateRoomBookingAllNull() {
        Date date = null;
        Time startTime = null;
        Time endTime = null;
        Member member = null;
        Room room = null;

        Exception e = assertThrows(IllegalArgumentException.class, () -> roomBookingService.createRoomBooking(date, startTime, endTime, member, room));
        assert(e.getMessage().contains("Date cannot be empty."));
        assert(e.getMessage().contains("Start and end times cannot be empty."));
        assert(e.getMessage().contains("Member cannot be empty."));
        assert(e.getMessage().contains("Room cannot be empty."));
    }

    @Test
    public void testGetRoomByIdSuccessful() {
        Room room = roomBookingService.getRoomById(ROOM_ID);
        assertNotNull(room);
    }

    @Test
    public void testGetRoomByIdNonexistent() {
        Exception e = assertThrows(NoSuchElementException.class,
                () -> roomBookingService.getRoomById(INVALID_ROOM_ID));
        assertEquals("No room with id " + INVALID_ROOM_ID + " exists.", e.getMessage());
    }

    @Test
    public void testGetRoomBookingByIdSuccessful() {
        RoomBooking roomBooking = roomBookingService.getRoomBookingById(BOOKING_ID);
        assertNotNull(roomBooking);
    }

    @Test
    public void testGetRoomBookingByIdNonexistent() {
        Exception e = assertThrows(NoSuchElementException.class,
                () -> roomBookingService.getRoomBookingById(INVALID_BOOKING_ID));
        assertEquals("No room booking with id " + INVALID_BOOKING_ID + " exists.", e.getMessage());
    }

    @Test
    public void testDeleteRoomSuccessful() {
        roomBookingService.deleteRoom(roomBookingService.getRoomById(ROOM_ID));

        verify(roomDao, times(1)).delete(argThat((Room r) -> ROOM_ID == r.getId()));
        verify(roomDao, times(0)).delete(argThat((Room r) -> ROOM_ID != r.getId()));
    }

    @Test
    public void testDeleteRoomNull() {
        Exception error = assertThrows(IllegalArgumentException.class,
                () -> roomBookingService.deleteRoom(null));
        assertEquals("Room cannot be null.", error.getMessage());
        verify(roomDao, times(0)).delete(any(Room.class));
    }

    @Test
    public void testDeleteRoomBookingSuccessful() {
        roomBookingService.deleteRoomBooking(roomBookingService.getRoomBookingById(BOOKING_ID));

        verify(roomBookingDao, times(1)).delete(argThat((RoomBooking rb) -> BOOKING_ID == rb.getId()));
        verify(roomBookingDao, times(0)).delete(argThat((RoomBooking rb) -> BOOKING_ID != rb.getId()));
    }

    @Test
    public void testDeleteRoomBookingNull() {
        Exception error = assertThrows(IllegalArgumentException.class,
                () -> roomBookingService.deleteRoomBooking(null));
        assertEquals("Room booking cannot be null.", error.getMessage());
        verify(roomBookingDao, times(0)).delete(any(RoomBooking.class));
    }

    @Test
    public void getAllRoomsSuccessful() {
        Iterable<Room> rooms = roomBookingService.getAllRooms();
        assertEquals(ALL_ROOMS, rooms);
    }

    @Test
    public void testGetAllRoomBookingsSuccessful() {
        Iterable<RoomBooking> roomBookings = roomBookingService.getAllRoomBookings();
        assertEquals(ALL_ROOM_BOOKINGS, roomBookings);
    }
}
