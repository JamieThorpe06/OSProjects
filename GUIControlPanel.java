import java.awt.Canvas;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUIControlPanel extends Canvas {
	private Image TrainControls;
	private Image TrainTrack;
	private Image TrainView;
	private Image RedLight;
	private Image BlackTile;
	private Image GreenLight;
	private Image Zero;
	private Image One;
	private Image Two;
	private Image GreyTile;
	private boolean first_time = true;
	private int whichTrain = 1;
	private int Train1Speed = 0;
	private int Train2Speed = 0;
	private boolean stopped = false;
	private boolean switchOn[] = new boolean[4];
	private MediaTracker imageTracker;
	private Train Train1;
	private Train Train2;
	private Boolean AutoControlFlag = false;
	private MyCollisionController mycc;

	public GUIControlPanel(Train Train1IN, Train Train2IN, MyCollisionController cc) {
		super();
		Train1 = Train1IN;
		Train2 = Train2IN;
		mycc = cc;
		File file = null;
		String path = null;

		imageTracker = new MediaTracker(this);
		try {
			path = "images/TrainControls2.png";
			file = new File(path);
			TrainControls = ImageIO.read(file);
			imageTracker.addImage(TrainControls, 100);

			path = "images/redlight.gif";
			file = new File(path);
			RedLight = ImageIO.read(file);
			imageTracker.addImage(RedLight, 100);

			path = "images/greenlight.gif";
			file = new File(path);
			GreenLight = ImageIO.read(file);
			imageTracker.addImage(GreenLight, 100);

			path = "images/blacktile.gif";
			file = new File(path);
			BlackTile = ImageIO.read(file);
			imageTracker.addImage(BlackTile, 100);

			path = "images/one.gif";
			file = new File(path);
			Zero = ImageIO.read(file);
			imageTracker.addImage(Zero, 100);

			path = "images/one.gif";
			file = new File(path);
			One = ImageIO.read(file);
			imageTracker.addImage(One, 100);

			path = "images/two.gif";
			file = new File(path);
			Two = ImageIO.read(file);
			imageTracker.addImage(Two, 100);

		} catch (Exception e) {
		}

	}

	// Set the train speed from outside here
	public void setSpeed(int Trainnumber, int s) {
		whichTrain(Trainnumber);
		setSpeed(s);
	}

	public void ResetAll() {
		// fill this in
		for (int i = 0; i < 4; i++) {
			switchOn[i] = false;
		}
		
		//mycc.reset();
		Train1.Reset();
		Train2.Reset();
		repaint();
	}

	// Flip a particular switch, depending on arguments passed.
	public void flipSwitch(boolean dir, int num) {
		if (!stopped) {
			// Set Switch (num straight)
			if (dir == true) {
				try {
					if (num == 1 && mycc.switchClear(2)) {
						Train1.SetSwitch2(false);
						Train2.SetSwitch2(false);
						switchOn[num] = false;
					}
					if (num == 3 && mycc.switchClear(4)) {
						Train1.SetSwitch4(false);
						Train2.SetSwitch4(false);
						switchOn[num] = false;
					}

				} catch (Exception e) {
				}
				
			}
			// Set Switch (num) curved
			else {
				try {
					if (num == 1 && mycc.switchClear(2)) {
						Train1.SetSwitch2(true);
						Train2.SetSwitch2(true);
						switchOn[num] = true;
					}
					if (num == 3 && mycc.switchClear(4)) {
						Train1.SetSwitch4(true);
						Train2.SetSwitch4(true);
						switchOn[num] = true;
					}
				} catch (Exception e) {
				}
				
			}
			repaint();
		}
	}

	public void paint(Graphics g) {
		if (stopped == false) {
			if (first_time == true) {
				g.setColor(new Color(0, 0, 0));
				g.drawString("Loading Graphics please wait...", 280, 80);
				try {
					imageTracker.waitForID(100);
				} catch (InterruptedException e) {
				}

				first_time = false;
			}

			try {
				imageTracker.waitForID(100);
			} catch (InterruptedException e) {
			}

			g.drawImage(TrainControls, 0, 0, this);

			ChangeSwitch(g);
			ChooseTrain(g);
	
			//ChangeSpeed(whichTrain, g);
			//ChangeSpeed(2, g, Train2Speed);
			DrawTrainSpeeds(g);
			
			if (AutoControlFlag) {
				g.drawString("Auto is ON", 610, 170);
			} else {
				g.drawString("Auto is OFF", 610, 170);
			}
		} else if (stopped == true) {
			g.drawImage(TrainControls, 0, 0, this);
		}
	} // end paint.

	// don't clear screen before drawing next image.
	public void update(Graphics g) {
		paint(g);
	} // End update

	public boolean mouseDown(Event evt, int x, int y) {
		// Switch #1 Turn
		if (((x > 44) && (x < 69)) && ((y > 75) && (y < 100))) {
			//flipSwitch(false, 0);
			return true;
		}
		// Switch #1 Straight
		else if (((x > 44) && (x < 69)) && ((y > 122) && (y < 145))) {
			//flipSwitch(true, 0);
			return true;
		}
		// Switch #2 Turn
		else if (((x > 84) && (x < 109)) && ((y > 75) && (y < 100))) {
			flipSwitch(false, 1);
			return true;
		}
		// Switch #2 Straight
		else if (((x > 84) && (x < 109)) && ((y > 122) && (y < 145))) {
			flipSwitch(true, 1);
			return true;
		}
		// Switch #3 Turn
		else if (((x > 124) && (x < 150)) && ((y > 75) && (y < 100))) {
			//flipSwitch(false, 2);
			return true;
		}
		// Switch #3 Straight
		else if (((x > 124) && (x < 150)) && ((y > 122) && (y < 145))) {
			//flipSwitch(true, 2);
			return true;
		}
		// Switch #4 Turn
		else if (((x > 164) && (x < 195)) && ((y > 75) && (y < 100))) {
			flipSwitch(false, 3);
			return true;
		}
		// Switch #4 Straight
		else if (((x > 164) && (x < 195)) && ((y > 122) && (y < 145))) {
			flipSwitch(true, 3);
			return true;
		}
		// Select Train #1
		else if (((x > 402) && (x < 442)) && ((y > 36) && (y < 60))) {
			whichTrain(1);
			return true;
		}
		// Select Train #2
		else if (((x > 402) && (x < 442)) && ((y > 69) && (y < 90))) {
			whichTrain(2);
			return true;
		}
		// Select Train #1
		else if (((x > 268) && (x < 289)) && ((y > 130) && (y < 155))) {
			whichTrain(1);
			return true;
		}
		// Select Train #2
		else if (((x > 300) && (x < 323)) && ((y > 130) && (y < 155))) {
			whichTrain(2);
			return true;
		}
		// Auto control trains
		else if (((x > 605) && (x < 700)) && ((y > 105) && (y < 155))) {
			// changeDir();
			AutoControl();
			return true;
		}
		// These set the current trains speed
		else if (((x > 354) && (x < 370)) && ((y > 210) && (y < 220))) {
			setSpeed(1);
			// System.out.println("speed is " + 1);
			return true;
		} else if (((x > 359) && (x < 375)) && ((y > 193) && (y < 209))) {
			setSpeed(2);
			return true;
		} else if (((x > 369) && (x < 385)) && ((y > 178) && (y < 192))) {
			setSpeed(3);
			return true;
		} else if (((x > 379) && (x < 395)) && ((y > 165) && (y < 177))) {
			setSpeed(4);
			return true;
		} else if (((x > 394) && (x < 410)) && ((y > 152) && (y < 165))) {
			setSpeed(5);
			return true;
		} else if (((x > 414) && (x < 430)) && ((y > 142) && (y < 160))) {
			setSpeed(6);
			return true;
		} else if (((x > 434) && (x < 450)) && ((y > 135) && (y < 155))) {
			setSpeed(7);
			return true;
		} else if (((x > 454) && (x < 470)) && ((y > 135) && (y < 155))) {
			setSpeed(8);
			return true;
		} else if (((x > 474) && (x < 490)) && ((y > 140) && (y < 160))) {
			setSpeed(9);
			return true;
		} else if (((x > 489) && (x < 510)) && ((y > 150) && (y < 170))) {
			setSpeed(10);
			return true;
		} else if (((x > 509) && (x < 525)) && ((y > 160) && (y < 180))) {
			setSpeed(11);
			return true;
		} else if (((x > 519) && (x < 535)) && ((y > 175) && (y < 191))) {
			setSpeed(12);
			return true;
		} else if (((x > 529) && (x < 545)) && ((y > 190) && (y < 208))) {
			setSpeed(13);
			return true;
		} else if (((x > 529) && (x < 545)) && ((y > 210) && (y < 225))) {
			setSpeed(14);
			// System.out.println("speed is " + 14);
			return true;
		} else if (((x > 444) && (x < 460)) && ((y > 210) && (y < 225))) {
			stopTrain();
			return true;
		}
		return false;
	} // End mouseDown

	// ------------------------------------------------------
	// private methods
	// ------------------------------------------------------
	private void AutoControl() {
		AutoControlFlag = !AutoControlFlag;
	//	Train1.SetAutoControlFlag(AutoControlFlag);
	//	Train2.SetAutoControlFlag(AutoControlFlag);
		repaint();
		//JOptionPane.showMessageDialog(null, "Auto Control is "
		//		+ AutoControlFlag);

	}

	// Paint the screen with the current status of all the switches
	private void ChangeSwitch(Graphics g) {
		if (switchOn[0] == true) {
			g.drawImage(RedLight, 54, 50, this);
			g.drawImage(BlackTile, 53, 155, this);
		} else {
			g.drawImage(BlackTile, 54, 50, this);
			g.drawImage(GreenLight, 53, 155, this);
		}

		if (switchOn[1] == true) {
			g.drawImage(RedLight, 93, 50, this);
			g.drawImage(BlackTile, 92, 155, this);
		} else {
			g.drawImage(BlackTile, 92, 50, this);
			g.drawImage(GreenLight, 93, 155, this);
		}

		if (switchOn[2] == true) {
			g.drawImage(RedLight, 133, 50, this);
			g.drawImage(BlackTile, 132, 155, this);
		} else {
			g.drawImage(BlackTile, 132, 50, this);
			g.drawImage(GreenLight, 133, 155, this);
		}

		if (switchOn[3] == true) {
			g.drawImage(RedLight, 173, 50, this);
			g.drawImage(BlackTile, 172, 155, this);
		} else {
			g.drawImage(BlackTile, 172, 50, this);
			g.drawImage(GreenLight, 173, 155, this);
		}
	} // End FlipSwitch.

	// Paint the screen with the currently selected train
	private void ChooseTrain(Graphics g) {
		if (whichTrain == 1) {
			g.drawImage(One, 305, 27, this);
		} else if (whichTrain == 2) {

			g.drawImage(Two, 305, 27, this);
		}
	} // End ChooseTrain

	// Paint the screen indicator for the current trains speed
	private void ChangeSpeed(int TrainNum, Graphics g) {
		int speed=0;
		if (TrainNum == 1) {
			g.setColor(Color.yellow);
			speed = Train1Speed;
		} else {
			g.setColor(Color.pink);
			speed = Train2Speed;
		}

		if (speed == 1) {
			g.drawLine(364, 217, 454, 217);
			g.drawLine(364, 218, 454, 218);
		} else if (speed == 2) {
			g.drawLine(369, 200, 454, 217);
			g.drawLine(369, 201, 454, 218);
		} else if (speed == 3) {
			g.drawLine(378, 185, 454, 217);
			g.drawLine(378, 186, 454, 218);
		} else if (speed == 4) {
			g.drawLine(388, 170, 454, 217);
			g.drawLine(388, 171, 454, 218);
		} else if (speed == 5) {
			g.drawLine(404, 160, 454, 217);
			g.drawLine(404, 161, 454, 218);
		} else if (speed == 6) {
			g.drawLine(422, 155, 454, 217);
			g.drawLine(422, 156, 454, 218);
		} else if (speed == 7) {
			g.drawLine(441, 150, 454, 217);
			g.drawLine(441, 151, 454, 218);
		} else if (speed == 8) {
			g.drawLine(460, 152, 454, 217);
			g.drawLine(460, 153, 454, 218);
		} else if (speed == 9) {
			g.drawLine(480, 154, 454, 217);
			g.drawLine(480, 155, 454, 218);
		} else if (speed == 10) {
			g.drawLine(496, 162, 454, 217);
			g.drawLine(496, 163, 454, 218);
		} else if (speed == 11) {
			g.drawLine(512, 174, 454, 217);
			g.drawLine(512, 175, 454, 218);
		} else if (speed == 12) {
			g.drawLine(525, 187, 454, 217);
			g.drawLine(525, 188, 454, 218);
		} else if (speed == 13) {
			g.drawLine(529, 200, 454, 217);
			g.drawLine(529, 201, 454, 218);
		} else if (speed == 14) {
			g.drawLine(534, 217, 454, 217);
			g.drawLine(534, 218, 454, 218);
		}
	} // End ChangeSpeed

	// Set the current trains speed
		private void setSpeed(int s) {
			if (!stopped) {
				//System.out.println("Setting speed");
				if (whichTrain == 1) {
				
					try {
						Train1.SetDesiredSpeed(s);
						Train1Speed = s;
					} catch (Exception e) {
					}

				} else {
					
					try {
						Train2Speed = s;
						Train2.SetDesiredSpeed(s);
					} catch (Exception e) {
					}

				}
				repaint();
			}
		}


	// Set the current train and repaint the controls
	private void whichTrain(int which) {
		whichTrain = which;
		repaint();
	}

	// Paint the screen indicator for the current trains speed
			private void DrawTrainSpeeds(Graphics g) {
				int speed = 0;

				g.setColor(Color.yellow);
				speed = Train1.GetDesiredSpeed();

				if (speed == 1) {
					g.drawLine(364, 217, 454, 217);
					g.drawLine(364, 218, 454, 218);
				} else if (speed == 2) {
					g.drawLine(369, 200, 454, 217);
					g.drawLine(369, 201, 454, 218);
				} else if (speed == 3) {
					g.drawLine(378, 185, 454, 217);
					g.drawLine(378, 186, 454, 218);
				} else if (speed == 4) {
					g.drawLine(388, 170, 454, 217);
					g.drawLine(388, 171, 454, 218);
				} else if (speed == 5) {
					g.drawLine(404, 160, 454, 217);
					g.drawLine(404, 161, 454, 218);
				} else if (speed == 6) {
					g.drawLine(422, 155, 454, 217);
					g.drawLine(422, 156, 454, 218);
				} else if (speed == 7) {
					g.drawLine(441, 150, 454, 217);
					g.drawLine(441, 151, 454, 218);
				} else if (speed == 8) {
					g.drawLine(460, 152, 454, 217);
					g.drawLine(460, 153, 454, 218);
				} else if (speed == 9) {
					g.drawLine(480, 154, 454, 217);
					g.drawLine(480, 155, 454, 218);
				} else if (speed == 10) {
					g.drawLine(496, 162, 454, 217);
					g.drawLine(496, 163, 454, 218);
				} else if (speed == 11) {
					g.drawLine(512, 174, 454, 217);
					g.drawLine(512, 175, 454, 218);
				} else if (speed == 12) {
					g.drawLine(525, 187, 454, 217);
					g.drawLine(525, 188, 454, 218);
				} else if (speed == 13) {
					g.drawLine(529, 200, 454, 217);
					g.drawLine(529, 201, 454, 218);
				} else if (speed == 14) {
					g.drawLine(534, 217, 454, 217);
					g.drawLine(534, 218, 454, 218);
				}

				g.setColor(Color.pink);
				speed = Train2.GetDesiredSpeed ();
				
				if (speed == 1) {
					g.drawLine(364, 217, 454, 217);
					g.drawLine(364, 218, 454, 218);
				} else if (speed == 2) {
					g.drawLine(369, 200, 454, 217);
					g.drawLine(369, 201, 454, 218);
				} else if (speed == 3) {
					g.drawLine(378, 185, 454, 217);
					g.drawLine(378, 186, 454, 218);
				} else if (speed == 4) {
					g.drawLine(388, 170, 454, 217);
					g.drawLine(388, 171, 454, 218);
				} else if (speed == 5) {
					g.drawLine(404, 160, 454, 217);
					g.drawLine(404, 161, 454, 218);
				} else if (speed == 6) {
					g.drawLine(422, 155, 454, 217);
					g.drawLine(422, 156, 454, 218);
				} else if (speed == 7) {
					g.drawLine(441, 150, 454, 217);
					g.drawLine(441, 151, 454, 218);
				} else if (speed == 8) {
					g.drawLine(460, 152, 454, 217);
					g.drawLine(460, 153, 454, 218);
				} else if (speed == 9) {
					g.drawLine(480, 154, 454, 217);
					g.drawLine(480, 155, 454, 218);
				} else if (speed == 10) {
					g.drawLine(496, 162, 454, 217);
					g.drawLine(496, 163, 454, 218);
				} else if (speed == 11) {
					g.drawLine(512, 174, 454, 217);
					g.drawLine(512, 175, 454, 218);
				} else if (speed == 12) {
					g.drawLine(525, 187, 454, 217);
					g.drawLine(525, 188, 454, 218);
				} else if (speed == 13) {
					g.drawLine(529, 200, 454, 217);
					g.drawLine(529, 201, 454, 218);
				} else if (speed == 14) {
					g.drawLine(534, 217, 454, 217);
					g.drawLine(534, 218, 454, 218);
				}
			} // End ChangeSpeed

			
	// Stop the Current Train
	private void stopTrain() {
		if (!stopped) {
			if (whichTrain == 1) {
				Train1Speed = 0;
				Train1.StopTrain();
			} else {
				Train2Speed = 0;
				Train2.StopTrain();
			}
			repaint();
		}
	}

	// Start or Stop the train system, depending on the boolean
	// value passed.
	private void systemState(boolean state) {
		if (state == false) {
			try {
				// output.println("trainsStop");
			} catch (Exception e) {
			}
			stopped = true;
		} else {
			try {
				// output.println("trainsGo");
			} catch (Exception e) {
			}
			stopped = false;
		}
		repaint();
	}

}
