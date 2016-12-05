/*

RSA Encryption
This Program Enciphers and Deciphers Using RSA
*/

import java.io.*;
import java.util.*;
import java.math.*;

class RSA
{
	static BigInteger m,n,e,p,q,d; //Private and Public Keys Declared as Big Integers
	static int pb,qb; //Represents Number of Bits of p and q Respectively
	static int b; //Integer for Base
	static int crypt; //For Choosing Whether to Encrypt or Decrypt Later
	static Scanner kbd = new Scanner(System.in); //Read Strings
	static Scanner cin=new Scanner(System.in); //Read Integers
	static Scanner infile; //Read From File
	static PrintWriter outfile; //Read to File

	public static void main(String [] args) throws IOException // Main Function
	{
		int keys; //To Choose Whether to Create or Load Keys

		System.out.println("");
		System.out.println("***Welcome User: This Program Encrypts/Decrypts Using RSA***");
		System.out.println("");
		System.out.println("What would you like to do?");
		System.out.println("");
		System.out.println("1. Create Keys");
		System.out.println("2. Load Keys");
		System.out.println("");
		keys = cin.nextInt();

		while (keys<1 || keys>2) //User Can Only Select 1 or 2
		{
			System.out.println("");
			System.out.println("Not A Valid Answer: ");
			System.out.println("Type 1 to Create Keys or 2 to Load Them");
			keys = cin.nextInt();
		}

		if (keys == 1) //User Chose to Create Keys
		{	
			System.out.println("");
			System.out.println("How many bits should prime p be?");
			pb = cin.nextInt(); //Enter Digit Number of p
			
			System.out.println("");
			System.out.println("How many bits should prime q be?");
			qb = cin.nextInt(); //Enter Digit Number of q
			
			CalcKeys(); //Call Calculate Keys Function
			SaveKeys(); //Call Save Keys Function
		}

		else if (keys == 2) //User Chose to Load Keys
		{
			LoadKeys(); //Call Load Keys Function
			m = p.multiply(q); //Sets m to be p*q
		}

		System.out.println("");
		System.out.println("Now what would you like to do?");
		System.out.println("");
		System.out.println("1. Encrypt");
		System.out.println("2. Decrypt");
		System.out.println("");
		crypt = cin.nextInt();

		while (crypt<1 || crypt>2) //User Can Only Select 1 or 2
		{
			System.out.println("");
			System.out.println("Not A Valid Answer: ");
			System.out.println("Type 1 to Encrypt or 2 to Decrypt");
			crypt = cin.nextInt();
		}

		System.out.println("");
		System.out.println("Which file has the original text?");
		infile = new Scanner(new FileReader(kbd.nextLine())); //Enter File to Read Text From
		System.out.println("");
		System.out.println("Which file would you like to save the new text to?");
		outfile = new PrintWriter(new FileWriter(kbd.nextLine())); //Enter File to Read Text To
		System.out.println("");
		System.out.println("What is the base you would like to use?");
		b = cin.nextInt(); //Select Base
		Crypt();
	}

	static void CalcKeys() throws IOException //Function That Calculates Keys
	{
		BigInteger one = BigInteger.valueOf(1);
		p = BigInteger.probablePrime(pb, new Random()); //p is A Random Prime Number With pd Digits
		System.out.println("");
		System.out.println("p = "+p); //Print Value of p
		q = BigInteger.probablePrime(qb, new Random()); //q is A Random Prime Number With qd Digits
		System.out.println("q = "+q); //Print Value of q
		n = (p.subtract(one)).multiply(q.subtract(one)); //Calculate n-Value
		System.out.println("n = "+n); //Print Value of n
		m = p.multiply(q); //Sets m to be p*q
		System.out.println("m = "+m); //Print Value of m
		e = new BigInteger(Integer.toBinaryString(n.intValue()).length(), new Random()); //Sets e to A Random Number From 0 to 2^(n.BitLength) - 1
		
		while (e.gcd(n).intValue() != 1) //Check to See if e is Relatively Prime to n, If Not, Makes New Random Number Till Is
		{
			e = new BigInteger(Integer.toBinaryString(n.intValue()).length(), new Random());
		}
		
		System.out.println("e = "+e); //Print Value of e
		d = e.modInverse(n); //Calculate d-Value
		System.out.println("d = "+d); //Print Value of d
	}

	static void SaveKeys() throws IOException
	{
		System.out.println("");
		System.out.println("What would you like to save the private keys to?");
		outfile = new PrintWriter(new FileWriter(kbd.nextLine())); //Save Private Keys to This File
		outfile.println(n); //Outfile n
		outfile.println(d); //Outfile d
		outfile.println(p); //Outfile p
		outfile.println(q); //Outfile q
		outfile.close();
		System.out.println("");
		System.out.println("What would you like to save the public keys to?");
		outfile = new PrintWriter(new FileWriter(kbd.nextLine())); //Save Public Keys to This File
		outfile.println(n); //Outfile n
		outfile.println(e); //Outfile e
		outfile.close();
	}

	static void LoadKeys() throws IOException
	{
		System.out.println("");
		System.out.println("What would you like to load the private keys from?");
		infile = new Scanner(new FileReader(kbd.nextLine())); //Load Private Keys From Here
		n = infile.nextBigInteger(); //First Integer is n-Value
		d = infile.nextBigInteger(); //Second Integer is d-Value
		p = infile.nextBigInteger(); //Third Integer is p-Value
		q = infile.nextBigInteger(); //Fourth Integer is q-Value
		System.out.println("");
		System.out.println("What would you like to load the public keys from?");
		infile = new Scanner(new FileReader(kbd.nextLine())); //Load Public Keys From Here
		n = infile.nextBigInteger(); //First Integer is n-Value
		e = infile.nextBigInteger(); //Second Integer is e-Value
	}

	static void Crypt() throws IOException
	{
		System.out.println("");

		String text; //String to Hold Original Text
		BigInteger x; //Big Integer to Hold Original Text
		BigInteger y; //Big Integer to Hold Encrypted/Decrypted Text

		while(infile.hasNext())
		{
			text = infile.nextLine(); //Read In Text
			text = text.replaceAll("[^A-Za-z0-9]", ""); //Remove Non-Alphanumeric Characters (Including Spaces)
			
			if (text.length() >= n.intValue()) //Text is Longer than Value of n
			{
				int i;
				int quotient = text.length()/n.intValue();
				String subtext;
				
				for (i=1; i<=quotient+1; i++) //Split Text into Blocks
				{
					subtext = text.substring((n.intValue()-1)*(i-1),i*(n.intValue()-1));
					x = new BigInteger(subtext,b); //Convert Subtext to Base b
				
					if (crypt == 1) //User Chose to Encrypt
					{
						y = x.modPow(e,m); //Encryption Formula
					}
				
					else //User Chose to Decrypt
					{
						y = x.modPow(d,m); //Decryption Formula
					}
					
					System.out.print(y.toString(b)); //Print y in Base b
					outfile.print(y.toString(b)); //Outfile y in Base b
				}
				
				//Now to Handle the Remaining Subtext of the Original Text
				subtext = text.substring((n.intValue()-1)*i, text.length()+1);
				x = new BigInteger(subtext,b); //Convert Subtext to Base b
				
				if (crypt == 1) //User Chose to Encrypt
				{
					y = x.modPow(e,m); //Encryption Formula
				}
				
				else //User Chose to Decrypt
				{
					y = x.modPow(d,m); //Decryption Formula
				}
				
				System.out.print(y.toString(b)); //Print y in Base b
				outfile.print(y.toString(b)); //Outfile y in Base b
			}
			
			else
			{
				x = new BigInteger(text,b); //Convert Text to Base b
				
				if (crypt == 1) //User Chose to Encrypt
				{
					y = x.modPow(e,m); //Encryption Formula
				}
				
				else //User Chose to Decrypt
				{
					y = x.modPow(d,m); //Decryption Formula
				}
				
				System.out.println(y.toString(b)); //Print y in Base b
				outfile.println(y.toString(b)); //Outfile y in Base b
			}
		}
		
		System.out.println("");
		System.out.println("");
		outfile.close();
	}
}