//Battleship - Kratos 7/10/14
import java.util.Scanner;
public class Battleship
{
   static Scanner input = new Scanner(System.in);
   public static void main(String[] args)
   {
      Coordinate[][] board = new Coordinate[10][10]; //10x10 bao
      boolean run = true; //Rerunning game
      int shots; //Counts total moves
      while(run) //Continues until game ends and player says 'n'
      {
         resetBoard(board); //Empties board
         setBoat(board, 5); //Places aircraft carrier
         shots = 0; //Resets shot counter to 0
         while(!checkWin(board)) //Continues until all ships are destroyed
         {
            showBoard(board); //Displays board
            shoot(board); //Asks user where to shoot
            shots++; //Increments shot counter
         }//Close while(!checkWin())
         input.nextLine();
         System.out.println("You won in " + shots + " moves! \n"+
                        "Do you want to play again?");
         if(!(input.nextLine().toLowerCase().charAt(0) == 'y')) //Checks if user wishes to play again
            run = false;
      }//Close while(run)
   }//Close main()
   private static void resetBoard(Coordinate[][] b) //Converts entire board to water
   {
      for(int i = 0; i < b.length; i++)
         for(int j = 0; j < b[i].length; j++)
            b[i][j] = new Coordinate(j, i);
   }//Close resetBoard()
   private static void showBoard(Coordinate[][] b) //Prints board
   {
      for(int i = 0; i < b.length; i++)
      {
         for(int j = 0; j < b[i].length; j++)
            System.out.print(b[i][j] + " ");
         System.out.println();
      }
   }//Close showBoard()
   private static void showBoardHide(Coordinate[][] b) //Prints board but hides ships as water
   {
      for(int i = 0; i < b.length; i++)
      {
         for(int j = 0; j < b[i].length; j++)
            if(b[i][j].getSelf() == 's')
               System.out.print("~ ");
            else
               System.out.print(b[i][j] + " ");
         System.out.println();
      }
   }//Close showBoardHide()
   private static void setBoat(Coordinate[][] b, int size) //Places a boat randomly on the board for any size
   {
      int xpos, ypos; //Farthest up and left coordinates
      int dir = (int)(Math.random()*2); //dir = 0 is horizontal and dir = 1 is vertical
      if(dir == 0)
      {
         xpos = (int)(Math.random()*(b.length - size - 1));
         ypos = (int)(Math.random()*b.length);
         for(int i = 0; i < size; i++)
         {
            b[ypos][xpos+i].setSelf('s');
            b[ypos][xpos+i].life();
         }
      }
      else{
         xpos = (int)(Math.random()*b.length);
         ypos = (int)(Math.random()*(b.length - size - 1));
         for(int j = 0; j < size; j++)
         {
            b[ypos+j][xpos].setSelf('s');
            b[ypos+j][xpos].life();
         }
      }
   }
   private static void shoot(Coordinate[][] b) //Asks user for coordinate to shoot and outputs result
   {
      System.out.println("How many spaces to the right?");
      int x = input.nextInt();
      System.out.println("How many spaces down?");
      int y = input.nextInt();
      while(x < 1 || x > 10 || y < 1 || y > 10 || b[y-1][x-1].getSelf() == 'x' || b[y-1][x-1].getSelf() == '!') //Checks validity of shot
      {
         System.out.println("Invalid choice.");
         System.out.println("How many spaces to the right?");
         x = input.nextInt();
         System.out.println("How many spaces down?");
         y = input.nextInt();
      }//Close validity check
      if(b[y-1][x-1].getSelf() == 's') //Checks if shoot hits
      {
         System.out.println("Hit!");
         b[y-1][x-1].setSelf('!');
         b[y-1][x-1].death();
      }//Close hit check
      else //Missed
      {
         System.out.println("Miss!");
         b[y-1][x-1].setSelf('x');
      }//Close miss
   }//Close shoot()
   private static boolean checkWin(Coordinate[][] b) //Checks for alive ships remaining
   {
      for(int i = 0; i < b.length; i++)
         for(int j = 0; j < b.length; j++)
            if(b[i][j].isAlive())
               return false;
      return true;
   }//Close checkWin()
}//Close Battleship