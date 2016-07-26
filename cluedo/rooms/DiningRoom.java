package cluedo.rooms;

import java.util.ArrayList;

public class DiningRoom implements Room{
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

	@Override
	public String toString() {
		return "DiningRoom [doors=" + doors + "]";
	}
	
}
