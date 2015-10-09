package com.ragebox.bombframework.bwt.strategy.update;

import com.ragebox.bombframework.bwt.BTAbstractComponent;

public class BTBlinkStrategy extends BTAbstractUpdateStrategy {
	
	private boolean warningEnd;
	
	private int originalDelay;
	private int delay;
	private int time;
	private int id;

	public BTBlinkStrategy(BTAbstractComponent component) {
		super(component);
		
		warningEnd = false;
		
		originalDelay = 0;
		delay = 0;
		time = 0;
		id = 0;
	}

	@Override
	public void executeUpdate(int deltaTime) {
		delay -= deltaTime;
		time -= deltaTime;
		
		if (time > 0) {
			if (delay <= 0) {
				delay += originalDelay;
				
				component.setVisible(!component.isVisible());
			}
		} else {
			component.setVisible(true);
			component.endBlink();
			
			originalDelay = 0;
			delay = 0;
			time = 0;
			
			if (warningEnd) {
				component.notifyBlinkFinished(id);
			}
		}
		executeNextUpdateStrategy(deltaTime);
	}

	public void setBlink(int delay, int time) {
		this.originalDelay = delay;
		this.delay = delay;
		this.time = time;
		
		warningEnd = false;
	}
	
	public void setBlink(int delay, int time, int id) {
		this.originalDelay = delay;
		this.delay = delay;
		this.time = time;
		this.id = id;
		
		warningEnd = true;
	}
	
	public void addBlink(int delay, int time) {
		this.originalDelay += delay;
		this.delay += delay;
		this.time += time;
		
		warningEnd = false;
	}
	
	public void addBlink(int delay, int time, int id) {
		this.originalDelay += delay;
		this.delay += delay;
		this.time += time;
		
		this.id = id;
		
		warningEnd = true;
	}
}
