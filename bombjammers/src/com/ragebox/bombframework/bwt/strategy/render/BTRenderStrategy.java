package com.ragebox.bombframework.bwt.strategy.render;

import com.ragebox.bombframework.bwt.BTAbstractComponent;

public class BTRenderStrategy extends BTAbstractRenderStrategy {

	public BTRenderStrategy(BTAbstractComponent component) {
		super(component);
	}

	@Override
	public void executeRender() {
		component.renderComponent();
	}
}
