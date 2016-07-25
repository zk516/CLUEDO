package cluedo.rooms;

import java.util.ArrayList;

public class BilliardRoom {
	private ArrayList<Door> doors;

	// 2 doors
	public BilliardRoom(ArrayList<Door> doors) {
		this.doors = doors;
	}

	public ArrayList<Door> getDoors() {
		return doors;
	}

	public void setDoors(ArrayList<Door> doors) {
		this.doors = doors;
	}
}
