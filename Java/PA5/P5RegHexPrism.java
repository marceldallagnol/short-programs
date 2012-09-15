/**
* P5RegHexPrism.java
* This class extends P5Hexagon, and therefore has one (non-final) instance
* variable, its side, apart from its height. It represents a prism with a
* regular hexagon for a base.
**/
public class P5RegHexPrism extends P5RegHexagon
{
   private double height;                        //Prism height

   /*This no-argument constructor initializes the height of the prism to 1. The
     implicit super() call sets the side to 1 as well*/
   public P5RegHexPrism(){height = 1.0;}

   /*This constructor initializes the side of the prism to s, and
     height to h*/
   public P5RegHexPrism(double s, double h)
   {
      side = s;
      height = h;
   }

   /*This method sets the side of the base hexagon to the argument dim1,
     and its height to the argument dim2 (overloaded)*/
   public void setDim(double dim1, double dim2)
   {
      side = dim1;
      height = dim2;
   }

   /*This method returns a string with the name of the class and the value of
     the side and height (overloaded)*/
   public String toString()
   {
      return(this.getName() + "  " + side + " x " + height);
   }

   /*This method returns the surface area of the prism that invoked it
     (overloaded)*/
   public double area()
   {
      return(2 * super.area() + N_SIDES * side * height);
      //Base area time 2, plus area of sides
   }

   /*This method returns the volume of the prism that invoked it (overloaded)*/
   public double volume()
   {
      return(super.area() * height);      //Base area times height
   }
}
