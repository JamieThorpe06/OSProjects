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
	private Semaphore sem;

	public Train(String name, int timedelay, int trainnumber,
			ReedContacts ReedContactsIN, FAArray array, Semaphore s) {
		super(name);
		msdelay = timedelay;
		TrainNumber = trainnumber + 1;
		TrainPosition = new TrainImagePosition(trainnumber, ReedContactsIN);
		if (TrainNumber == 1) {
			SetSpeed(14);
			SetDesiredSpeed(14);
		} else {
			SetSpeed(2);
			SetDesiredSpeed(2);// for now
		}
		
		fa = array;
		sem = s;
	}// end constructor
	
	public boolean trainStarted(){
		return currentNode != 0;
	}
	
	public int getSignal(){
		return fa.getSignal(currentNode);
	}
	
	public void advance(){
		currentNode = fa.getNext(currentNode);
	}
	
	public int getNext(int start){
		return fa.getNext(start);
	}
	
	public int getCurrent() {
		return currentNode;
	}

	public Boolean GetSwitch2() {
		return (Switch2);
	}

	public void SetSwitch2(boolean flag) {
		if(Switch2!=flag && TrainNumber ==1){
			fa.switchThrown(2);
		}
		TrainPosition.SetSwitch2(flag);
		Switch2 = flag;
	}

	public Boolean GetSwitch4() {
		return (Switch4);
	}

	public void SetSwitch4(boolean flag) {
		if(Switch4!=flag && TrainNumber ==1){
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
		TrainPosition.ResetAll();
		SetSwitch2(false);
		SetSwitch4(false);
		if (TrainNumber == 1) {
			SetSpeed(14);
			SetDesiredSpeed(14);
		} else {
			SetSpeed(2);
			SetDesiredSpeed(2);// for now
		}
		// JOptionPane.showMessageDialog(null, "Train: " + TrainNumber +
		// " reset" );
	}

	public void StopTrain() {
		// you stop the train here
		JOptionPane.showMessageDialog(null, "Train: " + TrainNumber
				+ " You stop it here...");
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
		// System.out.println("Train " + TrainNumber + "  Desired speed is "
		// + DesiredSpeed);

	}

	public void run() {
		while (true) {

			try {
				mytime = msdelay;// (long) (Math.random() * msdelay);
				Thread.sleep(mytime);
			} catch (Exception e) {
				System.out.println("exception in thread sleep" + e.toString());
			}

			sem.P();
			TrainPosition.MoveTrainPosition();
			sem.V();
		}
	}
}
