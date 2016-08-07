package cluedo.game;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import cluedo.cards.Card;
import cluedo.cards.Characters;

public class CluedoTest {

	@Test
	public void test01_GAME_STATUS() {
		try {
			Game game = setUpMockGame();
			assert game.GAME_STATUS = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void test02_PlayerOption(){
		try {
			Game game = setUpMockGame();

			String name1 = Characters.COLONEL_MUSTARD.name();
			Player p1 = new Player(game.board.startPoints.get(0),name1,name1.charAt(name1.length()-1), new ArrayList<Card>());
			assertFalse (game.checkPlayerOptions(p1, game.board).isEmpty());
			assertTrue(game.checkPlayerOptions(p1, game.board).size()>=3);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test03_PlayerStartingPosition(){
		try {
			Game game = setUpMockGame();

			assertTrue(game.playTeam.get(0).getP().equals(new Position(0,9)));
			assertTrue(game.playTeam.get(1).getP().equals(new Position(0,14)));
			assertTrue(game.playTeam.get(2).getP().equals(new Position(6,23)));
			assertTrue(game.playTeam.get(3).getP().equals(new Position(19,23)));
			assertTrue(game.playTeam.get(4).getP().equals(new Position(24,7)));
			assertTrue(game.playTeam.get(5).getP().equals(new Position(17,0)));


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	//the dice should always be in 1~6 range
	public void test04_dice(){
		int i = 0;
		Dice d = new Dice();
		while(i!=100){
			assertTrue(d.throwDice()>=1 && d.throwDice()<=6);
			i++;
		}


	}
	@Test
	//the cluedo Cards should be be three
	public void test05_checkCluedoCards(){
		try{
			Game game = setUpMockGame();
			assertTrue(game.cluedoCards.size()==3);
		}catch (IOException e) {

		}
	}

	@Test
	//In the game, there are 21 cards
	public void test06_checkTotalCards(){
		try{
			Game game = setUpMockGame();
			assertTrue(game.allCards.size()==21);
		}catch (IOException e) {

		}
	}

	@Test
	public void test07_checkBoardBoundry(){
		try{
			Game game = setUpMockGame();
			game.playTeam.get(0).setP(new Position(100,100));
			fail("The player can not move outside of the board");
		}catch(IOException e){

		}
	}

//	@Test
//	public void test08_checkInput(){
//		try{
//			Game game = setUpMockGame();
//			game.
//		}
//	}

	public Game setUpMockGame() throws IOException{
		Board board = new Board("map.txt");
		ArrayList<Player> playTeam = new ArrayList<>();

		//player's name
		String name1 = Characters.COLONEL_MUSTARD.name();
		String name2 = Characters.MISS_SCARLETT.name();
		String name3 = Characters.MRS_PEACOCK.name();
		String name4 = Characters.MRS_WHITE.name();
		String name5 = Characters.PROFESSOR_PLUM.name();
		String name6 = Characters.THE_REVEREND_GREEN.name();

		//set up players
		Player p1 = new Player(board.startPoints.get(0),name1,name1.charAt(name1.length()-1), new ArrayList<Card>());
		Player p2 = new Player(board.startPoints.get(1),name2,name2.charAt(name2.length()-1), new ArrayList<Card>());
		Player p3 = new Player(board.startPoints.get(2),name3,name3.charAt(name3.length()-1), new ArrayList<Card>());
		Player p4 = new Player(board.startPoints.get(3),name4,name4.charAt(name4.length()-1), new ArrayList<Card>());
		Player p5 = new Player(board.startPoints.get(4),name5,name5.charAt(name5.length()-1), new ArrayList<Card>());
		Player p6 = new Player(board.startPoints.get(5),name6,name6.charAt(name6.length()-1), new ArrayList<Card>());

		playTeam.add(p1);
		playTeam.add(p2);
		playTeam.add(p3);
		playTeam.add(p4);
		playTeam.add(p5);
		playTeam.add(p6);

		//Creates a new game
		Game game = new Game(board,playTeam);
		return game;
	}

}
