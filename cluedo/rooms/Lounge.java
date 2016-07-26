package cluedo.rooms;

public class Lounge implements Room{
	//mark as l on map
	private Stair s;
	private Door d;

	public Lounge(Stair s, Door d){
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

	@Override
	public String toString() {
		return "Lounge [s=" + s + ", d=" + d + "]";
	}
	
}
