package ch.hearc.bombtoolkit.bwt;

public abstract class BTAbstractGraphicObject {	
	
	public void init() {
		initComponent();
	}
	
	public void initComponent() {}
	
	public void update(int deltaTime) {
		updateComponent(deltaTime);
	}
	
	public void updateComponent(int deltaTime) {}
	
	public void render() {
		renderComponent();
	}
	
	public void renderComponent() {}
	
	public void destroy() {
		destroyComponent();
	}
	
	public void destroyComponent() {}
}