package com.ragebox.bombframework.bwt;

import java.util.LinkedList;

public class BTRootSwitchPanel extends BTSwitchPanel {
	
	private LinkedList<BTAbstractContainer> listContainerAlreadyInit;

	public BTRootSwitchPanel(BTSwitchPanel parent) {
		super(parent);
		
		listContainerAlreadyInit = new LinkedList<BTAbstractContainer>();
	}
	
	public BTRootSwitchPanel() {
		this(null);
	}
	
	@Override
	protected void changeCurrentPanel(BTAbstractContainer panel) {
		// XXX Destruction non effective
		// Si on ne pointe pas sur la même référence, c'est un nouveau panel
//		if (!panel.equals(currentPanel)) {
//			panel.init();
//			
//			BTAbstractContainer oldPanel = currentPanel;
//				
//			currentPanel = panel;
//			
//			if (oldPanel != null) {
//				oldPanel.destroy();
//			}
//		}
		
		// XXX Fix temporaire : on empêche la superposition d'init d'un état
		boolean found = false;
		
		for (BTAbstractContainer btContainer : listContainerAlreadyInit) {
			if (btContainer.equals(panel)) {
				found = true;
			}
		}
		
		// XXX Fix temporaire : premier appel -> initialisation
		if (!found) {
			listContainerAlreadyInit.add(panel);
			
			panel.init();
		}
		currentPanel = panel;
	}
	
	@Override
	public void init() {
		initComponent();
		
		for (BTAbstractComponent btComponent : btComponentList) {
			btComponent.init();
		}
	}
	
	@Override
	public void destroy() {
		currentPanel.destroy();
	}
}
