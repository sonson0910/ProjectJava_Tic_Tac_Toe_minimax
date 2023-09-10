package ui;

import static util.LoadSave.getSpriteAtLas;
import static util.Constants.MenuButtonSize.*;
import static util.Constants.MenuButtonSize.MENU_B_HEIGHT_DEFAULT;
import static util.Constants.MenuButtonSize.MENU_B_WIDTH_DEFAULT;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gamestates.GameStates;
import util.LoadSave;

public class MenuButton {
	//	Location
	private int xPos, yPos;
	
	//	Animation
	private int rowIndex, index;
	
	private GameStates gamestate;
	
	private BufferedImage[] imgs;
	
	private boolean mouseOver, mousePressed;
	
	private Rectangle bounds;
	
	public MenuButton(int x, int y, int rowIndex, GameStates gameState) {
		this.xPos = x;
		this.yPos = y;
		this.rowIndex = rowIndex;
		this.gamestate = gameState;
		
		loadImages();
		initBounds();
		
//		System.out.println("x = " + x + " / y = " + y);
	}

	private void initBounds() {
		bounds = new Rectangle(xPos, yPos, MENU_B_WIDTH, MENU_B_HEIGHT);
	}

	private void loadImages() {
		imgs = new BufferedImage[3];
		
		BufferedImage temp = LoadSave.getSpriteAtLas(LoadSave.MENU_BUTTON);
		
		for (int i = 0; i < imgs.length; i++) {
			imgs[i] = temp.getSubimage(i * MENU_B_WIDTH_DEFAULT, rowIndex * MENU_B_HEIGHT_DEFAULT, MENU_B_WIDTH_DEFAULT, MENU_B_HEIGHT_DEFAULT);
		}
	}
	
	public void draw(Graphics g) {
		g.drawImage(imgs[index], xPos, yPos, MENU_B_WIDTH, MENU_B_HEIGHT, null);
	}
	
	public void update() {
		index = 0;		
		
		if (mouseOver) {
			index = 1;
		}	
		
		if (mousePressed) {
			index = 2;
		}
	}
	
	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
	}

	public boolean isMouseOver() {
		return mouseOver;
	}

	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	public boolean isMousePressed() {
		return mousePressed;
	}

	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public GameStates getState() {
		return gamestate;
	}
}
