package cluedo.game;

import java.util.ArrayList;

import cluedo.cards.Card;
import cluedo.cards.Character;

/**
 * this class should show all the abilities that every player can do
 * 
 * @author rongjiwang
 *
 */
public class Player {
	private Position p;
	private String name;
	private ArrayList<Card> knowningCards;
	private Character character;
	private char simbol;

	public Player(Position p, String name, Character character, ArrayList<Card> knowningCards) {
		this.p = p;
		this.name = name;
		this.character = character;
		this.knowningCards = knowningCards;
	}
	
	public void setSimbol(char c){
		simbol = c;
	}
	
	public char getSimbol(){
		return simbol;
	}
	
}
