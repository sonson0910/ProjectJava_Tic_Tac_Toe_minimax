package ui;

import static util.Constants.UrmVolumeButtons.*;
import static util.Constants.PreplayConstants.*;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import util.LoadSave;

public class PreplayButton extends PauseButton{
	private BufferedImage[][] imgs;
	
	private boolean value;
	
	private int rowIndex, colIndex;

	public PreplayButton(int x, int y, int width, int height, boolean value) {
		super(x, y, width, height);
		
		this.value = value;
		
		loadImgs();
	}

	private void loadImgs() {
		BufferedImage temp = LoadSave.getSpriteAtLas(LoadSave.PRE_PLAY_BUTTON);
		
		imgs = new BufferedImage[2][3];
		
		for (int i = 0; i < imgs.length; i++) {
			for (int j = 0; j < imgs[i].length; j++) {
				imgs[i][j] = temp.getSubimage(j * BUTTON_WIDTH_DEFAULT, i * BUTTON_HEIGHT_DEFAULT, BUTTON_WIDTH_DEFAULT , BUTTON_HEIGHT_DEFAULT);
			}
		}
	}

	public void update() {
		rowIndex = 0;
		if (value == false) {
			rowIndex = 1;
		}
		
		colIndex = 0;		
		if (mouseOver) {
			colIndex = 1;
		}	
		if (mousePressed) {
			colIndex = 2;
		}
	}
	
	public void draw(Graphics g) {
		g.drawImage(imgs[rowIndex][colIndex], x, y, BUTTON_WIDTH, BUTTON_HEIGHT, null);
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
	
	public boolean isValue() {
		return value;
	}
	
	public void setValue(boolean value) {
		this.value = value;
	}
}
