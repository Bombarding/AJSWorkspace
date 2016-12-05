import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class PaintBrush
{

	private Point center;

	private int radius;
	
	private Color color;

	public PaintBrush( Point center, int radius, Color color )
	{
		this.center = new Point( center.x - radius, center.y - radius );
		this.radius = radius;
		this.color = color;
	}

	public void onDraw( Graphics g )
	{
		g.setColor( color );
		//g.drawOval( center.x, center.y, radius * 2, radius * 2 );
		g.fillOval( center.x, center.y, radius * 2, radius * 2 );
	}

}
