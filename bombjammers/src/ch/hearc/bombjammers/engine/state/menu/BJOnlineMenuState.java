package ch.hearc.bombjammers.engine.state.menu;

import org.lwjgl.input.Keyboard;

import ch.hearc.bombjammers.engine.BJEngine;
import ch.hearc.bombjammers.util.BJActionable;
import ch.hearc.bombtoolkit.bwt.BTButton;
import ch.hearc.bombtoolkit.bwt.BTImage;
import ch.hearc.bombtoolkit.bwt.BTStatePanel;
import ch.hearc.bombtoolkit.bwt.BTStatePanelManager;
import ch.hearc.bombtoolkit.bwt.BTSwitchPanel;
import ch.hearc.bombtoolkit.bwt.event.BTKeyboardAdapter;
import ch.hearc.bombtoolkit.bwt.event.BTKeyboardEvent;

public class BJOnlineMenuState extends BTStatePanel implements BJActionable {
	
	private String stateToChange;
	
	private BJEngine engine;
	
	private BTButton buttonReturn;
	
	private BTImage imageInConstruction;

	public BJOnlineMenuState(String nameId, BTSwitchPanel parent,
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
		buttonReturn = new BTButton(200, 100, 248, 74, "Return");
		imageInConstruction = new BTImage(0, 0, 800, 600, "./res/component/under_construction.png");
		
		addComponent(imageInConstruction);
		addComponent(buttonReturn);
	}
	
	@Override
	public void activateComponent() {
		buttonReturn.setFocus(BJEngine.MAIN_FOCUS, true);
		engine.allowInput();
	}
	
	@Override
	public void doAction() {
		switch(stateToChange) {
		case BJEngine.MAIN_MENU_ID:
			buttonReturn.setFocus(BJEngine.MAIN_FOCUS, false);
			BTStatePanelManager.getStatePanel(BJEngine.MAIN_MENU_ID)
					.activate();
			break;
		default:
			break;
		}
	}

	private void createListeners() {		
		buttonReturn.addKeyboardListener(new BTKeyboardAdapter() {
			
			@Override
			public void keyPressed(BTKeyboardEvent event) {
				switch (event.getKeyCode())
				{
				case Keyboard.KEY_E :
					stateToChange = BJEngine.MAIN_MENU_ID;
					engine.blockInput();
					engine.closeGate(BJOnlineMenuState.this);
					break;
				}
			}
		});
	}
}
