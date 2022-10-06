
/*
VisAD Tutorial
Copyright (C) 2000 Ugo Taddei
*/

// Import needed classes

import visad.*;
import visad.util.*;
import visad.java3d.DisplayImplJ3D;
import java.rmi.RemoteException;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * VisAD Tutorial example 4_14
 * Variation of 4_09
 * Change display layout: axes colors and etc
 */

public class visad5_3D_dots {

  // Declare variables
  // The domain quantities longitude and latitude
  // and the dependent quantity rgbVal

  private RealType red, green, blue;
  private RealType rgbVal;

  // Tuple to pack longitude and latitude together

  private RealTupleType domain_tuple;

  // The function (domain_tuple -> rgbVal )

  private FunctionType func_domain_rgbVal;

  // Our Data values for the domain are represented by the Set

  private Set domain_set;

  // The Data class FlatField

  private FlatField vals_ff;

  // The DataReference from data to display

  private DataReferenceImpl data_ref;

  // The 2D display, and its the maps

  private DisplayImpl display;
  private ScalarMap redXMap, greenYMap, blueZMap;
  private ScalarMap redMap, greenMap, blueMap;

  public visad5_3D_dots(String[] args)
      throws RemoteException, VisADException {

    // Create the quantities
    // Use RealType(String name, Unit unit, Set set);

    red = RealType.getRealType("RED", null, null);
    green = RealType.getRealType("GREEN", null, null);
    blue = RealType.getRealType("BLUE", null, null);

    domain_tuple = new RealTupleType(red, green, blue);

    // The independent variable

    rgbVal = RealType.getRealType("RGB_VALUE", null, null);

    // Create a FunctionType (domain_tuple -> range_tuple )
    // Use FunctionType(MathType domain, MathType range)

    func_domain_rgbVal = new FunctionType(domain_tuple, rgbVal);

    // Create the domain Set
    // Integer3DSet(MathType type, int lengthX, int lengthY, int lengthZ)

    int NCOLS = 8;
    int NROWS = 8;
    int NLEVS = 8;

    domain_set = new Integer3DSet(domain_tuple, NROWS, NCOLS, NLEVS);

    // Our 'flat' array

    double[][] flat_samples = new double[1][NCOLS * NROWS * NLEVS];

    // Fill our 'flat' array with the rgbVal values
    // by looping over NCOLS and NROWS

    // Note the use of an index variable, indicating the order of the samples

    int index = 0;

    for (int l = 0; l < NLEVS; l++)

      for (int c = 0; c < NCOLS; c++)

        for (int r = 0; r < NROWS; r++) {

          // set rgbVal rgbVal
          flat_samples[0][index] = index; //

          // increment index
          index++;
        }

    // Create a FlatField
    // Use FlatField(FunctionType type, Set domain_set)

    vals_ff = new FlatField(func_domain_rgbVal, domain_set);

    // ...and put the rgbVal values above into it

    // Note the argument false, meaning that the array won't be copied

    vals_ff.setSamples(flat_samples, false);

    // Create Display and its maps

    // This is new: a 3D display

    display = new DisplayImplJ3D("display1");

    // Get display's graphics mode control and draw scales

    GraphicsModeControl dispGMC = (GraphicsModeControl) display.getGraphicsModeControl();
    dispGMC.setScaleEnable(true);
    dispGMC.setPointSize(4.0f);

    // Get the display renderer
    DisplayRenderer dRenderer = display.getDisplayRenderer();

    // Set the display background color
    dRenderer.setBackgroundColor(Color.white);

    // Set box on or off: you choose
    dRenderer.setBoxOn(false);

    // could also change the foreground color
    dRenderer.setForegroundColor(Color.gray);

    // uncomment to set the box color
    // dRenderer.setBoxColor(Color.gray);

    // Create the ScalarMaps: latitude to XAxis, longitude to YAxis and
    // rgbVal to ZAxis and to RGB
    // Use ScalarMap(ScalarType scalar, DisplayRealType display_scalar)

    redXMap = new ScalarMap(red, Display.XAxis);
    greenYMap = new ScalarMap(green, Display.YAxis);
    blueZMap = new ScalarMap(blue, Display.ZAxis);

    redXMap.setScalarName("The RED Component");
    greenYMap.setScalarName("The GREEN Component");
    blueZMap.setScalarName("The BLUE Component");

    redMap = new ScalarMap(red, Display.Red);
    greenMap = new ScalarMap(green, Display.Green);
    blueMap = new ScalarMap(blue, Display.Blue);

    // Add maps to display

    display.addMap(redXMap);
    display.addMap(greenYMap);
    display.addMap(blueZMap);

    display.addMap(redMap);
    display.addMap(greenMap);
    display.addMap(blueMap);

    // Set axes colors

    float[] r = colorToFloats(Color.red);
    redXMap.setScaleColor(r);

    float[] g = colorToFloats(Color.green);
    greenYMap.setScaleColor(g);

    float[] b = colorToFloats(Color.blue);
    blueZMap.setScaleColor(b);

    // Create a data reference and set the FlatField as our data

    data_ref = new DataReferenceImpl("data_ref");

    data_ref.setData(vals_ff);

    // Add reference to display

    display.addReference(data_ref);

    // Create application window and add display to window

    JFrame jframe = new JFrame("VisAD Tutorial example 4_14");
    jframe.getContentPane().add(display.getComponent());

    // Set window size and make it visible

    jframe.setSize(600, 600);
    jframe.setVisible(true);

  }

  /*
   * Utility method to transform a Java color in
   * an array of rgb components between 0 and 1
   */
  private float[] colorToFloats(Color c) {

    float[] rgb = new float[] { 0.5f, 0.5f, 0.5f }; // init with gray
    if (c != null) {
      rgb[0] = (float) c.getRed() / 255.0f;
      rgb[1] = (float) c.getGreen() / 255.0f;
      rgb[2] = (float) c.getBlue() / 255.0f;

    }

    return rgb;
  }

  public static void main(String[] args)
      throws RemoteException, VisADException {
    new visad5_3D_dots(args);
  }

} // end of Visad Tutorial Program 4_14
