package cluedo.rooms;

import cluedo.game.Position;

public class Stair {
	private Position p;
	private char c;

	// stair path 1<->4 and 2<->3
	public Stair(Position p, char c) {
		this.p = p;
		this.c = c;
	}

	public Position getP() {
		return p;
	}

	public void setP(Position p) {
		this.p = p;
	}

	public char getC() {
		return c;
	}

	public void setC(char c) {
		this.c = c;
	}

	@Override
	public String toString() {
		return "Stair [p=" + p + ", c=" + c + "]";
	}
	
}
