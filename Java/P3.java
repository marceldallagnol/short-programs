/**
* This program receives a sequence of words as input, and produces, as
* output, the words in alphabetical order and divides them in groups of words 
* that begin woth the same letter. These groups are composed of a 2D array of
* strings, containing a group in each row. This array is then also
* printed on screen.
**/

import java.util.Scanner;

public class P3
{
   public static void main(String []args)
   {
      /* The main method is responsible for scanning the words from the
         keyboard, assigning each one to a position in an array and printing
         the sorted 1-D array. The program repeats until the user types ^D.
         The method sort1D() is used to sort the 1D array, and sort2D() is
         used to separate each word into a row in a 2D array (and then printing
         the result).
      */
      Scanner scan = new Scanner(System.in);       //Input from keyboard
      String sentence;                             //User string input
      int i;
      
      System.out.print("Enter your words to be sorted (exit ^D):");
      do                                           //Repeat until user enters ^D
      {
         sentence = scan.nextLine();               //Scan input from user
         String words[] = sentence.split(" ");     //Split sentence into an
                                                   //array of words. Words are
                                                   //delimited by a blank space

         sort1D(words);                            //Sort words alphabetically
         System.out.print("1-D Sorted: ");
         for(i = 0; i < words.length; i++)
            System.out.print(words[i] + " ");      //Print ordered words
         System.out.println();

         sort2D(words);                            //Sort words into 2D array,
                                                   //one row per letter

         System.out.print("\nEnter your words to be sorted (exit ^D): ");
      }while(scan.hasNext());                      //Repeat until ^D
      System.exit(0);                              //End program
   }//End main

   public static void sort1D(String []w)
   {
      /* This method receives an array of strings and orders them
         alphabetically with the bubble sort algorithm.
      */
      String swapTemp;                             //Auxiliary variable for swap
      
        for(int i = 0; (i < w.length-1)            //Start from first element
                        && (w[i] != null); i++)    //until second to last
          for(int j = w.length-1; i < j            //Bring the element starting
                                  && (w[j] != null); j--)//with smallest letter
            if(w[j-1].compareToIgnoreCase(w[j]) > 0)//Compare (case insensitive)
            {
              swapTemp = w[j-1];                    //Swap if word j comes
              w[j-1] = w[j];                        //before word j-1
              w[j] = swapTemp;                      //alphabetically
    	   }
   }//End sort1D

   public static void sort2D(String []sw)
   {
      /* This method receives an array of strings and orders them
         alphabetically, dividing them into a 2D array. Each row contains words
         that start with a letter. The resulting array is then printed.
      */
      final int ALPHA = 26;                         //26 letters in the alphabet
      int i,j;
      int col;                                      //Columns of a2D[][]
      char uCase = 'A';                             //Upper case letter
      char lCase = 'a';                             //Lower case letter
      
      String a2D[][] = new String[ALPHA][];         //Allocate ALPHA rows
      for(i = 0; i < ALPHA; i++) a2D[i] = null;     //Initialize with nulls


      for(j = col = 0; j <= ALPHA; j++)             //Count number of words
      {                                             //with the same letter
        if(col > 0)
          a2D[j-1] = new String[col];               //Allocate memory
        for(i = col = 0; (i<sw.length) && (sw[i] != null); i++)
          if(sw[i].charAt(0) == uCase || sw[i].charAt(0) == lCase) col++;
                                                    //If word starts with this
                                                    //letter, add column
        uCase++;
        lCase++;                                    //Next letter
      }

      uCase = 'A';
      lCase = 'a';
      for(j = 0; j < ALPHA; j++)                    //Copy words into the
      {                                             //allocated memory
         if(a2D[j] != null)                         //Run if there are any words
                                                    //starting with this letter
            for(i = 0, col = 0;
               (i<sw.length) && (col < a2D[j].length) && (sw[i] != null); i++)
            {                                       //Traverse vector to find
               if(sw[i].charAt(0) == uCase || sw[i].charAt(0) == lCase)//words
               {                                    //starting with this letter
                  a2D[j][col] = sw[i];              //Copy word
                  col++;                            //Go to next column
               }
            }
         uCase++;                                   //Next letter
         lCase++;
      }
      
      for(i = 0; i < ALPHA; i++)                    //Print results
        if(a2D[i] != null)                          //If there are words
        {                                           //starting with this letter
           System.out.println();                    //Extra line between rows
           for(j = 0; j < a2D[i].length; j++)       //Print words
              System.out.println("a2D["+i+"]["+j+"] :"+a2D[i][j]);
        }
   }//End sort2D
}//End class P3
