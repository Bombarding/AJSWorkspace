import java.awt.*;
import java.awt.geom.Line2D;
import java.applet.*;

//||||||||||||||||||||||||||||||||||||||||||||||||||||||||
//Everything you need should be here
//g.setColor(Color.ORANGE)
//g.fillOval(xpos,ypos,width,height)
//g.fillRect(xpos,ypos,width,height)
//*Below is creating an array of x and y positions, setting/creating a custom polygon
//||int x[]={98,300,501};//set the x values of triangle
//||int y[]={250,130,250};//set y values of triangle
//||g.fillPolygon(x,y,3);
//g.fillArc(x,y,width,height,start angle,arc angle)
//*above is creating an ...............................................................;

public class OlympicRings extends Applet 
{

	public void paint (Graphics g)
	{
	
		setSize(800,600);
		
		//background
		setBackground(Color.WHITE);
		
		
		
		
			
				
		//red ring
		g.setColor(new Color(220,0,30));
		//g.fillArc(500,50,200,200,200,340);
		g.fillOval(500, 50, 200, 200);
		g.setColor(Color.WHITE);
		g.fillOval(520,70,160,160);
				
		
				
				
				
				//blue ring
				g.setColor(new Color(50,75,250));
				//g.fillArc(50,50,200,200,290,340);
				g.fillOval(50, 50, 200, 200);
				g.setColor(Color.WHITE);
				g.fillOval(70,70,160,160);
						
				
				//green ring
				g.setColor(new Color(10,180,50));
				//g.fillArc(375,150,200,200,108,250);
				//g.fillArc(375,150,200,200,20,68);
				g.fillOval(375, 150, 200, 200);
				g.setColor(Color.WHITE);
				g.fillOval(395,170,160,160);
				
				//black ring
				g.setColor(Color.BLACK);
				//g.fillArc(275,50,200,200,200,85);
				//g.fillArc(275,50,200,200,305,235);
				g.fillOval(275,50,200,200);
				g.setColor(Color.WHITE);
				g.fillOval(295,70,160,160);
				
				//yellow ring
				g.setColor(new Color(250,230,0));
				//g.fillArc(145,145,200,200,108,250);
				//g.fillArc(150,150,200,200,20,68);
				g.fillOval(150, 150, 200, 200);
				g.setColor(Color.WHITE);
				g.fillOval(170,170,160,160);
		
		
		/*//filling in the spaces
		//blue
		g.setColor(new Color(50,75,250));
		int x[]={175,173,193};
		int y[]={230,250,218};
		g.fillPolygon(x,y,3);
	
		
		//black
		//g.setColor(Color.BLACK);
		//int X[]={375,273,393};
		//int Y[]={230,250,218};
		//g.fillPolygon(X,Y,3);
		*/
		
		

	}
	
	

}
