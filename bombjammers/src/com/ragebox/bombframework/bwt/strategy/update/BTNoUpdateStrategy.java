package com.ragebox.bombframework.bwt.strategy.update;

import com.ragebox.bombframework.bwt.BTAbstractComponent;

public class BTNoUpdateStrategy extends BTAbstractUpdateStrategy {

	public BTNoUpdateStrategy(BTAbstractComponent component) {
		super(component);
	}

	@Override
	public void executeUpdate(int deltaTime) {}
}
