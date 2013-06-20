package ch.hearc.bombtoolkit.bwt.strategy.render;

import ch.hearc.bombtoolkit.bwt.BTAbstractComponent;

public class BTFirstRenderStrategy extends BTAbstractRenderStrategy {

	public BTFirstRenderStrategy(BTAbstractComponent component) {
		super(component);
	}

	@Override
	public void executeRender() {
		executeNextRenderStrategy();
	}
}
