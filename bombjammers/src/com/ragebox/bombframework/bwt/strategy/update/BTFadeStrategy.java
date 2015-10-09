package com.ragebox.bombframework.bwt.strategy.update;

import com.ragebox.bombframework.bwt.BTAbstractComponent;

public class BTFadeStrategy extends BTAbstractUpdateStrategy {

	private boolean warningEnd;
	
	private float coeffOpacity;
	
	private int time;
	private int id;
	
	public BTFadeStrategy(BTAbstractComponent component) {
		super(component);
	}

	@Override
	public void executeUpdate(int deltaTime) {
		// TODO Implémenter l'estompage
	}

	public void setFade(float coeffOpacity, int time) {
		this.coeffOpacity = coeffOpacity;
		this.time = time;
		
		warningEnd = false;
	}
	
	public void setFade(float coeffOpacity, int time, int id) {
		this.coeffOpacity = coeffOpacity;
		this.time = time;
		this.id = id;
		
		warningEnd = true;
	}
	
	public void addFade(float coeffOpacity, int time) {
		this.coeffOpacity += coeffOpacity;
		this.time += time;
		
		warningEnd = false;
	}
	
	public void addFade(float coeffOpacity, int time, int id) {
		this.coeffOpacity += coeffOpacity;
		this.time += time;
		
		this.id = id;
		
		warningEnd = true;
	}
}
