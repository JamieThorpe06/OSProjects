
public class FANode {
	
	private String name;
	private int signal1; //index in ReedContacts array of signal to leave current node; follow straight
	private int signal2; //same as signal 1, but switch track if necessary
	private int nextInd; //index of FAArray if switch remains straight
	private int jumpTo; //index of FAArray to jump to in case of track switch 2 or 4
	
	FANode(String name, int trigger, int trigger2, int next, int j){
		this.name = name;
		signal1 = trigger;
		signal2 = trigger2;
		nextInd = next;
		jumpTo = j;
	}
	
	FANode(String name, int trigger, int next){
		this.name = name;
		signal1 = trigger;
		nextInd = next;
	}
	
	public int getNext(){
		return nextInd;
	}
	
	public int getJump(){
		return jumpTo;
	}
	
	public boolean hasJump(){
		return (jumpTo != 0);
	}
	
	public String getName(){
		return name;
	}
	
	public int getStraightSig() {
		return signal1;
	}
	
	public int getJumpSig() {
		return signal2;
	}

}
