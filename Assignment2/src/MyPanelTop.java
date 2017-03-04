
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

public class MyPanelTop extends JPanel implements ActionListener,
		WindowListener {
	// -------------------------------------------------------------------
	// this is the GUI controller for buttons and graphics
	// -------------------------------------------------------------------
	
	private static Button startBarber, killBarber, startCustomer, killCustomer,
			viewOutput;
	private static TextField minArrival, maxArrival, minCut, maxCut;
	private static TextArea debug;
	private static JFrame output = new JFrame();
	private boolean outputOn = true;
	private int minArrivalTime;
	private int maxArrivalTime;
	private int minCutTime;
	private int maxCutTime;
	
	private Barber barber;
	private Customer cust;

	public MyPanelTop(Customer customer, Barber barber) {

		this.setLayout(null);
		this.barber = barber;
		this.cust = customer;

		startBarber = new Button("Start Barber");
		add(startBarber);
		startBarber.setSize(100, 40);
		startBarber.setLocation(450, 20);
		startBarber.addActionListener(this);

		killBarber = new Button("Kill Barber");
		add(killBarber);
		killBarber.setSize(100, 40);
		killBarber.setLocation(575, 20);
		killBarber.addActionListener(this);

		startCustomer = new Button("Start Customer");
		add(startCustomer);
		startCustomer.setSize(100, 40);
		startCustomer.setLocation(75, 20);
		startCustomer.addActionListener(this);

		killCustomer = new Button("Kill Customer");
		add(killCustomer);
		killCustomer.setSize(100, 40);
		killCustomer.setLocation(200, 20);
		killCustomer.addActionListener(this);

		viewOutput = new Button("View Output");
		add(viewOutput);
		viewOutput.setSize(100, 40);
		viewOutput.setLocation(325, 20);
		viewOutput.addActionListener(this);

		minArrival = new TextField("1000");
		minArrivalTime = 1000;
		add(minArrival);
		minArrival.setSize(60, 25);
		minArrival.setLocation(103, 94);
		minArrival.addActionListener(this);

		maxArrival = new TextField("3000");
		maxArrivalTime = 3000;
		add(maxArrival);
		maxArrival.setSize(60, 25);
		maxArrival.setLocation(228, 94);
		maxArrival.addActionListener(this);

		minCut = new TextField("2000");
		minCutTime = 2000;
		add(minCut);
		minCut.setSize(60, 25);
		minCut.setLocation(479, 94);
		minCut.addActionListener(this);

		maxCut = new TextField("4000");
		maxCutTime = 4000;
		add(maxCut);
		maxCut.setSize(60, 25);
		maxCut.setLocation(608, 94);
		maxCut.addActionListener(this);

		output = new JFrame("output");
		output.setSize(320, 600);
		output.setLocation(700, 100);
		output.addWindowListener(this);

		debug = new TextArea();
		output.add(debug);
		debug.setSize(320, 220);
		debug.setLocation(120, 165);
		debug.setEditable(false);
		output.setVisible(true);
		outputOn = true;
	}

	// implementation of window listener

	public void windowActivated(WindowEvent we) {
	}

	public void windowClosed(WindowEvent we) {
	}

	public void windowClosing(WindowEvent we) {
		if (we.getSource() == output) {
			output.setVisible(false);
			outputOn = false;
		}

	}

	public void windowDeactivated(WindowEvent we) {
	}

	public void windowDeiconified(WindowEvent we) {
	}

	public void windowIconified(WindowEvent we) {
	}

	public void windowOpened(WindowEvent we) {
	}

	@Override
	public void paint(Graphics g) {

		// //////////////////////////
		g.drawString("Cutting Time", 531, 80);
		g.drawString("min:", 77, 110);
		g.drawString("max:", 200, 110);

		g.drawString("Arrival Time", 151, 80);
		g.drawString("min:", 452, 110);
		g.drawString("max:", 580, 110);
	}

	public void debugAppend(String temp) {
		debug.append(temp + "\n");
	}

	// /////////////////////////////////
	// Implementation of ActionListener
	// /////////////////////////////////
	public void actionPerformed(ActionEvent ae) {
		
		if (ae.getSource() == startBarber) {
			barber.revive();
			debug.append("Barber arrives at shop\n");
		}
		
		if (ae.getSource() == killBarber) {
			barber.kill();
			debug.append("Barber goes home\n");
		}
		
		if (ae.getSource() == startCustomer) {
			cust.revive();
			debug.append("Customers start arriving\n");
		}
		
		if (ae.getSource() == killCustomer) {
			cust.kill();
			debug.append("Customers stop arriving\n");
		}
		
		if (ae.getSource() == viewOutput) { // Is this implemented correctly?
			if(outputOn){
				outputOn = false;
			}
			else{
				outputOn = true;
			}
		}
		
		if (ae.getSource() == minArrival) {
			minArrivalTime = Integer.parseInt(minArrival.getText());
		}
		
		if (ae.getSource() == maxArrival) {
			maxArrivalTime = Integer.parseInt(maxArrival.getText());
		}
		
		if (ae.getSource() == minCut) {
			minCutTime = Integer.parseInt(minCut.getText());
		}
		
		if (ae.getSource() == maxCut) {
			maxCutTime = Integer.parseInt(maxCut.getText());
		}
					

	} // end actionPerformed, implementation of ActionListener
}

