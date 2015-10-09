package com.icebox.bombjammers.engine.state.menu;

import java.io.File;

import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

import org.lwjgl.input.Keyboard;

import com.icebox.bombjammers.engine.BJEngine;
import com.icebox.bombjammers.loaders.BJPropertiesLoader;
import com.icebox.bombjammers.util.BJActionable;
import com.ragebox.bombframework.bwt.BTButton;
import com.ragebox.bombframework.bwt.BTImage;
import com.ragebox.bombframework.bwt.BTStatePanel;
import com.ragebox.bombframework.bwt.BTStatePanelManager;
import com.ragebox.bombframework.bwt.BTSwitchPanel;
import com.ragebox.bombframework.bwt.event.BTKeyboardAdapter;
import com.ragebox.bombframework.bwt.event.BTKeyboardEvent;

public class BJMapMenuState extends BTStatePanel implements BJActionable {
	
	private String stateToChange;
	
	private BJEngine engine;
	
	private BTImage titleMenu;
	
	private BTButton buttonReturn;
	private BTButton buttonMapStandard;
	
	private Sound cursor;
	private double volEffects;

	public BJMapMenuState(String nameId, BTSwitchPanel parent,
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
		titleMenu = new BTImage(173, 50, 454, 88, "./res/component/map_selection.png");
		
		buttonReturn = new BTButton(200, 150, 248, 74, "Return");
		buttonMapStandard = new BTButton(200, 250, 100, 100, "", "./res/maps/kaamelott/small_portrait.png");
		
		addComponent(titleMenu);
		addComponent(buttonMapStandard);
		addComponent(buttonReturn);
		
		cursor = TinySound.loadSound(new File("./res/sound/cursor.wav"));
	}
	
	@Override
	public void activateComponent() {
		buttonMapStandard.setFocus(BJEngine.MAIN_FOCUS, true);
		engine.allowInput();
		volEffects = Double.valueOf(BJPropertiesLoader.getInstance().
				getProperty(BJPropertiesLoader.VOLUME_EFFECTS))/100;
	}
	
	@Override
	public void doAction() {
		switch(stateToChange) {
		case BJEngine.CHARACTER_MENU_ID:
			buttonReturn.setFocus(BJEngine.MAIN_FOCUS, false);
			BTStatePanelManager.getStatePanel(BJEngine.CHARACTER_MENU_ID)
					.activate();
			break;
		case BJEngine.INGAME_ID:
			buttonMapStandard.setFocus(BJEngine.MAIN_FOCUS, false);
			BTStatePanelManager.getStatePanel(
					BJEngine.INGAME_ID).activate();
			break;
		default:
			break;
		}
	}

	private void createListeners() {	
		buttonMapStandard.addKeyboardListener(new BTKeyboardAdapter() {
			
			@Override
			public void keyPressed(BTKeyboardEvent event) {
				switch (event.getKeyCode())
				{
				case Keyboard.KEY_E :
					stateToChange = BJEngine.INGAME_ID;
					engine.blockInput();
					engine.closeGate(BJMapMenuState.this);
					break;
				case Keyboard.KEY_W :
					buttonMapStandard.giveFocus(BJEngine.MAIN_FOCUS, buttonReturn);
					cursor.play(volEffects);
					break;
				case Keyboard.KEY_S :
					buttonMapStandard.giveFocus(BJEngine.MAIN_FOCUS, buttonReturn);
					cursor.play(volEffects);
					break;
				}
			}
		});
		
		buttonReturn.addKeyboardListener(new BTKeyboardAdapter() {
			
			@Override
			public void keyPressed(BTKeyboardEvent event) {
				switch (event.getKeyCode())
				{
				case Keyboard.KEY_E :
					stateToChange = BJEngine.CHARACTER_MENU_ID;
					engine.blockInput();
					engine.closeGate(BJMapMenuState.this);
					break;
				case Keyboard.KEY_W :
					buttonReturn.giveFocus(BJEngine.MAIN_FOCUS, buttonMapStandard);
					cursor.play(volEffects);
					break;
				case Keyboard.KEY_S :
					buttonReturn.giveFocus(BJEngine.MAIN_FOCUS, buttonMapStandard);
					cursor.play(volEffects);
					break;
				}
			}
		});
	}
}
