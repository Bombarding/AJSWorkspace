//||/////////////////////////////////////////////////////////////////////////||
//||Program Name - Snake													 ||
//||Author - Kizr															 ||
//||Description - This is a program designed to implement techniques learned ||
//||throughout the week as well as advanced techniques. The purpose of this  ||
//||program is to serve as a final project for students.					 ||
//||/////////////////////////////////////////////////////////////////////////||

package snake;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;
//The above list are all the things that you will need to import to
//get this program running. Please keep the spelling the exact same.


//Main method. You must implement ActionListener and KeyListener to allow
//you to use specific keys to set for movement
public class Snake implements ActionListener, KeyListener
{
	//Creating game snake
	public static Snake ______;
	//creating jframe
	public JFrame jframe;
	//creating renderpanel
	public RenderPanel renderPanel;
	//starting the timer
	public Timer _____ = new Timer(20, this);
	//creating an array list to store the snake
	public ArrayList<Point> snakeParts = new ArrayList<Point>();
	//setting initial move conitions
	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SCALE = 10;
	//initial snake conditions
	public int ticks = 0, direction = DOWN, score, tailLength = 10, time;
	//creating the head of snake and the objective block
	public Point head, cherry;
	//starting random
	public Random random;
	//boolean T/F to determine game state
	public boolean over = false, paused;
	//create dimensions
	public Dimension dim;
	//snake method
	public Snake()
	{	
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		jframe = new JFrame("________");
		jframe.setVisible(_____);
		jframe.setSize(805, 700);
		jframe.setResizable(false);
		jframe.setLocation(dim.width / 2 - jframe.getWidth() / 2, dim.height / 2 - jframe.getHeight() / 2);
		jframe.add(renderPanel = new RenderPanel());
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.addKeyListener(this);
		startGame();
	}
	//method to start the game
	public void startGame()
	{
		over = ______;
		paused = ______;
		time = 0;
		score = 0;
		tailLength = ____;
		ticks = 0;
		direction = DOWN;
		head = new Point(0, -1);
		_______ = new Random();
		snakeParts.clear();
		cherry = new Point(random.nextInt(79), random.nextInt(66));
		timer.start();
	}

	//Action performed method holds all key events
	public void actionPerformed(ActionEvent arg0)
	{
		renderPanel.repaint();
		ticks++;

		if (ticks % 2 == 0 && head != null && !over && !paused)
		{
			time++;

			snakeParts.add(new Point(head.x, head.y));
			//conditions for movement if moving up
			if (direction == UP)
			{
				if (head.y - 1 >= 0 && noTailAt(head.x, head.y - 1))
				{
					head = new Point(head.x, head.y - 1);
				}
				else
				{
					over = true;

				}
			}
			//conditions for movement if moving down
			if (direction == _____)
			{
				if (____.__ + 1 < 67 && noTailAt(____.__, ____.__ + 1))
				{
					head = new Point(____.__, ____.__ + 1);
				}
				else
				{
					over = _____;
				}
			}
			//conditions for movement if moving left
			if (direction == _____)
			{
				if (head.x - 1 >= 0 && noTailAt(head.x - 1, head.y))
				{
					head = new Point(head.x - 1, head.y);
				}
				else
				{
					over = true;
				}
			}
			//conditions for movement if moving right
			if (direction == ______)
			{
				if (____.__ + 1 < 80 && noTailAt(____.__ + 1, ____.__))
				{
					______ = new Point(____.__ + 1, ____.__);
				}
				else
				{
					over = _______;
				}
			}
			//if the snake is greater than the tail length
			if (snakeParts.size() > _________)
			{
				snakeParts.remove(0);

			}
			//if the objective block is active
			if (______ != null)
			{
				if (head.equals(cherry))
				{
					score += 10;
					tailLength++;
					cherry.setLocation(random.nextInt(79), random.nextInt(66));
				}
			}
		}
	}
	//boolean T/F to determine if there is no tail
	public boolean noTailAt(int x, int y)
	{
		for (Point point : snakeParts)
		{
			if (point.equals(new Point(x, y)))
			{
				return ______;
			}
		}
		return true;
	}
	//main method calling snake() method
	public static void main(String[] args)
	{
		______ = new Snake();
	}

	//method holding all key press information
	public void keyPressed(KeyEvent e)
	{	//setting variable = i
		int i = e.getKeyCode();
		//if i = A,L, Not RIGHT
		if ((i == KeyEvent.VK_A || i == KeyEvent.VK_LEFT) && direction != RIGHT)
		{
			direction = LEFT;
		}
		//if i = D,R, Not LEFT
		if ((___ == KeyEvent.VK_--- || __ == KeyEvent.VK_-----) && direction != LEFT)
		{
			direction = _____;
		}
		//if i = W,UP, Not DOWN
		if ((___ == KeyEvent.VK_--- || i == KeyEvent.VK_---) && direction != DOWN)
		{
			direction = UP;
		}
		//if i = S,DOWN, Not UP
		if ((___ == KeyEvent.VK_--- || ____ == KeyEvent.VK_----) && direction != UP)
		{
			direction = _____;
		}
		//if space is pressed
		if (i == KeyEvent.VK_SPACE)
		{	//if its over start again
			if (over)
			{
				________(); // what method call do we want. if its over and space is pressed, do what?
			}
			else//else pause the game
			{
				_______ = !paused; //else what?
			}
		}
	}

	//Key release override
	public void keyReleased(KeyEvent e)
	{
	}

	//Key Type override
	public void keyTyped(KeyEvent e)
	{
	}

}