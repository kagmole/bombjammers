package com.icebox.bombjammers.engine.state.start;

import java.io.File;

import kuusisto.tinysound.Music;
import kuusisto.tinysound.TinySound;

import org.lwjgl.input.Keyboard;

import com.icebox.bombjammers.engine.BJEngine;
import com.icebox.bombjammers.loaders.BJPropertiesLoader;
import com.icebox.bombjammers.util.BJActionable;
import com.ragebox.bombframework.bwt.BTBlinkingImage;
import com.ragebox.bombframework.bwt.BTImage;
import com.ragebox.bombframework.bwt.BTStatePanel;
import com.ragebox.bombframework.bwt.BTStatePanelManager;
import com.ragebox.bombframework.bwt.BTSwitchPanel;
import com.ragebox.bombframework.bwt.event.BTKeyboardAdapter;
import com.ragebox.bombframework.bwt.event.BTKeyboardEvent;

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
