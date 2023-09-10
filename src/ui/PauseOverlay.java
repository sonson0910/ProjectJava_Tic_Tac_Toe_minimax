package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.GameStates;
import gamestates.Playing;
import util.LoadSave;

import static util.Constants.UrmVolumeButtons.*;
import static util.Constants.SoundConstants.*;

public class PauseOverlay {
	private BufferedImage backgroundImg;
	
	private UrmButton 		menuB, replayB, unpauseB;
	private AudioOptions 	audioOptions;
	
	private Playing playing;
	
	public PauseOverlay(Playing playing) {
		this.playing = playing;
		
		audioOptions = playing.getGame().getAudioOptions();
		
		loadBackground();
		createUrmButton();
	}

	private void createUrmButton() {
		unpauseB		= new UrmButton(URM_X, UNPAUSE_Y, URM_B_WIDTH, URM_B_HEIGHT, UNPAUSE_INDEX);
		replayB			= new UrmButton(URM_X, REPLAY_Y, URM_B_WIDTH, URM_B_HEIGHT, REPLAY_INDEX);
		menuB			= new UrmButton(URM_X, MENU_Y, URM_B_WIDTH, URM_B_HEIGHT, MENU_INDEX);
	}

	private void loadBackground() {
		backgroundImg = LoadSave.getSpriteAtLas(LoadSave.SETTINGS_BACKGROUND);
	}

	public void update() {
		// U(npause)R(eplay)M(enu) buttons
		menuB.update();
		replayB.update();
		unpauseB.update();
		
		audioOptions.update();
	}
	
	public void draw(Graphics g) {	
		// Background
		g.drawImage(backgroundImg, BG_X, BG_Y, BG_WIDTH, BG_HEIGHT, null);
		
		// U(npause)R(eplay)M(enu) buttons
		menuB.draw(g);
		replayB.draw(g);
		unpauseB.draw(g);
		
		audioOptions.draw(g);
	}
	
	public void mouseDragged(MouseEvent e) {
		audioOptions.mouseDragged(e);
	}

	public void mousePressed(MouseEvent e) {
		if (isIn(e, menuB)) {
			menuB.setMousePressed(!menuB.isMousePressed());
		}
		else if (isIn(e, replayB)) {
			replayB.setMousePressed(!replayB.isMousePressed());
		}
		else if (isIn(e, unpauseB)) {
			unpauseB.setMousePressed(!unpauseB.isMousePressed());
		}
		else audioOptions.mousePressed(e);
	}

	public void mouseReleased(MouseEvent e) {
		if (isIn(e, menuB)) {
			if (menuB.isMousePressed()) {
				playing.getGame().getAudioPlayer().playSfx(MENU_CLICK);
				playing.getGame().getAudioPlayer().playSong(MENU);
				
				GameStates.state = GameStates.MENU;
				
				playing.resetAll();
				playing.unpauseGame();
			};
		}
		else if (isIn(e, replayB)) {
			if (replayB.isMousePressed()) {
				playing.getGame().getAudioPlayer().playSfx(MENU_CLICK);
				
				playing.resetAll();
				playing.initTurnCycle();
				playing.unpauseGame();
			};
		}
		else if (isIn(e, unpauseB)) {
			playing.getGame().getAudioPlayer().playSfx(MENU_CLICK);
			
			if (unpauseB.isMousePressed()) {
				playing.unpauseGame();
			};
		}
		else {
			audioOptions.mouseReleased(e);
		}
		
		menuB.resetBools();
		replayB.resetBools();
		unpauseB.resetBools();
	}

	public void mouseMoved(MouseEvent e) {
		menuB.setMouseOver(false);
		replayB.setMouseOver(false);	
		unpauseB.setMouseOver(false);
		
		if (isIn(e, menuB)) {
			menuB.setMouseOver(true);
		}
		else if (isIn(e, replayB)) {
			replayB.setMouseOver(true);
		}
		else if (isIn(e, unpauseB)) {
			unpauseB.setMouseOver(true);
		}
		else audioOptions.mouseMoved(e);
	}
	
	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
}