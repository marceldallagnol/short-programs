/**
* P5RegHexagon.java
* This class extends P5Polygon, and therefore has one (non-final) instance
* variable, its side. It represents a regular hexagon, with a number of sides
* and an area constant specific of this type of polygon. The area constant is
* the constant that side^2 is multiplied by to yield its area.
**/
public class P5RegHexagon extends P5Polygon
{
   final protected int N_SIDES = 6;
   final protected double AREA_CONSTANT = 2.598076;
   
   /*This no-argument constructor initializes the side of the hexagon to 1*/
   public P5RegHexagon(){side = 1.0;}

   /*This constructor initializes the side of the hexagon to the argument s*/
   public P5RegHexagon(double s){side = s;}

   /*This method returns a string with the name of the class and the value of
     the side (implemented abstract method)*/
   public String toString(){return(this.getName() + "   " + side + " side");}

   /*This method returns the perimeter of the hexagon that invoked it
     (overloaded)*/
   public double perimeter(){return(N_SIDES * side);}

   /*This method returns the area of the hexagon that invoked it
     (implemented abstract method)*/
   public double area(){return(AREA_CONSTANT * side * side);}
}
