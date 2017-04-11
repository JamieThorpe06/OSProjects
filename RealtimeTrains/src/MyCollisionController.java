import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MyCollisionController extends Thread{
	
	ReedContacts rc;
	Train train1;
	Train train2;
	DfaCanvas dfaCanvas;
	Semaphore sem;
	
	MyCollisionController(ReedContacts reed, Train t1, Train t2, Semaphore s){
		
		rc = reed;
		train1 = t1;
		train2 = t2;
		sem = s;
		
		// Display the implemented DFA to demonstrate each trains state.
		JFrame dfa = new JFrame ( );
		dfa.setSize (700, 475);
		dfa.setLocation (700, 100);
		dfa.setResizable(false);
		dfa.setTitle ("RealTime Trains: DFA of the Current Train State (Kimberlyn Broskie)");
		dfaCanvas = new DfaCanvas ();
		dfa.getContentPane().add(dfaCanvas, BorderLayout.PAGE_START);
		dfa.setVisible(true);
	}
	
	public void run(){
		System.out.println("Controller is running");
		while(true){
			
			try {
				Thread.sleep(30);
			} catch (Exception e) {
				System.out.println("exception in thread sleep" + e.toString());
			}
			
			sem.P();
			
			System.out.println("Controller is awake");
			for(int i = 0; i < 16; i++){
				boolean tripped = rc.readASwitch(i);
				
				if(tripped){
					System.out.println("Signal " + i + " tripped");
					checkTrain1(i);
					
					if(train2.trainStarted()){
						System.out.println("Train2 started");
						checkTrain2(i);
					}
					else{
						checkTrain1toStartTrain2();
					}
				}
			}
			
			sem.V();
		}
	}
	
	public void checkTrain1(int signal){
	
		if(train1.getSignal() == signal){
			System.out.println("Train1 advancing");
			train1.advance();
			dfaCanvas.update(train1.getCurrent(), train2.getCurrent());
		}
	}
	
	public void checkTrain2(int signal){
		if(train2.getSignal() == signal){
			System.out.println("Train2 advancing");
			train2.advance();
			dfaCanvas.update(train1.getCurrent(), train2.getCurrent());
		}
	}
	
	public void checkTrain1toStartTrain2(){
		// check if current node of each are two apart; changes if one of the switches are flipped
		
		int check = train2.getCurrent();
		for(int i = 0; i <= 2; i++){
			if(check == train1.getCurrent()){
				return;
			}
			check = train2.getNext(check);
		}
		train2.advance();
		dfaCanvas.update(train1.getCurrent(), train2.getCurrent());
		
	}

}
