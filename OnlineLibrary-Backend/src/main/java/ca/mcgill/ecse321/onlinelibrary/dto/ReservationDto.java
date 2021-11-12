package ca.mcgill.ecse321.onlinelibrary.dto;

import ca.mcgill.ecse321.onlinelibrary.model.Reservation;

import java.sql.Date;

public class ReservationDto {

	private Integer reservationId;
	private Date date;
	private MemberDto member;
	private ReservableItemInfoDto reservedItem;

	public ReservationDto(Integer reservationId, MemberDto member, ReservableItemInfoDto reservedItem, Date date) {
		this.reservationId = reservationId;
		this.member = member;
		this.reservedItem = reservedItem;
		this.date = date;
	}

	public static ReservationDto fromReservation(Reservation reservation) {
		if (reservation == null) {
			throw new IllegalArgumentException("There is no such reservation.");
		}
		return new ReservationDto(reservation.getId(), MemberDto.fromMember(reservation.getMember()),
				reservation.getReservableItemInfo().convertToDto(), reservation.getDate());
	}

	public Integer getId() {
		return this.reservationId;
	}

	public MemberDto getMember() {
		return this.member;
	}

	public Date getDate() {
		return this.date;
	}

	public ReservableItemInfoDto getReservableItemInfo() {
		return this.reservedItem;
	}
}
