package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import audio.AudioPlayer;
import gamestates.GameStates;
import gamestates.Menu;
import gamestates.Options;
import gamestates.Playing;
import gamestates.PreplayMenu;
import ui.AudioOptions;
import util.LoadSave;

import static util.Constants.PANEL_HEIGHT;
import static util.Constants.PANEL_WIDTH;
import static util.Constants.GameConstants.*;

public class GameModel{
	//	Essentials
	private GameController gameController;
	private GameView gameView;
	
	private AudioPlayer audioPlayer;
	private AudioOptions audioOptions;

	//	Game States
	private Playing playing;
	private Menu menu;
	private PreplayMenu preplayMenu;
	private Options options;
	
	public GameModel() {
		initClasses();
		
		gameView = new GameView(this);
		gameController = new GameController(this);
		
		gameView.setFocusable(true);
		gameView.requestFocus();
	}

	private void initClasses() {
		audioOptions = new AudioOptions(this);
		
		menu = new Menu(this);
		preplayMenu = new PreplayMenu(this);
		playing = new Playing(this);
		options = new Options(this);
		
		audioPlayer = new AudioPlayer();
	}
	
	public Menu getMenu() {
		return menu;
	}
	
	public Playing getPlaying() {
		return playing;
	}
	
	public PreplayMenu getPreplayMenu() {
		return preplayMenu;
	}
	
	public Options getOptions() {
		return options;
	}
	
	public AudioPlayer getAudioPlayer() {
		return audioPlayer;
	}
	
	public AudioOptions getAudioOptions() {
		return audioOptions;
	}
	
	public GameView getGameView() {
		return gameView;
	}
	
	public GameController getGameController() {
		return gameController;
	}
}
