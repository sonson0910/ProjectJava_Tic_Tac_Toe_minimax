package gamestates;

import static util.Constants.PANEL_HEIGHT;
import static util.Constants.PANEL_WIDTH;
import static util.Constants.GameValues.*;
import static util.Constants.MatchConstants.*;
import static util.Constants.SoundConstants.*;
import static util.Constants.PreplayConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

import board.Board;
import board.Bot;
import main.GameModel;
import ui.PauseOverlay;
import ui.PlayFinished;

public class Playing extends State implements StateMethods{
	private Board board;
	
	private PauseOverlay pauseOverlay;
	private PlayFinished playFinished;
	private int winCountdown = 240;
	private boolean paused;
	
	private Bot bot;
	private Random rnd;
	private boolean playerTurn;
	private boolean withBot;
	
	private int firstMove;
	private int currTurn = VALUE_EMPTY;
	
	public Playing(GameModel gameModel) {
		super(gameModel);
		
		initClasses();
	}

	private void initClasses() {
		pauseOverlay = new PauseOverlay(this);
		playFinished = new PlayFinished(this);
		
		board = new Board();
		rnd = new Random();
	}

	public void initTurnCycle() {
		//	Initialize first turn
		if (gameModel.getPreplayMenu().isValue(RND_FIRST_TURN)) {
				playerTurn = rnd.nextBoolean();	
		}
		else playerTurn = gameModel.getPreplayMenu().isValue(PLAYER_1ST_TURN);
			
		//	Initialize values
		if (playerTurn) firstMove = gameModel.getPreplayMenu().getPreferedValue();
		else {
			if (gameModel.getPreplayMenu().getPreferedValue() == VALUE_O) firstMove = VALUE_X;
			else firstMove = VALUE_O;
		}
		
		withBot = gameModel.getPreplayMenu().isValue(AGAINST_BOT);
		initBoard();
		
		if (withBot) {
			initBot();	
		}
		else {
			playerTurn = true;
		}
	}
	
	private void initBoard() {
		board.initFirstTurn(firstMove);
		board.setPlayerTurn(playerTurn);
		board.setWithBot(withBot);
		board.setBlockBothWay(gameModel.getPreplayMenu().isValue(BLOCK_BOTHWAY));
		board.setCurrentTurn(playerTurn ? gameModel.getPreplayMenu().getPreferedValue() : (gameModel.getPreplayMenu().getPreferedValue() == VALUE_O ? VALUE_X : VALUE_O));
	}

	private void initBot() {
		if (!playerTurn) bot = new Bot(firstMove, firstMove == VALUE_O ? VALUE_X : VALUE_O);
		else bot = new Bot(firstMove == VALUE_O ? VALUE_X : VALUE_O, firstMove);
	
		bot.setBlockBothWay(gameModel.getPreplayMenu().isValue(BLOCK_BOTHWAY));
	}
	
	public void update() {
		if (paused) {
			pauseOverlay.update();
		}
		else if (board.isWon() != VALUE_EMPTY) {			
			if (winCountdown <= 0) {
				playFinished.update();
			}
			else if (winCountdown == 240) {
				gameModel.getAudioPlayer().playSfx(GAME_WON);
			}
				
			winCountdown--;
		}
		else {
			if (withBot) updateWithBot();
			
			else updateWithoutBot();
		}
	}

	private void updateWithoutBot() {
		if (currTurn != board.getCurrentTurn())
		{
			gameModel.getAudioPlayer().playSfx(PLAYER_MOVE);
			
			currTurn = board.getCurrentTurn();
		}
	}

	private void updateWithBot() {
//		System.out.println("playerTurn = " + playerTurn + 
//				" | board.playerTurn = " + board.isPlayerTurn() + 
//				" | playerFirst = " + playerFirst + 
//				" | botActive = " + bot.isBotActive() + 
//				" | bestMoveReady = " + bot.isBestMoveReady());
		
		if (!playerTurn) {
			if (!bot.isBestMoveReady() && board.isWon() == VALUE_EMPTY) {
				// bot.setCells(board.getCells());
				
				bot.getBestMove();
				
//				System.out.println("BOT PASSED!");
			}
			
			else if (bot.isBestMoveReady()) {
				board.turnPlay(bot.getXBestMove(), bot.getYBestMove());
				
				bot.setBestMoveReady(false);
				
				board.setPlayerTurn(true);
				playerTurn = true;
				
//				System.out.println("BOT PLAYED!");
				
				gameModel.getAudioPlayer().playSfx(ENEMY_MOVE);
			}
		}
		else {
			if (!board.isPlayerTurn()) {
				gameModel.getAudioPlayer().playSfx(PLAYER_MOVE);
				
				playerTurn = false;
			}
		}
	}

	public void draw(Graphics g) {
		//	caro gaming
		board.draw(g);

		if (paused) {
			g.setColor(new Color(0, 0, 0, 90));
			g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
			
			pauseOverlay.draw(g);
		}
		else if (board.isWon() != VALUE_EMPTY) {
			if (winCountdown <= 0) {
				playFinished.draw(g, board.isWon());
			}
			
		}
		else if (!board.isPlayerTurn()) {
			g.setColor(new Color(0, 0, 0, 180));
			g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
			
			g.setColor(Color.white);
			g.drawString("BOT TURN", PANEL_WIDTH / 2 - 30, PANEL_HEIGHT / 5);;
		}
	}

	public void resetAll() {
		board = new Board();
		
		firstMove = VALUE_EMPTY;
		
		winCountdown = 240;
		
		unpauseGame();
	}

	public void unpauseGame() {
		paused = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (paused) {
			pauseOverlay.mouseDragged(e);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (paused) {
			pauseOverlay.mousePressed(e);
		}
		else if (winCountdown <= 0) {
			playFinished.mousePressed(e);
		}
		else if (playerTurn) {
			board.mousePressed(e);
			
			board.update(e);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (paused) {
			pauseOverlay.mouseReleased(e);
		}
		else if (winCountdown <= 0) {
			playFinished.mouseReleased(e);
		}
		else if (playerTurn) {
			board.mouseReleased(e);
			
			board.update(e);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (paused) {
			pauseOverlay.mouseMoved(e);
		}
		else if (winCountdown <= 0) {
			playFinished.mouseMoved(e);
		}
		else if (playerTurn) board.update(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			paused = !paused;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	public void setPlayerTurn(boolean turn) {
		playerTurn = turn;
	}
}
