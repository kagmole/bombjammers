package ch.hearc.bombjammers.elements.character;

import java.util.LinkedList;

import ch.hearc.bombjammers.elements.bomb.BJBomb;
import ch.hearc.bombjammers.loaders.BJAnimationLoader;
import ch.hearc.bombtoolkit.bwt.BTAnimatedImage;

public class BJCharacterMoveHasBomb extends BJCharacterMoveState {
	
	private boolean reverse;
	
	private BTAnimatedImage animationHasBombN;
	private BTAnimatedImage animationHasBombNE;
	private BTAnimatedImage animationHasBombE;
	private BTAnimatedImage animationHasBombSE;
	private BTAnimatedImage animationHasBombS;
	
	private BTAnimatedImage currentAnimation;
	
	private long timeArrival;
	
	private LinkedList<BombExplodeListener> bombExplodeListenerList;

	protected BJCharacterMoveHasBomb(BJCharacter character,
			BJCharacterMoveContext context, boolean reverse) {
		super(character, context);
		
		this.reverse = reverse;
		
		BJAnimationLoader loader = BJAnimationLoader.getInstance();
		
		animationHasBombN = loader.getAnimation("with_bomb_n",
				character.getFileRepertory()+"sprite_sheet.png");
		animationHasBombNE = loader.getAnimation("with_bomb_ne",
				character.getFileRepertory()+"sprite_sheet.png");
		animationHasBombE = loader.getAnimation("with_bomb_e",
				character.getFileRepertory()+"sprite_sheet.png");
		animationHasBombSE = loader.getAnimation("with_bomb_se",
				character.getFileRepertory()+"sprite_sheet.png");
		animationHasBombS = loader.getAnimation("with_bomb_s",
				character.getFileRepertory()+"sprite_sheet.png");
		
		if (reverse) {
			animationHasBombN.horizontalReverse();
			animationHasBombNE.horizontalReverse();
			animationHasBombE.horizontalReverse();
			animationHasBombSE.horizontalReverse();
			animationHasBombS.horizontalReverse();
		}
		
		bombExplodeListenerList = new LinkedList<BombExplodeListener>();
	}
	
	@Override
	public void init() {
		animationHasBombN.init();
		animationHasBombNE.init();
		animationHasBombE.init();
		animationHasBombSE.init();
		animationHasBombS.init();
	}

	@Override
	public void activate() {
		animationHasBombN.reset();
		animationHasBombNE.reset();
		animationHasBombE.reset();
		animationHasBombSE.reset();
		animationHasBombS.reset();
		
		reset();
	}
	
	@Override
	public void verifyAnimation(byte mask) {		
		if ((mask & BJCharacter.BUTTON_A) != 0) {
			int speedX = character.getStrength();
			int speedY = character.getStrength();
			
			BJBomb bomb = character.getBomb();
			
			if (!reverse) {
				/* Nothing to do for speedX */
				bomb.setX(character.getX() + 64);
			} else {
				speedX *= -1;
				bomb.setX(character.getX());
			}
			
			// FIXME NullPointerException quand on bloque avec le personnage de droite au premier tir
			if (currentAnimation.equals(animationHasBombN)) {
				speedY *= -1;
				bomb.setY(character.getY() - 16);
			} else if (currentAnimation.equals(animationHasBombNE)) {
				speedY *= -0.5;
				bomb.setY(character.getY());
			} else if (currentAnimation.equals(animationHasBombS)) {
				/* Nothing to do for speedY */
				bomb.setY(character.getY() + 48);
			} else if (currentAnimation.equals(animationHasBombSE)) {
				speedY *= 0.5;
				bomb.setY(character.getY() + 32);
			} else {
				speedY = 0;
				bomb.setY(character.getY() + 16);
			}			
			character.getBomb().setSpeed(speedX, speedY);
			
			context.changeContext(BJCharacterMoveContext.SHOOT, mask);
		} else if (!reverse) {
			if ((mask & BJCharacter.MOVE_UP) != 0) {
				if ((mask & BJCharacter.MOVE_RIGHT) != 0) {
					currentAnimation = animationHasBombNE;
				} else {
					currentAnimation = animationHasBombN;
				}
			} else if ((mask & BJCharacter.MOVE_DOWN) != 0) {
				if ((mask & BJCharacter.MOVE_RIGHT) != 0) {
					currentAnimation = animationHasBombSE;
				} else {
					currentAnimation = animationHasBombS;
				}
			} else {
				currentAnimation = animationHasBombE;
			}
		} else {
			if ((mask & BJCharacter.MOVE_UP) != 0) {
				if ((mask & BJCharacter.MOVE_LEFT) != 0) {
					currentAnimation = animationHasBombNE;
				} else {
					currentAnimation = animationHasBombN;
				}
			} else if ((mask & BJCharacter.MOVE_DOWN) != 0) {
				if ((mask & BJCharacter.MOVE_LEFT) != 0) {
					currentAnimation = animationHasBombSE;
				} else {
					currentAnimation = animationHasBombS;
				}
			} else {
				currentAnimation = animationHasBombE;
			}
		}
	}

	@Override
	public void update(byte mask, int deltaTime) {
		verifyAnimation(mask);
		
		currentAnimation.update(deltaTime);
		
		// vérifie si la bombe doit explosé
		if((System.currentTimeMillis() - timeArrival) / 1000 > 1) {
			for (BombExplodeListener listener : bombExplodeListenerList) {
				listener.bombExplosed();
			}
			reset();
		}
	}

	@Override
	public void render() {
		currentAnimation.render();
	}

	public void addBombExplodeListener(BombExplodeListener listener) {
		bombExplodeListenerList.add(listener);
	}

	public void reset() {
		timeArrival = System.currentTimeMillis();
	}
}
