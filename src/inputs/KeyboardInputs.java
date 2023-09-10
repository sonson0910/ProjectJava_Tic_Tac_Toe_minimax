package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gamestates.GameStates;
import main.GameModel;

public class KeyboardInputs implements KeyListener{
	private GameModel gameModel;
	
	public KeyboardInputs(GameModel gameModel) {
		this.gameModel = gameModel;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (GameStates.state) {
		case MENU:
			gameModel.getMenu().keyPressed(e);
			break;
			
		case PLAYING:
			gameModel.getPlaying().keyPressed(e);
			break;
			
		case PREPLAYMENU:
			gameModel.getPreplayMenu().keyPressed(e);
			break;
			
		case OPTIONS:
			gameModel.getOptions().keyPressed(e);
			break;
			
		case QUIT:
			break;
		default:
			break;
		 }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (GameStates.state) {
		case MENU:
			gameModel.getMenu().keyReleased(e);
			break;
			
		case PLAYING:
			gameModel.getPlaying().keyReleased(e);
			break;
			
		case OPTIONS:
			gameModel.getOptions().keyReleased(e);
			break;
			
		case PREPLAYMENU:
			gameModel.getPreplayMenu().keyReleased(e);
			break;
			
		case QUIT:
			break;
		default:
			break;
		 }
	}

}
