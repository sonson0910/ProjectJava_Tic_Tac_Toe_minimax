package main;

import static util.Constants.GameConstants.FPS_SET;
import static util.Constants.GameConstants.UPS_SET;

import javax.swing.JFrame;

import gamestates.GameStates;

public class GameController implements Runnable{
	private GameModel gameModel;
	
	private Thread gameThread;
	
	public GameController(GameModel gameModel) {	
		this.gameModel = gameModel;

		startGameLoop();
	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		double timePerFrame = 1000000000 / FPS_SET;
		double timePerUpdate = 1000000000 / UPS_SET;
		
		long previousTime = System.nanoTime();
		
		int frames = 0, updates = 0;
		long lastCheck = System.currentTimeMillis();
		
		double deltaU = 0;
		double deltaF = 0;
		
		while (true) {
			long currentTime = System.nanoTime();
			
			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;
			
			if (deltaU >= 1) {
				update();
				
				updates++;
				deltaU--;
			}
			
			if (deltaF >= 1) {
				gameModel.getGameView().repaint();
				
				frames++;
				deltaF--;
			}
			
			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				
//				System.out.println("FPS = " + frames + " | UPS = " + updates);
				frames = 0;
				updates = 0;
			}
		}
	}
	
	public void update() {
		switch (GameStates.state) {
		case MENU:
			gameModel.getMenu().update();
			break;
			
		case PLAYING:
			gameModel.getPlaying().update();
			break;
			
		case PREPLAYMENU:
			gameModel.getPreplayMenu().update();
			break;
			
		case OPTIONS:
			gameModel.getOptions().update();
			break;
			
		case QUIT:

		default:
			System.exit(0);
			break;
		}
	}
}
