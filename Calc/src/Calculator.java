import java.util.Scanner;

public class Calculator
{
	static Scanner input = new Scanner(System.in);
	public static void main(String [] args)
	{
		System.out.println("Welcome to the Calculator.");
		boolean run = true;
		char choice;
		double first, second, answer;
		int fact;
		
		while(run==true)
		{
			System.out.println("");
			
			choice = checkInput();
			
			if(choice=='+')
			{
				System.out.println("What is the first number?");
				first = input.nextDouble();
				System.out.println("What is the second number?");
				second = input.nextDouble();
				answer = first + second;
				System.out.println(first + " + " + second + " = " + answer);
			}//plus
			else if(choice=='-')
			{
				System.out.println("What is the first number?");
				first = input.nextDouble();
				System.out.println("What is the second number?");
				second = input.nextDouble();
				answer = first - second;
				System.out.println(first + " - " + second + " = " + answer);
			}//plus
			else if(choice=='x')
			{
				System.out.println("What is the first number?");
				first = input.nextDouble();
				System.out.println("What is the second number?");
				second = input.nextDouble();
				answer = first * second;
				System.out.println(first + " + " + second + " = " + answer);
			}//plus
			else if(choice=='/')
			{
				System.out.println("What is the first number?");
				first = input.nextDouble();
				System.out.println("What is the second number?");
				second = input.nextDouble();
				answer = first + second;
				System.out.println(first + " + " + second + " = " + answer);
			}//plus
			else if(choice=='e')
			{
				System.out.println("What is the first number?");
				first = input.nextDouble();
				System.out.println("What is the second number?");
				second = input.nextDouble();
				answer = Math.pow(first, second);
				System.out.println(first + " ^ " + second + " = " + answer);
			}//plus
			else if(choice=='r')
			{
				System.out.println("What is the first number?");
				first = input.nextDouble();
				System.out.println("What is the second number?");
				second = input.nextDouble();
				answer = Math.pow(first, 1/second);
				System.out.println(first + " 1/ " + second + " = " + answer);
			}//plus
			else if(choice=='!')
			{
				System.out.println("What is the first number?");
				fact = input.nextInt();
				System.out.println(fact + "! =" + factorial(fact));
			}//plus
			input.nextLine();
			System.out.println("Would you like to play again y/n");
			choice = input.nextLine().toLowerCase().charAt(0);
			if(choice != 'y')
			{
				System.out.println("Farewell");
				run = false;
			}
			
		}//while
	}//main
	public static char checkInput()
	{
		char i = input.nextLine().toLowerCase().charAt(0);
		while(i != '+' && i != '-' && i != 'x' && i != '/' && i != 'e' && i != 'r' && i != '!')
		{
			System.out.println("invalid choose again");
			i = input.nextLine().toLowerCase().charAt(0);
		}
		return i;
	}
	public static int factorial(int f)
	{
		if(f==1)
		{
			return f;
		}
		else
		{
			return f*factorial(f-1);
		}
	}
	
	
}//class