import java.io.BufferedReader;                 //input from keyboard
import java.io.InputStreamReader;              //input from keyboard
import java.io.IOException;                    //throws IOException

/* This program receives an input from the user, requesting 3 integers
representing the sides of a triangle. It then checks various errors, namely:
range (input must be 1-100), triangle rule (no side exceeds the sum of the 2
others, number of arguments and if the inputs can be parsed as integers. Some
of these checks will be made as exception handles. If the input is valid, the
program calculates the perimeter of the triangle. The program repeats until
the user types Cntrl+D. */
public class P7
{
   private static final int ASIDES = 3;         //3 triangle sides
   private static final int MAX_SIDE = 100;     //max value for each side
   private static final int MIN_SIDE = 1;       //min value for each side
   private static final int sides[] = new int[ASIDES];//array for triangle sides
   
   public static void main(String[] args) throws IOException
   {

      int i, j, sum;
      /* Input error flag. Will be set to true if any error occurs, including
         both error checks and exceptions (apart from ArrayIndexOutOfBounds,
         in which case the three first inputs are read and the program continues
      */
      boolean inputErr;
      /* Keyboard input */
      InputStreamReader converter = new InputStreamReader(System.in);
      BufferedReader in = new BufferedReader(converter);
      String inputStr, strSides[];       //input string and split string array
      
      System.out.print("\nENTER 3 triangle sides (range 1-100): ");
      while((inputStr = in.readLine()) != null)   //Until user presses Cntrl+D
      {
         System.out.print("Input: " + inputStr);
         inputErr = false;                        //initialize error to false
         strSides = inputStr.split(" ");          //split input into array

         /*In the try block, error checks and possible exceptions will occur*/
         try
         {
            /* Error occurs if user inputs less than 3 sides*/
            if(strSides.length < ASIDES)
            {
               System.out.println
               ("\n\tERROR! Less than 3 sides entered. Enter again.");
               inputErr = true;
            }
            else
            {
               for(i = 0; i < strSides.length; i++)
               {
                  sides[i] = Integer.parseInt(strSides[i]);//May throw exception

                  /* Error occurs if any side is out of 1-100 range*/
                  if(sides[i] > MAX_SIDE || sides[i] < MIN_SIDE)
                  {
                     System.out.println
                     ("\n\tERROR! Out of range 1-100. Enter again.");
                     inputErr = true;
                     break;
                  }
               }
               /* Error occurs if any side is equal to or higher than the sum
                  of the 2 others (rule for the existence of a triangle) */
               for(i = 0; i < ASIDES; i++)
               {
                  sum = 0;
                  for(j = 0; j < ASIDES; j++)
                     if(j != i) sum += sides[j];
                  if(sides[i] >= sum)
                  {
                     System.out.println
                     ("\n\tERROR! Sum of 2 sides <= 3rd side. Enter again.");
                     inputErr = true;
                     break;
                  }
               }
            }
            if(inputErr)   //If any error has occured, repeat input
               System.out.print("\nENTER 3 triangle sides (range 1-100): ");
         }
         /* This catch block will be entered if the user inputs something other
            than an integer, parse to int will throw exception */
         catch(NumberFormatException e)
         {
            inputErr = true;
            System.out.println(" are not valid sides of a triangle.");
            e.printStackTrace();    //Print stack trace
            System.out.print
            ("\nENTER again - 3 triangle sides (range 1-100): ");
         }
         /* This catch block will be entered if the user inputs more than 3
            sides for the triangle. Exception will be thrown, and 3 first inputs
            will be considered. */
         catch(ArrayIndexOutOfBoundsException e)
         {
            System.out.println("\n\tEntered more than 3 sides.");
            System.out.print("\tFirst 3 sides accepted. Continuing...");
         }
         /* Catch-all block. If any exception other than the 2 above occur,
            prints the stack trace */
         catch(Exception e)
         {
            inputErr = true;
            e.printStackTrace();
            System.out.print
            ("\nENTER again - 3 triangle sides (range 1-100): ");
         }
         
         if(!inputErr)     //If no input error occurs, prints sides and
         {                 //calculates the perimeter
            System.out.print("\nTriangle: " + sides[0]);
            for(i = 1; i < ASIDES; i++)
               System.out.print(" + " + sides[i]);
            System.out.println();
            calcPerimeter();
            System.out.print("\nENTER 3 triangle sides (range 1-100): ");
         }
      }
   }

   /* This method will calculate the perimeter of the triangle (sum of all sides
      of the sides[] class array) and print it to the screen */
   public static void calcPerimeter()
   {
      int perimeter = 0;
      int i;
      
      for(i = 0; i < ASIDES; i++)
         perimeter += sides[i];
      System.out.println("Perimeter: " + perimeter);
   }
}
