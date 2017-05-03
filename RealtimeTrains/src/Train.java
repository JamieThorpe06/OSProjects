import javax.swing.JOptionPane;

public class Train extends Thread {
	private long mytime;

	private int msdelay;
	private TrainImagePosition TrainPosition;
	private int TrainNumber = 0;
	private int speed = 0;
	private int DesiredSpeed = 14;
	private Boolean Switch2 = false;
	private Boolean Switch4 = false;

	private int currentNode = 0;
	private FAArray fa;

	private boolean planReset = false;
	private boolean trainStarted = false;
	private boolean collisionStop = false;
	private boolean speedZero = false;
	private Semaphore twoStart = new Semaphore(0, 1);
	private Semaphore collision = new Semaphore(0, 1);
	private Semaphore speedStop = new Semaphore(0, 1);

	public Train(String name, int timedelay, int trainnumber, ReedContacts ReedContactsIN, FAArray array) {
		super(name);
		msdelay = timedelay;
		TrainNumber = trainnumber + 1;
		TrainPosition = new TrainImagePosition(trainnumber, ReedContactsIN);
		if (TrainNumber == 1) {
			SetSpeed(14);
			SetDesiredSpeed(14);
			trainStarted = true;
		} else {
			SetSpeed(2);
			SetDesiredSpeed(2);// for now
		}

		fa = array;

	}// end constructor

	public boolean trainStarted() {
		return trainStarted;
	}

	public int getSignal() {
		return fa.getSignal(currentNode);
	}

	public void advance() {
		currentNode = fa.getNext(currentNode);
	}

	public int getNext(int start) {
		return fa.getNext(start);
	}

	public int getCurrent() {
		return currentNode;
	}

	public Boolean GetSwitch2() {
		return (Switch2);
	}

	public void SetSwitch2(boolean flag) {
		if (Switch2 != flag && TrainNumber == 1) {
			fa.switchThrown(2);
		}
		TrainPosition.SetSwitch2(flag);
		Switch2 = flag;
	}

	public Boolean GetSwitch4() {
		return (Switch4);
	}

	public void SetSwitch4(boolean flag) {
		if (Switch4 != flag && TrainNumber == 1) {
			fa.switchThrown(4);
		}
		TrainPosition.SetSwitch4(flag);
		Switch4 = flag;
	}

	public TrackPoint2D GetCurrentTrainPosition() {
		return (TrainPosition.GetCurrentTrainPosition());
	}

	public void Reset() {
		// you reset the train here
		planReset = true;
	}
	
	public boolean getResetPlans() {
		return planReset;
	}
	
	public void performReset(){

		currentNode = 0;
		TrainPosition.ResetAll();
		SetSwitch2(false);
		SetSwitch4(false);
		if (TrainNumber == 1) {
			SetSpeed(14);
			SetDesiredSpeed(14);
			trainStarted = true;
		} else {
			SetSpeed(2);
			SetDesiredSpeed(2);// for now
			if (trainStarted) {
				trainStarted = false;
			}
		}

		collisionStop = false;
		speedZero = false;
		collision.V();
		speedStop.V();
		twoStart.V();
		
		planReset = false;

		// JOptionPane.showMessageDialog(null, "Train: " + TrainNumber +
		// " reset" );
	}

	public void release() {
		System.out.println("Train 2 released");
		twoStart.V();
	}

	public void setTrainStarted(boolean start) {
		trainStarted = start;
	}

	public void StopTrain() {
		// you stop the train here
		JOptionPane.showMessageDialog(null, "Train: " + TrainNumber + " You stop it here...");

		speedZero = true;
	}

	public void spStop() {
		speedStop.P();
	}

	public boolean getSpeedZero() {
		return speedZero;
	}

	public int getSpeed() {
		return speed;
	}

	public void SetSpeed(int speedIN) {
		// you set the train speed here
		speed = speedIN;
		// msdelay = 1400 - (speed * 100) + 50;
		msdelay = (15 - speed) * 30;
		// JOptionPane.showMessageDialog(null, "Train: " + TrainNumber +
		// " Speed now is " + speed + "msdelay " + msdelay );

	}

	public synchronized int GetDesiredSpeed() {
		return (DesiredSpeed);
	}

	public synchronized void SetDesiredSpeed(int speedIN) {
		
		DesiredSpeed = speedIN;
		SetSpeed(speedIN);
		// System.out.println("Train " + TrainNumber + " Desired speed is "
		// + DesiredSpeed);

		if (speedZero && speedIN != 0) {
			speedZero = false;
			speedStop.V();
		}
		

	}

	public void urgentCollision() {
		System.out.println("Train" + TrainNumber + " collision stop");
		collision.P();
	}

	public void setColStop(boolean stop) {
	
		collisionStop = stop;
	}

	public boolean getColStop() {
		return collisionStop;
	}

	public void colSafe() {
		collision.V();
	}

	public void run() {

		while (true) {
			System.out.println("I am " + Thread.currentThread().getName() + " and I am running");

			// while loop here to prevent stutter
			while(!trainStarted || collisionStop || speedZero){
			
				if (!trainStarted) {
					twoStart.P();
				}

				if (collisionStop) {
					urgentCollision();
				}

				if (speedZero) {
					spStop();
				}
			}

			try {
				mytime = msdelay;// (long) (Math.random() * msdelay);
				Thread.sleep(mytime);
			} catch (Exception e) {
				System.out.println("exception in thread sleep" + e.toString());
			}

			TrainPosition.MoveTrainPosition();

		}

	}
}