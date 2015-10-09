package com.icebox.bombjammers.elements.character;

public abstract class BJCharacterMoveState {
	
	protected BJCharacter character;
	protected BJCharacterMoveContext context;

	protected BJCharacterMoveState(BJCharacter character,
			BJCharacterMoveContext context) {
		this.character = character;
		this.context = context;
	}
	
	public void change(int id, byte mask) {
		context.changeContext(id, mask);
	}
	
	public abstract void init();
	public abstract void activate();
	public abstract void update(byte mask, int deltaTime);
	public abstract void render();

	public void verifyAnimation(byte mask) {}
}
