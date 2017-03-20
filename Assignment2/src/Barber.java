public class Barber extends Thread {
	
	//private int delayTime;
	private WaitRoom wr;
	private MyCanvas myCanvas;
	private Semaphore s;
	private Semaphore delaySem;
	private MyPanelTop pt; // for printing to debug panel
	
	private int minTime;
	private int maxTime;
	
	private boolean isAlive = false;
	private boolean isWaiting = false;

	public Barber(String name, int delayTime, WaitRoom wr, MyCanvas myCanvas, Semaphore s, Semaphore delay) {
		super(name);
		this.minTime = delayTime;
		this.maxTime = delayTime;
		this.wr = wr;
		this.myCanvas = myCanvas;
		this.s = s;
		this.delaySem = delay;
		
		this.start();
	}
	
	public void run() {
		while(true) {
			System.out.println("I am the Barber running");
			
			// Checks if the thread should be alive; determined by "revive" and "kill" buttons in panel
			if (isAlive) {
				
				// Grab semaphore s
				s.P();
				
				// Check if the waiting room is empty
				// If so, tell the barber to go to sleep, release semaphore s and grab semaphore delay
				if(wr.isEmpty()){
					wr.setBarberGoingToSleep(true);
					pt.debugAppend("Barber -> Going to sleep");
					s.V();
					delaySem.P();
					
					// Once another customer comes in, the barber can take the semaphore and wake up
					s.P();
					pt.debugAppend("Barber -> Has been woken up");
				}
				
				// Since the waiting room is not empty, the barber brings in a customer
				// This removes a customer from the waiting room
				pt.debugAppend("Barber -> Getting a customer");
				wr.removeCust();
				myCanvas.moveToChair();
				
				// Waiting room is no longer affect by Barber's actions, so semaphore s is released
				s.V();
				
				// Sleeps to represent the time it takes to cut the customer's hair
				try {
					this.sleep((long)Math.random()*(maxTime-minTime+1) + minTime);
					
				} catch (Exception e) {
					System.out.println("Exception thrown: " + e.toString());
				}
				
				// Shows customer leaving barber chair after haircut
				myCanvas.showCustomerLeave();
	 
			}
			else {
				// Thread is not alive
				waitThread();
			}
			
		}
	}
	
	// Sets the MyPanelTop object so that statements can be printed to the debugging window
	public void setMyTopPanel(MyPanelTop pt) {
		this.pt = pt;
	}
	
	// Resets max and min cut times; only called if min<=max
	public void resetTimes(int min, int max){
		minTime = min;
		maxTime = max;
	}
	
	// If the thread was not alive, make it alive and notify if it was waiting
	public synchronized void revive() {
		if (!isAlive){
			isAlive = true;
			
			if(isWaiting){
				notify();
			}
		}
		pt.debugAppend("Barber -> You revived the barber");
	}

	// If the thread is alive, make it not alive
	public void kill() {
		if (isAlive){
			isAlive = false;
		}
		pt.debugAppend("Barber -> You killed the barber");
	}
	
	// Wait until the thread is alive again
	public synchronized void waitThread(){
		try{
			isWaiting = true;
			this.wait();
		}catch (Exception e){}
	}

}