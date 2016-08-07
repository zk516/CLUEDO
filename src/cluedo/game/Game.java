package cluedo.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import cluedo.cards.Card;
import cluedo.cards.Characters;
import cluedo.cards.Places;
import cluedo.cards.Weapons;
import cluedo.rooms.BallRoom;
import cluedo.rooms.BilliardRoom;
import cluedo.rooms.Conservatory;
import cluedo.rooms.DiningRoom;
import cluedo.rooms.Door;
import cluedo.rooms.Hall;
import cluedo.rooms.Kitchen;
import cluedo.rooms.Library;
import cluedo.rooms.Lounge;
import cluedo.rooms.Room;
import cluedo.rooms.Study;

public class Game {
	Board board;
	public ArrayList<Player> playTeam;
	public static ArrayList<Card> allCards;
	public static boolean GAME_STATUS; // textmain should set it to true
	public static ArrayList<Card> currentCards;
	public static ArrayList<Card> cluedoCards;
	public static ArrayList<Card> showLeftCards;

	public Game(Board board, ArrayList<Player> playTeam) {
		this.board = board;
		this.playTeam = playTeam;
		GAME_STATUS = true;
		renewAllCards(); // split reminding cards and cluedo cards
		handOutCards(currentCards);
		displayPlayers(playTeam);
	}


	/**
	 * take user's input, must be number as given, also support to exit the game
	 *
	 * @param msg
	 * @param scan
	 * @return integer for decision
	 * @throws IOException
	 */
	private static int inputNumber(String msg, Scanner scan) throws IOException {
		System.out.println(msg);
		if (scan.hasNextInt()) {
			int num = scan.nextInt();
			return num;
		} else {
			if (scan.next().equalsIgnoreCase("x")) {
				System.exit(1);
			}
			return -1;
		}
	}



	/**
	 * player should execute the string decision by himself/herself
	 *
	 * @param string
	 * @param player
	 * @param board
	 * @return a special integer to represent a none countable move or
	 *         exceptions(eg. player out of the game and so on)
	 * @throws IOException
	 */
	static int executeTheChoice(String string, Player player, Board board, Game game) throws IOException {
		int moveNotCountAsStep = 2;
		switch (string) {

		case "move north":

			player.Move("north", board);
			break;
		case "move south":

			player.Move("south", board);
			break;
		case "move east":

			player.Move("east", board);
			break;
		case "move west":

			player.Move("west", board);
			break;
		case "entry BallRoom":
			player.enterRoom(board, 'b');
			break;
		case "entry BilliardRoom":
			player.enterRoom(board, 'i');
			break;
		case "entry Conservatory":
			player.enterRoom(board, 'c');
			break;
		case "entry DiningRoom":
			player.enterRoom(board, 'd');
			break;
		case "entry Hall":
			player.enterRoom(board, 'h');
			break;
		case "entry Kitchen":
			player.enterRoom(board, 'k');
			break;
		case "entry Library":
			player.enterRoom(board, 'l');
			break;
		case "entry Lounge":
			player.enterRoom(board, 'o');
			break;
		case "entry Study":
			player.enterRoom(board, 's');
			break;
		case "go stairs":
			// use the stairs to transfer the player to another room
			player.useStairs(board);
			break;
		case "exit room": // multiple doors
			// find the door and transfer player to the knock door position
			Door d = UserChooseADoor(board, player);
			player.exitRoom(board, d);
			break;
		case "make suggestion":
			// if player name match with suggestion name,move that player to the
			// current room with this player
			ArrayList<Card> suggestionCards = new ArrayList<>();
			Card character1 = chooseUnknowCharacter(player, TextClient.scan, Game.allCards);
			Card weapon1 = chooseUnknowWeapon(player, TextClient.scan, Game.allCards);
			Card place1 = findPlayerLocation(player, board);
			suggestionCards.add(character1);
			suggestionCards.add(weapon1);
			suggestionCards.add(place1);
			// drag the the player(if match with alive player) from suggestion
			// to the current player room
			for (Player p : TextClient.team) {
				if (p.getName().equalsIgnoreCase(character1.toString())) {
					transferThePlayer(player, board, p);
				}
			}
			// clockwise fashion check cards from next alive player
			refuteSuggestion(player, suggestionCards);
			return moveNotCountAsStep;
		case "make accusation":
			// compare the card names with the cludoCards,if match player win
			// ,else remove player from the game
			// show all the cards the other players
			Card character = chooseUnknowCharacter(player, TextClient.scan, Game.allCards);
			Card place = chooseUnknowPlace(player, TextClient.scan, Game.allCards);
			Card weapon = chooseUnknowWeapon(player, TextClient.scan, Game.allCards);
			if (Game.cluedoCards.contains(character) && Game.cluedoCards.contains(place)
					&& Game.cluedoCards.contains(weapon)) {
				game.wonTheGame(player);
			} else {
				return game.lostTheGame(player);
			}

			break;
		case "check cards":
			// display the knowing cards to player
			// also display unknown cards to player
			player.checkCards(Game.allCards);
			return moveNotCountAsStep;
		case "guidelines":
			game.outPutGuidelines();
			return moveNotCountAsStep;
		case "end turn":
			System.out.println("[" + player.getName() + "] made (end turn) move, next player please!");
			return 1;

		default:
			throw new GameError("invaild option " + string);
		}
		return 0;
	}

	/**
	 * output text to inform user to choose a exit door from current room
	 *
	 * @param board2
	 * @param player
	 * @return
	 * @throws IOException
	 */
	private static Door UserChooseADoor(Board board2, Player player) throws IOException {
		ArrayList<Door> doorsOption = board2.getExitDoors(player.getRoomSymbol());
		String output = "Please choose your exit door from the position(s) below.\n";
		for (int i = 0; i < doorsOption.size(); i++) {
			output += (i + 1) + ". " + doorsOption.get(i).getKp() + "\n";
		}
		int inputNum = 0;
		while (inputNum < 1 || inputNum > doorsOption.size()) {
			inputNum = inputNumber(output, TextClient.scan);
		}

		return doorsOption.get(inputNum - 1);
	}

	/**
	 * clockwise any other player has one of suggestion card,the card will
	 * become this player's knowing card
	 *
	 * @param player
	 * @param suggestionCards
	 */
	private static void refuteSuggestion(Player player, ArrayList<Card> suggestionCards) {
		int getPlayerIndex = -1;
		int fullRoundClockwiseCount = 1;
		System.out.println("---------------------------------------------------------");
		System.out.println("Checking your suggestion with clockwise players\n");
		for (int i = 0; i < TextClient.team.size(); i++) { // get the player index
			if (player.equals(TextClient.team.get(i))) {
				getPlayerIndex = i + 1;
			}
		}
		// clockwise fashion to check next player
		while (fullRoundClockwiseCount < TextClient.team.size()) {
			if (getPlayerIndex >= TextClient.team.size()) {
				getPlayerIndex = 0;
			}
			for (Card c : suggestionCards) {
				if (TextClient.team.get(getPlayerIndex).getKnowingCards().contains(c)) {
					// at least a player refute your suggestion
					player.getKnowingCards().add(c);
					System.out.println(TextClient.team.get(getPlayerIndex).getName() + " has " + c.toString() + ".");
					System.out.println("Your suggestion has refuted.");
					System.out.println("However [" + c.toString() + "] card will added to your knowing card list.");
					System.out.println("---------------------------------------------------------");
					return;
				} else {
					System.out.println("[" + TextClient.team.get(getPlayerIndex).getName() + "] doesn't have " + c.toString()
							+ " in his/her bag");
				}
			}
			System.out.println(
					"[" + TextClient.team.get(getPlayerIndex).getName() + "] has no match card with your suggestion, pass on!");
			getPlayerIndex++;
			fullRoundClockwiseCount++;
		}
		// perfect suggestion
		System.out.println("Congratulation!, you made a perfect suggestion, "
				+ "make your accusation with those cards will help you to win this game!");
		System.out.println("---------------------------------------------------------");
	}

	/**
	 * transfer the player from suggestion to the made suggestion player's room
	 *
	 * @param player
	 * @param board2
	 * @param p
	 */
	private static void transferThePlayer(Player player, Board board2, Player p) {
		Position position = player.findPositionInTheRoom(board2, player.getRoomSymbol());
		p.movePostion(board2, position);
		p.setArePlayerInARoom(true);
		p.setRoomSymbol(player.getRoomSymbol());
	}

	/**
	 * return current player location as card name
	 *
	 * @param player
	 * @param board2
	 * @return
	 */
	public static Card findPlayerLocation(Player player, Board board2) {
		char c = player.getRoomSymbol();
		switch (c) {
		case 'k':
			return Places.KITCHEN;
		case 'b':
			return Places.BALL_ROOM;
		case 'i':
			return Places.BILLIARD_ROOM;
		case 'c':
			return Places.CONSERVATORY;
		case 'd':
			return Places.DINING_ROOM;
		case 'h':
			return Places.HALL;
		case 'l':
			return Places.LIBRARY;
		case 'o':
			return Places.LOUNGE;
		case 's':
			return Places.STUDY;
		default:
			throw new GameError("Not a game board room");
		}
	}

	/**
	 *
	 * @param player
	 * @param scan2
	 * @param allCards
	 * @return
	 * @throws IOException
	 */
	private static Card chooseUnknowWeapon(Player player, Scanner scan2, ArrayList<Card> allCards) throws IOException {
		String output = "Choose a weapon for your accusation\n";
		ArrayList<Card> list = new ArrayList<>();
		for (Card c : allCards) {
			if (!player.getKnowingCards().contains(c)) {
				if (c instanceof Weapons) {
					list.add(c);
					output += (list.size()) + ". " + c + "\n";
				}
			}
		}
		if (!list.isEmpty()) {
			int num = 0;
			while (num < 1 || num > list.size()) {
				num = inputNumber(output, scan2);
			}
			return list.get(num - 1);
		}

		return null;
	}

	/**
	 *
	 * @param player
	 * @param scan2
	 * @param allCards
	 * @return
	 * @throws IOException
	 */
	private static Card chooseUnknowPlace(Player player, Scanner scan2, ArrayList<Card> allCards) throws IOException {
		String output = "Choose a place for your accusation\n";
		ArrayList<Card> list = new ArrayList<>();
		for (Card c : allCards) {
			if (!player.getKnowingCards().contains(c)) {
				if (c instanceof Places) {
					list.add(c);
					output += (list.size()) + ". " + c + "\n";
				}
			}
		}
		if (!list.isEmpty()) {
			int num = 0;
			while (num < 1 || num > list.size()) {
				num = inputNumber(output, scan2);
			}
			return list.get(num - 1);
		}

		return null;
	}

	/**
	 *
	 * @param player
	 * @param scan2
	 * @param allCards
	 * @return
	 * @throws IOException
	 */
	private static Card chooseUnknowCharacter(Player player, Scanner scan2, ArrayList<Card> allCards)
			throws IOException {
		String output = "Choose a character for your accusation\n";
		ArrayList<Card> list = new ArrayList<>();
		for (Card c : allCards) {
			if (!player.getKnowingCards().contains(c)) {
				if (c instanceof Characters) {
					list.add(c);
					output += (list.size()) + ". " + c + "\n";
				}
			}
		}
		if (!list.isEmpty()) {
			int num = 0;
			while (num < 1 || num > list.size()) {
				num = inputNumber(output, scan2);
			}
			return list.get(num - 1);
		}

		return null;
	}

	/**
	 * list all current player options as string for display on screen
	 *
	 * @param playerOptions
	 * @return
	 */
	static String showOptions(ArrayList<String> playerOptions) {
		String output = "";
		for (int i = 0; i < playerOptions.size(); i++) {
			output += (i + 1) + ". " + playerOptions.get(i) + "\n";
		}
		return output;
	}





	/**
	 * set up starting position for each player
	 *
	 * @param playTeam2
	 */
	private void displayPlayers(ArrayList<Player> team) {
		for (Player p : team) {
			board.outputBoard[p.getP().getX()][p.getP().getY()] = p.getSymbol();
		}
	}

	/**
	 * store the leftover cards and show to all players(add into their known
	 * cards collection) randomly evenly hand out the remaining cards to each
	 * player
	 *
	 * @param cards
	 */
	private void handOutCards(ArrayList<Card> cards) {
		showLeftCards = new ArrayList<>();
		int cardsLeft = cards.size() % playTeam.size();
		if (cardsLeft != 0) {
			for (int i = 0; i < cardsLeft; i++) {
				showLeftCards.add(cards.get(i));
				cards.remove(i);
			}
		}

		// split cards to player
		Collections.shuffle(cards);
		int cardsNumber = cards.size() / playTeam.size();
		for (Player p : playTeam) {
			for (int i = 0; i < cardsNumber; i++) {
				p.getKnowingCards().add(cards.get(0));
				cards.remove(0);
			}

			// just make really random
			Collections.shuffle(cards);

		}
	}

	/**
	 * randomly choose 3 card to store as cluedo cards(a character, a place and
	 * a weapon) store the rest into collection
	 */
	public void renewAllCards() {
		// Set<Card> shiftCards = new HashSet<>();
		currentCards = new ArrayList<>();
		allCards = new ArrayList<>();
		for (Card c : Characters.values()) {
			currentCards.add(c);
			allCards.add(c);
		}
		for (Card c : Places.values()) {
			currentCards.add(c);
			allCards.add(c);

		}
		for (Card c : Weapons.values()) {
			currentCards.add(c);
			allCards.add(c);

		}
		Collections.shuffle(currentCards);
		cluedoCards = new ArrayList<>();
		boolean haveCharacter = false;
		boolean havePlaces = false;
		boolean haveWeapon = false;
		for (int i = 0; i < currentCards.size(); i++) {
			Card c = currentCards.get(i);
			if (c instanceof Characters && !haveCharacter) {
				cluedoCards.add(c);
				currentCards.remove(i);
				haveCharacter = true;
			}
			if (c instanceof Places && !havePlaces) {
				cluedoCards.add(c);
				currentCards.remove(i);
				havePlaces = true;
			}
			if (c instanceof Weapons && !haveWeapon) {
				cluedoCards.add(c);
				currentCards.remove(i);
				haveWeapon = true;
			}

		}

	}

	/**
	 * the game should filter through all the options that suitable for current
	 * player to choose
	 *
	 * @param player
	 *            current player
	 * @return options list
	 */
	public ArrayList<String> checkPlayerOptions(Player player, Board board) {
		ArrayList<String> currentOptions = new ArrayList<>();
		// move options
		ArrayList<String> directions = board.movable(player);
		if (!directions.isEmpty()) {
			for (String s : directions) {
				currentOptions.add(s);
			}
		}
		// enter room option
		String roomEntry = checkRoomOption(player, board);
		if (roomEntry != null) {
			currentOptions.add(roomEntry);
		}
		// stair use option
		String stairOption = checkStairOption(player, board);
		if (stairOption != null) {
			currentOptions.add(stairOption);
		}

		// exit room option
		String exitRoomOption = checkExitRoomOption(player, board);
		if (exitRoomOption != null) {
			currentOptions.add(exitRoomOption);
		}
		// suggestion option
		String makeSuggestion = checkSuggestionOption(player, board);
		if (makeSuggestion != null) {
			currentOptions.add(makeSuggestion);
		}
		// accusation option
		String makeAccusation = "make accusation";
		currentOptions.add(makeAccusation);
		// check knowing cards
		String checkCards = "check cards";
		currentOptions.add(checkCards);

		String makeEndTurn = "end turn";
		currentOptions.add(makeEndTurn);

		String guideLine = "guidelines";
		currentOptions.add(guideLine);

		return currentOptions;
	}

	/**
	 * as long as the player in a room, this should return make suggestion
	 *
	 * @param player
	 * @param board2
	 * @return
	 */
	public String checkSuggestionOption(Player player, Board board2) {
		if (player.isArePlayerInARoom() && !player.isSuggestion()) {
			player.setSuggestion(true);
			return "make suggestion";
		}
		return null;
	}

	/**
	 * exit room options , could be multiple doors
	 *
	 * @param player
	 * @param board2
	 * @return
	 */
	public String checkExitRoomOption(Player player, Board board2) {
		if (player.isArePlayerInARoom()) {
			return "exit room";
		}
		return null;
	}

	/**
	 * only 4 room should support this stair option,check player in thr right
	 * room or not
	 *
	 * @param player
	 * @param board2
	 * @return
	 */
	public String checkStairOption(Player player, Board board2) {
		// if player in the room
		// if player in 1,2,3,4,room,
		// then the stair can be using
		if (player.isArePlayerInARoom()) {
			if (player.getRoomSymbol() == 'k' || player.getRoomSymbol() == 'c' || player.getRoomSymbol() == 'o'
					|| player.getRoomSymbol() == 's') {
				return "go stairs";
			}
		}
		return null;
	}

	/**
	 * should return enter room if player stand on a knock door position
	 *
	 * @param player
	 * @param board2
	 * @return
	 */
	public String checkRoomOption(Player player, Board board2) {
		// return the correct room name only player standing at the knocking
		// door position
		for (Room r : board2.allRooms) {
			if (r instanceof BallRoom) {
				BallRoom ballRoom = (BallRoom) r;
				for (Door d : ballRoom.getDoors()) {
					if (player.getP().equals(d.getKp().getP())) {
						return "entry BallRoom";
					}
				}
			} else if (r instanceof BilliardRoom) {
				BilliardRoom billiardRoom = (BilliardRoom) r;
				for (Door d : billiardRoom.getDoors()) {
					if (player.getP().equals(d.getKp().getP())) {
						return "entry BilliardRoom";
					}
				}
			} else if (r instanceof Conservatory) {
				Conservatory conservatory = (Conservatory) r;
				if (player.getP().equals(conservatory.getD().getKp().getP())) {

					return "entry Conservatory";
				}
			} else if (r instanceof DiningRoom) {
				DiningRoom diningRoom = (DiningRoom) r;
				for (Door d : diningRoom.getDoors()) {
					if (player.getP().equals(d.getKp().getP())) {
						return "entry DiningRoom";
					}
				}
			} else if (r instanceof Hall) {
				Hall hall = (Hall) r;
				for (Door d : hall.getDoors()) {
					if (player.getP().equals(d.getKp().getP())) {
						return "entry Hall";
					}
				}
			} else if (r instanceof Kitchen) {
				Kitchen kitchen = (Kitchen) r;
				if (player.getP().equals(kitchen.getD().getKp().getP())) {
					return "entry Kitchen";
				}

			} else if (r instanceof Library) {
				Library library = (Library) r;
				for (Door d : library.getDoors()) {
					if (player.getP().equals(d.getKp().getP())) {
						return "entry Library";
					}
				}
			} else if (r instanceof Lounge) {
				Lounge lounge = (Lounge) r;
				if (player.getP().equals(lounge.getD().getKp().getP())) {
					return "entry Lounge";
				}

			} else if (r instanceof Study) {
				Study study = (Study) r;
				if (player.getP().equals(study.getD().getKp().getP())) {
					return "entry Study";
				}

			}

		}
		return null;
	}

	/**
	 * won the game , print messages and exit the game
	 *
	 * @param player
	 */
	public void wonTheGame(Player player) {
		String output = "-------------The accusation does perfect match with the secret result-------------";
		for (Card c : Game.cluedoCards) {
			output += c.toString() + "\n";
		}
		output += "These three are the hidden secret cluedo cards\n";
		output += player.getName() + " has just won the game.\n";
		output += "Good luck with next round\n";
		System.out.println(output);
		System.exit(1);
	}

	/**
	 * erase player from the map, send message out, show his cards to the rest
	 *
	 * @param player
	 * @return
	 */
	public int lostTheGame(Player player) {
		String output = "-------------The accusation doesn't match with the secret result-------------\n";
		output += player.getName() + " has just lost this game, he will face up all his cards.\n";
		System.out.println(output);
		// erase player from the board
		board.outputBoard[player.getP().getX()][player.getP().getY()] = ' ';
		// show up card to other players
		for (Player p : TextClient.team) {
			if (p != player) {
				for (Card c : player.getKnowingCards()) {
					if (!p.getKnowingCards().contains(c)) {
						p.getKnowingCards().add(c);
					}
				}
			}
		}
		// remove player
		TextClient.team.remove(player);
		if (TextClient.team.size() == 1) {
			wonTheGame(TextClient.team.get(0));
		}
		return 1;
	}

	public void outPutGuidelines() {
		String output = "*********************Game Guidelines***********************\n";
		output += "symbol [k] is Kitchen room.\n";
		output += "symbol [b] is Ball room.\n";
		output += "symbol [i] is Billiard room.\n";
		output += "symbol [c] is Conservatory room.\n";
		output += "symbol [d] is Dining room.\n";
		output += "symbol [h] is Hall room.\n";
		output += "symbol [l] is Library room.\n";
		output += "symbol [o] is Lounge room.\n";
		output += "symbol [s] is Study room.\n";
		output += "\n";
		output += "symbol [1,2,3,4] are stairs in the rooms,\n"
				+ "stairs only can take players to a corresponding room,\n" + "1 <--> 4 or 2 <--> 3.\n";
		output += "\n";
		output += "symbol [+] means door, player can not stand on the + symbol,\n"
				+ "the entry room option should only appear when the player stand on the knocking door position\n";
		output += "\n";
		output += "Player can only win the game by make perfect accusation or the player is the last player in the game\n";
		output += "\n";
		output += "Player will lose this game by making wrong accusation, and show all your cards to others\n";
		output += "\n";
		output += "When player stand in a room, he should have one chance per round to make suggestion,\n"
				+ "if the suggestion character is still playing in the game, he should get transfered to the corresponding room,\n"
				+ "and then other players should use clockwise fashion to show\n at least one matching card to reject this suggestion.\n"
				+ "but, this card will become the current player's knowing card\n";
		output += "Otherwise this player has just made a perfect suggestion to match cludo secret cards\n";
		output += "\n";
		output += "*********************Enjoy the Cluedo***********************\n";
		System.out.println(output);
	}
}
