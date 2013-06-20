package ch.hearc.bombtoolkit.bwt;

public class BTStatePanel extends BTAbstractContainer {

	public BTStatePanel(String nameId, BTSwitchPanel parent) {
		super(parent);
		
		BTStatePanelManager.addStatePanel(nameId, this);
	}
	
	public void activate() {
		parent.changeCurrentPanel(this);
		
		activateComponent();
	}
	
	public void activateComponent() {}
	
	@Override
	public void init() {
		initComponent();
		
		for (BTAbstractComponent btComponent : btComponentList) {
			btComponent.init();
		}
	}
	
	@Override
	public void update(int deltaTime) {
		updateComponent(deltaTime);
		
		for (BTAbstractComponent btComponent : btComponentList) {
			btComponent.update(deltaTime);
		}
	}
	
	@Override
	public void render() {
		renderComponent();
		
		for (BTAbstractComponent btComponent : btComponentList) {
			btComponent.render();
		}
		renderAboveComponent();
	}
	
	@Override
	public void destroy() {
		destroyComponent();
		
		for (BTAbstractComponent btComponent : btComponentList) {
			btComponent.destroy();
		}
	}
}
