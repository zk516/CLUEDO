package cluedo.game;

import java.util.ArrayList;

import cluedo.cards.Card;

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

	public Player(Position p, String name, ArrayList<Card> knowningCards) {
		this.p = p;
		this.name = name;
		this.knowningCards = knowningCards;
	}
	
}
