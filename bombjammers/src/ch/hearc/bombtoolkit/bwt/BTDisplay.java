package ch.hearc.bombtoolkit.bwt;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import kuusisto.tinysound.TinySound;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import ch.hearc.bombtoolkit.bwt.event.BTKeyboardEvent;

public class BTDisplay extends Thread	 {
	
	private static final int NB_FPS_SYNC = 60;
	
	private boolean inputEnabled;
	private boolean gameRunning;
	
	private int width;
	private int height;
	
	private long lastFrame;
	
	private String startStateId;
	
	private BTRootSwitchPanel rootSwitchPanel;
	
	public BTDisplay(int width, int height) {	
		this.width = width;
		this.height = height;
		
		inputEnabled = true;
		gameRunning = false;
		rootSwitchPanel = new BTRootSwitchPanel();
	}
	
	private void createDisplay() {
		// XXX Temporaire pour la présentation
		try {
			DisplayMode targetDisplayMode = null;
			
			DisplayMode[] modes = Display.getAvailableDisplayModes();
			
			int freq = 60;
			
			for (int i = 0; i <modes.length ; i++) {
				DisplayMode current = modes[i];
			
				if ((current.getWidth() == width) && (current.getHeight() == height)) {
					if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
						if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
							targetDisplayMode = current;
							freq = targetDisplayMode.getFrequency();
						}
					}
					
					if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) &&
							(current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
						targetDisplayMode = current;
					break;
					}
				}
			}
			if (targetDisplayMode == null) {
				Display.setDisplayMode(new DisplayMode(width, height));
			
			} else {
				Display.setDisplayMode(targetDisplayMode);
				Display.setFullscreen(true);
			}			
			} catch (LWJGLException e) {}
		finally{
			try {
				Display.create();
			} catch (LWJGLException e) {}
		}
		
//		try {
//			// XXX Manière temporaire d'activer le fullscreen
//			DisplayMode[] modes = Display.getAvailableDisplayModes();
//			
//			Display.setDisplayMode(modes[37]);
////			Display.setDisplayMode(new DisplayMode(width, height));
//			Display.setFullscreen(true);
//			Display.create();
//		} catch (LWJGLException e) {}
	}

	private void initOpenGL() {
		/* Enable textures used by Sprite */
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		/* Enable alpha blending */
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		/* Because the game render in 2D, disable the OpenGL depth test */
		GL11.glDisable(GL11.GL_DEPTH_TEST);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();

		GL11.glOrtho(0, width, height, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glViewport(0, 0, width, height);
	}
	
	private void clearOpenGL() {
		/* Clear the screen and depth buffer */
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		/* Reset color */
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
	}
	
	private void lookForKeyboardEvent() {
		HashMap<Integer, LinkedList<BTAbstractComponent>> mapFocusedComponent =
				BTKeyboardFocusManager.getFocusedComponent();
		
		HashMap<Integer, LinkedList<BTAbstractComponent>> mapCopy =
				new HashMap<Integer, LinkedList<BTAbstractComponent>>();
		
		mapCopy.putAll(mapFocusedComponent);
		
		while (Keyboard.next()) {
			if (inputEnabled) {
				int eventKey = Keyboard.getEventKey();
				
				int id = (Keyboard.getEventKeyState() ?
						BTKeyboardEvent.KEY_PRESSED :
						BTKeyboardEvent.KEY_RELEASED);
				
				for (Entry<Integer, LinkedList<BTAbstractComponent>> entry : mapCopy.entrySet()) {
					
					int focusId = entry.getKey();
					
					LinkedList<BTAbstractComponent> listFocusedComponent = entry.getValue();
					
					BTKeyboardEvent event = new BTKeyboardEvent(id, focusId, eventKey);
					
					LinkedList<BTAbstractComponent> listCopy =
							new LinkedList<BTAbstractComponent>();
					
					listCopy.addAll(listFocusedComponent);
					
					for (BTAbstractComponent component : listCopy) {
						component.processKeyboardEvent(event);
					}
				}
			}
		}
	}
	
	private int getDeltaTime() {
		long time = getTime();
		int deltaTime = (int) (time - lastFrame);
		
		lastFrame = (int) time;
		
		return deltaTime;
	}
	
	private long getTime() {
		long time = (Sys.getTime() * 1000) / Sys.getTimerResolution();
		
		return time;
	}
	
	public BTSwitchPanel getRootSwitchPanel() {
		return rootSwitchPanel;
	}
	
	public void setRootSwitchPanel(BTRootSwitchPanel rootSwitchPanel) {
		this.rootSwitchPanel = rootSwitchPanel;
	}
	
	public void setStartState(String startStateId) {
		this.startStateId = startStateId;
	}
	
	public void setGameRunning(boolean gameRunning) {
		this.gameRunning = gameRunning;
	}
	
	public void setInputEnabled(boolean inputEnabled) {
		this.inputEnabled = inputEnabled;
	}
	
	@Override
	public void run() {
		createDisplay();
		initOpenGL();
		getDeltaTime();
		TinySound.init();
		
		BTStatePanelManager.getStatePanel(startStateId).activate();
		rootSwitchPanel.init();
		
		while (!gameRunning);
		
		while (gameRunning && !Display.isCloseRequested()) {
			int deltaTime = getDeltaTime();
			
			lookForKeyboardEvent();
			rootSwitchPanel.update(deltaTime);
			BTKeyboardFocusManager.update(deltaTime);
			
			clearOpenGL();			
			rootSwitchPanel.render();
			
			Display.update();
			Display.sync(NB_FPS_SYNC);
		}
		Display.destroy();
		TinySound.shutdown();
	}
}
