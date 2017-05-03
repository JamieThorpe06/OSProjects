import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class realtimetrains {

	public static void main(String[] args) {

		ReedContacts ReedContacts = new ReedContacts();
		FAArray fa = new FAArray();

		Train Train1 = new Train("Train1", 300, 0, ReedContacts, fa);
		Train Train2 = new Train("Train2", 3000, 1, ReedContacts, fa);
		MyCollisionController mcc = new MyCollisionController(ReedContacts, Train1, Train2);

		GUIControlPanel myGUIControlPanel = new GUIControlPanel(Train1, Train2, mcc);
		mcc.setControlPanel(myGUIControlPanel);
		GUITrackPanel myGUITrackPanel = new GUITrackPanel(myGUIControlPanel,
				Train1, Train2);

		JFrame myMainWindow = new JFrame();
		myMainWindow.setSize(745, 560);
		myMainWindow.setResizable(false);
		myMainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myMainWindow
				.setTitle("Dr. Webster's RealTime Trains Simulation Problem STUB CS380 Program");
		File file = null;
		String path = null;
		Image iconimage;
		try {
			path = "images/icon.bmp";
			file = new File(path);
			iconimage = ImageIO.read(file);
			myMainWindow.setIconImage(iconimage);
		} catch (Exception e) {

		}

		Container mainpane = myMainWindow.getContentPane();
		mainpane.add(myGUITrackPanel, BorderLayout.PAGE_START);
		mainpane.add(myGUIControlPanel, BorderLayout.CENTER);
		myMainWindow.setVisible(true);
		Train1.SetSpeed(14);
		Train2.SetSpeed(2);
		mcc.start();
		Train1.start();
		Train2.start();
		while (true) {

			try {
				long mytime = (long) (Math.random() * 10);
				Thread.sleep(mytime);
			} catch (Exception e) {
				System.out.println("exception in  sleep" + e.toString());
			}
			myGUITrackPanel.repaint();
		}
	}

}