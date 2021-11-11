package ca.mcgill.ecse321.onlinelibrary.dto;

import ca.mcgill.ecse321.onlinelibrary.model.Room;

public class RoomDto {
    private int id;
    private int capacity;
    private String name;

    public RoomDto(int id, int capacity, String name) {
        this.id = id;
        this.capacity = capacity;
        this.name = name;
    }

    public static RoomDto fromRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("There is no such room.");
        }
        return new RoomDto(room.getId(), room.getCapacity(), room.getName());
    }

    public int getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getName() {
        return name;
    }
}
