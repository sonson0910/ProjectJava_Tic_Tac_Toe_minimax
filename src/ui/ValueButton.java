package ui;

import static util.Constants.GameValues.VALUE_SIZE;
import static util.Constants.GameValues.VALUE_O;
import static util.Constants.PreplayConstants.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import util.LoadSave;

public class ValueButton extends PauseButton{
	private BufferedImage[][] imgs;
	
	private int preferedValue = VALUE_O;
	
	private int rowIndex, colIndex;
	
	public ValueButton(int x, int y, int width, int height) {
		super(x, y, width, height);
		
		loadImgs();
	}

	private void loadImgs() {
		imgs = new BufferedImage[3][3];
		
		BufferedImage temp = LoadSave.getSpriteAtLas(LoadSave.VALUE);
		
		for (int value = 0; value < 3; value++) {
			for (int type = 0; type < 3; type++) {
				imgs[type][value] = temp.getSubimage(value * VALUE_SIZE, type * VALUE_SIZE, VALUE_SIZE, VALUE_SIZE);
			}
		}
	}
	
	public void update() {
		rowIndex = preferedValue;
		
		colIndex = 0;		
		if (mouseOver) {
			colIndex = 1;
		}	
		if (mousePressed) {
			colIndex = 2;
		}
	}
	
	public void draw(Graphics g) {
		g.drawImage(imgs[rowIndex][colIndex], x, y, width, height, null);
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

	public int getPreferedValue() {
		return preferedValue;
	}

	public void setPreferedValue(int preferedValue) {
		this.preferedValue = preferedValue;
	}
}
