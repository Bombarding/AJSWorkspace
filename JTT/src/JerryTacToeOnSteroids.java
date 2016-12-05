/*
	KiZr
	Jerry Tac Toe

 */
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;

class Board extends JPanel
{
	//Declaring the initial X and Y coordinates of the cirlces. 
	public static final int[] DOTS_X = {125, 300, 475, 212, 300, 387, 125, 300, 475};
	public static final int[] DOTS_Y = {100, 100, 100, 212, 212, 212, 325, 325, 325};
	//variable for the initial dot size
	public static final int DOT_SIZE = 50;
	//starting the board conditions
	public static final int EMPTY= 0;
	public static final int USER= 1;
    public static final int COMP= 2;
    public static final int HOVER = 3;
    //initialize the number of spaces
	int [] spaces=new int [9];
	//create some custom colors
	Color c, custom1, custom2, custom3, custom4;
	//initialize number as a string
	String number;
	//method to reset colors
	public void resetColor () { for(int i=0; i<9; i++) { spaces[i]=EMPTY; } }
	//setting color c to x
	public void setColor(Color x) { c=x; }
 	//setting face of buttons (number to x)
	public void setFace(String x) { number=x; }
	//board method
	 public Board()
	 {
	 	super();//call the super (title bar)
		setPreferredSize(new Dimension(600,400));//set the dimension of the board
		number="";//number equals empty
	 }
	 //method for player colors
	 public void humancolors(int Color,int player) { spaces[Color]=player; }
	 //method for computers colors
	 public void compcolors(int Color, int player) { spaces[Color]=player; }

	 public void paintComponent(Graphics g) //draws board
	 {
		custom1 = new Color(229,228,226); //RGB Values for Platinum
		custom2 = new Color(128,128,255); //RGB Values for Dodger Blue
		custom3 = new Color(18,188,103); //Custom Green-Blue Color
		custom4 = new Color(228,27,87); //Custom Lighter Red Color
		g.setColor(custom2); //Set Color to Silver
    	g.fillRect(50,30, 500,400); //Board Outer Rectangle Colored Silver
    	
    	//dots stored in an array. why do they start at 0? array positions always start at 0 unless specificed
		//Lines (Drawn First So That They Are Under Button Spots and Numbers)
    	g.setColor(Color.black); //Black Lines
    	g.drawLine(DOTS_X[0], DOTS_Y[0], DOTS_X[2], DOTS_Y[2]); //1 - 2 - 3
    	g.drawLine(DOTS_X[0], DOTS_Y[0], DOTS_X[7], DOTS_Y[7]); //1 - 4 - 8
    	g.drawLine(DOTS_X[0], DOTS_Y[0], DOTS_X[8], DOTS_Y[8]); //1 - 5 - 9
    	g.drawLine(DOTS_X[1], DOTS_Y[1], DOTS_X[6], DOTS_Y[6]); //2 - 4 - 7
    	g.drawLine(DOTS_X[1], DOTS_Y[1], DOTS_X[8], DOTS_Y[8]); //2 - 6 - 9
    	g.drawLine(DOTS_X[2], DOTS_Y[2], DOTS_X[6], DOTS_Y[6]); //3 - 5 - 7
    	g.drawLine(DOTS_X[2], DOTS_Y[2], DOTS_X[7], DOTS_Y[7]); //3 - 6 - 8
    	g.drawLine(DOTS_X[3], DOTS_Y[3], DOTS_X[5], DOTS_Y[5]); //4 - 5 - 6
    	g.drawLine(DOTS_X[6], DOTS_Y[6], DOTS_X[8], DOTS_Y[8]); //7 - 8 - 9


    	
    	//Button Spots
    	g.setFont(new Font(g.getFont().getName(), Font.BOLD, 30));
    	for (int a = 0; a <  9; a++) //for dot starting at 0 -> 9
    	{
    		if (spaces[a] == EMPTY) //if empty set color to blue
    			g.setColor(Color.blue);
    		else if (spaces[a] == USER) //selected by user make it custom 3
    			g.setColor(custom3);
    		else if (spaces[a] == COMP)//selected by comp make it custom 4
    			g.setColor(custom4);
    		else if (spaces[a] == HOVER)//if hovering over a space, make it custom 1
    			g.setColor(custom1);

			g.fillOval(DOTS_X[a] - DOT_SIZE/2, DOTS_Y[a] - DOT_SIZE/2, DOT_SIZE, DOT_SIZE);//fill the dots
			g.setColor(Color.white);
			if(spaces[a] ==EMPTY)//if empty
				g.drawString(""+(a+1), DOTS_X[a]-8, DOTS_Y[a] + 12);
			else if (spaces[a] ==USER)//if user
				g.drawString("X", DOTS_X[a]-8, DOTS_Y[a] + 12);
			else if (spaces[a] ==COMP)//if comp
				g.drawString("O", DOTS_X[a]-8, DOTS_Y[a] + 12);
    	}
	 }
}

public class JerryTacToeOnSteroids extends JFrame //main class
{	
	//initial conditions
	private static int x, y;
	public static final int EMPTY= 0;
	public static final int USER= 1;
	public static final int COMP= 2;
	public static final int DRAW= 3;
	int move;
	int pn= 1;
	int t = 0;
	//activate the mouse
	MouseListener mouseListener = new MouseListener();
	//JTextField t;
	Board JTTB;
	int [] places = new int [9];
	//int [] check= new int [9];
	//listen for the mouse so if clicked and released 
	class MouseListener extends MouseInputAdapter
	{	//method for if the mouse is released
		 public void mouseReleased(MouseEvent mouseEvent)
		 {
		 	JPanel thepanel= (JPanel)mouseEvent.getSource();
		 	x = mouseEvent.getX();
			y = mouseEvent.getY();

			for (int a = 0; a < 9; a++)
				if ((x-Board.DOTS_X[a])*(x-Board.DOTS_X[a])+(y-Board.DOTS_Y[a])*(y-Board.DOTS_Y[a]) < Board.DOT_SIZE*Board.DOT_SIZE/4)
					HumanTurn(a);
		 }
		 //method for if you are moving the mouse
		 public void mouseMoved(MouseEvent mouseEvent)
		 {
		 	JPanel thepanel= (JPanel)mouseEvent.getSource();
		 	x = mouseEvent.getX();
			y = mouseEvent.getY();

			for (int a = 0; a < 9; a++)
				if (places[a] == EMPTY)
					JTTB.humancolors(a, 0);
			for (int a = 0; a < 9; a++)
				if(places[a] == EMPTY && (x-Board.DOTS_X[a])*(x-Board.DOTS_X[a])+(y-Board.DOTS_Y[a])*(y-Board.DOTS_Y[a]) < Board.DOT_SIZE*Board.DOT_SIZE/4)
					JTTB.humancolors(a, 3);
			repaint();
		 }
	};
	
	public JerryTacToeOnSteroids() //Constructor
    {
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,640);
		setTitle("Jerry-Tac-Toe: By Kizr");

		JTTB=new Board();//create new board every time

		JPanel panel=new JPanel();
		panel.setLayout( new BorderLayout() );
		JPanel panel1=new JPanel();
		panel1.setLayout( new BorderLayout() );
		//set the buttons
		JPanel button1panel=new JPanel();
		JPanel button2panel=new JPanel();
		JPanel button3panel=new JPanel();
		//tell the user what the symbols are
		button1panel.add(new JLabel("User is Green X's & "));
		button1panel.add(new JLabel("Computer is Red O's"));
		button3panel.add(JTTB);
		//coordinates for screen
		panel1.add(button1panel, "Center");
		panel1.add(button2panel, "South");
		panel.add(button3panel, "North");
		panel.add(panel1, "South");

		JTTB.addMouseMotionListener(mouseListener);
		JTTB.addMouseListener(mouseListener);

		Container c = getContentPane();
		//for loop to check if the spaces are empty
		for(int i = 0; i<9; i++){places[i] = EMPTY;}
		c.add(panel);
		setSize(600,550);
		ResetBoard();
  	}

	//Only call this function if there is a move to be made.
	public void CompTurn() // plays comp turn.
	{
		int n;

		do
		{
			n= (int)(choosemove()); //choosemove(places);  //(int)(9*Math.random());
			//System.out.println(n);
		} while (places[n] != EMPTY);

		places[n]= COMP;
		move++;
		JTTB.compcolors(n, COMP);
		repaint();

		if((BoardFull()==true) ||(Wins(USER)==true)||(Wins(COMP)==true))
		{
			WhoWins();
			PlayAgain();
		}
	}

	//Only call this function if there is a move to be made.
	public void HumanTurn(int c) // plays User turn.
	{
		if(places[c] != EMPTY)
		{
			repaint();
		}
		else if(places[c] == EMPTY)
		{
			places[c] = USER;
			move++;
			JTTB.humancolors(c, USER);
			repaint();

			if(BoardFull() || Wins(USER) || Wins(COMP))
			{
				WhoWins();
				PlayAgain();
			}
			else
				CompTurn();
		}
	}

	public void WhoWins() // determines and prints out who wins, whether player wins, comp wins, or draw.
	{
		if (Wins(USER))
		{
			System.out.println("");
			System.out.println("You Win!");
			t = 1;
		}
		else if (Wins(COMP))
		{
			System.out.println("");
			System.out.println("Computer Wins!");
			t = 2;
		}
		else if (BoardFull())
		{
			System.out.println("");
			System.out.println("Draw Game.");
			t = 3;
		}
	}

	public boolean Wins(int player) //checks winning combinations.
	{
		if(places[0]==player && places[3]==player && places[7]==player)
			return true;
		if(places[0]==player && places[1]==player && places[2]==player)
			return true;
		if(places[0]==player && places[4]==player && places[8]==player)
			return true;
		if(places[3]==player && places[4]==player && places[5]==player)
			return true;
		if(places[2]==player && places[5]==player && places[7]==player)
			return true;
		if(places[1]==player && places[3]==player && places[6]==player)
			return true;
		if(places[1]==player && places[5]==player && places[8]==player)
			return true;
		if(places[2]==player && places[4]==player && places[6]==player)
			return true;
		if(places[6]==player && places[7]==player && places[8]==player)
			return true;

		return false;
	}

	public void PlayAgain() // asks to play again and resets board if yes.
	{
		int result = 5;

		do
		{
			if(t==1)
			{
				result = JOptionPane.showConfirmDialog(null, "You Win! Can You Do It Again?", "Play Again?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			}
			else if (t==2)
			{
				result = JOptionPane.showConfirmDialog(null, "Computer Wins! Want Revenge?", "Play Again?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			}
			else if (t==3)
			{
				result = JOptionPane.showConfirmDialog(null, " Draw Game. Don't Quit on A Tie.", "Play Again?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			}
		} while (result < 0);

		if (result == 0)
		{
			JTTB.resetColor();
			repaint();
			ResetBoard();
			pn++;
			WhoFirst();
		}
		else if (result == 1)
		{
			System.out.println("");

    		if (pn==1)
				System.out.print("Thanks for playing "+pn+" game!");
			else if (pn>1)
				System.out.print("Thanks for playing "+pn+" games!");

			System.out.println("");
			System.exit(0);
		}

	}

	public void WhoFirst()
	{
		int result;

		do
		{
			result = JOptionPane.showConfirmDialog(null, "Do You Want to go First?", "Do You Want to go First?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		} while (result < 0);

		if (result==0)
		{
			JTTB.resetColor();
			repaint();
			ResetBoard();
		}
		else if (result == 1)
		{
			JTTB.resetColor();
			repaint();
			ResetBoard();
			CompTurn();
		}

	}

	public boolean BoardFull() // checks to see if board is full.
	{
		for(int i=0; i<9; i++)
		{
			if(places[i]==EMPTY)
				return false;
		}
		return true;
	}

	public void ResetBoard()
	{
		for(int i = 0; i < 9; i++)
			places[i] = EMPTY;
		move = 1;
	}

	public static void main(String [] args)
	{
		JerryTacToeOnSteroids Game = new JerryTacToeOnSteroids();
		Game.pack();
		Game.setVisible(true);
		Game.setLocationRelativeTo(null);
		Game.WhoFirst();
	}

	public int choosemove()
	{
		int block = -1;
		int win = -1;

		for(int j = 0; j < 9; j++)
		{
			if(places[j] == EMPTY)
			{
				places[j] = USER;
				if (Wins(USER) == true)
					block=j;
				places[j]=COMP;
				if (Wins(COMP) == true)
					win=j;
				places[j]=EMPTY;
			}
		}

		if (win >= 0)
			return win;
		if (block >= 0)
			return block;

		if (move == 2)
		{
			if (places[0] == USER)
				return (new int[]{5,6})[(int)(2*Math.random())];
			if (places[1] == USER)
				return (new int[]{4,7})[(int)(2*Math.random())];
			if (places[2] == USER)
				return (new int[]{3,8})[(int)(2*Math.random())];
			if (places[3] == USER)
				return (new int[]{2,8})[(int)(2*Math.random())];
			if (places[4] == USER)
				return (new int[]{1,7})[(int)(2*Math.random())];
			if (places[5] == USER)
				return (new int[]{0,6})[(int)(2*Math.random())];
			if (places[6] == USER)
				return (new int[]{5,0})[(int)(2*Math.random())];
			if (places[7] == USER)
				return (new int[]{1,4})[(int)(2*Math.random())];
			if (places[8] == USER)
				return (new int[]{2,3})[(int)(2*Math.random())];
		}

		if (move == 3)
		{
			if (places[0] == COMP && places[5] == USER)
				return 6;
			if (places[0] == COMP && places[6] == USER)
				return 5;
			if (places[1] == COMP && places[4] == USER)
				return 7;
			if (places[1] == COMP && places[7] == USER)
				return 4;
			if (places[2] == COMP && places[3] == USER)
				return 8;
			if (places[2] == COMP && places[8] == USER)
				return 3;
			if (places[3] == COMP && places[2] == USER)
				return 8;
			if (places[3] == COMP && places[8] == USER)
				return 2;
			if (places[4] == COMP && places[1] == USER)
				return 7;
			if (places[4] == COMP && places[7] == USER)
				return 1;
			if (places[5] == COMP && places[0] == USER)
				return 6;
			if (places[5] == COMP && places[6] == USER)
				return 0;
			if (places[6] == COMP && places[5] == USER)
				return 0;
			if (places[6] == COMP && places[0] == USER)
				return 5;
			if (places[7] == COMP && places[1] == USER)
				return 4;
			if (places[7] == COMP && places[4] == USER)
				return 1;
			if (places[8] == COMP && places[3] == USER)
				return 2;
			if (places[8] == COMP && places[2] == USER)
				return 3;
		}

		return (int)(9*Math.random());
  	}
}



