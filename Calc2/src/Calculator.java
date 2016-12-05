import java.util.Scanner;

public class Calculator
{
	static Scanner input = new Scanner(System.in);
	public static void main(String [] args)
	{
		System.out.println("**Welcome to the Calculator");
		System.out.println("Chose 1: +,-,/,*,e,r,!");
		boolean run = true;
		char choice;
		double first,second,answer;
		int fact;
		
		while(run==true)
		{
			System.out.println("");
			choice = checkInput();
			
			if(choice=='+')
			{
				System.out.println("Please type the first number");
				first = input.nextDouble();
				System.out.println("Please type the second number");
				second = input.nextDouble();
				answer = first+second;
				System.out.println(first + " + " + second + " = " + answer);
			}//plus
			else if(choice=='-')
			{
				System.out.println("Please type the first number");
				first = input.nextDouble();
				System.out.println("Please type the second number");
				second = input.nextDouble();
				answer = first-second;
				System.out.println(first + " - " + second + " = " + answer);
			}//subtract
			else if(choice=='*')
			{
				System.out.println("Please type the first number");
				first = input.nextDouble();
				System.out.println("Please type the second number");
				second = input.nextDouble();
				answer = first*second;
				System.out.println(first + " * " + second + " = " + answer);
			}//multiply
			else if(choice=='/')
			{
				System.out.println("Please type the first number");
				first = input.nextDouble();
				System.out.println("Please type the second number");
				second = input.nextDouble();
				answer = first/second;
				System.out.println(first + " / " + second + " = " + answer);
			}//divide
			else if(choice=='e')
			{
				System.out.println("Please type the first number");
				first = input.nextDouble();
				System.out.println("Please type the second number");
				second = input.nextDouble();
				answer = Math.pow(first, second);
				System.out.println(first + " ^ " + second + " = " + answer);
			}//exponent
			else if(choice=='r')
			{
				System.out.println("Please type the first number");
				first = input.nextDouble();
				System.out.println("Please type the second number");
				second = input.nextDouble();
				answer = Math.pow(first, 1/second);
				System.out.println(first + " 1/ " + second + " = " + answer);
			}//root
			else if(choice =='!')
			{
				System.out.println("Please type a number");
				fact = input.nextInt();
				System.out.println(fact + "! =" + factorial(fact));
			}
			input.nextLine();
			System.out.println("Would you like to run again? y/n");
			choice = input.nextLine().toLowerCase().charAt(0);
			if(choice != 'y')
			{
				System.out.println("Peace");
				run = false;
			}
		}//while
		
	}//main
	public static char checkInput()
	{
		char i = input.nextLine().toLowerCase().charAt(0);
		while(i != '+' && i != '-' && i != '*' && i != '/' && i != 'e' && i != 'r' && i!= '!')
		{
			System.out.println("invalid choice, chose again");
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