package board;

import static util.Constants.GameValues.*;
import static util.Constants.MatchConstants.*;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import util.LoadSave;

import static util.Constants.SoundConstants.*;

public class Board {
	//	Value of the board
	static Cell[][] cells;
	
	//	Settings
	private boolean blockBothWay;
	
	//	Images
	private BufferedImage[][] valueImgs;
	
	//	Mouse state
	private int mouseAction = DEFAULT;
	private boolean mousePressed = false;
	
	//	Turn of the players
	private int currentTurn = VALUE_EMPTY;
	private boolean playerTurn;
	private boolean withBot;
	
	//	Locate the highlighted cell
	private int xMouse, yMouse;
	private int xTemp,	yTemp;
	private int xLastMove = -1, yLastMove = -1;
	private boolean inBoard = false;
	
	//	Locate the connected 5 to draw
	private int x1Win, y1Win;
	private int x2Win, y2Win;
	private int dirX, dirY;
	private boolean active;
	private int valueWon;
	
	public Board() {
		initBoard();
		
		initValues();
		
		initImages();
	}

	private void initImages() {
		valueImgs = new BufferedImage[3][3];
		
		BufferedImage temp = LoadSave.getSpriteAtLas(LoadSave.VALUE);
		
		for (int value = 0; value < 3; value++) {
			for (int type = 0; type < 3; type++) {
				valueImgs[type][value] = temp.getSubimage(value * VALUE_SIZE, type * VALUE_SIZE, VALUE_SIZE, VALUE_SIZE);
			}
		}
	}

	private void initBoard() {
		cells = new Cell[BOARD_CONTENT][BOARD_CONTENT];
		
		for (int i = 0; i < BOARD_CONTENT; i++) {
			for (int j = 0; j < BOARD_CONTENT; j++) {
				cells[i][j] = new Cell(VALUE_EMPTY, -1);
			}
		}
	}

	private void initValues() {	
		valueWon = VALUE_EMPTY;
		
		x1Win = -1;
		x2Win = -1;
		y1Win = -1;
		y2Win = -1;
		
		dirX = 0;
		dirY = 0;
		
		active = true;
//		System.out.println("Board Initialized!");
	}
	
	public void update(MouseEvent e) {
		xMouse = (int)((e.getX() - BOARD_X) / CELL_SIZE);
		yMouse = (int)((e.getY() - BOARD_Y) / CELL_SIZE);
		
		if ((xMouse < BOARD_CONTENT && xMouse >= 0) && (yMouse < BOARD_CONTENT && yMouse >= 0)) {
			inBoard = true;
			
//			System.out.println(" x = " + xMouse + " | y = " + yMouse + " | mousePressed = " + mousePressed);
			
			if (mousePressed) {
				mouseAction = PRESSED;
			}
			else {
				mouseAction = HOVER;
			}
		}
		else inBoard = false;
		
//		System.out.println("x = " + xMouse + " | y = " + yMouse);
	}
	
	//	TODO: make the red value shine brighter for HOVERED
	public void draw(Graphics g) {
		if (valueWon != VALUE_EMPTY) {
			for (int x = 0; x < BOARD_CONTENT; x++) {
				for (int y = 0; y < BOARD_CONTENT; y++) {
					drawCell(g, x, y, cells[x][y].getPlayerValue(), PRESSED);
				}
			}
			
			for (int xTemp = x2Win + dirX, yTemp = y2Win + dirY; xTemp != x1Win || yTemp != y1Win; xTemp += dirX, yTemp += dirY) {
				drawCell(g, xTemp, yTemp, valueWon, HOVERED);
				
//				System.out.println("xTemp = " + xTemp + " | yTemp = " + yTemp);
			}
			
//			System.out.println("WON!");
		}
		
		else {
			for (int x = 0; x < BOARD_CONTENT; x++) {
				for (int y = 0; y < BOARD_CONTENT; y++) {
					if (x == xLastMove && y == yLastMove) {
						
						drawCell(g, x, y, cells[x][y].getPlayerValue(), HOVERED);
						
//						System.out.println("xLastMove = " + xLastMove + " | yLastMove = " + yLastMove);
					}
					
					else if ((xMouse == x || yMouse == y) && inBoard) {
						
						drawCell(g, x, y, cells[x][y].getPlayerValue(), mouseAction);
						
					}
					else {
							
						drawCell(g, x, y, cells[x][y].getPlayerValue(), DEFAULT);
						
					}
//					g.setColor(new Color(3, 3, 200));
//					g.fillRect(BOARD_X + CELL_SIZE * x, BOARD_Y + CELL_SIZE * y, CELL_SIZE, CELL_SIZE);
				}
			}
		}
	}
	
	private void drawCell(Graphics g, int x, int y, int value, int type) {
		g.drawImage(valueImgs[value][type], BOARD_X + CELL_SIZE * x, BOARD_Y + CELL_SIZE * y, CELL_SIZE, CELL_SIZE, null);
	
	}
	
	public void turnPlay(int x, int y) {
		if (active) {
			cells[x][y].setPlayerValue(currentTurn);
			
			xLastMove = x;
			yLastMove = y;
			
//			System.out.println("currentTurn = " + (currentTurn == VALUE_O ? "O" : "X"));
			
			checkWin();
			valueProximityUpdate();
			turnPass();
		}
	}
	
	private void turnPass() {
//		System.out.println("Turn Passed!");
		
		if (currentTurn == VALUE_O) currentTurn = VALUE_X;
		else currentTurn = VALUE_O;
		
		if (withBot) playerTurn = !playerTurn;
	}
	
	private void checkWin() {
		for (int i = 0, j = 0; i < 4; i++, j++) {
			if (lineCheck(DIR_X[i], DIR_Y[j])) {
				valueWon = currentTurn;
				
//				System.out.println("x1Win = " + x1Win + " | y1Win = " + y1Win + " | x2Win = " + x2Win + " | y2Win = " + y2Win + " | Value won: " + (valueWon == VALUE_O ? "O" : "X"));
				break;
			}
		}
	}
	
	private boolean lineCheck(int i, int j) {
		int count = 1;
		
		int check = 0;
		
		int xTemp1 = xLastMove + i;
		int yTemp1 = yLastMove + j;
		int xTemp2 = xLastMove - i;
		int yTemp2 = yLastMove - j;
		
		while ((xTemp1 >= 0 && xTemp1 < BOARD_CONTENT) && (yTemp1 >= 0 && yTemp1 < BOARD_CONTENT)) {
			if (cells[xTemp1][yTemp1].getPlayerValue() == currentTurn) count++;
			else if (cells[xTemp1][yTemp1].getPlayerValue() != VALUE_EMPTY) {
				check++;
				
				break;
			}
			else break;
			
			xTemp1 += i;
			yTemp1 += j;
		}
		
		while ((xTemp2 >= 0 && xTemp2 < BOARD_CONTENT) && (yTemp2 >= 0 && yTemp2 < BOARD_CONTENT)) {
			if (cells[xTemp2][yTemp2].getPlayerValue() == currentTurn) count++;
			else if (cells[xTemp2][yTemp2].getPlayerValue() != VALUE_EMPTY) {
				check++;
				
				break;
			}
			else break;
			
			xTemp2 -= i;
			yTemp2 -= j;
		}
		
		if ((count >= 5 && check < 2 && blockBothWay) || (count >= 5 && !blockBothWay)) {
			x1Win = xTemp1;
			y1Win = yTemp1;
			
			x2Win = xTemp2;
			y2Win = yTemp2;
			
			dirX = i;
			dirY = j;
			
			active = false;
			
			return true;
		}
		else return false;
	}
	
	private void valueProximityUpdate() {
		for (int i = xLastMove - 2; i <= xLastMove + 2; i++) {
			for (int j = yLastMove - 2; j <= yLastMove + 2; j++) {
				if ((i >= 0 && i < BOARD_CONTENT) && (j >= 0 && j < BOARD_CONTENT)) {
					if (cells[i][j].getMinmaxValue() == -1) cells[i][j].setMinmaxValue(0);
				}
			}
		}
	}

	public int isWon() {
		return valueWon;
	}

	public void mousePressed(MouseEvent e) {
		if (active && inBoard) {
			mousePressed = true;

			xTemp = xMouse;
			yTemp = yMouse;
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (active) {
			mousePressed = false;
			
			if (xTemp == (int)((e.getX() - BOARD_X) / CELL_SIZE) && yTemp == (int)((e.getY() - BOARD_Y) / CELL_SIZE) && cells[xMouse][yMouse].getPlayerValue() == VALUE_EMPTY) turnPlay(xMouse, yMouse);
		}
	}
	
	public void initFirstTurn(int value) {
		currentTurn = value;
	}
	
	public Cell[][] getCells() {
		return cells;
	}
	
	public void setPlayerTurn(boolean playerTurn) {
		this.playerTurn = playerTurn;
	}
	
	public boolean isPlayerTurn() {
		return playerTurn;
	}
	
	public void setWithBot(boolean withBot) {
		this.withBot = withBot;
	}
	
	public void setBlockBothWay(boolean blockBothWay) {
		this.blockBothWay = blockBothWay;
	}
	
	public void setCurrentTurn(int currentTurn) {
		this.currentTurn = currentTurn;
	}
	
	public int getXLastMove() {
		return xLastMove;
	}
	
	public int getYLastMove() {
		return yLastMove;
	}
	
	public int getCurrentTurn() {
		return currentTurn;
	}
}
