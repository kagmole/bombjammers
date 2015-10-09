package com.ragebox.bombframework.bwt.strategy.render;

import com.ragebox.bombframework.bwt.BTAbstractComponent;

public class BTNoRenderStrategy extends BTAbstractRenderStrategy {

	public BTNoRenderStrategy(BTAbstractComponent component) {
		super(component);
	}

	@Override
	public void executeRender() {}
}
