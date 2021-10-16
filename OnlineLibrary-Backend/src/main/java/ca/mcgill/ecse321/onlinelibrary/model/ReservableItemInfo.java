package ca.mcgill.ecse321.onlinelibrary.model;
import java.util.*;
import javax.persistence.*;

@Entity
public abstract class ReservableItemInfo extends LibraryItemInfo{
	
	//Seb : is this actually useful? Louis needs it?
	//TestSeb
	//Test2
	private Set<User> users;

	public User getUser(int index) {
	User aUser = users.get(index);
	return aUser;
	}
	
	@ManyToMany
	public List<User> getUsers() {
	List<User> newUsers = Collections.unmodifiableList(users);
	return newUsers;
	}

	public int numberOfUsers() {
	int number = users.size();
	return number;
	}
	
	public boolean hasUsers() {
	boolean has = users.size() > 0;
	return has;
	}
	 
	public int indexOfUser(User aUser) {
	int index = users.indexOf(aUser);
	return index;
	}

	public static int minimumNumberOfUsers() {
	return 0;
	}

	public boolean addUser(User aUser) {
	boolean wasAdded = false;
	if (users.contains(aUser)) {
		return false; 
	}
	users.add(aUser);
	if (aUser.indexOfReservableItemInfo(this) != -1) {
		wasAdded = true;
	}
	else {
		wasAdded = aUser.addReservableItemInfo(this);
		if (!wasAdded) {
			users.remove(aUser);
		}
	}
	return wasAdded;
	}

	public boolean removeUser(User aUser) {
	boolean wasRemoved = false;
	if (!users.contains(aUser)) {
		return wasRemoved;
	}
	
	int oldIndex = users.indexOf(aUser);
	users.remove(oldIndex);
	if (aUser.indexOfReservableItemInfo(this) == -1) {
		wasRemoved = true;
	}
	else {
		wasRemoved = aUser.removeReservableItemInfo(this);
		if (!wasRemoved) {
			users.add(oldIndex,aUser);
		}
	}
	return wasRemoved;
	}

	public boolean addUserAt(User aUser, int index)	{ 
	boolean wasAdded = false;
	if(addUser(aUser)) {
		if(index < 0 ) {
			index = 0;
		}
		if(index > numberOfUsers()) {
			index = numberOfUsers() - 1;
		}
	users.remove(aUser);
	users.add(index, aUser);
	wasAdded = true;
	}
	return wasAdded;
	}

	public boolean addOrMoveUserAt(User aUser, int index) {
	boolean wasAdded = false;
	if(users.contains(aUser)) {
		if(index < 0 ) {
			index = 0;
		}
	if(index > numberOfUsers()) {
		index = numberOfUsers() - 1;
	}
	users.remove(aUser);
	users.add(index, aUser);
	wasAdded = true;
	}
	else {
	wasAdded = addUserAt(aUser, index);
	}
	return wasAdded;
	}
	
	public void delete() {
	ArrayList<User> copyOfUsers = new ArrayList<User>(users);
	users.clear();
	for(User aUser : copyOfUsers) {
		aUser.removeReservableItemInfo(this);
	}
	super.delete();
	}
}
