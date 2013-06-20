package ch.hearc.bombjammers.engine.state.menu.settings;

import ch.hearc.bombjammers.engine.BJEngine;
import ch.hearc.bombtoolkit.bwt.BTSwitchPanel;

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
