
//----------------------------------------------------
// DON NOT EDIT
//----------------------------------------------------
import java.awt.*;
//Dr. Roger Webster

public class MyCanvas extends Canvas {
    //-------------------------------------------------------------
// customers are moved around graphically, please dont edit
 //--------------------------------------------------------------
	private WaitRoom wr;
    private static final int MAXIMUM = 40;
    private boolean inChair = false;
    private boolean hasHair = true;
    private int curCust = 0;
    private int temp;
    // x, y coordinates of customers in waiting room
    private int[] x;
    private int[] y;
    private int withBarber = 0;
    private int i;
    private Image offScrImg;
    private Image buffer;
    private boolean onetimeonly = true;

    public MyCanvas(WaitRoom wrin) {
        x = new int[MAXIMUM + 1];
        y = new int[MAXIMUM + 1];// create control buttons
        wr = wrin;
    }

    public boolean gethasHair() {
        return hasHair;
    }

    public void sethasHair() {
        hasHair = true;
    }
    public boolean getinChair() {
        return inChair;
    }

    public void setinChair() {
        inChair = true;
    }
    public void incrementCurrentCustomerNumber() {
        curCust++;
    }

    public int getCurrentCustomerNumber() {
        return curCust;
    }
   public synchronized int CustomerNoWithBarber() {

        return (this.curCust - (wr.custCount()));
    }

    public void setwithBarberNumber(int w) {
        withBarber = w;
    }

	public void setwithBarberNumber() {
        withBarber = CustomerNoWithBarber();
    }
    //-------------------------------------------------------------------
    // makes people move around in the waiting room graphically
    //-------------------------------------------------------------------
    private void advancePeople() {
        temp = 0;
        if (wr.custCount() > 0) {
            i = (curCust % 40) - wr.custCount() + 1;
            if (i < 0) {
                i += 40;
            }
            temp = 0;
            while (temp < wr.custCount()) {

                i = i % 40;
                if ((temp / 8) % 2 == 0) {
                    x[i] = 400 - 40 * (temp % 8);
                } else {
                    x[i] = 120 + 40 * (temp % 8);
                }
                y[i] = 210 + 40 * (temp / 8);
                temp++;
                i++;
            }
        }
        this.paintComponent(this.getGraphics());
    }
    //------------------------------------------------------
    // makes a person enter the waiting room graphically
    //------------------------------------------------------
    public void enterShop() {
        int finalx;
        int finaly;
        incrementCurrentCustomerNumber();
        
        int person = (curCust % 40);

        temp = wr.custCount() - 1;

        if ((temp / 8) % 2 == 0) {
            finalx = 400 - 40 * (temp % 8);
        } else {
            finalx = 120 + 40 * (temp % 8);
        }

        finaly = 210 + 40 * (temp / 8);

        x[person] = 0;
        y[person] = 370;
        this.paintComponent(this.getGraphics());

        for (int k = 0; k <= finalx; k += 2) {
            x[person] = k;
            this.paintComponent(this.getGraphics());
        }

        for (int k = 370; k >= finaly; k -= 2) {
            y[person] = k;
            this.paintComponent(this.getGraphics());
        }
      

    }

    //-------------------------------------------------------------------
    // makes a person move to the chair from the waiting room graphically
    //-------------------------------------------------------------------
    public void moveToChair() {
    	  this.setwithBarberNumber();
    	  inChair = true;
        y[MAXIMUM] = 210;
        for (int k = 400; k <= 535; k += 2) {
            x[MAXIMUM] = k;

            this.paintComponent(this.getGraphics());
        }
        this.advancePeople();
      
    }

    //-------------------------------------------------------------------
    // makes a person move from the chair to the street graphically
    //-------------------------------------------------------------------
    public void showCustomerLeave() {
        hasHair = false;
        y[MAXIMUM] = 210;

        // move off barbers chair
        for (int k = 535; k >= 450; k -= 2) {
            x[MAXIMUM] = k;
            this.paintComponent(this.getGraphics());
        }

        // move down to door
        for (int k = 210; k <= 370; k += 2) {
            y[MAXIMUM] = k;
            this.paintComponent(this.getGraphics());
        }

        // move out door
        for (int k = 450; k <= 650; k += 2) {
            x[MAXIMUM] = k;
            this.paintComponent(this.getGraphics());
        }
        inChair = false;
        hasHair = true;
        this.paintComponent(this.getGraphics());

    }
    ///////////////////////////////////////
    // update method - for double buffering
    ///////////////////////////////////////
    // off screen image to do initial painting

    public void myupdate(Graphics g) {
        // if off screen image is not created, create it
        if (offScrImg == null) {
            offScrImg = createImage(this.getWidth(), this.getHeight());
        }

        Graphics og = offScrImg.getGraphics();
        paint(og);	// paint on the off screen graphics object

        g.drawImage(offScrImg, 0, 0, this);	// draw completed image to applet
        og.dispose();

    } // end update

    protected synchronized void paintComponent(Graphics gr) {

        // create an off-screen buffer
        if (onetimeonly) {
            int w = getWidth();
            int h = getHeight();
            buffer = createImage(w, h);
            onetimeonly = false;
        }

        // get buffer's graphics context
        Graphics2D g = (Graphics2D) buffer.getGraphics();

        // clear background
        ///////////////////
        g.setColor(new Color(240, 240, 240));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        g.setColor(new Color(0, 0, 0));	// set color to black

        g.drawString("Waiting Room Capacity:", 270, 150);
        g.drawString("" + 40, 410, 150);


        // draw barbershop
        g.drawRect(90, 175, 350, 300);
        g.drawRect(440, 175, 175, 300);

        g.drawLine(525, 250, 575, 250);
        g.drawLine(575, 250, 583, 205);
        g.drawLine(535, 250, 525, 290);
        g.drawLine(565, 250, 575, 290);

        g.drawLine(90, 400, 140, 400);
        g.drawLine(440, 200, 490, 200);
        g.drawLine(615, 400, 665, 400);

        g.setColor(new Color(240, 240, 240));
        g.drawLine(90, 350, 90, 400);
        g.drawLine(440, 200, 440, 250);
        g.drawLine(615, 350, 615, 400);

        // draw customers
        if (wr.custCount() > 0) {
            i = (curCust % 40) - wr.custCount() + 1;
            if (i < 0) {
                i += 40;
            }
            temp = 0;
            while (temp < wr.custCount()) {
                temp++;
                i = i % 40;
                g.setColor(new Color(200, 200, 250));
                g.fillOval(x[i], y[i], 20, 20);

                g.setColor(new Color(0, 0, 0));
                if ((temp + withBarber) < 10) {
                    g.drawString("" + (withBarber + temp), x[i] + 7, y[i] + 15);
                } else {
                    g.drawString("" + (withBarber + temp), x[i] + 3, y[i] + 15);
                }

                g.drawLine(x[i] + 7, y[i] + 2, x[i] + 5, y[i] - 5);
                g.drawLine(x[i] + 10, y[i], x[i] + 10, y[i] - 6);
                g.drawLine(x[i] + 13, y[i] + 2, x[i] + 15, y[i] - 5);

                i++;
            }
        }

        if (inChair) {
            g.setColor(new Color(200, 200, 250));
            g.fillOval(x[MAXIMUM], y[MAXIMUM], 20, 20);

            g.setColor(new Color(0, 0, 0));
            if (withBarber < 10) {
                g.drawString("" + withBarber, x[MAXIMUM] + 7, y[MAXIMUM] + 15);
            } else {
                g.drawString("" + withBarber, x[MAXIMUM] + 3, y[MAXIMUM] + 15);
            }

            if (hasHair) {
                g.drawLine(x[MAXIMUM] + 7, y[MAXIMUM] + 2, x[MAXIMUM] + 5, y[MAXIMUM] - 5);
                g.drawLine(x[MAXIMUM] + 10, y[MAXIMUM], x[MAXIMUM] + 10, y[MAXIMUM] - 6);
                g.drawLine(x[MAXIMUM] + 13, y[MAXIMUM] + 2, x[MAXIMUM] + 15, y[MAXIMUM] - 5);
            }
        }
        gr.drawImage(buffer, 0, 0, this);
    }

    public synchronized void paint(Graphics g) {

        // clear background
        ///////////////////
        g.setColor(new Color(240, 240, 240));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        g.setColor(new Color(0, 0, 0));	// set color to black

        // draw barbershop
        g.drawRect(90, 175, 350, 300);
        g.drawRect(440, 175, 175, 300);

        g.drawLine(525, 250, 575, 250);
        g.drawLine(575, 250, 583, 205);
        g.drawLine(535, 250, 525, 290);
        g.drawLine(565, 250, 575, 290);

        g.drawLine(90, 400, 140, 400);
        g.drawLine(440, 200, 490, 200);
        g.drawLine(615, 400, 665, 400);

        g.setColor(new Color(240, 240, 240));
        g.drawLine(90, 350, 90, 400);
        g.drawLine(440, 200, 440, 250);
        g.drawLine(615, 350, 615, 400);

        // draw customers
        if (wr.custCount() > 0) {
            i = (curCust % 40) - wr.custCount() + 1;
            if (i < 0) {
                i += 40;
            }
            temp = 0;
            while (temp < wr.custCount()) {
                temp++;
                i = i % 40;
                g.setColor(new Color(200, 200, 250));
                g.fillOval(x[i], y[i], 20, 20);

                g.setColor(new Color(0, 0, 0));
                if ((temp + withBarber) < 10) {
                    g.drawString("" + (withBarber + temp), x[i] + 7, y[i] + 15);
                } else {
                    g.drawString("" + (withBarber + temp), x[i] + 3, y[i] + 15);
                }

                g.drawLine(x[i] + 7, y[i] + 2, x[i] + 5, y[i] - 5);
                g.drawLine(x[i] + 10, y[i], x[i] + 10, y[i] - 6);
                g.drawLine(x[i] + 13, y[i] + 2, x[i] + 15, y[i] - 5);

                i++;
            }
        }

        if (inChair) {
            g.setColor(new Color(200, 200, 250));
            g.fillOval(x[MAXIMUM], y[MAXIMUM], 20, 20);

            g.setColor(new Color(0, 0, 0));
            if (withBarber < 10) {
                g.drawString("" + withBarber, x[MAXIMUM] + 7, y[MAXIMUM] + 15);
            } else {
                g.drawString("" + withBarber, x[MAXIMUM] + 3, y[MAXIMUM] + 15);
            }

            if (hasHair) {
                g.drawLine(x[MAXIMUM] + 7, y[MAXIMUM] + 2, x[MAXIMUM] + 5, y[MAXIMUM] - 5);
                g.drawLine(x[MAXIMUM] + 10, y[MAXIMUM], x[MAXIMUM] + 10, y[MAXIMUM] - 6);
                g.drawLine(x[MAXIMUM] + 13, y[MAXIMUM] + 2, x[MAXIMUM] + 15, y[MAXIMUM] - 5);
            }
        }
    }
}


