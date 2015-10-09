package com.icebox.bombjammers.engine.state.menu.settings;

import com.icebox.bombjammers.engine.BJEngine;
import com.ragebox.bombframework.bwt.BTSwitchPanel;

public class BJSettingsSwitch extends BTSwitchPanel {
	
	private BJEngine engine;

	public BJSettingsSwitch(BTSwitchPanel parent, BJEngine engine) {
		super(parent);
		
		this.engine = engine;
		
		initSwitchPanel();
	}

	private void initSwitchPanel() {
		addContainer(new BJControlSettingsState(
				BJEngine.CONTROL_SETTINGS_ID, this, engine));
		
		addContainer(new BJSoundsVideoSettingsState(
				BJEngine.SOUNDS_VIDEO_SETTINGS_ID, this, engine));
	}

}
