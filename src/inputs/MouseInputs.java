package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import gamestates.GameStates;
import main.GameModel;

public class MouseInputs implements MouseListener, MouseMotionListener{
	private GameModel gameModel;
	
	public MouseInputs(GameModel gameModel) {
		this.gameModel = gameModel;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		switch (GameStates.state) {
		case PLAYING:
			gameModel.getPlaying().mouseDragged(e);
			break;
			
		case OPTIONS:
			gameModel.getOptions().mouseDragged(e);
			break;
			
		default:
			break;
		
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		switch (GameStates.state) {
		case MENU:
			gameModel.getMenu().mouseMoved(e);
			break;
			
		case PLAYING:
			gameModel.getPlaying().mouseMoved(e);
			break;
			
		case PREPLAYMENU:
			gameModel.getPreplayMenu().mouseMoved(e);
			break;
			
		case OPTIONS:
			gameModel.getOptions().mouseMoved(e);
			break;
		
		case QUIT:
			break;
		default:
			break;
		 }
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch (GameStates.state) {
		case MENU:
			gameModel.getMenu().mouseClicked(e);
			break;
			
		case PLAYING:
			gameModel.getPlaying().mouseClicked(e);
			break;
			
		case PREPLAYMENU:
			gameModel.getPreplayMenu().mouseClicked(e);
			break;
			
		case OPTIONS:
			gameModel.getOptions().mouseClicked(e);
			break;
		
		case QUIT:
			break;
		default:
			break;
		 }
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (GameStates.state) {
		case MENU:
			gameModel.getMenu().mousePressed(e);
			break;
			
		case PLAYING:
			gameModel.getPlaying().mousePressed(e);
			break;
			
		case PREPLAYMENU:
			gameModel.getPreplayMenu().mousePressed(e);
			break;
			
		case OPTIONS:
			gameModel.getOptions().mousePressed(e);
			break;
		
		case QUIT:
			break;
		default:
			break;
		 }
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch (GameStates.state) {
		case MENU:
			gameModel.getMenu().mouseReleased(e);
			break;
			
		case PLAYING:
			gameModel.getPlaying().mouseReleased(e);
			break;
			
		case PREPLAYMENU:
			gameModel.getPreplayMenu().mouseReleased(e);
			break;
			
		case OPTIONS:
			gameModel.getOptions().mouseReleased(e);
			break;
		
		case QUIT:
			break;
		default:
			break;
		 }
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

}
