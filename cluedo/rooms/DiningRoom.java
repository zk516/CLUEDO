package cluedo.rooms;

import java.util.ArrayList;

public class DiningRoom {
	private ArrayList<Door> doors;

	// 2 doors
	public DiningRoom(ArrayList<Door> doors) {
		this.doors = doors;
	}

	public ArrayList<Door> getDoors() {
		return doors;
	}

	public void setDoors(ArrayList<Door> doors) {
		this.doors = doors;
	}
}
