
public class Customer extends Thread {
	
	private int delayTime;
	private WaitRoom wr;
	private MyCanvas myCanvas;
	private Semaphore s;
	private Semaphore delaySem;
	private MyPanelTop pt;
	
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
			
			if (isAlive) {
				
				s.P();
				if (wr.isEmpty() && wr.isBarberGoingToSleep()){
					delaySem.V();
				}
				if (!wr.isFull()){
					wr.addCust();
					myCanvas.enterShop();
				}
				s.V();
				
				// Check order of code and signaling
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
