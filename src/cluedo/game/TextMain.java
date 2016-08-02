package cluedo.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import cluedo.cards.*;
import cluedo.cards.Character;
import cluedo.game.*;
import cluedo.rooms.*;
/**
 * Game engine spark(main program to active the game)
 * this class should support screen output and take users input to execute
 * @author rongjiwang
 *
 */
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
		// System.out.println(new Dice().throwDice());
		// setup room.stair
		// Game game = new Game(players, board);

		//Give each character a fixed position
		//Should this be in the board class?
		HashMap<Character,Position> hm = new HashMap<Character, Position>();
		try{
			hm.put(Character.COLONEL_MUSTARD, Board.startPoints.get(0));
			hm.put(Character.MISS_SCARLETT, Board.startPoints.get(1));
			hm.put(Character.MRS_PEACOCK, Board.startPoints.get(2));
			hm.put(Character.MRS_WHITE, Board.startPoints.get(3));
			hm.put(Character.PROFESSOR_PLUM, Board.startPoints.get(4));
			hm.put(Character.THE_REVEREND_GREEN, Board.startPoints.get(5));
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("start point position is not correct");
		}

		//Print banner
		System.out.println("*******Cluedo*******");
		System.out.println("By Rongji Wang && Pukun Yang");

		//input player info
		int numOfPlayers = inputNumber("How many players?");
		ArrayList<Player> players = inputPlayers(numOfPlayers);

		//set position for the given players
		for(Player p: players){
			if(hm.containsKey(p.getCharacter())){
				p.setPosition(hm.get(p.getCharacter()));

			}
		}

		//change the board for the given players
		for(Player p: players){
			//System.out.println(p.getPosition().toString());
			Board.board[p.getPosition().getX()][p.getPosition().getY()]=p.getSimbol();
		}
		//change the position of the remaining players to '-'
		for (int i = 0; i < Board.board.length; i++) {
			for (int j = 0; j < Board.board[0].length; j++) {
				if(Board.board[i][j]=='S'){
					Board.board[i][j] = '-';
				}
			}
		}

		//Deal cards to each player




		//the game starts
		int turn = 1;
		Dice d = new Dice();
		while (true) { // loop forever
			System.out.println("\n********************");
			System.out.println("***** TURN " + turn + " *******");
			System.out.println("********************\n");
			b.displayBoard(Board.board);
			for(Player p: players){
				System.out.println(p.toString());
				int steps = d.throwDice();
				//int steps = 3;//need to be changed
				moveOption(steps,p,b);
			}

		}

	}



	private void gameOption(){

	}

	private static String inputString(String msg){
		System.out.println(msg+" ");
		while(true){
			BufferedReader input  = new BufferedReader(new InputStreamReader(System.in));
			try{
				return input.readLine();
			}catch(IOException e){
				System.out.println("I/O Error ... Please try again");
			}
		}
	}

	private static int inputNumber(String msg){
		System.out.println(msg+" ");
		while(true){
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			try{
				String num = input.readLine();
				if(num != null){
					return Integer.parseInt(num);
				}
			}catch(IOException e){
				System.out.println("Please enter a number!");
			}

		}

	}

	private static char inputChar(String msg){
		System.out.println(msg+" ");
		while(true){
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			try{
				String c = input.readLine();
				if(c.length()>1){
					System.out.println("Please enter a single character!");
				}
				return c.charAt(0);
			}catch(IOException e){
				System.out.println("I/O Error ... Please try again");
			}
		}
	}

	private static ArrayList<Player> inputPlayers(int num){
		ArrayList<Player> players = new ArrayList<Player>();
		HashMap<String,Character> hm = new HashMap<String, Character>();
		hm.put("sca", Character.MISS_SCARLETT);
		hm.put("mus", Character.COLONEL_MUSTARD);
		hm.put("pea", Character.MRS_PEACOCK);
		hm.put("whi", Character.MRS_WHITE);
		hm.put("plu", Character.PROFESSOR_PLUM);
		hm.put("gre", Character.THE_REVEREND_GREEN);
		for(int i = 0; i !=num; i++){
			String playerName = inputString("Player #"+ i + " name?");
			String character = inputString("Player #"+ i + " character?\n"
					+ "sca for MISS_SCARLETT\n"
					+ "mus for COLONEL_MUSTARD\n"
					+ "pea for MRS_PEACOCK\n"
					+ "whi for MRS_WHITE\n"
					+ "plu for PROFESSOR_PLUM\n"
					+ "gre for THE_REVEREND_GREEN\n");
			if(hm.containsKey(character)){
				players.add(new Player(null,character, hm.get(character), null));
				hm.remove(character);
			}else{
				character = inputString("Player #"+ i + " character?\n"
						+ "sca for MISS_SCARLETT\n"
						+ "mus for COLONEL_MUSTARD\n"
						+ "pea for MRS_PEACOCK\n"
						+ "whi for MRS_WHITE\n"
						+ "plu for PROFESSOR_PLUM\n"
						+ "gre for THE_REVEREND_GREEN\n");
			}
			//ask the user to get a single character to show on the board
			char simbol = inputChar("Player #"+ i + " simbol?\n This is the simbol to show on the board.");
			players.get(i).setSimbol(simbol);
		}
		return players;

	}
	private static void moveOption(int steps,Player p,Board b){

		while(steps!=0){
			System.out.println("Remaining steps: "+steps+"\n");
			char c = inputChar("Please choose a direction!\n"
					+ "w: up\n"
					+ "a: left\n"
					+ "s: down\n"
					+ "d: right\n");
			if(c=='w'){
				moveUp(p);
				b.displayBoard(Board.board);
			}else if(c == 'a'){
				moveLeft(p);
				b.displayBoard(Board.board);
			}else if(c =='s'){
				moveDown(p);
				b.displayBoard(Board.board);
			}else if(c == 'd'){
				moveRight(p);
				b.displayBoard(Board.board);
			}
			steps--;
		}


	}


	//For those four move method, need to add checking system.
	private static void moveUp(Player p){
		Board.board[p.getPosition().getX()][p.getPosition().getY()] = ' ';
		Board.board[p.getPosition().getX()-1][p.getPosition().getY()] = p.getSimbol();
		p.setPosition(new Position(p.getPosition().getX()-1,p.getPosition().getY()));
	}

	private static void moveLeft(Player p){
		Board.board[p.getPosition().getX()][p.getPosition().getY()] = ' ';
		Board.board[p.getPosition().getX()][p.getPosition().getY()-1] = p.getSimbol();
		p.setPosition(new Position(p.getPosition().getX(),p.getPosition().getY()-1));
	}

	private static void moveDown(Player p){
		Board.board[p.getPosition().getX()][p.getPosition().getY()] = ' ';
		Board.board[p.getPosition().getX()+1][p.getPosition().getY()] = p.getSimbol();
		p.setPosition(new Position(p.getPosition().getX()+1,p.getPosition().getY()));
	}

	private static void moveRight(Player p){
		Board.board[p.getPosition().getX()][p.getPosition().getY()] = ' ';
		Board.board[p.getPosition().getX()][p.getPosition().getY()+1] = p.getSimbol();
		p.setPosition(new Position(p.getPosition().getX(),p.getPosition().getY()+1));
	}


}
