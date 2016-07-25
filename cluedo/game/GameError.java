package cluedo.game;

@SuppressWarnings("serial")
public class GameError extends RuntimeException{
	public GameError(String message){
		super(message);
	}
}
