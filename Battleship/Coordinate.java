//Coordinate - Kratos 8/8/14
public class Coordinate{
   private int xval, yval; //Coordinate postions
   private boolean alive; //Hit or not
   private char self; //Character to display
   protected Coordinate() //Default constructor
   {
      xval = 0;
      yval = 0;
      alive = false;
      self = ' ';
   }//Close default constructor
   protected Coordinate(int x, int y) //water constructor
   {
      xval = x;
      yval = y;
      alive = false;
      self = '~';
   }//Close water constructor
   protected Coordinate(int x, int y, boolean a, char s) //Ship constructor
   {
      xval = x;
      yval = y;
      alive = a;
      self = s;
   }//Close ship constructor
   protected int getX() //Returns x position
   {
      return xval;
   }//Close getX
   protected void setX(int x) //Sets x position
   {
      xval = x;
   }//Close setX
   protected int getY() //Returns y position
   {
      return yval;
   }//Close getY
   protected void setY(int y) //Sets y position
   {
      yval = y;
   }//Close setY
   protected boolean isAlive() //Returns living state
   {
      return alive;
   }//Close isAlive
   protected void life() //Brings coordinate to life
   {
      alive = true;
   }//Close life
   protected void death() //Kills coordinate
   {
      alive = false;
   }//Close death
   protected char getSelf() //Returns character status
   {
      return self;
   }//Close getSelf
   protected void setSelf(char s) //Sets character status
   {
      self = s;
   }//Close setSelf
   public String toString()
   {
      return self + "";
   }
}