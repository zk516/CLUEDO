package cluedo.rooms;

import cluedo.game.Position;

public class Door {
	private Position p;
	private KnockDoorPosition kp;

	public Door(Position p, KnockDoorPosition kp){
		this.p = p;
		this.kp = kp;
	}

	public Position getP() {
		return p;
	}

	public void setP(Position p) {
		this.p = p;
	}

	public KnockDoorPosition getKp() {
		return kp;
	}

	public void setKp(KnockDoorPosition kp) {
		this.kp = kp;
	}

	@Override
	public String toString() {
		return "Door [p=" + p + ", kp=" + kp + "]";
	}
	
}
