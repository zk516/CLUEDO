package cluedo.rooms;

import cluedo.game.Position;

public class KnockDoorPosition {
	private Position p;

	public KnockDoorPosition(Position p){
		this.p = p;
	}

	public Position getP() {
		return p;
	}

	public void setP(Position p) {
		this.p = p;
	}
}
