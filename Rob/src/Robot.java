import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;

public class Robot extends Frame
{
	public static void main(String [] args)
	{
		new Robot();
	}
	Robot()
	{
		super("Robot");
		addWindowListener(new WindowAdapter() 
		{
            public void windowClosing(WindowEvent e)
            {System.exit(0);
            }
         });
		setSize(800, 600);
        add("Center", new CvRobot());
        show();
        
	}
}
class CvRobot extends Canvas
{
	public void paint(Graphics g)
	{
		int robotPositionX;
        int robotPositionY;
        //int finger2PositionX;
        //int finger2PositionY;
        double headingDirection;
        headingDirection = 0;
 
        
		Graphics2D g2d = (Graphics2D)g;
		int [] basex = {0, 0, 50, 50};
        int [] basey = {0, 200, 200, 0};
        Polygon base = new Polygon(basex, basey, 4);
        
        Path2D basep2d = new Path2D.Double(base);
        
        int [] armx = {0,0,50,50};
        int [] army = {0,150,150,0};
        Polygon arm = new Polygon(armx,army,4);
        Path2D armp2d = new Path2D.Double(arm);
        
        int [] handx = {0,0,50,50};
        int [] handy = {0,200,200,0};
        Polygon hand = new Polygon(handx,handy,4);
        Path2D handp2d = new Path2D.Double(hand);
        
        int [] fingerx = {25,0,0,15,15,25};
        int [] fingery = {0,0,50,50,15,15};
        Polygon finger = new Polygon(fingerx,fingery,6);
        Path2D fingerp2d = new Path2D.Double(finger);
        
        int [] finger2x = {25,0,0,15,15,25};
        int [] finger2y = {0,0,50,50,15,15};
        Polygon finger2 = new Polygon(fingerx,fingery,6);
        Path2D finger2p2d = new Path2D.Double(finger2);
        
        //finger2Position2X = 500;
        //finger2Position2Y = 124;
        g.drawPolygon(finger2);
        
        robotPositionX = 375;
        robotPositionY = 212;
        g.drawOval(robotPositionX - 20, robotPositionY - 20, 10, 10);
        
        
        //headingDirection(g, robotPositionX, robotPositionY, 20, (headingDirection / 360) * 2 * Math.PI);
        
        
        AffineTransform tx1 = new AffineTransform();
        tx1.translate(300d, 200d);
        basep2d.transform(tx1);
        handp2d.transform(tx1);
        fingerp2d.transform(tx1);
        
        tx1.rotate(10d);
        tx1.translate(10d, 10d);
        armp2d.transform(tx1);
        
        // Get Base 2 coords
        // Get Arm 3 coords
        // Get x, y distance from Arm 3 to Base 2
        // Translate base by Arm3x-Base2x, Arm3y-Base2y
        // Rotate about Arm3

        // The index of the point where one component connects to another
        final int baseAnchorInd = 3;
        final int armAnchorInd = 0;
        final int baseAnchor2Ind = 2;
        final int armAnchor2Ind = 1;
        
        //coords where one connects to another
        double [] baseAnchorCoordinates = new double[4];
        double [] armAnchorCoordinates = new double[4];
        double [] baseAnchor2Coordinates = new double[4];
        double [] armAnchor2Coordinates = new double[4];
        double [] working = new double[4];

        int i = 0;
        // Step through all the points in the base. Find our anchor point.
        PathIterator pi = basep2d.getPathIterator(null);
        while (!pi.isDone()) 
        {
            pi.currentSegment(working);
            if (i == baseAnchorInd) {
                baseAnchorCoordinates[0] = working[0];
                baseAnchorCoordinates[1] = working[1];
            }
            i++;
            pi.next();
        }
        i=0;
        pi = armp2d.getPathIterator(null);
        //do the same thing for the arm
        while(!pi.isDone())
        {
        	pi.currentSegment(working);
        	if(i==armAnchorInd)
        	{
        		armAnchorCoordinates[0] = working[0];
        		armAnchorCoordinates[1] = working[1];
        	}
        	i++;
        	pi.next();
        }
        // Move the arm by the difference in its anchor point minus the base anchor point (attach the corners)
        AffineTransform tx2 = new AffineTransform();
        double deltaX = baseAnchorCoordinates[0] - armAnchorCoordinates[0];
        double deltaY = baseAnchorCoordinates[1] - armAnchorCoordinates[1];

        tx2.translate(deltaX, deltaY);
        // tx2.rotate(30d, baseAnchorCoord[0], baseAnchorCoord[1]);

        // Apply the actual transform
        armp2d.transform(tx2);

        // Draw it all.
        g2d.draw(basep2d);
        g2d.draw(handp2d);
        g2d.draw(fingerp2d);
        g2d.draw(armp2d);
        g2d.draw(finger2p2d);
        
        
	}
	
	
}