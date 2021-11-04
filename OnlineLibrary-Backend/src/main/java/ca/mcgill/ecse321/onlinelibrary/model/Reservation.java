package ca.mcgill.ecse321.onlinelibrary.model;
import javax.persistence.*;
import java.util.*;
@Entity
public class Reservation {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer reservationId;
    
    @ManyToOne (cascade = CascadeType.ALL)
    private Member member;
    
    @ManyToOne (cascade = CascadeType.ALL)
    private ReservableItemInfo reservedItem;

    public Reservation(Member member, ReservableItemInfo reservableItem){
        this.member = member;
        this.reservedItem = reservableItem;
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
