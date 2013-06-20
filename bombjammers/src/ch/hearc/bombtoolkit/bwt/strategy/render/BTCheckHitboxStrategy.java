package ch.hearc.bombtoolkit.bwt.strategy.render;

import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

import ch.hearc.bombtoolkit.bwt.BTAbstractComponent;
import ch.hearc.bombtoolkit.bwt.BTAbstractComponent.BTHitboxListenerContainer;

public class BTCheckHitboxStrategy extends BTAbstractRenderStrategy {
	
	LinkedList<BTHitboxListenerContainer> listHitboxListenerContainers;

	public BTCheckHitboxStrategy(BTAbstractComponent component,
			LinkedList<BTHitboxListenerContainer> listHitboxListenerContainers) {
		super(component);
		
		this.listHitboxListenerContainers = listHitboxListenerContainers;
	}

	@Override
	public void executeRender() {
		Rectangle2D hitbox = component.getHitbox();
		
		for (BTHitboxListenerContainer container : listHitboxListenerContainers) {
			Rectangle2D otherHitbox = container.getHitbox();
			
			if (hitbox.intersects(otherHitbox)) {
				component.notifyHitboxIntersected(otherHitbox);
			}
		}
		executeNextRenderStrategy();
	}
}
