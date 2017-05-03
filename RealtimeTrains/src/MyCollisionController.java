import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MyCollisionController extends Thread{
	
	private ReedContacts rc;
	private Train train1;
	private Train train2;
	private DfaCanvas dfaCanvas;
	private GUIControlPanel gcp;
	
	private boolean planReset;
	
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

			
			if (train2.trainStarted() && !planReset){
				checkTrain1collisions();
				checkTrain2collisions();
			}
			
			for(int i = 1; i < 16; i++){
				boolean tripped = rc.readASwitch(i);
				
				if(tripped && !planReset){
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
			
			if(planReset){
				train1.performReset();
				train2.performReset();
				dfaCanvas.update(0,0);
				gcp.setResetWait(false);
				gcp.repaint();
				planReset = false;
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
		// check if current node of each are two apart
		
		if(lookAhead(train1, train2) > 1){
			train2.setTrainStarted(true);
			train2.release();
		}
	}
	
	public void checkTrain1collisions(){
		
		int dist = lookAhead(train2, train1);
		int train1place = train1.getCurrent();
		int future = 3;
		
		boolean switch2Danger = ((train1place==5 || train1place==6) && train1.GetSwitch2());
		boolean switch4Danger = ((train1place==8 || train1place==9) && train1.GetSwitch4());
		
		if (switch2Danger || switch4Danger){
			future = futureCheck(train2, train1);
		}
		
		if (dist == 1 || future == 1){
			train1.setColStop(true);
		}
		
		else if (dist == 2 || future == 2){
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
		int train2place = train2.getCurrent();
		int future = 3;
		
		boolean startDanger = ((train2place == 0) && train1.trainStarted());
		boolean switch2Danger = ((train2place==5 || train2place==6) && train2.GetSwitch2());
		boolean switch4Danger = ((train2place==8 || train2place==9) && train2.GetSwitch4());
		
		if (switch2Danger || switch4Danger || startDanger){
			future = futureCheck(train1, train2);
		}
		
		if (dist == 1 || future == 1){
			train2.setColStop(true);
		}
		else if (dist == 2 || future == 2){
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
	public int lookAhead(Train ahead, Train behind){
		
		int check = behind.getCurrent();
		int i = 0;
		
		while(i < 3){
			if(check == ahead.getCurrent()){
				return i;
			}
			check = behind.getNext(check);
			i++;
		}
		
		return i;
	}
	
	// For sake of switch coordination, also check "ahead"'s next node
	public int futureCheck(Train ahead, Train behind){
		
		int check = behind.getCurrent();
		int inFront = ahead.getNext(ahead.getCurrent());
		int i = 0;
		
		while(i < 3){
			if(check == inFront){
				return i;
			}
			check = behind.getNext(check);
			i++;
		}
		
		return i;
	}
	
	// Check if the switches are clear, and it is safe to switch tracks
	public boolean switchClear(int switchNum) {
		int train1Pos = train1.getCurrent();
		int train2Pos = train2.getCurrent();
		
		if(switchNum == 2){
			return !(train1Pos == 5 || train1Pos == 6 || train2Pos == 5 || train2Pos == 6);
		}
		
		else if(switchNum == 4){
			return !(train1Pos == 8 || train1Pos == 9 || train2Pos == 8 || train2Pos == 9);
		}
		
		return true;
	}
	
	public void reset(){
		planReset = true;
	}
	
	public void setControlPanel(GUIControlPanel gcp){
		this.gcp = gcp;
	}

}