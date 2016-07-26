package cluedo.game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import cluedo.rooms.BallRoom;
import cluedo.rooms.BilliardRoom;
import cluedo.rooms.Conservatory;
import cluedo.rooms.DiningRoom;
import cluedo.rooms.Door;
import cluedo.rooms.Hall;
import cluedo.rooms.Kitchen;
import cluedo.rooms.KnockDoorPosition;
import cluedo.rooms.Library;
import cluedo.rooms.Lounge;
import cluedo.rooms.Room;
import cluedo.rooms.Stair;
import cluedo.rooms.Study;

public class Board {
	private static final int BOARD_WIDTH = 25;
	private static final int BOARD_HEIGHT = 24;
	public static ArrayList<Room> allRooms = new ArrayList<>();
	public static ArrayList<Position> startPoints = new ArrayList<>();
	private Character board[][] = new Character[BOARD_WIDTH][BOARD_HEIGHT];

	public Board(String map) throws IOException {
		setUpBoard(map);
		displayBoard(board);
		// setup stairs, done
		setUpRoom(board);
		// setup doors,done
		// setup rooms,done
		// setup start points,done
		setUpStartPoint(board);
		// for (Room r : allRooms) {
		// System.out.println(r.toString());
		// }
//		for (Position p : startPoints) {
//			System.out.println(p.toString());
//		}

	}

	/**
	 * fixd position for character
	 * 
	 * @param board2
	 */
	private void setUpStartPoint(Character[][] board2) {
		for (int i = 0; i < board2.length; i++) {
			for (int j = 0; j < board2[0].length; j++) {
				if (board2[i][j] == 'S') {
					startPoints.add(new Position(i, j));
				}
			}
		}
	}

	/**
	 * set up stairs with position class
	 * 
	 * @param board2
	 */
	private void setUpRoom(Character[][] board2) {
		// doors detail per each room
		ArrayList<Door> bDoors = new ArrayList<>();
		ArrayList<Door> dDoors = new ArrayList<>();
		ArrayList<Door> iDoors = new ArrayList<>();
		ArrayList<Door> lDoors = new ArrayList<>();
		ArrayList<Door> hDoors = new ArrayList<>();
		// stairs
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
						allRooms.add(kitchen);
					}
					// setup conservatory room
					else if (type == 'c') {
						Conservatory con = new Conservatory(s2,
								new Door(new Position(i, j), new KnockDoorPosition(new Position(i + 1, j))));
						allRooms.add(con);
					}
					// setup Lounge
					else if (type == 'o') {
						Lounge lou = new Lounge(s3,
								new Door(new Position(i, j), new KnockDoorPosition(new Position(i - 1, j))));
						allRooms.add(lou);
					}
					// setup study room
					else if (type == 's') {
						Study study = new Study(s4,
								new Door(new Position(i, j), new KnockDoorPosition(new Position(i - 1, j))));
						allRooms.add(study);
					} else if (type == 'b') {
						// identify the knock position , search aound + and find
						Position p = findKnockPosition(i, j);
						Door door = new Door(new Position(i, j), new KnockDoorPosition(p));
						bDoors.add(door);
					} else if (type == 'd') {
						Position p = this.findKnockPosition(i, j);
						Door door = new Door(new Position(i, j), new KnockDoorPosition(p));
						dDoors.add(door);
					} else if (type == 'i') {
						Position p = this.findKnockPosition(i, j);
						Door door = new Door(new Position(i, j), new KnockDoorPosition(p));
						iDoors.add(door);
					} else if (type == 'l') {
						Position p = this.findKnockPosition(i, j);
						Door door = new Door(new Position(i, j), new KnockDoorPosition(p));
						lDoors.add(door);
					} else if (type == 'h') {
						Position p = this.findKnockPosition(i, j);
						Door door = new Door(new Position(i, j), new KnockDoorPosition(p));
						hDoors.add(door);
					}
				}
			}
		}
		// Structure rooms
		BallRoom ballroom = new BallRoom(bDoors);
		allRooms.add(ballroom);
		DiningRoom diningroom = new DiningRoom(dDoors);
		allRooms.add(diningroom);
		BilliardRoom billiardroom = new BilliardRoom(iDoors);
		allRooms.add(billiardroom);
		Library library = new Library(lDoors);
		allRooms.add(library);
		Hall hall = new Hall(hDoors);
		allRooms.add(hall);

	}

	/**
	 * find the knock door position for multi-doors room
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private Position findKnockPosition(int x, int y) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[x][y + 1] == ' ') {
					return new Position(x, y + 1);
				}
				if (board[x][y - 1] == ' ') {
					return new Position(x, y - 1);
				}
				if (board[x + 1][y] == ' ') {
					return new Position(x + 1, y);
				}
				if (board[x - 1][y] == ' ') {
					return new Position(x - 1, y);
				}
			}
		}
		return null;
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
