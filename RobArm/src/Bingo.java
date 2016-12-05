/*
 * Shaun Mbateng
 * Bingo
 * This program is the classic game of bingo. No use inputs are needed,
 * 		the program handles everything. It has a time complexity of O(1).
 */

import java.io.File; //For User File With Numbers
import java.io.IOException; //Input Output Exception
import java.util.Arrays; //For Built In Array Functions
//import java.util.HashSet; //Using HashSet to Keep Track of Numbers
import java.util.Scanner; //For Inputs

public class Bingo 
{
	public static void main(String[] args) throws IOException
	{
		//Scanner cin = new Scanner(System.in); //For Integer Input
		Game Set = new Game(); //Class That Handles the Game
		Object	[][] bingoCard = Set.createCard(); //Original Unmarked Card
		Object	[][] playingCard = bingoCard; //Marked Card for Each Game
		int [] bingoNumbers; //Numbers to be Called
		
		/*
		//int nog; //Number of Games
		
		//Get Number of Games
		System.out.println("How many games to play?");
		nog = cin.nextInt();
		cin.close();
		bingoNumbers = new int [100*nog]; //Set Amount of Numbers to Call, Per Instructions in ReadMe
		//*/
		
		//Would Normally Not Do This. Would Make Only 50 Numbers at A Time Instead of 100,
			//but Description Asks for It
		bingoNumbers = new int [500]; //Set Amount of Numbers to Call, Per Instructions in ReadMe
		Scanner numbers = new Scanner(new File("Numbers.txt")); //File With Numbers
		int t = 0; //Traverse Array of Bingo Numbers
		while(numbers.hasNext()) //Till No More Numbers
		{
			String tmp = numbers.next(); //Get Number
			tmp = tmp.substring(0, tmp.length()-1); //Remove Delimiter
			bingoNumbers[t++] = Integer.parseInt(tmp); //Convert to Integer
		}
		numbers.close(); //Close Scanner, No Longer Needed
		
		/*
		HashSet<Integer> numbs = new HashSet<Integer>(); //HashSet to Keep Track of Already Used Numbers
		
		//Get Numbers, No DataSet Given With Numbers, So Picked At Random
		for (int i=0; i<bingoNumbers.length;)
		{	
			int random; //Number to Call
			do
				{random = (int)(Math.random()*100+1);} //Pick Random Number From Allowed Range
			while (numbs.contains(random)); //Make Sure for No Repeats
			
			bingoNumbers[i] = random; //Add Number to List of Numbers to Call
			numbs.add(random); //Add to List of Already Chosen
			
			if (++i%100==0) //Reset List Every 100 Numbers
				numbs = new HashSet<Integer>();
		}
		//*/
		
		//Play and Print Game(s)
		//for (int i=0; i<nog;)
		for (int i=0; i<6;)
		{
			Object [] res; //Results of Game
			int start = i*100; //Start Location
			int end = (++i)*100-1; //End Location
			int [] bn = Arrays.copyOfRange(bingoNumbers, start, end); //Get 100 Numbers From the Set at A Time
			res = Set.play(bn, playingCard); //Play Game
			printGame(playingCard, res, bn, i); //Print Result
			playingCard = bingoCard; //Reset Card
		}
	}
	static void printGame(Object [][] playingCard, Object [] res, int [] bn, int game) //Print the Result
	{	
		System.out.println(); //Print Line
		System.out.println("-Game "+game+"-"); //Print Game Number
		System.out.println("========"); //Print Line Break
		
		for (int i=0; i<7; i++) //Loop Through Rows
		{
			for (int j=0; j<5; j++) //Loop Through Columns
			{	
				switch (i) //Print Format Depending on Row
				{
					case 0: //Top Row
						System.out.print(String.format(" %-4s", playingCard[i][j])); //Print Column Headings
						break;
					case 1: //Second Row
						System.out.printf("%-5s", playingCard[i][j]); //Print Line Break
						break;
					default: //Playing Rows						
						if (playingCard[i][j] instanceof Integer) //If Integer (Number Not Called)
							System.out.print(String.format("%5s", playingCard[i][j]+"  ")); //Print Number
						else //String, Number Called
							System.out.print(String.format("%5s", playingCard[i][j]+" ")); //Print String
				}
			}
			
			System.out.println(); //End Line
		}
		
		System.out.println(); //Print Line
		System.out.println("The numbers called out during the game were..."); //Print Message
		System.out.print(bn[0]); //Print First Number Called
		
		for (int i=1; i<(int)res[1]; i++) //Loop From 1 To Last Number Called in Game
		{
			System.out.print(", "); //Print Comma
			if (i%25==0) //If 25 Numbers Have Been Called
				System.out.println(); //New Line
			System.out.print(bn[i]); //Print Number
		}
		
		System.out.print(".\n\n"); //End Line, Line Space
		
		//Print Result Along With Game Number and Amount of Numbers Called
		if(!(boolean)res[0]) //Losing Game
			System.out.println("This card was a LOSER in Game "+game+" because it didn't end the game within 50 numbers.");
		else //Winning Game
			System.out.println("This card was a WINNER in Game "+game+" because it needed only "+(int)res[1]+" numbers to end the game.");
		
		System.out.println("=============================================================="); //Line Break
	}
}