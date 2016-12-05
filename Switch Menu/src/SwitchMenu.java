import java.util.Scanner;
 
public class SwitchMenu {
 
    public static void main(String[] args) {
         
        // Set up scanner for user input
        Scanner userInput = new Scanner(System.in);
         
        System.out.println("Choose a class\n1: Warrior\n2: Rogue\n3: Wizard");
         
        // Get an integer from the user
        int choice = userInput.nextInt();
         
        // Start switch statment using choice variable
        switch(choice){
        case 1:
            // This will run if user types 1
            System.out.println("You picked Warrior");
            break;
        case 2:
            // This will run if user types 2
            System.out.println("You picked Rogue");
            break;
        case 3:
            // This will run if user types 3
            System.out.println("You picked Wizard");
            break;
        default:
            // This will run if user types any other number
            System.out.println("That wasn't an option.");
            break;
        }
         
    }
 
}