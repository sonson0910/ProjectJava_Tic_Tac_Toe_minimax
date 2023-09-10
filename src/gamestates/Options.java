package gamestates;

import static util.Constants.MenuButtonSize.*;
import static util.Constants.SoundConstants.MENU_CLICK;
import static util.Constants.UrmVolumeButtons.*;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import main.GameModel;
import ui.AudioOptions;
import ui.MenuButton;
import util.LoadSave;

public class Options extends State implements StateMethods{
	private AudioOptions audioOptions;
	
	private BufferedImage optionsBGImg;
	private MenuButton menuB;
	
	public Options(GameModel gameModel) {
		super(gameModel);
		
		loadImgs();
		loadButton();
	}

	private void loadButton() {
		menuB = new MenuButton(MENU_B_X, MENU_B_Y2, 0, GameStates.MENU);
		
		audioOptions = gameModel.getAudioOptions();
	}

	private void loadImgs() {
		optionsBGImg = LoadSave.getSpriteAtLas(LoadSave.SETTINGS_BACKGROUND);
	}

	@Override
	public void update() {
		menuB.update();
		
		audioOptions.update();
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(optionsBGImg, BG_X, BG_Y, BG_WIDTH, BG_HEIGHT, null);
		
		menuB.draw(g);
		
		audioOptions.draw(g);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		audioOptions.mouseDragged(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (isIn(e, menuB)) {
			menuB.setMousePressed(true);
		}
		else audioOptions.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (isIn(e, menuB)) {
			if (menuB.isMousePressed()) {
				this.setGameState(menuB.getState());
				
				gameModel.getAudioPlayer().playSfx(MENU_CLICK);
			}
		}
		else audioOptions.mouseReleased(e);
		
		menuB.resetBools();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		menuB.setMouseOver(false);
		
		if (isIn(e, menuB)) {
			menuB.setMouseOver(true);
		}
		else audioOptions.mouseMoved(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		
	}
	
}
