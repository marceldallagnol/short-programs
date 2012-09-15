/**
* This program creates objects of the classes P5RegPentagon, P5RegHexagon,
* P5RegPentaPrism and P5RegHexPrism. It prints their perimeter, area and volume
* obtained from the values set in the initialization. It then prompts the
* user to input a value for the side and height of the objects, and prints
* the perimeter, area and volume calculated from these values. This repeats
* until the user chooses to stop.
**/
import java.util.Scanner;                       //Scan input form keyboard

public class P5
{
   private static final int POLYS = 4;          //4 Polygon objects
   private static P5Polygon []a;
   
   public static void main(String[] args)
   {
      char choice;                              //Repeat program
      double side;                              //Input Pentagon, Hexagon
      double height;                            //Input Prisms
      String inputStr = null;                   //Input string
      Scanner scan = new Scanner(System.in);
      
      a = new P5Polygon[POLYS];                 //Allocate 4 Polygon ref
      a[0] = new P5RegPentagon();               //Instantiate objects
      a[1] = new P5RegPentaPrism(1.2,2.3);
      a[2] = new P5RegHexagon();
      a[3] = new P5RegHexPrism(3.4,4.5);
      prt();                                    //Display objects
      
      do
      {
         System.out.print("\nEnter Pentagon and Hexagon (side): ");
         side = scan.nextDouble();
         
         System.out.print("Enter Prism height: ");
         height = scan.nextDouble();
         
         a[0].setDim(side);                     //Reassign
         a[1].setDim(side, height);
         a[2].setDim(side);
         a[3].setDim(side, height);
         prt();                                 //Display objects
         
         System.out.print("\nWant to compute polygon figures (y/n)? ");
         inputStr = scan.next();                //Read, assign to string
         choice = inputStr.charAt(0);           //Assign 1st character
      }while(choice != 'n' && choice != 'N');   //Loop until n or N
   }

   /* This method prints the values of area and perimeter (if it is a pentagon
      or hexagon) or surface area and volume (if it is a prism) of all the
      objects in the array a[].
   */
   public static void prt()
   {
      int i;
      
      for(i = 0; i < POLYS; i++)                //Iterate for all shapes
      {
         if(i % 2 == 0)                         //For even (2D) objects
         {
            System.out.print(a[i] + " has an area:"); //Print area and perimeter
            System.out.printf("\t%.3f \n",a[i].area());
            System.out.print(a[i] + " has a perimeter:");
            System.out.printf("\t%.3f \n",a[i].perimeter());
         }
         else                                   //For odd (3D) objects
         {
            System.out.print(a[i] + " has a surface area:"); //Print surface
            System.out.printf("\t%.3f",a[i].area());         //area and volume
            System.out.printf(" and volume: %.3f\n", a[i].volume());
         }
      }
   }
}
