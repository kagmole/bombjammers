package com.icebox.bombjammers.engine.state.menu.settings;

import java.io.File;

import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

import org.lwjgl.input.Keyboard;

import com.icebox.bombjammers.engine.BJEngine;
import com.icebox.bombjammers.loaders.BJPropertiesLoader;
import com.icebox.bombjammers.util.BJActionable;
import com.ragebox.bombframework.bwt.BTButton;
import com.ragebox.bombframework.bwt.BTSlider;
import com.ragebox.bombframework.bwt.BTStatePanel;
import com.ragebox.bombframework.bwt.BTStatePanelManager;
import com.ragebox.bombframework.bwt.BTSwitchPanel;
import com.ragebox.bombframework.bwt.event.BTKeyboardAdapter;
import com.ragebox.bombframework.bwt.event.BTKeyboardEvent;

public class BJSoundsVideoSettingsState extends BTStatePanel
		implements BJActionable {
	
	private String stateToChange;
	
	private BJEngine engine;
	
	private BTButton buttonMusic;
	private BTButton buttonEffects;
	private BTButton buttonAmbience;
	private BTButton buttonApply;
	private BTButton buttonClose;
	
	private BTSlider sliderMusic;
	private BTSlider sliderEffects;
	private BTSlider sliderAmbience;
	
	private Sound cursor;
	private double volEffects;

	public BJSoundsVideoSettingsState(String nameId, BTSwitchPanel parent,
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
		buttonMusic = new BTButton(100, 150, 248, 74, "Music");
		buttonEffects = new BTButton(100, 250, 248, 74, "Effects");
		buttonAmbience = new BTButton(100, 350, 248, 74, "Ambience");
		buttonApply = new BTButton(200, 500, 248, 74, "Apply");
		buttonClose = new BTButton(500, 500, 248, 74, "Close");
		
		sliderMusic = new BTSlider(400, 150, 248, 74, 10, 10);
		sliderEffects = new BTSlider(400, 250, 248, 74, 10, 10);
		sliderAmbience = new BTSlider(400, 350, 248, 74, 10, 10);
		
		addComponent(buttonMusic);
		addComponent(buttonEffects);
		addComponent(buttonAmbience);
		addComponent(buttonApply);
		addComponent(buttonClose);
		
		addComponent(sliderMusic);
		addComponent(sliderEffects);
		addComponent(sliderAmbience);
		
		cursor = TinySound.loadSound(new File("./res/sound/cursor.wav"));
	}
	
	@Override
	public void activateComponent() {
		buttonMusic.setFocus(BJEngine.MAIN_FOCUS, true);
		
		BJPropertiesLoader properties = BJPropertiesLoader.getInstance();
		
		sliderMusic.setValue(Integer.parseInt(properties.getProperty(
				BJPropertiesLoader.VOLUME_MUSIC)) / 10);
		
		sliderEffects.setValue(Integer.parseInt(properties.getProperty(
				BJPropertiesLoader.VOLUME_EFFECTS)) / 10);
		
		sliderAmbience.setValue(Integer.parseInt(properties.getProperty(
				BJPropertiesLoader.VOLUME_AMBIENCE)) / 10);
		
		engine.allowInput();
		
		volEffects = Double.valueOf(BJPropertiesLoader.getInstance().
				getProperty(BJPropertiesLoader.VOLUME_EFFECTS))/100;
	}
	
	@Override
	public void doAction() {
		switch(stateToChange) {
		case BJEngine.MAIN_MENU_ID:			
			buttonApply.setFocus(BJEngine.MAIN_FOCUS, false);
			buttonClose.setFocus(BJEngine.MAIN_FOCUS, false);
			BTStatePanelManager.getStatePanel(BJEngine.MAIN_MENU_ID)
					.activate();
			break;
		default:
			break;
		}
	}
	
	private void saveChanges() {
		BJPropertiesLoader properties = BJPropertiesLoader.getInstance();
		
		properties.setProperty(BJPropertiesLoader.VOLUME_MUSIC,
				String.valueOf(sliderMusic.getValue() * 10));
		
		properties.setProperty(BJPropertiesLoader.VOLUME_EFFECTS,
				String.valueOf(sliderEffects.getValue() * 10));
		
		properties.setProperty(BJPropertiesLoader.VOLUME_AMBIENCE,
				String.valueOf(sliderAmbience.getValue() * 10));
	}

	private void createListeners() {
		buttonMusic.addKeyboardListener(new BTKeyboardAdapter() {
			
			@Override
			public void keyPressed(BTKeyboardEvent event) {
				switch (event.getKeyCode())
				{
				case Keyboard.KEY_W :
					buttonMusic.giveFocus(BJEngine.MAIN_FOCUS, buttonApply);
					cursor.play(volEffects);
					break;
				case Keyboard.KEY_A :
					sliderMusic.setValue(sliderMusic.getValue() - 1);
					cursor.play(volEffects);
					break;
				case Keyboard.KEY_S :
					buttonMusic.giveFocus(BJEngine.MAIN_FOCUS, buttonEffects);
					cursor.play(volEffects);
					break;
				case Keyboard.KEY_D :
					sliderMusic.setValue(sliderMusic.getValue() + 1);
					cursor.play(volEffects);
					break;
				}	
			}
		});
		
		buttonEffects.addKeyboardListener(new BTKeyboardAdapter() {
			
			@Override
			public void keyPressed(BTKeyboardEvent event) {
				switch (event.getKeyCode())
				{
				case Keyboard.KEY_W :
					buttonEffects.giveFocus(BJEngine.MAIN_FOCUS, buttonMusic);
					cursor.play(volEffects);
					break;
				case Keyboard.KEY_A :
					sliderEffects.setValue(sliderEffects.getValue() - 1);
					volEffects = Double.valueOf(sliderEffects.getValue())/10;
					cursor.play(volEffects);
					break;
				case Keyboard.KEY_S :
					buttonEffects.giveFocus(BJEngine.MAIN_FOCUS, buttonAmbience);
					cursor.play(volEffects);
					break;
				case Keyboard.KEY_D :
					sliderEffects.setValue(sliderEffects.getValue() + 1);
					volEffects = Double.valueOf(sliderEffects.getValue())/10;
					cursor.play(volEffects);
					break;
				}
			}
		});

		buttonAmbience.addKeyboardListener(new BTKeyboardAdapter() {
		
			@Override
			public void keyPressed(BTKeyboardEvent event) {
				switch (event.getKeyCode())
				{
				case Keyboard.KEY_W :
					buttonAmbience.giveFocus(BJEngine.MAIN_FOCUS, buttonEffects);
					cursor.play(volEffects);
					break;
				case Keyboard.KEY_A :
					sliderAmbience.setValue(sliderAmbience.getValue() - 1);
					cursor.play(volEffects);
					break;
				case Keyboard.KEY_S :
					buttonAmbience.giveFocus(BJEngine.MAIN_FOCUS, buttonApply);
					cursor.play(volEffects);
					break;
				case Keyboard.KEY_D :
					sliderAmbience.setValue(sliderAmbience.getValue() + 1);
					cursor.play(volEffects);
					break;
				}
			}
		});
		
		buttonApply.addKeyboardListener(new BTKeyboardAdapter() {
			
			@Override
			public void keyPressed(BTKeyboardEvent event) {
				switch (event.getKeyCode())
				{
				case Keyboard.KEY_E :
					saveChanges();
					
					stateToChange = BJEngine.MAIN_MENU_ID;
					engine.blockInput();
					engine.closeGate(BJSoundsVideoSettingsState.this);
					break;
				case Keyboard.KEY_W :
					buttonApply.giveFocus(BJEngine.MAIN_FOCUS, buttonAmbience);
					cursor.play(volEffects);
					break;
				case Keyboard.KEY_A :
					buttonApply.giveFocus(BJEngine.MAIN_FOCUS, buttonClose);
					cursor.play(volEffects);
					break;
				case Keyboard.KEY_S :
					buttonApply.giveFocus(BJEngine.MAIN_FOCUS, buttonMusic);
					cursor.play(volEffects);
					break;
				case Keyboard.KEY_D :
					buttonApply.giveFocus(BJEngine.MAIN_FOCUS, buttonClose);
					cursor.play(volEffects);
					break;
				}
			}
		});
		
		buttonClose.addKeyboardListener(new BTKeyboardAdapter() {
			
			@Override
			public void keyPressed(BTKeyboardEvent event) {
				switch (event.getKeyCode())
				{
				case Keyboard.KEY_E :
					stateToChange = BJEngine.MAIN_MENU_ID;
					engine.blockInput();
					engine.closeGate(BJSoundsVideoSettingsState.this);
					break;
				case Keyboard.KEY_W :
					buttonClose.giveFocus(BJEngine.MAIN_FOCUS, buttonAmbience);
					cursor.play(volEffects);
					break;
				case Keyboard.KEY_A :
					buttonClose.giveFocus(BJEngine.MAIN_FOCUS, buttonApply);
					cursor.play(volEffects);
					break;
				case Keyboard.KEY_S :
					buttonClose.giveFocus(BJEngine.MAIN_FOCUS, buttonMusic);
					cursor.play(volEffects);
					break;
				case Keyboard.KEY_D :
					buttonClose.giveFocus(BJEngine.MAIN_FOCUS, buttonApply);
					cursor.play(volEffects);
					break;
				}
			}
		});
	}
}
