/*
 * This is the class that handles the Bingo game. 
 * It has a time complexity of O(1).
 */

import java.util.Arrays; //For Built In Array Functions
import java.util.HashSet; //Using HashSet to Keep Track of Numbers

class Game 
{
	Object [][] createCard() //Create Playing Card
	{
		Object [][] playingCard = new Object [7][5]; //Matrix of Objects
		playingCard[0][0] = "B"; //Place B
		playingCard[0][1] = "I"; //Place I
		playingCard[0][2] = "N"; //Place N
		playingCard[0][3] = "G"; //Place G
		playingCard[0][4] = "O"; //Place O
		Arrays.fill(playingCard[1], "---"); //Fill Second Row With Dashes (Em Dash)
		
		for (int i=0; i<5; i++) //Loop Through Rows
		{
			HashSet<Integer> colNumbs = new HashSet<Integer>(); //HashSet of Numbers Used
			int random = -1; //Random Number
			int min = 20*i+1; //Minimum Number in Range
			colNumbs.add(-1); //Add -1 to HashSet
			
			for (int j=2; j<7; j++) //Loop Through Columns
			{	
				while(colNumbs.contains(random)) //Pick Number Until Not Repeat
					random = (int)(Math.random()*20+min);
					
				playingCard[j][i] = random; //Place Number on Card
				colNumbs.add(random); //Add to List of Numbers
			}
		}
		
		//playingCard[4][2] = "*"; //Optional Free Space
		return playingCard; //Return Matrix
	}
	Object [] play(int [] numbers, Object [][] playingCard) //Function that Plays Game
	{
		Object [] result = new Object [2]; //Results of Game
		result[0] = false; //Assume Not Winning Till Win Shown
		result[1] = 50; //Position of Winning Number, Assume All Numbers Called Till Win Shown
		
		//for (int i=0; i<(numbers.length/2); i++)
		for (int i=0; i<50; i++) //Call 50 Numbers At A Time
		{
			int col = (numbers[i]-1)/20; //Get Possible Column of Number Location
			
			for (int j=2; j<7; j++) //Loop Through Rows of Column
			{
				if (playingCard[j][col].equals(numbers[i])) //If Numbers Match
				{
					playingCard[j][col]+="*"; //Place Star on Number
					
					if (i>2 && checkWin(j, col, playingCard)) //Start Checking for Win After 4th Number Called
					{
						result[0] = true; //Set True
						result[1] = i+1; //Place Location
						break; //Break Out of Loop
					}
				}
			}
			if ((boolean) result[0]) //If Game Has Been Won
				break; //Break Out of Loop
		}
		
		return result; //Return Game Results
	}
	private boolean checkWin(int row, int col, Object [][] playingCard) //Function That Checks Game Win
	{
		int t = -1; //Traverse Matrix
		
		//Check Row of Move for Win
		while (++t<5 && playingCard[row][t] instanceof String);
		if (t>4)
			return true;
		t = 1;
		//Check Column of Move for Win
		while (++t<7 && playingCard[t][col] instanceof String);
		if (t>6)
			return true;
		t = -1;
		//Check Diagonals if Move Falls in One
		if (row-col==2) //Up-Left to Down-Right
		{
			while (++t<5 && playingCard[t+2][t] instanceof String);
			if (t>4)
				return true;
		}
		t = -1;
		if (row+col==6) //Down-Left to Up-Right
		{
			while (++t<5 && playingCard[6-t][t] instanceof String);
			if (t>4)
				return true;
		}
		//Check Corners
		if (col==0)
		{
			switch (row)
			{
				case 2:
					if (playingCard[6][0] instanceof Integer)
						break;
				case 6:
					if (playingCard[2][0] instanceof Integer)
						break;
					if (playingCard[2][4] instanceof Integer)
						break;
					if (playingCard[6][4] instanceof Integer)
						break;
					return true;
			}
		}
		else if (col==4)
		{
			switch (row)
			{
				case 2:
					if (playingCard[6][4] instanceof Integer)
						break;
				case 6:
					if (playingCard[2][4] instanceof Integer)
						break;
					if (playingCard[2][0] instanceof Integer)
						break;
					if (playingCard[6][0] instanceof Integer)
						break;
					return true;
			}
		}
		
		return false; //If Here, Return False
	}
}