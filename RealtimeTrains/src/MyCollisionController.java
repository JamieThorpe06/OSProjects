import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MyCollisionController extends Thread{
	
	ReedContacts rc;
	Train train1;
	Train train2;
	DfaCanvas dfaCanvas;
	
	MyCollisionController(ReedContacts reed, Train t1, Train t2){
		
		rc = reed;
		train1 = t1;
		train2 = t2;
		
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
		while(true){
			//System.out.println("Controller is running");
			
			try {
				Thread.sleep(30);
			} catch (Exception e) {
				System.out.println("exception in thread sleep" + e.toString());
			}
			
			//System.out.println("Controller is awake");
			
			if (train2.trainStarted()){
				checkTrain1collisions();
				checkTrain2collisions();
			}
			
			for(int i = 1; i < 16; i++){
				boolean tripped = rc.readASwitch(i);
				
				if(tripped){
					System.out.println("Signal " + i + " tripped");
					checkTrain1(i);
					
					if(train2.trainStarted()){
						//System.out.println("Train2 started");
						checkTrain2(i);
					}
					else{
						checkTrain1toStartTrain2();
					}
				}
			}
			
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
		
		if(lookAhead(train1, train2) > 2){
			train2.setTrainStarted(true);
			train2.release();
			
		}
		
	}
	
	// Do we need to check getSpeedZero in the collision methods?
	// If it is speed zero and we move forward and then the "ahead" train (behind in switch) is released,
	// will it check again for collisions before colliding?
	// Or  if we don't check, could the trains end up stalled for a while?
	
	public void checkTrain1collisions(){
		
		int dist = lookAhead(train2, train1);
		
		if (dist == 1 && !train2.getColStop() && !train2.getSpeedZero()){ 
			train1.setColStop(true);
		}
		else if (dist == 2){
			train1.SetSpeed((train1.GetDesiredSpeed())/2);
		}
		else{
			if(train1.getColStop()){
				train1.colSafe();
				train1.setColStop(false);
			}
			if(train1.getSpeed() != train1.GetDesiredSpeed()){
				train1.SetSpeed(train1.GetDesiredSpeed());
			}
		}
	}
	
	public void checkTrain2collisions(){ 
		
		int dist = lookAhead(train1, train2);
		
		if (dist == 1 && !train1.getColStop() && !train1.getSpeedZero()){
			train2.setColStop(true);
		}
		else if (dist == 2){
			train2.SetSpeed((train2.GetDesiredSpeed())/2);
		}
		else{
			if(train2.getColStop()){
				train2.colSafe();
				train2.setColStop(false);
			}
			if(train2.getSpeed() != train2.GetDesiredSpeed()){
				train2.SetSpeed(train2.GetDesiredSpeed());
			}
		}
		
	}
	
	// Return the distance between the trains, or 3 if it is further away than 2 nodes
	// For sake of switch coordination, also check "ahead"'s next node
	public int lookAhead(Train ahead, Train behind){
		
		int check = behind.getCurrent();
		int i = 0;
		
		while(i < 3){
			if(check == ahead.getCurrent() || check == ahead.getNext(ahead.getCurrent())){
				return i;
			}
			check = behind.getNext(check);
			i++;
		}
		
		return i;
	}
	
	public void reset(){
		dfaCanvas.update(0,0);
		
	}

}