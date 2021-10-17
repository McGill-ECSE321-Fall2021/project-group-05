package ca.mcgill.ecse321.onlinelibrary.model;

import java.util.*;
import javax.persistence.*;

@Entity

public abstract class ReservableItemInfo extends LibraryItemInfo {
	// // Seb : is this actually useful? Louis needs it?
	// @ManyToMany
	// @OrderColumn
	// private List<Member> members;

	// public Member getMember(int index) {
	// 	Member aMember= members.get(index);
	// 	return aMember;
	// }

	// public void setMember(Member aMember){
	// 	if(members == null) 
	// 		members = new ArrayList<Member>();
	// 	if (members.contains(aMember))
	// 		return;
	// 	members.add(aMember);
	// }

	// public List<Member> getMembers() {
	// 	List<Member> newMembers = Collections.unmodifiableList(members);
	// 	return newMembers;
	// }

	// public int numberOfMembers() {
	// 	int number = members.size();
	// 	return number;
	// }

	// public boolean hasMembers() {
	// 	boolean has = members.size() > 0;
	// 	return has;
	// }

	// public int indexOfMember(Member aMember) {
	// 	int index = members.indexOf(aMember);
	// 	return index;
	// }

	// public static int minimumNumberOfMembers() {
	// 	return 0;
	// }

}
