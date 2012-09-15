import java.io.*; 
import java.text.DecimalFormat;              //Format line number to 2 decimals
import java.lang.Character;                  //Checks for identifier validity

/* This program checks for errors in the declarations of class variables of a
   file. It receives a file as input and checks, line by line, if the access
   modifiers are private, and if data types and identifiers are valid or
   missing. It reports each error it finds, showing its type and linking it to
   the line in which it was found. This program also reports errors if the
   input format is incorrect or if the file passed does not exist. */
public class P8
{ 
   public static void main(String[] args) throws IOException
   {
      LineNumberReader file = null ;         //Read lines from file
      String line;                           //Current line
      String[] lineString;                   //Current line, divided
      int i, lineNumber, nErrors = 0;        //Counter, line number and errors
      DecimalFormat df = new DecimalFormat("00");  //Format to 2 decimals
      boolean skipLoop;                   //Flag for program to skip remaining
                                          //operations in the iteration
      if(args.length != 1)                //Check for program input
      {
         System.out.println("Wrong input format! Input is 'java P8 file'.");
         System.exit(-1);
      }
      try                                 //Set up file reference
      {
         file = new LineNumberReader( new FileReader(new File(args[0])));
      }
      catch(FileNotFoundException e)      //Check for file name validity
      {
         System.out.println("File " + args[0] + " does not exist!");
         System.exit(-1);
      }
      
      /* Skip lines with the public class header and opening braces.
         Implementation for error checks here are possible. */
      do
      {
         line = file.readLine();
         lineString = line.split(" +");
      }while(!lineString[lineString.length-1].equals("{"));

      file.setLineNumber(file.getLineNumber() + 1);//Set starting line to 1
      lineNumber = file.getLineNumber();           //First line number
      line = file.readLine();                      //First line
      while(line != null)                          //Repeat until end of file
      {
         i = 0;                                    //First word of line
         skipLoop = false;
         lineString = line.split(" +");            //Split line into words
         
         if(lineString[0].equals(""))              //If there are white spaces
         {                                         //in beginning of line
            i = 1;                                 //First word set forward
            if(lineString.length == 1)             //If there are only white
               skipLoop = true;                    //spaces (0 or more), skip
         }                                         //to next line
         else
            if(lineString[i].charAt(0) == '}')     //If closing braces found,
               skipLoop = true;                    //skip line

         if(!skipLoop)
         {
            if(lineString[i].endsWith(";") ||      //If access identifier
               lineString[i+1].equals(";"))        //is followed by ';', error
            {                                      
               System.out.print(args[0] + ":" +    //Print error line
                                df.format(lineNumber) + ": ");
               if(lineString[i].endsWith(";"))
                  lineString[i] = 
                     lineString[i].substring(0, lineString[i].length()-1);
               if(!isPrivate(lineString[i]))       //Check identifier validity
                  System.out.println("\"" + lineString[i] + 
                                     "\", non-private access modifier");
               else                                //If access modifier valid
                  System.out.println("data type missing");//No data type
               nErrors++;                          //Add error to total
               skipLoop = true;                    //Skip rest of iteration
            }
            else            //Access modifier not followed by ';'
            {
               if(!isPrivate(lineString[i])) //Check access modifier validity
               {
                  System.out.println(args[0] + ":" +
                                   df.format(lineNumber) + ": " + 
                                   "\"" + lineString[i] + 
                                   "\", non-private access modifier");
                  nErrors++;
                  skipLoop = true;
               }
            }
         }

         i++;
         if(!skipLoop)                             //Move to data type check
         {
            if(lineString[i].endsWith(";") ||      //If data type followed
               lineString[i].endsWith(",") ||      //by ';' or ',', error
               lineString[i+1].equals(";") ||
               lineString[i+1].charAt(0) == ',')
            {
               System.out.print(args[0] + ":" +    //Print line
                                df.format(lineNumber) + ": ");
               if((lineString[i].endsWith(";") || lineString[i].endsWith(","))
                    && !lineString[i].equals(","))  //Trim if necessary
                  lineString[i] =
                     lineString[i].substring(0, lineString[i].length()-1);
               if(!isDataType(lineString[i]))     //Check data type validity
                  System.out.println("\"" + lineString[i] + 
                                     "\", data type unknown");
               else              //If data type valid, identifier missing
                  System.out.println("no identifier specified");
               nErrors++;
               skipLoop = true;
            }
            else                 //Data type not followed by ';' or ','
            {
               if(!isDataType(lineString[i]))//Check data type validity
               {  
                  System.out.println(args[0] + ":" +
                                   df.format(lineNumber) + ": " +
                                   "\"" + lineString[i] + 
                                   "\", data type unknown");
                  nErrors++;
                  skipLoop = true;
               }
            }
         }

         while(!skipLoop)              //Move to identifiers
         {
            i++;
   
            if(lineString[i].charAt(0) == ',')  //Trim if necessary
               lineString[i] =
                  lineString[i].substring(0, lineString[i].length()-1);

            if(!lineString[i].equals("") &&     //If word was not equal to ','
               !isStartID(lineString[i]))       //and first character invalid
            {
               while(lineString[i].endsWith(";") ||
                     lineString[i].endsWith(","))
                  lineString[i] =               //Trim if necessary
                     lineString[i].substring(0, lineString[i].length()-1);
               System.out.println(args[0] + ":" +     //Print error
                      df.format(lineNumber) + ": '" + lineString[i].charAt(0) +
                      "': invalid first character of identifier, \"" +
                      lineString[i] + "\"");
               nErrors++;
               skipLoop = true;
            }
            
            if(!skipLoop && (lineString[i].endsWith(";") ||   //First character
                             lineString[i].endsWith(",")))    //valid, followed
            {                                                 //by ';' ot ','
               if(lineString[i].endsWith(";"))
                  skipLoop = true;                            //End of line

               lineString[i] =                                //Trim
                  lineString[i].substring(0, lineString[i].length()-1);

               if(lineString[i].endsWith(",") || (!skipLoop && //Check for
                 (lineString[i+1].equals(";") ||               //combinations
                  lineString[i+1].charAt(0) == ',')))          //',,',',;'
               {
                  System.out.print(args[0] + ":" +
                                df.format(lineNumber) + ": ");
                  if(isID(lineString[i]) != -1)    //Check validity
                     System.out.println
                        ("'" + lineString[i].charAt(isID(lineString[i])) +
                         "': invalid character in identifier, \"" +
                         lineString[i] + "\"");
                  else                   //Identifier valid, but ',,', ',;'
                     System.out.println("no identifier specified");
                  nErrors++;
                  skipLoop = true;
               }
               else                     //No combinations ',,', ',;'
               {
                  if(isID(lineString[i]) != -1)    //Check validity
                  {
                     System.out.println(args[0] + ":" +
                        df.format(lineNumber) + ": '" +
                        lineString[i].charAt(isID(lineString[i])) +
                        "': invalid character in identifier, \"" +
                        lineString[i] + "\"");
                     nErrors++;
                     skipLoop = true;
                  }
               }
            }

            if(!skipLoop && isID(lineString[i]) != -1)   //ID does not end in
            {                                       //',,', ',;', but invalid
               System.out.println(args[0] + ":" +
                      df.format(lineNumber) + ": '" +
                      lineString[i].charAt(isID(lineString[i])) +
                      "': invalid character in identifier, \"" +
                      lineString[i] + "\"");
               nErrors++;
               skipLoop = true;
            }
         }
         lineNumber = file.getLineNumber();              //Next line number
         line = file.readLine();                         //Next line
      }
      System.out.println(nErrors + " errors");           //Print total errors
   }

   /* This method receives a strinng and returns a boolean value depending on 
      whether it is or is not a keyword referring to a data type, returning
      true if it is and false if it isn't. The data types checked are the 8
      primitive data types in Java. */
   public static boolean isDataType(String s)
   {
      String[] dataTypes = 
      {"boolean", "byte", "char", "double", "float", "int", "long", "short"};
      int i;
      boolean valid = false;
   
      for(i = 0; i < dataTypes.length; i++)
         if(s.equals(dataTypes[i]))
            valid = true;
      return valid;
   }
   
   /* This method receives a string and compares it to the identifier
      "promitive", returning true if they are equal and false otherwise */
   public static boolean isPrivate(String s)
   {
      if(s.equals("private"))
         return true;
      else
         return false;
   }
   
   /* This method receives a string and checks if its first character is a
      valid start character for a Java identifier, returning true if it is and
      false otherwise. */
   public static boolean isStartID(String s)
   {
      if(Character.isJavaIdentifierStart(s.charAt(0)))
         return true;
      else
         return false;
   }
   
   /* This method receives a string and checks if all of its characters are
      valid for a Java identifier. It returns true if so, and false otherwise.
    */
   public static int isID(String s)
   {
      int i;
      int index = -1;
      for(i = 0; i < s.length(); i++)
         if(!Character.isJavaIdentifierPart(s.charAt(i)))
            index = i;
      return index;
   }
}
