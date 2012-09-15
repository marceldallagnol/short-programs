/**
* This program displays the calendar of a month between
* the years of 2000 and 3000, matching each day
* with the day of the week it falls under.
**/

import java.util.Scanner;
public class P2
{
   private static final int MIN_YR = 2000;      //Minimum year in range
   private static final int MAX_YR = 3000;      //Maximum year in range
   private static final int MIN_MONTH = 1;      //Minimum value for month (Jan)
   private static final int MAX_MONTH = 12;     //Maximum value for month (Dec)
   private static final int JAN = 1;            //January
   private static final int FEB = 2;            //February
   private static final int MAR = 3;            //March
   private static final int APR = 4;            //April
   private static final int MAY = 5;            //May
   private static final int JUN = 6;            //June
   private static final int JUL = 7;            //July
   private static final int AUG = 8;            //August
   private static final int SEP = 9;            //September
   private static final int OCT = 10;           //October
   private static final int NOV = 11;           //November
   private static final int DEC = 12;           //December
   
   public static void main(String[] args)
   {
   /*
   * This program receives as inputs: a year, number between
   * 2000 and 3000 (MIN_YR and MAX_YR); the number of a month
   * (1-12, representing Jan-Dec); a string indicating whether
   * the user wishes to repeat. It prints as output the calendar
   * of the month and year given, taking into account the offset of
   * the weekdays caused by the years past, including the effect of leap years.
   */
      final int WK_DAY_BS_YR = 6;               //Week day of January 1st
                                                //of the base year (0 is
                                                //Sunday, 6 is Saturday)
      final int DAYS_WK = 7;                    //Number of days in a week	
      
      boolean isLeap = false;                   //Flag(true in leap year)
      boolean inputErr;                         //Flag for input error
      char choice;                              //Repeat input
      int mon, year, days;                      //Month input, year imput
                                                //and number of days in month
      int i, j;                                 //Counters
      int offsetDays;                           //Number of week days offset
                                                //from the 1st day in mon
                                                //relative to WK_DAY_BS_YR
		
      Scanner scan = new Scanner(System.in);    //Scan inputs from keyboard
      String inputStr = null;                   //Input string
		
      do                                        //Repeat entire program
      {			
         do                                     //Repeat while
         {                                      //there is an input error
            inputErr = false;
            System.out.print("Enter year (" +MIN_YR+ "-" +MAX_YR+ ")");
            System.out.print("and month (1-12) to display calendar: ");
            year = scan.nextInt();
            mon = scan.nextInt();
            if(year < MIN_YR || year > MAX_YR)     //Year must be in range
            {
               System.out.println("ERROR! Enter year between "
                                  + MIN_YR + "-" + MAX_YR + ".");
               inputErr = true;
            }
            if(mon < MIN_MONTH || mon > MAX_MONTH) //Month must be in range
            {
               System.out.println("ERROR! Enter month between 1 and 12.");
               inputErr = true;
            }
         }while(inputErr == true);
			
         System.out.println("\n     ********************"+ year //Print year
                            +"********************");
         prtMonthTitle(mon);                    //Print month title and names
                                                //of weekdays
			
         if((year % 4 == 0 && year % 100 != 0)  //If year is not centesimal
          || year % 400 == 0) isLeap = true;    //and divisible by 4 or
                                                //by 400, it is a leap year
         else isLeap = false;

          /*
          Offset days will be calculated in 5 stages: 1st, calculate
          the offset from MIN_YR as if there were no leap years, adding one
          day per year; 2nd, adding the days past since the beginning of 
          year until the specified month; 3rd, adding the offsets caused by
          leap years between MIN_YR and year; 4th, adding the weekday of the
          first day of MIN_YR; and finally, keeping the remainder of the
          division by 4 to find the week day of the first day of the month.
          */

         offsetDays = year - MIN_YR;            //1st stage
         for(i = JAN; i < mon; i++)             //2nd stage
             offsetDays += getNumDaysInMonth(i,isLeap);

         if(MIN_YR % 4 == 0)                    //Setting the starting year for
            i = MIN_YR;                         //calculating the offset from
        else                                    //leap years. Since they may
            i = (MIN_YR - MIN_YR % 4) + 4;      //only happen from 4 to 4 years,
                                                //set i to next year evenly
                                                //divisible by 4 (if MIN_YR
                                                //isn't divisible by 4 itself)

        while(i < year)                         //3rd stage
        {
           if((i % 4 == 0 && i % 100 != 0) || i % 400 == 0)
              offsetDays++;
            i += 4;                             //Setting i to the appropriate
        }                                       //starting value, unnecessary
                                                //loops are eliminated (i+=4
                                                //instead of i++)

         offsetDays += WK_DAY_BS_YR;            //4th stage
         offsetDays %= DAYS_WK;                 //5th stage
                                                
         for(i = 0; i < offsetDays; i++)        //Print blank spaces
             System.out.print("       ");       //up to the day
         days = getNumDaysInMonth(mon,isLeap);  //Get number of days in
                                                //the specified month
         j = offsetDays + 1;
         for(i = 1; i <= days; i++)             //Print days of the month
         {
            System.out.printf("%7d",i);         //Print day i
            if(j % DAYS_WK == 0) System.out.println();//If Saturday,
                                                      //print newline after
            j++;
         }
         System.out.println("\n\nWant to see another month (y/n)?  ");
         inputStr = scan.next();                //Scan input string
         choice = inputStr.charAt(0);           //Get first character
      }while(choice != 'n' && choice != 'N');   //Repeat program until user
                                                //input starts with 'n' or 'N'
   }
	
   public static void prtMonthTitle(int month)
   {
   /*
   This method receives the number of a month (1-12)
   and prints its name on the screen.
   */
      switch(month)                             //Print month (Jan-Dec)
      {                                         //corresponding to month
         case JAN:
            System.out.print("     *******************");
            System.out.println("JANUARY******************");
            break;
         case FEB:
        	System.out.print("     ******************");
         System.out.println("FEBRUARY*****************");
            break;
         case MAR:
        	System.out.print("     *******************");
         System.out.println("MARCH*******************");
            break;
         case APR:
        	System.out.print("     ********************");
         System.out.println("APRIL*******************");
            break;
         case MAY:
        	System.out.print("     *********************");
         System.out.println("MAY********************");
            break;
         case JUN:
        	System.out.print("     ********************");
         System.out.println("JUNE********************");
            break;
         case JUL:
        	System.out.print("     ********************");
         System.out.println("JULY********************");
            break;
         case AUG:
        	System.out.print("     *******************");
         System.out.println("AUGUST*******************");
            break;
         case SEP:
        	System.out.print("     ******************");
         System.out.println("SEPTEMBER****************");
            break;
         case OCT:
        	System.out.print("     *******************");
         System.out.println("OCTOBER******************");
            break;
         case NOV:
        	System.out.print("     ******************");
         System.out.println("NOVEMBER******************");
            break;
         case DEC:
        	System.out.print("     ******************");
         System.out.println("DECEMBER******************");
            break;
         default:
            System.out.println("ERROR! Invalid argument in prtMonthTitle().");
                                          //Range check makes default
      }                                   //impossible, but may be useful
                                          //for debugging (memory error?)

      System.out.print("     SUN    MON    TUE    "); //Print days of
      System.out.println("WED   THU    FRI    SAT");  //the week in columns
   }

   public static int getNumDaysInMonth(int month, boolean isLeapYr)
   {
   /*
   This method receives the number of a month (1-12)
   and a flag indicating whether or not it is a leap year.
   It returns the number of days of that month, taking into
   account the value of this flag in case the month is February.
   */
      switch(month)                      //Return number of days of month
      {                                  //corrensponding to variable month
         case JAN:
         case MAR:
         case MAY:
         case JUL:
         case AUG:
         case OCT:
         case DEC:
            return 31;                  //Months with 31 days
         case APR:
         case JUN:
         case SEP:
         case NOV:
            return 30;                  //Months with 30 days
         case FEB:
            if(isLeapYr) return 29;     //February has 29 days if it is a
            else return 28;             //leap year and 28 otherwise
         default:
            System.out.print("ERROR! Invalid argument");
            System.out.println("in getNumDaysInMonth().");
            return -1;                 //Range check makes default
      }                                //impossible, but may be useful
                                       //for debugging (memory error?)
   }
}
