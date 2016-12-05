import java.awt.Graphics;
import java.util.Timer.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import javax.swing.JPanel;
import javax.swing.*;

public class Move extends JPanel implements ActionListener
{
	Timer t = new Timer(10,this);
	double x=450, y=0, velX=-2, velY=-2;
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(Color.BLUE);
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
		if(y<0 || y>340)
		{
			velY = -velY;
		}
		x+=velX;
		y+=velY;
		repaint();
		
	}
	
}