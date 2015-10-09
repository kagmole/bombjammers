package com.icebox.bombjammers.elements.character;

import com.icebox.bombjammers.loaders.BJAnimationLoader;
import com.ragebox.bombframework.bwt.BTAnimatedImage;

public class BJCharacterMoveBlock extends BJCharacterMoveState {
	
	private int blockingTime;
	
	private BTAnimatedImage animationBlock;

	protected BJCharacterMoveBlock(BJCharacter character,
			BJCharacterMoveContext context, boolean reverse) {
		super(character, context);
		
		BJAnimationLoader loader = BJAnimationLoader.getInstance();
	
		animationBlock = loader.getAnimation("block",
				character.getFileRepertory()+"sprite_sheet.png");
		
		if (reverse) {
			animationBlock.horizontalReverse();
		}
	}
	
	@Override
	public void init() {
		animationBlock.init();
	}

	@Override
	public void activate() {
		animationBlock.reset();
		
		blockingTime = 1000;
	}

	@Override
	public void update(byte mask, int deltaTime) {
		animationBlock.update(deltaTime);
		
		if ((mask & BJCharacter.HAS_BOMB) != 0) {
			context.changeContext(BJCharacterMoveContext.HAS_BOMB, mask);
		} else if (blockingTime > 0) {
			blockingTime -= deltaTime;
		} else {
			context.changeContext(BJCharacterMoveContext.STILL, mask);
		}
	}

	@Override
	public void render() {
		animationBlock.render();
	}
}
