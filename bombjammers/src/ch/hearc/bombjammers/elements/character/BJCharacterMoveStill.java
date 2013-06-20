package ch.hearc.bombjammers.elements.character;

import ch.hearc.bombjammers.loaders.BJAnimationLoader;
import ch.hearc.bombtoolkit.bwt.BTAnimatedImage;

public class BJCharacterMoveStill extends BJCharacterMoveState {
	
	private BTAnimatedImage animationStill;

	protected BJCharacterMoveStill(BJCharacter character,
			BJCharacterMoveContext context, boolean reverse) {
		super(character, context);
		
		BJAnimationLoader loader = BJAnimationLoader.getInstance();
		
		animationStill = loader.getAnimation("without_bomb",
				character.getFileRepertory()+"sprite_sheet.png");
		
		if (reverse) {
			animationStill.horizontalReverse();
		}
	}
	
	@Override
	public void init() {
		animationStill.init();
	}

	@Override
	public void activate() {
		animationStill.reset();
	}

	@Override
	public void update(byte mask, int deltaTime) {
		
		if ((mask & BJCharacter.HAS_BOMB) != 0) {
			context.changeContext(BJCharacterMoveContext.HAS_BOMB, mask);
		} else if ((mask & BJCharacter.MOVE) != 0) {
			context.changeContext(BJCharacterMoveContext.RUN, mask);
		} else if ((mask & BJCharacter.BUTTON_A) != 0) {
			context.changeContext(BJCharacterMoveContext.BLOCK, mask);
		} else {
			animationStill.update(deltaTime);
		}
	}

	@Override
	public void render() {
		animationStill.render();
	}
}
