package robot;

import com.sun.opengl.util.GLUT;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

/*
 * RobotListener.java
 * This program illustrates how to create and manipulate  a three part robot 
 * arm. The code structure is designed based on a schene graph hierarchy.
 */
import javax.swing.JFrame;

/**
 *
 * @author gorr
 */
public class RobotListener extends KeyAdapter implements GLEventListener {
   
    GLU glu = new GLU();
    GLUT glut = new GLUT();
    GLCanvas canvas;
    /** Rotation angle of entire arm around y-axis  */
    public double rotBase = 0;
    /** Rotation angle of entire arm based on coordinate system of base */
    public double rotBottom = 0;
    /** Rotation angle of middle and upper arm based on coordinate system of lower arm  */
    public double rotMid = 0;
    /** Rotation angle of upper arm based on coordinate system of middle arm  */
    public double rotTop = 0;
    /** Amount angle is changed each time a button is pressed. */
    public double dAngle = 5;

    int frameWidth = 640;
    int frameHeight = 480;
   
    public RobotListener(GLCanvas c) {
        canvas = c;
        c.setSize(frameWidth,frameHeight);
    }

    public void init(GLAutoDrawable d) {
        GL gl = d.getGL();
        gl.glClearColor(.5f, .5f, .5f, 0);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(20.0f, 1.0f, 1.0f, 200.0f);
        gl.glMatrixMode(GL.GL_MODELVIEW);
    }

    public void reshape(GLAutoDrawable d, int x, int y, int width, int height) {
        frameWidth = width;
        frameHeight = height;
        GL gl = d.getGL();
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(60., (double) width / height, 1.0, 300.);
        gl.glMatrixMode(GL.GL_MODELVIEW);
    }

    public void display(GLAutoDrawable d) {
        GL gl = d.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
        glu.gluLookAt(20.0, 10.0, 20.0, 0.0, 10.0, 0.0, 0.0, 1.0, 0.0);

        makeGround(gl);

        coordAxes(gl);

        gl.glRotated(rotBase, 0, 1, 0);
        gl.glRotated(rotBottom, 1, 0, 0);

        makeBase(gl);

        gl.glTranslated(0, 10, 0);
        gl.glRotated(rotMid, 1, 0, 0);

        makeMiddle(gl);

        gl.glTranslated(0, 6, 0);
        gl.glRotated(rotTop, 1, 0, 0);

        makeTop(gl);
        
    }

    public void makeGround(GL gl) {
        gl.glPushMatrix();

        gl.glTranslated(0, -.2, 0);
        gl.glScaled(30, .1, 30);

        gl.glColor3d(.75, .75, .75);
        glut.glutSolidCube(1);

        gl.glPopMatrix();
    }

    public void coordAxes(GL gl) {
        gl.glBegin(GL.GL_LINES); // coordinate axes

        gl.glColor3d(1, 0, 0);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(30, 0, 0);

        gl.glColor3d(0, 1, 0);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(0, 30, 0);

        gl.glColor3d(0, 0, 1);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(0, 0, 30);
        gl.glEnd();
    }

    public void makeBase(GL gl) {
        gl.glPushMatrix();

        gl.glTranslated(0, 5, 0);
        gl.glScaled(1, 10, 1);

        gl.glColor3d(1, 1, 0);
        glut.glutSolidCube(1);
        gl.glColor3d(0, 0, 0);
        glut.glutWireCube(1);

        gl.glPopMatrix();
    }

    public void makeMiddle(GL gl) {
        gl.glPushMatrix();

        gl.glTranslated(0, 3, 0);
        gl.glScaled(1, 6, 1);

        gl.glColor3d(1, 0, 1);
        glut.glutSolidCube(1);
        gl.glColor3d(0, 0, 0);
        glut.glutWireCube(1);

        gl.glPopMatrix();
    }

    public void makeTop(GL gl) {
        gl.glPushMatrix();

        gl.glTranslated(0, 2, 0);
        gl.glScaled(1, 4, 1);

        gl.glColor3d(0, 1, 1);
        glut.glutSolidCube(1);
        gl.glColor3d(0, 0, 0);
        glut.glutWireCube(1);

        gl.glPopMatrix();
    }

    /** Make sure arm does not go through the ground. */
    public boolean groundCheck() {
        boolean ok = true;
        double loc = 10 * Math.cos(degToRads(rotBottom));
        if (loc < .5) {
            ok = false;
        }
        loc += 6 * Math.cos(degToRads(rotMid + rotBottom));
        if (loc < 1) {
            ok = false;
        }
        loc += 4 * Math.cos(degToRads(rotMid + rotBottom + rotTop));
        if (loc < 1) {
            ok = false;
        }
        return ok;
    }

    public double degToRads(double ang) {
        return ang * Math.PI / 180.;
    }

    public void displayChanged(GLAutoDrawable d, boolean modeChanged,
            boolean deviceChanged) {
    }

    public void keyPressed(KeyEvent evt) {
        int key = evt.getKeyCode();
        System.out.println("Pressing key");
        switch (key) {
            case KeyEvent.VK_UP:
                rotBase += Math.abs(dAngle);
                break;
            case KeyEvent.VK_DOWN:
                rotBase += Math.abs(dAngle);
                break;
        }
        canvas.display();
    }

    public void changeBottom() {
        rotBottom += dAngle;
        if (!groundCheck()) {
            rotBottom -= dAngle;
        }
    }

    public void changeMid() {
        rotMid += dAngle;
        System.out.println("rotMid" + rotMid);
        if (!groundCheck() || Math.abs(rotMid) > 179) {
            rotMid -= dAngle;
        }
    }

    public void changeTop() {
        rotTop += dAngle;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new RobotListener(canvas));
        frame.getContentPane().add(canvas);
        frame.setSize(640, 480);

        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}