package ch.hearc.bombtoolkit.bwt.strategy.render;

import ch.hearc.bombtoolkit.bwt.BTAbstractComponent;

public class BTRenderStrategy extends BTAbstractRenderStrategy {

	public BTRenderStrategy(BTAbstractComponent component) {
		super(component);
	}

	@Override
	public void executeRender() {
		component.renderComponent();
	}
}
