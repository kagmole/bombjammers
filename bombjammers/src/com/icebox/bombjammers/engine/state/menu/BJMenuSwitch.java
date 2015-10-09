package com.icebox.bombjammers.engine.state.menu;

import com.icebox.bombjammers.engine.BJEngine;
import com.icebox.bombjammers.engine.state.ingame.BJIngameState;
import com.icebox.bombjammers.engine.state.menu.settings.BJSettingsSwitch;
import com.ragebox.bombframework.bwt.BTImage;
import com.ragebox.bombframework.bwt.BTSwitchPanel;

public class BJMenuSwitch extends BTSwitchPanel {
	
	private BJEngine engine;
	
	private BTImage backgroundImage;
	
	private BJIngameState inGameState;

	public BJMenuSwitch(BTSwitchPanel parent, BJIngameState inGameState, BJEngine engine) {
		super(parent);
		
		this.engine = engine;
		
		this.inGameState = inGameState;
		
		initSwitchPanel();
	}

	private void initSwitchPanel() {		
		addContainer(new BJCharacterMenuState(
				BJEngine.CHARACTER_MENU_ID, this, engine, inGameState));
		
		addContainer(new BJMainMenuState(
				BJEngine.MAIN_MENU_ID, this, engine));
		
		addContainer(new BJMapMenuState(
				BJEngine.MAP_MENU_ID, this, engine));
		
		addContainer(new BJOnlineMenuState(
				BJEngine.ONLINE_MENU_ID, this, engine));
		
		addContainer(new BJSettingsSwitch(this, engine));
	}
	
	@Override
	public void initComponent() {
		backgroundImage = new BTImage(0, 0, 800, 600,
				"res/component/startScreen.png");
		
		addComponent(backgroundImage);
		

	}
}
