package cluedo.rooms;

import java.util.ArrayList;

public class BallRoom extends Room {
	private ArrayList<Door> doors;

	// 4 doors
	public BallRoom(ArrayList<Door> doors) {
		this.doors = doors;
	}

	public ArrayList<Door> getDoors() {
		return doors;
	}

	public void setDoors(ArrayList<Door> doors) {
		this.doors = doors;
	}

}
