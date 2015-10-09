package com.ragebox.bombframework.bwt;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import com.icebox.bombjammers.tools.FontTools;

public class BTButton extends BTAbstractComponent {
	
	private String text;
	private String imagePath;
	private Texture texture;
	
	public BTButton(float x, float y, float width, float height, String text,
			String imagePath) {
		super(x, y, width, height);
		
		this.width = width;
		this.height = height;
		this.text = text;
		this.imagePath = imagePath;
	}

	public BTButton(float x, float y, float width, float height, String text) {
		this(x, y, width, height, text, "./res/component/button/button.png");
	}
	
	public void changeImage(String imagePath) {
		
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
	
	@Override
	public void initComponent() {
		try {
			texture = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream(imagePath));
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
		
		/* Change color filter */
		if (!focused) {
			GL11.glColor3f(0.2f, 0.2f, 0.2f);
		}
		
		/* Bind to the appropriate texture for this sprite */
		texture.bind();
		
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
		
		/* Draw text */
		FontTools.getInstance().getButtonFont().drawString(
				20, 7, text, Color.white);
		
		/* Reset color filter */
		if (!focused) {
			GL11.glColor3f(1.0f, 1.0f, 1.0f);
		}
		
		/* Restore the model view matrix to prevent contamination */
		GL11.glPopMatrix();
		
		/* Show the focus */
		if (focused) {
			for (Integer focusId : listFocusId) {
				BTAnimatedImage image = BTKeyboardFocusManager.getFocusImage(focusId);
				
				if (image != null) {
					image.render();
				}
			}
		}
	}

	public void setText(String text) {
		this.text = text;
	}
}
