import java.awt.EventQueue;
import javax.swing.JFrame;

public class Pacman extends JFrame {

    public Pacman() {
        
        initUI();
    }
    
    private void initUI() {
        
        add(new Board());       //creates new board object     
        setTitle("Pacman");     //Tiles window "Pacman"
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(380, 420);      //sets size of window
        setLocationRelativeTo(null);    
        setVisible(true);        
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                Pacman ex = new Pacman();
                ex.setVisible(true);
            }
        });
    }
}