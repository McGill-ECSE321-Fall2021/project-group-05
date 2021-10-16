package ca.mcgill.ecse321.onlinelibrary.model;

import java.util.*;
import javax.persistence.*;

@Entity
public abstract class ReservableItemInfo extends LibraryItemInfo {

	// Seb : is this actually useful? Louis needs it?
	@ManyToMany
	@OrderColumn
	private List<User> users;

	public User getUser(int index) {
		User aUser = users.get(index);
		return aUser;
	}

	public void setUser(User aUser){
		if(users == null) 
			users = new ArrayList<User>();
		if (users.contains(aUser))
			return;
		users.add(aUser);
	}

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

}
