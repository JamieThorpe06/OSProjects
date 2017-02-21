
public class MyThread extends Thread {
	
	private MyStack stack;
	private int delay;
	private boolean isAlive = false; //used to be true???
	private boolean isPopper;
	private boolean iswaiting = false;
	int number;
	int counter;

	public MyThread(String name, MyStack stack, int delay, boolean is_popper) {
		super(name);
		this.stack = stack;
		this.delay = delay;
		this.isPopper = is_popper;
		counter = 0;	
		
		this.start();
	}

	//Check if we should wait
	public void run() {
		while (true) {

			try {
				this.sleep((long) delay);
			} catch (Exception e) {
				System.out.println("Exception thrown: " + e.toString());
			}
// This is to prevent popping/pushing when the output is stopped.
			if (isAlive) {
				if (isPopper) {
					number = stack.pop();
					counter++;
				} else {
					number = (int) Math.ceil(Math.random() * 100);
					boolean success = stack.push(number);
					
					if(success){
						counter++;
					}
				}
			}
			else {
				waitThread();
			}

		}
	}

	public boolean isPopper() {
		return isPopper;
	}

	public synchronized void revive() {
		if (!isAlive){
			isAlive = true;
			if(iswaiting){
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
			iswaiting = true;
			this.wait();
		}catch (Exception e){}
	}

}