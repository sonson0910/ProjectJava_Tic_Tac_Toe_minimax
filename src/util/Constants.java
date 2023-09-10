package util;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Constants {
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public static final int PANEL_WIDTH 	= (int) screenSize.getWidth() / 3;	//	512
	public static final int PANEL_HEIGHT 	= (int) screenSize.getHeight();		//	864
	
	public static class GameConstants {
		public static final int FPS_SET = 120;
		public static final int UPS_SET = 200;
	}
	
	public static class PreplayConstants {
		public static final int PREPLAY_BG_WIDTH 	= 400;
		public static final int PREPLAY_BG_HEIGHT 	= 500;
		public static final int PREPLAY_BG_X	 	= PANEL_WIDTH / 2 - PREPLAY_BG_WIDTH / 2;
		public static final int PREPLAY_BG_Y 		= 50;
		
		public static final int BUTTON_WIDTH_DEFAULT	= 176;
		public static final int BUTTON_HEIGHT_DEFAULT 	= 83;
		public static final int BUTTON_WIDTH 			= 80;
		public static final int BUTTON_HEIGHT 			= 40;
		
		public static final int PREPLAY_B_X 	= 340;
		
		public static final int PREPLAY_B_Y1	= 135;
		public static final int PREPLAY_B_Y2	= 207;
		public static final int PREPLAY_B_Y3	= 279;
		public static final int PREPLAY_B_Y4	= 351;
		
		public static final int PREFERED_SIZE	= 75;
		public static final int PREFERED_X		= 342;
		public static final int PREFERED_Y		= 425;
		
		public static final int BLOCK_BOTHWAY		= 0;
		public static final int AGAINST_BOT			= 1;
		public static final int RND_FIRST_TURN		= 2;
		public static final int PLAYER_1ST_TURN		= 3;
	
		public static final int PLAY_BUTTON_Y1 	= 580;
		public static final int PLAY_BUTTON_Y2 	= 700;
	}
	
	public static class SoundConstants {
		public static final int MENU 		= 0;
		public static final int GAME 		= 1;
		public static final int WIN			= 2;
		
		public static final int PLAYER_MOVE = 0;
		public static final int ENEMY_MOVE  = 1;
		public static final int GAME_WON	= 2;
		public static final int GAME_LOST	= 3;
		public static final int MENU_CLICK  = 4;
	}
	
	public static class MenuTitleConstants {
		public static final int TITLE_WIDTH 		= 231;
		public static final int TITLE_HEIGHT		= 198;
		
		public static final int TITLE_STARTING_X	= PANEL_WIDTH / 2 - TITLE_WIDTH / 2 + 20;
		public static final int TITLE_STARTING_Y	= PANEL_HEIGHT / 6;
	
		public static final int TITLE_SPEED			= PANEL_HEIGHT / 100;
	}
	
	public static class MenuButtonSize {
		public static final int MENU_B_WIDTH_DEFAULT	= 376;
		public static final int MENU_B_HEIGHT_DEFAULT	= 166;
		
		public static final int MENU_B_WIDTH	= (int)(MENU_B_WIDTH_DEFAULT * 0.75);
		public static final int MENU_B_HEIGHT	= (int)(MENU_B_HEIGHT_DEFAULT * 0.75);
	
		public static final int MENU_B_X	= PANEL_WIDTH / 2 - MENU_B_WIDTH / 2;
		
		public static final int MENU_B_Y0	= 345;
		public static final int MENU_B_Y1	= 475;
		public static final int MENU_B_Y2	= 605;
	}
	
	public static class UrmVolumeButtons {
		public static final int URM_B_WIDTH_DEFAULT 	= 638;
		public static final int URM_B_HEIGHT_DEFAULT 	= 172;
		public static final int URM_B_WIDTH 			= 240;
		public static final int URM_B_HEIGHT 			= 60;
		
		public static final int MENU_Y 		= 700;
		public static final int REPLAY_Y 	= 620;
		public static final int UNPAUSE_Y 	= 540;
		
		public static final int MENU_INDEX		= 0;
		public static final int UNPAUSE_INDEX	= 1;
		public static final int REPLAY_INDEX	= 2;
		
		public static final int URM_X		= PANEL_WIDTH / 2 - URM_B_WIDTH / 2 ;
		
		public static final int VOLUME_BUTTON_WIDTH_DEFAULT 	= 11;
		public static final int VOLUME_BUTTON_HEIGHT_DEFAULT 	= 40;
		public static final int VOLUME_BUTTON_WIDTH				= 30;
		public static final int VOLUME_BUTTON_HEIGHT			= 60;
		
		public static final int VOLUME_SLIDER_WIDTH_DEFAULT		= 300;
		public static final int VOLUME_SLIDER_HEIGHT_DEFAULT	= 12;
		public static final int VOLUME_SLIDER_WIDTH				= 400;
		public static final int VOLUME_SLIDER_HEIGHT			= 30;
		
		public static final int VOLUME_X = PANEL_WIDTH / 2 - VOLUME_SLIDER_WIDTH / 2;
		public static final int VOLUME_Y = 450;
		
		public static final int BG_WIDTH	= 560;
		public static final int BG_HEIGHT	= PANEL_HEIGHT;
		public static final int BG_X		= PANEL_WIDTH / 2 - BG_WIDTH / 2;
		public static final int BG_Y		= 0;
		
		public static final int SOUND_SIZE_DEFAULT  = 99;
		public static final int SOUND_SIZE			= 80;
		
		public static final int SOUND_X 	= 275;
		public static final int MUSIC_Y 	= 150;
		public static final int SFX_Y 		= 250;
	}
	
	public static class MatchConstants {
		public static final int HOVERED = 0;
		
		public static final int BOARD_SIZE = 448;
		
		public static final int BOARD_X = 32;
		public static final int BOARD_Y	= 316;
		
		public static final int BOARD_CONTENT = 16;
		
		public static final int CELL_SIZE = BOARD_SIZE / BOARD_CONTENT;
	}
	
	public static class PlayFinishedConstants {
		public static final int PF_BG_WIDTH		= 400;
		public static final int PF_BG_HEIGHT	= 500;
		public static final int PF_BG_X			= PANEL_WIDTH / 2 - PF_BG_WIDTH / 2;
		public static final int PF_BG_Y			= 200;
		
		public static final int PF_VALUE_WON_WIDTH 	= 0;
		public static final int PF_VALUE_WON_HEIGHT	= 0;
		public static final int PF_VALUE_WON_X		= 0;
		public static final int PF_VALUE_WON_Y		= 0;
		
		public static final int PF_BUTTON_X		= UrmVolumeButtons.URM_X;
		public static final int PF_REPLAY_Y		= 520;
		public static final int PF_MENU_Y 		= 600;
	}
	
	public static class GameValues {
		public static final int VALUE_SIZE 		= 1024;
		
		public static final int VALUE_EMPTY 	= 0;
		public static final int VALUE_O 		= 1;
		public static final int VALUE_X 		= 2;
		
		public static final int VALUE_X_PREDICT = 3;
		public static final int VALUE_O_PREDICT = 4;
		
		public static final int DEFAULT = 0;
		public static final int HOVER	= 1;
		public static final int PRESSED	= 2;
		
		public static final int X = 0;
		public static final int Y = 1;
		
		public static final int DEPTH = 4;
		
		public static final int DIR_X[] = {0, 1, 1, 1};
		public static final int DIR_Y[] = {1, 1, 0, -1};
	}
}
