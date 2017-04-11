import java.awt.Image;

public class TrackPositions {
	private static final int MAXIMUM = 500;
	// x, y coordinates of train on track
	public TrackPoint2D[] trackpoint = new TrackPoint2D[MAXIMUM];
	private int totalpoints = 0;
	private int index = 0;
	private boolean ForwardDirection = true;
	private int loopcounter = 0;

	public TrackPositions() {
		super();
		trackpoint = new TrackPoint2D[MAXIMUM];
	}

	public void SetTrackLocation(int i, int x, int y) {
		trackpoint[i] = new TrackPoint2D();
		trackpoint[i].setLocation(x, y);
		totalpoints++;
	}
	public synchronized void ResetLoopCounter() {
		loopcounter = 0;
	}
	public synchronized int GetLoopCounter() {
		return (loopcounter);
	}
	public synchronized int GetCurrentIndex() {
		return (index);
	}
	public synchronized void SetCurrentIndex(int indexIN) {
		 index = indexIN;
	}

	public synchronized TrackPoint2D MoveToNextTrackPositionTP() {

		if (GetDirection()) {
			index++;
		} else {
			index--;
		}
		if (index >= totalpoints) {
			index = 0;
			loopcounter++;
		}
		TrackPoint2D temp = trackpoint[index];
		return (temp);
	}

	public synchronized void PrintCurrentTrackPosition() {
		System.out.println("index is " + index + " x " + trackpoint[index].getX() + " y "
				+ trackpoint[index].getY());

	}

	public synchronized TrackPoint2D GetCurrentTrackPosition() {
		TrackPoint2D temp = trackpoint[index];
		return (temp);
	}

	public synchronized void SetDirection(boolean flag) {
		ForwardDirection = flag;
	}

	public synchronized boolean GetDirection() {
		return (ForwardDirection);
	}

	public synchronized void Reset() {
		ForwardDirection = true;
		index = 0;
	}

}
