// The Science of Computing robot class.

// History:
//   June 2003 -- Created by Doug Baldwin.




package geneseo.cs.sc;




import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;




/**
 * Represents simple simulated robots. These robots occupy rooms with walls and
 * tiled floors. Robots walk around on the floors (one tile at a time), changing
 * and sensing the colors of tiles if they wish. But robots can't move through
 * walls, thus they have to stay in their room. Robots can face in any of four
 * directions, which are described as compass directions in analogy to a map:
 * north is towards the top of the robot's room as drawn on a computer screen,
 * east is towards the right, etc.
 * @see geneseo.cs.sc.RobotRoom
 */

public class Robot {




    /**
     * The heading a robot has when facing north.
     */

    public static final int NORTH = 0;


    /**
     * The heading a robot has when facing east.
     */
    
    public static final int EAST = 1;


    /**
     * The heading a robot has when facing south.
     */
    
    public static final int SOUTH = 2;


    /**
     * The heading a robot has when facing west.
     */
    
    public static final int WEST = 3;




    // Internally, a robot records the room it is in, its row and column
    // coordinates (in tiles) within that room, its heading, whether
    // or not it is visible, and the amount of time it should delay
    // after movements:

    private RobotRoom room;
    private int row;
    private int col;
    private int direction;
    private boolean visible;
    private int delayTime;

    private static final int initialDelay = 500;	// Number of milliseconds delay for new robots

    


    // Robots also have some data that they use to draw themselves: x and y offsets
    // of vertices of the robot relative to its center, and a color to draw it in
    // (see the comments for the "draw" method below for more details):
    
    private static final int[] xOffsets = { 0, -10, -5, -5, 5, 5, 10 };
    private static final int[] yOffsets = { -10, 0, 0, 10, 10, 0, 0 };

    private static final Color robotColor = new Color( (float)0.25, (float)0.0, (float)0.3 );
    



    /**
     * Initialize a robot and a room for it to occupy. The room will be a default room,
     * and this robot will be its only occupant. The robot will have the default position
     * and heading (center of the room, facing north). For example
	 * <p><code>Robot r = new Robot();</code></p>
     */

    public Robot() {

        room = new RobotRoom();				// A default room for this robot.

        col = room.getRoomWidth() / 2;
        row = room.getRoomHeight() / 2;

        direction = NORTH;

        visible = true;
        delayTime = initialDelay;

        room.addRobot( this, col, row );
    }




    /**
     * Initialize a robot from its position, orientation, and room. For example
	 * <p><code>Robot r = new Robot( 1, 3, Robot.NORTH, someRoom );</code></p>
     * @param col The horizontal coordinate of the tile this robot is on (i.e., the
     *  tile column the robot is in). This must be within the range of columns
     *  for the room the robot is in, and, with the row parameter, must specify
     *  an unobstructed tile.
     * @param row The vertical coordinate of the tile this robot is on (i.e., the
     *  tile row the robot is in). This must be within the range of rows for
     *  the room the robot is in, and, with the column parameter, must specify
     *  an unobstructed tile.
     * @param heading The robot's heading. This should be one of the four headings
     *  defined for robots.
     * @param room The room the robot is in.
     */

    public Robot( int col, int row, int heading, RobotRoom room ) {

        this.room = room;
        this.col = col;
        this.row = row;
        this.direction = heading;
        this.visible = true;
        this.delayTime = initialDelay;

        room.addRobot( this, col, row );
    }




    /**
     * Move a robot one tile forward, unless the way is blocked by a wall or
     *  other robot. If the way is blocked, the robot will flash briefly to
     *  indicate that it can't move. For example
	 *  <p><code>r.move();</code></p>
     */
    
    public void move() {

        if ( this.okToMove() ) {
            
            Point next = this.nextTile();
            room.moveRobot( col, row, next.x, next.y );
            col = next.x;
            row = next.y;
            
            this.delay();
        }
        else {
            this.flash(); 
        }
    }




    /**
     * Find out whether a robot can move forward. A robot can move whenever
     *  the tile in front of it doesn't contain a wall or another robot.
	 *  For example
	 *  <p><code>if ( r.okToMove() ) {...</code></p>
     * @return True if the robot can move, false if moving would cause the
     *  robot to collide with a wall or another robot.
     */

    public boolean okToMove() {
        Point next = this.nextTile();
        return ! room.isObstructed( next.x, next.y );
    }




    /**
     * Turn a robot 90 degrees to its left (i.e., counterclockwise about
     *  its center). For example
	 *  <p><code>r.turnLeft();</code></p>
     */

    // I do a 90-degree left turn as 3 90-degree right turns, 'though done
    // all at once so the user doesn't see distinct turns.

    public void turnLeft() {
        this.turn( 3 );
    }




    /**
     * Turn a robot 90 degrees to its right (i.e., clockwise about its center).
	 *  For example
	 *  <p><code>r.turnRight();</code></p>
     */
    
    public void turnRight() {
        this.turn( 1 );
    }




    /**
     * Change the color of the tile under a robot. For example
	 *  <p><code>r.paint( java.awt.Color.yellow );</code></p>
     * @param newColor The color the tile changes to.
     */

    public void paint( Color newColor ) {
        room.setColor( col, row, newColor );
    }




    /**
     * Find out what color the tile under a robot is. For example
	 *  <p><code>if ( r.colorOfTile() == java.awt.Color.red ) {...</code></p>
     * @return The color of the tile under the robot.
     */

    public Color colorOfTile() {
        return room.getColor( col, row );
    }




    /**
     * Find out what direction a robot is facing. For example
	 *  <p><code>if ( r.heading() == Robot.NORTH ) {...</code></p>
     * @return The robot's direction, encoded as one of the values
     *  <code>Robot.NORTH</code>, <code>Robot.EAST</code>,
     *  <code>Robot.SOUTH</code>, or <code>Robot.WEST</code>.
     */

    public int heading() {
        return direction;
    }




    /**
     * Set the speed at which a robot moves and turns. For example
	 *  <p><code>r.setSpeed( 100 );</code></p>
     * @param speed An integer between 1 and 1000, which indicates approximately
     *  how many moves or turns per second the robot should do. Values below 1
     *  or above 1000 are treated as if they were 1 and 1000, respectively.
     */

    public void setSpeed( int speed ) {

        if ( speed < 1 ) {
            speed = 1;
        }

        if ( speed > 1000 ) {
            speed = 1000;
        }

        delayTime = 1000 / speed;
    }




    /**
     * Draw a robot.
     * @param context The graphics context in which to draw.
     * @param x The horizontal coordinate at which to place the robot's center.
     * @param y The vertical coordinate at which to place the robot's center
     */

    // The robot is a polygon, filled with a robot color not likely to be
    // generated by a student -- right now, a dark purple. I define the polygon
    // by giving lists of X and Y offsets for its vertices from the center of
    // the robot, when the robot is facing north. For other orientations, I
    // rotate these offsets using the standard equations for 2D rotation
    // (x' = x cos(a) + y sin(a); y' = -x sin(a) + y cos(a)), keeping in mind
    // that the rotations are all multiples of 90 degrees, so the sines and cosines
    // are all -1, 1, or 0. To actually draw the robot, I add the rotated offsets
    // to the center coordinates provided as parameters, and use the results as
    // the vertices of a filled polygon.

    public void draw( Graphics context, int x, int y ) {

        if ( visible ) {

            int sine;				// Will hold the sine of the rotation angle
            int cosine;				// Will hold the cosine of the rotation angle

            switch ( direction ) {
                case NORTH:				// no rotation
                    sine = 0;
                    cosine = 1;
                    break;
                case WEST:				// 90 degrees
                    sine = 1;
                    cosine = 0;
                    break;
                case SOUTH:				// 180 degrees
                    sine = 0;
                    cosine = -1;
                    break;
                case EAST:				// -90 degrees
                    sine = -1;
                    cosine = 0;
                    break;
                default:				// Invalid rotations act like no rotation
                    sine = 0;
                    cosine = 1;
                    break;
            }

            int[] xVertices = new int[ xOffsets.length ];
            int[] yVertices = new int[ yOffsets.length ];

            for ( int i = 0; i < xOffsets.length; i++ ) {
                xVertices[i] = x + cosine * xOffsets[i] + sine * yOffsets[i];
                yVertices[i] = y - sine * xOffsets[i] + cosine * yOffsets[i];
            }

            context.setColor( robotColor );
            context.fillPolygon( xVertices, yVertices, xVertices.length );            
        }
    }




    /**
     * Find out the tile coordinates of the tile a robot would move to if it moved
     *  forward (assuming that move is permitted).
     * @return A Point containing the tile column (x coordinate) and row (y
     *  coordinate) of the tile the robot would move to.
     */

    private Point nextTile() {
        
        switch ( direction ) {

            case NORTH:
                return new Point( col, row - 1 );

            case EAST:
                return new Point( col + 1, row );

            case SOUTH:
                return new Point( col, row + 1 );

            case WEST:
                return new Point( col - 1, row );

            default:					// Treat invalid headings as if they were north
                return new Point( col, row - 1 );
        }
    }




    /**
     * Pause. This is useful in order to slow down robot programs enough that users
     *  can see where their robot is moving, for visual debugging.
     */

    private void delay() {

        try {
            Thread.sleep( delayTime );
        }
        catch ( InterruptedException interrupt ) { }
    }




    /**
     * Make a robot flash on the screen. Typically used to signal some error, e.g.,
     *  running into a wall or other robot.
     */

    // This works by toggling a "visible" flag in the robot, while redrawing its
    // room. A pause between redraws ensures that the user's brain has time to
    // process the alternately visible and invisible robots as "flashing".

    private void flash() {

        final int flashes = 6;				// The number of times to toggle the robot
        final int flashDelay = 80;			// Milliseconds to wait between toggles

        for ( int i = 0; i < flashes; i++ ) {
            
            visible = ! visible;
            room.repaint();

            try {
                Thread.sleep( flashDelay );    
            }
            catch ( InterruptedException interrupt ) { }
        }
    }




    /**
     * Turn a robot an arbitrary number of 90-degree steps clockwise.
     * @param steps The number of 90-degree steps to turn.
     */

    // Since directions are represented by integers in the range 0 to 3,
    // increasing clockwise from north, I turn by simply adding the number
    // of steps to the current direction, and taking the result mod 4 to keep
    // it a valid direction.

    private void turn( int steps ) {
        
        direction = ( direction + steps ) % 4;

        room.repaint();

        this.delay();        
    }
    
}
 
