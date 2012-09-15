/**
* This program represents a deck of cards, consisting of SIZE_DECK (52) cards,
* each with a rank between 1 and CARDS_RANK (13), and one of
* SIZE_DECK/CARDS_RANK = 4 suits: clubs, diamonds, hearts or spades. The class
* P4CardDeck represents the deck, containing cards of class P4Card. There are
* 4 possible operations to be made: generate  new deck, sort cards ascending,
* sort cards descending and shuffle. The program repeats until the user inputs
* EXIT (number 5)
**/

import java.text.DecimalFormat;                 //Convert do decimal format
import java.util.Random;                        //Generate random numbers for
                                                //shuffling
import java.util.Scanner;                       //Read input from keyboard

public class P4
{
   /* The main method is responsible for reading the inputs, checking their
      validity, calling the requested methods and exiting the program.
   */
   public static void main(String[] args)
   {
      final int NEW_DECK     = 1;               //Assign cards to deck
      final int ASCEND_SORT  = 2;               //Sort ascending
      final int DESCEND_SORT = 3;               //Sort descending
      final int SHUFFLE      = 4;               //Shuffle deck
      final int EXIT         = 5;               //Exit program
      
      Scanner scan = new Scanner(System.in);    //Read from keyboard
      int choice;                               //Choose operation
      P4CardDeck deck = new P4CardDeck();       //Instantiate deck

      do                                        //Repeat until input = EXIT
      {
         System.out.println("***********************");
         System.out.println("   DECK OF CARDS");
         System.out.println("   1) NewDeck");
         System.out.println("   2) AscendSort");
         System.out.println("   3) DescendSort");
         System.out.println("   4) Shuffle");
         System.out.println("   5) Exit");
         System.out.println("***********************");
         System.out.print("Enter 1 2 3 4 or 5: ");
         choice = scan.nextInt();               //Read input
         switch(choice)
         {
            case NEW_DECK:                      //Assign values to deck
               deck.setP4CardDeck();            //and print
               System.out.println(deck.toString());
               break;
            case ASCEND_SORT:                   //Sort ascending and print
                 System.out.println(deck.transpAscendSort().toString());
               break;
            case DESCEND_SORT:                  //Sort descending and print
                 System.out.println(deck.transpDescendSort().toString());
               break;
            case SHUFFLE:                       //Shuffle and print
                 System.out.println(deck.shuffle().toString());
               break;
            case EXIT:                          //Exit
               break;
            default:
               System.out.println("\nERROR! Enter a number between 1 and 5.");
         }
         System.out.println();
      }while(choice != EXIT);
   }//End main
}//End P4 class

class P4Card   //One card of the deck
{
   private int rank;                            //Card's rank
   private String suit;                         //Card's suit
   
   /*This constructor initializes the card to the default value: rank is 1 and
      suit is null*/
   public void setP4Card()
   {
      rank = 1;
      suit = null;
   }
   /*Assign values (rank and suit) to card, according to input arguments*/
   public void setP4Card(int r, String s)
   {
      rank = r;
      suit = s;
   }
   
   /*Return rank of the card*/
   public int getRank()
   {
      return(rank);
   }
   
   /*Return suit of the card*/
   public String getSuit()
   {
      return(suit);
   }
   
   /*Return a string with the rank and suit of the card*/
   public String toString()
   {
      DecimalFormat df = new DecimalFormat("00");//Convert to decimal format
      String cardNumString = "";                //Initialize empty string
      cardNumString += df.format(rank) + " of " + suit;//Add rank and suit
      return(cardNumString);
   }
}//End P4Card class

class P4CardDeck  //Deck of cards, with SIZE_DECK cards
{
   private final int SIZE_DECK   = 52;          //Number of cards in deck
   private final int CARDS_SUIT  = 13;          //Number of cards per suit
   private final String CLUBS    = "clubs   ";  //Suits
   private final String DIAMONDS = "diamonds";
   private final String HEARTS   = "hearts  ";
   private final String SPADES   = "spades  ";
   
   private P4Card[] deck = new P4Card[SIZE_DECK];//Array of cards
   private String[] asuit = {SPADES, DIAMONDS, CLUBS, HEARTS};
   
   /*This constructor initializes and sets values to all cards of the deck*/
   public P4CardDeck()
   {
      int i;
      
      for(i = 0; i <SIZE_DECK; i++)        //Initialize, assign rank and suit
      {
        deck[i] = new P4Card();
        deck[i].setP4Card(i % CARDS_SUIT + 1, asuit[i / CARDS_SUIT]);
      }
   }
   /*This method sets values to all cards of the deck*/
   public void setP4CardDeck()
   {
      int i;
      
      for(i = 0; i <SIZE_DECK; i++)             //Assign rank and suit
        deck[i].setP4Card(i % CARDS_SUIT + 1, asuit[i / CARDS_SUIT]);
   }
   
   /*This method returns a string containing the values (rank and suit) of all
      cards in the deck.
   */
   public String toString()
   {
      int i;
      String strDeck = "";
      
      for(i = 0; i < SIZE_DECK; i++)            //Add all cards to string
      {
         if(deck[i] == null || deck[i].getSuit() == null)//If any card is not
           return null;                                  //assigned, return null                                            
         if(i % 4 == 0) strDeck += "\n";        //Print a newline every 4 cards
            strDeck += deck[i].toString() + "   ";//Separate consecutive cards 
      }
      return(strDeck);
   }
   
   /* This method reorganizes cards in ascending order, also taking into
      account the alphabetical value of the suits. If any card has invalid
      value, this method returns null. Otherwise, it returns a reference to the
      object that called it.
   */
   public P4CardDeck transpAscendSort()
   {
      int i, j;
      P4Card swap = new P4Card();               //Auxiliary variable for swaps

      for(i = 0; i < SIZE_DECK; i++)
        if(deck[i] == null || deck[i].getSuit() == null)//If any card is not
          return null;                                  //assigned, return null
      
      for(i = 0; i < SIZE_DECK-1; i++)          //Sort in ascending order
         for(j = i + 1; j < SIZE_DECK; j++)
         {
            if(deck[i].getRank() > deck[j].getRank() ||
              (deck[i].getRank() == deck[j].getRank() &&
               deck[i].getSuit().compareTo(deck[j].getSuit()) > 0))
            {        //Swap if rank of i is higher than of j. If they are the
                     //same, compare suits. If i's suit is higher, also swap.
               swap.setP4Card(deck[j].getRank(), deck[j].getSuit());
               deck[j].setP4Card(deck[i].getRank(), deck[i].getSuit());
               deck[i].setP4Card(swap.getRank(), swap.getSuit());
            }
         }
      return this;
   }

   /* This method reorganizes cards in descending order, also taking into
      account the alphabetical value of the suits. If any card has invalid
      value, this method returns null. Otherwise, it returns a reference to the
      object that called it.
   */
   public P4CardDeck transpDescendSort()
   {
      int i, j;
     P4Card swap = new P4Card();               //Auxiliary variable for swaps
     for(i = 0; i < SIZE_DECK; i++)
        if(deck[i] == null || deck[i].getSuit() == null)//If any card is not
          return null;                                  //assigned, return null
	      
     for(i = 0; i < SIZE_DECK-1; i++)         //Sort in descending order
       for(j = i + 1; j < SIZE_DECK; j++)
       {
          if(deck[i].getRank() < deck[j].getRank() ||
            (deck[i].getRank() == deck[j].getRank() &&
             deck[i].getSuit().compareTo(deck[j].getSuit()) < 0))
          {          //Swap if rank of i is lower than of j. If they are the
                     //same, compare suits. If i's suit is lower, also swap.
             swap.setP4Card(deck[j].getRank(), deck[j].getSuit());
             deck[j].setP4Card(deck[i].getRank(), deck[i].getSuit());
             deck[i].setP4Card(swap.getRank(), swap.getSuit());
          }
       }
     return this;
   }
   
   /*This method shuffles the entire deck, swapping each card with another
      randomly chosen. If any card is not assigned, it returns null. Otherwise,
      it returns a reference to the object that called it.*/
   public P4CardDeck shuffle()
   {
      int i, r;
      P4Card swap;
      Random rand = new Random();               //Generate random numbers
      long seed;                                //Seed for random method
      
      seed = System.currentTimeMillis();        //Set seed to current time
      rand.setSeed(seed);

      for(i = 0; i < SIZE_DECK; i++)
        if(deck[i] == null || deck[i].getSuit() == null)//If any card is not
          return null;                                  //assigned, return null
      
      for(i = 0; i < SIZE_DECK; i++)            //Swap card i with any other
      {                                         //randomly chosen
         r = rand.nextInt(SIZE_DECK);
         swap = deck[i];
         deck[i] = deck[r];
         deck[r] = swap;
      }
      
      return this;
   }
}//End P4CardDeck class
