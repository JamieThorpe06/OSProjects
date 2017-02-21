
import java.awt.Container;
import javax.swing.JFrame;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		   JFrame myWindow = new JFrame();
	       
	        myWindow.setTitle("CS380 Assignment 1");
	        myWindow.setSize(1000, 600);
	        myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        MyTopPanel mytop = new MyTopPanel();
	        MyStack stack = new MyStack(mytop.textarea, mytop.stackTextArea, 30);
	        mytop.setStack(stack);
	    	
	    	MyThread t1 = new MyThread("Thread 1 (Pusher)", stack, 750, false); 
			MyThread t2 = new MyThread("Thread 2 (Pusher)", stack, 800, false);
			MyThread t3 = new MyThread("Thread 3 (Popper)", stack, 750, true);
			MyThread t4 = new MyThread("Thread 4 (Popper)", stack, 800, true);
			
			ArrayList<MyThread> myList = new ArrayList<MyThread>(4);
			myList.add(t1);
			myList.add(t2);
			myList.add(t3);
			myList.add(t4);
			
			mytop.setThreads(myList);
	    	       
	        Container pane = myWindow.getContentPane();
	        pane.add("Center", mytop);
	        myWindow.setVisible(true);

	}

}