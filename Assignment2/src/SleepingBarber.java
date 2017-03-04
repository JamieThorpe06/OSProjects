
import javax.swing.*;     // For JFrame
import java.awt.*;       // For Color and Container

public class SleepingBarber {
	
	public static void main(String[] args) {
		
		Customer customer;
        Barber barber;

        JFrame myMainWindow = new JFrame();
        myMainWindow.setTitle("Dr. Webster's Sleeping Barber Problem CS380 Program");
        myMainWindow.setSize(700, 700);
        myMainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container mainpane = myMainWindow.getContentPane();
        
        
        WaitRoom wr = new WaitRoom(0, 40);
        MyCanvas mycanvas = new MyCanvas(wr);
        Semaphore s = new Semaphore(1, 1);
        Semaphore delay = new Semaphore(0, 1);
       
        customer = new Customer("Customer", 1000, wr, mycanvas, s, delay);
        barber = new Barber("Barber", 1300, wr, mycanvas, s, delay);
        MyPanelTop mytop = new MyPanelTop(customer, barber);
        customer.setMyTopPanel(mytop);
        barber.setMyTopPanel(mytop);
        wr.setPanelTop(mytop);
        
        mytop.setPreferredSize(new Dimension(200, 130));
        mainpane.add(mytop, BorderLayout.PAGE_START);
        mainpane.add(mycanvas, BorderLayout.CENTER);
        myMainWindow.setVisible(true);


	}
	
	// What is the delay number passed to the threads?
	// Does it have anything to do with the min and max cut and arrive times?
	// If so, what's the point? Can't we get these times from MyPanelTop?

}
