import java.awt.*;
import java.awt.event.*;

public class Square extends Frame
{	public static void main(String[] args)
	{new Square();}

	Square()
	{
	//Sets Title
	super("Square: 250 squares inside each other");
	// Creates window
	addWindowListener(new WindowAdapter()
	{public void windowClosing(WindowEvent e){System.exit(0);}});
	setSize(600, 400);
	add("Center", new CvSquare());
	show();
	}
}

class CvSquare extends Canvas
{
	int maxX, maxY, minMaxXY, xCenter, yCenter;
	
	void initialize()
	{
		//Gets screen dimentions
		Dimension d = getSize();
		//Sets the max X and max Y values of the screen
		maxX = d.width - 1; maxY = d.height - 1;
		//Takes the minimum of the maximum values
		minMaxXY = Math.min(maxX, maxY);
		//Finds the center for X and Y
		xCenter = maxX/2; yCenter = maxY/2;
	}
	//Rounds coordinates to ints
	int iX(float x){return Math.round(x);}
	int iY(float y){return maxY - Math.round(y);}
	
	public void paint (Graphics g)
	{
		initialize();
		//Makes the square 0.95* the previous square and defines this as side.
		float side = 0.95F * minMaxXY,
				//Finds half of the side
				sideHalf = 0.5F * side,
				//Multiplies the sideHalf times sqrt(3)
				height = sideHalf * (float)Math.sqrt(3),
						//Sets up points needed
				xA, yA, xB, yB, xC, yC, xD, yD, xA1, yA1, xB1, yB1, xC1, yC1, xD1, yD1, p, q;
		//Sets the values to the points
		q = 0.02F;
		p = 1-q;
		xA = xCenter - sideHalf;
		yA = yCenter - 0.5F * height;
		xB = xCenter + sideHalf;
		yB = yA;
		xC = xB;
		yC = yCenter + 0.5F * height;
		xD = xA;
		yD = yC;
		for (int i=0; i<250; i++)
		{
			//Draws lines from those points
			g.setColor(Color.RED);
			g.drawLine(iX(xA), iY(yA), iX(xB), iY(yB));
			g.setColor(Color.GREEN);
			g.drawLine(iX(xB), iY(yB), iX(xC), iY(yC));
			g.setColor(Color.BLUE);
			g.drawLine(iX(xC), iY(yC), iX(xD), iY(yD));
			g.setColor(Color.CYAN);
			g.drawLine(iX(xD), iY(yD), iX(xA), iY(yA));
			
			//Creates point locations for next run
			xA1 = p * xA + q * xB;
			yA1 = p * yA + q * yB;
			
			xB1 = p * xB + q * xC;
			yB1 = p * yB + q * yC;
			
			xC1 = p * xC + q * xD;
			yC1 = p * yC + q * yD;
			
			xD1 = p * xD + q * xA;
			yD1 = p * yD + q * yA;
			
			//Sets the points equal to their new location
			xA = xA1;
			xB = xB1;
			xC = xC1;
			xD = xD1;
			
			yA = yA1;
			yB = yB1;
			yC = yC1;
			yD = yD1;
		}
	}
}