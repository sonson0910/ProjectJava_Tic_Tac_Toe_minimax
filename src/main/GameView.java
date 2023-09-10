package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import gamestates.GameStates;
import gamestates.Menu;
import gamestates.Options;
import gamestates.Playing;
import gamestates.PreplayMenu;
import inputs.KeyboardInputs;
import inputs.MouseInputs;
import util.LoadSave;

import static util.Constants.PANEL_HEIGHT;
import static util.Constants.PANEL_WIDTH;

public class GameView extends JPanel{
	private MouseInputs mausInputs;
	private KeyboardInputs kehbardInputs;
	
	private GameModel gameModel;
	
	private BufferedImage backgroundImg;	
	
	
	public GameView(GameModel gameModel) {
		this.gameModel = gameModel;
		
		mausInputs = new MouseInputs(gameModel);
		kehbardInputs = new KeyboardInputs(gameModel);

		this.addKeyListener(kehbardInputs);
		
		this.addMouseListener(mausInputs);
		this.addMouseMotionListener(mausInputs);
		
		setPanelSize();
		
		initBackground();
		initJFrame();
	}

	private void initJFrame() {
		JFrame jframe = new JFrame();
		
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(this);
		
		jframe.setResizable(false);
		jframe.pack();
		jframe.setLocationRelativeTo(null);
		jframe.setVisible(true);
		
		jframe.setFocusable(true);
		jframe.toFront();
		jframe.requestFocus();
	}

	private void initBackground() {
		backgroundImg = LoadSave.getSpriteAtLas(LoadSave.MENU_BACKGROUND_IMG);
	}

	private void setPanelSize() {
		Dimension screenSize = new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
		setPreferredSize(screenSize);
		
//		System.out.println("size = " + (int)screenSize.getWidth() + " / " + (int)screenSize.getHeight());
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(backgroundImg, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, null);
		
		switch (GameStates.state) {
		case MENU:
			gameModel.getMenu().draw(g);
			break;
			
		case PLAYING:
			gameModel.getPlaying().draw(g);
			break;
			
		case PREPLAYMENU:
			gameModel.getPreplayMenu().draw(g);
			break;
			
		case OPTIONS:
			gameModel.getOptions().draw(g);
			break;
			
		case QUIT:
			break;
			
		default:
			break;
		}
	}

	public GameModel getGameModel() {
		return gameModel;
	}
}
