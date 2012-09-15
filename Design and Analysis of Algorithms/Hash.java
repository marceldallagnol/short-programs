import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class Hash
{
   private static final int TSIZE = 196613;
   private static final int NLINES = 100000;
   public static void main(String[] args) throws IOException
	{
	   LineNumberReader file = null ;         //Read lines from file
      intList[] hashTable = new intList[TSIZE];
      int[] numbers = new int[NLINES];
      int[] targetSums = {231552,234756,596873,648219,726312,981237,988331,1277361,1283379};
      int i, j, k, current;
      boolean found;
      
      for(i = 0; i < TSIZE; i++)
         hashTable[i] = new intList();
         
      if(args.length != 1)                //Check for program input
      {
         System.out.println("Wrong input format! Input is 'java SCC file'.");
         System.exit(-1);
      }
      try                                 //Set up file reference
      {
         file = new LineNumberReader( new FileReader(new File(args[0])));
      }
      catch(FileNotFoundException e)      //Check for file name validity
      {
         System.out.println("File '" + args[0] + "' does not exist!");
         System.exit(-1);
      }

      for(i = 0; i < NLINES; i++)
      {  
         current = Integer.parseInt(file.readLine());
         hashTable[current % TSIZE].add(current);
         numbers[i] = current;
      }
      
      System.out.println("Target sums and portions:\n");
      for(k = 0; k < targetSums.length; k++)
      {
         found = false;
         System.out.print(targetSums[k] + " -> ");
         for(i = 0; i < NLINES - 1 && !found; i++)
         {
            current = targetSums[k] - numbers[i];
            for(j = i + 1; j < NLINES && !found; j++)
               if(hashTable[j].search(current))
                  {
                     found = true;
                     System.out.println(numbers[i] + " " + current + " ");
                  }
         }
         if(!found)
            System.out.println("No match");
      }
	   System.out.println();
	}
}
/*Estrutura lista de ints*/
class intList
{
   public int size, capacity, number;
   public int[] list;
   private static final int LIST_BLOCK = 10;
   
   public intList()
   {
      list = new int[LIST_BLOCK];
      capacity = LIST_BLOCK;
   }
   
   public void add(int n)
   {
      if(size + 1 == capacity)
      {
         int[] auxList = new int[capacity + LIST_BLOCK];
         for(int i = 0; i < size; i++)
            auxList[i] = list[i];
         list = auxList;
         capacity += LIST_BLOCK;
      }
      list[size++] = n;
   }
   
   public boolean search(int n)
   {
      int i;
      boolean exists = false;
      
      for(i = 0; i < size && !exists; i++)
         if(list[i] == n)
            exists = true;          
      return exists;
   }
}
