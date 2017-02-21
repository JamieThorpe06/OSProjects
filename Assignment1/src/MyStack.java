
import java.awt.TextArea;

public class MyStack {

	int[] data;
	int idx = 0;
	int size = 0;
	int len;
	int total = 0;
	TextArea text;
	TextArea stackText;
	
	boolean output = false;
	
	int wait_count = 0;
	
	MyStack (TextArea textarea, TextArea stackTextArea, int len){
		data = new int[len];
		this.len = len;
		text = textarea;
		stackText = stackTextArea;
	}
	
	public synchronized boolean push(int i) {
		if(size != len){ //should this be while???
			data[idx] = i;
			idx++;
			size++;
			total += i;
			
			text.append(Thread.currentThread().getName() + " pushed " + i + "\n"); 
			printStack();
			
			if(wait_count != 0){
				try{
					wait_count -= 1;
					notify();
				}catch (Exception e) {}
			}
			return true;
		}
		else{
			text.append("No more room to push!\n");
			return false;
		}
	}
	
	public synchronized int pop() {
		while(size == 0){
			try {
				wait_count += 1;
				wait();
			} catch (Exception e) {}
		}
		
		idx--;
		int result = data[idx];
		total -= result;
		size--;
		
		text.append(Thread.currentThread().getName() + " popped " + result + "\n");
		printStack();
		return result;
	}
	
	public void printStack() { 
		if(output){
			stackText.setText("Current Stack:\n");
			int i = size-1;
			while(i >= 0){
				String line = String.format("|       %-7d|\n", data[i]);
				stackText.append(line);
				i --;
			}		
			stackText.append("----------------");
		}
	}
}
