package com.ragebox.bombframework.bwt.strategy.render;

import com.ragebox.bombframework.bwt.BTAbstractComponent;

public class BTFirstRenderStrategy extends BTAbstractRenderStrategy {

	public BTFirstRenderStrategy(BTAbstractComponent component) {
		super(component);
	}

	@Override
	public void executeRender() {
		executeNextRenderStrategy();
	}
}
