package com.ragebox.bombframework.bwt.event;

public class BTKeyboardEvent {

	public static final int KEY_FIRST = 0;
	
	public static final int KEY_PRESSED = KEY_FIRST;
	public static final int KEY_RELEASED = 1 + KEY_FIRST;
	
	public static final int KEY_LAST = 2 + KEY_FIRST;
	
	private int id;
	private int focusId;
	private int keyCode;
	
	public BTKeyboardEvent(int id, int focusId, int keyCode) {
		this.id = id;
		this.focusId = focusId;
		this.keyCode = keyCode;
	}
	
	public int getID() {
		return id;
	}
	
	public int getFocusId() {
		return focusId;
	}
	
	public int getKeyCode() {
		return keyCode;
	}
}
