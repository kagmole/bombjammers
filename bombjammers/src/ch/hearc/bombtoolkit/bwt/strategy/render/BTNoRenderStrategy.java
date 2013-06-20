package ch.hearc.bombtoolkit.bwt.strategy.render;

import ch.hearc.bombtoolkit.bwt.BTAbstractComponent;

public class BTNoRenderStrategy extends BTAbstractRenderStrategy {

	public BTNoRenderStrategy(BTAbstractComponent component) {
		super(component);
	}

	@Override
	public void executeRender() {}
}
