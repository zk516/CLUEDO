package cluedo.rooms;

import java.util.ArrayList;

public class BallRoom implements Room {
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

	@Override
	public String toString() {
		return "b";
	}

}
