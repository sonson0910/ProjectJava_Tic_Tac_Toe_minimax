package audio;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import static util.Constants.SoundConstants.*;

public class AudioPlayer {	
	private Clip[] songs, sfx;
	private int currentSongID = GAME;
	
	private float songVolume = 0.8f;
	
	private boolean songMute;
	private boolean sfxMute;
	
	public AudioPlayer() {
		loadSongs();
		loadSfx();
		
		playSong(MENU);
	}
	
	private void loadSongs() {
		String[] names = {"Start", "Play", "Win"};
		songs = new Clip[names.length];
		
		for (int i = 0; i < songs.length; i++) {
			songs[i] = getClip(names[i]);
		}
		
		updateSongVolume();
	}
	
	private void loadSfx() {
		String[] names = {"PlayerMove", "EnemyMove", "GameWon", "GameLost", "MenuClick"};
		sfx = new Clip[names.length];
		
		for (int i = 0; i < sfx.length; i++) {
			sfx[i] = getClip(names[i]);
		}
	}
	
	public void setSongVolume(float volume) {
		this.songVolume = volume;
		updateSongVolume();
	}
	
	private void stopSong() {
		if (songs[currentSongID].isActive()) {
			songs[currentSongID].stop();
		}
	}
	
	private Clip getClip(String name) {
		URL url = getClass().getResource("/audio/" + name + ".wav");
		AudioInputStream audio;
		
		try {
			audio = AudioSystem.getAudioInputStream(url);
			Clip c = AudioSystem.getClip();
			c.open(audio);
			
			return c;
			
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {	
			e.printStackTrace();
			
		}
		
		return null;
	}
	
	public void toggleSongMute() {
		this.songMute = !songMute;
		
		for (Clip c : songs) {
			BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
			booleanControl.setValue(songMute);
		}
	}
	
	public void toggleSfxMute() {
		this.sfxMute = !sfxMute;
		
		for (Clip c : sfx) {
			BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
			booleanControl.setValue(sfxMute);
		}
		
		if (!sfxMute) {
			playSfx(MENU_CLICK);
		}
	}
	
	public void playSong(int songIndex) {
		if (currentSongID != songIndex) {
			stopSong();
			
			currentSongID = songIndex;
			updateSongVolume();
			
			songs[currentSongID].setMicrosecondPosition(0);
			songs[currentSongID].loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
	
	public void playSfx(int sfxIndex) {
		sfx[sfxIndex].setMicrosecondPosition(0);
		
		sfx[sfxIndex].start();
	}

	private void updateSongVolume() {
		FloatControl gainControl = (FloatControl) songs[currentSongID].getControl(FloatControl.Type.MASTER_GAIN);
		float range = gainControl.getMaximum() - gainControl.getMinimum();
		float gain = (range * songVolume) + gainControl.getMinimum();
		gainControl.setValue(gain);
	}
}
