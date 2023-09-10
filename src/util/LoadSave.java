package util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class LoadSave {
	//	Menu
	public static final String MENU_BACKGROUND_IMG 	= "Main_GUI_Background.png";
	public static final String MENU_TITLE			= "XO.png";
	
	//	Menu Button
	public static final String MENU_BUTTON			= "Main_GUI_Button.png";
	public static final String URM_BUTTONS			= "SettingGUI_Button.png";
	
	//	Preplay
	public static final String PRE_PLAY_BUTTON		= "Yes_No.png";
	public static final String PREPLAY_BG			= "Setting_Preplay.png";

	//	Win
	public static final String WIN_BG				= "WIN_BG.png";
	
	//	Volume Button
	public static final String VOLUME_BUTTON		= "Volume_1.png";
	public static final String MUTE_BUTTON			= "MS_SFX_Button.png";
	
	//	Settings
	public static final String SETTINGS_BACKGROUND 	= "GUI.png";
	
	//	Value
	public static final String VALUE				= "VALUE.png";
	
	public static BufferedImage getSpriteAtLas(String fileName) {
		BufferedImage img = null;
		
		InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
		
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return img;
	}
}
