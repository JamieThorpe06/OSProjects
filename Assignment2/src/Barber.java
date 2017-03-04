
public class Barber extends Thread {
	
	private int delayTime;
	private WaitRoom wr;
	private MyCanvas myCanvas;
	private Semaphore s;
	private Semaphore delaySem;
	private MyPanelTop pt; // for printing to debug panel
	
	private boolean isAlive = false;
	private boolean isWaiting = false;

	public Barber(String name, int delayTime, WaitRoom wr, MyCanvas myCanvas, Semaphore s, Semaphore delay) {
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
			System.out.println("I am the Barber running");
			
			if (isAlive) {
				
				s.P();
				
				if(wr.isEmpty()){
					wr.setBarberGoingToSleep(true);
					pt.debugAppend("Barber -> Going to sleep");
					s.V();
					delaySem.P();
					s.P();
					pt.debugAppend("Barber -> Has been woken up");
				}
				
				pt.debugAppend("Barber -> Getting a customer");
				wr.removeCust();
				myCanvas.moveToChair();
				
				s.V();
				
				try {
					this.sleep((long)Math.random()*delayTime);
				} catch (Exception e) {
					System.out.println("Exception thrown: " + e.toString());
				}
	 
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
		pt.debugAppend("Barber -> I'm alive!");
	}

	public void kill() {
		if (isAlive){
			isAlive = false;
		}
		pt.debugAppend("Barber -> You killed the barber");
	}
	
	public synchronized void waitThread(){
		try{
			isWaiting = true;
			this.wait();
		}catch (Exception e){}
	}

}
