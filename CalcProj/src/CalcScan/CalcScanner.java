/*
 * Shaun Mbateng
 * CS 441: Sorenson
 * Calculator Project Part 1: Scanner
 * January 23, 2014
 */
package CalcScan;
import java.io.*;
import java.util.*;

public class CalcScanner 
{
	public static void main(String [] args) throws IOException
	{
		Scanner kbd = new Scanner(System.in);
		Scanner cfile = null;
		String filename = null;
		String tokentype = null;
		Boolean parse = true;
		
		System.out.println("***Welcome User!!!***"); //Welcome User
		System.out.println("Enter the name of the file to be scanned (including extension)."); //Get File Name
		System.out.println("");
		filename = kbd.nextLine();
		System.out.println("");
		
		try //Try Locating File
		{
			cfile = new Scanner(new FileReader(filename));
		}
		catch (IOException n)
		{
			System.out.println("Error: File Not Found"); //If File Doesn't Exist
		}
		
		if (cfile != null) //Only Search if File Isn't Empty
		{
			while(cfile.hasNext())
			{
				String temp = cfile.nextLine(); //Get Line as String
				int i = 0;
				
				if (temp.contains("//")) //Allow for Comments (Beginning With "//")
				{
					temp = temp.substring(0, temp.indexOf("//")); //Cut Everything After and Including "//"
				}
				
				if (temp.replace(" ","").equalsIgnoreCase("quit;")) //If Token is Quit Command, Ignoring Case and Spaces
				{
					System.out.println("Quit Command");
					System.out.println("Semicolon: ;");
					break;
				}
				else
				{
					ArrayList<Character> value = new ArrayList<Character>(); //Create ArrayList to Hold Value
					char [] scanline = temp.toCharArray(); //Array to Hold Line
					
					for (i=0; i<scanline.length; i++)
					{
						if ((scanline[i] >= '0' && scanline[i] <= '9') || scanline[i] == '.') //If a number, add to value ArrayList (Including Real Numbers)
						{	
							if (scanline[i] == '.')
							{
								if (value.contains('.'))
								{
									System.out.println("Error: Invalid Input");
									break;
								}
							}
							value.add(scanline[i]);
							
							if (value.contains('.'))
								tokentype = "Real Number";
							else
								tokentype = "Integer";
							
							if (i == scanline.length-1) //If Line Ends With Integer, Display value and Clear it
							{
								System.out.println(tokentype+": "+value.toString().replace(",", "").replace("[", "").replace("]", "").replace(" ", ""));
								value.clear();
							}
						}
						else //If Token Isn't Integer
						{
							if (scanline[i] == 'e' && i != 0 && i != scanline.length-1) //If token is 'e', potentially for exponentation
							{
								if (value.contains('e'))
								{
									System.out.println("Error: Invalid Input");
									break;
								}
								
								if ((scanline[i-1] >= '0' && scanline[i-1] <= '9') && (scanline[i+1] >= '0' && scanline[i+1] <= '9')) //If previous and next values are integers
								{	
									value.add(scanline[i]);
									continue;
								}
							}
							if (value.isEmpty() == false) //If Integer Value Exists, Display It, Clear value
							{
								System.out.println(tokentype+": "+value.toString().replace(",", "").replace("[", "").replace("]", "").replace(" ", ""));
								value.clear();
							}
							
							//Depending on Character Type, tokentype is Set to Different String
							if (scanline[i] == ' ') //If Space, Continue to Next Character
								continue;
							else if (scanline[i]== ';')
								tokentype = "Semicolon"; 
							else if (scanline[i]== '=')
								tokentype = "Assignment";
							else if((scanline[i] >= 'A' && scanline[i] <= 'Z') || (scanline[i] >= 'a' && scanline[i] <= 'z'))
								tokentype = "Variable";
							else if(scanline[i] == '+' ||  scanline[i] == '-' ||  scanline[i] == '*' ||  scanline[i] == '/' ||  scanline[i] == '%' ||  scanline[i] == '^')
								tokentype = "Operator";
							else
							{
								System.out.println("Error: Invalid Token");
								break;
							}
							
							System.out.println(tokentype+": "+scanline[i]); //Display Token Type and Token
						}
					}
					
					if (i != scanline.length)
						break;
					else
						System.out.println("NEW LINE"); //Signify New Line
				}
			}
			
			cfile.close();
			
			if (parse == true)
				System.out.println("Correctly Parsed.");
			else
				System.out.println("Incorrectly Parsed.");
		}
		
		kbd.close();
	}
}

