package com.ragebox.bombframework.bwt;

import java.util.HashMap;
import java.util.Map;

public class BTStatePanelManager {
	
	private static final Map<String, BTStatePanel> STATE_PANEL_MAP =
					new HashMap<String, BTStatePanel>();

	public static void addStatePanel(String nameId, BTStatePanel panel) {
		STATE_PANEL_MAP.put(nameId, panel);
	}
	
	public static void removeStatePanel(String nameId) {
		STATE_PANEL_MAP.remove(nameId);
	}
	
	public static BTStatePanel getStatePanel(String nameId) {		
		return STATE_PANEL_MAP.get(nameId);
	}
}
