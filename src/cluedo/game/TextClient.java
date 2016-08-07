package cluedo.game;

import java.io.IOException;
import java.util.*;
import cluedo.cards.*;
import cluedo.rooms.Door;

/**
 * Game engine spark(main program to active the game) this class should support
 * screen output and take users input to execute the decision
 *
 * @author rongjiwang
 *
 */
public class TextClient {

	public static ArrayList<Player> team;
	public static Board board;
	public static Scanner scan;

	/**
	 * control the gaming loop, text output, user input
	 *
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// message will be shown if the game could not find the appropriate
		// map.txt file
		if (args.length != 1) {
			System.out.println("Can not setup board with out map.txt file.\n"
					+ "Please drag the map.txt file outside your scr folder,\n"
					+ "or include the map.txt inside your package if run by terminal\n");
			System.exit(0);
		}
		// set up board from board class
		board = new Board(args[0]);
		// install scanner for user input
		scan = new Scanner(System.in);
		// only accept integer input from 3 to 6
		int numOfPlayers = 0;
		while (numOfPlayers < 3 || numOfPlayers > 6) {
			System.out.println("Please enter number 3,4,5 or 6 for how many players are going to play. ");
			System.out.println("For exit the game, Please enter x");
			numOfPlayers = inputNumber("How many players?", scan);
		}
		// store all the appropriate name for user to select
		ArrayList<Characters> chooseName = new ArrayList<>();
		team = new ArrayList<>();
		for (Characters c : Characters.values()) {
			chooseName.add(c);
		}
		// construct players
		int count = 0;
		while (count < numOfPlayers) {
			// if player return as null(invalid input), it will rerun setup
			// player method
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
		// active the game
		Game game = new Game(board, team);
		// loop will stop when a player made a perfect accusation or only 1
		// player left in the game
		while (Game.GAME_STATUS) {
			for (int i = 0; i < team.size(); i++) {
				// int steps = new Dice().throwDice();
				int steps = 30;
				System.out.println(team.get(i).getName() + " roll a dice with <" + steps + "> moves\n");
				for (int j = 0; j < steps; j++) { // per every movement chosen
					System.out.println("-----------------------------------------------------------------------------");
					board.displayBoard();
					System.out.println("-----------------------------------------------------------------------------");
					System.out.println("You[" + team.get(i).getName() + "] have " + (steps - j)
							+ " move(s) left\n Take one option below as your next move\n");
					ArrayList<String> playerOptions = game.checkPlayerOptions(team.get(i), board);
					// show a list of options
					String outputOptions = game.showOptions(playerOptions);
					// pick a integer within the list
					int inputNum = 0;
					while (inputNum < 1 || inputNum > playerOptions.size()) {
						inputNum = inputNumber(outputOptions, scan);
					}
					int num = game.executeTheChoice(playerOptions.get(inputNum - 1), team.get(i), board, game);
					if (num == 1) // player out of the game
						break; // if accusation failed
					else if (num == 2)
						j--; // execute The Move Not Count As Step

				}
				team.get(i).setSuggestion(false);
			}
		}

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

		// take the name choose by user
		int nameNumber = inputNumber(nameList, scan);
		// if it's a valid input
		if (nameNumber > 0 && nameNumber <= chooseName.size()) {
			ArrayList<Card> cardList = new ArrayList<>();
			// store the name
			String name = chooseName.get(nameNumber - 1).name();
			// remove from all names list
			chooseName.remove(nameNumber - 1);
			// user last letter as personal symbol show on the board
			char symbol = name.charAt((name.length()) - 1);
			// clockwise fashion for starting the game on board
			Position start = Board.startPoints.get(0);
			System.out.println("* " + start.toString() + " is your starting position!");
			Board.startPoints.remove(0);
			// complete the player
			Player player = new Player(start, name, symbol, cardList);
			return player;
		}
		// message will be shown if game receive invalid input
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