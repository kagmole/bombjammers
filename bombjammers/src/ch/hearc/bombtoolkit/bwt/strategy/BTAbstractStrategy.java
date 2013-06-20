package ch.hearc.bombtoolkit.bwt.strategy;

import ch.hearc.bombtoolkit.bwt.BTAbstractComponent;

public abstract class BTAbstractStrategy {
	
	protected BTAbstractComponent component;

	public BTAbstractStrategy(BTAbstractComponent component) {
		this.component = component;
	}
}
