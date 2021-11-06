package ca.mcgill.ecse321.onlinelibrary.dto;

import ca.mcgill.ecse321.onlinelibrary.model.Reservation;
import ca.mcgill.ecse321.onlinelibrary.model.Member;
import ca.mcgill.ecse321.onlinelibrary.model.ReservableItemInfo;
import java.sql.Date;

public class ReservationDto {
    private Integer reservationId;
    private Date date;
    private Member member;
    private ReservableItemInfo reservedItem;

    //reservedItem and member are optional???
    public ReservationDto(Integer reservationId, Member member, ReservableItemInfo reservedItem, Date date){
        this.reservationId = reservationId;
        this.member = member;
        this.reservedItem = reservedItem;
        this.date = date;
    }

    public static ReservationDto fromReservation(Reservation reservation){
        if (reservation == null){
            throw new IllegalArgumentException("There is no such reservation.");
        }
        return new ReservationDto(reservation.getId(), reservation.getMember(), reservation.getReservableItemInfo(), reservation.getDate());
    }

    public Integer getId(){
        return this.reservationId;
    }

    public Member getMember(){
        return this.member;
    }

    public Date getDate(){
        return this.date;
    }

    public ReservableItemInfo getReservableItemInfo(){
        return this.reservedItem;
    }
}
