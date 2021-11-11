package ca.mcgill.ecse321.onlinelibrary.dto;

import ca.mcgill.ecse321.onlinelibrary.model.RoomBooking;

import java.sql.Date;
import java.sql.Time;

public class RoomBookingDto {
    private int id;
    private Date date;
    private Time startTime;
    private Time endTime;
    private MemberDto member;
    private RoomDto room;

    public RoomBookingDto(int id, Date date, Time startTime, Time endTime, MemberDto member, RoomDto room) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.member = member;
        this.room = room;
    }

    public static RoomBookingDto fromRoomBooking(RoomBooking roomBooking) {
        if (roomBooking == null) {
            throw new IllegalArgumentException("There is no such room booking.");
        }
        return new RoomBookingDto(roomBooking.getId(), roomBooking.getDate(), roomBooking.getStartTime(), roomBooking.getEndTime(), MemberDto.fromMember(roomBooking.getMember()), RoomDto.fromRoom(roomBooking.getRoom()));
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public MemberDto getMember() {
        return member;
    }

    public RoomDto getRoom() {
        return room;
    }
}
