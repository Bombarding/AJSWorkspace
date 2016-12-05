import java.io.*;
import java.util.*;

public class MyScanner
{
	public static String getFile()
	{
		Scanner kbd  =  new Scanner(System.in);
		
		System.out.println("");
		System.out.println("*** Welcome User!!! ***"); //Welcome User
		System.out.println("");
		System.out.println("Enter the name of the file to be scanned (including extension)."); //Get File Name
		System.out.println("");
		
		return kbd.nextLine();
	}
	
	public static void invalid(char c, int i, String line)
	{
		System.out.println("Error: illegal character: '"+c+"' Position "+i+":\n"+line);
		System.exit(0);
	}

	public static Token [] doScan(String line)
	{
		Token [] tokens;
		int tpos = 0;
		
		if (line.contains("//"))
			line = line.substring(0, line.indexOf("//")); //Cut Everything After and Including "//"
		
		tokens = new Token[line.length()]; // overkill
		
		for(int i = 0; i<line.length(); i++)
		{
			char c = line.charAt(i);

			// integer or real number
			if(('0'<=c && c<='9') || c == '.')
			{
				int j;
				int pcount = 0;
				int ecount = 0;
				boolean real = false;
				
				for(j = i; j<line.length() && (('0'<=c && c<='9') || c == '.' || c == 'e'); j++)
				{
					c = line.charAt(j);
					
					if (c == '.' || c == 'e')
					{
						real = true;
						
						if (c == '.')
							pcount++;
						if (c == 'e')
							ecount++;
						
						if (pcount>1 || ecount>1)
							invalid(c, i, line);
					}
				}

				if (real == true)
					tokens[tpos++] = new Token(line.substring(i,j),Token.REAL);
				else
					tokens[tpos++] = new Token(line.substring(i,j),Token.INT);
				
				i = j-1;
			}
			// operators
			else if(c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^')
				tokens[tpos++] = new Token(""+c,Token.OP);
			// assignment
			else if(c == '=') 
				tokens[tpos++] = new Token("=",Token.ASSIGN);
			// semicolon
			else if(c == ';')
				tokens[tpos++] = new Token(";",Token.SEMI);
			// quit
			else if(i+4<=line.length() && line.substring(i,i+4).equalsIgnoreCase("quit"))
			{
				tokens[tpos++] = new Token("QUIT",Token.QUIT);
				i += 3; // skip rest of token
			}
			// variable
			else if('a'<=c && c<='z' || 'A'<=c && c<='Z')
				tokens[tpos++] = new Token(line.substring(i,i+1),Token.VAR);
			// else fail
			else if(c == ' ' || c == '\t' || c == '\n' || c == '\0') 
				continue;
			else
			{
				invalid(c, i, line);
			}
		}
		
		return tokens;
	}

	public static void main(String [] args) throws IOException
	{
		// input file name is argument 1 to the program
		Scanner filename;
		String line;
		Token [] tokens = null;
		
		filename  =  new Scanner(new FileReader(getFile()));
		System.out.println("");
		
		while(filename.hasNextLine())
		{
			line = filename.nextLine().trim();
			
			tokens = doScan(line);
		  
			for(int i = 0; i<tokens.length; i++)
			{
				if(tokens[i] != null) 
					System.out.println(tokens[i]);
			}
			
			System.out.println("");
			System.out.println("** New Line **");
			System.out.println("");
		}
	}
}