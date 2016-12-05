package Arena.Characters;
 
import Arena.Arena;
 
public class Character {
 
    private static String[] nameList = {"Geoff", "Steve","Kruger" };
     
    public String name;
     
    // Private stats we don't want outside classes changing
    public int strength = 10;
    public int health = 20;
    public int defense = 5;
     
    public Character(){
        // Class constructor
         
        // Pick a character name at random
        this.name = nameList[Arena.generator.nextInt(nameList.length)];
    }
     
     
    public Character(int strength, int defense, int health){
        this();
        this.strength = strength;
        this.defense = defense;
        this.health = health;
         
        }
     
     
    // The code to run when this character attacks
    public int attack(Character target){
     
        // Apply damage formula
        int damageDealt = this.strength;
         
        // Tell the target to take this much damage, then return the amount of damage the target took.
        return target.takeDamage(damageDealt);
         
    }
     
    // The code to run when this character takes some damage
    public int takeDamage(int damage){
         
        // Apply defense formula
        int damageTaken = damage - this.defense;
         
        // Subtract the final damage number from this character's health
        this.health -= damageTaken;
         
        return damageTaken;
    }
    
    public boolean isAlive(){
    return this.health > 0;
    }
    public boolean getHealth(){
    return this.health > 0;
    }
    
}