import java.awt.*;
import java.awt.event.*;

public class RangeFinder extends Frame
{  public static void main(String[] args) {new RangeFinder(); }
   
    RangeFinder()
    {  super("Robot Range Finder");
       addWindowListener(new WindowAdapter()
        {public void windowClosing(
                WindowEvent e){System.exit(0);}});
       
       setSize (640, 480);
       add("Center", new CvRangeFinder());
       //setBackground (new Color(0,0,0));
       show();
    }
  }

class CvRangeFinder extends Canvas
{
	public void paint(Graphics g)
	{
		int n=20;
		double heading = 0.9;
		int circleCentX = 310;
		int circleCentY = 250;
		Dimension d = getSize();
		int [] xv = {100,500,500,100};
		int [] yv = {100,100,400,400};
		Polygon p = new Polygon(xv,yv,4);
		g.drawPolygon(p);
		g.drawOval(300, 240, 20, 20);
		
		for(int i=0; i<n; i++)
		{
			double angle = (2*Math.PI)/n;
			angle *= 1;
			angle += heading;
			int len= 1;
			double endX, endY;
			do
			{
				endX = circleCentX + len * Math.cos(angle);
				endY = circleCentY + len * Math.sin(angle);
				len++;
			}while(p.contains(endX,endY));
			g.drawLine(circleCentX, circleCentY, (int)endX, (int)endY);
		}
	
	}
	
}