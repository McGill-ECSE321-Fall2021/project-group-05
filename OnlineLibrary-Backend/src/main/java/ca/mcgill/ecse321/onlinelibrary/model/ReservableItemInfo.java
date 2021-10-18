package ca.mcgill.ecse321.onlinelibrary.model;

import java.util.*;
import javax.persistence.*;

@Entity
public abstract class ReservableItemInfo extends LibraryItemInfo {
	// Seb : is this actually useful? Louis needs it?
	@ManyToMany
	@OrderColumn
	private List<Member> waitlist;

	/**
	 * Returns the member according to the order of priority, just as a queue.
	 * For example, if index=0, it will return the next person who reserved the item.
	 * @param index The order in the queue
	 * @return the member in "index" positionin the queue
	 */
	public Member getMember(int index) {
		Member aMember= waitlist.get(index);
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
	public void addMember(Member aMember){
		if(waitlist == null)
			waitlist = new ArrayList<Member>();
		if (waitlist.contains(aMember))
			return;
		waitlist.add(aMember);

		aMember.setReservedList(this);
	}

	// TODO removeMember()?

	public List<Member> getMembers() {
		List<Member> newMembers = Collections.unmodifiableList(waitlist);
		return newMembers;
	}

	/**
	 * returns how many people are in the queue of reservation for the book
	 */
	public int numberOfMembers() {
		int number = waitlist.size();
		return number;
	}

	public boolean hasMembers() {
		boolean has = (waitlist != null) && (waitlist.size() > 0);
		return has;
	}

	/**
	 * Returns the position in the queue of the input member
	 * returns -1 if the member is not in the queue
	 * @param aMember the member that we want to know his position in the queue
	 * @return the position of the member
	 */
	public int indexOfMember(Member aMember) {
		if (waitlist == null)
			return -1;
		int index = waitlist.indexOf(aMember);
		return index;
	}

	public static int minimumNumberOfMembers() {
		return 0;
	}

}
