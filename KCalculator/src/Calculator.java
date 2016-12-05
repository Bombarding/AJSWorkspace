
import java.util.Scanner;
public class Calculator
{
   static Scanner input = new Scanner(System.in);
   
   public static void main(String[] args)
   {
      System.out.println("Welcome to the Calculator.");
      boolean run = true; //runs while loop as long as user wants to continue
      char choice; //menu selection
      double first, second, answer; //values to be used
      int fact; //for factorial
      
      while(run == true)
      {
         System.out.println("+:Addition \n" + "-:Subtraction \n" + "x:Multiplication \n" + "/:Division \n" + "e:Exponent \n" + "r:Root \n"+  "!:Factorial");
         
         choice = checkInput(); //Asks the user for an input and makes sure it is valid.
         
         if(choice == '+') //Addition case
         {
            System.out.println("What is the first number?");
            first = input.nextDouble();
            System.out.println("What is the second number?");
            second = input.nextDouble();
            answer = first + second;
            System.out.println(first + " + " + second + " = " + answer);
         }
         else if(choice == '-') //Subtraction case
         {
            System.out.println("What is the first number?");
            first = input.nextDouble();
            System.out.println("What is the second number?");
            second = input.nextDouble();
            answer = first - second;
            System.out.println(first + " - " + second + " = " + answer);
         }
         else if(choice == 'x') //Multiplication case
         {
            System.out.println("What is the first number?");
            first = input.nextDouble();
            System.out.println("What is the second number?");
            second = input.nextDouble();
            answer = first * second;
            System.out.println(first + " x " + second + " = " + answer);
         }
         else if(choice == '/') //Division case
         {
            System.out.println("What is the first number?");
            first = input.nextDouble();
            System.out.println("What is the second number?");
            second = input.nextDouble();
            answer = first / second;
            System.out.println(first + " / " + second + " = " + answer);
         }
         else if(choice == 'e') //Exponential case
         {
            System.out.println("What is the base?");
            first = input.nextDouble();
            System.out.println("What is the exponent?");
            second = input.nextDouble();
            answer = Math.pow(first, second);
            System.out.println(first + "^" + second + " = " + answer);
         }
         else if(choice == 'r') //Root case
         {
            System.out.println("What is the radicand?");
            first = input.nextDouble();
            System.out.println("What is the index?");
            second = input.nextDouble();
            answer = Math.pow(first, 1 / second);
            System.out.println(first + "^1/" + second + " = " + answer);
         }
         else if(choice == '!') //Factorial case
         {
            System.out.println("What do you want to factorialize?");
            fact = input.nextInt();
            System.out.println(fact + "! = " + factorial(fact));
         }
         input.nextLine();
         System.out.println("Do you want to run again? y/n"); //Asks the user if they want to play again.
         choice = input.nextLine().toLowerCase().charAt(0);
         if(choice != 'y')
         {
         	System.out.println("Farewell!");
            run = false;
         }
      }
   }
   public static char checkInput() //Asks user for input and checks for validity
   {
      char i = input.nextLine().toLowerCase().charAt(0);
      while(i != '+' && i != '-' && i != 'x' && i != '/' && i != 'e' && i != 'r' && i != '!')
      {
         System.out.println("Invalid choice. Choose another: +, -, x, /, e, r, !");
         i = input.nextLine().toLowerCase().charAt(0);
      }
      return i;
   }
   public static int factorial(int f) //Recursive factorial method
   {
      if(f == 1)
         return f;
      else
         return f * factorial(f-1);
      	
   }
}