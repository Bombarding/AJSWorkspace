package Arena;
 
import Arena.Characters.Rogue;
import Arena.Characters.Character;
 
import java.util.Random;
 
 
public class Arena {
     
    // Create a random number generator for everyone to use. This is easier than every character having their own.
    // the "static" keyword means that there will only ever be one of these.
    public static Random generator = new Random();
     
    public static void main(String[] args) {
         
        Character player1 = new Character();
        Character player2 = new Character(10,2,100);
         
         
         
         
        // Put a 2 after the name if characters are name the same
        if (player2.name.equals(player1.name)){
            player2.name += " 2";
        }
         
        System.out.println(player1.name + " vs. " + player2.name);
         
        // Fight as long as both characters are alive
        int turns = 0;
        while(player1.isAlive() && player2.isAlive()){
            turns++;
             
            System.out.println("Turn " + turns + "\n" + player1.name + ": " + player1.getHealth() + " Health | " + player2.name + ": " + player2.getHealth() + " Health\n");
            int damage;
            // player 1 attack
            damage = player1.attack(player2);
            System.out.println(player1.name + " hits " + player2.name + " for " + damage + " damage.");
             
            // player 2 attack
            damage = player2.attack(player1);
            System.out.println(player2.name + " hits " + player1.name + " for " + damage + " damage.\n");
        }
         
        // Check to see who won
        if( player1.isAlive()){
            System.out.println(player1.name + " wins!");
        }
        else if (player2.isAlive()){
            System.out.println(player2.name + " wins!");
        }
        else{
            System.out.println("It's a draw!");
        }
         
    }
 
}