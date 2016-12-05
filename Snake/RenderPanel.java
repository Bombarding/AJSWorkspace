//||/////////////////////////////////////////////////////////////////////////||
//||Program Name - Snake													     ||
//||Author - Kizr															 ||
//||Description - This is a program designed to implement techniques learned ||
//||throughout the week as well as advanced techniques. The purpose of this  ||
//||program is to serve as a final project for students.					 ||
//||/////////////////////////////////////////////////////////////////////////||

package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

//The above list are all the things that you will need to import to
//get this program running. Please keep the spelling the exact same.


public class RenderPanel extends JPanel
{

	

	@Override
	protected void paintComponent(Graphics g)
	{
		_______.paintComponent(g);
		
		Snake snake = Snake.snake;

		g.setColor(Color._______;
		
		g.fillRect(0, 0, 800, 700);

		g.setColor(Color._______);

		for (Point point : snake.snakeParts)
		{
			g.fillRect(point.x * Snake.SCALE, point.y * Snake.SCALE, Snake.SCALE, Snake.SCALE);
		}
		
		g.fillRect(snake.head.x * Snake.SCALE, snake.head.y * Snake.SCALE, Snake.SCALE, Snake.SCALE);
		
		g.setColor(Color._____);
		
		g.fillRect(snake.cherry.x * Snake.SCALE, snake.cherry.y * Snake.SCALE, Snake.SCALE, Snake.SCALE);
		
		String string = "Score: " + snake.score + ", Length: " + snake.tailLength + ", Time: " + snake.time / 20;
		
		g.setColor(Color._____);
		
		g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), 10);

		string = "Game Over!";

		if (snake.over)
		{
			g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), (int) snake.dim.getHeight() / 4);
		}

		string = "Paused!";

		if (snake.paused && !snake.over)
		{
			g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), (int) snake.dim.getHeight() / 4);
		}
	}
}