package ch.hearc.bombtoolkit.bwt.strategy.update;

import ch.hearc.bombtoolkit.bwt.BTAbstractComponent;

public class BTScalingStrategy extends BTAbstractUpdateStrategy {

	private boolean warningEnd;
	
	private float scaleX;
	private float scaleY;
	
	private int time;
	private int id;
	
	public BTScalingStrategy(BTAbstractComponent component) {
		super(component);
	}

	@Override
	public void executeUpdate(int deltaTime) {
		// TODO Implémenter l'agrandissement
	}

	public void setScale(float scaleX, float scaleY, int time) {
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		
		this.time = time;
		
		warningEnd = false;
	}
	
	public void setScale(float scaleX, float scaleY, int time, int id) {
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		
		this.time = time;
		this.id = id;
		
		warningEnd = true;
	}
	
	public void addScale(float scaleX, float scaleY, int time) {
		this.scaleX += scaleX;
		this.scaleY += scaleY;
		
		this.time += time;
		
		warningEnd = false;
	}
	
	public void addScale(float scaleX, float scaleY, int time, int id) {
		this.scaleX += scaleX;
		this.scaleY += scaleY;
		
		this.time += time;
		
		this.id = id;
		
		warningEnd = true;;
	}
}
