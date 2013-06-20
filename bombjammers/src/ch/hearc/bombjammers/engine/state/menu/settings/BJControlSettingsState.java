package ch.hearc.bombjammers.engine.state.menu.settings;

import ch.hearc.bombjammers.engine.BJEngine;
import ch.hearc.bombtoolkit.bwt.BTStatePanel;
import ch.hearc.bombtoolkit.bwt.BTSwitchPanel;

public class BJControlSettingsState extends BTStatePanel {
	
	private BJEngine engine;

	public BJControlSettingsState(String nameId, BTSwitchPanel parent,
			BJEngine engine) {
		super(nameId, parent);
		
		this.engine = engine;
	}

}
