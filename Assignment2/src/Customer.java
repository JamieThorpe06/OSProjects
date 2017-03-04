
public class Customer extends Thread {
	
	private int delayTime;
	private WaitRoom wr;
	private MyCanvas myCanvas;
	private Semaphore s;
	private Semaphore delaySem;
	private MyPanelTop pt; // for printing to debug panel
	
	private boolean isAlive = false;
	private boolean isWaiting = false;

	public Customer(String name, int delayTime, WaitRoom wr, MyCanvas myCanvas, Semaphore s, Semaphore delay) {
		super(name);
		this.delayTime = delayTime;
		this.wr = wr;
		this.myCanvas = myCanvas;
		this.s = s;
		this.delaySem = delay;
		
		this.start();
	}
	
	public void run() {
		while(true) {
			System.out.println("I am the Customer running");
			
			if (isAlive) {
				
				try {
					this.sleep((long)Math.random()*delayTime);
				} catch (Exception e) {
					System.out.println("Exception thrown: " + e.toString());
				}
				
				s.P();
				if (wr.isEmpty() && wr.isBarberGoingToSleep()){
					wr.setBarberGoingToSleep(false);
					pt.debugAppend("Customer -> Waking up the barber");
					delaySem.V();
				}
				if (!wr.isFull()){
					wr.addCust();
					myCanvas.enterShop();
					pt.debugAppend("Customer -> Joining the waiting room");
				}
				s.V();
				
			}
			
			else {
				waitThread();
			}
		}
	}
	
	public void setMyTopPanel(MyPanelTop pt) {
		this.pt = pt;
	}
	
	public synchronized void revive() {
		if (!isAlive){
			isAlive = true;
			
			if(isWaiting){
				notify();
			}
		}
	}

	public void kill() {
		if (isAlive){
			isAlive = false;
		}
	}
	
	public synchronized void waitThread(){
		try{
			isWaiting = true;
			this.wait();
		}catch (Exception e){}
	}

}
