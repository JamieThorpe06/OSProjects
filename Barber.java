
public class Barber extends Thread {
	
	private int delayTime;
	private WaitRoom wr;
	private MyCanvas myCanvas;
	private Semaphore s;
	private Semaphore delaySem;
	private MyPanelTop pt;
	
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
			
			if (isAlive) {
				// Do main code here
				// Make sure to check if the thread is alive, OR don't tell it to start in constructor 
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
