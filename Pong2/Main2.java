//||/////////////////////////////////////////////////////////////////////////||
//||Program Name - Pong													     ||
//||Author - Kizr															 ||
//||Description - This is a program designed to implement techniques learned ||
//||throughout the week as well as advanced techniques. The purpose of this  ||
//||program is to serve as a final project for students.					 ||
//||/////////////////////////////////////////////////////////////////////////||

import java.awt.BorderLayout;
import javax.swing.JFrame;
//The above list are all the things that you will need to import to
//get this program running. Please keep the spelling the exact same.

public class Main
{

    public static void main(String[] args) 
    {

        JFrame frame = new JFrame("Pong By Kizr");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        PongPanel pongPanel = new PongPanel();
        frame.add(pongPanel, BorderLayout.CENTER);

        frame.setSize(500, 500);
        frame.setVisible(true);

    }
}