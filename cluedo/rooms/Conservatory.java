package cluedo.rooms;

public class Conservatory implements Room{
	private Stair s;
	private Door d;

	//mark as c on map
	public Conservatory(Stair s, Door d){
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
		return "Conservatory [s=" + s + ", d=" + d + "]";
	}
	
}
