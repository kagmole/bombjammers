package com.ragebox.bombframework.bwt;

public class BTBlinkingImage extends BTImage {
	
	private boolean isVisible;
	
	private int currentDelay;
	private int delayMS;

	public BTBlinkingImage(float x, float y, float width, float height,
			String texturePath, int delayMS) {
		super(x, y, width, height, texturePath);
		
		isVisible = true;
		currentDelay = 0;
		this.delayMS = delayMS;
	}
	
	@Override
	public void updateComponent(int deltaTime) {
		currentDelay += deltaTime;
		
		if (currentDelay >= delayMS) {
			currentDelay -= delayMS;
			
			isVisible = !isVisible;
		}
	}
	
	@Override
	public void renderComponent() {
		if (isVisible) {
			super.renderComponent();
		}
	}

}
