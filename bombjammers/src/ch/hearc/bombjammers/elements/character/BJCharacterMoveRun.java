package ch.hearc.bombjammers.elements.character;

import ch.hearc.bombjammers.loaders.BJAnimationLoader;
import ch.hearc.bombtoolkit.bwt.BTAnimatedImage;

public class BJCharacterMoveRun extends BJCharacterMoveState {
	
	private BTAnimatedImage animationRunN;
	private BTAnimatedImage animationRunNE;
	private BTAnimatedImage animationRunE;
	private BTAnimatedImage animationRunSE;
	private BTAnimatedImage animationRunS;
	private BTAnimatedImage animationRunSW;
	private BTAnimatedImage animationRunW;
	private BTAnimatedImage animationRunNW;
	
	private BTAnimatedImage currentAnimation;

	protected BJCharacterMoveRun(BJCharacter character,
			BJCharacterMoveContext context) {
		super(character, context);
		
		BJAnimationLoader loader = BJAnimationLoader.getInstance();
		
		animationRunN = loader.getAnimation("move_n",
				character.getFileRepertory()+"sprite_sheet.png");
		animationRunNE = loader.getAnimation("move_ne",
				character.getFileRepertory()+"sprite_sheet.png");
		animationRunE = loader.getAnimation("move_e",
				character.getFileRepertory()+"sprite_sheet.png");
		animationRunSE = loader.getAnimation("move_se",
				character.getFileRepertory()+"sprite_sheet.png");
		animationRunS = loader.getAnimation("move_s",
				character.getFileRepertory()+"sprite_sheet.png");
		animationRunSW = loader.getAnimation("move_sw",
				character.getFileRepertory()+"sprite_sheet.png");
		animationRunW = loader.getAnimation("move_w",
				character.getFileRepertory()+"sprite_sheet.png");
		animationRunNW = loader.getAnimation("move_nw",
				character.getFileRepertory()+"sprite_sheet.png");
	}

	@Override
	public void init() {
		animationRunN.init();
		animationRunNE.init();
		animationRunE.init();
		animationRunSE.init();
		animationRunS.init();
		animationRunSW.init();
		animationRunW.init();
		animationRunNW.init();
	}
	
	@Override
	public void activate() {
		animationRunN.reset();
		animationRunNE.reset();
		animationRunE.reset();
		animationRunSE.reset();
		animationRunS.reset();
		animationRunSW.reset();
		animationRunW.reset();
		animationRunNW.reset();
	}
	
	@Override
	public void verifyAnimation(byte mask) {
		int modX = 0;
		int modY = 0;
		
		int speed = character.getSpeed();
		
		if ((mask & BJCharacter.HAS_BOMB) != 0) {
			context.changeContext(BJCharacterMoveContext.HAS_BOMB, mask);
		} else if ((mask & BJCharacter.MOVE) != 0) {
			if ((mask & BJCharacter.BUTTON_A) != 0) {
				context.changeContext(BJCharacterMoveContext.DASH, mask);
			} else {
				if ((mask & BJCharacter.MOVE_UP) != 0) {
					if ((mask & BJCharacter.MOVE_LEFT) != 0) {
						currentAnimation = animationRunNW;
						modX = -speed;
						modY = -speed;
					} else if ((mask & BJCharacter.MOVE_RIGHT) != 0) {
						currentAnimation = animationRunNE;
						modX = speed;
						modY = -speed;
					} else if ((mask & BJCharacter.MOVE_DOWN) == 0) {
						currentAnimation = animationRunN;
						modY = -speed;
					}
				} else if ((mask & BJCharacter.MOVE_DOWN) != 0) {
					if ((mask & BJCharacter.MOVE_LEFT) != 0) {
						currentAnimation = animationRunSW;
						modX = -speed;
						modY = speed;
					} else if ((mask & BJCharacter.MOVE_RIGHT) != 0) {
						currentAnimation = animationRunSE;
						modX = speed;
						modY = speed;
					} else if ((mask & BJCharacter.MOVE_UP) == 0) {
						currentAnimation = animationRunS;
						modY = speed;
					}
				} else if ((mask & BJCharacter.MOVE_LEFT) != 0) {
					currentAnimation = animationRunW;
					modX = -speed;
				} else if ((mask & BJCharacter.MOVE_RIGHT) != 0) {
					currentAnimation = animationRunE;
					modX = speed;
				}
				character.translate(modX, modY);
			}
		} else {
			context.changeContext(BJCharacterMoveContext.STILL, mask);
		}
	}

	@Override
	public void update(byte mask, int deltaTime) {
		verifyAnimation(mask);
		
		currentAnimation.update(deltaTime);
	}

	@Override
	public void render() {
		currentAnimation.render();
	}
}
