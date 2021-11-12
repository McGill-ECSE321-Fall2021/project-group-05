package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
public class RoomBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Date date;
    private Time startTime;
    private Time endTime;
    
    // Associations
	@ManyToOne(optional = false)
    private Member member;	
	@ManyToOne(optional = false)
    private Room room;
	
	// Constructors
	public RoomBooking() {}
	
	public RoomBooking(Date date, Time startTime, Time endTime, Member member, Room room) {
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;

		if (member == null)
			throw new IllegalArgumentException("A member is required for every loan");
		this.member = member;
		
		if (room == null)
			throw new IllegalArgumentException("A reservable item is required for every loan.");
		this.room = room;
	}

    // Getters
	/**
	 * Returns the unique ID of the RoomBooking
	 * @return the id of the RoomBooking
	 */
    public Integer getId() {
        return this.id;
    }
	/**
	 * Returns date of the RoomBooking
	 * @return the date of the RoomBooking
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * Returns start time of the RoomBooking
	 * @return the start time of the RoomBooking
	 */
	public Time getStartTime() {
		return startTime;
	}
	/**
	 * Returns start time of the RoomBooking
	 * @return the start time of the RoomBooking
	 */
	public Time getEndTime() {
		return endTime;
	}
	/**
	 * Returns the member that booked the room
	 * @return the library member that holds the RoomBooking
	 */
	public Member getMember() {
		return member;
	}
	/**
	 * Returns the room that is booked
	 * @return the booked room
	 */
	public Room getRoom() {
		return room;
	}
	
	// Setters
	/**
    * Sets the start time of the RoomBooking
    * @param the start time of the RoomBooking
    */
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	/**
    * Sets the end time of the RoomBooking
    * @param the end time of the RoomBooking
    */
	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}
	/**
    * Sets the date of the RoomBooking
    * @param the date of the RoomBooking
    */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
    * Sets the booked room to the RoomBooking
    * @param the room to be booked
    */
	public void setRoom(Room room) {
		this.room = room;
	}
	/**
    * Sets the member that owns the RoomBooking
    * @param the member that books the RoomBooking
    */
	public void setMember(Member member) {
		this.member = member;
	}

	public void setId(int id) {
		this.id = id;
	}
}
