package ch.hearc.bombtoolkit.bwt.strategy.update;

import ch.hearc.bombtoolkit.bwt.BTAbstractComponent;

public class BTNoUpdateStrategy extends BTAbstractUpdateStrategy {

	public BTNoUpdateStrategy(BTAbstractComponent component) {
		super(component);
	}

	@Override
	public void executeUpdate(int deltaTime) {}
}
