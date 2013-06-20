package ch.hearc.bombjammers.engine.state.menu;

import java.io.File;

import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

import org.lwjgl.input.Keyboard;

import ch.hearc.bombjammers.engine.BJEngine;
import ch.hearc.bombjammers.engine.state.ingame.BJIngameState;
import ch.hearc.bombjammers.loaders.BJPropertiesLoader;
import ch.hearc.bombjammers.util.BJActionable;
import ch.hearc.bombtoolkit.bwt.BTButton;
import ch.hearc.bombtoolkit.bwt.BTImage;
import ch.hearc.bombtoolkit.bwt.BTKeyboardFocusManager;
import ch.hearc.bombtoolkit.bwt.BTStatePanel;
import ch.hearc.bombtoolkit.bwt.BTStatePanelManager;
import ch.hearc.bombtoolkit.bwt.BTSwitchPanel;
import ch.hearc.bombtoolkit.bwt.event.BTKeyboardAdapter;
import ch.hearc.bombtoolkit.bwt.event.BTKeyboardEvent;

public class BJCharacterMenuState extends BTStatePanel implements BJActionable {
	
	private String stateToChange;
	private String folder1 = "./res/character/bluemage/";
	private String folder2 = "./res/character/redmage/";
	
	private BJEngine engine;
	
	private BTImage titleMenu;
	
	private BTButton buttonCharBlackMage;
	private BTButton buttonCharKnight;
	private BTButton buttonReturn;
	
	private BJIngameState inGameState;
	
	private Sound cursor;
	private double volEffects;

	public BJCharacterMenuState(String nameId, BTSwitchPanel parent,
			BJEngine engine, BJIngameState inGameState) {
		super(nameId, parent);
		
		this.engine = engine;
		stateToChange = null;
		this.inGameState = inGameState;
	}
	
	@Override
	public void initComponent() {
		createComponents();
		createListeners();
	}

	private void createComponents() {	
		titleMenu = new BTImage(173, 50, 454, 88, "./res/component/char_select.png");
		
		buttonReturn = new BTButton(200, 150, 248, 74, "Return");
		buttonCharBlackMage = new BTButton(200, 250, 100, 100, "", "./res/character/bluemage/small_portrait.png");
		buttonCharKnight = new BTButton(350, 250, 100, 100, "", "./res/character/redmage/small_portrait.png");
		
		addComponent(buttonCharBlackMage);
		addComponent(buttonCharKnight);
		addComponent(buttonReturn);
		addComponent(titleMenu);
		
		cursor = TinySound.loadSound(new File("./res/sound/cursor.wav"));
	}
	
	@Override
	public void activateComponent() {
		buttonCharBlackMage.setFocus(BJEngine.FOCUS_1, true);
		buttonCharKnight.setFocus(BJEngine.FOCUS_2, true);
		engine.allowInput();
		volEffects = Double.valueOf(BJPropertiesLoader.getInstance().
				getProperty(BJPropertiesLoader.VOLUME_EFFECTS))/100;
	}
	
	@Override
	public void doAction() {
		// TODO A généraliser via les transitions
		BTKeyboardFocusManager.removeAllFocusedComponent();
		
		buttonCharBlackMage.removeAllFocus();
		buttonCharKnight.removeAllFocus();
		buttonReturn.removeAllFocus();
		
		switch(stateToChange) {
		case BJEngine.MAP_MENU_ID:
			BTStatePanelManager.getStatePanel(BJEngine.MAP_MENU_ID)
					.activate();
			//TODO prendre les focus de chaque joueur
			inGameState.setFolders(folder1, folder2);
			break;
		case BJEngine.MAIN_MENU_ID:
			BTStatePanelManager.getStatePanel(
					BJEngine.MAIN_MENU_ID).activate();
			break;
		default:
			break;
		}
	}

	private void createListeners() {
		buttonCharBlackMage.addKeyboardListener(new BTKeyboardAdapter() {
			
			@Override
			public void keyPressed(BTKeyboardEvent event) {
				if (event.getFocusId() == BJEngine.FOCUS_1) {
					switch (event.getKeyCode())
					{
					case Keyboard.KEY_E :
						stateToChange = BJEngine.MAP_MENU_ID;
						engine.blockInput();
						engine.closeGate(BJCharacterMenuState.this);
						break;
					case Keyboard.KEY_W :
						buttonCharBlackMage.giveFocus(BJEngine.FOCUS_1, buttonReturn);
						cursor.play(volEffects);
						break;
					case Keyboard.KEY_A :
						buttonCharBlackMage.giveFocus(BJEngine.FOCUS_1, buttonCharKnight);
						folder1 = "./res/character/redmage/";
						cursor.play(volEffects);
						break;
					case Keyboard.KEY_S :
						buttonCharBlackMage.giveFocus(BJEngine.FOCUS_1, buttonReturn);
						cursor.play(volEffects);
						break;
					case Keyboard.KEY_D :
						buttonCharBlackMage.giveFocus(BJEngine.FOCUS_1, buttonCharKnight);
						folder1 = "./res/character/redmage/";
						cursor.play(volEffects);
						break;
					}
				} else {
					switch (event.getKeyCode())
					{
					case Keyboard.KEY_U:
						stateToChange = BJEngine.MAP_MENU_ID;
						engine.blockInput();
						engine.closeGate(BJCharacterMenuState.this);
						break;
					case Keyboard.KEY_I:
						buttonCharBlackMage.giveFocus(BJEngine.FOCUS_2, buttonReturn);
						cursor.play(volEffects);
						break;
					case Keyboard.KEY_J:
						buttonCharBlackMage.giveFocus(BJEngine.FOCUS_2, buttonCharKnight);
						folder2 = "./res/character/redmage/";
						cursor.play(volEffects);
						break;
					case Keyboard.KEY_K:
						buttonCharBlackMage.giveFocus(BJEngine.FOCUS_2, buttonReturn);
						cursor.play(volEffects);
						break;
					case Keyboard.KEY_L:
						buttonCharBlackMage.giveFocus(BJEngine.FOCUS_2, buttonCharKnight);
						folder2 = "./res/character/redmage/";
						cursor.play(volEffects);
						break;
					}
				}
			}
		});
		
		buttonCharKnight.addKeyboardListener(new BTKeyboardAdapter() {
			
			@Override
			public void keyPressed(BTKeyboardEvent event) {
				if (event.getFocusId() == BJEngine.FOCUS_1) {
					switch (event.getKeyCode())
					{
					case Keyboard.KEY_E :
						stateToChange = BJEngine.MAP_MENU_ID;
						engine.blockInput();
						engine.closeGate(BJCharacterMenuState.this);
						break;
					case Keyboard.KEY_W :
						buttonCharKnight.giveFocus(BJEngine.FOCUS_1, buttonReturn);
						cursor.play(volEffects);
						break;
					case Keyboard.KEY_A :
						buttonCharKnight.giveFocus(BJEngine.FOCUS_1, buttonCharBlackMage);
						folder1 = "./res/character/bluemage/";
						cursor.play(volEffects);
						break;
					case Keyboard.KEY_S :
						buttonCharKnight.giveFocus(BJEngine.FOCUS_1, buttonReturn);
						cursor.play(volEffects);
						break;
					case Keyboard.KEY_D :
						buttonCharKnight.giveFocus(BJEngine.FOCUS_1, buttonCharBlackMage);
						folder1 = "./res/character/bluemage/";
						cursor.play(volEffects);
						break;
					}
				} else {
					switch (event.getKeyCode()) {
					case Keyboard.KEY_U:
						stateToChange = BJEngine.MAP_MENU_ID;
						engine.blockInput();
						engine.closeGate(BJCharacterMenuState.this);
						break;
					case Keyboard.KEY_I:
						buttonCharKnight.giveFocus(BJEngine.FOCUS_2, buttonReturn);
						cursor.play(volEffects);
						break;
					case Keyboard.KEY_J:
						buttonCharKnight.giveFocus(BJEngine.FOCUS_2, buttonCharBlackMage);
						folder2 = "./res/character/bluemage/";
						cursor.play(volEffects);
						break;
					case Keyboard.KEY_K:
						buttonCharKnight.giveFocus(BJEngine.FOCUS_2, buttonReturn);
						cursor.play(volEffects);
						break;
					case Keyboard.KEY_L:
						buttonCharKnight.giveFocus(BJEngine.FOCUS_2, buttonCharBlackMage);
						folder2 = "./res/character/bluemage/";
						cursor.play(volEffects);
						break;
					}
				}
			}
		});
		
		buttonReturn.addKeyboardListener(new BTKeyboardAdapter() {
			
			@Override
			public void keyPressed(BTKeyboardEvent event) {
				if (event.getFocusId() == BJEngine.FOCUS_1) {
					switch (event.getKeyCode())
					{
					case Keyboard.KEY_E :
						stateToChange = BJEngine.MAIN_MENU_ID;
						engine.blockInput();
						engine.closeGate(BJCharacterMenuState.this);
						break;
					case Keyboard.KEY_W :
						buttonReturn.giveFocus(BJEngine.FOCUS_1, buttonCharBlackMage);
						folder1 = "./res/character/bluemage/";
						cursor.play(volEffects);
						break;
					case Keyboard.KEY_S :
						buttonReturn.giveFocus(BJEngine.FOCUS_1, buttonCharBlackMage);
						folder1 = "./res/character/bluemage/";
						cursor.play(volEffects);
						break;
					}
				} else {
					switch (event.getKeyCode()) {
					case Keyboard.KEY_U:
						stateToChange = BJEngine.MAIN_MENU_ID;
						engine.blockInput();
						engine.closeGate(BJCharacterMenuState.this);
						break;
					case Keyboard.KEY_I:
						buttonReturn.giveFocus(BJEngine.FOCUS_2, buttonCharBlackMage);
						folder2 = "./res/character/bluemage/";
						cursor.play(volEffects);
						break;
					case Keyboard.KEY_K:
						buttonReturn.giveFocus(BJEngine.FOCUS_2, buttonCharBlackMage);
						folder2 = "./res/character/bluemage/";
						cursor.play(volEffects);
						break;
					}
				}
			}
		});
	}
}
