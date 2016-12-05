
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BrickBreaker extends JPanel implements KeyListener, ActionListener, Runnable 
{
	//Setting up movement
	static boolean right = false;
	static boolean left = false;
	// ..............
	//Placing the ball initially
	int ballX = 160;
	int ballY = 218;
	//Setting up the bat
	int batX = 160;
	int batY = 245;
	//Setting up the bricks
	int brickX = 70;
	int brickY = 50;
	//brick width and height
	int brickBreadth = 30;
	int brickHeight = 20;
	//creating the Ball and Bat as a rectangle, x,y,w,h
	Rectangle Ball = new Rectangle(ballX, ballY, 5, 5);
	Rectangle Bat = new Rectangle(batX, batY, 40, 5);
	// Rectangle Brick;// = new Rectangle(brickx, bricky, 30, 10);
	Rectangle[] Brick = new Rectangle[12];//12 Bricks

	//reverses......==>
	int movex = -1;
	int movey = -1;
	boolean ballFallDown = false;
	boolean bricksOver = false;
	int count = 0;
	String status;

	BrickBreaker() 
	{

	}
	//main method
	public static void main(String[] args) 
	{
		JFrame frame = new JFrame();//new jframe
		BrickBreaker game = new BrickBreaker();//new game call
		JButton button = new JButton("restart");//button
		frame.setSize(350, 450);//set size of screen
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//default close

		frame.add(game);//add the frame
		frame.add(button, BorderLayout.SOUTH);//give the button placement
		frame.setLocationRelativeTo(null);//default frame operations
		frame.setResizable(false);//above
		frame.setVisible(true);//above
		button.addActionListener(game);//above

		game.addKeyListener(game);//above
		game.setFocusable(true);//above
		Thread t = new Thread(game);//start the thread
		t.start();

	}

	// declaring ball, paddle,bricks

	public void paint(Graphics g) 
	{
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, 350, 450);
		
		g.setColor(Color.blue);
		g.fillOval(Ball.x, Ball.y, Ball.width, Ball.height);
		
		g.setColor(Color.green);
		g.fill3DRect(Bat.x, Bat.y, Bat.width, Bat.height, true);
		
		g.setColor(Color.GRAY);
		g.fillRect(0, 251, 450, 200);
		
		g.setColor(Color.red);
		g.drawRect(0, 0, 343, 250);
		//for brick starting at 0 to how many there are [12]
		for (int i = 0; i < Brick.length; i++)
		{
			if (Brick[i] != null) //if there isnt one
			{	//create one
				g.fill3DRect(Brick[i].x, Brick[i].y, Brick[i].width, Brick[i].height, true);
			}
		}
		//if ball passes paddle or no bricks
		if (ballFallDown == true || bricksOver == true) 
		{
			Font f = new Font("Arial", Font.BOLD, 20);
			g.setFont(f);
			g.drawString(status, 70, 120);
			ballFallDown = false;
			bricksOver = false;
		}

	}

	// /...Game Loop...................


	//run method
	public void run() 
	{

		//call to create bricks
		createBricks();
		
		//ballFallDown == false && bricksOver == false
		while (true) 
		{
			//   if(gameOver == true){return;}\//for the creation of the bricks
			for (int i = 0; i < Brick.length; i++) 
			{//if there are none
				if (Brick[i] != null) 
				{//if the brick hits the ball
					if (Brick[i].intersects(Ball)) 
					{//make the brick in position [i] null
						Brick[i] = null;
						// movex = -movex;
						movey = -movey;//reverse direction
						count++;//add one to count
					}// end of 2nd if..
				}// end of 1st if..
			}// end of for loop..

			

			if (count == Brick.length) 
			{// check if ball hits all bricks
				bricksOver = true;
				status = "YOU WON THE GAME";
				repaint();
			}
			
			repaint();//repaint the screen
			Ball.x += movex;//ball + move in x direction
			Ball.y += movey;//ball + move in y direction
			//if you are moving left
			if (left == true) 
			{

				Bat.x -= 3;
				right = false;//you cant move right
			}
			//if you are moving right
			if (right == true) 
			{
				Bat.x += 3;
				left = false;//you cant move left
			}
			if (Bat.x <= 4) 
			{
				Bat.x = 4;
			} 
			else if (Bat.x >= 298) 
			{
				Bat.x = 298;
			}
			//Ball reverses when strikes the bat
			if (Ball.intersects(Bat)) 
			{
				movey = -movey;
				// if(Ball.y + Ball.width >=Bat.y)
			}
			
			// ball reverses when touches left and right boundary
			if (Ball.x <= 0 || Ball.x + Ball.height >= 343) 
			{
				movex = -movex;
			}// if ends here
			if (Ball.y <= 0) 
			{
				movey = -movey;
			}// if ends here
			if (Ball.y >= 250) 
			{// when ball falls below bat game is over...
				ballFallDown = true;
				status = "YOU LOST THE GAME";
				repaint();
				//    System.out.print("game");
			}
			try 
			{
				Thread.sleep(10);//sleep after 10 second
			} 
			catch (Exception ex) //try catch exception
			{
			}// try catch ends here

		}// while loop ends here

	}

	// loop ends here

	// ///////..... HANDLING KEY EVENTS................//
	@Override
	public void keyPressed(KeyEvent e) 
	{
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_LEFT) 
		{
			left = true;
			// System.out.print("left");
		}
		if (keyCode == KeyEvent.VK_RIGHT) 
		{
			right = true;
			// System.out.print("right");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_LEFT)
		{
			left = false;
		}
		if (keyCode == KeyEvent.VK_RIGHT) 
		{
			right = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) 
	{

	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String str = e.getActionCommand();
		if (str.equals("Restart")) 
		{
			this.restart();
		}
	}
	//restart call
	public void restart() 
	{
		requestFocus(true);
		initializeVariables();
		createBricks();
		repaint();
	}

	public void initializeVariables()
	{
		// ..............
		// variables declaration for ball.................................
		ballX = 160;
		ballY = 218;
		// variables declaration for ball.................................
		// ===============================================================
		// variables declaration for bat..................................
		batX = 160;
		batY = 245;
		// variables declaration for bat..................................
		// ===============================================================
		// variables declaration for brick...............................
		brickX = 70;
		brickY = 50;
		// variables declaration for brick...............................
		// ===============================================================
		// declaring ball, paddle,bricks
		Ball = new Rectangle(ballX, ballY, 5, 5);
		Bat = new Rectangle(batX, batY, 40, 5);
		// Rectangle Brick;// = new Rectangle(brickx, bricky, 30, 10);
		Brick = new Rectangle[12];

		movex = -1;
		movey = -1;
		ballFallDown = false;
		bricksOver = false;
		count = 0;
		status = null;


	}
	public void createBricks()
	{
		// //////////// =====Creating bricks for the game===>.....
		/*
		 * creating bricks again because this for loop is out of while loop in
		 * run method
		 */
		for (int i = 0; i < Brick.length; i++) 
		{
			Brick[i] = new Rectangle(brickX, brickY, brickBreadth, brickHeight);
			if (i == 5) 
			{
				brickX = 70;
				brickY = (brickY + brickHeight + 2);
			}
			if (i == 9) 
			{
				brickX = 100;
				brickY = (brickY + brickHeight + 2);
			}
			brickX += (brickBreadth+1);
		}
	}

}

