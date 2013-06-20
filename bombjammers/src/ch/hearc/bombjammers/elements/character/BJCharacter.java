package ch.hearc.bombjammers.elements.character;

import org.lwjgl.opengl.GL11;

import ch.hearc.bombjammers.elements.bomb.BJBomb;
import ch.hearc.bombjammers.loaders.BJAnimationLoader;
import ch.hearc.bombtoolkit.bwt.BTAbstractComponent;
import ch.hearc.bombtoolkit.bwt.BTImage;

public class BJCharacter extends BTAbstractComponent {
	
	public static final byte MOVE = 15;
	public static final byte MOVE_UP = 1;
	public static final byte MOVE_RIGHT = 2;
	public static final byte MOVE_DOWN = 4;
	public static final byte MOVE_LEFT = 8;
	
	public static final byte BUTTON_A = 16;
	public static final byte BUTTON_B = 32;
	
	public static final byte HAS_BOMB = 64;
	
	private byte mask;
	
	private int speed;
	private int strength;
	private int weight;
	
	private BJBomb bomb;
	
	private BJCharacterMoveContext moveContext;
	
	private String fileRepertory;
	
	private BTImage portraitBig;
	private BTImage portraitSmall;
	
	private String name;

	public BJCharacter(float x, float y, float width, float height, int speed,
			int strength, int weight, String name, BTImage portraitBig,
			BTImage portraitSmall, boolean reverse, String fileRepertory) {
		super(x, y, width, height);
		
		mask = 0;
		bomb = null;
		
		this.fileRepertory = fileRepertory;

		this.speed = speed / 10;
		this.strength = strength / 5;
		this.weight = weight / 10;
		this.name = name;
		
		BJAnimationLoader.getInstance().loadXMLFile(
				fileRepertory+"./animation.xml");
		moveContext = new BJCharacterMoveContext(this, reverse);
		
		this.portraitBig = portraitBig;
		this.portraitSmall = portraitSmall;
	}
	
	@Override
	public void initComponent() {
		moveContext.initContext();
	}

	@Override
	public void updateComponent(int deltaTime) {
		moveContext.updateContext(mask, deltaTime);
	}
	
	@Override
	public void renderComponent() {
		/* Store the current model matrix */
		GL11.glPushMatrix();
		
		/* Translate to the right location */
		GL11.glTranslatef(x, y, 0);
		
		moveContext.renderContext();
		
		/* Restore the model view matrix to prevent contamination */
		GL11.glPopMatrix();
	}
	
	public void addBombExplodeListener(BombExplodeListener listener) {
		moveContext.addBombExplodeListener(listener);
	}
	
	public void reset() {
		moveContext.reset();
	}
	
	public void changeMask(int maskId) {
		mask ^= maskId;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public int getStrength() {
		return strength;
	}

	public int getWeight() {
		return weight;
	}

	public void setBomb(BJBomb bomb) {
		this.bomb = bomb;
	}
	public BJBomb getBomb() {
		return bomb;
	}

	public void block() {
				
	}

	public void unblock() {
		// TODO JDFAJLFJAS
		
	}
	
	public String getFileRepertory(){
		return this.fileRepertory;
	}
}
