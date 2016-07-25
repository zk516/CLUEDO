package cluedo.game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import cluedo.rooms.Conservatory;
import cluedo.rooms.Door;
import cluedo.rooms.Kitchen;
import cluedo.rooms.KnockDoorPosition;
import cluedo.rooms.Lounge;
import cluedo.rooms.Stair;
import cluedo.rooms.Study;

public class Board {
	private static final int BOARD_WIDTH = 25;
	private static final int BOARD_HEIGHT = 24;
	private Character board[][] = new Character[BOARD_WIDTH][BOARD_HEIGHT];

	public Board(String map) throws IOException {
		setUpBoard(map);
		displayBoard(board);
		// setup stairs
		setUpRoom(board);
		// setup doors
		// setup rooms
		// setup start points
		setUpStartPoint(board);

	}

	/**
	 * fixd position for character
	 * 
	 * @param board2
	 */
	private void setUpStartPoint(Character[][] board2) {
		// TODO Auto-generated method stub

	}

	/**
	 * set up stairs with position class
	 * 
	 * @param board2
	 */
	private void setUpRoom(Character[][] board2) {
		Stair s1 = null, s2 = null, s3 = null, s4 = null;
		for (int i = 0; i < board2.length; i++) {
			for (int j = 0; j < board2[0].length; j++) {
				// Stairs set up
				if (board2[i][j].equals('1')) {
					s1 = new Stair(new Position(i, j), '1');
				} else if (board2[i][j].equals('2')) {
					s2 = new Stair(new Position(i, j), '2');

				} else if (board2[i][j].equals('3')) {
					s3 = new Stair(new Position(i, j), '3');

				} else if (board2[i][j].equals('4')) {
					s4 = new Stair(new Position(i, j), '4');

				}

			}

		}
		// initialize rooms
		for (int i = 0; i < board2.length; i++) {
			for (int j = 0; j < board2[0].length; j++) {
				if (board2[i][j].equals('+')) {
					char type = checkRoomType(i, j);
					// setup kitchen room
					if (type == 'k') {
						Kitchen kitchen = new Kitchen(s1,
								new Door(new Position(i, j), new KnockDoorPosition(new Position(i + 1, j))));
						// System.out.println(
						// kitchen.getD().getP().toString() + " " +
						// kitchen.getD().getKp().getP().toString());
					}
					// setup conservatory room
					else if (type == 'c') {
						Conservatory con = new Conservatory(s2,
								new Door(new Position(i, j), new KnockDoorPosition(new Position(i + 1, j))));
					}
					// setup Lounge
					else if (type == 'o') {
						Lounge lou = new Lounge(s3,
								new Door(new Position(i, j), new KnockDoorPosition(new Position(i - 1, j))));
					}
					// setup study room
					else if (type == 's') {
						Study study = new Study(s4,
								new Door(new Position(i, j), new KnockDoorPosition(new Position(i - 1, j))));
					} else if (type == 'b') {
						// identify the knock position , search aound + and find
						// the empty spot and return
						// case to a door
						// add to the list
						// structure the room
					}
				}
			}
		}
	}

	/**
	 * check the door belongs which room
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	private char checkRoomType(int i, int j) {
		if (!board[i][j + 1].equals(' ')) {
			return board[i][j + 1];
		}
		if (!board[i][j - 1].equals(' ')) {
			return board[i][j - 1];
		}
		if (!board[i + 1][j].equals(' ')) {
			return board[i + 1][j];
		}
		if (!board[i - 1][j].equals(' ')) {
			return board[i - 1][j];
		}
		return 0;
	}

	/**
	 * Draw the board on the screen
	 * 
	 * @param board2
	 */
	private void displayBoard(Character[][] board2) {
		for (int i = 0; i < board2.length; i++) {
			for (int j = 0; j < board2[0].length; j++) {
				System.out.print(board2[i][j]);
			}
			System.out.println();
		}
	}

	/**
	 * Set up the board
	 * 
	 * @param map
	 * @throws IOException
	 */
	private void setUpBoard(String map) throws IOException {
		try {
			FileReader file = new FileReader(map);
			BufferedReader buff = new BufferedReader(file);
			String line = buff.readLine();

			int row = 0;
			int col = 0;
			while (row < BOARD_WIDTH) {
				char token[] = line.toCharArray();
				for (Character c : token) {
					if (col < BOARD_HEIGHT)
						board[row][col++] = c;
					else
						break;
				}

				line = buff.readLine();
				row++;
				col = 0;

			}

		} catch (GameError e) {
			System.out.println("Failed setup Board" + e);
		}
	}
}
