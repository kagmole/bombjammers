package ch.hearc.bombtoolkit.bwt.strategy.render;

import ch.hearc.bombtoolkit.bwt.BTAbstractComponent;
import ch.hearc.bombtoolkit.bwt.strategy.BTAbstractStrategy;
import ch.hearc.bombtoolkit.core.BTRenderable;

public abstract class BTAbstractRenderStrategy extends BTAbstractStrategy
		implements BTRenderable {
	
	protected BTAbstractRenderStrategy nextRenderStrategy;
	protected BTAbstractRenderStrategy previousRenderStrategy;

	public BTAbstractRenderStrategy(BTAbstractComponent component) {
		super(component);
		
		nextRenderStrategy = null;
		previousRenderStrategy = null;
	}
	
	protected void executeNextRenderStrategy() {
		nextRenderStrategy.executeRender();
	}
	
	public void addRenderStrategy(BTAbstractRenderStrategy renderStrategy) {
		if (nextRenderStrategy != null) {
			nextRenderStrategy.previousRenderStrategy = renderStrategy;
		}
		renderStrategy.nextRenderStrategy = nextRenderStrategy;
		renderStrategy.previousRenderStrategy = this;
		
		nextRenderStrategy = renderStrategy;
	}

	public void removeRenderStrategy(BTAbstractRenderStrategy renderStrategy) {
		if (equals(renderStrategy)) {
			previousRenderStrategy.nextRenderStrategy = nextRenderStrategy;
			nextRenderStrategy.previousRenderStrategy = previousRenderStrategy;
		} else if (nextRenderStrategy != null) {
			nextRenderStrategy.removeRenderStrategy(renderStrategy);
		}
	}
	
	public void changeLastStrategy(BTAbstractRenderStrategy renderStrategy) {
		if (nextRenderStrategy == null) {
			previousRenderStrategy.nextRenderStrategy = renderStrategy;
			renderStrategy.previousRenderStrategy = previousRenderStrategy;
		} else {
			nextRenderStrategy.changeLastStrategy(renderStrategy);
		}
	}
}
