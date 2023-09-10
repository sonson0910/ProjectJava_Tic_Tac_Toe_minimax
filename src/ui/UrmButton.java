package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import util.LoadSave;
import static util.Constants.UrmVolumeButtons.*;

public class UrmButton extends PauseButton{
	private BufferedImage[] imgs;
	
	private int rowIndex, index;
	
	public UrmButton(int x, int y, int width, int height, int rowIndex) {
		super(x, y, width, height);
		
		this.rowIndex = rowIndex;
		
		loadImgs();
	}

	private void loadImgs() {
		BufferedImage temp = LoadSave.getSpriteAtLas(LoadSave.URM_BUTTONS);
		
		imgs = new BufferedImage[3];
		
		for (int i = 0; i < imgs.length; i++) {
			imgs[i] = temp.getSubimage(i * URM_B_WIDTH_DEFAULT, rowIndex * URM_B_HEIGHT_DEFAULT, URM_B_WIDTH_DEFAULT, URM_B_HEIGHT_DEFAULT);
		}
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
	
	public void draw(Graphics g) {
		g.drawImage(imgs[index], x, y, width, height, null);
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
}
