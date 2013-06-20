package ch.hearc.bombtoolkit.bwt;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class BTSlider extends BTAbstractComponent {
	
	private float buttonX;
	private float buttonY;
	
	private int maxValue;
	private int value;
	
	private Texture lineTexture;
	private Texture buttonTexture;

	public BTSlider(float x, float y, float width, float height,
			int maxValue, int value) {
		super(x, y, width, height);
		
		buttonX = x;
		buttonY = y;
		
		this.maxValue = maxValue;
		this.value = value;
	}
	
	public float getWidth() {
		return width * lineTexture.getWidth();
	}
	
	public void setWidth(float width) {
		this.width = width / lineTexture.getWidth();
	}
	
	public float getHeight() {
		return height * lineTexture.getHeight();
	}
	
	public void setHeight(float height) {
		this.height = height / lineTexture.getHeight();
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		if (value < 0) {
			this.value = 0;
		} else if (value > maxValue) {
			this.value = maxValue;
		} else {
			this.value = value;
		}
		
		buttonX = x + ((float) this.value / maxValue) * lineTexture.getImageWidth()
				- buttonTexture.getImageWidth() / 2;
	}
	
	@Override
	public void initComponent() {
		try {
			lineTexture = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream(
							"res/component/slider/slider.png"));
			
			buttonTexture = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream(
							"res/component/slider/button.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setWidth(width);
		setHeight(height);
		setValue(value);
		
		buttonY += lineTexture.getImageHeight() / 2
				- buttonTexture.getImageHeight() / 2;
	}
	
	@Override
	public void renderComponent() {
		/* Store the current model matrix */
		GL11.glPushMatrix();
		
		/**
		 * line
		 */
		
		/* Bind to the appropriate texture for this sprite */
		lineTexture.bind();
		
		/* Translate to the right location */
		GL11.glTranslatef(x, y, 0);
		
		/* Draw a quad texture to match the sprite */
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(0, 0);
			
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(width, 0);
			
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(width, height);
			
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(0, height);
		}
		GL11.glEnd();
		
		GL11.glTranslatef(-x, -y, 0);
		
		/**
		 * button
		 */
		
		/* Bind to the appropriate texture for this sprite */
		buttonTexture.bind();
		
		/* Translate to the right location */
		GL11.glTranslatef(buttonX, buttonY, 0);
		
		/* Draw a quad texture to match the sprite */
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(0, 0);
			
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(buttonTexture.getTextureWidth(), 0);
			
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(buttonTexture.getTextureWidth(),
					buttonTexture.getTextureHeight());
			
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(0, buttonTexture.getTextureHeight());
		}
		GL11.glEnd();
		
		/* Restore the model view matrix to prevent contamination */
		GL11.glPopMatrix();
	}
}
