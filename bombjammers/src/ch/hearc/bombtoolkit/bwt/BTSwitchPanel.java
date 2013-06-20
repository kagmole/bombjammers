package ch.hearc.bombtoolkit.bwt;

public class BTSwitchPanel extends BTAbstractContainer {

	protected BTAbstractContainer currentPanel;
	
	public BTSwitchPanel(BTSwitchPanel parent) {
		super(parent);
	}
	
	protected void changeCurrentPanel(BTAbstractContainer panel) {
		currentPanel = panel;
		parent.changeCurrentPanel(this);
	}
	
	@Override
	public void init() {
		initComponent();
		
		for (BTAbstractComponent btComponent : btComponentList) {
			btComponent.init();
		}
		
		for (BTAbstractContainer btContainer : btContainerList) {
			btContainer.init();
		}
	}
	
	@Override
	public void update(int deltaTime) {
		updateComponent(deltaTime);
		
		for (BTAbstractComponent btComponent : btComponentList) {
			btComponent.update(deltaTime);
		}
		currentPanel.update(deltaTime);
	}
	
	@Override
	public void render() {
		renderComponent();
		
		for (BTAbstractComponent btComponent : btComponentList) {
			btComponent.render();
		}
		currentPanel.render();
		
		renderAboveComponent();
	}
	
	@Override
	public void destroy() {
		destroyComponent();
		
		for (BTAbstractComponent btComponent : btComponentList) {
			btComponent.destroy();
		}
		
		for (BTAbstractContainer btContainer : btContainerList) {
			btContainer.destroy();
		}
	}
}
