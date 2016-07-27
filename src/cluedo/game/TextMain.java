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
		HashMap<Character,Position> hm = new HashMap();
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
		
		//System.out.println("Please enter a number(1~9)");
		//System.out.println("1: ");
		Scanner sc = new Scanner(System.in);
		int i = sc.nextInt();


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

	private static ArrayList<Player> inputPlayers(int num){
		ArrayList<Player> players = new ArrayList<Player>();
		HashMap<String,Character> hm = new HashMap();
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
		}
		return players;

	}

}
