/*
 * Shaun Mbateng
 * CS 441: Sorenson
 * Calculator Project
 * Part 1 - January 23, 2014
 * Part 2 - February 25, 2014
 * Part 3 - March 2, 2014
 */
package CalcScan;

import java.io.*;
import java.util.*;

public class CalcProj
{
	static int pos; //Keep Position of ArrayList
	static String result; //Hold Final Result to be Displayed
	static Object [] variables = new Object [26];
	static double expr = 0;
	static int count = 0;
	static boolean accessed = false;
	
	public static void main(String [] args) throws IOException
	{
		Scanner kbd = new Scanner(System.in);
		Scanner cfile = null;
		String filename = null;
		String tokentype = null;
		String predict = null; //Hold Predictions
		ArrayList<String> tts = new ArrayList<String>(); //ArrayList to Hold Token Types
		ArrayList<Character> value = new ArrayList<Character>(); //ArrayList to Hold Characters
		boolean parsed = true; //Parsing Correct or Incorrect
		result = ""; //Hold Final Result(s)
		pos = -1; //Position of Element
		
		System.out.println("");
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
			char [] scanline = null; //Array to Hold Line
			
			while(cfile.hasNext() && parsed == true) //While There is Still Data and Parsing Has Been Done Correctly
			{
				String temp = cfile.nextLine(); //Get Line as String
				temp = temp.toLowerCase();
				int i = 0;
	
				if (temp.contains("//")) //Allow for Comments (Beginning With "//")
				{
					temp = temp.substring(0, temp.indexOf("//")); //Cut Everything After and Including "//"
				}
				if (temp.toLowerCase().contains("quit")) //If Contains Quit Command
				{
					if (temp.replace(" ","").equalsIgnoreCase("quit;")) //If Line is Only Quit Command and Semicolon
					{	
						tts = new ArrayList<String>();
						System.out.println("Quit Command");
						System.out.println("Semicolon: ;");
						tts.add("qc");
						tts.add("sc");
						pos = tts.size() - 1;
						parsed = parser(tts, predict);
						predict = prediction(scanline, i, value, tts, "sc"); //Get New Prediction
						break;
					}
					else
						parsed = false; //Incorrectly Parsed, Quit Must be on Own Line
				}
				else
				{
					value = new ArrayList<Character>(); //Reset value
					tts = new ArrayList<String>(); //Reset tts
					expr = 0; //Reset expr
					accessed = false;
					
					while (temp.contains(" ;")) //Remove Space Semicolon
							temp = temp.replace(" ;", ";");
					
					scanline = temp.toCharArray(); //Array to Hold Line
					
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
							{
								tokentype = "Real Number";
								
							}
							else
							{
								tokentype = "Integer";
							}
					
							if (i == scanline.length-1) //If Line Ends With Integer, Display value and Clear it
							{
								tts.add("num"); //Add num to tokentype array list
								pos = tts.size() - 1; //Set to Position of Last Element
								parsed = parser(tts, predict); //Check if Matches Prediction
								predict = prediction(scanline, i, value, tts, "num"); //Get New Prediction
								
								System.out.println(tokentype+": "+value.toString().replace(",", "").replace("[", "").replace("]", "").replace(" ", ""));
								
								if ((i < scanline.length - 2))// && scanline[i] != ' ')// && (scanline[i+1] != ';'))
									value.clear();
							}
						}
						else //If Token Isn't Integer
						{
							if (scanline[i] == 'e' && i != 0 && i != scanline.length-1) //If token is 'e', potentially for exponentiation
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
							if (value.isEmpty() == false && (tokentype.equalsIgnoreCase("Integer") || tokentype.equalsIgnoreCase("Real Number"))) //If Integer Value Exists, Display It, Clear value
							{
								tts.add("num"); //Add num to tokentype array list
								pos = tts.size() - 1; //Set to Position of Last Element
								parsed = parser(tts, predict); //Check if Matches Prediction
								predict = prediction(scanline, i, value, tts, "num"); //Get New Prediction
								System.out.println(tokentype+": "+value.toString().replace(",", "").replace("[", "").replace("]", "").replace(" ", ""));
								
								if ((i < scanline.length - 2))// && scanline[i] != ' ')// && (scanline[i+1] != ';'))
									value.clear();
							}
							
							//Depending on Character Type, tokentype is Set to Different String
							if (scanline[i] == ' ') //If Space, Continue to Next Character
								continue;
							else if (scanline[i] == ';')
							{
								tokentype = "Semicolon"; 
								tts.add("sc"); //Add sc to tokentype array list
								pos = tts.size() - 1; //Set to Position of Last Element
								parsed = parser(tts, predict); //Check if Matches Prediction
								predict = prediction(scanline, i, value, tts, "sc"); //Get New Prediction
							}
							else if (scanline[i]== '=')
							{
								tokentype = "Assignment";
								tts.add("eq"); //Add eq to tokentype array list
								pos = tts.size() - 1; //Set to Position of Last Element
								parsed = parser(tts, predict); //Check if Matches Prediction
								predict = prediction(scanline, i, value, tts, "eq"); //Get New Prediction
							}
							else if((scanline[i] >= 'A' && scanline[i] <= 'Z') || (scanline[i] >= 'a' && scanline[i] <= 'z'))
							{
								tokentype = "Variable";
								tts.add("var"); //Add var to tokentype array list
								pos = tts.size() - 1; //Set to Position of Last Element
								parsed = parser(tts, predict); //Check if Matches Prediction
								predict = prediction(scanline, i, value, tts, "var"); //Get New Prediction
							}
							else if(scanline[i] == '+' ||  scanline[i] == '-' ||  scanline[i] == '*' ||  scanline[i] == '/' ||  scanline[i] == '%' ||  scanline[i] == '^')
							{
								tokentype = "Operator";
								tts.add("op"); //Add op to tokentype array list
								pos = tts.size() - 1; //Set to Position of Last Element
								parsed = parser(tts, predict); //Check if Matches Prediction
								predict = prediction(scanline, i, value, tts, "op"); //Get New Prediction
							}
							else
							{
								System.out.println("Error: Invalid Token");
								break;
							}
							
							System.out.println(tokentype+": "+Character.toLowerCase(scanline[i])); //Display Token Type and Token
						}
					}
					
					if (i != scanline.length)
						break;
					else
						System.out.println("NEW LINE"); //Signify New Line
				}
			}
			
			cfile.close();
			
			if (parsed == true)
			{
				System.out.println("\nCorrectly Parsed.");
				System.out.println(result);
				
				/*for (int i = 0; i<variables.length; i++)
					System.out.print(variables[i]+" ");*/
			}
			else
				System.out.println("\nIncorrectly Parsed.");
		}
		
		kbd.close();
	}
	
	private static boolean parser(ArrayList<String> tts, String predict)
	{
		/*
		 * Symbols for Parser Predict
		 * qc = quit command
		 * sc = semi colon
		 * op = operator
		 * var = variable
		 * ex = expression
		 * num = number (Real or Integer)
		 * eq = Assignment Operator
		 * ln = Line
		 */
		
		boolean par = true;
		
		if (pos == 0) //If at Beginning
		{
			if (tts.get(pos).equalsIgnoreCase("eq") || tts.get(pos).equalsIgnoreCase("sc"))
				par = false; //Can't Start w/ Assignment or Semicolon
		}
		else //If in Middle or End
		{
			if (pos == tts.size() - 1) //If At End
			{
				if (!tts.get(pos).equalsIgnoreCase("sc"))
					return false; //Line Needs to End on Semicolon
			}
			
			if (!predict.toLowerCase().contains(tts.get(pos).toLowerCase())) //If Prediction Doesn't Match
				par = false;
		}
		
		return par;
	} 
	
	private static String prediction(char [] scanline, int z, ArrayList<Character> value, ArrayList<String> tts, String tok)
	{
		/*
		 * Symbols for Parser Predict
		 * sc = Semicolon
		 * op = Operator
		 * var = Variable
		 * ex = Expression
		 * num = Number (Real or Integer)
		 * eq = Assignment Operator
		 * ln = Line
		 */
		String predict = ""; //Start Empty

		//Change Prediction Depending on Current Token Type
		if (tok.equalsIgnoreCase("var"))
		{	
			if (pos == 0)
				predict = "eq"; //If var is first element in line, predict assignment
			else //Else, replace in tts, recall predict with ex
			{
				tts.remove(pos);
				tts.add("ex");
				pos = tts.size() - 1;
				predict = prediction(scanline, z, value, tts, "ex"); //Recall Predict
			}
		}
		if (tok.equalsIgnoreCase("op"))
			predict = "op var num";
		if (tok.equalsIgnoreCase("eq"))
			predict = "op num";
		if (tok.equalsIgnoreCase("num")) //If number, enter as expression, recall function
		{
			tts.remove(pos);
			tts.add("ex");
			pos = tts.size() - 1;
			predict = prediction(scanline, z, value, tts, "ex");
		}
		if (tok.equalsIgnoreCase("qc")) //If semicolon, enter as line, recall function
			predict = prediction(scanline, z, value, tts, "sc");
		if (tok.equalsIgnoreCase("sc")) //If semicolon, enter as line, recall function
			predict = prediction(scanline, z, value, tts, "ln");
		if (tok.equalsIgnoreCase("ex")) //If expression, and following operation and variable or operation and expression
		{
			if (pos >=2 && tts.get(pos-1).equals("ex") && tts.get(pos-2).equals("op"))
			{	
				if (accessed == false)
				{
					accessed = true;
					expr += expression(scanline, z, value, tts);
				}

				tts.remove(pos);
				tts.remove(pos-1);
				tts.remove(pos-2);
				tts.add("ex"); //Enter as expression, replace preceding operator and variable/expression
				pos = tts.size() - 1;
				predict = prediction(scanline, z, value, tts, "ex"); //Recall Predict
			}
			
			predict = "sc op num";
		}
		if (tok.equalsIgnoreCase("ln"))
		{
			predict = "";
			line(scanline, z, value, tts);
		}
		
		return predict;
	}
	
	private static void line(char [] scanline, int z, ArrayList<Character> value, ArrayList<String> tts)
	{	
		/*
		 * Symbols for Parser Predict
		 * sc = Semicolon
		 * op = Operator
		 * var = Variable
		 * ex = Expression
		 * num = Number (Real or Integer)
		 * eq = Assignment Operator
		 * ln = Line
		 */
		
		ArrayList<String> l1 = new ArrayList<String>(); //First Possibility of Line
		ArrayList<String> l2 = new ArrayList<String>(); //Second Possibility of Line
		ArrayList<String> l3 = new ArrayList<String>(); //Third Possibility of Line
		
		l1.add("ex");
		l1.add("sc"); //ln := <ex><sc>
		l2.add("var");
		l2.add("eq");
		l2.add("ex");
		l2.add("sc"); //ln := <var><eq><ex><sc>
		l3.add("qc");
		l3.add("sc"); //ln := <qc><sc>
		
		if (tts.equals(l1)) //If line matches l1, 
		{
			double tmp = expression(scanline, z, value, tts);
			
			result += "\n";
					
			if (expr == 0)
			{
				expr += tmp;
			}
			else
				tmp = expr;
			
			if (tmp == (int)tmp)
				result += Integer.toString((int)tmp);
			else
				result += Double.toString(tmp);
		}
		else if (tts.equals(l2))
		{
			double tmp = expression(scanline, z, value, tts);
			
			result += "\n";
			
			if (expr == 0)
			{
				expr += tmp;
			}
			else
				tmp = expr;
			
			if (tmp == (int)tmp)
				result += Integer.toString((int)tmp);
			else
				result += Double.toString(tmp);
			
			char var = Character.toLowerCase(scanline[0]);
			
			variables[var-'a'] = expr;
		}
		else if (tts.equals(l3))
		{
			result += "\nEnd of Program";
		}
	}
	
	private static double expression(char [] scanline, int z, ArrayList<Character> value, ArrayList<String> tts)
	{
		double val = 0;
		double val2 = 0;
		
		pos = tts.size() - 1; //Make Sure it Points to Last Item
		
		if (pos >=2 && (tts.get(pos-1).equals("ex")) && tts.get(pos-2).equals("op"))
		{
			String tmp = value.toString().replace(",", "").replace("[", "").replace("]", "").replace(" ", "");
			int t = z-1;
			char op;
			
			try
			{
				val += Double.parseDouble(tmp);
			}
			catch(java.lang.NumberFormatException n)
			{
				val += (double)variables[scanline[t]-'a'];
			}
			
			while (scanline[t] != '+' && scanline[t] != '-' && scanline[t] != '*' && scanline[t] != '/' && scanline[t] != '%' && scanline[t] != '^' && t>0)
			{
				t--;
			}
			
			op = scanline[t];
			scanline[t] = ' ';
			tmp = "";
			
			while(scanline[t] == ' ' && t<scanline.length)
			{
				t++;
			}
			
			while(scanline[t] != ' ' && t<scanline.length)
			{
				tmp += scanline[t];
				t++;
				//scanline[t-1] = ' ';
			}
			
			t--;
			
			try
			{
				val2 += Double.parseDouble(tmp);
			}
			catch(java.lang.NumberFormatException n)
			{
				val2 += (double)variables[scanline[t]-'a'];
			}
			
			if (op == '+')
				val2 += val;
			if (op == '-')
				val2 -= val;
			if (op == '*')
				val2 *= val;
			if (op == '/')
				val2 /= val;
			if (op == '%')
				val2 %= val;
			if (op == '^')
				val2 = Math.pow(val2, val);
			
			val = val2;
		}
		else
		{
			String tmp = value.toString().replace(",", "").replace("[", "").replace("]", "").replace(" ", "");

			try
			{
				val = Double.parseDouble(tmp);
			}
			catch(java.lang.NumberFormatException n)
			{
				val = (double)variables[scanline[z-1]-'a'];
			}
		}
		
		if (scanline[z] != ';')
			accessed = false;
		
		return val; 
	}
}
