package com.ragebox.bombframework.bwt;

import java.util.Iterator;
import java.util.LinkedList;

import org.lwjgl.opengl.GL11;

public class BTAnimatedImage extends BTAbstractComponent {

	private int currentDelay;
	private int delayMS;
	
	private BTImage currentImage;
	private Iterator<BTImage> imageIterator;
	private LinkedList<BTImage> imagesList;

	public BTAnimatedImage(float x, float y, float width, float height,
			int delayMS, LinkedList<BTImage> imagesList) {
		super(x, y, width, height);
		
		this.imagesList = imagesList;
		currentDelay = 0;
		this.delayMS = delayMS;
	}
	
	
	
	public void reset() {
		imageIterator = imagesList.iterator();
		currentImage = imageIterator.next();
	}
	
	public void horizontalReverse() {
		for (BTImage image : imagesList) {
			image.horizontalReverse();
		}
	}
	
	@Override
	public void initComponent() {		
		for (BTImage image : imagesList) {
			image.init();
		}
		reset();
	}
	
	@Override
	public void updateComponent(int deltaTime) {
		currentDelay += deltaTime;
		
		if (currentDelay >= delayMS) {
			currentDelay -= delayMS;
			
			if (!imageIterator.hasNext()) {
				imageIterator = imagesList.iterator();
			}
			currentImage = imageIterator.next();
		}
	}
	
	@Override
	public void renderComponent() {
		/* Store the current model matrix */
		GL11.glPushMatrix();
		
		/* Translate to the right location */
		GL11.glTranslatef(x, y, 0);
		
		currentImage.render();
		
		/* Restore the model view matrix to prevent contamination */
		GL11.glPopMatrix();
	}
}
