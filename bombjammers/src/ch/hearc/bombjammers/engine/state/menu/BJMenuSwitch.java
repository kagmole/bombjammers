package ch.hearc.bombjammers.engine.state.menu;

import ch.hearc.bombjammers.engine.BJEngine;
import ch.hearc.bombjammers.engine.state.ingame.BJIngameState;
import ch.hearc.bombjammers.engine.state.menu.settings.BJSettingsSwitch;
import ch.hearc.bombtoolkit.bwt.BTImage;
import ch.hearc.bombtoolkit.bwt.BTSwitchPanel;

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
