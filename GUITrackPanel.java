import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUITrackPanel extends Canvas {

	private GUIControlPanel GUIControlPanel;
	private Image offScrImg;
	private Image buffer;
	private Image TrainTrack;
	private Image YellowTrain;
	private Image PinkTrain;
	private Image WhiteLight2;
	private Image WhiteLight4;
	private MediaTracker imageTracker = new MediaTracker(this);
	private Train Train1;
	private Train Train2;
	private int SW2WhiteX = 320;
	private int SW2WhiteY = (195 - 180) / 2;

	private int SW4WhiteX = 320;
	private int SW4WhiteY = (195 - 180) / 2;

	public GUITrackPanel(GUIControlPanel p, Train Train1IN, Train Train2IN) {
		super();
		GUIControlPanel = p;
		Train1 = Train1IN;
		Train2 = Train2IN;
		File file = null;
		String path = null;
		try {
			path = "images/TrainTrack7402.jpg";
			file = new File(path);
			TrainTrack = ImageIO.read(file);
			imageTracker.addImage(TrainTrack, 100);

			path = "images/yellowtrain.gif";
			file = new File(path);
			YellowTrain = ImageIO.read(file);
			imageTracker.addImage(YellowTrain, 100);

			path = "images/pinktrain.gif";
			file = new File(path);
			PinkTrain = ImageIO.read(file);
			imageTracker.addImage(PinkTrain, 100);

			path = "images/whitelight.gif";
			file = new File(path);
			WhiteLight2 = ImageIO.read(file);
			imageTracker.addImage(WhiteLight2, 100);
			
			path = "images/whitelight.gif";
			file = new File(path);
			WhiteLight4 = ImageIO.read(file);
			imageTracker.addImage(WhiteLight4, 100);
		} catch (Exception e) {

		}
		this.setPreferredSize(new Dimension(740, 272));
		PaintSwitch2Straight();
		PaintSwitch4Straight();
	}

	public void paint(Graphics g) {
		try {
			imageTracker.waitForID(100);
		} catch (InterruptedException e) {
		}

		g.drawImage(TrainTrack, 0, 0, this);

		DrawTrain1(g);//
		DrawTrain2(g);//

		g.drawImage(WhiteLight2, SW2WhiteX, SW2WhiteY, this);
		g.drawImage(WhiteLight4, SW4WhiteX, SW4WhiteY, this);

	} // End Paint

	// /////////////////////////////////////
	// update method - for double buffering
	// /////////////////////////////////////
	// off screen image to do initial painting
	public void update(Graphics g) {
		// if off screen image is not created, create it
		if (offScrImg == null) {
			offScrImg = createImage(this.getWidth(), this.getHeight());
		}
		Graphics og = offScrImg.getGraphics();
		paint(og); // paint on the off screen graphics object
		g.drawImage(offScrImg, 0, 0, this); // draw completed image
		og.dispose();

	} // end update

	public void DrawTrain1(Graphics g) {
		TrackPoint2D mynextpoint = new TrackPoint2D();
		mynextpoint = Train1.GetCurrentTrainPosition();
		if (Train1.GetSwitch2())
		{
			PaintSwitch2Curved();
		}
		else
		{
			PaintSwitch2Straight();
		}
		if (Train1.GetSwitch4())
		{
			PaintSwitch4Curved();
		}
		else
		{
			PaintSwitch4Straight();
		}
		int w = YellowTrain.getWidth(this);
		int x = mynextpoint.getX() - w / 2;
		int h = YellowTrain.getHeight(this);
		int y = mynextpoint.getY() - h / 2;
		g.drawImage(YellowTrain, x, y, this);
	}

	public void DrawTrain2(Graphics g) {
		TrackPoint2D mynextpoint = new TrackPoint2D();
		mynextpoint = Train2.GetCurrentTrainPosition();
		int w = PinkTrain.getWidth(this);
		int x = mynextpoint.getX() - w / 2;
		int h = PinkTrain.getHeight(this);
		int y = mynextpoint.getY() - h / 2;
		g.drawImage(PinkTrain, x, y, this);
	}

	public boolean mouseDown(Event evt, int x, int y) {

		// Alternate track switches
		// Switch #1 Turn
		if (((x > 179) && (x < 200)) && ((y > 220) && (y < 235))) {
			//GUIControlPanel.flipSwitch(false, 0);
			return true;
		}
		// Switch #1 Straight
		else if (((x > 179) && (x < 200)) && ((y > 245) && (y < 260))) {
			//GUIControlPanel.flipSwitch(true, 0);
			return true;
		}
		// Switch #2 Turn
		else if (((x > 309) && (x < 330)) && ((y > 203) && (y < 218))) {
			//GUIControlPanel.flipSwitch(false, 1);
			//PaintSwitch2Curved();

			return true;
		}
		// Switch #2 Straight
		else if (((x > 309) && (x < 330)) && ((y > 180) && (y < 195))) {
			//GUIControlPanel.flipSwitch(true, 1);
			//PaintSwitch2Straight();

			return true;
		}
		// Switch #3 Turn
		else if (((x > 294) && (x < 310)) && ((y > 50) && (y < 65))) {
			//GUIControlPanel.flipSwitch(false, 2);
			return true;
		}
		// Switch #3 Straight
		else if (((x > 294) && (x < 310)) && ((y > 75) && (y < 90))) {
			//GUIControlPanel.flipSwitch(true, 2);
			return true;
		}
		// Switch #4 Turn
		else if (((x > 179) && (x < 195)) && ((y > 35) && (y < 50))) {
			//GUIControlPanel.flipSwitch(false, 3);
			return true;
		}
		// Switch #4 Straight
		else if (((x > 179) && (x < 195)) && ((y > 13) && (y < 25))) {
			//GUIControlPanel.flipSwitch(true, 3);
			return true;
		} else
		// Alternate track switches
		// Switch #1 Turn
		if (((x > 570) && (x < 650)) && ((y > 180) && (y < 220))) {
			// JOptionPane.showMessageDialog(null, "RESET: x " + x + " Y " + y);
			ResetAll();
			return true;
		}
		// JOptionPane.showMessageDialog(null, "x " + x + " Y " + y);

		return false;
	}

	public void PaintSwitch2Curved() {
		int halfX = WhiteLight2.getWidth(this) / 2;
		int halfY = WhiteLight2.getHeight(this) / 2;
		SW2WhiteX = 320;
		SW2WhiteX = SW2WhiteX - halfX;
		SW2WhiteY = 187;
		SW2WhiteY = SW2WhiteY - halfY;
		//System.out.print("X is " + WhiteX + " Y " + WhiteY);
	}

	public void PaintSwitch2Straight() {
		int halfX = WhiteLight2.getWidth(this) / 2;
		int halfY = WhiteLight2.getHeight(this) / 2;
		SW2WhiteX = 320;
		SW2WhiteX = SW2WhiteX - halfX;
		SW2WhiteY = 210;
		SW2WhiteY = SW2WhiteY - halfY;
		//System.out.print("X is " + WhiteX + " Y " + WhiteY);
	}
	
	public void PaintSwitch4Straight() {
		int halfX = WhiteLight4.getWidth(this) / 2;
		int halfY = WhiteLight4.getHeight(this) / 2;
		SW4WhiteX = 187;
		SW4WhiteX = SW4WhiteX - halfX;
		SW4WhiteY = 42;
		SW4WhiteY = SW4WhiteY - halfY;
		
	}

	public void PaintSwitch4Curved() {
		int halfX = WhiteLight4.getWidth(this) / 2;
		int halfY = WhiteLight4.getHeight(this) / 2;
		SW4WhiteX = 187;
		SW4WhiteX = SW4WhiteX - halfX;
		SW4WhiteY = 19;
		SW4WhiteY = SW4WhiteY - halfY;
		
	}

	public void ResetAll() {
		Train1.Reset();
		Train2.Reset();
		GUIControlPanel.ResetAll();
		// JOptionPane.showMessageDialog(null, "RESET ALL");

	}

}
