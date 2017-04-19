
public class FAArray {
	
	private FANode[] graph = new FANode[13];
	
	FAArray(){
		graph[0] = new FANode("S", 15, 1);
		
		graph[1] = new FANode("I1", 6, 2);
		graph[2] = new FANode("I2", 5, 3);
		graph[3] = new FANode("I3", 4, 4);
		graph[4] = new FANode("I4", 12, 5);
		graph[5] = new FANode("I5", 11, 6);
		graph[6] = new FANode("I6", 10, 9, 1, 7);
		
		graph[7] = new FANode("O1", 7, 8);
		graph[8] = new FANode("O2", 1, 9);
		graph[9] = new FANode("O3", 2, 3, 10, 4);
		graph[10] = new FANode("O4", 13, 11);
		graph[11] = new FANode("O5", 14, 12);
		graph[12] = new FANode("O6", 8, 7);
		
 	}
	
	public int getSignal(int in){
		return graph[in].getStraightSig();
	}
	
	public int getNext(int in){
		return graph[in].getNext();
	}
	
	public void switchThrown(int sw){
		if(sw == 2){
			graph[6].switchNodes();
		}
		else if(sw == 4){
			graph[9].switchNodes();
		}
	}
	
}