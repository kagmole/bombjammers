package com.icebox.bombjammers.elements.bomb;

import org.lwjgl.opengl.GL11;

import com.icebox.bombjammers.engine.state.ingame.BJIngameState;
import com.icebox.bombjammers.loaders.BJAnimationLoader;
import com.ragebox.bombframework.bwt.BTAbstractComponent;
import com.ragebox.bombframework.bwt.BTAnimatedImage;

public class BJBomb extends BTAbstractComponent {
	
	public static final byte MOVING_EAST = 1;
	public static final byte MOVING_WEST = 2;
	public static final byte MOVING = MOVING_EAST + MOVING_WEST;
	
	private byte mask;
	
	private int speedX;
	private int speedY;
	
	private BTAnimatedImage animationMoving;

	public BJBomb(float x, float y, float width, float height, 
			BJIngameState ingameState) {
		super(x, y, width, height);
		
		mask = 0;
		
		BJAnimationLoader loader = BJAnimationLoader.getInstance();
		
		loader.loadXMLFile("res/element/bomb/info.xml");
		animationMoving = loader.getAnimation("moving",
				"res/element/bomb/bomb.png");
	}

	@Override
	public void initComponent() {
		animationMoving.init();
	}
	
	@Override
	public void updateComponent(int deltaTime) {
		if ((mask & MOVING) != 0) {
			animationMoving.update(deltaTime);
			
			translateX(speedX);
			translateY(speedY);
		}
	}
	
	@Override
	public void renderComponent() {
		/* Store the current model matrix */
		GL11.glPushMatrix();
		
		/* Translate to the right location */
		GL11.glTranslatef(x, y, 0);
		
		if ((mask & MOVING) != 0) {
			animationMoving.render();
		}
		
		/* Restore the model view matrix to prevent contamination */
		GL11.glPopMatrix();
	}
	
	public void changeMask(byte maskId) {
		mask ^= maskId;
	}
	
	public byte getMask() {
		return mask;
	}
	
	public void setSpeed(int speedX, int speedY) {
		this.speedX = speedX;
		this.speedY = speedY;
	}

	public void inverseSpeedY() {
		speedY *= -1;
	}
}
