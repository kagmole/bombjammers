package ch.hearc.bombtoolkit.bwt.strategy.update;

import ch.hearc.bombtoolkit.bwt.BTAbstractComponent;

public class BTUpdateStrategy extends BTAbstractUpdateStrategy {

	public BTUpdateStrategy(BTAbstractComponent component) {
		super(component);
	}

	@Override
	public void executeUpdate(int deltaTime) {
		component.updateComponent(deltaTime);
	}
}
