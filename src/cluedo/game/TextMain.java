package cluedo.game;

import java.io.IOException;
import java.util.*;
import cluedo.cards.*;
import cluedo.rooms.Door;

/**
 * Game engine spark(main program to active the game) this class should support
 * screen output and take users input to execute
 *
 * @author rongjiwang
 *
 */
public class TextMain {

	private static ArrayList<Player> team;
	public static Board board;
	private static Scanner scan;

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
		board = new Board(args[0]);
		// I will user last letter of player name to show the position on the

		// // Print banner
		// System.out.println("*******Cluedo*******");
		// System.out.println("By Rongji Wang && Pukun Yang");
		//
		// // input player info
		scan = new Scanner(System.in);
		int numOfPlayers = 0;
		while (numOfPlayers < 3 || numOfPlayers > 6) {
			System.out.println("Please enter number 3,4,5 or 6 for how many players are going to play. ");
			System.out.println("For exit the game, Please enter x");
			numOfPlayers = inputNumber("How many players?", scan);

		}
		ArrayList<Characters> chooseName = new ArrayList<>();
		team = new ArrayList<>();
		for (Characters c : Characters.values()) {
			chooseName.add(c);
		}
		int count = 0;
		while (count < numOfPlayers) {
			Player player = setUpNewPlayer(chooseName);
			if (player != null) {
				count++;
				team.add(player);
				String msg = "Hello User " + (count) + ", you have chosen <" + player.getName()
						+ "> to be your character name\nYou symbol on the map will be [" + player.getSymbol()
						+ "] \nGood Luck with your game!\n";
				msg += "---------------------------------------------------------\n";
				System.out.println(msg);
			}
		}
		Game game = new Game(board, team);

		while (Game.GAME_STATUS) { // game still goes
			for (int i = 0; i < team.size(); i++) { // per each player
				//int steps = new Dice().throwDice();
				int steps = 30;
				System.out.println(team.get(i).getName() + " roll a dice with <" + steps + "> moves\n");
				for (int j = 0; j < steps; j++) { // per every movement chosen
					board.displayBoard(); // by player
					System.out.println(
							"You have " + (steps - j) + " moves left\n Take one option below as your next move\n");
					ArrayList<String> playerOptions = game.checkPlayerOptions(team.get(i), board);

					String outputOptions = makeOptions(playerOptions);
					int inputNum = 0;
					while (inputNum < 1 || inputNum > playerOptions.size()) {
						inputNum = inputNumber(outputOptions, scan);
					}
					int num = executeTheChoice(playerOptions.get(inputNum - 1), team.get(i), board);
					if (num == 1) // player out of the game
						break; // if accusation failed
					else if (num == 2)
						j--; // execute The Move Not Count As Step
					// move?
					// enter a room -make suggestion -exit a room - go to stairs
					// check cards
					//
					// make final decision
				}
			}
		}
		// ArrayList<Player> players = inputPlayers(numOfPlayers);
		//
		// // System.out.println("Please enter a number(1~9)");
		// // System.out.println("1: ");
		// Scanner sc = new Scanner(System.in);
		// int i = sc.nextInt();

	}

	private static int executeTheChoice(String string, Player player, Board board) throws IOException {
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
			System.out.println("aaa");
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
			// clockwise check each player's cards until one match with the
			// suggestion, suggestion fail
			// else suggestion is right , this player will becomes the winner
			ArrayList<Card> suggestionCards = new ArrayList<>();
			Card character1 = chooseUnknowCharacter(player, scan, Game.allCards);
			Card weapon1 = chooseUnknowWeapon(player, scan, Game.allCards);
			Card place1 = findPlayerLocation(player, board);
			suggestionCards.add(character1);
			suggestionCards.add(weapon1);
			suggestionCards.add(place1);

			for (Player p : team) {
				if (p.getName().equalsIgnoreCase(character1.toString())) {
					transferThePlayer(player, board, p);
				}
			}
			refuteSuggestion(player, suggestionCards);
			return moveNotCountAsStep;
		case "make accusation":
			// compare the card names with the cludoCards,if match player win
			// ,else remove player from the game
			// show all the cards the other players
			Card character = chooseUnknowCharacter(player, scan, Game.allCards);
			Card place = chooseUnknowPlace(player, scan, Game.allCards);
			Card weapon = chooseUnknowWeapon(player, scan, Game.allCards);
			if (Game.cluedoCards.contains(character) && Game.cluedoCards.contains(place)
					&& Game.cluedoCards.contains(weapon)) {
				wonTheGame(player);
			} else {
				return lostTheGame(player);
			}

			break;
		case "check cards":
			// display the knowing cards to player
			// also display unknown cards to player
			player.checkCards(Game.allCards);
			return moveNotCountAsStep;
		case "map guide":
			return moveNotCountAsStep;
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
			inputNum = inputNumber(output, scan);
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
		String output = "Checking your suggestion with clockwise players\n";
		for (Player p : team) {
			if (p != player) {
				for (Card c : suggestionCards) {
					if (p.getKnowingCards().contains(c)) {
						player.getKnowingCards().add(c);
						output += p.getName() + " has " + c.toString() + " \n";
						output += "Your suggestion has refuted.\n";
						System.out.println(output);
						return;
					}
				}
			}
		}

		wonTheGame(player);
	}

	private static void transferThePlayer(Player player, Board board2, Player p) {
		Position position = player.findPositionInTheRoom(board2, player.getRoomSymbol());
		p.movePostion(board2, position);
		p.setArePlayerInARoom(true);
		p.setRoomSymbol(player.getRoomSymbol());
		p.setSuggestion(true);
	}

	/**
	 * return current player location as card name
	 *
	 * @param player
	 * @param board2
	 * @return
	 */
	private static Card findPlayerLocation(Player player, Board board2) {
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
	 * erase player from the map, send message out, show his cards to the rest
	 * players
	 *
	 * @param player
	 * @return
	 */
	private static int lostTheGame(Player player) {
		String output = "";
		output += player.getName() + "has just lost this game, he will face up all his cards.\n";
		System.out.println(output);
		board.outputBoard[player.getP().getX()][player.getP().getY()] = ' ';
		for (Player p : team) {
			if (p != player) {
				for (Card c : player.getKnowingCards()) {
					if (!p.getKnowingCards().contains(c)) {
						p.getKnowingCards().add(c);
					}
				}
			}
		}
		team.remove(player);
		if (team.size() == 1) {
			wonTheGame(team.get(0));
		}
		return 1;
	}

	/**
	 * won the game , print messages and exit the game
	 *
	 * @param player
	 */
	private static void wonTheGame(Player player) {
		String output = "";
		output += player.getName() + " made a perfect accusation\n";
		output += player.getName() + " has just won the game.";
		System.out.println(output);
		System.exit(1);
	}

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
	private static String makeOptions(ArrayList<String> playerOptions) {
		String output = "";
		for (int i = 0; i < playerOptions.size(); i++) {
			output += (i + 1) + ". " + playerOptions.get(i) + "\n";
		}
		return output;
	}

	/**
	 * setup a player with start position, choose name by user, empty card list
	 *
	 * @param chooseName
	 * @return
	 * @throws IOException
	 */
	private static Player setUpNewPlayer(ArrayList<Characters> chooseName) throws IOException {
		String nameList = "*Please enter a number from 1 to " + chooseName.size() + " to choose your name\n";

		for (int i = 0; i < chooseName.size(); i++) {
			nameList += (i + 1) + "." + chooseName.get(i) + "\n";
		}
		nameList += "*The last Letter in your name will represent you in the map\n";
		nameList += "*Your starting position will be randomly selected by the computer";
		int nameNumber = inputNumber(nameList, scan);
		if (nameNumber > 0 && nameNumber <= chooseName.size()) {
			ArrayList<Card> cardList = new ArrayList<>();
			String name = chooseName.get(nameNumber - 1).name();// maybe
																// tostring
																// ,just try
																// name()***
			chooseName.remove(nameNumber - 1);

			char symbol = name.charAt((name.length()) - 1);
			//Collections.shuffle(Board.startPoints);
			Position start = Board.startPoints.get(0);
			Board.startPoints.remove(0);

			Player player = new Player(start, name, symbol, cardList);
			return player;
		}
		System.out.println("Warning! only require number from 1 to " + chooseName.size() + " \nPlease try again!\n");
		return null;
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

}