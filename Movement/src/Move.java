import java.awt.Graphics;
import java.util.Timer.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class Move extends JPanel implements ActionListener
{
	Timer t = new Timer(10,this);
	double x=450, y=0, velX = 0, velY = 2;
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		Ellipse2D circle = new Ellipse2D.Double(x,y,40,40);
		g2.fill(circle);
		t.start();
		
	}
	public void actionPerformed(ActionEvent e)
	{
		if(x<0 || x>560)
		{
			velX = -velX;
		}
		if(y<0 || y>360)
		{
			velY = -velY;
		}
		x+=velX;
		y+=velY;
		repaint();
	}
	
}
