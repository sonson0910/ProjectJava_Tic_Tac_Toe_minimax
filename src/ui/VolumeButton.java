package ui;

//import static util.Constants.UI.VolumeButtons.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import util.LoadSave;
import static util.Constants.UrmVolumeButtons.*;

public class VolumeButton extends PauseButton{
	private BufferedImage[] imgs;
	private BufferedImage	slider;
	
	private int index = 0;
	
	private int buttonX, minX, maxX;
	
	private float floatValue = 0;

	public VolumeButton(int x, int y, int width, int height) {
		super(x + width / 2, y, width, height);
		
		buttonX = x + VOLUME_SLIDER_WIDTH * 7/10;
		
		this.x = x;
		this.width = width;
		
		minX = x;
		maxX = x + VOLUME_SLIDER_WIDTH - VOLUME_BUTTON_WIDTH ;

		loadImgs();
	}

	private void loadImgs() {
		BufferedImage temp = LoadSave.getSpriteAtLas(LoadSave.VOLUME_BUTTON);
		
		imgs = new BufferedImage[3];
		
		for (int i = 0; i < imgs.length; i++) {
			imgs[i] = temp.getSubimage(i * VOLUME_BUTTON_WIDTH_DEFAULT, 0, VOLUME_BUTTON_WIDTH_DEFAULT, VOLUME_BUTTON_HEIGHT_DEFAULT);
		}
		
		slider = temp.getSubimage(3 * VOLUME_BUTTON_WIDTH_DEFAULT, 0, VOLUME_SLIDER_WIDTH_DEFAULT, VOLUME_SLIDER_HEIGHT_DEFAULT);
	}

	public void update() {
		index = 0;		
		
		if (mouseOver) {
			index = 1;
		}	
		
		if (mousePressed) {
			index = 2;
		}
		
		bounds.x = buttonX;
	}
	
	public void draw(Graphics g) {
		g.drawImage(slider, x, y, VOLUME_SLIDER_WIDTH, VOLUME_SLIDER_HEIGHT, null);

		g.drawImage(imgs[index], buttonX, y - 15, VOLUME_BUTTON_WIDTH, VOLUME_BUTTON_HEIGHT, null);
		
		//	Debugging Purpose
//		g.setColor(Color.gray);
//		g.fillRect(x, y, width, height);
//		
//		g.setColor(Color.red);
//		g.fillRect(buttonX, y, 340, height);
	}
	
	public void changeX(int x) {
		if (x < minX) {
			buttonX = minX;
		}
		
		else if (x > maxX) {
			buttonX = maxX;
		}
		
		else buttonX = x;
		
		updateFloatValue();
		
		bounds.x = buttonX - VOLUME_SLIDER_WIDTH_DEFAULT / 2;
	}
	
	private void updateFloatValue() {
		float range = maxX - minX;
		
		float value = buttonX - minX;
		
		floatValue = value/range;
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
	
	public float getFloatValue() {
		return floatValue;
	}
}
