package cluedo.game;

import java.io.IOException;

public class TextMain {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// how many players
		// player names
		// structure board from txt file
		if (args.length != 1) {
			System.out.println("Can not run game without board file input");
			System.exit(0);
		}
		// System.out.println(args[0].toString());
		Board b = new Board(args[0]);
		// Dice d = new Dice();
		System.out.println(new Dice().throwDice());
		// setup room.stair
		// Game game = new Game(players, board);
	}

}
