import java.applet.Applet;
import java.awt.*;
import java.applet.*;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Shield extends Applet
{
	public void paint(Graphics g)
	{
		ashield(g);
		setSize(500,500);
	}
	public void ashield(Graphics g)
	{
		//outer ring
		g.setColor(new Color(200,0,0));
		g.fillOval(0, 0, 500, 500);
		
		//outer right ring
		g.setColor(Color.WHITE);
		g.fillOval(50, 50, 400, 400);
		
		g.setColor(new Color(200,0,0));
		g.fillOval(100, 100, 300, 300);
		
		g.setColor(new Color(0,0,200));
		g.fillOval(137, 137, 225, 225);
		
		g.setColor(Color.WHITE);
		Graphics2D g2 = (Graphics2D) g;
		Polygon star = new Polygon
		(
			new int [] {0,11,48,18,30,0,-30,-18,-48,-11},
			new int [] {-50,-16,-16,6,41,18,41,6,-16,-16},10
		);
		g.translate(250, 250);
		g2.scale(2.2, 2.2);
		g.fillPolygon(star);
	}
}