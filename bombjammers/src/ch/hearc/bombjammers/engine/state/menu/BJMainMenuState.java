package ch.hearc.bombjammers.engine.state.menu;

import java.io.File;

import kuusisto.tinysound.Music;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

import org.lwjgl.input.Keyboard;

import ch.hearc.bombjammers.engine.BJEngine;
import ch.hearc.bombjammers.loaders.BJPropertiesLoader;
import ch.hearc.bombjammers.util.BJActionable;
import ch.hearc.bombtoolkit.bwt.BTButton;
import ch.hearc.bombtoolkit.bwt.BTStatePanel;
import ch.hearc.bombtoolkit.bwt.BTStatePanelManager;
import ch.hearc.bombtoolkit.bwt.BTSwitchPanel;
import ch.hearc.bombtoolkit.bwt.event.BTKeyboardAdapter;
import ch.hearc.bombtoolkit.bwt.event.BTKeyboardEvent;

public class BJMainMenuState extends BTStatePanel implements BJActionable {
	
	private BTButton buttonPlayOnline;
	private BTButton buttonPlayOffline;
	private BTButton buttonSettings;
	private BTButton buttonQuit;
	
	private BJEngine engine;
	
	private String stateToChange;
	
	private Music song;
	private Sound cursor;
	private double volEffects;

	public BJMainMenuState(String nameId, BTSwitchPanel parent,
			BJEngine engine) {
		super(nameId, parent);
		
		this.engine = engine;
		stateToChange = null;
	}
	
	@Override
	public void initComponent() {
		createComponents();
		createListeners();
	}

	private void createComponents() {
		buttonPlayOnline = new BTButton(200, 150, 248, 74, "Play Online");
		buttonPlayOffline = new BTButton(200, 250, 248, 74, "Play Offline");
		buttonSettings = new BTButton(200, 350, 248, 74, "Settings");
		buttonQuit = new BTButton(200, 450, 248, 74, "Quit");
		
		addComponent(buttonPlayOnline);
		addComponent(buttonPlayOffline);
		addComponent(buttonSettings);
		addComponent(buttonQuit);
		
		song = TinySound.loadMusic(new File("./res/music/selection_perso_bomjammers.ogg"));
		cursor = TinySound.loadSound(new File("./res/sound/cursor.wav"));
	}
	
	@Override
	public void activateComponent() {
		buttonPlayOnline.setFocus(BJEngine.MAIN_FOCUS, true);
		engine.allowInput();
		song.setVolume(Double.valueOf(BJPropertiesLoader.getInstance().
				getProperty(BJPropertiesLoader.VOLUME_MUSIC))/100);
		volEffects = Double.valueOf(BJPropertiesLoader.getInstance().
				getProperty(BJPropertiesLoader.VOLUME_EFFECTS))/100;
		song.play(true,Double.valueOf(BJPropertiesLoader.getInstance().
				getProperty(BJPropertiesLoader.VOLUME_MUSIC))/100);
	}
	
	@Override
	public void doAction() {
		switch(stateToChange) {
		case BJEngine.ONLINE_MENU_ID:
			buttonPlayOnline.setFocus(BJEngine.MAIN_FOCUS, false);
			BTStatePanelManager.getStatePanel(BJEngine.ONLINE_MENU_ID)
					.activate();
			break;
		case BJEngine.CHARACTER_MENU_ID:
			buttonPlayOffline.setFocus(BJEngine.MAIN_FOCUS, false);
			BTStatePanelManager.getStatePanel(
					BJEngine.CHARACTER_MENU_ID).activate();
			break;
		case BJEngine.SOUNDS_VIDEO_SETTINGS_ID:
			buttonSettings.setFocus(BJEngine.MAIN_FOCUS, false);
			BTStatePanelManager.getStatePanel(
					BJEngine.SOUNDS_VIDEO_SETTINGS_ID).activate();
			break;
		default:
			break;
		}
	}

	private void createListeners() {
		buttonPlayOnline.addKeyboardListener(new BTKeyboardAdapter() {
			
			@Override
			public void keyPressed(BTKeyboardEvent event) {				
				switch (event.getKeyCode())
				{
				case Keyboard.KEY_E :
					stateToChange = BJEngine.ONLINE_MENU_ID;
					engine.blockInput();
					engine.closeGate(BJMainMenuState.this);
					break;
				case Keyboard.KEY_W :
					buttonPlayOnline.giveFocus(BJEngine.MAIN_FOCUS, buttonQuit);
					cursor.play(volEffects);
					break;
				case Keyboard.KEY_S :
					buttonPlayOnline.giveFocus(BJEngine.MAIN_FOCUS, buttonPlayOffline);
					cursor.play(volEffects);
					break;
				}
			}
		});
		
		buttonPlayOffline.addKeyboardListener(new BTKeyboardAdapter() {
			
			@Override
			public void keyPressed(BTKeyboardEvent event) {
				switch (event.getKeyCode())
				{
				case Keyboard.KEY_E :
					stateToChange = BJEngine.CHARACTER_MENU_ID;
					engine.blockInput();
					engine.closeGate(BJMainMenuState.this);
					break;
				case Keyboard.KEY_W :
					buttonPlayOffline.giveFocus(BJEngine.MAIN_FOCUS, buttonPlayOnline);
					cursor.play(volEffects);
					break;
				case Keyboard.KEY_S :
					buttonPlayOffline.giveFocus(BJEngine.MAIN_FOCUS, buttonSettings);
					cursor.play(volEffects);
					break;
				}
			}
		});

		buttonSettings.addKeyboardListener(new BTKeyboardAdapter() {
			
			@Override
			public void keyPressed(BTKeyboardEvent event) {
				switch (event.getKeyCode())
				{
				case Keyboard.KEY_E :
					stateToChange = BJEngine.SOUNDS_VIDEO_SETTINGS_ID;
					engine.blockInput();
					engine.closeGate(BJMainMenuState.this);
					break;
				case Keyboard.KEY_W :
					buttonSettings.giveFocus(BJEngine.MAIN_FOCUS, buttonPlayOffline);
					cursor.play(volEffects);
					break;
				case Keyboard.KEY_S :
					buttonSettings.giveFocus(BJEngine.MAIN_FOCUS, buttonQuit);
					cursor.play(volEffects);
					break;
				}
			}
		});
		
		buttonQuit.addKeyboardListener(new BTKeyboardAdapter() {
			
			@Override
			public void keyPressed(BTKeyboardEvent event) {
				switch (event.getKeyCode())
				{
				case Keyboard.KEY_E :
					engine.stopGame();
					break;
				case Keyboard.KEY_W :
					buttonQuit.giveFocus(BJEngine.MAIN_FOCUS, buttonSettings);
					cursor.play(volEffects);
					break;
				case Keyboard.KEY_S :
					buttonQuit.giveFocus(BJEngine.MAIN_FOCUS, buttonPlayOnline);
					cursor.play(volEffects);
					break;
				}
			}
		});
	}
}
