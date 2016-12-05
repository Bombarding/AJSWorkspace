// CubRot2.java: Two rotating cubes with double buffering.
//   Uses: Point2D (Section 1.5),
//         Point3D, Rota3D (Section 3.9)

//http://www.medialab.ntua.gr/education/ComputerGraphics/JavaExercises/Java/
//http://drawingfacepencil.xyz/tags/how-to-draw-a-3d-cube-in-java


import java.awt.*;
import java.awt.event.*;

public class CubeRot2 extends Frame
{  public static void main(String[] args){new CubeRot2();}

   CubeRot2()
   {  super("Rotating cubes (double buffering)");
      addWindowListener(new WindowAdapter()
       {public void windowClosing(WindowEvent e)
               {System.exit(0);}});
      add("Center", new CvCubeRot2());
      Dimension dim = getToolkit().getScreenSize();
      setSize(3 * dim.width/4, dim.height/2);
      setLocation(dim.width/8, dim.height/4);
      show();
   }
}

class CvCubeRot2 extends Canvas
   implements Runnable
{  int centerX, centerY, w, h;
   Obj2 obj = new Obj2();
   Image image;
   Graphics gImage;

   double alpha = 0;
   Thread thr = new Thread(this);

   public void run()
{  try
   {  for (;;)
      {  alpha += 0.01;
         repaint();
         Thread.sleep (20);
      }
   }
   catch (InterruptedException e){}
}

CvCubeRot2(){thr.start();}
public void update(Graphics g){paint(g);}

int iX(float x){return Math.round(centerX + x);}
int iY(float y){return Math.round(centerY - y);}

void line(int i, int j)
{  Point2D P = obj.vScr[i], Q = obj.vScr[j];
   gImage.drawLine(iX(P.x), iY(P.y), iX(Q.x), iY(Q.y));
}
public void paint(Graphics g)
{  Dimension dim = getSize();
   int maxX = dim.width - 1, maxY = dim.height - 1;
   centerX = maxX/2; centerY = maxY/2;
   int minMaxXY = Math.min(maxX, maxY);
   obj.d = obj.rho * minMaxXY / obj.objSize;
   obj.rotateCube(alpha);
   obj.eyeAndScreen();
   if (w != dim.width || h != dim.height)
   {  w = dim.width; h = dim.height;
      image = createImage(w, h);
      gImage = image.getGraphics();
   }
   gImage.clearRect (0, 0, w, h);
   // Horizontal edges at the bottom:
   line (0, 1); line (1, 2); line (2, 3); line (3, 0);
   // Horizontal edges at the top:
   line (4, 5); line (5, 6); line (6, 7); line (7, 4);
   // Vertical edges:
   line (0, 4); line (1, 5); line (2, 6); line (3, 7);
   // Same for second cube:
   line (8, 9); line (9, 10); line (10, 11); line (11, 8);
   // Horizontal edges at the top:
      line (12, 13); line (13, 14); line (14, 15); line (15, 12);
      // Vertical edges:
      line (8, 12); line (9, 13); line (10, 14); line (11, 15);
      g.drawImage(image, 0, 0, null);
   }
}

class Obj2 // Contains 3D object data for two cubes
{   float rho, theta=0F, phi=1.2F, d;
    Point3D[] s, w; // World coordinates
    Point2D[] vScr; // Screen coordinates
    float v11, v12, v13, v21, v22, v23,
      v32, v33, v43, // Elements of viewing matrix V.
      xe, ye, ze, objSize = 8;

    Obj2()
    {  s = new Point3D[16]; // Start situation
       w = new Point3D[16]; // After rotation
       vScr = new Point2D[16];
       // Bottom surface:
       s[0] = new Point3D( 1, -3, -1);
       s[1] = new Point3D( 1, -1, -1);
       s[2] = new Point3D(-1, -1, -1);
       s[3] = new Point3D(-1, -3, -1);
       // Top surface:
       s[4] = new Point3D( 1, -3, 1);
       s[5] = new Point3D( 1, -1, 1);
       s[6] = new Point3D(-1, -1, 1);
       s[7] = new Point3D(-1, -3, 1);
       // Bottom surface:
       s[8] = new Point3D( 1, 1, -1);
       s[9] = new Point3D( 1, 3, -1);
       s[10] = new Point3D(-1, 3, -1);
       s[11] = new Point3D(-1, 1, -1);
       // Top surface:
       s[12] = new Point3D( 1, 1, 1);
       s[13] = new Point3D( 1, 3, 1);
       s[14] = new Point3D(-1, 3, 1);
       s[15] = new Point3D(-1, 1, 1);
       rho = 15; // For reasonable perspective effect
   }

void rotateCube(double alpha)
   {   Rota3D.initRotate(s[0], s[4], alpha);
       for (int i=0; i<8; i++)
          w[i] = Rota3D.rotate(s[i]);
       Rota3D.initRotate(s[13], s[9], 2 * alpha);
       for (int i=8; i<16; i++)
          w[i] = Rota3D.rotate(s[i]);
   }

   void initPersp()
   {  float costh = (float)Math.cos(theta),
            sinth = (float)Math.sin(theta),
            cosph = (float)Math.cos(phi),
            sinph = (float)Math.sin(phi);
      v11 = -sinth;
      v12 = -cosph * costh;
      v13 = sinph * costh;
      v21 = costh;
      v22 = -cosph * sinth;
      v23 = sinph * sinth;
      v32 = sinph;
      v33 = cosph;
      v43 = -rho;
   }

   void eyeAndScreen()
   {  initPersp();
      for (int i=0; i<16; i++)
      {  Point3D P = w[i];
         float x = v11 * P.x + v21 * P.y;
         float y = v12 * P.x + v22 * P.y + v32 * P.z;
         float z = v13 * P.x + v23 * P.y + v33 * P.z + v43;
         Point3D Pe = new Point3D(x, y, z);
         vScr[i] = new
            Point2D(-d * Pe.x/Pe.z, -d * Pe.y/Pe.z);
       }
    }
}

