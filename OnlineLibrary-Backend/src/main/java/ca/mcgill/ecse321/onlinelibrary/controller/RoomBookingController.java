package ca.mcgill.ecse321.onlinelibrary.controller;

import ca.mcgill.ecse321.onlinelibrary.dto.RoomBookingDto;
import ca.mcgill.ecse321.onlinelibrary.dto.RoomDto;
import ca.mcgill.ecse321.onlinelibrary.model.Member;
import ca.mcgill.ecse321.onlinelibrary.model.Room;
import ca.mcgill.ecse321.onlinelibrary.service.MemberService;
import ca.mcgill.ecse321.onlinelibrary.service.RoomBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
public class RoomBookingController {
    @Autowired
    private RoomBookingService roomBookingService;

    @Autowired
    private MemberService memberService;

    @PostMapping(value = {"/room/", "/room"})
    public RoomDto createRoom(@RequestParam int capacity, @RequestParam String name) {
        return RoomDto.fromRoom(roomBookingService.createRoom(capacity, name));
    }

    @GetMapping(value = {"/room/{id}", "/room/{id}/"})
    public RoomDto getRoomById(@PathVariable("id") int id) {
        return RoomDto.fromRoom(roomBookingService.getRoomById(id));
    }
    
    @DeleteMapping(value = {"/room/{id}", "/room/{id}/"})
    public void deleteRoomById(@PathVariable("id") int id) {
        roomBookingService.deleteRoom(roomBookingService.getRoomById(id));
    }

    @PostMapping(value = {"/roomBooking", "/roomBooking/"})
    public RoomBookingDto createRoomBooking(
    		@RequestParam  @DateTimeFormat(pattern="yyyy-MM-dd")  LocalDate date, 
    		@RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)  LocalTime startTime,
            @RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)  LocalTime endTime,
            @RequestParam int memberId, @RequestParam int roomId) {
        Member member = memberService.getMemberById(memberId);
        Room room = roomBookingService.getRoomById(roomId);
        return RoomBookingDto.fromRoomBooking(roomBookingService.createRoomBooking(date, startTime, endTime, member, room));
    }

    @GetMapping(value = {"/roomBooking/{id}", "/roomBooking/{id}/"})
    public RoomBookingDto getRoomBooking(@PathVariable("id") int id) {
        return RoomBookingDto.fromRoomBooking(roomBookingService.getRoomBookingById(id));
    }

    @DeleteMapping(value = {"/roomBooking/{id}", "/roomBooking/{id}/"})
    public void deleteRoomBooking(@PathVariable("id") int id) {
        roomBookingService.deleteRoomBooking(roomBookingService.getRoomBookingById(id));
    }

    @GetMapping(value = {"/room", "/room/"})
    public List<RoomDto> getAllRooms() {
        List<RoomDto> roomDtos = new ArrayList<>();
        roomBookingService.getAllRooms().forEach(room -> roomDtos.add(RoomDto.fromRoom(room)));
        return roomDtos;
    }

    @GetMapping(value = {"/roomBooking", "/roomBooking/"})
    public List<RoomBookingDto> getAllRoomBookings() {
        List<RoomBookingDto> roomBookingDtos = new ArrayList<>();
        roomBookingService.getAllRoomBookings().forEach(roomBooking -> roomBookingDtos.add(RoomBookingDto.fromRoomBooking(roomBooking)));
        return roomBookingDtos;
    }
    
    @GetMapping(value = { "/roomBooking/{startDate}/{endDate}", "/roomBooking/{startDate}/{endDate}/" })
	public List<RoomBookingDto> getRoomBookings(
			@PathVariable("startDate") @DateTimeFormat(pattern="yyyy-MM-dd")  LocalDate startDate, 
			@PathVariable("endDate") @DateTimeFormat(pattern="yyyy-MM-dd")  LocalDate endDate) throws IllegalArgumentException {
		return roomBookingService.getRoomBooking(startDate, endDate).stream()
				.map(RB -> RoomBookingDto.fromRoomBooking(RB)).collect(Collectors.toList());
	}

}
