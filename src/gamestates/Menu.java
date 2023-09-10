package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.GameModel;
import ui.MenuButton;
import util.LoadSave;

import static util.Constants.MenuButtonSize.*;
import static util.Constants.MenuTitleConstants.*;
import static util.Constants.SoundConstants.*;

public class Menu extends State implements StateMethods{
	private MenuButton[] buttons = new MenuButton[3];
	
	private BufferedImage title;
	
	private int titleX = TITLE_STARTING_X;
	private int titleY = TITLE_STARTING_Y;
	private boolean revSpeed = false;
	private int titleSpeed = 0;
	private int titleUpdate = 15;
	private int titleTick = 0;
	
	public Menu(GameModel gameModel) {
		super(gameModel);
		
		loadButtons();
		loadTitle();
	}

	private void loadTitle() {

		title = LoadSave.getSpriteAtLas(LoadSave.MENU_TITLE);
	}

	private void loadButtons() {
		buttons[0] = new MenuButton(MENU_B_X, MENU_B_Y0, 0, GameStates.PREPLAYMENU);
		buttons[1] = new MenuButton(MENU_B_X, MENU_B_Y1, 1, GameStates.OPTIONS);
		buttons[2] = new MenuButton(MENU_B_X, MENU_B_Y2, 2, GameStates.QUIT);
	}

	@Override
	public void update() {
		updateTitle();
		
		for (MenuButton mb : buttons) {
			 mb.update();
		 }
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(title, titleX, titleY, TITLE_WIDTH, TITLE_HEIGHT, null);
		
		for (MenuButton mb : buttons) {
			 mb.draw(g);
		}
	}

	private void updateTitle() {
		titleTick++;
		
		if (titleTick >= titleUpdate) {
			if (revSpeed) titleSpeed++;
			else titleSpeed--;
			
			titleY += titleSpeed;
			
			if (titleSpeed >= TITLE_SPEED || titleSpeed <= -TITLE_SPEED) {
				revSpeed = !revSpeed;
			}
			
			titleTick = 0;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (MenuButton mb : buttons) {
			 if (isIn(e, mb)) {
				 mb.setMousePressed(true);
				 break;
			 }
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for (MenuButton mb : buttons) {
			 if (isIn(e, mb)) {
				 if (mb.isMousePressed()) {
					 this.setGameState(mb.getState());
					 
					 gameModel.getAudioPlayer().playSfx(MENU_CLICK);
				 }
				 break;
			 }
		 }
		
		resetButtons();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for (MenuButton mb : buttons) {
			 mb.setMouseOver(false);
		}
		
		for (MenuButton mb : buttons) {
			 if (isIn(e, mb)) {
				 mb.setMouseOver(true);
				 
				 break;
			 }
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	private void resetButtons() {
		for (MenuButton mb : buttons) {
			 mb.resetBools();
		 }
	}
	
}
