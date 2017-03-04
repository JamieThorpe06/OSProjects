
//----------------------------------------------------
// DON NOT EDIT
//----------------------------------------------------
public class WaitRoom {
    // this is THE object shared among all threads
	private  int customers;
	private  int maxCustomers;
	private  MyPanelTop panel;
    private boolean BarberGoingToSleep = false;
    
    public WaitRoom(int c, int mc) {
        customers = c;
        maxCustomers = mc;
    }

    public synchronized void addCust() {
        customers++;
        panel.debugAppend("Customer Entered room; Currently " + customers + " customers.");
    }

    public synchronized void removeCust() {
        customers--;
        panel.debugAppend("Customer Left room; Currently " + customers + " customers.");
    }

    public synchronized int custCount() {
        return customers;
    }

    public synchronized boolean isFull() {
        return (customers == maxCustomers);
    }

    public synchronized boolean isEmpty() {
        return customers <= 0;
    }

    public void setPanelTop(MyPanelTop ptin) {
        panel = ptin;
    }
    //-----------------------------------------------
    //called by Barber 
    //-----------------------------------------------
    public synchronized void setBarberGoingToSleep(Boolean slp){
    	BarberGoingToSleep = slp;
    }   
    //-----------------------------------------------
    //called by Customer 
    //-----------------------------------------------
    public synchronized boolean isBarberGoingToSleep(){
    	return(BarberGoingToSleep);	
    }
    
}
