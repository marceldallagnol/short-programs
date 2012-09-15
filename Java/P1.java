/**
* This program displays a table of 7 years of federal taxes
* and earned income
**/
import java.util.Scanner;               //Scan input from keyboard
	
public class P1
{
   public static void main(String[] args)
   {
      final int MIN            = 1;     //Minimum wages/hour
      final int HRS_WEEK       = 45;    //45 work hours/week
      final int MAX_PAY        = 500;   //Paid MAX $500/hr
      final int WEEKS_IN_YR    = 52;    //Paid 52 weeks in year
      final int YEARS          = 7;     //7 years on the job!
      final double HR_RAISE    = 0.05;  //5% annual hurly raise

      final double B403_RATE1  = 0.10;  //10% defer retirement plan
      final double B403_RATE2  = 0.15;  //15% defer retirement plan
      final double B403_RATE3  = 0.20;  //20% defer retirement plan
      final double B403_MAX    = 17000; //Max contribution $17000 in 2011
      final double B403_MCD    = 10000; //Maximum common divisor between
                                        //403B bracket limits (30k and
                                        //80k). Used in the switch statement
										  
      final double TAX_RATE1   = 0.10;  //10% 0-$8700 income taxed by FED
      final double TAX_RATE2   = 0.15;  //15% to $35350 income taxed by FED
      final double TAX_RATE3   = 0.25;  //25% to $85650 income taxed by FED
      final double TAX_RATE4   = 0.28;  //28% to $178650 income taxed by FED
      final double TAX_RATE5   = 0.33;  //33% to $388350 income taxed by FED
      final double TAX_RATE6   = 0.35;  //35% > $388350 income taxed by FED
		
      final double TAX_BRACK1  = 8700;  //10% $8700 FED tax bracket 1 limit
      final double TAX_BRACK2  = 35350; //15% $35350 FED tax bracket 2 limit
      final double TAX_BRACK3  = 85650; //25% $85650 FED tax bracket 3 limit
      final double TAX_BRACK4  = 178650;//28% $178650 FED tax bracket 4 limit
      final double TAX_BRACK5  = 388350;//33% $388350 FED tax bracket 5 limit
		
      boolean inputErr = true;          //Input error flag
      char choice;                      //Reepeat loop

      int yr                   = 1;     //Start on year #1
      double hrWage            = 0;     //Input hourly wage
      double gross             = 0;     //Gross income for the year
      double inc               = 0;     //Reduced gross income (w/o 403B)
      double taxRate           = 0;     //Federal Tax rate form tax bracket
      double fedTax            = 0;     //Federal taxes paid
      double net               = 0;     //Net wages after taxes
      double b403              = 0;     //B403 deferred retirement income
		
      Scanner scan = new Scanner(System.in); //Read input from keyboard
      String inputStr = null;           //Input string
		
      do                                //Loop the entire program
      {
    	 inputErr = true;               //Reset flag to true every iteration
         do                             //Loop input scan until in valid range
         {
        	System.out.print("\nEnter hourly wage to display income:  ");
            hrWage = scan.nextDouble(); //Assign to double for hourly wage
            if(hrWage >= MIN && hrWage <= MAX_PAY) inputErr = false;
            else
			{
			  System.out.print("ERROR: Enter a number (1-500)");
              System.out.println("for hourly wages!");
			}
         }while(inputErr == true);     //Repeat scan if there is an input error
         
         System.out.print("\n************************** EARNED");//Print table
         System.out.println(" INCOME *************************");//header
         System.out.print(" Year      Gross Earnings     ");
         System.out.println("   403B       FedTax    Net Earnings");
         System.out.print("===============================");
		 System.out.println("===================================");
         for(yr = 1; yr <= YEARS; yr++)
         {                                   //Repeat 7 years with 5% raise
            fedTax = 0;                      //Reset tax to calculate next year
            gross = hrWage *                     
                    HRS_WEEK * WEEKS_IN_YR;  //Calculate gross earnings
            
            switch((int)(gross/B403_MCD))    //Divide gross earnings by
            {                                //maximum common divisor
               case 0:                       //between 403B bracket limits
               case 1:
               case 2:                       //For income 0-10k, 10k-20k,
                  taxRate = B403_RATE1;      //20k-30k, (case 0, 1 and 2,
                  break;                     //respectively), apply 403b rate 1
               case 3:
               case 4:
               case 5:												
               case 6:                       //Between 30-40, 40-50, 50-60
               case 7:                       //60-70, 70-80k (cases 3,4,5,6,7)
                  taxRate = B403_RATE2;      //apply 403b rate 2
                  break;											
               default:                      //Income greater than 80k
                  taxRate = B403_RATE3;      //Apply 403b rate 3
                  break;											
            }

            b403 = gross * taxRate;          //Calculate 403b. If value
            if(b403 > B403_MAX) b403 = B403_MAX; //is higher than
                                             //max, set to max

            inc = gross - b403;              //Calculate taxable income

            /* Beginning with the highest bracket, each 'if' statement compares
               the income with the next highest bracket limit. If the income is
               higher, the tax relative to the exceeding amount is calculated
               and added to the total. The income is then decremented to the 
               next highest backet limit for the process to repeat until the
               lowest bracket */
            if(inc > TAX_BRACK5)
            {
               fedTax = TAX_RATE6 * (inc - TAX_BRACK5);
               inc = TAX_BRACK5;
            }
			
            if(inc > TAX_BRACK4)
            {
               fedTax += TAX_RATE5 * (inc - TAX_BRACK4);
               inc = TAX_BRACK4;
            }
			
            if(inc > TAX_BRACK3)
            {
               fedTax += TAX_RATE4 * (inc - TAX_BRACK3);
               inc = TAX_BRACK3;
            }
			
            if(inc > TAX_BRACK2)
            {
               fedTax += TAX_RATE3 * (inc - TAX_BRACK2);
               inc = TAX_BRACK2;
            }
			
            if(inc > TAX_BRACK1)
            {
               fedTax += TAX_RATE2 * (inc - TAX_BRACK1);
               inc = TAX_BRACK1;
            }
			
            fedTax += TAX_RATE1 * inc;       //Calculate remaining taxes
            net = gross - b403 - fedTax;     //Deduct 403B and tax from gross
                                             //to find net earnings
            System.out.printf("%3d%19.2f%16.2f%12.2f",yr,gross,b403,fedTax);
            System.out.printf("%14.2f\n", net);//Print year's results
                                               //on the table
            
            hrWage *= 1 + HR_RAISE;            //5% raise per year
         }
		 
         System.out.print("=================================");//Print end of
         System.out.println("=================================");//table
         System.out.println("\nWant to calculate another tax table (y/n)?  ");
         inputStr = scan.next();              //Read and assign to string
         choice = inputStr.charAt(0);         //Get first character
      }while(choice != 'n' && choice != 'N'); //Repeat until input string
                                              //starts with n or N

      scan.close();                           //Close this scanner
      System.exit(0);                         //Close connections
   }
}
