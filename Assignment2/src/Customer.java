
public class Customer extends Thread {
	
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

	public Customer(String name, int delayTime, WaitRoom wr, MyCanvas myCanvas, Semaphore s, Semaphore delay) {
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
			System.out.println("I am the Customer running");
			
			// Check if the Customer is alive
			if (isAlive) {
				
				// Sleep represents the amount of time it takes for another customer to come in
				try {
					this.sleep((long)Math.random()*(maxTime-minTime+1) + minTime);
				} catch (Exception e) {
					System.out.println("Exception thrown: " + e.toString());
				}
				
				// Grab semaphore s so a customer can be added to the waiting room
				s.P();
				
				// If the waiting room is empty and the barber is asleep, wake him up
				if (wr.isEmpty() && wr.isBarberGoingToSleep()){
					wr.setBarberGoingToSleep(false);
					pt.debugAppend("Customer -> Waking up the barber");
					
					// Getting the barber running requires releasing the delaySem semaphore
					delaySem.V();
				}
				
				// If the waiting room isn't full, add a customer. If it is full, nothing happens here
				if (!wr.isFull()){
					wr.addCust();
					myCanvas.enterShop();
					pt.debugAppend("Customer -> Joining the waiting room");
				}
				
				// Semaphore s is released since the adjustments to the waiting room are done
				s.V();
				
			}
			
			else {
				waitThread();
			}
		}
	}
	
	// Set the MyPanelTop object so that statements can be printed to the debugging window
	public void setMyTopPanel(MyPanelTop pt) {
		this.pt = pt;
	}
	
	// Updates min and max arrival times; only called if min <= max
	public void resetTimes(int min, int max){
		minTime = min;
		maxTime = max;
	}
	
	// If thread is not alive, make it alive and notify if it was waiting
	public synchronized void revive() {
		if (!isAlive){
			isAlive = true;
			
			if(isWaiting){
				notify();
			}
		}
		pt.debugAppend("Customer -> You revived the customers");
	}

	// If thread is alive, make it not alive
	public void kill() {
		if (isAlive){
			isAlive = false;
		}
		pt.debugAppend("Customer -> You killed the customers");
	}
	
	// Wait until the thread is notified when it is alive again
	public synchronized void waitThread(){
		try{
			isWaiting = true;
			this.wait();
		}catch (Exception e){}
	}

}
