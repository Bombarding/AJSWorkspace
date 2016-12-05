import java.util.Scanner;
import java.util.Random;
public class AdventureCave {
 
    public static void main(String[] args) {
         
    	Random randomGenerator = new Random();
    	
        // Welcome the player to the game
        System.out.println("Welcome, traveler, "
                + "to the Cave of Wonders!"
                + " Adventures await!");
         
        // Initialize player name and level
        int playerLevel = 1;
        String playerName = "Horatio";
        double playerHealth = 10.0;
        Scanner playerInput = new Scanner(System.in);
         
        System.out.println( "What is your name?");
        playerName = playerInput.nextLine();
         
        System.out.println( "You are " + playerName + " the level " + playerLevel + " adventurer.");
         
        // Arithmetic with variables
         
        // Entrance Description
        System.out.println("You made it to the entrance of the cave.");
        playerLevel = playerLevel + 1;
        System.out.println("You leveled up. You are now level " + playerLevel);
         
         
        // Rock falling and causing damage
        double rockDamage = randomGenerator.nextInt(5);
        System.out.println("A treacherous rock ambushes you.");
        playerHealth -= rockDamage;
        System.out.println("You took " + rockDamage
                + " damage. You have " + playerHealth
                + " health remaining.");
         
        // Check if damage killed the player
        if( playerHealth <= 0 ){ 
 
            System.out.println("Oh no, "
                 + playerName +" died! They were level "
                    + playerLevel);
            System.exit(0);
 
        }
         
        // String where player's input will be stored
        String playerAnswer;
         
        // Room 1 Description
        System.out.println("You are in a cave."
                + "\nThere is a dark path to the left."
                + "\nThere is a pile of rubble to the right."
                + "\nWould you like to go 'left' or 'right'?");
        // get player's answer
        playerAnswer = playerInput.nextLine();
         
         
        /* If Statements
         * For choice 1
         */
        if(playerAnswer.equalsIgnoreCase("right")){ // Run if player typed "right"
            System.out.println("You climb up the pile of rubble."
                    + " It leads to a small cavern."
                    + "\nThere is a treasure chest here."
                    + " It is full of treasure.");
            // Player found some treasure - level up!
            playerLevel = playerLevel + 1;
            System.out.println("You leveled up. You are now level " + playerLevel);
        }
        else if(playerAnswer.equalsIgnoreCase("left")){ // Run if player typed "left"
            System.out.println("The path gets very dark. Another cunning rock ambushes you.");
            // Set the damage of the rock that attacks you
            rockDamage = 4.0;
            playerHealth -= rockDamage;
            System.out.println("You took " + rockDamage
                    + " damage. You have " + playerHealth
                    + " health remaining.");
            // Check if damage killed the player
            if( playerHealth <= 0 ){ 
 
                System.out.println("Oh no, "
                     + playerName +" died! They were level "
                        + playerLevel);
                System.exit(0);
 
            }
        }
         
         
        // Room 2 Description
                System.out.println("There is a small slime here. It burbles at you menacingly."
                        + "\nWould you like to 'befriend' it, 'fight' it or 'run' away?" );
                // get player's answer
                playerAnswer = playerInput.nextLine();
                 
                 
                /* If Statements
                 * For choice 2
                 */
                if(playerAnswer.equalsIgnoreCase("befriend")){ // Run if player typed "befriend"
                    System.out.println("You say hello to the slime and offer it some of your food.\n"
                            + "The slime dissolves your food. It burbles less menacingly and leads you to a magic hat."
                            + "\nYou found a magic hat!");
                    // Player found some treasure - level up!
                    playerLevel = playerLevel + 1;
                    System.out.println("You leveled up. You are now level " + playerLevel);
                }
                else if(playerAnswer.equalsIgnoreCase("fight")){ // Run if player typed "left"
                    System.out.println("You hit the slime with your sword and it dissolves.\n"
                            + "The slime burbles angrily and sprays some acid at you.");
                     
                    rockDamage = 5.0;
                    playerHealth -= rockDamage;
                    System.out.println("You took " + rockDamage
                            + " damage. You have " + playerHealth
                            + " health remaining.");
                    // Check if damage killed the player
                    if( playerHealth <= 0 ){ 
 
                        System.out.println("Oh no, "
                             + playerName +" died! They were level "
                                + playerLevel);
                        System.exit(0);
 
                    }
                     
                }
                else if(playerAnswer.equalsIgnoreCase("run")){ // Run if player typed "run"
                    System.out.println("You turn tail and run. The slime does not pursue you.\n"
                            + "You fall into a healing spring while running.");
                    double healing = 3.5; // Heal the player
                    playerHealth += healing;
                    System.out.println("You were healed for " + healing + ". Your health is now " + playerHealth);
                }
         
        System.out.println(playerName +" survived the Cave of Wonders! You made it to level "+ playerLevel);
         
        playerInput.close();
    }
 
}