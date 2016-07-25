package cluedo.rooms;

import java.util.ArrayList;

public class Library {
	private ArrayList<Door> doors;

	// 2doors
	public Library(ArrayList<Door> doors) {
		this.doors = doors;
	}

	public ArrayList<Door> getDoors() {
		return doors;
	}

	public void setDoors(ArrayList<Door> doors) {
		this.doors = doors;
	}
}
