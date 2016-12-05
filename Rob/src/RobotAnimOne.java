import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;

//http://zetcode.com/gfx/java2d/transformations/
public class RobotAnimOne extends Frame {

    public static void main(String[] args){
        new RobotAnimOne();
    }

    RobotAnimOne() {
        super("Robot Animation");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){System.exit(0);
            }});

        setSize(800, 600);
        add("Center", new CvRobotAnimOne());
        show();
    }
}

class CvRobotAnimOne extends Canvas {


    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        //set as base
        int [] basex = {0, 0, 75, 75};
        int [] basey = {0, 200, 200, 0};
        Polygon base = new Polygon(basex, basey, 4);
        Path2D basep2d = new Path2D.Double(base);

        //arm currently connected to findAnchor point on base
        int [] armx = {0, 0, 75, 75};
        int [] army = {0, 150, 150, 0};
        Polygon arm = new Polygon(armx, army, 4);
        Path2D armp2d = new Path2D.Double(arm);

        //second hand that overlaps the base currently. need to find placement for corner of arm
        int [] handx = {0, 0, 75, 75};
        int [] handy = {0, 150, 150, 0};
        Polygon hand = new Polygon(handx, handy, 4);
        Path2D handp2d = new Path2D.Double(hand);


        //finger 1
        int [] fingx = {25, 0, 0, 15, 15, 25};
        int [] fingy = {0, 0, 50, 50, 15, 15};
        Polygon fing1= new Polygon(fingx, fingy, 6);
        Path2D fing1p2d = new Path2D.Double(fing1);
        //finger 2
        int [] fingerx = {25, 0, 0, 15, 15, 25};
        int [] fingery = {0, 0, 50, 50, 15, 15};
        Polygon fing2= new Polygon(fingerx, fingery, 6);
        Path2D fing2p2d = new Path2D.Double(fing2);


        // Layout robot parts visibly
        AffineTransform tx1 = new AffineTransform();
        tx1.translate(400d, 200d);
        basep2d.transform(tx1);

        /*
        handp2d.transform(tx1);
        fing1p2d.transform(tx1);
        fing2p2d.transform(tx1);

        tx1.rotate(10d);
        tx1.translate(10d, 10d);
        armp2d.transform(tx1);
        */

        tx1 = new AffineTransform();
        tx1.rotate(30d);
        armp2d.transform(tx1);
        tx1.rotate(20d);
        handp2d.transform(tx1);
        tx1 = new AffineTransform();
        tx1.rotate(30d);
        fing1p2d.transform(tx1);
        tx1 = new AffineTransform();
        tx1.scale(-1, 1);
        tx1.rotate(20d);
        fing2p2d.transform(tx1);

        // The index of the point where one component connects to another
        // I.e., baseToArmInd = 2 means that the arm's anchor point will connect to the third (because of zero-indexing)
        // Point on the base

        final int baseToArmInd = 3;
        final int armToBaseInd = 3;

        final int armToHandInd = 1;
        final int handToArmInd = 0;

        final int handToFing1Ind = 1;
        final int fing1ToHandInd = 0;

        final int handToFing2Ind = 2;
        final int fing2ToHandInd = 0;


        // The coordinates where one component connects to another
        // Anchor coord is the coordinates of the anchor point on the "fixed" component,
        // Comp coord is the coordinates anchor point of the component being moved to the fixed component
        double [] anchorCoord = new double[2];
        double [] compCoord = new double[2];

        findAnchor(basep2d, anchorCoord, baseToArmInd);
        findAnchor(armp2d, compCoord, armToBaseInd);
        snapToAnchor(armp2d, anchorCoord, compCoord);

        findAnchor(armp2d, anchorCoord, armToHandInd);
        findAnchor(handp2d, compCoord, handToArmInd);
        snapToAnchor(handp2d, anchorCoord, compCoord);

        findAnchor(handp2d, anchorCoord, handToFing1Ind);
        findAnchor(fing1p2d, compCoord, fing1ToHandInd);
        snapToAnchor(fing1p2d, anchorCoord, compCoord);

        findAnchor(handp2d, anchorCoord, handToFing2Ind);
        findAnchor(fing2p2d, compCoord, fing2ToHandInd);
        snapToAnchor(fing2p2d, anchorCoord, compCoord);

        // Draw it all.
        g2d.draw(basep2d);
        g2d.draw(handp2d);
        g2d.draw(fing1p2d);
        g2d.draw(fing2p2d);
        g2d.draw(armp2d);

    }

    void findAnchor(Path2D p2d, double[] anchorCoords, int anchorInd) {
        double [] working = new double[2];
        int i = 0;

        // Step through all the points in the given part. Find our findAnchor point.
        PathIterator pi = p2d.getPathIterator(null);
        while (!pi.isDone()) {
            pi.currentSegment(working);
            if (i == anchorInd) {
                anchorCoords[0] = working[0];
                anchorCoords[1] = working[1];
            }
            i++;
            pi.next();
        }
    }

    void snapToAnchor(Path2D component, double[] anchorCoords, double[] componentCoords) {
        AffineTransform tx2 = new AffineTransform();
        double deltaX = anchorCoords[0] - componentCoords[0];
        double deltaY = anchorCoords[1] - componentCoords[1];
        tx2.translate(deltaX, deltaY);
        component.transform(tx2);
    }
    /*public abstract void rotate(Path2D p2d, double [] anchorCoords,int anchorINd)
    {
    	translate(x,y);
    	rotate(p2d);
    	translate(-x,-y)
    }*/
}