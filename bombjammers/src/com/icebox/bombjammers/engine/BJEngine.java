package com.icebox.bombjammers.engine;

import com.icebox.bombjammers.engine.state.ingame.BJIngameState;
import com.icebox.bombjammers.engine.state.menu.BJMenuSwitch;
import com.icebox.bombjammers.engine.state.start.BJStartState;
import com.icebox.bombjammers.loaders.BJPropertiesLoader;
import com.icebox.bombjammers.util.BJActionable;
import com.ragebox.bombframework.bwt.BTDisplay;
import com.ragebox.bombframework.bwt.BTKeyboardFocusManager;

public class BJEngine {
	public static final int FOCUS_START = 0;
	public static final int MAIN_FOCUS = FOCUS_START;
	public static final int FOCUS_1 = 1 + FOCUS_START;
	public static final int FOCUS_2 = 2 + FOCUS_START;
	
	public static final String START_ID = "start";
	public static final String CHARACTER_MENU_ID = "characterMenu";
	public static final String MAIN_MENU_ID = "mainMenu";
	public static final String MAP_MENU_ID = "mapMenu";
	public static final String ONLINE_MENU_ID = "onlineMenu";
	public static final String CONTROL_SETTINGS_ID = "controlSettings";
	public static final String SOUNDS_VIDEO_SETTINGS_ID = "soundsVideoSettings";
	public static final String INGAME_ID = "ingame";
	
	private BTDisplay display;
	private BJMainPanel mainPanel;
	
	public BJEngine() {
		initDisplay();
		initEngine();
		startGame();
	}

	private void initDisplay() {
		display = new BTDisplay(800, 600);
		
		BTKeyboardFocusManager.createFocus(MAIN_FOCUS);
		BTKeyboardFocusManager.createFocus(FOCUS_1);
		BTKeyboardFocusManager.createFocus(FOCUS_2);
	}

	private void initEngine() {
		mainPanel = new BJMainPanel();
		
		BJIngameState inGameState = new BJIngameState(INGAME_ID, mainPanel, this);
		BJStartState startState = new BJStartState(START_ID, mainPanel, this);
		BJMenuSwitch menuSwitch = new BJMenuSwitch(mainPanel, inGameState, this);
		
		mainPanel.addContainer(startState);
		mainPanel.addContainer(menuSwitch);
		mainPanel.addContainer(inGameState);
		
		display.setRootSwitchPanel(mainPanel);
		display.setStartState(START_ID);
	}
	
	private void startGame() {
		display.setGameRunning(true);
		display.start();
	}
	
	public void stopGame() {
		display.setGameRunning(false);
		
		BJPropertiesLoader.getInstance().saveProperties();
	}
	
	public void blockInput() {
		display.setInputEnabled(false);
	}
	
	public void allowInput() {
		display.setInputEnabled(true);
	}
	
	public void closeGate(BJActionable actionableState) {
		mainPanel.closeGate(actionableState);
	}
}
