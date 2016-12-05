import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener 
{
	Random randomGenerator = new Random();
    private boolean mPressed = false;
    private boolean nPressed = false;
    private Dimension d;//d will stand for dimension in other lines of code
    private final Font smallfont = new Font("Helvetica", Font.BOLD, 14);

    private Image ii;
    private final Color dotcolor = new Color(randomGenerator.nextInt(255), 
            randomGenerator.nextInt(255), 
    		randomGenerator.nextInt(255)
    		);//there is 1920amount of red, 192 amount of green , and no amount of blue  
    private Color mazecolor;//the color of the game board (aka maze)

    private boolean ingame = false;
    private boolean dying = false;

    private final int blocksize = 24;//size of pacman
    private final int nrofblocks = 15;
    private final int scrsize = nrofblocks * blocksize;
    private final int pacanimdelay = 2;
    private final int pacmananimcount = 4;//amount of pacman
    private final int maxghosts = 12;//
    private final int pacmanspeed = 6;//how fast the pacman is going

    private int pacanimcount = pacanimdelay;
    private int pacanimdir = 1;
    private int pacmananimpos = 0;
    private int nrofghosts = 6;//the amount of ghosts
    private int pacsleft, score;//the number of pacmans left at the end of the game (0)and your score 
    private int[] dx, dy;
    private int[] ghostx, ghosty, ghostdx, ghostdy, ghostspeed;

    private Image ghost;//the image of the ghost
    private Image pacman1, pacman2up, pacman2left, pacman2right, pacman2down;//the moves of pacman 2
    private Image pacman3up, pacman3down, pacman3left, pacman3right;//the moves of pacman 3
    private Image pacman4up, pacman4down, pacman4left, pacman4right;//the moves of pacman 4

    private int pacmanx, pacmany, pacmandx, pacmandy;
    private int reqdx, reqdy, viewdx, viewdy;

    private final short leveldata[] = {
            19, 26, 26, 26, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22,
            21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            21, 0, 0, 0, 17, 16, 16, 24, 16, 16, 16, 16, 16, 16, 20,
            17, 18, 18, 18, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 20,
            17, 16, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 16, 24, 20,
            25, 16, 16, 16, 24, 24, 28, 0, 25, 24, 24, 16, 20, 0, 21,
            1, 17, 16, 20, 0, 0, 0, 0, 0, 0, 0, 17, 20, 0, 21,
            1, 17, 16, 16, 18, 18, 22, 0, 19, 18, 18, 16, 20, 0, 21,
            1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21,
            1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21,
            1, 17, 16, 16, 16, 16, 16, 18, 16, 16, 16, 16, 20, 0, 21,
            1, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0, 21,
            1, 25, 24, 24, 24, 24, 24, 24, 24, 24, 16, 16, 16, 18, 20,
            9, 8, 8, 8, 8, 8, 8, 8, 8, 8, 25, 24, 24, 24, 28
    };

    private final int validspeeds[] = {1, 2, 3, 4, 6, 8};
    private final int maxspeed = 6;

    private int currentspeed = 3;
    private short[] screendata;
    private Timer timer;

    public Board() {

        loadImages();
        initVariables();

        addKeyListener(new TAdapter());

        setFocusable(true);

        setBackground(Color.black);//background color of game
        setDoubleBuffered(true);
    }

    private void initVariables() {

        screendata = new short[nrofblocks * nrofblocks];
        mazecolor = new Color(5, 100, 5); //sets color of maze, 5 of red, 100 of green, 5 of blue
        d = new Dimension(400, 400); //sets dimensions of the maze, 400 width, 400 height
        ghostx = new int[maxghosts]; 
        ghostdx = new int[maxghosts]; 
        ghosty = new int[maxghosts];
        ghostdy = new int[maxghosts];
        ghostspeed = new int[maxghosts];
        dx = new int[4];
        dy = new int[4];

        timer = new Timer(40, this);//40 of when this starts
        timer.start();
    }

    
    
    
    //Method labeled step. Notice above
    	
    	@Override
    public void addNotify() {
        super.addNotify();

        initGame();
    }

    private void doAnim() {

        pacanimcount--;

        if (pacanimcount <= 0) {//if pacanimcount is less than or equal to 0 
            pacanimcount = pacanimdelay;//then it will be equal to pacanimdelay
            pacmananimpos = pacmananimpos + pacanimdir;//pacmananimpos will be equal to pacmananimpos plus pacanimdir

            if (pacmananimpos == (pacmananimcount - 1) || pacmananimpos == 0) {//if pacmananimpos is equal to itself minus 1 or if pacmananimpos is equal to 0 
                pacanimdir = -pacanimdir;//then pacmananimpos is equal to its negative self
            }
        }
    }

    private void playGame(Graphics2D g2d) {

        if (dying) {

            death();    //lose a life

        } else {

            movePacman();    
            drawPacman(g2d);
            moveGhosts(g2d);
            checkMaze();
        }
    }

    private void showIntroScreen(Graphics2D g2d) {

        g2d.setColor(new Color(0, 32, 48));//colors of intro screen, 0 of red, 32 of green, 48 of blue
        g2d.fillRect(50, scrsize / 2 - 30, scrsize - 100, 50);//size of screen, 50 for x, the screen size divided by 2-3(y), screen size-100 (height), 50 for width; this is used when computer is filling it with the colors
        g2d.setColor(Color.white);
        g2d.drawRect(50, scrsize / 2 - 30, scrsize - 100, 50);//size of screen, 50 for x, the screen size divided by 2-3(y), screen size-100 (height), 50 for width; this is used when computer is drawing it

        String s = "Press s to start.";//telling the player to press the letter s to start the game
        Font small = new Font("Helvetica", Font.BOLD, 14);//font of the words/numbers on the game
        FontMetrics metr = this.getFontMetrics(small);

        g2d.setColor(Color.white);
        g2d.setFont(small);
        g2d.drawString(s, (scrsize - metr.stringWidth(s)) / 2, scrsize / 2);
    }

    private void drawScore(Graphics2D g) {

        int i;
        String s;

        g.setFont(smallfont);//size of the words/numbers
        g.setColor(new Color(96, 128, 255));//96 of red, 128 of green, 255 of blue  
        s = "Score: " + score;//when game is finished it will say score: (whatever the score is)
        g.drawString(s, scrsize / 2 + 96, scrsize + 16);//size of the words

        for (i = 0; i < pacsleft; i++) {
            g.drawImage(pacman3left, i * 28 + 8, scrsize + 1, this);
        }
    }

    private void checkMaze() {

        short i = 0;//when there are no more balls,
        boolean finished = true;//the game is finished

        while (i < nrofblocks * nrofblocks && finished) {

            if ((screendata[i] & 48) != 0) {
                finished = false;
            }

            i++;
        }

        if (finished) {

            score += 50;

            if (nrofghosts < maxghosts) {//if the amount of ghosts are less than the max amount of ghosts possible,
                nrofghosts++;//another ghost is added
            }

            if (currentspeed < maxspeed) {//if the current speed is less than the max amount the speed can go,
                currentspeed++;//the current speed will speed up by one
            }

            initLevel();
        }
    }

    private void death() {

        pacsleft--;//the player dies when there are no more pacmen left

        if (pacsleft == 0) {//when the amount of pacmen left are equal to 0, 
            ingame = false;//the player is no longer in the game (game has ended)
        }

        continueLevel();
    }

    private void moveGhosts(Graphics2D g2d) {

        short i;
        int pos;
        int count;

        for (i = 0; i < nrofghosts; i++) {
            if (ghostx[i] % blocksize == 0 && ghosty[i] % blocksize == 0) {
                pos = ghostx[i] / blocksize + nrofblocks * (int) (ghosty[i] / blocksize);

                count = 0;

                if ((screendata[pos] & 1) == 0 && ghostdx[i] != 1) {
                    dx[count] = -1;
                    dy[count] = 0;
                    count++;
                }

                if ((screendata[pos] & 2) == 0 && ghostdy[i] != 1) {
                    dx[count] = 0;
                    dy[count] = -1;
                    count++;
                }

                if ((screendata[pos] & 4) == 0 && ghostdx[i] != -1) {
                    dx[count] = 1;
                    dy[count] = 0;
                    count++;
                }

                if ((screendata[pos] & 8) == 0 && ghostdy[i] != -1) {
                    dx[count] = 0;
                    dy[count] = 1;
                    count++;
                }

                if (count == 0) {

                    if ((screendata[pos] & 15) == 15) {
                        ghostdx[i] = 0;
                        ghostdy[i] = 0;
                    } else {
                        ghostdx[i] = -ghostdx[i];
                        ghostdy[i] = -ghostdy[i];
                    }

                } else {

                    count = (int) (Math.random() * count);

                    if (count > 3) {
                        count = 3;
                    }

                    ghostdx[i] = dx[count];
                    ghostdy[i] = dy[count];
                }

            }

            ghostx[i] = ghostx[i] + (ghostdx[i] * ghostspeed[i]);
            ghosty[i] = ghosty[i] + (ghostdy[i] * ghostspeed[i]);
            drawGhost(g2d, ghostx[i] + 1, ghosty[i] + 1);

            if (pacmanx > (ghostx[i] - 12) && pacmanx < (ghostx[i] + 12)
                    && pacmany > (ghosty[i] - 12) && pacmany < (ghosty[i] + 12)
                    && ingame) {

                dying = true;
            }
        }
    }

    private void drawGhost(Graphics2D g2d, int x, int y) {

        g2d.drawImage(ghost, x, y, this);
    }

    private void movePacman() {

        int pos;
        short ch;

        if (reqdx == -pacmandx && reqdy == -pacmandy) {
            pacmandx = reqdx;
            pacmandy = reqdy;
            viewdx = pacmandx;
            viewdy = pacmandy;
        }

        if (pacmanx % blocksize == 0 && pacmany % blocksize == 0) {
            pos = pacmanx / blocksize + nrofblocks * (int) (pacmany / blocksize);
            ch = screendata[pos];

            if ((ch & 16) != 0) {
                screendata[pos] = (short) (ch & 15);
                score++;
            }

            if (reqdx != 0 || reqdy != 0) {
                if (!((reqdx == -1 && reqdy == 0 && (ch & 1) != 0)
                        || (reqdx == 1 && reqdy == 0 && (ch & 4) != 0)
                        || (reqdx == 0 && reqdy == -1 && (ch & 2) != 0)
                        || (reqdx == 0 && reqdy == 1 && (ch & 8) != 0))) {
                    pacmandx = reqdx;
                    pacmandy = reqdy;
                    viewdx = pacmandx;
                    viewdy = pacmandy;
                }
            }

            // Check for standstill
            if ((pacmandx == -1 && pacmandy == 0 && (ch & 1) != 0)
                    || (pacmandx == 1 && pacmandy == 0 && (ch & 4) != 0)
                    || (pacmandx == 0 && pacmandy == -1 && (ch & 2) != 0)
                    || (pacmandx == 0 && pacmandy == 1 && (ch & 8) != 0)) {
                pacmandx = 0;
                pacmandy = 0;
            }
        }
        pacmanx = pacmanx + pacmanspeed * pacmandx;
        pacmany = pacmany + pacmanspeed * pacmandy;
    }

    private void drawPacman(Graphics2D g2d) {

        if (viewdx == -1) {
            drawPacnanLeft(g2d);
        } else if (viewdx == 1) {
            drawPacmanRight(g2d);
        } else if (viewdy == -1) {
            drawPacmanUp(g2d);
        } else {
            drawPacmanDown(g2d);
        }
    }

    private void drawPacmanUp(Graphics2D g2d) {

        switch (pacmananimpos) {
            case 1:
                g2d.drawImage(pacman2up, pacmanx + 1, pacmany + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3up, pacmanx + 1, pacmany + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4up, pacmanx + 1, pacmany + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pacmanx + 1, pacmany + 1, this);
                break;
        }
    }

    private void drawPacmanDown(Graphics2D g2d) {

        switch (pacmananimpos) {
            case 1:
                g2d.drawImage(pacman2down, pacmanx + 1, pacmany + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3down, pacmanx + 1, pacmany + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4down, pacmanx + 1, pacmany + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pacmanx + 1, pacmany + 1, this);
                break;
        }
    }

    private void drawPacnanLeft(Graphics2D g2d) {

        switch (pacmananimpos) {
            case 1:
                g2d.drawImage(pacman2left, pacmanx + 1, pacmany + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3left, pacmanx + 1, pacmany + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4left, pacmanx + 1, pacmany + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pacmanx + 1, pacmany + 1, this);
                break;
        }
    }

    private void drawPacmanRight(Graphics2D g2d) {

        switch (pacmananimpos) {
            case 1:
                g2d.drawImage(pacman2right, pacmanx + 1, pacmany + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3right, pacmanx + 1, pacmany + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4right, pacmanx + 1, pacmany + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pacmanx + 1, pacmany + 1, this);
                break;
        }
    }

    private void drawMaze(Graphics2D g2d) {

        short i = 0;
        int x, y;

        for (y = 0; y < scrsize; y += blocksize) {
            for (x = 0; x < scrsize; x += blocksize) {

                g2d.setColor(mazecolor);
                g2d.setStroke(new BasicStroke(2));

                if ((screendata[i] & 1) != 0) { 
                    g2d.drawLine(x, y, x, y + blocksize - 1);
                }

                if ((screendata[i] & 2) != 0) { 
                    g2d.drawLine(x, y, x + blocksize - 1, y);
                }

                if ((screendata[i] & 4) != 0) { 
                    g2d.drawLine(x + blocksize - 1, y, x + blocksize - 1,
                            y + blocksize - 1);
                }

                if ((screendata[i] & 8) != 0) { 
                    g2d.drawLine(x, y + blocksize - 1, x + blocksize - 1,
                            y + blocksize - 1);
                }

                if ((screendata[i] & 16) != 0) { 
                    g2d.setColor(new Color(randomGenerator.nextInt(255), 
		                    randomGenerator.nextInt(255), 
                    		randomGenerator.nextInt(255)
                    		));
                    g2d.fillRect(x + 11, y + 11, 2, 2);
                }
                
                i++;
            }
        }
    }

    private void initGame() {//initGame means the beginning of the game

        pacsleft = 3;//3 pacs left
        score = 0;//score is 0
        initLevel();
        nrofghosts = 6;//there are 6 ghosts in the maze
        currentspeed = 3;//the speed is 3
    }

    private void initLevel() {

        int i;
        for (i = 0; i < nrofblocks * nrofblocks; i++) {
            screendata[i] = leveldata[i];
        }

        continueLevel();
    }

    private void continueLevel() {

        short i;
        int dx = 1;
        int random;

        for (i = 0; i < nrofghosts; i++) {

            ghosty[i] = 4 * blocksize;
            ghostx[i] = 4 * blocksize;
            ghostdy[i] = 0;
            ghostdx[i] = dx;
            dx = -dx;
            random = (int) (Math.random() * (currentspeed + 1));

            if (random > currentspeed) {
                random = currentspeed;
            }

            ghostspeed[i] = validspeeds[random];
        }

        pacmanx = 7 * blocksize;
        pacmany = 11 * blocksize;
        pacmandx = 0;
        pacmandy = 0;
        reqdx = 0;
        reqdy = 0;
        viewdx = -1;
        viewdy = 0;
        dying = false;
    }

    private void loadImages() {

        ghost = new ImageIcon("images/ghost.png").getImage();//the images of the ghost
        pacman1 = new ImageIcon("images/pacman.png").getImage();//image of the first pacman
        pacman2up = new ImageIcon("images/pacman.png").getImage();//image of second pacman when it's going up
        pacman3up = new ImageIcon("images/pacman.png").getImage();//image of third pacman when it's going up
        pacman4up = new ImageIcon("images/pacman.png").getImage();//image of fourth pacman when it's going up
        pacman2down = new ImageIcon("images/pacman.png").getImage();//image of second pacman when it's going down
        pacman3down = new ImageIcon("images/pacman.png").getImage();//image of third pacman when it's going down
        pacman4down = new ImageIcon("images/pacman.png").getImage();//image of fourth pacman when it's going down
        pacman2left = new ImageIcon("images/pacman.png").getImage();//image of second pacman when it's going to the left
        pacman3left = new ImageIcon("images/pacman.png").getImage();//image of third pacman when it's going to the left
        pacman4left = new ImageIcon("images/pacman.png").getImage();//image of fourth pacman when it's going to the left
        pacman2right = new ImageIcon("images/pacman.png").getImage();//image of second pacman when it's going to the right
        pacman3right = new ImageIcon("images/pacman.png").getImage();//image of third pacman when it's going to the right
        pacman4right = new ImageIcon("images/pacman.png").getImage();//image of fourth pacman when it's going to the right
        
//        pacman2up = new ImageIcon("images/up1.png").getImage();
//        pacman3up = new ImageIcon("images/up2.png").getImage();
//        pacman4up = new ImageIcon("images/up3.png").getImage();
//        pacman2down = new ImageIcon("images/down1.png").getImage();
//        pacman3down = new ImageIcon("images/down2.png").getImage();
//        pacman4down = new ImageIcon("images/down3.png").getImage();
//        pacman2left = new ImageIcon("images/left1.png").getImage();
//        pacman3left = new ImageIcon("images/left2.png").getImage();
//        pacman4left = new ImageIcon("images/left3.png").getImage();
//        pacman2right = new ImageIcon("images/right1.png").getImage();
//        pacman3right = new ImageIcon("images/right2.png").getImage();
//        pacman4right = new ImageIcon("images/right3.png").getImage();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.setColor((new Color(randomGenerator.nextInt(255), 
                randomGenerator.nextInt(255), 
        		randomGenerator.nextInt(255)
        		)));
       
        g2d.fillRect(0, 0, d.width, d.height);

        drawMaze(g2d);
        drawScore(g2d);
        doAnim();

        if (ingame) {
            playGame(g2d);
        } else {
            showIntroScreen(g2d);
        }

        g2d.drawImage(ii, 5, 5, this);
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }

    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (ingame) {
                if (key == KeyEvent.VK_LEFT) {
                    reqdx = -1;
                    reqdy = 0;
                } else if (key == KeyEvent.VK_RIGHT) {
                    reqdx = 1;
                    reqdy = 0;
                } else if (key == KeyEvent.VK_UP) {
                    reqdx = 0;
                    reqdy = -1;
                } else if (key == KeyEvent.VK_DOWN) {
                    reqdx = 0;
                    reqdy = 1;
                } 
                else if (key==KeyEvent.VK_M){
                	pacsleft++;
                }
                else if (key==KeyEvent.VK_N){
                	setBackground(new Color(randomGenerator.nextInt(255), 
		                    randomGenerator.nextInt(255), 
                    		randomGenerator.nextInt(255)
                    		));
                }
                else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    ingame = false;
                } else if (key == KeyEvent.VK_PAUSE) {
                    if (timer.isRunning()) {
                        timer.stop();
                    } else {
                        timer.start();
                    }
                }
            } else {
                if (key == 's' || key == 'S') {
                    ingame = true;
                    initGame();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) 
        {

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT) {
                reqdx = 0;
                reqdy = 0;
            } else if (key == KeyEvent.VK_RIGHT) {
                reqdx = 0;
                reqdy = 0;
            } else if (key == KeyEvent.VK_UP) {
                reqdx = 0;
                reqdy = 0;
            } else if (key == KeyEvent.VK_DOWN) {
                reqdx = 0;
                reqdy = 0;
            }
            else if (key==KeyEvent.VK_M){
            	pacsleft++;
            }
            else if (key==KeyEvent.VK_N){
            	setBackground(new Color(randomGenerator.nextInt(255), 
	                    randomGenerator.nextInt(255), 
                		randomGenerator.nextInt(255)
                		));
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
    }
}
