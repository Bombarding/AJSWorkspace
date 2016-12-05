

import java.io.*;

import java.util.*;
import java.util.Scanner;


class parser {

	
	public static int position;
	
	/**
	@param str the string being tested
	checks to see if the imported string is an integer
	If one of these is found the expression is true
	*/
	public static boolean isInteger(String str) {
    try {
        Integer.parseInt(str);
        return true;
    } catch (NumberFormatException nfe) {
        return false;
    }
	}
	
	/**
	@param str the string being tested
	checks to see if the imported string is an arithmetic operation
	+, -, /, *, %, or ^
	If one of these is found the expression is true
	*/
	public static boolean isArthOp (String str)
	{
		if(str.indexOf('+') !=-1 || str.indexOf('-') !=-1 || str.indexOf('*') !=-1 || 
							str.indexOf('/') !=-1 || str.indexOf('%') !=-1 ||str.indexOf('^') !=-1)			
			return true;			
		else	
			return false;
	}
	
	/**
	@param str the string being tested
	checks to see if the imported string is an =
	If one of these is found the expression is true
	*/
	public static boolean isAssOp (String str)	
	{
		if(str.indexOf('=') !=-1)			
			return true;			
		else	
			return false;
	}
	
	/**
	@param str the string being tested
	checks to see if the imported string is a ;
	If one of these is found the expression is true
	*/
	public static boolean isSemi (String str)
	{
		if(str.indexOf(';') !=-1)			
			return true;			
		else	
			return false;
	}
	
	/**
	@param str the string being tested
	checks to see what comes before the semicolon
	*/
	public static String getbegin(String str)
	{
		int end=str.indexOf(';');
		String thing = str.substring(0,end);
		return thing;
	}
	
	/**
	@param str the string being tested
	checks to see what comes after the semicolon
	*/
	public static String getend (String str)
	{
		int begin=str.indexOf(':');
		int end = str.length();
		String thing = str.substring(begin+1,end);
		thing = thing.trim();
		return thing;
	}
	
	
	
	/**
	@param array The array of tokens from the main program
	*/
	public static boolean expression(String [] array)
	{
		
		
		/**
		Checks to see if the information in that array position is a variable
		*/
		if (getbegin(array[position]).equals("Variable"))
			return true;
		/**
		Checks to see if the information in that array position is an integer
		*/
		else if (getbegin(array[position]).equals("Integer"))
			return true;
			
		/**
		Checks to see if the information in that array position is an operator
		*/
		else if (getbegin(array[position]).equals("Operator"))
		{
			/**
			creates two new recursive calls, first and second that help to determine if
			every operator has a 2 variables, 2 ints, or a variable and int matched with 
			it
			*/
			position++;
			boolean first = expression(array);
			position++;
			boolean second = expression(array);
			
			if (first && second)
			{
				return true;
			}
			
			
			
			else 
				return false;
		
		}
		/**
		If none of these are found it is invalid, and the program quits
		*/
		else
		{
			System.out.println("I was expecting a variable, integer, or an operator");
			System.exit(0);
			return false;
		}
		
			
	}
	
	/**
	@param array The array of tokens from the main program
	*/
	public static void parse(String [] array)
	{
		position=0;
		/**
		@param length the number of items in the array
		*/
		int length=array.length;
		/**
		If the first item in the array is a variable
		*/
		if(getbegin(array[position]).equals("Variable"))
		{
			/**
			makes sure that the item isn't also the last item
			*/
			if(position!=length-1){
				position++;
				/**
				determines if the next item is an =s
				*/
				if(getbegin(array[position]).equals("Assignment"))
				{
				/**
				Updates the position to get the item after the =
				*/
				position++;
				if(expression(array))
				{
				/**
				makes sure you are not already at the end of the array
				*/
				if(position!=length-1)
				{
					position++;
					/**
					Checks to see if the next item is a ;
					*/
					if(getbegin(array[position]).equals("Semicolon"))
					{
						/**
						if the semicolon is the end of the array then the line has 
						been parsed correctly
						*/
						if(position==length-1)
						{
							System.out.println("Correctly Parsed"+"\n");
						}
						
					
					
							position++;
							/**
							if it is not the end of the line the position is updated, and there is a check if the 
							item after the ; is a -1, which is what all unused spots in the array are filled with
							*/
							if (array[position].equals("-1"))
							{
								System.out.println("Correctly Parsed"+"\n");
							}
							/**
							if there is not a negative one after the semicolon is in the wrong place, and
							the program exits
							*/
							else
							{
								System.out.println("Invalid semicolon placement");
								System.exit(0);
							}
						}
					}
					
					/**
					if there is not a semicolon at the end of the line it is invalid, and the program exits
					*/
					else if (!getbegin(array[position]).equals("Semicolon"))
					{
						System.out.println("I was expecting a semicolon");
						System.exit(0);
					}
				}
				/**
				if you are at the end of the array, you are missing a semicolon
				so the program exits
				*/
				else 
				{
					System.out.println("I was expecting a semicolon");
					System.exit(0);
				}
				
				
				}	
				/**
				if there is not an operator, variable, or integer the line is invalid, and
				the program exits
				*/
				else
				{
					System.out.println("I had a vairable and an equals, I was expecting"+
					" an operator, variable, or integer.");
					System.exit(0);
				}
			
			}
			/**
			a variable at the beginning of a line can only be follwed by an equals sign
			if there is not one then the line is invalid
			*/
			else
			{
				System.out.println("I had a variable, and a was expecting an '='");
				System.exit(0);
			}
			}
			
			/**
			If it is the last item the line is invalid
			and the program quits
			*/
			else
			{
				System.out.println("I had a variable, but nothing after, this is not valid");
				System.exit(0);
			}
		
		
		
		
		 if(getbegin(array[position]).equals("Operator"))
		{
			/**
			if the first character is an operator it must be an expression			
			*/
			if(expression(array))
			{
			/**
			if you are not at the end of the line update the position and check for semicolons
			*/
			if(position!=length-1)
			{
				position++;
				if(getbegin(array[position]).equals("Semicolon"))
				{
					/**
					if you are at the end and have a semicolon the line has been correctly parsed
					*/
					if(position==length-1)
					{
						System.out.println("Correctly Parsed"+"\n");
					}
					else if (position!=length-1){
						
						
						position++;
						/**
						if the item after the semicolon is a -1, then the line has been correctly parsed
						*/
						if (array[position].equals("-1"))
						{
							
							System.out.println("Correctly Parsed"+"\n");
							
						}
						/**
						if it isn't then the line is invalid and the programs exits
						*/
						else
						{
							System.out.println("Invalid semicolon placement");
							System.exit(0);
						}
					}
					
				}
				/**
				if the line doesn't end in a semicolon then it is invalid and the program exits
				*/
				else if (!getbegin(array[position]).equals("Semicolon"))
				{
					System.out.println("I was expecting a semicolon");
					System.exit(0);
				}
			}
			/**
			if there isn't a semicolon the line is invaldi and the program exits
			*/
			else
			{
				System.out.println("I was expecting a semicolon");
				System.exit(0);
			}
			}
			
			/**
			if it is not an expression the line is invalid and the program exits
			*/
			else 
			{
				System.out.println("I had an operator, and I was expecting another operator"+
				", an integer, or a variable");
				System.exit(0);
			}
		}
		/**
		a line that is only a semicolon is valid
		*/
		else if(getbegin(array[position]).equals("Semicolon"))
					{
						/**
						if there is only the semicolon the line is valid
						*/
						if(position==length-1)
						{
							System.out.println("Correctly Parsed"+"\n");
						}
						
						else if (position!=length-1){
							position++;
							/**
							if the semicolon is followed by -1, then it is valid
							*/
							if (array[position].equals("-1"))
							{
								System.out.println("Correctly Parsed"+"\n");
							}
							/**
							if there is not a -1 after the line is invalid and the program exits
							*/
							else
							{
								System.out.println("Invalid semicolon placement");
								System.exit(0);
							}
						}
					}
		
		
		else if (getbegin(array[position]).equals("quit"))
		{
			if(position!=length-1)
			{
			position++;
			if(getbegin(array[position]).equals("Semicolon"))
					{
						/**
						if there is only the semicolon the line is valid
						*/
						if(position==length-1)
						{
							System.out.println("The program has quit");
							System.exit(0);
						}
						
						else if (position!=length-1){
							position++;
							/**
							if the semicolon is followed by -1, then it is valid
							*/
							if (array[position].equals("-1"))
							{
								System.out.println("The program has quit");
								System.exit(0);
							}
							/**
							if there is not a -1 after the line is invalid and the program exits
							*/
							else
							{
								System.out.println("Invalid semicolon placement");
								System.exit(0);
							}
						}
					}
			}
			/**
			If there is anything other than a semicolon after quit, it is invalid and the program
			exits.
			*/
			else{
				System.out.println("Invalid use of quit, I was expecting a semicolon");
				System.exit(0);
			}
			
		}
		/**
		if there is not a semicolon, variable, or operator. The line is invalid,
		and the program exits
		*/
		else {
			System.out.println("I was expecting a semicolon, variable, or operator");
			System.exit(0);
		}
			
	
	}
	


	public static void main(String [] args)
			throws IOException
		{
			String fl;
			int count = 0;
			int linenum=0;
			String temp;
			String temp2;
			int numint=0;
			char vartest;
			String answer=" ";
	
			/**
			Reads in the file, and then sets the String fl equal to the top line of the file
			this process is repeated until there are no more lines in the file.
			*/
			Scanner datafile=new Scanner(new FileReader("sample.txt"));	
			while(datafile.hasNextLine()){
				fl=datafile.nextLine();
				/**
				to avoid confusion the enitre line is forced to lowercase
				*/
				fl=fl.toLowerCase();
				
				int length=fl.length();
				/**
				allocates an array of the size the line
				*/
				String [] array;	
				array = new String[length];
				/**
				fills the array with -1s, so that it can be determined whether or not the end of the line has been reached
				*/
				Arrays.fill( array, "-1");
				/**
				The string fl is broken down into its individual characters, 
				and these are tested against the conditions shown above
				*/
				for (int i=0; i<length; i++)
				{
					temp  = fl.substring(i+numint,i+numint+1);
					numint=0;
					vartest = temp.charAt(0);
					
					/**
					@param vartest the character being tested
					checks to see if vartest is a letter
					*/
					if(Character.isLetter(vartest))
					{
						/**
						Since I do not allow multi-character variables, I can
						check to make sure that 'q' is actually a variable
						and not part of the word quit like so
						*/
						if (vartest == 'q')
						{
							temp=fl.substring(i+1,i+4);
							vartest = temp.charAt(0);
							if (temp.equals("uit"))
								{
									/**
									If there is quit is present then the program will pass it along to the parser
									*/
									System.out.println("quit");
									array[count]="quit: quit";
									count++;
									i=i+3;
								}
							else
							{
								answer="Variable: q";
								array[count]=answer;
								count++;
								System.out.println(answer);
							}
							
						}
						
						else
						{	
							answer="Variable: "+vartest;
							array[count]=answer;
							count++;
							System.out.println(answer);
						}
					}
					/**
					@param temp the string being tested
					checks to see if temp is an =, and if it is
					it will be printed, if not the program will move to the
					if statement
					*/
					else if( isAssOp(temp))
					{
						answer="Assignment: "+temp;
						array[count]=answer;
						count++;
						System.out.println(answer);
					}
					
					/**
					@param temp the string being tested
					checks to see if temp is an arithmetic operation
					*/
					else if (isArthOp(temp))
					{
						answer="Operator: "+temp;
						array[count]=answer;
						count++;
						System.out.println(answer);
					}
					
					/**
					@param temp the string being tested
					checks to see if temp is an integer
					if it is there is then a check to see if the next character
					is an integer also, this continues until the end of a line
					or a non-integer character is found
					*/
					else if (isInteger(temp))
					{
						for(int x=0; x<length; x++)
						{
						temp2=fl.substring(i+x+1,i+x+2);
						if (isInteger(temp2))
							temp=temp+temp2;
						else 
							break;
						}
						numint = temp.length()-1;
						answer="Integer: "+temp;
						array[count]=answer;
						count++;
						System.out.println(answer);
					}
					
					/**
					@param temp the string being tested
					checks to see if temp is a ;
					*/
					else if (isSemi(temp))
					{
						answer="Semicolon: "+temp;
						array[count]=answer;
						count++;
						System.out.println(answer);
					}
					
					/**
					@param vartest 
					if the program gets here, there are only two remaining 
					possibility, the character in question is a space
					or it is invalid, so as long as the character is not 
					a space the program will give an error message and end.
					*/
					else if (vartest!=' ')
						{
							System.out.print("That is not a valid character"+"\n");
							System.exit(0);
						}
					
				}
				
				System.out.println();
				parse(array);
				System.out.println("NEW LINE"+"\n");
				
				count=0;
				linenum+=1;
			
			}
		}
		
		
}


/**
Variable: a
Assignment: =
Integer: 3
Semicolon: ;

Correctly Parsed

NEW LINE

Operator: +
Integer: 5
Integer: 31
Semicolon: ;

Correctly Parsed

NEW LINE

Variable: b
Assignment: =
Operator: +
Operator: *
Variable: a
Integer: 4
Operator: -
Integer: 5
Integer: 4
Semicolon: ;

Correctly Parsed

NEW LINE

Semicolon: ;

Correctly Parsed

NEW LINE

quit
Semicolon: ;

The program has quit

*/