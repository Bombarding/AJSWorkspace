/*
Guessing Game Program
This Program Picks A Random Integer Between 1 and 100, and the User
	has to Guess it
*/

import java.io.*;
import java.util.*;

class GuessGame
{
	static void intro()
	{
		System.out.println("");
		System.out.println("**Guessing Game**");
		System.out.println("A number between 1 and 100 is going to be picked and you'll have to find it.");
		System.out.println("");
	}
	
	public static void main(String [] args)
	{
		intro();
		int Y = 1;
		do{
			int user = 0;
			int number;
			
			number = (int)(1+100*Math.random());
				
			Scanner scan=new Scanner(System.in);
			System.out.println("I've got my number. Try to guess it.");
			
			System.out.println(number);
			while (user!=number)
			{	
				user = scan.nextInt();
				
				if (user<1 || user>100)
				{
					System.out.println("That number isn't even in the range. How stupid are you? Try again. ");
					System.out.println("");
				}
				else if (user>number)
				{
					System.out.println("Too High.");
					System.out.println("Try Again");
					
				}
				else if (user<number)
				{
					System.out.println("Too Low.");
					System.out.println("Try Again");

					
				}
				else if (user==number)
				{
					System.out.println("You got the number. Nice!");
				}

				System.out.println("");
			}
			
			System.out.println("Would you like to play again? Type 1 for yes or 0 for no.");
	
			Y = scan.nextInt();
			
			while (Y!=1 && Y!=0)
			{
				System.out.println("That's not one of the options idiot. Type 1 or 0.");
				Y = scan.nextInt();
			}
			if (Y==1)
			{
				System.out.println("Alright!!!");
			}
			if (Y==0)
			{
				System.out.println("Boo! You stink!!!");
			}
			System.out.println("");
		}while (Y==1);
	}
}