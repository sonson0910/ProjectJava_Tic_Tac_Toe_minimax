package ui;

import static util.Constants.UrmVolumeButtons.*;
import static util.Constants.PANEL_HEIGHT;
import static util.Constants.PANEL_WIDTH;
import static util.Constants.PlayFinishedConstants.*;
import static util.Constants.SoundConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.GameStates;
import gamestates.Playing;
import util.LoadSave;

public class PlayFinished {
	private BufferedImage backgroundImg;
	
	private UrmButton menuB, replayB;
	
	private Playing playing;
	
	public PlayFinished(Playing playing) {
		this.playing = playing;
		
		loadBackground();
		createUrmButton();
	}

	private void loadBackground() {
		backgroundImg = LoadSave.getSpriteAtLas(LoadSave.WIN_BG);
	}

	private void createUrmButton() {
		replayB			= new UrmButton(PF_BUTTON_X, PF_REPLAY_Y, URM_B_WIDTH, URM_B_HEIGHT, REPLAY_INDEX);
		menuB			= new UrmButton(PF_BUTTON_X, PF_MENU_Y, URM_B_WIDTH, URM_B_HEIGHT, MENU_INDEX);
	}
	
	public void update() {
		// U(npause)R(eplay)M(enu) buttons
		menuB.update();
		replayB.update();
	}
	
	//	TODO: draw the winning value + make the required sprites
	public void draw(Graphics g, int value) {	
		// Background
		g.setColor(new Color(0, 0, 0, 180));
		g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		g.drawImage(backgroundImg, PF_BG_X, PF_BG_Y, PF_BG_WIDTH, PF_BG_HEIGHT, null);
		
//		System.out.println("Value won: " + (value == VALUE_O ? "O" : "X"));
		
		// U(npause)R(eplay)M(enu) buttons
		menuB.draw(g);
		replayB.draw(g);
	}
	
	public void mousePressed(MouseEvent e) {
		if (isIn(e, menuB)) {
			menuB.setMousePressed(!menuB.isMousePressed());
		}
		else if (isIn(e, replayB)) {
			replayB.setMousePressed(!replayB.isMousePressed());
		}
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
		
		menuB.resetBools();
		replayB.resetBools();
	}

	public void mouseMoved(MouseEvent e) {
		menuB.setMouseOver(false);
		replayB.setMouseOver(false);
		
		if (isIn(e, menuB)) {
			menuB.setMouseOver(true);
		}
		else if (isIn(e, replayB)) {
			replayB.setMouseOver(true);
		}
	}
	
	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
}
