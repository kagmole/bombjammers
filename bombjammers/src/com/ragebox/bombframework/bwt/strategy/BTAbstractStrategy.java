package com.ragebox.bombframework.bwt.strategy;

import com.ragebox.bombframework.bwt.BTAbstractComponent;

public abstract class BTAbstractStrategy {
	
	protected BTAbstractComponent component;

	public BTAbstractStrategy(BTAbstractComponent component) {
		this.component = component;
	}
}
