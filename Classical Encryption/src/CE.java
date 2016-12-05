/*
Shaun Mbateng
Classic Encryption
This Program Enciphers and Deciphers Using Various Cipher Algorithms
*/

import java.io.*;
import java.util.*;

class CE
{
	static int crypt;
	static int cipher;
	static int a,b,c,d;
	static char [] text;
	static char [] keyword;
	static int shift;
	static Scanner kbd = new Scanner(System.in);
	static Scanner cin=new Scanner(System.in);
	static Scanner infile;
	static String temp;
	static String KW;
	static PrintWriter outfile;

	static int char2int(char x) // Takes Character and Figures out Numeric Encoding
	{
	  if('a'<=x && x<='z') return x-'a';
	  if('A'<=x && x<='Z') return x-'A';
	  return -1;
	}

	static char int2char(int x) //Takes Numeric Encoding and Figures out Character
	{
	  return (char) (x+'A');
	}

	static char encode(char x) //Encode Function for Affine, VigenËre, and Shift Ciphers
	{
	  int y;
	  if(('a'<=x && x<='z')||('A'<=x && x<='Z'))
	  {
		y=char2int(x);
		y=(a*y+shift)%26;
		return int2char(y);
	  }
	  else
		return x;
	}

	static char decode(char x) //Decode Function for Affine, VigenËre, and Shift Ciphers
	{
	  int y;
	  if(('a'<=x && x<='z')||('A'<=x && x<='Z'))
	  {
		y=char2int(x);
		y=(c*y+(26-shift))%26;
		return int2char(y);
	  }
	  else
		return x;
	}

	public static void main(String [] args) throws IOException // Main Function
	{
		System.out.println("");
		System.out.print("Please enter the name of your data file: ");
		String infilename = kbd.nextLine();
		infile = new Scanner(new FileReader(infilename));
		outfile = new PrintWriter(new FileWriter("Text.txt")); //This File Will contain the Final Encrypted/Decrypted Message That Will Also Be Displayed
		System.out.println("");
		System.out.println("Would you Like to Encrypt (1) or Decrypt (2)?");
		crypt = cin.nextInt();
		while (crypt<1 || crypt>2)
		{
			System.out.println("");
			System.out.println("Not A Valid Answer ");
			System.out.println("Would you Like to Encrypt (1) or Decrypt (2)?");
			crypt = cin.nextInt();
		}

		System.out.println("");
		System.out.println("1. Shift Cipher") ;
		System.out.println("2. Substitution Cipher (Columnar Transposition)");
		System.out.println("3. Affine Cipher");
		System.out.println("4. VigenËre Cipher");
		System.out.println("");
		System.out.println("Pick the algorithm number you would like to use.");
		cipher = cin.nextInt();

		while (cipher<1 || cipher>4)
		{
			System.out.println("");
			System.out.println("That's Not A Valid Answer. ");
			System.out.println("Pick the algorithm number you would like to use.");
			cipher = cin.nextInt();
		}

		//Depending on What Method User Choose to Use, A Different Function is Called
		if (cipher == 1)
		{
			Shift(); // Shift Cipher
		}
		else if (cipher == 2)
		{
			Substitution(); // Columnar Transposition Cipher
		}
		else if (cipher == 3)
		{
			Affine(); // Affine Cipher
		}
		else if (cipher == 4)
		{
			VigenËre(); //VigenËre Cipher
		}
	}

	static void Shift() throws IOException
	{
		a = 1; // Shift Cipher is a Special Case of Affine Cipher, where the Character's Numeric Encoding is Multiplied by 1 Then Shifted
		c = 1;
		System.out.println("");
		System.out.println("What is the shift?");
		shift = cin.nextInt();
		System.out.println("");
		System.out.println("Check Text.txt File");

		if (crypt == 1) // Means if User Chose to Encrypt, the Shift Will be the Value of "shift"
		{
			System.out.println("");
			while(infile.hasNext())
			{
				temp = infile.nextLine();
				text = temp.toCharArray();
				for(int i=0; i<text.length; i++)
				{
					text[i]=encode(text[i]);
					System.out.print(text[i]);
					outfile.print(text[i]);
				}
				System.out.println("");
				outfile.println("");
			}

			System.out.println("");
			outfile.println("");
		}

		else if (crypt == 2) //If User Chose to Decrypt, the Shift will be 26 Minus the value of "shift"
		{
			System.out.println("");
			while(infile.hasNext())
			{
				temp = infile.nextLine();
				text = temp.toCharArray();
				for(int i=0; i<text.length; i++)
				{
					text[i]=decode(text[i]);
					System.out.print(text[i]);
					outfile.print(text[i]);
				}
				System.out.println("");
				outfile.println("");
			}
			System.out.println("");
			outfile.println("");
		}
		outfile.close();
	}

	static void Substitution() throws IOException
	{
		PrintWriter CToutfile = new PrintWriter(new FileWriter("CTOut.txt"));
		System.out.println("");
		System.out.println("What is the keyword?");
		KW=kbd.nextLine().replace(" ","");
		KW=KW.toLowerCase();
		System.out.println("");
		System.out.println("Check Text.txt File");
		System.out.println("");
		keyword = KW.toCharArray();
		char tempKW[] = new char [keyword.length];
		for (int i=0; i<keyword.length;i++)
		{
			for (int j=0; j<keyword.length;j++)
			{
				if (keyword[j]==keyword[i] && i!=j)
					keyword[j] = '\u0000'; //If There are Letter Repeats in the Keyword, Replace the Repeated Letter With the Default Character
										  // However, Size of the Keyword Remains the Same
			}
		}
		tempKW = keyword; // Makes A Temporary Char Array of the Keyword (With Repeated Letters Removed)
		for (int i=0; i<tempKW.length;i++)
		{
			if (tempKW[i]=='\u0000')
			{
				int j=i;
				while (tempKW[j]=='\u0000' && j!=tempKW.length-1)
					j++;
				char ctemp = tempKW[i];
				tempKW[i]=tempKW[j];
				tempKW[j]=ctemp; //Moves the Empty Spots to the End of the Array
			}
		}
		int length = tempKW.length; //"Length" is Originally the Length of the Keyword (With Repeated Letters)
		for (int k=0; k<tempKW.length;k++)
		{
			if (tempKW[k]=='\u0000')
			{
				length--; //Reduces "Length" to the Size of The Keyword With No Spaces
			}
		}
		keyword = new char [length];
		for (int i=0; i<keyword.length;i++)
		{
				if (tempKW[i] != '\u0000')
					keyword[i]=tempKW[i]; //Creates the Final Keyword Used With Repeats Removed and Size Reduced
		}
		int columns = keyword.length; //How Many Columns to Split Up the Alphabet With the Keyword
		System.out.print("Keyword Used: ");
		System.out.println(keyword);
		System.out.println("");
		KW=new String(keyword); //Sets KW to a String of the Reduced Keyword
		KW=KW+"abcdefghijklmnopqrstuvwxyz"; //Adds the Alphabet to the KW
		keyword=KW.toCharArray(); //Creates Another Char Array of Keyword, but With Alphabet
		char tempKW2[] = new char [keyword.length];

		//Process of Eliminating Repeates and Reducing Size Repeated, Thus Leaving the Keyword Along with the Unused Alphabet Letters Following
		//Could Not Have Been Done in A Loop Because Then The Length of the Original Keyword Would Be Unknown
		for (int i=0; i<keyword.length;i++)
		{
			for (int j=0; j<keyword.length;j++)
			{
				if (keyword[j]==keyword[i] && i!=j)
				{
					keyword[j] = '\u0000';
				}
			}
		}
		tempKW2 = keyword;
		for (int i=0; i<tempKW2.length;i++)
		{
			if (tempKW2[i]=='\u0000')
			{
				int j=i;
				while (tempKW2[j]=='\u0000' && j!=tempKW2.length-1)
					j++;
				char ctemp = tempKW2[i];
				tempKW2[i]=tempKW2[j];
				tempKW2[j]=ctemp;
			}
		}
		length = tempKW2.length;
		for (int k=0; k<tempKW2.length;k++)
		{
			if (tempKW2[k]=='\u0000')
			{
				length--;
			}
		}
		keyword = new char [length];
		for (int i=0; i<keyword.length;i++)
		{
				if (tempKW2[i] != '\u0000')
					keyword[i]=tempKW2[i];
		}
		char CT [] = new char [keyword.length]; //New Char Array
		if (keyword.length%columns!=0) //If Keyword Length (With Alphabet Letters Following) Isn't A Multiple of the Original Keyword Length (columns)
		{
			int r = keyword.length%columns;
			CT = new char [keyword.length+(columns-r)]; //Sets CT's Size to Size of Next Integer That is A Multiple of "columns"
														//Thus Gets Rid of Size Error When Keyword and Alphabet is Printed
			for (int i=0; i<keyword.length; i++)
				CT[i] = keyword[i];
		}
		else if (keyword.length%columns==0) //If Already Multiple of Keyword Length, Just Fill Regularly
		{
			for (int i=0; i<keyword.length; i++)
				CT[i] = keyword[i];
		}

		for (int i=0; i<CT.length; i=i+columns) //Print Keyword and Remaining Unused Alphabet Letters in Columns of Size "columns"
		{
			for (int j=0; j<columns;j++)
			{
				System.out.print(CT[i+j]);
			}
			System.out.println("");
		}
		System.out.println("");


		char [] tCT = new char [CT.length]; //New Char Array
		for (int i=0; i<columns; i++)
		{
			for (int j=0; j<CT.length;j=j+columns)
			{
				CToutfile.print(CT[i+j]); //Outfiles Keyword with Alphabet, but Transposed
			}
		}
		CToutfile.close();

		Scanner CTinfile = new Scanner(new FileReader("CTOut.txt")); //Infiles CTOut file With Transposed Keyword With Alphabet
		while (CTinfile.hasNext())
		{
			temp = CTinfile.nextLine();
			tCT = temp.toCharArray(); //Fils Char Array With Letters in Transposed Order, Thus Determining Which Encryption/Decryption Order
		}

		for (int i=0; i<tCT.length;i++) //Reduces Size of Char Array With Transposed Letters
		{
			if (tCT[i]=='\u0000')
			{
				int j=i;
				while (tCT[j]=='\u0000' && j!=tCT.length-1)
					j++;
				char ctemp = tCT[i];
				tCT[i]=tCT[j];
				tCT[j]=ctemp;
			}
		}


		while (infile.hasNext())
		{
			temp = infile.nextLine();
			temp = temp.toLowerCase();
			text = temp.toCharArray();
			if (crypt==1)
			{
				for (int i=0; i<text.length;i++)
				{
					//Depending on What the Letter is, Letter Diplayed is the Letter Who's Position in the Transposed Char Array
					//Matches the Numeric Encoding of the Letter Read In (Minus 'a')
					if (text[i]=='a')
					{
						System.out.print(tCT[0]);
						outfile.print(tCT[0]);
					}
					else if (text[i]=='b')
					{
						System.out.print(tCT[1]);
						outfile.print(tCT[1]);
					}
					else if (text[i]=='c')
					{
						System.out.print(tCT[2]);
						outfile.print(tCT[2]);
					}
					else if (text[i]=='d')
					{
						System.out.print(tCT[3]);
						outfile.print(tCT[3]);
					}
					else if (text[i]=='e')
					{
						System.out.print(tCT[4]);
						outfile.print(tCT[4]);
					}
					else if (text[i]=='f')
					{
						System.out.print(tCT[5]);
						outfile.print(tCT[5]);
					}
					else if (text[i]=='g')
					{
						System.out.print(tCT[6]);
						outfile.print(tCT[6]);
					}
					else if (text[i]=='h')
					{
						System.out.print(tCT[7]);
						outfile.print(tCT[7]);
					}
					else if (text[i]=='i')
					{
						System.out.print(tCT[8]);
						outfile.print(tCT[8]);
					}
					else if (text[i]=='j')
					{
						System.out.print(tCT[9]);
						outfile.print(tCT[9]);
					}
					else if (text[i]=='k')
					{
						System.out.print(tCT[10]);
						outfile.print(tCT[10]);
					}
					else if (text[i]=='l')
					{
						System.out.print(tCT[11]);
						outfile.print(tCT[11]);
					}
					else if (text[i]=='m')
					{
						System.out.print(tCT[12]);
						outfile.print(tCT[12]);
					}
					else if (text[i]=='n')
					{
						System.out.print(tCT[13]);
						outfile.print(tCT[13]);
					}
					else if (text[i]=='o')
					{
						System.out.print(tCT[14]);
						outfile.print(tCT[14]);
					}
					else if (text[i]=='p')
					{
						System.out.print(tCT[15]);
						outfile.print(tCT[15]);
					}
					else if (text[i]=='q')
					{
						System.out.print(tCT[16]);
						outfile.print(tCT[16]);
					}
					else if (text[i]=='r')
					{
						System.out.print(tCT[17]);
						outfile.print(tCT[17]);
					}
					else if (text[i]=='s')
					{
						System.out.print(tCT[18]);
						outfile.print(tCT[18]);
					}
					else if (text[i]=='t')
					{
						System.out.print(tCT[19]);
						outfile.print(tCT[19]);
					}
					else if (text[i]=='u')
					{
						System.out.print(tCT[20]);
						outfile.print(tCT[20]);
					}
					else if (text[i]=='v')
					{
						System.out.print(tCT[21]);
						outfile.print(tCT[21]);
					}
					else if (text[i]=='w')
					{
						System.out.print(tCT[22]);
						outfile.print(tCT[22]);
					}
					else if (text[i]=='x')
					{
						System.out.print(tCT[23]);
						outfile.print(tCT[23]);
					}
					else if (text[i]=='y')
					{
						System.out.print(tCT[24]);
						outfile.print(tCT[24]);
					}
					else if (text[i]=='z')
					{
						System.out.print(tCT[25]);
						outfile.print(tCT[25]);
					}
					else if (text[i]==' ')
					{
						System.out.print(" ");
						outfile.print(" ");
					}
					else if (text[i]=='\u0000')
					{
						System.out.print(" ");
						outfile.print(" ");
					}
				}
				System.out.println("");
				outfile.println("");
			}
			else if (crypt==2)
			{
				for (int j=0; j<text.length;j++)
				{
					//If text has a space, display space
					if (text[j]==' ')
					{
						System.out.print(' ');
						outfile.print(' ');
					}
					else if (text[j]=='\u0000')
					{
						System.out.print(" ");
						outfile.print(" ");
					}
					for (int i=0; i<tCT.length;i++)
					{
						//Figure Out Which Position the Letter Being Read Has in the Transposed Char Array
						//Display Letter With Matching Position in Alphabet
						if (text[j]==tCT[i] && text[j]!=' ' && text[j]!='\u0000')
						{
							if (i==0)
							{
								System.out.print('A');
								outfile.print('A');
							}
							else if (i==1)
							{
								System.out.print('B');
								outfile.print('B');
							}
							else if (i==2)
							{
								System.out.print('C');
								outfile.print('C');
							}
							else if (i==3)
							{
								System.out.print('D');
								outfile.print('D');
							}
							else if (i==4)
							{
								System.out.print('E');
								outfile.print('E');
							}
							else if (i==5)
							{
								System.out.print('F');
								outfile.print('F');
							}
							else if (i==6)
							{
								System.out.print('G');
								outfile.print('G');
							}
							else if (i==7)
							{
								System.out.print('H');
								outfile.print('H');
							}
							else if (i==8)
							{
								System.out.print('I');
								outfile.print('I');
							}
							else if (i==9)
							{
								System.out.print('J');
								outfile.print('J');
							}
							else if (i==10)
							{
								System.out.print('K');
								outfile.print('K');
							}
							else if (i==11)
							{
								System.out.print('L');
								outfile.print('L');
							}
							else if (i==12)
							{
								System.out.print('M');
								outfile.print('M');
							}
							else if (i==13)
							{
								System.out.print('N');
								outfile.print('N');
							}
							else if (i==14)
							{
								System.out.print('O');
								outfile.print('O');
							}
							else if (i==15)
							{
								System.out.print('P');
								outfile.print('P');
							}
							else if (i==16)
							{
								System.out.print('Q');
								outfile.print('Q');
							}
							else if (i==17)
							{
								System.out.print('R');
								outfile.print('R');
							}
							else if (i==18)
							{
								System.out.print('S');
								outfile.print('S');
							}
							else if (i==19)
							{
								System.out.print('T');
								outfile.print('T');
							}
							else if (i==20)
							{
								System.out.print('U');
								outfile.print('U');
							}
							else if (i==21)
							{
								System.out.print('V');
								outfile.print('V');
							}
							else if (i==22)
							{
								System.out.print('W');
								outfile.print('W');
							}
							else if (i==23)
							{
								System.out.print('X');
								outfile.print('X');
							}
							else if (i==24)
							{
								System.out.print('Y');
								outfile.print('Y');
							}
							else if (i==25)
							{
								System.out.print('Z');
								outfile.print('Z');
							}

						}
					}
				}
				System.out.println("");
				outfile.println("");
			}
		}
		outfile.close();
	}

	static void Affine() throws IOException
	{
		if (crypt==1)
		{
			System.out.println("");
			System.out.println("E(x) = ax+b mod 26");
			System.out.println("What is a?");
			a=cin.nextInt(); //Gets a
			System.out.println("What is b?");
			b=cin.nextInt(); //Gets b
			shift=b; //Sets Shift Equal to b
			System.out.println("");
			System.out.println("Check Text.txt File");
			while(infile.hasNext())
			{
				temp = infile.nextLine();
				text = temp.toCharArray();
				for(int i=0; i<text.length; i++)
				{
					text[i]=encode(text[i]); //Used Same Function to Encode as Shift Cipher
					System.out.print(text[i]);
					outfile.print(text[i]);
				}
				System.out.println("");
				outfile.println("");
			}
			System.out.println("");
			outfile.println("");
		}
		else if (crypt==2)
		{
			System.out.println("");
			System.out.println("D(y) = cy+d mod 26");
			System.out.println("What is c?");
			c=cin.nextInt(); //Gets c
			System.out.println("What is d?");
			d=cin.nextInt(); //Gets d
			shift=26-d; //Sets Shift to 26-d
			System.out.println("");
			System.out.println("Check Text.txt File");
			System.out.println("");
			while(infile.hasNext())
			{
				temp = infile.nextLine();
				text = temp.toCharArray();
				for(int i=0; i<text.length; i++)
				{
					text[i]=decode(text[i]); //Used Same Function to Decode as Shift Cipher
					System.out.print(text[i]);
					outfile.print(text[i]);
				}
				System.out.println("");
				outfile.println("");
			}
			System.out.println("");
			outfile.println("");
		}
		outfile.close();
	}

	static void VigenËre() throws IOException
	{
		a=1; //a and c are Equal to One
		c=1;
		System.out.println("What is the keyword?");
		KW=kbd.nextLine().replace(" ","");
		System.out.println("");
		System.out.println("Check Text.txt File");
		System.out.println("");
		KW=KW.toLowerCase();
		keyword = KW.toCharArray();
		char [] VKW;
		char [] VText;

		while (infile.hasNext())
		{
			temp=infile.nextLine();
			temp=temp.toLowerCase();
			VText=temp.toCharArray(); //Contains Line Text With Spaces
			temp=temp.replace(" ","");
			text=temp.toCharArray(); //Contains Line Text With No Spaces
			ArrayList FText=new ArrayList(text.length); //Array List, Used Later
			VKW = new char[text.length]; //Size of Array is Set to Size of Text
			if (text.length<=keyword.length)
			{
				for (int i=0; i<VKW.length; i++)
				{
					VKW[i]=keyword[i];
					//If text is shorter than or Equal to keyword, then fills char array with first n letters of keyword
					//n being the size of the text in this case
				}

				for (int i=0; i<text.length;i++)
				{
					shift = VKW[i]-'a'; //Sets the Shift Equal to the Numeric Encoding of Character at i Position
					if (text[i]!=' ' && text[i]!='\u0000')
					{
						if (crypt==1)
						{
							text[i]=encode(text[i]); //Used Same Encode Function
							FText.add(text[i]); //Add Encoded Letter to Array List
						}
						else if (crypt==2)
						{
							text[i]=decode(text[i]); //Uses Same Decode Function
							FText.add(text[i]); //Add Decoded Letter to Array List
						}
					}
				}
			}
			else if (text.length>keyword.length)
			{
				//If text is Longer than keyword, Then Fills Char Array With Keyword
				//Then Fills Rest of Array Letters of Keyword Again Until Array is Filled
				for (int i=0; i<keyword.length; i++)
				{
					VKW[i]=keyword[i];
				}
				for (int i=keyword.length; i<VKW.length; i++)
				{
					VKW[i]=keyword[i-(i/keyword.length)*keyword.length];
				}
				for (int i=0; i<text.length;i++)
				{
					shift = VKW[i]-'a'; //Sets the Shift Equal to the Numeric Encoding of Character at i Position
					if (text[i]!=' ' && text[i]!='\u0000')
					{
						if (crypt==1)
						{
							text[i]=encode(text[i]); //Used Same Encode Function
							FText.add(text[i]); //Add Encoded Letter to Array List
						}
						else if (crypt==2)
						{
							text[i]=decode(text[i]); //Uses Same Decode Function
							FText.add(text[i]); //Add Decoded Letter to Array List
						}
					}
				}
			}
			for (int i=0; i<VText.length;i++)
			{
					if (VText[i]=='\u0000' || VText[i]==' ')
						FText.add(i," "); //Adds Spaces to Position That Contain It in Original Text
			}
			for (int i=0; i<FText.size(); i++)
			{
				System.out.print(FText.get(i)); //Display and Outfile Text With Spaces in Correct Position
				outfile.print(FText.get(i));
			}
			System.out.println("");
		}
		outfile.close();
	}
}