package ch.hearc.bombtoolkit.bwt.event;

public abstract class BTAnimationAdapter implements BTAnimationListener {

	@Override
	public void accelerationFinished(int id) {}

	@Override
	public void blinkFinished(int id) {}

	@Override
	public void fadeFinished(int id) {}

	@Override
	public void rotationFinished(int id) {}

	@Override
	public void scalingFinished(int id) {}

	@Override
	public void translationFinished(int id) {}
}
