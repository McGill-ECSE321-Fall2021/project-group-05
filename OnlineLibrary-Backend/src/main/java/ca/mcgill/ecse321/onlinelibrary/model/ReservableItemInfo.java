package ca.mcgill.ecse321.onlinelibrary.model;

import java.util.*;
import javax.persistence.*;

@Entity
public abstract class ReservableItemInfo extends LibraryItemInfo {
	// Seb : is this actually useful? Louis needs it?
	@ManyToMany
	@OrderColumn
	private List<Member> members;

	/**
	 * Returns the member according to the order of priority, just as a queue.
	 * For example, if index=0, it will return the next person who reserved the item.
	 * @param index The order in the queue
	 * @return the member in "index" positionin the queue
	 */
	public Member getMember(int index) {
		Member aMember= members.get(index);
		return aMember;
	}

	/**
	 * Adds a member in the queue to the reservable item
	 * so that they can take the book when it becomes available
	 * Also instantiates the members list if it's null
	 * ie if there is not queue of people that reserved
	 * it will create a queue
	 * @param aMember The member that wants to reserve the book
	 */
	public void setMember(Member aMember){
		if(members == null) 
			members = new ArrayList<Member>();
		if (members.contains(aMember))
			return;
		members.add(aMember);

		aMember.setReservedList(this);
	}

	public List<Member> getMembers() {
		List<Member> newMembers = Collections.unmodifiableList(members);
		return newMembers;
	}

	/**
	 * returns how many people are in the queue of reservation for the book
	 */
	public int numberOfMembers() {
		int number = members.size();
		return number;
	}

	public boolean hasMembers() {
		boolean has = (members != null) && (members.size() > 0);
		return has;
	}

	/**
	 * Returns the position in the queue of the input member
	 * returns -1 if the member is not in the queue
	 * @param aMember the member that we want to know his position in the queue
	 * @return the position of the member
	 */
	public int indexOfMember(Member aMember) {
		if (members == null)
			return -1;
		int index = members.indexOf(aMember);
		return index;
	}

	public static int minimumNumberOfMembers() {
		return 0;
	}

}
