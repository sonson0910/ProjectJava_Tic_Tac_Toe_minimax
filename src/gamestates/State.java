package gamestates;

import java.awt.event.MouseEvent;

import audio.AudioPlayer;
import main.GameModel;
import ui.MenuButton;
import ui.PreplayButton;
import ui.ValueButton;

import static util.Constants.SoundConstants.*;

public class State {
	protected GameModel gameModel;
	
	public State(GameModel gameModel) {
		this.gameModel = gameModel;
	}
	
	protected void setGameState(GameStates state) {
		switch(state) {
		case MENU, PREPLAYMENU, OPTIONS:
			gameModel.getAudioPlayer().playSong(MENU);
		
			break;
		case PLAYING: 
			gameModel.getAudioPlayer().playSong(GAME);
			
			break;
		case QUIT:
			
		default:
			
			break;
		}
		
		GameStates.state = state;
	}
	
	protected boolean isIn(MouseEvent e, MenuButton mb) {
		return mb.getBounds().contains(e.getX(), e.getY());
	}

	protected boolean isIn(MouseEvent e, PreplayButton pb) {
		return pb.getBounds().contains(e.getX(), e.getY());
	}
	
	protected boolean isIn(MouseEvent e, ValueButton vb) {
		return vb.getBounds().contains(e.getX(), e.getY());
	}
	
	public GameModel getGame() {
		return gameModel;
	}
}
