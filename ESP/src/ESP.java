/*

Project 2 - Exam Statistics Program
CS 248
Janurary 21, 2014
*/
import java.io.*;
import java.util.*;

class ESP
{
	static void intro() //Required Function: Displayed Before User Input. Introduction to Program
	{
		System.out.println("");
		System.out.println("**Exam Statistics Program**");
		System.out.println("This Program Sorts A List of Grades, Then Gives the Maximum, Minimum,");
		System.out.println("Median, and Average Score. It Also Gives the Number of Scores and");
		System.out.println("Letter Grades.");
		System.out.println("");
	}
	
	
	public static void main(String [] args) throws IOException
	{
		intro(); //Calls Required Function
		String infilename;
		Scanner kbd = new Scanner(System.in);
		System.out.print("Please enter the name of your data file: ");
		infilename = kbd.nextLine(); //Files in Data File Put Into String
		System.out.println("");
		Scanner gradefile = new Scanner(new FileReader(infilename));
		int numgrade = gradefile.nextInt(); //Data File Changed Into Integer
		int [] Grades = new int[numgrade]; //Data File Put Into Array
		int A = 0; //A Count Starts at Zero
		int B = 0; //B Count Starts at Zero
		int C = 0; //C Count Starts at Zero
		int D = 0; //D Count Starts at Zero
		int F = 0; //F Count Starts at Zero
		int sort; //Temporary Integer to Sort Scores
		for(int i=0; i<Grades.length; i++)
			Grades[i] = gradefile.nextInt(); //Reads Array One Line at A Time
		int min = 1000; //Sets Minimum Very High, Odds Are No Score Will Match This
		int max = 0; //Sets Max to Lowest Possible
		int med = 0; //Median Integer
		int mid = Grades.length/2; //This is the Position that's Halfway Between the Full Array Length
		double sum = 0; //Sum of Scores Set to Zero
		for(int i=0; i<Grades.length; i++)
		{
			if (Grades[i]<60)
				F++; //If Score Being Read is < 60, F Count Goes Up 1
			else if (60<=Grades[i] && Grades[i]<70)
				D++; //If Score Being Read is < 70, and >= 60 D Count Goes Up 1
			else if (70<=Grades[i] && Grades[i]<80)
				C++; //If Score Being Read is < 80, and >= 70 C Count Goes Up 1
			else if (80<=Grades[i] && Grades[i]<90)
				B++; //If Score Being Read is < 90, and >= 80 B Count Goes Up 1
			else if (Grades[i]>=90)
				A++; //If Score Being Read is >= 90 A Count Goes Up 1
			sum = Grades[i]+sum; //Finds the Sum of All the Scores
			if (Grades[i]<min)
				min = Grades[i]; //Makes Score Being Read the New Minimum if It's Smaller Than the Current Minimum
			if (Grades[i]>max)
				max = Grades[i]; //Makes Score Being Read the New Maximum if It's Greater Than the Current Maximum
		}
		for (int i=0; i<Grades.length; i++)
		{
			for (int j=0; j<Grades.length; j++)
			{
				if (Grades[j]<Grades[i])
				{
					//Perform Swap
					sort = Grades[i];
					Grades[i] = Grades[j];
					Grades[j] = sort;
					//Sorts Scores from Highest to Lowest
				}
				if (mid*2==Grades.length)
				{
					med = (Grades[mid]+Grades[mid-1])/2;
					/*
					If Array Length is Even, the Average is Taken of the Score Whose 
						Position is Half of the Array of Length, and the Score That is Before It.
						That Average Becomes the Median.
					*/
				}
				else if (mid*2!=Grades.length)
				{
					med = Grades[mid+1];
					/*
					If Array Length is Odd, the Median is the Score Whose Position is One More
						Than the Score Whose Postion is is Half of the Array Length.
						(Mid is an Integer, so Value of Grades.length Gets Rounded Down. To Find
							Median, Take Score Whose Postion is One More Than the Mid Value)
					*/
				}
			}
		}
		int numscore = A+B+C+D+F; //Adds Up Amount of Each Score. This Equals the Amount of Scores There Are, Which is Used to Find the Average
		double avg = sum/numscore; //Finds the Average of the Scores
		System.out.println("Minimum Score:"+min); //Displays Minimum
		System.out.println("Median Score:"+med); //Displays Median
		System.out.println("Maximum Score:"+max); //Displays Maximum
		System.out.println("Average Score:"+avg); //Displays Average
		System.out.println("");
		System.out.println("Number of Scores by Letter Grade:");
		System.out.println("A:"+A); //Displays A Count
		System.out.println("B:"+B); //Displays B Count
		System.out.println("C:"+C); //Displays C Count
		System.out.println("D:"+D); //Displays D Count
		System.out.println("F:"+F); //Displays F Count
		System.out.println("There are "+numscore+" Scores."); //Gives Number of Scores
	}
}