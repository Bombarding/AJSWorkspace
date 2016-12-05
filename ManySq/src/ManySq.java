
//Hw 1
//Many Squares
//n=10, k=50, q=0.1

import java.awt.*;
import java.awt.event.*;

public class ManySq extends Frame
{  public static void main(String[] args)
   {  if (args.length != 3)
      {  System.out.println("Supply n, k and q as arguments");
         System.exit(1);
      }
      int n = Integer.valueOf(args[0]).intValue(),
          k = Integer.valueOf(args[1]).intValue();
      float q = Float.valueOf(args[2]).floatValue();
      new ManySq(n, k, q);
    }
    ManySq(int n, int k, float q)
    {  super("ManySq: Many squares");
       addWindowListener(new WindowAdapter()
        {public void windowClosing(
                WindowEvent e){System.exit(0);}});
       add("Center", new CvManySq(n, k, q));
       setSize (1280, 720);
       setBackground (new Color(0,0,0));
       show();
    }
  }

  class CvManySq extends Canvas
  {  
 
 
 int centerX, centerY, n, k;
     float p0, q0;

     CvManySq(int nn, int kk, float qq){n=nn; k=kk; q0=qq; p0 =
                    1-q0;}

     int iX(float x){return Math.round(centerX + x);}
     int iY(float y){return Math.round(centerY - y);}

     public void paint(Graphics g)
     {  Dimension d = getSize();
        int maxX = d.width - 1, maxY = d.height - 1,
           minMaxXY = Math.min(maxX, maxY);
        centerX = maxX/2; centerY = maxY/2;

        float r = 0.5F * minMaxXY / n;
        for (int x=0; x<n; x++)
        for (int y=0; y<n; y++)
        {  float xCnew = (2 * x - n + 1) * r,
                 yCnew = (2 * y - n + 1) * r,
              xA, yA, xB, yB, xC, yC, xD, yD,
              xA1, yA1, xB1, yB1, xC1, yC1, xD1, yD1, p=p0, q=q0;
           if (x % 2 + y % 2 == 1){p = q0; q = p0;}
           xA = xD = xCnew - r; xB = xC = xCnew + r;
           yA = yB = yCnew - r; yC = yD = yCnew + r;
           for (int i=0; i<k; i++)
           {  g.setColor (new Color(179,0,255));
         g.drawLine(iX(xA), iY(yA), iX(xB), iY(yB));
         g.setColor (new Color(255,0,0));
         g.drawLine(iX(xB), iY(yB), iX(xC), iY(yC));
         g.setColor (new Color(255,137,0));
         g.drawLine(iX(xC), iY(yC), iX(xD), iY(yD));
         g.setColor (new Color(77,255,0));
         g.drawLine(iX(xD), iY(yD), iX(xA), iY(yA));
              xA1 = p * xA+q * xB; yA1 = p * yA+q * yB;
              xB1 = p * xB+q * xC; yB1 = p * yB+q * yC;
              xC1 = p * xC+q * xD; yC1 = p * yC+q * yD;
              xD1 = p * xD+q * xA; yD1 = p * yD+q * yA;
              xA = xA1; xB = xB1; xC = xC1; xD = xD1;
              yA = yA1; yB = yB1; yC = yC1; yD = yD1;
           }
        }
    }
}