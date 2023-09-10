package board;

import static util.Constants.GameValues.*;

public class Cell {
	private int playerValue;
	
	private int minmaxValue;
	
	private int anticipatedValue;
	private int[][] locate;
	
	public Cell(int value, int minmax) {
		playerValue = value;
		
		minmaxValue = minmax;
	
		locate = new int[DEPTH][2];
	}

	public int getPlayerValue() {
		return playerValue;
	}

	public void setPlayerValue(int playerValue) {
		this.playerValue = playerValue;
	}

	public int getMinmaxValue() {
		return minmaxValue;
	}

	public void setMinmaxValue(int minmaxValue) {
		this.minmaxValue = minmaxValue;
	}
	
	public void setLocateSingle(int x, int y, int DEPTH, int value) {
		locate[DEPTH][X] = x;
		locate[DEPTH][Y] = y;
		
		anticipatedValue = value + 2;
	}
	
	public int getLocateSingle(int value, int DEPTH) {
		return locate[DEPTH][value];
	}
	
	public void setLocate(int[][] locate) {
		this.locate = locate;
	}
	
	public int[][] getLocate() {
		return locate;
	}
	
	public int getAnticipatedValue() {
		return anticipatedValue;
	}
}
