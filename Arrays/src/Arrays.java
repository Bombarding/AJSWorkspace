/*import java.util.Random;
 
 
public class Arrays {
 
    public static void main(String[] args) {
         
        // Declare array
        String[] nameList = { "Jeff", "Steve", "Krogar" };
         
        // Declare random generator
        Random randomGenerator = new Random();
         
        // Pick a random index
        int randomIndex = randomGenerator.nextInt(nameList.length);
         
        // Print the element at the random index
        System.out.println(nameList[randomIndex]);
    }
 
}*/

public class Arrays {
 
    public static void main(String[] args) {
         
        String[] nameList = { "Jeff", "Steve", "Krogar" };
         
        // This loop will print each element in the array.
        for( int index = 0; index < nameList.length; index++){
             
            // This will print the name at the current index
            System.out.println(nameList[index]);
             
        }
         
    }
 
}