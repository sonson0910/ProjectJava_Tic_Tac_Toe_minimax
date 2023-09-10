package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import main.GameModel;

import static util.Constants.UrmVolumeButtons.*;

public class AudioOptions {
	private VolumeButton volumeButton;
	private SoundButton musicButton, sfxButton;
	
	private GameModel gameModel;
	
	public AudioOptions(GameModel gameModel) {
		createSoundButtons();
		createVolumeButton();
		
		this.gameModel = gameModel;
	}

	private void createVolumeButton() {
		volumeButton = new VolumeButton(VOLUME_X, VOLUME_Y, 60, 60);
	}

	private void createSoundButtons() {
		musicButton = new SoundButton(SOUND_X, MUSIC_Y, SOUND_SIZE, SOUND_SIZE);
		sfxButton = new SoundButton(SOUND_X, SFX_Y, SOUND_SIZE, SOUND_SIZE);
	}
	
	public void update() {
		musicButton.update();
		sfxButton.update();
		
		volumeButton.update();
	}
	
	public void draw(Graphics g) {
		musicButton.draw(g);
		sfxButton.draw(g);
		
		volumeButton.draw(g);
	}
	
	public void mouseDragged(MouseEvent e) {
		if (volumeButton.isMousePressed()) {
			float valueBefore = volumeButton.getFloatValue();
			
			volumeButton.changeX(e.getX());
			
			float valueAfter = volumeButton.getFloatValue();
			
			if (valueBefore != valueAfter) {
				gameModel.getAudioPlayer().setSongVolume(valueAfter);
				
				System.out.println(valueAfter);
			}
		}
	}

	public void mousePressed(MouseEvent e) {		
		if (isIn(e, musicButton)) {
			musicButton.setMousePressed(!musicButton.isMousePressed());
		}
		else if (isIn(e, sfxButton)) {
			sfxButton.setMousePressed(!sfxButton.isMousePressed());
		}
		else if (isIn(e, volumeButton)) {
			volumeButton.setMousePressed(!volumeButton.isMousePressed());
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (isIn(e, musicButton)) {
			if (musicButton.isMousePressed()) {
				musicButton.setMuted(!musicButton.isMuted());
				
				gameModel.getAudioPlayer().toggleSongMute();
			}
		}
		else if (isIn(e, sfxButton)) {
			if (sfxButton.isMousePressed()) {
				sfxButton.setMuted(!sfxButton.isMuted());
				
				gameModel.getAudioPlayer().toggleSfxMute();
			};
		}
		
		musicButton.resetBools();
		sfxButton.resetBools();
		
		volumeButton.resetBools();
	}

	public void mouseMoved(MouseEvent e) {
		musicButton.setMouseOver(false);
		sfxButton.setMouseOver(false);
		volumeButton.setMouseOver(false);
		
		if (isIn(e, musicButton)) {
			musicButton.setMouseOver(true);
		}
		else if (isIn(e, sfxButton)) {
			sfxButton.setMouseOver(true);
		}
		else if (isIn(e, volumeButton)) {
			volumeButton.setMouseOver(true);
		}
	}
	
	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
}