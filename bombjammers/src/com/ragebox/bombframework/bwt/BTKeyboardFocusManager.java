package com.ragebox.bombframework.bwt;

import java.util.HashMap;
import java.util.LinkedList;

public class BTKeyboardFocusManager {
	
	private static HashMap<Integer, LinkedList<BTAbstractComponent>> MAP_FOCUSED_COMPONENT =
			new HashMap<Integer, LinkedList<BTAbstractComponent>>();
	
	private static HashMap<Integer, BTAnimatedImage> MAP_IMAGE_FOCUS = 
			new HashMap<Integer, BTAnimatedImage>();
	
	public static void createFocus(int focusId) {
		MAP_FOCUSED_COMPONENT.put(focusId, new LinkedList<BTAbstractComponent>());
	}
	
	public static void destroyFocus(int focusId) {
		MAP_FOCUSED_COMPONENT.remove(focusId);
	}
	
	public static void removeAllFocus() {
		MAP_FOCUSED_COMPONENT.clear();
	}
	
	public static void removeAllFocusedComponent() {
		for (LinkedList<BTAbstractComponent> listFocusedComponent : MAP_FOCUSED_COMPONENT.values()) {
			listFocusedComponent.clear();
		}
	}
	
	public static void linkImageToFocus(int focusId, BTAnimatedImage image) {
		image.init();
		
		MAP_IMAGE_FOCUS.put(focusId, image);
	}
	
	static void addFocusedComponent(int focusId, BTAbstractComponent component) {
		MAP_FOCUSED_COMPONENT.get(focusId).add(component);
	}
	
	static void removeFocusedComponent(int focusId, BTAbstractComponent component) {
		MAP_FOCUSED_COMPONENT.get(focusId).remove(component);
	}
	
	static HashMap<Integer, LinkedList<BTAbstractComponent>> getFocusedComponent() {
		return MAP_FOCUSED_COMPONENT;
	}
	
	static BTAnimatedImage getFocusImage(int focusId) {
		return MAP_IMAGE_FOCUS.get(focusId);
	}
	
	static void update(int deltaTime) {
		for (BTAnimatedImage image : MAP_IMAGE_FOCUS.values()) {
			image.update(deltaTime);
		}
	}
}
