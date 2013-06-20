package ch.hearc.bombtoolkit.bwt.strategy.update;

import ch.hearc.bombtoolkit.bwt.BTAbstractComponent;

public class BTTranslationStrategy extends BTAbstractUpdateStrategy {

	private boolean warningEnd;
	
	private float ratioTranslateX;
	private float ratioTranslateY;
	
	private int time;
	private int id;
	
	public BTTranslationStrategy(BTAbstractComponent component) {
		super(component);
		
		warningEnd = false;
		
		ratioTranslateX = 0;
		ratioTranslateY = 0;
	
		time = 0;
		id = 0;
	}

	@Override
	public void executeUpdate(int deltaTime) {
		time -= deltaTime;
		
		if (time > 0) {
			component.translate(ratioTranslateX * deltaTime,
					ratioTranslateY * deltaTime);
		} else {
			int remainingTime = deltaTime + time;
			
			component.translate(ratioTranslateX * remainingTime,
					ratioTranslateY * remainingTime);
			
			component.endTranslation();
			
			ratioTranslateX = 0;
			ratioTranslateY = 0;
			time = 0;
			
			if (warningEnd) {
				component.notifyTranslationFinished(id);
			}
		}
		executeNextUpdateStrategy(deltaTime);
	}

	public void setTranslate(float translateX, float translateY, int time) {
		ratioTranslateX = translateX / time;
		ratioTranslateY = translateY / time;
		
		this.time = time;
		
		warningEnd = false;
	}
	
	public void setTranslate(float translateX, float translateY, int time, 
			int id) {
		ratioTranslateX = translateX / time;
		ratioTranslateY = translateY / time;
		
		this.time = time;
		this.id = id;
		
		warningEnd = true;
	}
	
	public void addTranslate(float translateX, float translateY, int time) {
		ratioTranslateX = ((ratioTranslateX * this.time) + translateX)
				/ (time + this.time);
		ratioTranslateY = ((ratioTranslateY * this.time) + translateY)
				/ (time + this.time);
		
		this.time += time;
		
		warningEnd = false;
	}
	
	public void addTranslate(float translateX, float translateY, int time, 
			int id) {
		ratioTranslateX = ((ratioTranslateX * this.time) + translateX)
				/ (time + this.time);
		ratioTranslateY = ((ratioTranslateY * this.time) + translateY)
				/ (time + this.time);
		
		this.time += time;
		
		this.id = id;
		
		warningEnd = true;
	}
}
