/* Assignment 6			P6.java			Due March 3, 2012 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
* This class extends JApplet and impements the interfaces MouseListener,
* MouseMotionListener and ItemListener. It gives the user options of colors and
* shapes to choose from, and a white rectangle on which the user may draw these
* shapes by dragging the mouse from one point to the other. It also shows the
* position of the mouse pointer on the status bar.
*
* @author Dall'Agnol, Marcel
* @version 1.0
* @since 02/28/2012
* @see javax.swing.JComboBox
* @see javax.swing.JRadioButton
* @see javax.swing.JApplet
* @see java.awt.event.MouseListener
* @see java.awt.event.MouseMotionListener
* @see java.awt.event.ItemListener
*/
public class P6 extends JApplet
implements  MouseListener, MouseMotionListener, ItemListener
{
   private final int BLUE =      0;          //Selectable colors
   private final int BLACK =     1;
   private final int CYAN =      2;
   private final int GREEN =     3;
   private final int MAGENTA =   4;
   private final int RED =       5;
   
   private final int ARC =       0;          //Selectable shapes
   private final int LINE =      1;
   private final int OVAL =      2;
   private final int POLYGON =   3;
   private final int RECTANGLE = 4;
   
   private int mouseX, mouseY;               //X and Y position of mouse pointer
   private int x1, x2, y1, y2;               //Start and end point of shapes
   private int x3[], y3[];                   //Points of polygon
   private int shape = -1;
   private int color = -1;
   private JComboBox combo;                  //Combo box with colors
   private String colorNames[] =
         {"Blue", "Black", "Cyan", "Green", "Magenta", "Red"};
   private String shapeNames[] =
         {"Arc", "Line", "Oval", "Polygon", "Rectangle"};
   private JRadioButton radioButtons[];      //Radio buttons with shapes
   
	/**
   * This initializes all necessary objects and variables for the applet,
   * including combo box and radio buttons, and also sets the layout
   * and adds those to it.
   *
   * @see javax.swing.ButtonGroup
   */
   public void init()
   {
      int i;
      
      /* Instantiate objects */
      Container c = getContentPane();
      c.setLayout(new FlowLayout());
      combo = new JComboBox(colorNames);
      radioButtons = new JRadioButton[shapeNames.length];
      x3 = new int[5];                          //X and Y coordinates of
      y3 = new int[5];                          //points of the polygon
      
      ButtonGroup radioButtonGroup = new ButtonGroup();
      for(i = 0; i < shapeNames.length; i++)    //Add radio buttons
      {
         radioButtons[i] = new JRadioButton(shapeNames[i]);
         radioButtons[i].addItemListener(this);
         radioButtonGroup.add(radioButtons[i]);
         c.add(radioButtons[i]);
      }
      
      c.add(combo);                             //Add combo box
      combo.addItemListener(this);              //Add listeners
      addMouseListener(this);
      addMouseMotionListener(this);
      c.setBackground(Color.blue);              //Blue background color
   }
   
   /**
   * This method sets the color and shape selected and paints them onto the
   * applet.
   *
   * @see java.awt.Color
   * @see java.awt.Graphics
   * @see java.awt.Font
   */
   public void paint(Graphics g)
   {
      super.paint(g);
      g.setColor(Color.white);                  //White inner rectangle
      g.fillRect(15, 50, 470, 435);             //Draw inner rectangle
      g.setColor(Color.black);                  //Black text
      g.setFont(new Font("Serif", Font.ITALIC + Font.BOLD, 18));
      g.drawString("Start here", 15, 68);
      g.drawString("End here", 395, 482);

      switch(color)     //Choose shape color
      {
         case BLUE:    g.setColor(Color.blue); break;
         case BLACK:   g.setColor(Color.black); break;
         case CYAN:    g.setColor(Color.cyan); break;
         case GREEN:   g.setColor(Color.green); break;
         case MAGENTA: g.setColor(Color.magenta); break;
         case RED:     g.setColor(Color.red); break;
         default: break;
      }
      
      switch(shape)     //Choose shape
      {
         case ARC: 
            g.fillArc(x1, y1, x2 - x1, y2 - y1, 0, 90);    //Draw 0-90 and
            g.fillArc(x1, y1, x2 - x1, y2 - y1, 180, 90);  //180-270 degree arcs
            break;
         case LINE:
            g.drawLine(x1, y1, x2, y2);                    //Draw line
            break;
         case OVAL:
            g.fillOval(x1, y1, x2 - x1, y2 - y1);          //Draw oval shape
            break;
         case POLYGON:
            g.fillPolygon(x3, y3, x3.length);              //Draw polygon
            break;
         case RECTANGLE:
            g.fillRect(x1, y1, x2 - x1, y2 - y1);          //Draw rectangle
            break;
         default:                                          //Do nothing if shape
            break;                                         //is not selected
      }
   }

   /**
   * This implemented interface method is called when an item is selected or
   * deselected, setting the color to the current color selected in the combo
   * box, and the shape to the one selected in the radio buttons, and then
   * repaints the result.
   *
   * @see java.awt.event.ItemListener
   */
   public void itemStateChanged(ItemEvent e)
   {
      int i;
      
      color = combo.getSelectedIndex();       //Set color to current selection

      for(i = 0; i < shapeNames.length; i++)  //Set shape to current selection
         if(radioButtons[i].isSelected()) shape = i;
      repaint();                              //Repaint shape
   }

   /**
   * This implemented interface method is called when the mouse pointer moves,
   * updating its position and repainting the result. The position is relative
   * to the upper left corner of the applet.
   *
   * @see java.awt.event.MouseMotionListener
   */
   public void mouseMoved(MouseEvent e)
   {
      mouseX = e.getX();                     //Get mouse position
      mouseY = e.getY();
      this.showStatus("(" + mouseX + ", " + mouseY + ")");
   }

   /**
   * This implemented interface method is called when the mouse button is
   * pressed. It records the position as the starting point for the shape to
   * be drawn.
   *
   * @see java.awt.event.MouseListener
   */
   public void mousePressed(MouseEvent e)
   {
      x1 = x3[0] = x3[2] = x3[4]= e.getX();    //Set start position of the shape
      y1 = y3[0] = y3[1] = y3[4]= e.getY();    //and polygon points.
   }

   /**
   * This implemented interface method is called when the mouse button is
   * released, recording the position as the end point of the shape to be drawn.
   *
   * @see java.awt.event.MouseListener
   */
   public void mouseReleased(MouseEvent e)
   {
      x2 = x3[1] = x3[3] = e.getX();            //Set end position of the shape
      y2 = y3[2] = y3[3] = e.getY();            //and polygon points.
   }

   /** This (inherited from interface) stub is called when the mouse button is
   * clicked (does nothing).
   *
   * @see java.awt.event.MouseListener
   */
   public void mouseClicked(MouseEvent e){}

   /** This (inherited from interface) stub is called when the mouse pointer
   * enters a component (does nothing).
   *
   * @see java.awt.event.MouseListener
   */
   public void mouseEntered(MouseEvent e){}

   /** This (inherited from interface) stub is called when the mouse pointer
   * exits a component (does nothing).
   *
   * @see java.awt.event.MouseListener
   */
   public void mouseExited(MouseEvent e){}

   /** This (inherited from interface) stub is called when the mouse is
   * dragged (does nothing).
   *
   * @see java.awt.event.MouseMotionListener
   */
   public void mouseDragged(MouseEvent e){}
}
