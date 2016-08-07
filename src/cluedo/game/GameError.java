package cluedo.game;

/**
 * this class should print error message on the screen when touch exceptions
 * 
 * @author rongjiwang
 *
 */
@SuppressWarnings("serial")
public class GameError extends RuntimeException {
	public GameError(String message) {
		super(message);
	}
}
