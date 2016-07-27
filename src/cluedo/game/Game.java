package cluedo.game;

public class Game {
	private Board b;
	private int playernumber;

	// 1.check how many players
	// 2.pick character
	// display the board
	// player turn
	// dice
	// options, walk, check, speak,
	// when dice num 0
	// next turn
	// if in a room, more options active
	public Game(Board b, int playernumber) {
		this.b = b;
		this.playernumber = playernumber;
	}

}
