package ch.hearc.bombjammers.elements.character;

import java.util.HashMap;

public class BJCharacterMoveContext {

	private static final int STATE_FIRST = 0;
	
	protected static final int BLOCK = STATE_FIRST;
	protected static final int DASH = 1 + STATE_FIRST;
	protected static final int HAS_BOMB = 2 + STATE_FIRST;
	protected static final int RUN = 3 + STATE_FIRST;
	protected static final int STILL = 4 + STATE_FIRST;
	protected static final int SHOOT = 5 + STATE_FIRST;
	
	private BJCharacterMoveState currentState;
	
	private HashMap<Integer, BJCharacterMoveState> stateMap;
	
	public BJCharacterMoveContext(BJCharacter character, boolean reverse) {
		stateMap = new HashMap<Integer, BJCharacterMoveState>();
		
		stateMap.put(BLOCK, new BJCharacterMoveBlock(character, this, reverse));
		stateMap.put(DASH, new BJCharacterMoveDash(character, this));
		stateMap.put(HAS_BOMB, new BJCharacterMoveHasBomb(character, this,
				reverse));
		stateMap.put(RUN, new BJCharacterMoveRun(character, this));
		stateMap.put(STILL, new BJCharacterMoveStill(character, this, reverse));
		stateMap.put(SHOOT, new BJCharacterMoveShoot(character, this, reverse));
		
		currentState = stateMap.get(STILL);
		currentState.activate();
	}
	
	public void initContext() {
		for (BJCharacterMoveState moveState : stateMap.values()) {
			moveState.init();
		}
	}
	
	public void addBombExplodeListener(BombExplodeListener listener) {
		((BJCharacterMoveHasBomb) stateMap.get(HAS_BOMB)).addBombExplodeListener(listener);
	}
	
	public void reset() {
		((BJCharacterMoveHasBomb) stateMap.get(HAS_BOMB)).reset();
	}
	
	public void changeContext(int id, byte mask) {
		currentState = stateMap.get(id);
		currentState.activate();
		currentState.verifyAnimation(mask);
	}
	
	public void updateContext(byte mask, int deltaTime) {
		currentState.update(mask, deltaTime);
	}

	public void renderContext() {
		currentState.render();
	}
}
