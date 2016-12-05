// CubePersDash.java: A cube in perspective;
//    invisible lines will appear as dashed lines.
// Uses: Point2D (Section 1.5), Point3D (Section 3.9).

// Solution to Exercise 5.1 of
//    Ammeraal, L. (1998) Computer Graphics for Java Programmers,
//       Chichester: John Wiley.

import java.awt.*;
import java.awt.event.*;

public class CubePersDash extends Frame
{  public static void main(String[] args){new CubePersDash();}
 
   CubePersDash()
   {  super("Invisible edges as dashed lines");
      addWindowListener(new WindowAdapter()
         {public void windowClosing(WindowEvent e){System.exit(0);}});
      setLayout(new BorderLayout());
      add("Center", new CvCubePersDash());
      Dimension dim = getToolkit().getScreenSize();
      setSize(dim.width/2, dim.height/2);
      setLocation(dim.width/4, dim.height/4);
      show();
   }
}

class CvCubePersDash extends Canvas
{  int centerX, centerY; 
   Obj obj = new Obj();

   int iX(float x){return Math.round(centerX + x);}
   int iY(float y){return Math.round(centerY - y);}
   
   void line(Graphics g, int i, int j)
   {  Point2D P = obj.vScr[i], Q = obj.vScr[j];
   		
      g.drawLine(iX(P.x), iY(P.y), iX(Q.x), iY(Q.y));
   }

   public void paint(Graphics g) 
   {  Dimension dim = getSize();
      int maxX = dim.width - 1, maxY = dim.height - 1,
          minMaxXY = Math.min(maxX, maxY);
      centerX = maxX/2; centerY = maxY/2;
      obj.d = obj.rho * minMaxXY / obj.objSize;
      obj.eyeAndScreen();  
/*
      // Horizontal edges at the bottom:
      line(g, 0, 1); line(g, 1, 2); line(g, 2, 3); line(g, 3, 0);
      // Horizontal edges at the top:
      line(g, 4, 5); line(g, 5, 6); line(g, 6, 7); line(g, 7, 4);
      // Vertical edges:
      line(g, 0, 4); line(g, 1, 5); line(g, 2, 6); line(g, 3, 7);
*/

      line(g, 0, 1); line(g, 1, 5); line(g, 5, 4); line(g, 4, 0);
      line(g, 1, 2); line(g, 2, 6); line(g, 6, 7); line(g, 7, 4);
      line(g, 5, 6);
      g.setColor(Color.red);
      dash(g, 0, 3); dash(g, 3, 2); dash(g, 3, 7);
      
      
      

   }
   void dash(Graphics g, int i, int j)
   {  Point2D P = obj.vScr[i], Q = obj.vScr[j];
//      dashedLine(g, iX(P.x), iY(P.y), iX(Q.x), iY(Q.y), 8);
      dashedLine(g, P.x, P.y, Q.x, Q.y, 8);
   }

   void dashedLine(Graphics g, float xA, float yA, float xB, float yB, 
      float dashLength)
   {  float u1 = xB - xA, u2 = yB - yA,
         L = (float)Math.sqrt(u1 * u1 + u2 * u2);
      int n = Math.round((L/dashLength + 1)/2);
      float h1 = u1/(2 * n - 1), h2 = u2/(2 * n - 1);
  
      for (int i=0; i<n; i++)
      {  float x1 = xA + 2 * i * h1, y1 = yA + 2 * i * h2,
               x2 = x1 + h1, y2 = y1 + h2;
         drawLine(g, x1, y1, x2, y2);
      }
   }
   
   void drawLine(Graphics g, float x1, float y1, 
      float x2, float y2)
   {  if (x1 != x2 || y1 != y2)
      {  g.drawLine(iX(x1), iY(y1), iX(x2), iY(y2));

      }
   }
}

class Obj // Contains 3D object data
{  float rho, theta=0.3F, phi=1.3F, d, objSize,
         v11, v12, v13, v21, v22, v23, v32, v33, v43;
                    // Elements of viewing matrix V
   Point3D[] w;     // World coordinates
   Point2D[] vScr;  // Screen coordinates
   
   Obj()
   {  w = new Point3D[8];
      vScr = new Point2D[8];
      // Bottom surface:
      w[0] = new Point3D( 1, -1, -1);
      w[1] = new Point3D( 1,  1, -1);
      w[2] = new Point3D(-1,  1, -1);
      w[3] = new Point3D(-1, -1, -1);
      // Top surface:
      w[4] = new Point3D( 1, -1,  1);
      w[5] = new Point3D( 1,  1,  1);
      w[6] = new Point3D(-1,  1,  1);
      w[7] = new Point3D(-1, -1,  1);
      objSize = (float)Math.sqrt(12F); 
         // = sqrt(2 * 2 + 2 * 2 + 2 * 2) 
         // = distance between two opposite vertices.
      rho = 5 * objSize; // For reasonable perspective effect
   }

   void initPersp()
   {  float costh = (float)Math.cos(theta), 
            sinth = (float)Math.sin(theta),
            cosph = (float)Math.cos(phi), 
            sinph = (float)Math.sin(phi);
      v11 = -sinth; v12 = -cosph * costh; v13 = sinph * costh;
      v21 = costh;  v22 = -cosph * sinth; v23 = sinph * sinth; 
                    v32 = sinph;          v33 = cosph; 
                                          v43 = -rho;  
   } 

   void eyeAndScreen()
   {  initPersp();
      for (int i=0; i<8; i++)
      {  Point3D P = w[i];
         float x = v11 * P.x + v21 * P.y,
               y = v12 * P.x + v22 * P.y + v32 * P.z,
               z = v13 * P.x + v23 * P.y + v33 * P.z + v43;
         vScr[i] = new Point2D(-d * x/z, -d * y/z);
      } 
   }
}