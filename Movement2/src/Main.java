import javax.swing.JFrame;

public class Main
{
	public static void main(String [] args)
	{
		Move m = new Move();
		JFrame f = new JFrame();
		f.add(m);
		f.setVisible(true);
		f.setSize(600, 400);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("Moving Ball by Kizr");
		
	}
	
	
}