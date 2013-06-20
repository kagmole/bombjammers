package ch.hearc.bombjammers.elements.character;

import java.io.File;

import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

import ch.hearc.bombjammers.loaders.BJAnimationLoader;
import ch.hearc.bombjammers.loaders.BJPropertiesLoader;
import ch.hearc.bombtoolkit.bwt.BTAnimatedImage;

public class BJCharacterMoveDash extends BJCharacterMoveState {
	
	private int accelerationTime;
	private int decelerationTime;
	
	private float modX;
	private float modY;
	
	private BTAnimatedImage animationDashN;
	private BTAnimatedImage animationDashNE;
	private BTAnimatedImage animationDashE;
	private BTAnimatedImage animationDashSE;
	private BTAnimatedImage animationDashS;
	private BTAnimatedImage animationDashSW;
	private BTAnimatedImage animationDashW;
	private BTAnimatedImage animationDashNW;
	
	private BTAnimatedImage currentAnimation;
	
	private Sound dash;
	private double volEffects = Double.valueOf(BJPropertiesLoader.getInstance().
			getProperty(BJPropertiesLoader.VOLUME_EFFECTS))/100;

	protected BJCharacterMoveDash(BJCharacter character,
			BJCharacterMoveContext context) {
		super(character, context);
		
		BJAnimationLoader loader = BJAnimationLoader.getInstance();
		
		animationDashN = loader.getAnimation("dash_n",
				character.getFileRepertory()+"sprite_sheet.png");
		animationDashNE = loader.getAnimation("dash_ne",
				character.getFileRepertory()+"sprite_sheet.png");
		animationDashE = loader.getAnimation("dash_e",
				character.getFileRepertory()+"sprite_sheet.png");
		animationDashSE = loader.getAnimation("dash_se",
				character.getFileRepertory()+"sprite_sheet.png");
		animationDashS = loader.getAnimation("dash_s",
				character.getFileRepertory()+"sprite_sheet.png");
		animationDashSW = loader.getAnimation("dash_sw",
				character.getFileRepertory()+"sprite_sheet.png");
		animationDashW = loader.getAnimation("dash_w",
				character.getFileRepertory()+"sprite_sheet.png");
		animationDashNW = loader.getAnimation("dash_nw",
				character.getFileRepertory()+"sprite_sheet.png");
		
		dash = TinySound.loadSound(new File("./res/sound/dash.wav"));
	}
	
	@Override
	public void init() {
		animationDashN.init();
		animationDashNE.init();
		animationDashE.init();
		animationDashSE.init();
		animationDashS.init();
		animationDashSW.init();
		animationDashW.init();
		animationDashNW.init();
	}

	@Override
	public void activate() {
		animationDashN.reset();
		animationDashNE.reset();
		animationDashE.reset();
		animationDashSE.reset();
		animationDashS.reset();
		animationDashSW.reset();
		animationDashW.reset();
		animationDashNW.reset();
		
		accelerationTime = 40 * character.getWeight();
		decelerationTime = 60 * character.getWeight();
		
		modX = (float) (character.getSpeed() * 2.5);
		modY = (float) (character.getSpeed() * 2.5);
		
		dash.play(volEffects);
	}
	
	@Override
	public void verifyAnimation(byte mask) {
		if ((mask & BJCharacter.MOVE) != 0) {
			if ((mask & BJCharacter.MOVE_UP) != 0) {
				if ((mask & BJCharacter.MOVE_LEFT) != 0) {
					currentAnimation = animationDashNW;
					modX *= -1;
					modY *= -1;
				} else if ((mask & BJCharacter.MOVE_RIGHT) != 0) {
					currentAnimation = animationDashNE;
					modY *= -1;
				} else if ((mask & BJCharacter.MOVE_DOWN) == 0) {
					currentAnimation = animationDashN;
					modX = 0;
					modY *= -1;
				}
			} else if ((mask & BJCharacter.MOVE_DOWN) != 0) {
				if ((mask & BJCharacter.MOVE_LEFT) != 0) {
					currentAnimation = animationDashSW;
					modX *= -1;
				} else if ((mask & BJCharacter.MOVE_RIGHT) != 0) {
					currentAnimation = animationDashSE;
				} else if ((mask & BJCharacter.MOVE_UP) == 0) {
					currentAnimation = animationDashS;
					modX = 0;
				}
			} else if ((mask & BJCharacter.MOVE_LEFT) != 0) {
				currentAnimation = animationDashW;
				modX *= -1;
				modY = 0;
			} else if ((mask & BJCharacter.MOVE_RIGHT) != 0) {
				currentAnimation = animationDashE;
				modY = 0;
			}
		}
	}

	@Override
	public void update(byte mask, int deltaTime) {
		currentAnimation.update(deltaTime);
		
		character.translateX(modX);
		character.translateY(modY);
		
		if (accelerationTime > 0) {
			accelerationTime -= deltaTime;
			
			if (accelerationTime <= 0) {
				decelerationTime -= accelerationTime;
				
				modX /= 5;
				modY /= 5;
			}
		} else if (decelerationTime > 0) {
			decelerationTime -= deltaTime;
			
		} else if ((mask & BJCharacter.HAS_BOMB) != 0) {
			context.changeContext(BJCharacterMoveContext.HAS_BOMB, mask);
		} else if ((mask & BJCharacter.MOVE) != 0) {
			context.changeContext(BJCharacterMoveContext.RUN, mask);
		} else {
			context.changeContext(BJCharacterMoveContext.STILL, mask);
		}
	}

	@Override
	public void render() {
		currentAnimation.render();
	}

}
