package ch.hearc.bombtoolkit.bwt;

import java.util.LinkedList;

public abstract class BTAbstractContainer extends BTAbstractGraphicObject {
	
	protected BTSwitchPanel parent;
	protected LinkedList<BTAbstractComponent> btComponentList;
	protected LinkedList<BTAbstractContainer> btContainerList;
	
	protected BTAbstractContainer(BTSwitchPanel parent) {
		this.parent = parent;
		
		btComponentList = new LinkedList<BTAbstractComponent>();
		btContainerList = new LinkedList<BTAbstractContainer>();
	}

	public void addComponent(BTAbstractComponent component) {
		btComponentList.add(component);
	}
	
	public void removeComponent(BTAbstractComponent component) {
		btComponentList.remove(component);
	}
	
	public void addContainer(BTAbstractContainer container) {
		btContainerList.add(container);
	}
	
	public void removeContainer(BTAbstractContainer container) {
		btContainerList.remove(container);
	}
	
	@Override
	public void render() {
		renderComponent();
		renderAboveComponent();
	}
	
	public void renderAboveComponent() {}
}