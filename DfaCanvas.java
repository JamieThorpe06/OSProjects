import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**************************************************************************
 * This class displays the DFA implemented in realtimetrains and displays
 * the current state of the two train threads.
 * 
 * @author Kimberlyn Broskie
 *************************************************************************/
public class DfaCanvas extends Canvas
{
	private static final long serialVersionUID = 1L;
	private Image dfaImage;
	private Color trainColor;
	private boolean firstTime = true;
	private int x;
	private int y;
	private int diameter = 44;
	private int train1Position = 0;
	private int train2Position = 0;
	
	DfaCanvas ()
	{
		super ();
		setSize(new Dimension (450, 450));
		
		try
		{
			dfaImage = ImageIO.read(new File("RTTRAINSFSM.jpg"));		
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(null,
					"Images not found exception is " + e.toString());
		}
	}
	
	public void setTrainColor (int trainNumber)
	{
		if (trainNumber == 0)
		{
			trainColor = Color.red;	
		}
		else if (trainNumber == 1)
		{
			trainColor = Color.yellow;
		}
		else
		{
			trainColor = Color.magenta;
		}
	}
	
	public void paint(Graphics g) 
	{
		if (firstTime)
		{
			g.setColor(new Color(0, 0, 0));
			g.drawImage(dfaImage, 0, 0, this);
			g.setColor (Color.RED);
			setNodeCoordinates (0);
			g.fillOval (x, y, diameter, diameter);
			firstTime = false;
		}
		else 
		{
			g.setColor(new Color(0, 0, 0));
			g.drawImage(dfaImage, 0, 0, this);
			
			if (train1Position == train2Position)
			{
				g.setColor (Color.RED);
				setNodeCoordinates (train1Position);
				g.fillOval (x, y, diameter, diameter);				
			}
			else
			{
				g.setColor (Color.yellow);
				setNodeCoordinates (train1Position);
				g.fillOval (x, y, diameter, diameter);
				
				g.setColor (Color.magenta);
				setNodeCoordinates (train2Position);
				g.fillOval (x, y, diameter, diameter);
			}			
		}
	} 

	/*
	 * Update the current node states.
	 */
	public void update(int train1, int train2) 
	{
		train1Position = train1;
		train2Position = train2;
		repaint ();
	} 
	
	/*
	* Set the x,y position corresponding to the node for the position.
	*/
	private void setNodeCoordinates (int trainPosition)
	{
		switch (trainPosition)
		{
			case 0:
			{	// S node
				x = 312;
				y = 191;
				break;
			}	
			case 1:
			{	
				// I1
				x = 272;
				y = 286;
				break;
			}	
			case 2:
			{	
				// I2
				x = 165;
				y = 266;
				break;
			}	
			case 3:
			{	
				// I3
				x = 181;
				y = 170;
				break;
			}	
			case 4:
			{	
				// I4
				x = 300;
				y = 87;
				break;
			}	
			
			case 5:
			{
				// I5
				x = 442;
				y = 136;
				break;
			}
			case 6:
			{	
				// I6
				x = 437;
				y = 249;
				break;
			}	
			case 7:
			{	
				// O1
				x = 309;
				y = 371;
				break;
			}	
			case 8:
			{	
				// O2
				x = 34;
				y = 291;
				break;
			}	
			case 9:
			{	
				// O3
				x = 113;
				y = 54;
				break;
			}	
			case 10:
			{	
				// O4
				x = 383;
				y = 18;
				break;
			}	
			case 11:
			{	
				// O5
				x = 560;
				y = 147;
				break;
			}
			case 12:
			{	
				// O6
				x = 558;
				y = 312;
				break;
			}	
		}
	}
}
