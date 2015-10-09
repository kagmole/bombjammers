package com.ragebox.bombframework.bwt.strategy.update;

import com.ragebox.bombframework.bwt.BTAbstractComponent;

public class BTFirstUpdateStrategy extends BTAbstractUpdateStrategy {

	public BTFirstUpdateStrategy(BTAbstractComponent component) {
		super(component);
	}

	@Override
	public void executeUpdate(int deltaTime) {
		executeNextUpdateStrategy(deltaTime);
	}
}
