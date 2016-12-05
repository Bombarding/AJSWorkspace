//This is a guessing game between 1 and 100. The user must pick a number

import java.io.*;
import java.util.*;

class GuessGame
{
	static void intro()
	{
		System.out.println("**Welcome to the Guessing Game**");
		System.out.println("Tech Girls are Awesome!");
		System.out.println("Guessing Game By Kizr");
	}
	public static void main(String [] args)
	{
		intro();
		int Y = 1;
		do
		{
			int guess = 0;
			int number;
			number = (int)(1+20*Math.random());
			Scanner scan = new Scanner(System.in);
			System.out.println("I have my number, guess it");
			
			while(guess!=number)
			{
				guess = scan.nextInt();
				if(guess<1 || guess >20)
				{
					System.out.println("Try again");
				}
				else if(guess<number)
				{
					System.out.println("Too low, try again");
				}
				else if(guess>number)
				{
					System.out.println("Too high, try again");
				}
				else if(guess==number)
				{
					System.out.println("Congrats!");
				}
			}
			System.out.println("Want to play again? Type 1 or 0");
			Y = scan.nextInt();
			while(Y!=1 && Y!=0)
			{
				System.out.println("Try 1 or 0");
				Y = scan.nextInt();
			}
			if(Y==1)
			{
				System.out.println("Restarting");
			}
			if(Y==0)
			{
				System.out.println("Farewell!");
			}
			
		}while(Y==1);
		
		
	}
	
	
	
}