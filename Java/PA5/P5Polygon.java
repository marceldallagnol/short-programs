/**
* P5Polygon.java
* This abstract class contains one instance variable, the side of the polygon
* it represents. It also contains all the methods used by the classes that
* inherit from P5Polygon, either fully defined, or as stubs, or as abstract
* methods. The abstract methods are the ones which all the child classes
* implement, but differently.
**/
public abstract class P5Polygon
{
   protected double side;                 //Polygon side

   /*This no-argument constructor initializes the side of the polygon to 1*/
   public P5Polygon(){side = 1.0;}

   /*This constructor initializes the side of the polygon to the argument s*/
   public P5Polygon(double s){side = s;}

   /*This abstract method returns a string with data from the object*/
   public abstract String toString();

   /*This method returns a string with the name of the class that the object
     that invoked it, followed by ": "*/
   public String getName(){return(this.getClass().getName() + ": ");}

   /*This method sets the side of the polygon to the argument dim*/
   public void setDim(double dim){side = dim;}

   /*This method sets the side of the base polygon to the argument dim1, and its
     height to the argument dim2. It is used for prisms (overloaded stub)*/
   public void setDim(double dim1, double dim2){}

   /*This method returns the perimeter of the polygon that invoked it
     (overloaded stub)*/
   public double perimeter(){return 0;}

   /*This abstract method returns the area of the object that invoked it (planar
     area for 2D objects and surface area for 3D objects*/
   public abstract double area();

   /*This method returns the volume of the prism that invoked it (overloaded 
     stub)*/
   public double volume(){return 0;}
}
