package ch.hearc.bombjammers.engine;

import ch.hearc.bombjammers.loaders.BJAnimationLoader;
import ch.hearc.bombjammers.util.BJActionable;
import ch.hearc.bombtoolkit.bwt.BTAlterableImage;
import ch.hearc.bombtoolkit.bwt.BTAnimatedImage;
import ch.hearc.bombtoolkit.bwt.BTKeyboardFocusManager;
import ch.hearc.bombtoolkit.bwt.BTRootSwitchPanel;

public class BJMainPanel extends BTRootSwitchPanel {
	
	private boolean gateIsClosing;
	private boolean gateIsOpening;
	private boolean gateIsVisible;
	
	private int gateDelay;
	
	private BJActionable currentActionable;
	
	private BTAlterableImage leftGateImage;
	private BTAlterableImage rightGateImage;
	
	public BJMainPanel() {
		super();
		
		gateIsClosing = false;
		gateIsOpening = false;
		gateIsVisible = false;
		
		initGateDelay();
	}
	
	private void initGateDelay() {
		gateDelay = 200;
	}

	public void closeGate(BJActionable actionableState) {
		currentActionable = actionableState;
		
		gateIsClosing = true;
		gateIsVisible = true;
		
		leftGateImage.translateOverTime(400, 0, 150);
		rightGateImage.translateOverTime(-526, 0, 150);
	}
	
	private void openGate() {
		gateIsOpening = true;
		
		leftGateImage.translateOverTime(-400, 0, 150);
		rightGateImage.translateOverTime(526, 0, 150);
	}
	
	@Override
	public void initComponent() {
		leftGateImage = new BTAlterableImage(-400, 0, 400, 600,
				"./res/component/gate/gate_left.png");
		
		rightGateImage = new BTAlterableImage(800, 0, 526, 600,
				"./res/component/gate/gate_right.png");
		
		BJAnimationLoader loader = BJAnimationLoader.getInstance();
		
		loader.loadXMLFile("./res/component/cursor/cursor.xml");
		BTKeyboardFocusManager.linkImageToFocus(BJEngine.MAIN_FOCUS,
				loader.getAnimation("normal", "./res/component/cursor/cursor.png"));
		
		loader.loadXMLFile("./res/component/cursor/cursor_p1.xml");
		BTKeyboardFocusManager.linkImageToFocus(BJEngine.FOCUS_1,
				loader.getAnimation("normal", "./res/component/cursor/cursor_p1.png"));
		
		loader.loadXMLFile("./res/component/cursor/cursor_p2.xml");
		BTKeyboardFocusManager.linkImageToFocus(BJEngine.FOCUS_2,
				loader.getAnimation("normal", "./res/component/cursor/cursor_p2.png"));
		
		leftGateImage.init();
		rightGateImage.init();
	}
	
	@Override
	public void updateComponent(int deltaTime) {
		if (gateIsVisible) {
			if (leftGateImage.isTranslating()) {
				leftGateImage.update(deltaTime);
				rightGateImage.update(deltaTime);
			} else {
				if (gateIsClosing) {
					// Les portes ont finies de se fermer
					
					gateIsClosing = false;
					currentActionable.doAction();
				} else if (!gateIsOpening) {
					// Les portes doivent s'ouvrir
					
					gateDelay -= deltaTime;
					
					if (gateDelay < 0) {
						initGateDelay();
						openGate();
					}
				} else if (gateIsOpening) {
					// Les portes ont finies de s'ouvrir
					
					gateIsOpening = false;
					gateIsVisible = false;			
				}
			}
		}
	}
	
	@Override
	public void renderAboveComponent() {
		if (gateIsVisible) {
			leftGateImage.render();
			rightGateImage.render();
		}
	}
}
