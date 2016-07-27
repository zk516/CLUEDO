package cluedo.game;

/**
 * this class should return random numbers from 1-6 as a real-life dice
 * 
 * @author rongjiwang
 *
 */
public class Dice {

	public int throwDice() {
		return (int) (Math.random() * 7);
	}
}
