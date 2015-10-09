package com.ragebox.bombframework.bwt.strategy.update;

import com.ragebox.bombframework.bwt.BTAbstractComponent;

public class BTRotationStrategy extends BTAbstractUpdateStrategy {

	private boolean warningEnd;
	
	private int degree;
	private int time;
	private int id;
	
	public BTRotationStrategy(BTAbstractComponent component) {
		super(component);
	}

	@Override
	public void executeUpdate(int deltaTime) {
		// TODO Implémenter la rotation
	}

	public void setRotate(int degree, int time) {
		this.degree = degree;
		this.time = time;
		
		warningEnd = false;
	}
	
	public void setRotate(int degree, int time, int id) {
		this.degree = degree;
		this.time = time;
		this.id = id;
		
		warningEnd = true;
	}
	
	public void addRotate(int degree, int time) {
		this.degree += degree;
		this.time += time;
		
		warningEnd = false;
	}
	
	public void addRotate(int degree, int time, int id) {
		this.degree += degree;
		this.time += time;
		
		this.id = id;
		
		warningEnd = true;
	}
}
