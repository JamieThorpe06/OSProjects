import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MyTopPanel extends JPanel implements ActionListener {

	Button StopButton;
	Button StartButton;
	Button StopPushButton;
	Button StartPushButton;
	Button StopPopButton;
	Button StartPopButton;
	Button statisticsButton;
	Button stackButton;
	//boolean output = false;
	MyStack stack;
	TextArea textarea = new TextArea();
	TextArea stackTextArea = new TextArea("Current stack:\n");
	//TextArea stackTextArea;
	JPanel mypanel = new JPanel();
	
	ArrayList<MyThread> threadList = null;
	MyThread t1;
	MyThread t2;
	MyThread t3;
	MyThread t4;

	public MyTopPanel() {

		this.setLayout(new BorderLayout());

		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(1, 2));

		textarea.setFont(new Font("Helvetica", Font.PLAIN, 12));
		textarea.setEditable(false);
		textarea.setSize(100, 100);

		stackTextArea.setFont(new Font("Helvetica", Font.PLAIN, 12));
		stackTextArea.setEditable(false);
		stackTextArea.setSize(100, 100);

		textPanel.add(textarea);
		textPanel.add(stackTextArea);
		// create control buttons
		// this.setLayout(null); // set layout to null
		// ///////////////////////
		
		StopButton = new Button("Stop All");
		StartButton = new Button("Start All");
		StopPushButton = new Button("Stop Push");
		StartPushButton = new Button("Start Push");
		StopPopButton = new Button("Stop Pop");
		StartPopButton = new Button("Start Pop");
		
		statisticsButton = new Button("View Statistics");
		stackButton = new Button("View Stack");

		mypanel.add(StartPushButton);
		mypanel.add(StopPushButton);
		
		mypanel.add(StartPopButton);
		mypanel.add(StopPopButton);
	
		mypanel.add(StartButton);
		mypanel.add(StopButton);
		mypanel.add(statisticsButton);
		mypanel.add(stackButton);
		
		textarea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
		this.add(textPanel, BorderLayout.CENTER);

		this.add(mypanel, BorderLayout.SOUTH);
		StopPushButton.addActionListener(this);
		StartPushButton.addActionListener(this);
		StopPopButton.addActionListener(this);
		StartPopButton.addActionListener(this);
	
		StopButton.addActionListener(this);
		StartButton.addActionListener(this);
		statisticsButton.addActionListener(this);
		stackButton.addActionListener(this);
		
	}

	public void setThreads(ArrayList<MyThread> threadList) {
		this.threadList = threadList;
		t1 = threadList.get(0);
		t2 = threadList.get(1);
		t3 = threadList.get(2);
		t4 = threadList.get(3);
	}

	public void setStack(MyStack stack) {
		this.stack = stack;
	}

	// /////////////////////////////////
	// Implementation of ActionListener
	// /////////////////////////////////
	//@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == StartPushButton) {
			t1.revive();
			t2.revive();
		}
		if (ae.getSource() == StartPopButton) {
			t3.revive();
			t4.revive();
		}
		if (ae.getSource() == StopPushButton) {
			t1.kill();
			t2.kill();
		}
		if (ae.getSource() == StopPopButton) {
			t3.kill();
			t4.kill();
		}
		if (ae.getSource() == StartButton) {
			t1.revive();
			t2.revive();
			t3.revive();
			t4.revive();
			
		}
		if (ae.getSource() == StopButton) {
			t1.kill();
			t2.kill();
			t3.kill();
			t4.kill();
		}
		if (ae.getSource() == statisticsButton) {	
			stack.output = false;
			
			t1.kill();
			t2.kill();
			t3.kill();
			t4.kill();
			
			stackTextArea.setText("");
			stackTextArea.append("Stack Size: " + stack.size + "\n");
			stackTextArea.append("Stack Sum: " + stack.total + "\n");
			
			//FIX THE AVERAGE AND PUSH PER POP TO FLOAT 
			
			double avg = (stack.size == 0) ? 0 : (stack.total/stack.size);
			stackTextArea.append("Stack Average: " + avg + "\n\n");
			
			int totalPops = t3.counter + t4.counter;
			int totalPush = t1.counter + t2.counter;
			stackTextArea.append("Number of Pops: " + totalPops + "\n");
			stackTextArea.append("Number of Pushes: " + totalPush + "\n");
			
			if(totalPops != 0){
				stackTextArea.append(String.format("Pushes per Pop (successfully): %f.2 \n", totalPush/totalPops));
			}
			
			stackTextArea.append("Thread 1 (Pusher) ran " + t1.counter + " times.\n");
			stackTextArea.append("Thread 2 (Pusher) ran " + t2.counter + " times.\n");
			stackTextArea.append("Thread 3 (Popper) ran " + t3.counter + " times.\n");
			stackTextArea.append("Thread 4 (Popper) ran " + t4.counter + " times.\n\n");
			
			double allMoves = ((totalPops + totalPush) == 0) ? 1.0: (totalPops + totalPush);
			stackTextArea.append(String.format("Thread 1 (Pusher) run percentage was %f.2 %\n", (t1.counter/allMoves)*100));
			stackTextArea.append(String.format("Thread 2 (Pusher) run percentage was %f.2 %\n", (t2.counter/allMoves)*100));
			stackTextArea.append(String.format("Thread 3 (Popper) run percentage was %f.2 %\n", (t3.counter/allMoves)*100));
			stackTextArea.append(String.format("Thread 4 (Popper) run percentage was %f.2 %\n\n", (t4.counter/allMoves)*100));
		}
		
		if (ae.getSource() == stackButton){
			stack.output = true;
			stack.printStack();
		}
		
	} // end actionPerformed, implementation of ActionListener
}