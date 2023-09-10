package gamestates;

import static util.Constants.MenuButtonSize.*;
import static util.Constants.PreplayConstants.*;
import static util.Constants.GameValues.*;
import static util.Constants.SoundConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.GameModel;
import ui.MenuButton;
import ui.PreplayButton;
import ui.ValueButton;
import util.LoadSave;

public class PreplayMenu extends State implements StateMethods{
	private BufferedImage BGImg;
	
	private PreplayButton[] buttons = new PreplayButton[4];
	private MenuButton menuB, playB;
	private ValueButton valueB;
	
	private boolean[] values = new boolean[4];
	
	private int preferedValue = VALUE_O;
	
	public PreplayMenu(GameModel gameModel) {
		super(gameModel);
		
		loadButtons();
		loadImgs();
	}

	private void loadImgs() {
		BGImg = LoadSave.getSpriteAtLas(LoadSave.PREPLAY_BG);
	}

	private void loadButtons() {
		values[BLOCK_BOTHWAY] 	= false;
		values[AGAINST_BOT] 	= true;
		values[RND_FIRST_TURN] 	= false;
		values[PLAYER_1ST_TURN] = true;
		
		buttons[BLOCK_BOTHWAY] 		= new PreplayButton(PREPLAY_B_X, PREPLAY_B_Y1, BUTTON_WIDTH, BUTTON_HEIGHT, values[BLOCK_BOTHWAY]);
		buttons[AGAINST_BOT] 		= new PreplayButton(PREPLAY_B_X, PREPLAY_B_Y2, BUTTON_WIDTH, BUTTON_HEIGHT, values[AGAINST_BOT]);
		buttons[RND_FIRST_TURN] 	= new PreplayButton(PREPLAY_B_X, PREPLAY_B_Y3, BUTTON_WIDTH, BUTTON_HEIGHT, values[RND_FIRST_TURN]);
		buttons[PLAYER_1ST_TURN] 	= new PreplayButton(PREPLAY_B_X, PREPLAY_B_Y4, BUTTON_WIDTH, BUTTON_HEIGHT, values[PLAYER_1ST_TURN]);
		
		playB = new MenuButton(MENU_B_X, PLAY_BUTTON_Y1, 0, GameStates.PLAYING);
		menuB = new MenuButton(MENU_B_X, PLAY_BUTTON_Y2, 2, GameStates.MENU);
		
		valueB = new ValueButton(PREFERED_X, PREFERED_Y, PREFERED_SIZE, PREFERED_SIZE);
	}

	//	TODO: set active for buttons
	@Override
	public void update() {
		for (PreplayButton pb : buttons) {
			 pb.update();
		}
		
		playB.update();
		menuB.update();
		
		valueB.update();
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(BGImg, PREPLAY_BG_X, PREPLAY_BG_Y, PREPLAY_BG_WIDTH, PREPLAY_BG_HEIGHT, null);
		
		for (PreplayButton pb : buttons) {
			pb.draw(g);
		}
		
		playB.draw(g);
		menuB.draw(g);
		
		valueB.draw(g);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (PreplayButton pb : buttons) {
			 if (isIn(e, pb)) {
				 pb.setMousePressed(true);
				 break;
			 }
		}
		if (isIn(e, menuB)) {
			menuB.setMousePressed(true);
		}
		else if (isIn(e, playB)) {
			playB.setMousePressed(true);
		}
		else if (isIn(e, valueB)) {
			valueB.setMousePressed(true);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for (int i = 0; i < 4; i++) {
			 if (isIn(e, buttons[i])) {
				 if (buttons[i].isMousePressed()) {
					 buttons[i].setValue(!buttons[i].isValue());
					 
					 values[i] = !values[i];
					 
					 gameModel.getAudioPlayer().playSfx(MENU_CLICK);
					 
//					 System.out.println(i + " " + values[i]);
					 
					 break;
				 }
			 }
		}
		if (isIn(e, playB)) {
			if (playB.isMousePressed()) {
				gameModel.getPlaying().initTurnCycle();
				
				this.setGameState(playB.getState());
				
				gameModel.getAudioPlayer().playSfx(MENU_CLICK);
			}
		}
		else if (isIn(e, menuB)) {
			if (menuB.isMousePressed()) {
				this.setGameState(menuB.getState());
				
				gameModel.getAudioPlayer().playSfx(MENU_CLICK);
			}
		}
		else if (isIn(e, valueB)) {
			if (valueB.isMousePressed()) {
				if (valueB.getPreferedValue() == VALUE_O) {
					valueB.setPreferedValue(VALUE_X);
				}
				else {
					valueB.setPreferedValue(VALUE_O);
				}
				
//				System.out.println("Prefered Value: " + (PreferedValue == VALUE_O ? "O" : "X"));
				
				gameModel.getAudioPlayer().playSfx(MENU_CLICK);
				
				preferedValue = valueB.getPreferedValue();
			}
		}
		
		resetButtons();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for (PreplayButton pb : buttons) {
			 pb.setMouseOver(false);
		}
		menuB.setMouseOver(false);
		playB.setMouseOver(false);
		valueB.setMouseOver(false);
		
		for (PreplayButton pb : buttons) {
			 if (isIn(e, pb)) {
				 pb.setMouseOver(true);
				 
				 break;
			 }
		}
		if (isIn(e, menuB)) {
			menuB.setMouseOver(true);
		}
		else if (isIn(e, playB)) {
			playB.setMouseOver(true);
		}
		else if (isIn(e, valueB)) {
			valueB.setMouseOver(true);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		
	}

	private void resetButtons() {
		for (PreplayButton pb : buttons) {
			 pb.resetBools();
		}
		menuB.resetBools();
		playB.resetBools();
		valueB.resetBools();
	}

	public boolean isValue(int index) {
		return this.values[index];
	}

	public void setAgainstBot(boolean value, int index) {
		this.values[index] = value;
	}
	
	public int getPreferedValue() {
		return preferedValue;
	}
}
