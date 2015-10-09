package com.ragebox.bombframework.bwt.strategy.update;

import com.ragebox.bombframework.bwt.BTAbstractComponent;

public class BTUpdateStrategy extends BTAbstractUpdateStrategy {

	public BTUpdateStrategy(BTAbstractComponent component) {
		super(component);
	}

	@Override
	public void executeUpdate(int deltaTime) {
		component.updateComponent(deltaTime);
	}
}
