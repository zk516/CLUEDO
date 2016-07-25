package cluedo.rooms;

public class Study {
	private Stair s;
	private Door d;

	//mark as s on map
	public Study(Stair s, Door d){
		this.s = s;
		this.d = d;
	}

	public Stair getS() {
		return s;
	}

	public void setS(Stair s) {
		this.s = s;
	}

	public Door getD() {
		return d;
	}

	public void setD(Door d) {
		this.d = d;
	}
}