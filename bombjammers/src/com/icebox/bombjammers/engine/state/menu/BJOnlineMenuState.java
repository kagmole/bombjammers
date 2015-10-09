package com.icebox.bombjammers.engine.state.menu;

import org.lwjgl.input.Keyboard;

import com.icebox.bombjammers.engine.BJEngine;
import com.icebox.bombjammers.util.BJActionable;
import com.ragebox.bombframework.bwt.BTButton;
import com.ragebox.bombframework.bwt.BTImage;
import com.ragebox.bombframework.bwt.BTStatePanel;
import com.ragebox.bombframework.bwt.BTStatePanelManager;
import com.ragebox.bombframework.bwt.BTSwitchPanel;
import com.ragebox.bombframework.bwt.event.BTKeyboardAdapter;
import com.ragebox.bombframework.bwt.event.BTKeyboardEvent;

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
