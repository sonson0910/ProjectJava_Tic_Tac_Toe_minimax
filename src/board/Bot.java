package board;

import static util.Constants.GameValues.*;
import static util.Constants.MatchConstants.*;

import java.util.Random;

public class Bot{	
//	private Cell[][] cells;
	
	private boolean bestMoveReady = false;
	
	private boolean blockBothWay;
	
	private int botValue;
	private int playerValue;
	private int i2 = 8, j2 = 8;
	
	private int xBestMove = BOARD_CONTENT / 2, yBestMove = BOARD_CONTENT / 2;
	
	public Bot(int botValue, int playerValue) {
		this.botValue = botValue;
		this.playerValue = playerValue;
		
		Board.cells = new Cell[BOARD_CONTENT][BOARD_CONTENT];
		for (int i = 0; i < BOARD_CONTENT; i++) {
			for (int j = 0; j < BOARD_CONTENT; j++) {
				Board.cells[i][j] = new Cell(VALUE_EMPTY, -1);
			}
		}
	}
    
	public void getBestMove(){
    	//	TODO: fix minimax
		int bestValue = Integer.MIN_VALUE;
		
		for(int i = 0; i < BOARD_CONTENT; i++) {
			for (int j = 0; j < BOARD_CONTENT; j++) {
				Cell cell = Board.cells[i][j];
				if (cell.equals(VALUE_EMPTY)) {
					cell.setPlayerValue(botValue);
					int moveValue = minimax(DEPTH, i, j);
					if (moveValue > bestValue) {
						xBestMove = i;
						yBestMove = j;
						bestValue = moveValue;
					}
					cell.setPlayerValue(VALUE_EMPTY);
				}
			}
		}
        
//        int moveValue = minimax(DEPTH, 0, 0);  
//
//	      xBestMove = i2;
//	      yBestMove = j2;
 
        bestMoveReady = true;
    }
    
	public int minimax(int depth, int i, int j) {
        return alphabeta(depth, i, j, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
    }
    
    public int alphabeta(int depth, int i1, int j1, int alpha, int beta, int isMax) {
 
    	
        if (depth == 0) {
            return calcCell(i1, j1, isMax);
        }
        
        if (isMax == 1) {

            int highestVal = Integer.MIN_VALUE;
            
            for (int i = 0; i < BOARD_CONTENT; i++) {
                for (int j = 0; j < BOARD_CONTENT; j++) {
                    Cell cell = Board.cells[i][j];
                    
                    if (cell.getPlayerValue() == VALUE_EMPTY ) {
                    	cell.setPlayerValue(botValue);
                        
                        int alphabeta = alphabeta(depth - 1, i, j, alpha, beta, 0);

                        if (highestVal < alphabeta) {
                        	highestVal = alphabeta;

//                        	i2 = i;
//                        	j2 = j;
                        	cell.setLocateSingle(i, j, DEPTH - depth, playerValue);
                        }
                        
                        alpha = Math.max(alpha, highestVal);
                        
                        cell.setPlayerValue(VALUE_EMPTY);
                        
                        if (alpha >= beta) {

                            return highestVal;
                        }
                    }
                }
            }
            return highestVal;
        }
        else {
            int lowestVal = Integer.MAX_VALUE;
            
            for (int i = 0; i < BOARD_CONTENT; i++) {
                for (int j = 0; j < BOARD_CONTENT; j++) {
                    Cell cell = Board.cells[i][j];
                    
                    if (cell.getPlayerValue() == VALUE_EMPTY ) {
                        cell.setPlayerValue(playerValue);

                        int alphabeta = alphabeta(depth - 1, i, j, alpha, beta, 1);

                        if (lowestVal > alphabeta) {
                        	lowestVal = alphabeta;

//                        	i2 = i;
//                        	j2 = j;
                        	cell.setLocateSingle(i, j, DEPTH - depth, botValue);
                        }
                        
                        beta = Math.min(beta, lowestVal);
                        
                        cell.setPlayerValue(VALUE_EMPTY);
                        
                        if (beta <= alpha) {
                            return lowestVal;
                        }
                    }
                }
            }
            return lowestVal;
        }
    }
	
	public int calcCell(int row, int col, int isMax) {
		int calcDef = 0;
		int calcAtk = 0;
		
		int calcPoint = (isMax == 1) ? -1 : 1;
		
		int calcLocation = 
						(row < BOARD_CONTENT / 2 
						? (BOARD_CONTENT / 2) - Math.abs(BOARD_CONTENT / 2 - row) 
						: (BOARD_CONTENT - 1 - row))
						+ 
						(col < BOARD_CONTENT / 2 
						? (BOARD_CONTENT / 2) - Math.abs(BOARD_CONTENT / 2 - col) 
						: (BOARD_CONTENT - 1 - col));
		
		int own = (isMax == 1) ? playerValue : botValue;
		int foe  = (isMax == 0) ? playerValue : botValue;
		
		for (int i = 0; i < 4; i++) {
			int temp = calcDir(row, col, DIR_X[i], DIR_Y[i], own, foe);
			
			if (temp >= 0) calcAtk += temp;
			else calcDef += Math.abs(temp);
		}
//		System.out.println("CalcDef = " + calcDef + " | CalcAtk = " + calcAtk + " | CalcLocation = " + calcLocation);
		
		return calcPoint * (calcDef + calcAtk + calcLocation);
    }
	
	private int calcDir(int row, int col, int dirX, int dirY, int own, int foe) {
		int distance1 = 1,  distance2 = 1;
		int distanceTemp1 = 0, distanceTemp2 = 0;
		int determine1 = 0, determine2 = 0;
		int calcPoint1 = 0, calcPoint2 = 0;
		boolean blocked1 = false, blocked2 = false;
		
		for (int i = row + dirX, j = col + dirY; 
				i < BOARD_CONTENT && i >= 0 && j < BOARD_CONTENT && j >= 0; 
				i += dirX, j += dirY) {
			
			if (Board.cells[i][j].getPlayerValue() == VALUE_EMPTY) {
				distanceTemp1++;
			}
			else if (Board.cells[i][j].getPlayerValue() == own) {
				if (calcPoint1 == 0) {
					calcPoint1 = 1;
					
					determine1 = 1;
					distance1 += distanceTemp1;
					distanceTemp1 = 0;
				}
				
				else if (calcPoint1 > 0) calcPoint1 *= 100;
				
				else {
					blocked1 = true;
					
					break;
				}
			}
			else {
				if (calcPoint1 == 0) {
					calcPoint1 = -1;
					
					determine1 = -1;
					distance1 += distanceTemp1;
					distanceTemp1 = 0;
				}
				
				else if (calcPoint1 < 0) calcPoint1 *= 95;
				
				else {
					blocked1 = true;
					
					break;
				}
			}
		}
		
		for (int i = row - dirX, j = col - dirY; 
				i < BOARD_CONTENT && i >= 0 && j < BOARD_CONTENT &&	 j >= 0; 
				i -= dirX, j -= dirY) {
			
			if (Board.cells[i][j].getPlayerValue() == VALUE_EMPTY) {
				distanceTemp2++;
			}
			else if (Board.cells[i][j].getPlayerValue() == own) {
				if (calcPoint2 == 0) {
					calcPoint2 = 1;
					
					determine2 = 1;
					
					distance2 += distanceTemp2;
					distanceTemp2 = 0;
				}
				else if (calcPoint2 > 0) calcPoint2 *= 100;
				
				else {
					blocked2 = true;
					
					break;
				}
			}
			else {
				if (calcPoint2 == 0) {
					calcPoint2 = -1;
					
					determine2 = -1;
					distance2 += distanceTemp2;
					distanceTemp2 = 0;
				}
				else if (calcPoint2 < 0) calcPoint2 *= 95;
				
				else {
					blocked2 = true;
					
					break;
				}
			}
		}
		
		if (((calcPoint1 >= 10000 && distance2 != 0) || (distance1 != 0 && calcPoint1 >= 10000)) && blockBothWay) {
			return 1000000;
		}
		else {
			if (determine1 != determine2) {
				calcPoint1 = distance1 > 3 ? 0 : (int)(calcPoint1 * (1 / distance1));
				
				calcPoint2 = distance2 > 3 ? 0 : (int)(calcPoint2 * (1 / distance2));
				
				return Math.abs(calcPoint1) + Math.abs(calcPoint2);
			}
			else return ((distance1 + distance2) > 2 ? 0 : (int)((calcPoint1 * calcPoint2) * (1 / (distance1 + distance2))));
		}
	}
	
	// public void setCells(Cell[][] cells) {
	// 	for (int i = 0; i < BOARD_CONTENT; i++) {
	// 		for (int j = 0; j < BOARD_CONTENT; j++) {
	// 			this.cells[i][j] = new Cell(cells[i][j].getPlayerValue(), cells[i][j].getMinmaxValue());
	// 		}
	// 	}
	// }
	
	public void setBestMoveReady(boolean value) {
		bestMoveReady = value;
	}
	
	public boolean isBestMoveReady() {
		return bestMoveReady;
	}
	
	public void setBlockBothWay(boolean blockBothWay) {
		this.blockBothWay = blockBothWay;
	}
	
	public int getXBestMove() {
		return xBestMove;
	}
	
	public int getYBestMove() {
		return yBestMove;
	}
}
