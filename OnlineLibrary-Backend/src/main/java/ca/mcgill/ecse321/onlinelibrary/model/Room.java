package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.*;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private int capacity;
    private String name;
    
    // Constructors
    public Room() {}
    
    public Room(int capacity, String name) {
    	this.capacity = capacity;
    	this.name = name;
    }

    // Getters 
	/**
	 * Returns the unique ID of the room
	 * @return the id of the room
	 */
    public Integer getId() {
        return this.id;
    }
	/**
	 * Returns the number of people that can fit in the room
	 * @return the capacity of the room
	 */
    public int getCapacity() {
    	return this.capacity;
    }
	/**
	 * Returns the name of the room
	 * @return the name of the room
	 */
    public String getName() {
    	return this.name;
    }
    
    // Setters
	/**
    * Sets the capacity of the room
    * @param capacity of the room
    */
    public void setCapacity(int capacity) {
    	this.capacity = capacity;
    }
	/**
    * Sets the name of the room
    * @param name of the room
    */
    public void setName(String name) {
    	this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }
}
