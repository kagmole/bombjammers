package ch.hearc.bombtoolkit.bwt.event;

public interface BTAnimationListener {

	public void accelerationFinished(int id);
	public void blinkFinished(int id);
	public void fadeFinished(int id);
	public void rotationFinished(int id);
	public void scalingFinished(int id);
	public void translationFinished(int id);
}
