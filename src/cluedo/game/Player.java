package cluedo.game;

import java.util.ArrayList;

import cluedo.cards.Card;
import cluedo.rooms.Door;

/**
 * this class should show all the abilities that every player can do
 *
 * @author rongjiwang
 *
 */
public class Player {
	private Position p;
	private String name;
	private ArrayList<Card> knowingCards;
	private char symbol;
	private boolean arePlayerInARoom;
	private char roomSymbol;
	private boolean suggestion;

	public Player(Position p, String name, char symbol, ArrayList<Card> knowingCards) {
		this.p = p;
		this.name = name;
		this.knowingCards = knowingCards;
		this.symbol = symbol;
	}

	public boolean isArePlayerInARoom() {
		return arePlayerInARoom;
	}

	public void setArePlayerInARoom(boolean arePlayerInARoom) {
		this.arePlayerInARoom = arePlayerInARoom;
	}

	public char getRoomSymbol() {
		if (isArePlayerInARoom()) {
			return roomSymbol;
		}
		return '0';
	}

	public void setRoomSymbol(char roomSymbol) {
		this.roomSymbol = roomSymbol;
	}

	/**
	 * player move
	 *
	 * @param where
	 */
	public void Move(String where, Board board) {
		if (where.equals("north")) {
			// erase symbol
			board.outputBoard[this.p.getX()][this.p.getY()] = ' ';
			this.p = new Position(p.getX() - 1, p.getY());
			// add the symbol to new position
			board.outputBoard[this.p.getX()][this.p.getY()] = this.symbol;

		} else if (where.equals("south")) {
			board.outputBoard[this.p.getX()][this.p.getY()] = ' ';
			this.p = new Position(p.getX() + 1, p.getY());
			board.outputBoard[this.p.getX()][this.p.getY()] = this.symbol;
		}

		else if (where.equals("west")) {
			board.outputBoard[this.p.getX()][this.p.getY()] = ' ';
			this.p = new Position(p.getX(), p.getY() - 1);
			board.outputBoard[this.p.getX()][this.p.getY()] = this.symbol;
		} else if (where.equals("east")) {
			board.outputBoard[this.p.getX()][this.p.getY()] = ' ';
			this.p = new Position(p.getX(), p.getY() + 1);
			board.outputBoard[this.p.getX()][this.p.getY()] = this.symbol;
		}

	}

	public Position getP() {
		return p;
	}

	public void setP(Position p) {
		this.p = p;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Card> getKnowingCards() {
		return knowingCards;
	}

	public void setKnowingCards(ArrayList<Card> knowingCards) {
		this.knowingCards = knowingCards;
	}

	public char getSymbol() {
		return symbol;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}

	@Override
	public String toString() {
		return "name";
	}

	/**
	 * move the player position to a random position inside the room
	 *
	 * @param board
	 * @param c
	 */
	public void enterRoom(Board board, char c) {
		switch (c) {
		case 'b':
			movePostion(board, findPositionInTheRoom(board, c));
			this.setArePlayerInARoom(true);
			this.setRoomSymbol(c);
			;
			// get out to the knocking room position
			break;
		case 'i':
			movePostion(board, findPositionInTheRoom(board, c));
			this.setArePlayerInARoom(true);
			this.setRoomSymbol(c);
			;
			// get out to the knocking room position
			break;
		case 'c':
			movePostion(board, findPositionInTheRoom(board, c));
			this.setArePlayerInARoom(true);
			this.setRoomSymbol(c);
			;
			// get out to the knocking room position
			break;
		case 'd':
			movePostion(board, findPositionInTheRoom(board, c));
			this.setArePlayerInARoom(true);
			this.setRoomSymbol(c);
			;
			// get out to the knocking room position
			break;
		case 'h':
			movePostion(board, findPositionInTheRoom(board, c));
			this.setArePlayerInARoom(true);
			this.setRoomSymbol(c);
			;
			// get out to the knocking room position
			break;
		case 'k':
			movePostion(board, findPositionInTheRoom(board, c));
			this.setArePlayerInARoom(true);
			this.setRoomSymbol(c);
			;
			// get out to the knocking room position
			break;
		case 'l':
			movePostion(board, findPositionInTheRoom(board, c));
			this.setArePlayerInARoom(true);
			this.setRoomSymbol(c);
			;
			// get out to the knocking room position
			break;
		case 'o':
			movePostion(board, findPositionInTheRoom(board, c));
			this.setArePlayerInARoom(true);
			this.setRoomSymbol(c);
			;
			// get out to the knocking room position
			break;
		case 's':
			movePostion(board, findPositionInTheRoom(board, c));
			this.setArePlayerInARoom(true);
			this.setRoomSymbol(c);
			;
			// get out to the knocking room position
			break;
		default:
			throw new GameError("Wrong room entering");
		}
	}

	public void movePostion(Board board, Position newPosition) {
		board.outputBoard[newPosition.getX()][newPosition.getY()] = this.symbol;
		board.outputBoard[this.p.getX()][this.p.getY()] = ' ';
		this.p = newPosition;
	}

	public boolean isSuggestion() {
		return suggestion;
	}

	public void setSuggestion(boolean suggestion) {
		this.suggestion = suggestion;
	}

	/**
	 * find a movable position inside the room
	 *
	 * @param board
	 * @param c
	 * @return
	 */
	public Position findPositionInTheRoom(Board board, char c) {
		for (int i = 0; i < board.outputBoard.length; i++) {
			boolean foundRoom = false;
			for (int j = 0; j < board.outputBoard[0].length; j++) {
				// int compareNum = 0;
				if (board.outputBoard[i][j] == c && foundRoom) {
					int findRightPosition = 1;
					while (board.outputBoard[i][j - findRightPosition] != ' ') {
						findRightPosition++;
					}
					return new Position(i, j - findRightPosition);
				}
				if (board.outputBoard[i][j] == c && j <= 20 && board.outputBoard[i][j + 1] == ' ') {
					foundRoom = true;
				}
			}
		}

		return null;
	}

	/**
	 * transfer player from one corner room to the facing corner room
	 *
	 * @param board
	 */
	public void useStairs(Board board) {
		char c = this.roomSymbol;
		if (c == 'k') {
			Position position = this.findPositionInTheRoom(board, 's');
			this.movePostion(board, position);
			this.setRoomSymbol('s');
		}
		if (c == 'c') {
			Position position = this.findPositionInTheRoom(board, 'o');
			this.movePostion(board, position);
			this.setRoomSymbol('o');
		}
		if (c == 'o') {
			Position position = this.findPositionInTheRoom(board, 'c');
			getClass();
			this.movePostion(board, position);
			this.setRoomSymbol('c');
		}
		if (c == 's') {
			Position position = this.findPositionInTheRoom(board, 'k');
			this.movePostion(board, position);
			this.setRoomSymbol('k');
		}
	}

	/**
	 * take the player outside the room
	 *
	 * @param board
	 * @param d
	 */
	public void exitRoom(Board board, Door d) {

		if (board.outputBoard[d.getKp().getP().getX()][d.getKp().getP().getY()] == ' ') {
			board.outputBoard[this.p.getX()][this.p.getY()] = ' ';
			board.outputBoard[d.getKp().getP().getX()][d.getKp().getP().getY()] = this.getSymbol();
			this.setP(d.getKp().getP());
			this.setArePlayerInARoom(false);

		} else {
			System.out.println("Sorry, the exit door is blocked.");
		}
	}

	public void checkCards(ArrayList<Card> allCards) {
		String msg = "Here are your knowing cards\n";
		for (Card c : this.getKnowingCards()) {
			msg += c + "\n";
		}
		msg += "\nHere are your unknowing cards\n";
		for (Card c : allCards) {
			if (!this.getKnowingCards().contains(c)) {
				msg += c + "\n";
			}
		}
		msg += "\n";
		System.out.println(msg);

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((p == null) ? 0 : p.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (p == null) {
			if (other.p != null)
				return false;
		} else if (!p.equals(other.p))
			return false;
		return true;
	}

}
