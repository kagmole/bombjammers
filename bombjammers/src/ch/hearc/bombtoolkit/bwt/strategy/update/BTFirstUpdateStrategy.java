package ch.hearc.bombtoolkit.bwt.strategy.update;

import ch.hearc.bombtoolkit.bwt.BTAbstractComponent;

public class BTFirstUpdateStrategy extends BTAbstractUpdateStrategy {

	public BTFirstUpdateStrategy(BTAbstractComponent component) {
		super(component);
	}

	@Override
	public void executeUpdate(int deltaTime) {
		executeNextUpdateStrategy(deltaTime);
	}
}
