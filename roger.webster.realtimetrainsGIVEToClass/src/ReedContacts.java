import javax.swing.JOptionPane;


public class ReedContacts {
	private Boolean[] ReadContactSwitches = new Boolean[16];
	private int index = 0;
	
	public ReedContacts()
	{
		for (int i=0;i<16;i++)
		{
			ReadContactSwitches[i] = false;//all off to start
		}
		
	}
	
	public synchronized void tripASwitch(int index){
		ReadContactSwitches[index] = true;//
		//JOptionPane.showMessageDialog(null, "Contact Switch Tripped " + index);
		System.out.println ("Contact Switch Tripped: " + index);
	}
	public synchronized void resetASwitch(int index){
		ReadContactSwitches[index] = false;//
	}
	public synchronized Boolean readASwitch(int index){	
		boolean tripped = ReadContactSwitches[index];
		ReadContactSwitches[index] = false;//
		return(tripped);
	}
}
