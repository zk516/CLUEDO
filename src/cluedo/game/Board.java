package cluedo.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
	public final static int BOARD_WIDTH = 25;
	public final static int BOARD_HEIGHT = 24;
	public ArrayList<Room> allRooms;
	public static ArrayList<Position> startPoints;
	public Character outputBoard[][];

	public Board(String map) throws IOException {
		outputBoard = new Character[BOARD_WIDTH][BOARD_HEIGHT];
		allRooms = new ArrayList<>();
		startPoints = new ArrayList<>();
		setUpBoard(map);
		setUpRoom(outputBoard);
		setUpStartPoint(outputBoard);
	}

	/**
	 * detect exit doors for user to choose from
	 *
	 * @param c
	 * @return
	 */
	public ArrayList<Door> getExitDoors(char c) {
		ArrayList<Door> list = new ArrayList<>();
		for (Room r : allRooms) {
			if (c == 'b' && r instanceof BallRoom) {
				BallRoom b = (BallRoom) r;
				list = b.getDoors();
				return list;
			}
			if (c == 'i' && r instanceof BilliardRoom) {
				BilliardRoom i = (BilliardRoom) r;
				list = i.getDoors();
				return list;
			}
			if (c == 'c' && r instanceof Conservatory) {
				Conservatory co = (Conservatory) r;
				list.add(co.getD());
				return list;
			}
			if (c == 'd' && r instanceof DiningRoom) {
				DiningRoom d = (DiningRoom) r;
				list = d.getDoors();
				return list;
			}
			if (c == 'h' && r instanceof Hall) {
				Hall h = (Hall) r;
				list = h.getDoors();
				return list;
			}
			if (c == 'k' && r instanceof Kitchen) {
				Kitchen k = (Kitchen) r;
				list.add(k.getD());
				return list;
			}
			if (c == 'l' && r instanceof Library) {
				Library l = (Library) r;
				list = l.getDoors();
				return list;
			}
			if (c == 'o' && r instanceof Lounge) {
				Lounge o = (Lounge) r;
				list.add(o.getD());
				return list;
			}
			if (c == 's' && r instanceof Study) {
				Study s = (Study) r;
				list.add(s.getD());
				return list;
			}
		}
		return null;
	}

	/**
	 * fixd position for character
	 *
	 * @param board2
	 */
	private void setUpStartPoint(Character[][] board2) {
		ArrayList<Position> temp = new ArrayList<Position>();

		for (int i = 0; i < board2.length; i++) {
			for (int j = 0; j < board2[0].length; j++) {
				if (board2[i][j] == '*') {
					temp.add(new Position(i, j));
				}
			}
		}
		// set fixed position for the player
		startPoints.add(temp.get(0));
		startPoints.add(temp.get(1));
		startPoints.add(temp.get(2));
		startPoints.add(temp.get(4));
		startPoints.add(temp.get(5));
		startPoints.add(temp.get(3));
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
		for (int i = 0; i < outputBoard.length; i++) {
			for (int j = 0; j < outputBoard[0].length; j++) {
				if (outputBoard[x][y + 1] == ' ') {
					return new Position(x, y + 1);
				}
				if (outputBoard[x][y - 1] == ' ') {
					return new Position(x, y - 1);
				}
				if (outputBoard[x + 1][y] == ' ') {
					return new Position(x + 1, y);
				}
				if (outputBoard[x - 1][y] == ' ') {
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
		if (outputBoard[i + 1][j] != ' ') {
			return outputBoard[i + 1][j];
		} else if (outputBoard[i - 1][j] != ' ') {
			return outputBoard[i - 1][j];
		} else if (outputBoard[i][j + 1] != ' ') {
			return outputBoard[i][j + 1];
		} else if (outputBoard[i][j - 1] != ' ') {
			return outputBoard[i][j - 1];
		}
		return 0;
	}

	/**
	 * Draw the board on the screen
	 *
	 * @param board2
	 */
	public void displayBoard() {
		for (int i = 0; i < outputBoard.length; i++) {
			for (int j = 0; j < outputBoard[0].length; j++) {
				System.out.print(outputBoard[i][j]);
			}
			System.out.println(); // start next line
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
						outputBoard[row][col++] = c;
					else
						break;
				}

				line = buff.readLine();
				row++;
				col = 0;

			}
			buff.close();
		} catch (GameError e) {
			System.out.println("Failed setup Board" + e);
		}
	}

	/**
	 * how many directions current player can move to
	 * 
	 * @param player
	 * @return
	 */
	public ArrayList<String> movable(Player player) {
		ArrayList<String> directions = new ArrayList<>();

		if (player.getP().getX() < Board.BOARD_WIDTH - 1) {
			if (this.outputBoard[player.getP().getX() + 1][player.getP().getY()] == ' ') {
				directions.add("move south");
			}
		}
		if (player.getP().getX() > 0) {
			if (this.outputBoard[player.getP().getX() - 1][player.getP().getY()] == ' ') {
				directions.add("move north");
			}
		}
		if (player.getP().getY() < Board.BOARD_HEIGHT - 1) {
			if (this.outputBoard[player.getP().getX()][player.getP().getY() + 1] == ' ') {
				directions.add("move east");
			}
		}
		if (player.getP().getY() > 0) {
			if (this.outputBoard[player.getP().getX()][player.getP().getY() - 1] == ' ') {
				directions.add("move west");
			}
		}

		return directions;
	}
}
