package cluedo.game;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import cluedo.cards.Card;
import cluedo.cards.Characters;
import cluedo.cards.Places;

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
	//player can not move outside the board
	public void test07_checkBoardBoundry(){
		try{
			Game game = setUpMockGame();
			game.playTeam.get(0).setP(new Position(100,100));
			fail("The player can not move outside of the board");
		}catch(IOException e){

		}
	}

	@Test
	//check move south option
	public void test08_ValidMove01(){
		try{
			Game game = setUpMockGame();
			int i = game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			assert game.playTeam.get(0).getP().equals(new Position(1,9));
		}catch(IOException e){

		}
	}

	@Test
	//when player uses "move west", the remaining steps should change
	public void test09_ValidMove02(){
		try{
			Game game = setUpMockGame();
			assert game.executeTheChoice("move west",game.playTeam.get(0), game.board, game)==0;

		}catch(IOException e){

		}
	}

	@Test
	//when player uses "check cards", the remaining steps should not change
	public void test10_ValidMove03(){
		try{
			Game game = setUpMockGame();
			assert game.executeTheChoice("check cards", game.playTeam.get(0), game.board, game)==2;

		}catch(IOException e){

		}
	}

	@Test
	//when player uses "end turn", the remaining steps should not change
	public void test11_ValidMove04(){
		try{
			Game game = setUpMockGame();
			assert game.executeTheChoice("end turn", game.playTeam.get(0), game.board, game)==1;

		}catch(IOException e){

		}
	}

	@Test
	//player can enter a room
	public void test12_ValidMove_05(){
		try{
			Game game = setUpMockGame();
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move west",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move west",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("entry BallRoom",game.playTeam.get(0), game.board, game);
		}catch(IOException e){

		}
	}

	@Test
	//check if the game can remove players
	public void test13_removePlayer(){
		try{
			Game game = setUpMockGame();
			game.lostTheGame(game.playTeam.get(0));
			game.lostTheGame(game.playTeam.get(1));
			game.lostTheGame(game.playTeam.get(2));
			game.lostTheGame(game.playTeam.get(3));
			assert game.playTeam.size()==2;
		}catch(IOException e){

		}
	}
//
	@Test
	//player can not make suggestion when is not in a room
	public void test14_InValidMove(){
		try{
			Game game = setUpMockGame();
			assertFalse( game.playTeam.get(0).isSuggestion());
		}catch(IOException e){

		}
	}

	@Test
	//the player can know another card when entering a room
	public void test15_checkKnowingCards(){
		try{
			Game game = setUpMockGame();
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move west",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move west",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			int i = game.playTeam.get(0).getKnowingCards().size();
			game.executeTheChoice("entry BallRoom",game.playTeam.get(0), game.board, game);
			assert i<game.playTeam.get(0).getKnowingCards().size();
		}catch(IOException e){

		}
	}
	@Test
	//player can enter a room in the knockingDoor position
	public void test16_checkEntryRoom(){
		try{
			Game game = setUpMockGame();
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move west",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move west",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			assert game.checkRoomOption(game.playTeam.get(0), game.board).equals("entry BallRoom");

		}catch(IOException e){

		}
	}


	@Test
	//player can not use stair if there is no stair in a room
	public void test17_checkStairs(){
		try{
			Game game = setUpMockGame();
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move west",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move west",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("entry BallRoom",game.playTeam.get(0), game.board, game);
			assert game.checkStairOption(game.playTeam.get(0), game.board).equals(null);
		}catch(IOException e){

		}
	}


	@Test
	//player can make suggestion in a room
	public void test18_checkSuggestOption(){
		try{
			Game game = setUpMockGame();
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move west",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move west",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("entry BallRoom",game.playTeam.get(0), game.board, game);
			assert game.checkSuggestionOption(game.playTeam.get(0), game.board).equals("make suggestion");
		}catch(IOException e){

		}
	}

	@Test
	//player can exit room in a room
	public void test19_checkExitRoomOption(){
		try{
			Game game = setUpMockGame();
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move west",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move west",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("entry BallRoom",game.playTeam.get(0), game.board, game);
			assert game.checkExitRoomOption(game.playTeam.get(0), game.board).equals("exit room");
		}catch(IOException e){

		}
	}
	@Test
	//check findPlayerLocation method
	public void test20_checkFindPlayerLocation(){
		try{
			Game game = setUpMockGame();
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move west",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move west",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("move south",game.playTeam.get(0), game.board, game);
			game.executeTheChoice("entry BallRoom",game.playTeam.get(0), game.board, game);
			assert game.findPlayerLocation(game.playTeam.get(0), game.board).equals(Places.BALL_ROOM);
		}catch(IOException e){

		}
	}






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
