/**
* P5RegPentagon.java
* This class extends P5Polygon, and therefore has one (non-final) instance
* variable, its side. It represents a regular pentagon, with a number of sides
* and an area constant specific of this type of polygon. The area constant is
* the constant that side^2 is multiplied by to yield its area.
**/
public class P5RegPentagon extends P5Polygon
{
   final protected int N_SIDES = 5;
   final protected double AREA_CONSTANT = 1.720477;

   /*This no-argument constructor initializes the side of the pentagon to 1*/
   public P5RegPentagon(){side = 1.0;}

   /*This constructor initializes the side of the pentagon to the argument s*/
   public P5RegPentagon(double s){side = s;}

   /*This method returns a string with the name of the class and the value of
     the side (implemented abstract method)*/
   public String toString(){return(this.getName() + "  " + side + " side");}

   /*This method returns the perimeter of the pentagon that invoked it
     (overloaded)*/
   public double perimeter(){return(N_SIDES * side);}

   /*This method returns the area of the pentagon that invoked it
     (implemented abstract method)*/
   public double area(){return(AREA_CONSTANT * side * side);}
}
