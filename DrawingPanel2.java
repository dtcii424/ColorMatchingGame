/*
 Stuart Reges and Marty Stepp
 February 24, 2007
 Changes by Tom Bylander in 2010 (no anti-alias, repaint on sleep)
 Changes by Tom Bylander in 2012 (track mouse clicks and movement)
 
 The DrawingPanel class provides a simple interface for drawing persistent
 images using a Graphics object.  An internal BufferedImage object is used
 to keep track of what has been drawn.  A client of the class simply
 constructs a DrawingPanel of a particular size and then draws on it with
 the Graphics object, setting the background color if they so choose.
 
 To ensure that the image is always displayed, a timer calls repaint at
 regular intervals.
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;

public class DrawingPanel2 implements ActionListener {
  private static final int DELAY = 100;  // delay between repaints in millis
  private static final boolean PRETTY = false;  // true to anti-alias
  
  private int width, height;    // dimensions of window frame
  private JFrame frame;         // overall window frame
  private JPanel panel;         // overall drawing surface
  private BufferedImage image;  // remembers drawing commands
  private Graphics2D g2;        // graphics context for painting
  private JLabel statusBar;     // status bar showing mouse position
  private volatile MouseEvent click;     // stores the last mouse click
  private volatile boolean pressed;      // true if the mouse is pressed
  private volatile MouseEvent move;      // stores the position of the mouse
  
  // construct a drawing panel of given width and height enclosed in a window
  public DrawingPanel2(int width, int height) {
    this.setWidth(width);
    this.setHeight(height);
    image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    
    statusBar = new JLabel(" ");
    statusBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    
    panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
    panel.setBackground(Color.WHITE);
    panel.setPreferredSize(new Dimension(width, height));
    panel.add(new JLabel(new ImageIcon(image)));
    
    click = null;
    move = null;
    pressed = false;
    
    // listen to mouse movement
    MouseInputAdapter listener = new MouseInputAdapter() {
      public void mouseMoved(MouseEvent e) {
        pressed = false;
        move = e;
        statusBar.setText("moved (" + e.getX() + ", " + e.getY() + ")");
      }
  
      public void mousePressed(MouseEvent e) {
        pressed = true;
        move = e;
        statusBar.setText("pressed (" + e.getX() + ", " + e.getY() + ")");
      }
      
      public void mouseDragged(MouseEvent e) {
        pressed = true;
        move = e;
        statusBar.setText("dragged (" + e.getX() + ", " + e.getY() + ")");
      }
      
      public void mouseReleased(MouseEvent e) {
        pressed = false;
        click = e;
        statusBar.setText("released (" + e.getX() + ", " + e.getY() + ")");
      }
      
    };
    panel.addMouseListener(listener);
    panel.addMouseMotionListener(listener);
    
    g2 = (Graphics2D)image.getGraphics();
    g2.setColor(Color.BLACK);
    if (PRETTY) {
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setStroke(new BasicStroke(1.1f));
    }
    
    frame = new JFrame("Drawing Panel");
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(panel);
    frame.getContentPane().add(statusBar, "South");
    frame.pack();
    frame.setVisible(true);
    toFront();
    
    // repaint timer so that the screen will update
    new Timer(DELAY, this).start();
  }
  
  // used for an internal timer that keeps repainting
  public void actionPerformed(ActionEvent e) {
    panel.repaint();
  }
  
  // obtain the Graphics object to draw on the panel
  public Graphics2D getGraphics() {
    return g2;
  }
  
  // set the background color of the drawing panel
  public void setBackground(Color c) {
    panel.setBackground(c);
  }
  
  // show or hide the drawing panel on the screen
  public void setVisible(boolean visible) {
    frame.setVisible(visible);
  }
  
  // makes the program pause for the given amount of time,
  // allowing for animation
  public void sleep(int millis) {
    panel.repaint();
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {}
  }
  
  // close the drawing panel
  public void close() {
    frame.dispose();
  }
  
  // makes drawing panel become the frontmost window on the screen
  public void toFront() {
    frame.toFront();
  }
  
  // return the X position of the mouse or -1
  public int getMouseX() {
    if (move == null) {
      return -1;
    } else {
      return move.getX();
    }
  }
  
  // return the Y position of the mouse or -1
  public int getMouseY() {
    if (move == null) {
      return -1;
    } else {
      return move.getY();
    }
  }
  
  // return the X position of the last click or -1
  public int getClickX() {
    if (click == null) {
      return -1;
    } else {
      return click.getX();
    }
  }
  
  // return the Y position of the last click or -1
  public int getClickY() {
    if (click == null) {
      return -1;
    } else {
      return click.getY();
    }
  }
  
  // return true if a mouse button is pressed
  public boolean mousePressed() {
    return pressed;
  }

public int getWidth() {
	return width;
}

public void setWidth(int width) {
	this.width = width;
}

public int getHeight() {
	return height;
}

public void setHeight(int height) {
	this.height = height;
}
  
}
