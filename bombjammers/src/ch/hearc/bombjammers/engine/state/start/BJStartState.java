package ch.hearc.bombjammers.engine.state.start;

import java.io.File;

import kuusisto.tinysound.Music;
import kuusisto.tinysound.TinySound;

import org.lwjgl.input.Keyboard;

import ch.hearc.bombjammers.engine.BJEngine;
import ch.hearc.bombjammers.loaders.BJPropertiesLoader;
import ch.hearc.bombjammers.util.BJActionable;
import ch.hearc.bombtoolkit.bwt.BTBlinkingImage;
import ch.hearc.bombtoolkit.bwt.BTImage;
import ch.hearc.bombtoolkit.bwt.BTStatePanel;
import ch.hearc.bombtoolkit.bwt.BTStatePanelManager;
import ch.hearc.bombtoolkit.bwt.BTSwitchPanel;
import ch.hearc.bombtoolkit.bwt.event.BTKeyboardAdapter;
import ch.hearc.bombtoolkit.bwt.event.BTKeyboardEvent;

public class BJStartState extends BTStatePanel implements BJActionable {
	
	private BJEngine engine;
	
	private BTImage backgroundImage;
	private BTBlinkingImage titleImage;
	private Music song;

	public BJStartState(String nameId, BTSwitchPanel parent,
			BJEngine engine) {
		super(nameId, parent);
		
		this.engine = engine;
	}

	@Override
	public void initComponent() {
		createComponents();
		createListeners();
	}

	private void createComponents() {		
		
		backgroundImage = new BTImage(0, 0, 800, 600,
				"res/component/startScreen.png");
		
		titleImage = new BTBlinkingImage(115, 174, 570, 252,
				"res/component/title.png", 500);
		
		addComponent(backgroundImage);
		addComponent(titleImage);
		
		song = TinySound.loadMusic(new File("./res/music/start.ogg"));
		song.play(true,Double.valueOf(BJPropertiesLoader.getInstance().
				getProperty(BJPropertiesLoader.VOLUME_MUSIC))/100);
	}
	
	@Override
	public void activateComponent() {
		titleImage.setFocus(BJEngine.MAIN_FOCUS, true);
	}
	
	@Override
	public void doAction() {
		titleImage.setFocus(BJEngine.MAIN_FOCUS, false);
		BTStatePanelManager.getStatePanel(BJEngine.MAIN_MENU_ID)
				.activate();
		song.stop();
	}

	private void createListeners() {
		titleImage.addKeyboardListener(new BTKeyboardAdapter() {
			
			@Override
			public void keyPressed(BTKeyboardEvent event) {
				switch (event.getKeyCode())
				{
				case Keyboard.KEY_E :
					engine.blockInput();
					engine.closeGate(BJStartState.this);
					break;
				}
			}
		});
	}
}
