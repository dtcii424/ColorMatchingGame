import java.awt.*;
import java.util.*;
import java.lang.Thread;

/*
 * This program implements a version of the Concentration game.
 * In this version, the game has two rows.  The goal of the user
 * is to find squares with matching colors on the two rows.
 * In each turn, the user selects a square from the top row,
 * then a square on the bottom row.  The colors of the squares
 * are revealed.  However, if the colors do not match, the
 * squares return to a gray color.
*/

public class Concentration {
  public static void main(String[] args) {
    System.out.println("Color Matching Game by Derek Cox");
    // DrawingPanel2 has additional methods for keeping track of the mouse.
    while(true) {
	    DrawingPanel2 panel = new DrawingPanel2(700,300);
	    Graphics g = panel.getGraphics();
	    int match = 0;
	    
	    // Draw 6 rectangles: 2 rows and 3 columns.
	    for (int r = 0; r < 2; r++) {
	      for (int c = 0; c < 6; c++) {
	        g.drawRect(c * 100 + 50, r * 100 + 50, 100, 100);
	      }
	    }
	    
	    // Fill the rectangles with light gray.
	    g.setColor(Color.LIGHT_GRAY);
	    for (int r = 0; r < 2; r++) {
	      for (int c = 0; c < 6; c++) {
	        g.fillRect(c * 100 + 51, r * 100 + 51, 99, 99);
	      }
	    }
	    
	    // Create color arrays.
	    Color[] colors0 = {Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA, Color.YELLOW};
	    Color[] colors1 = {Color.CYAN, Color.MAGENTA, Color.YELLOW, Color.RED, Color.GREEN, Color.BLUE};
	
	    // Randomize the order of the colors in the arrays.
	    // Leave randomizeColors commented out for Lab 9.
	    // Uncomment these lines and implement randomizeColors 
	    // for your final project.
	    
	     randomizeColors(colors0);
	     randomizeColors(colors1);
	   
	     boolean[] toprow = new boolean[6];
	     boolean[] botrow = new boolean[6];
	    
	    // Leave as an infinite loop for Lab 9.
	    // In the final project, the loop  should end when all the
	    // squares have been revealed.
	    while (match < 6) {
	      // Wait for a click on row 0, and then save the column.
	      // This needs to be changed to ensure that the color of
	      // the square on row 0 and column col0 is currently hidden.
	      int row = getClickRow(panel);
	      while (row != 0) {
	        row = getClickRow(panel);
	      }
	      int col0 = getClickColumn(panel);
	      System.out.print(col0);
	      
	      // Reveal the color on row 0 and column col1.
	      // Need code to remember that this square has been revealed.
	      Color color0 = colors0[col0];
	      if(toprow[col0] == false){
	      g.setColor(color0);
	      g.fillRect(col0 * 100 + 51, 51, 99, 99);
	      }
	      
	      // Wait for a click on row 1, and then save the column.
	      // This needs to be changed to ensure that the color of
	      // the square on row 1 and column col1 is currently hidden.
	      row = getClickRow(panel);
	      while (row != 1) {
	        row = getClickRow(panel);
	      }
	      int col1 = getClickColumn(panel);
	      System.out.print(col1);
	      
	      // Reveal the color on row 1 and column col1.
	      // Need code to remember that this square has been revealed.
	      Color color1 = colors1[col1];
	      if((toprow[col0] == false) && (botrow[col1] == false)){
	      g.setColor(color1);
	      g.fillRect(col1 * 100 + 51, 100 + 51, 99, 99);
	      }
	      
	      if(color0.equals(color1)){
	        if((toprow[col0] == false) && (botrow[col1] == false)){
	        toprow[col0] = true;
	        botrow[col1] = true;
	        match++;
	        }
	      }
	      
	      // Pause a second before (maybe) changing back to light gray.
	      panel.sleep(1000);
	
	      // Hide the colors if they are not the same.
	      if (! color0.equals(color1)) {
	        if((toprow[col0] == false) && (botrow[col1] == false)){
	        g.setColor(Color.LIGHT_GRAY);
	        g.fillRect(col0 * 100 + 51, 51, 99, 99);
	        g.fillRect(col1 * 100 + 51, 100 + 51, 99, 99);
	        // Need code to remember that the two squares are hidden.
	        }
	        else if ((toprow[col0] == false) && (botrow[col1] == true)){
	        g.setColor(Color.LIGHT_GRAY);
	        g.fillRect(col0 * 100 + 51, 51, 99, 99);
	        }
	      }
	      
	      if(match == 6){
	        g.setFont(new Font("SansSerif", Font.BOLD, 50));
	        g.setColor(Color.BLACK);
	        g.drawString("You win!", 250, 150);
	        try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        }
	    }
      
      }
    }
  
  // This method should randomize the order of the colors.
  public static void randomizeColors(Color[] colors) {
    //   repeat several times
    //   generate two random ints between 0 and colors.length - 1
    //   call the swap method with the color array and the two ints
    Random randGen = new Random();
    int i;
    
    for(i=0; i<= colors.length - 1; i++){
    int r1 = randGen.nextInt(colors.length - 1);
    swap(colors, r1, i);
    }
    
  }

  // This is like the swap method in Chapter 7 on pp. 456-457,
  // but modified for Color arrays.
  public static void swap(Color[] colors, int i, int j) {
    Color temp = colors[i];
    colors[i] = colors[j];
    colors[j] = temp;
  }
  
  // Return 0 if the user clicks on row 0.
  // Return 1 if the user clicks on row 1.
  // Otherwise, return -1.
  public static int getClickRow(DrawingPanel2 panel) {
    int x = panel.getClickX();
    int y = panel.getClickY();
    if (x > 50 && x < 650 && y > 50 && y < 150) {
      return 0;
    } else if (x > 50 && x < 650 && y > 150 && y < 250) {
      return 1;
    } else {
      return -1;
    }
  }
  
  // Return c if the user clicks on column c.
  // Otherwise, return -1.
  public static int getClickColumn(DrawingPanel2 panel) {
    int x = panel.getClickX();
    int y = panel.getClickY();
    if (x > 50 && x < 150 && y > 50 && y < 250) {
      return 0;
    } else if (x > 150 && x < 250 && y > 50 && y < 250) {
      return 1;
    } else if (x > 250 && x < 350 && y > 50 && y < 250) {
      return 2;
    } else if (x > 350 && x < 450 && y > 50 && y < 250) {
      return 3;
    } else if (x > 450 && x < 550 && y > 50 && y < 250) {
      return 4;
    } else if (x > 550 && x < 650 && y > 50 && y < 250) {
      return 5;  
    } else {
      return -1;
    }
  }
}
