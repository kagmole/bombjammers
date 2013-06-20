package ch.hearc.bombjammers.tools;

import java.awt.Font;
import java.io.InputStream;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

public class FontTools {
	
	private static FontTools instance;
	
	private TrueTypeFont buttonFont;
	
	private FontTools() {
		try {
			InputStream is = ResourceLoader.getResourceAsStream(
					"res/fonts/augusta.ttf");
			
			Font awtButtonFont = Font.createFont(Font.TRUETYPE_FONT, is);
			awtButtonFont = awtButtonFont.deriveFont(36f);
			
			buttonFont = new TrueTypeFont(awtButtonFont, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static FontTools getInstance() {
		if (instance == null) {
			instance = new FontTools();
		}
		return instance;
	}
	
	public TrueTypeFont getButtonFont() {
		return buttonFont;
	}
}
