package ca.mcgill.ecse321.onlinelibrary.model;
import javax.persistence.*;
import java.util.*;
@Entity
public class Reservation {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer reservationId;
    
    @ManyToOne
    private Member member;
    
    @ManyToMany
    private List<ReservableItemInfo> reservedItems;

    public Reservation(Member member, ReservableItemInfo reservableItem){
        this.member = member;
        this.reservedItems = new ArrayList<ReservableItemInfo>();
        reservedItems.add(reservableItem);
    }

    
}
