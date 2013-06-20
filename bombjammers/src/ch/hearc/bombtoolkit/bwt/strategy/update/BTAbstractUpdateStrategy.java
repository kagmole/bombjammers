package ch.hearc.bombtoolkit.bwt.strategy.update;

import ch.hearc.bombtoolkit.bwt.BTAbstractComponent;
import ch.hearc.bombtoolkit.bwt.strategy.BTAbstractStrategy;
import ch.hearc.bombtoolkit.core.BTUpdatable;

public abstract class BTAbstractUpdateStrategy extends BTAbstractStrategy
		implements BTUpdatable {
	
	protected BTAbstractUpdateStrategy nextUpdateStrategy;
	protected BTAbstractUpdateStrategy previousUpdateStrategy;

	public BTAbstractUpdateStrategy(BTAbstractComponent component) {
		super(component);
		
		nextUpdateStrategy = null;
		previousUpdateStrategy = null;
	}
	
	public void executeNextUpdateStrategy(int deltaTime) {
		nextUpdateStrategy.executeUpdate(deltaTime);
	}
	
	public void addUpdateStrategy(BTAbstractUpdateStrategy updateStrategy) {
		if (nextUpdateStrategy != null) {
			nextUpdateStrategy.previousUpdateStrategy = updateStrategy;
		}		
		updateStrategy.nextUpdateStrategy = nextUpdateStrategy;
		updateStrategy.previousUpdateStrategy = this;
		
		nextUpdateStrategy = updateStrategy;
	}

	public void removeUpdateStrategy(BTAbstractUpdateStrategy updateStrategy) {
		if (equals(updateStrategy)) {
			previousUpdateStrategy.nextUpdateStrategy = nextUpdateStrategy;
			nextUpdateStrategy.previousUpdateStrategy = previousUpdateStrategy;
		} else if (nextUpdateStrategy != null) {
			nextUpdateStrategy.removeUpdateStrategy(updateStrategy);
		}
	}
	
	public void changeLastStrategy(BTAbstractUpdateStrategy updateStrategy) {
		if (nextUpdateStrategy == null) {
			previousUpdateStrategy.nextUpdateStrategy = updateStrategy;
			updateStrategy.previousUpdateStrategy = previousUpdateStrategy;
		} else {
			nextUpdateStrategy.changeLastStrategy(updateStrategy);
		}
	}
}
