
// importing awt class  
import java.awt.*;
import java.io.*;
import java.lang.Math;

// class to construct a frame and containing main method    
public class fractal {
  // class constructor
  public static double width = 1200;
  public static double height = 1200;

  public fractal() {

    // creating a frame
    Frame f = new Frame("Canvas Example");
    // adding canvas to frame
    f.add(new MyCanvas());

    // setting layout, size and visibility of frame
    f.setLayout(null);
    f.setSize((int) width, (int) height);
    f.setVisible(true);
  }

  // main method
  public static void main(String args[]) {
    new fractal();
  }
}

// class which inherits the Canvas class
// to create Canvas
class MyCanvas extends Canvas {
  // class constructor
  public MyCanvas() {
    setBackground(Color.BLACK);
    setSize((int) fractal.width, (int) fractal.height);
  }

  // paint() method to draw inside the canvas
  public void paint(Graphics g) {
    // Do the interaction for eatch pixel in the Canvas
    for (double i = 0; i < (int) fractal.width; i++) {
      for (double j = 0; j < (int) fractal.height; j++) {
        double n, x, y, x2, y2;

        // Convert the canvas width and height in a number bitwen -2 and 2 making the
        // image a amplified graph
        x = (i / fractal.width) * 4 - 2;
        y = (j / fractal.height) * 4 - 2;
        System.out.printf("%.2f , %d, %d", x, (int) i, (int) j);

        // Saves the value of complex number for eatch interaction to be used in the
        // while loop
        double cx = x, cy = y;

        // Calculate a value for eatch pixel in the screen bitwen 0 and 100 based in the
        // Mandel brot formula,
        for (n = 0; (int) n < 100; n++) {

          // Calculate the real number value based in the past values for the x and y
          x2 = (x * x) - (y * y);
          y2 = 2 * (x * y);

          // make a complex number based in the sum of a real nuber and a complex one
          x = x2 + cx;
          y = y2 + cy;

          if (Math.abs(y + x) > 400) {
            break;
          }
        }

        // color the correct pixels in white
        System.out.println(n);
        g.setColor(Color.BLACK);
        if ((int) n < 50) {
          g.setColor(Color.white);
        }
        g.fillOval((int) i, (int) j, 2, 2);
      }
    }
  }
}
