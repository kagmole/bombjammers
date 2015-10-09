package com.ragebox.bombframework.bwt;

import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

import com.ragebox.bombframework.bwt.event.BTAnimationListener;
import com.ragebox.bombframework.bwt.event.BTHitboxListener;
import com.ragebox.bombframework.bwt.event.BTKeyboardEvent;
import com.ragebox.bombframework.bwt.event.BTKeyboardListener;
import com.ragebox.bombframework.bwt.strategy.render.*;
import com.ragebox.bombframework.bwt.strategy.update.*;

public abstract class BTAbstractComponent extends BTAbstractGraphicObject {

	protected boolean focused;
	protected boolean visible;
	
	protected boolean accelerating;
	protected boolean blinking;
	protected boolean fading;
	protected boolean rotating;
	protected boolean scaling;
	protected boolean translating;

	protected float x;
	protected float y;
	protected float width;
	protected float height;
	
	// Début des chaînes de stratégies
	protected BTFirstUpdateStrategy firstUpdateStrategy;
	protected BTFirstRenderStrategy firstRenderStrategy;

	// Stratégies de mises à jour
	protected BTBlinkStrategy blinkStrategy;
	protected BTFadeStrategy fadeStrategy;
	protected BTNoUpdateStrategy noUpdateStrategy;
	protected BTRotationStrategy rotationStrategy;
	protected BTScalingStrategy scalingStrategy;
	protected BTTranslationStrategy translationStrategy;
	protected BTUpdateStrategy updateStrategy;

	// Stratégies de rendu
	protected BTNoRenderStrategy noRenderStrategy;
	protected BTRenderStrategy renderStrategy;
	
	// Stratégie des hitbox
	protected BTCheckHitboxStrategy checkHitboxStrategy;

	// Liste de stratégies de rendu INUTILE
	
	protected LinkedList<BTAnimationListener> listAnimationListeners;
	protected LinkedList<BTHitboxListenerContainer> listHitboxListenerContainers;
	protected LinkedList<BTKeyboardListener> listKeyboardListeners;
	
	// Focus multiples
	protected LinkedList<Integer> listFocusId;
	
	protected Rectangle2D hitbox;

	protected BTAbstractComponent(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		focused = false;
		visible = true;
		
		hitbox = new Rectangle2D.Float(x, y, width, height);
		
		firstUpdateStrategy = new BTFirstUpdateStrategy(this);
		firstRenderStrategy = new BTFirstRenderStrategy(this);

		blinkStrategy = new BTBlinkStrategy(this);
		fadeStrategy = new BTFadeStrategy(this);
		noUpdateStrategy = new BTNoUpdateStrategy(this);
		rotationStrategy = new BTRotationStrategy(this);
		scalingStrategy = new BTScalingStrategy(this);
		translationStrategy = new BTTranslationStrategy(this);
		updateStrategy = new BTUpdateStrategy(this);

		noRenderStrategy = new BTNoRenderStrategy(this);
		renderStrategy = new BTRenderStrategy(this);

		listAnimationListeners = new LinkedList<BTAnimationListener>();
		listHitboxListenerContainers = new LinkedList<BTHitboxListenerContainer>();
		listKeyboardListeners = new LinkedList<BTKeyboardListener>();
		
		listFocusId = new LinkedList<Integer>();
		
		checkHitboxStrategy = new BTCheckHitboxStrategy(this, listHitboxListenerContainers);

		firstUpdateStrategy.addUpdateStrategy(updateStrategy);
		firstRenderStrategy.addRenderStrategy(renderStrategy);
	}
	
	public void addKeyboardListener(BTKeyboardListener listener) {
		listKeyboardListeners.add(listener);
	}
	
	public void removeKeyboardListener(BTKeyboardListener listener) {
		listKeyboardListeners.remove(listener);
	}
	
	public void processKeyboardEvent(BTKeyboardEvent event) {
		int id = event.getID();
		
		switch (id) {
		case BTKeyboardEvent.KEY_PRESSED:
			for (BTKeyboardListener listener : listKeyboardListeners) {
				listener.keyPressed(event);
			}
			break;
		case BTKeyboardEvent.KEY_RELEASED:
			for (BTKeyboardListener listener : listKeyboardListeners) {
				listener.keyReleased(event);
			}
			break;
		}
	}
	
	// TODO Implémenter une liste de focus plutôt qu'un booléen
	
	public void setFocus(int focusId, boolean focused) {
		if (focused) {
			listFocusId.add(focusId);
			
			BTAnimatedImage image = BTKeyboardFocusManager.getFocusImage(focusId);
			
			if (image != null) {
				image.setX(x - 50);
				image.setY(y);
			}			
			BTKeyboardFocusManager.addFocusedComponent(focusId, this);
		} else {
			listFocusId.remove((Integer) focusId);
			
			BTKeyboardFocusManager.removeFocusedComponent(focusId, this);
		}
		this.focused = !listFocusId.isEmpty();
	}
	
	public void removeAllFocus() {
		for (Integer focusId : listFocusId) {
			BTKeyboardFocusManager.removeFocusedComponent(focusId, this);
		}
		listFocusId.clear();
		
		this.focused = false;
	}
	
	public void giveFocus(int focusId, BTAbstractComponent component) {
		setFocus(focusId, false);
		component.setFocus(focusId, true);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
		
		hitbox.setRect(this.x, y, width, height);
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
		
		hitbox.setRect(x, this.y, width, height);
	}

	public void translateX(float x) {
		this.x += x;
		
		hitbox.setRect(this.x, y, width + x, height);
	}

	public void translateY(float y) {
		this.y += y;
		
		hitbox.setRect(x, this.y, width, height + y);
	}

	public void translate(float x, float y) {
		translateX(x);
		translateY(y);
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;

		if (visible) {
			firstRenderStrategy.changeLastStrategy(renderStrategy);
		} else {
			firstRenderStrategy.changeLastStrategy(noRenderStrategy);
		}
	}
	
	public Rectangle2D getHitbox() {
		return hitbox;
	}
	
	public void addAnimationListener(BTAnimationListener listener) {
		listAnimationListeners.addFirst(listener);
	}
	
	public void removeAnimationListener(BTAnimationListener listener) {
		listAnimationListeners.remove(listener);
	}
	
	public void addHitboxListener(Rectangle2D hitbox, BTHitboxListener listener) {
		if (listHitboxListenerContainers.size() == 0) {
			firstRenderStrategy.addRenderStrategy(checkHitboxStrategy);
		}
		listHitboxListenerContainers.addFirst(new BTHitboxListenerContainer(
				hitbox, listener));
	}
	
	public void removeHitboxListener(Rectangle2D hitbox, BTHitboxListener listener) {
		boolean found = false;
		
		int listenerId = 0;
		
		while (!found && listenerId < listHitboxListenerContainers.size()) {
			BTHitboxListenerContainer container = listHitboxListenerContainers
					.get(listenerId);
			
			if (hitbox.equals(container.hitbox) 
					&& listener.equals(container.listener)) {
				found = true;
			} else {
				++listenerId;
			}
		}
		
		if (found) {
			listHitboxListenerContainers.remove(listenerId);
			
			if (listHitboxListenerContainers.size() == 0) {
				endCheckHitbox();
			}
		}
	}
	
	@Override
	public void update(int deltaTime) {		
		firstUpdateStrategy.executeUpdate(deltaTime);
	}

	@Override
	public void render() {
		firstRenderStrategy.executeRender();
	}
	
/*---------------------------------------------------------------------------*\
|                                                                            *|
|                                 Animations                                 *|
|                                                                            *|
\*---------------------------------------------------------------------------*/

	// Clignements
	public void blinkOverTime(int delay, int time) {
		verifyBlinkPresence();
		
		blinkStrategy.setBlink(delay, time);
	}

	public void blinkOverTime(int delay, int time, int id) {
		verifyBlinkPresence();
		
		blinkStrategy.setBlink(delay, time, id);
	}

	public void incBlinkOverTime(int delay, int time) {
		verifyBlinkPresence();
		
		blinkStrategy.addBlink(delay, time);
	}

	public void incBlinkOverTime(int delay, int time, int id) {
		verifyBlinkPresence();
		
		blinkStrategy.addBlink(delay, time, id);
	}
	
	private void verifyBlinkPresence() {
		if (!blinking) {
			firstUpdateStrategy.addUpdateStrategy(blinkStrategy);
			
			blinking = true;
		}
	}

	// Estompages
	public void fadeOverTime(float coeffOpacity, int time) {
		verifyFadePresence();
		
		fadeStrategy.setFade(coeffOpacity, time);
	}

	public void fadeOverTime(float coeffOpacity, int time, int id) {
		verifyFadePresence();
		
		fadeStrategy.setFade(coeffOpacity, time, id);
	}

	public void incFadeOverTime(float coeffOpacity, int time) {
		verifyFadePresence();
		
		fadeStrategy.addFade(coeffOpacity, time);
	}

	public void incFadeOverTime(float coeffOpacity, int time, int id) {
		verifyFadePresence();
		
		fadeStrategy.addFade(coeffOpacity, time, id);
	}
	
	private void verifyFadePresence() {
		if (!fading) {
			firstUpdateStrategy.addUpdateStrategy(fadeStrategy);
			
			fading = true;
		}
	}
	
	// Rotations
	public void rotateOverTime(int degree, int time) {
		verifyRotatePresence();
		
		rotationStrategy.setRotate(degree, time);
	}
	
	public void rotateOverTime(int degree, int time, int id) {
		verifyRotatePresence();
		
		rotationStrategy.setRotate(degree, time, id);
	}
	
	public void incRotateOverTime(int degree, int time) {
		verifyRotatePresence();
		
		rotationStrategy.addRotate(degree, time);
	}
	
	public void incRotateOverTime(int degree, int time, int id) {
		verifyRotatePresence();
		
		rotationStrategy.addRotate(degree, time, id);
	}
	
	private void verifyRotatePresence() {
		if (!rotating) {
			firstUpdateStrategy.addUpdateStrategy(rotationStrategy);
			
			rotating = true;
		}
	}
	
	// Agrandissements
	public void scaleOverTime(float scaleX, float scaleY, int time) {
		verifyScalePresence();
		
		scalingStrategy.setScale(scaleX, scaleY, time);
	}
	
	public void scaleOverTime(float scaleX, float scaleY, int time, int id) {
		verifyScalePresence();
		
		scalingStrategy.setScale(scaleX, scaleY, time, id);
	}
	
	public void incScaleOverTime(float scaleX, float scaleY, int time) {
		verifyScalePresence();
		
		scalingStrategy.addScale(scaleX, scaleY, time);
	}
	
	public void incScaleOverTime(float scaleX, float scaleY, int time, int id) {
		verifyScalePresence();
		
		scalingStrategy.addScale(scaleX, scaleY, time, id);
	}
	
	private void verifyScalePresence() {
		if (!scaling) {
			firstUpdateStrategy.addUpdateStrategy(scalingStrategy);
			
			scaling = true;
		}
	}

	// Translations
	public void translateOverTime(float translateX, float translateY,
			int time) {
		verifyTranslatePresence();
		
		translationStrategy.setTranslate(translateX, translateY, time);
	}

	public void translateOverTime(float translateX, float translateY, int time,
			int id) {
		verifyTranslatePresence();
		
		translationStrategy.setTranslate(translateX, translateY, time, id);
	}

	public void incTranslateOverTime(float translateX, float translateY,
			int time) {
		verifyTranslatePresence();
		
		translationStrategy.addTranslate(translateX, translateY, time);
	}

	public void incTranslateOverTime(float translateX, float translateY,
			int time, int id) {
		verifyTranslatePresence();
		
		translationStrategy.addTranslate(translateX, translateY, time, id);
	}
	
	private void verifyTranslatePresence() {
		if (!translating) {
			firstUpdateStrategy.addUpdateStrategy(translationStrategy);
			
			translating = true;
		}
	}
	
	// Méthodes pour les stratégies lorsqu'elles finissent
	public void endBlink() {
		firstUpdateStrategy.removeUpdateStrategy(blinkStrategy);
		
		blinking = false;
	}
	
	public void endFade() {
		firstUpdateStrategy.removeUpdateStrategy(fadeStrategy);
		
		fading = false;
	}
	
	public void endRotation() {
		firstUpdateStrategy.removeUpdateStrategy(rotationStrategy);
		
		rotating = false;
	}
	
	public void endScaling() {
		firstUpdateStrategy.removeUpdateStrategy(scalingStrategy);
		
		scaling = false;
	}
	
	public void endTranslation() {
		firstUpdateStrategy.removeUpdateStrategy(translationStrategy);
		
		translating = false;
	}
	
	public void endCheckHitbox() {
		firstRenderStrategy.removeRenderStrategy(checkHitboxStrategy);
	}

	// Méthodes overridables pour gérer les boucles d'animations
	public void notifyAccelerationFinished(int id) {
		for (BTAnimationListener listener : listAnimationListeners) {
			listener.accelerationFinished(id);
		}
	}
	
	public void notifyBlinkFinished(int id) {
		for (BTAnimationListener listener : listAnimationListeners) {
			listener.blinkFinished(id);
		}
	}
	
	public void notifyFadeFinished(int id) {
		for (BTAnimationListener listener : listAnimationListeners) {
			listener.fadeFinished(id);
		}
	}
	
	public void notifyRotationFinished(int id) {
		for (BTAnimationListener listener : listAnimationListeners) {
			listener.rotationFinished(id);
		}
	}
	
	public void notifyScalingFinished(int id) {
		for (BTAnimationListener listener : listAnimationListeners) {
			listener.scalingFinished(id);
		}
	}
	
	public void notifyTranslationFinished(int id) {
		for (BTAnimationListener listener : listAnimationListeners) {
			listener.translationFinished(id);
		}
	}
	
	// Hitbox events	
	public void notifyHitboxIntersected(Rectangle2D otherHitbox) {
		for (BTHitboxListenerContainer container : listHitboxListenerContainers) {
			if (container.hitbox.equals(otherHitbox)) {
				container.listener.hitboxIntersected();
			}
		}
	}
	
/*---------------------------------------------------------------------------*\
|                                                                            *|
|                               Inner classes                                *|
|                                                                            *|
\*---------------------------------------------------------------------------*/
	
	public class BTHitboxListenerContainer {
		
		private Rectangle2D hitbox;
		private BTHitboxListener listener;
		
		BTHitboxListenerContainer(Rectangle2D hitbox, BTHitboxListener listener) {
			this.hitbox = hitbox;			
			this.listener = listener;
		}
		
		public Rectangle2D getHitbox() {
			return hitbox;
		}
		
		public BTHitboxListener getListener() {
			return listener;
		}
	}
}
