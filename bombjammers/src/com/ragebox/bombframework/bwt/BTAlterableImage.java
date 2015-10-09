package com.ragebox.bombframework.bwt;

public class BTAlterableImage extends BTImage {
	
	private boolean translating;
	
	private float translateX;
	private float translateY;
	
	private int currentTranslateDelay;
	private int translateDelayMS;	

	public BTAlterableImage(float x, float y, float width, float height,
			String texturePath) {
		super(x, y, width, height, texturePath);
		
		translating = false;
		translateX = 0;
		translateY = 0;
		currentTranslateDelay = 0;
		translateDelayMS = 0;
	}
	
	public void translateOverTime(float x, float y, int translateDelayMS) {
		translating = true;
		
		translateX = x;
		translateY = y;
		
		currentTranslateDelay = 0;
		this.translateDelayMS = translateDelayMS;
	}
	
	public boolean isTranslating() {
		return translating;
	}
	
	@Override
	public void updateComponent(int deltaTime) {
		if (translating) {
			if (currentTranslateDelay + deltaTime < translateDelayMS) {
				currentTranslateDelay += deltaTime;
				
				x += translateX / translateDelayMS * deltaTime;	
				y += translateY / translateDelayMS * deltaTime;
			} else {
				int deltaDelay = translateDelayMS - currentTranslateDelay;
				
				x += translateX / translateDelayMS * deltaDelay;
				y += translateY / translateDelayMS * deltaDelay;
				
				translating = false;
			}
		}
	}
}
