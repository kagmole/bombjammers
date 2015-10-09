package com.icebox.bombjammers.engine.state.menu.settings;

import com.icebox.bombjammers.engine.BJEngine;
import com.ragebox.bombframework.bwt.BTStatePanel;
import com.ragebox.bombframework.bwt.BTSwitchPanel;

public class BJControlSettingsState extends BTStatePanel {
	
	private BJEngine engine;

	public BJControlSettingsState(String nameId, BTSwitchPanel parent,
			BJEngine engine) {
		super(nameId, parent);
		
		this.engine = engine;
	}

}
