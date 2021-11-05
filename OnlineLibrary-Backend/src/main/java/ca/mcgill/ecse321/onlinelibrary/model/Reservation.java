package ca.mcgill.ecse321.onlinelibrary.model;
import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.*;
@Entity
public class Reservation {
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer reservationId;

    private Date reservationDate;
    
    @OnDelete (action = OnDeleteAction.CASCADE)
    @ManyToOne (optional = false)
    private Member member;
    
    @OnDelete (action = OnDeleteAction.CASCADE)
    @ManyToOne (optional = false)
    private ReservableItemInfo reservedItem;

    public Reservation(Member member, ReservableItemInfo reservableItem, Date date){
        this.member = member;
        this.reservedItem = reservableItem;
        this.reservationDate = date;
    }

    public Integer getId(){
        return this.reservationId;
    }

    public Member getMember(){
        return this.member;
    }

    public void setMember(Member member){
        this.member = member;
    }
    
}
