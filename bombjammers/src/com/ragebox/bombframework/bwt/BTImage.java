package com.ragebox.bombframework.bwt;

import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class BTImage extends BTAbstractComponent {

	private boolean isHorizontalReversted;
	
	private Texture texture;
	private InputStream is;
	
	public BTImage(float x, float y, float width, float height, 
			String texturePath) {
		super(x, y, width, height);
		
		this.width = width;
		this.height = height;
		
		is = ResourceLoader.getResourceAsStream(texturePath);
	}
	
	public BTImage(float x, float y, float width, float height,
			InputStream is) {
		super(x, y, width, height);
		
		this.width = width;
		this.height = height;
		
		this.is = is;
	}

	public float getWidth() {
		return width * texture.getWidth();
	}
	
	public void setWidth(float width) {
		this.width = width / texture.getWidth();
	}
	
	public float getHeight() {
		return height * texture.getHeight();
	}
	
	public void setHeight(float height) {
		this.height = height / texture.getHeight();
	}
	
	public void horizontalReverse() {
		isHorizontalReversted = !isHorizontalReversted;
	}
	
	@Override
	public void initComponent() {
		try {
			texture = TextureLoader.getTexture("PNG", is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setWidth(width);
		setHeight(height);
	}
	
	@Override
	public void renderComponent() {
		/* Store the current model matrix */
		GL11.glPushMatrix();
		
		/* Bind to the appropriate texture for this sprite */
		texture.bind();
		
		/* Translate to the right location */
		if (isHorizontalReversted) {
			GL11.glTranslatef(texture.getImageWidth() + x, y, 0);
		} else {
			GL11.glTranslatef(x, y, 0);
		}
		
		/* Draw a quad texture to match the sprite */
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(0, 0);
			
			if (isHorizontalReversted) {
				GL11.glTexCoord2f(1, 0);
				GL11.glVertex2f(-width, 0);
				
				GL11.glTexCoord2f(1, 1);
				GL11.glVertex2f(-width, height);
			} else {
				GL11.glTexCoord2f(1, 0);
				GL11.glVertex2f(width, 0);
				
				GL11.glTexCoord2f(1, 1);
				GL11.glVertex2f(width, height);
			}
			
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(0, height);
		}
		GL11.glEnd();
		
		/* Restore the model view matrix to prevent contamination */
		GL11.glPopMatrix();
	}
}
