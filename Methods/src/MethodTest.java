public class MethodTest {
 
    public static void main(String[] args) {
         
        int playerAttack = 10;
        int enemyArmor = 5;
         
        int damage = dealDamage(playerAttack, enemyArmor);
         
        System.out.println("Your attack did " + damage +" damage to the enemy.");
         
 
    }
     
    // Method Declaration
    public static int dealDamage(int damage, int armor){
        // Method Body
         
        // Calculate damage dealt
        int damageDealt = damage - armor;
         
        // Make sure we don't do less than 0 damage
        if(damageDealt < 0){
            damageDealt = 0;
        }
         
        return damageDealt; // Return Statement
    }
 
}