// This class represents rooms in which robots can move about.
//
// History:
//
//   July 2003 -- Created by Doug Baldwin.
//
//   March 2004 -- Extended by Doug Baldwin to accept "K" (black) as a
//     color specification for tiles in room descriptions.




package geneseo.cs.sc;

import java.util.StringTokenizer;
import javax.swing.JFrame;
import java.awt.*;




/**
 * Represents a room in which Science of Computing robots can move. A room consists
 * of a tiled floor and walls. The floor tiles can be colored. Within the room,
 * tiles provide a coordinate system for describing the positions of robots, walls,
 * etc. The coordinate system's origin is the upper left corner of the room, with
 * horizontal coordinates increasing to the right and vertical coordinates increasing
 * down. Coordinates start at 0, i.e., the left-most side of the room is horizontal
 * coordinate 0, and the top side is row 0. Every room has walls around its edges
 * (so that robots can't fall out of the room), and a room may have other walls
 * (or fragments of wall) in its interior.
 * @see geneseo.cs.sc.Robot
 */

public class RobotRoom extends Canvas {




    // Robot rooms use an internal class to represent tiles. This class
    // is basically just a simple record that stores a tile's color, whether
    // it has a wall on it, and whether it has a robot on it (and which robot,
    // if so). It also provides some simple support for things like initializing
    // and drawing tiles.

    private static class Tile {

        public static final int WIDTH = 30;		// The width of a tile when drawn, in pixels
        public static final int HEIGHT = 30;		// A tile's height, in pixels
        public static final Color wallColor = new Color( (float)0.4, (float)0.32, (float)0.08 );
        
        public Color color;				// The tile's color
        public Robot occupant;				// The robot on this tile, or null
        public boolean isWall;				// True if there is a wall on this tile

        public Tile( Color color, Robot occupant, boolean isWall ) {
            if ( isWall ) {
                this.color = wallColor;
            }
            else {
                this.color = color;
            }
            this.occupant = occupant;
            this.isWall = isWall;
        }

        public void draw( Graphics context, int left, int top ) {
            context.setColor( color );
            context.fillRect( left, top, WIDTH, HEIGHT );
            if ( occupant != null ) {
                occupant.draw( context, left+WIDTH/2, top+HEIGHT/2 );
            }
        }

    }
    



    // Internally, robot rooms record their width and height, and an array
    // of their tiles. They also have a frame in which they appear on the
	// screen:

    private int width;
    private int height;
    private Tile[][] tiles;
	private JFrame window;




    // Internal parameters for drawing robot rooms:
    
    private final int inset = 20;			// Number of pixels room is inset from frame's sides



    
    /**
     * Initialize a robot's room with default size and contents. The default
     * is a square room 10 tiles on a side, with all tiles white and no
     * interior walls. For example
	 *  <p><code>RobotRoom room = new RobotRoom();</code></p>
     */

    public RobotRoom() {
        this( "11 11" );
    }




    /**
     * Initialize a robot's room from its description. Regardless of what
     *  the description calls for, the room will be at least three tiles by
     *  three tiles, to leave space for a wall all around the room and at least
     *  one tile for a robot inside. The room will be no more than 25 tiles
     *  by 25. For example
	 *  <p><code>RobotRoom room = new RobotRoom( "5 5 2 1 Y" );</code></p>
     * @param description The description of the room. This is a string that
     *  must start with two integers, the room's width and height, in tiles.
     *  The string may then contain any number of tile specifications, which
     *  consist of column and row coordinates (integers) followed by a color
     *  or wall designator (R, G, B, Y, or K for red, green, blue, yellow, and
     *  black, or * for a wall). All elements of the specification should be
     *  separated by white space. For example
     *    <code>9 5 4 2 * 3 2 r</code>
     *  specifies a 9-tile wide by 5-tile high room whose middle tile is
     *  a wall, the tile to its left is red. The outer-most rows and columns of
     *  the room always contain walls, and any tiles not defined by the description
     *  are white.
     */

    public RobotRoom( String description ) {
    
    	super();
    	

        // Initialize the frame in which this world will appear:

        window = new JFrame( "Robot Room" );
		
		window.getContentPane().add( this );

        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        
        // Get the requested width and height:

        final int minWidth = 3;
        final int maxWidth = 25;
        final int minHeight = 3;
        final int maxHeight = 25;
        
        width = minWidth;		// Width and height start with their
        height = minHeight;		// default values

        StringTokenizer tokens = new StringTokenizer( description );

        try {

            width = Integer.parseInt( tokens.nextToken() );
            if (  width < minWidth  ||  width > maxWidth  ) {
                width = minWidth;
            }

            height = Integer.parseInt( tokens.nextToken() );
            if (  height < minHeight  ||  height > maxHeight  ) {
                height = minHeight;
            }
        }
        catch ( Exception error ) {
            // Ignore exceptions in processing width and height, width and/or
            // height variables will come out with their default values when
            // there are errors.
        }


        // Create the tile array, filling it with empty white tiles everywhere except
        // the outer rows and columns, which have walls on them:

        tiles = new Tile[height][width];

        for ( int row = 0; row < height; row++ ) {
            for ( int col = 0; col < width; col++ ) {
                boolean isWall = ( row == 0 || row == height-1 || col == 0 || col == width-1 );
                tiles[row][col] = new Tile( Color.white, null, isWall );
            }
        }


        // Go through the rest of the room's description, changing any tiles that it mentions
        // to the color and wall status it requests. But don't allow changes in the border wall,
        // or outside the room:

        while ( tokens.hasMoreTokens() ) {

            try {
                
                int col = Integer.parseInt( tokens.nextToken() );
                int row = Integer.parseInt( tokens.nextToken() );
                char colorCode = tokens.nextToken().charAt(0);

                if (  row > 0  &&  row < height-1  &&  col > 0  &&  col < width-1  ) {
                    switch ( colorCode ) {
                        case 'r':
                        case 'R':
                            tiles[row][col].color = Color.red;
                            tiles[row][col].isWall = false;
                            break;
                        case 'g':
                        case 'G':
                            tiles[row][col].color = Color.green;
                            tiles[row][col].isWall = false;
                            break;
                        case 'b':
                        case 'B':
                            tiles[row][col].color = Color.blue;
                            tiles[row][col].isWall = false;
                            break;
                        case 'y':
                        case 'Y':
                            tiles[row][col].color = Color.yellow;
                            tiles[row][col].isWall = false;
                            break;
                        case 'k':
                        case 'K':
                            tiles[row][col].color = Color.black;
                            tiles[row][col].isWall = false;
                            break;
                        case '*':
                            tiles[row][col].isWall = true;
                            tiles[row][col].color = Tile.wallColor;
                            break;
                    }
                }
            }
            catch ( Exception error ) {
                // Do nothing on an error, try to continue with the next tile, if any.
            }
        }


        // Size the window to display the room, and make it visible to the user. Note that
        // the window width and height reflect the space needed for the window's border, an
        // inset between each side of the room and the window border, and space for the room's
        // tiles, each of which has a 1-pixel border between it and the previous tile, plus
        // a one-pixel border after the last tile:

        window.pack();
        
        Insets borders = window.getInsets();
        int windowWidth = borders.left + borders.right + 2 * inset + width * ( Tile.WIDTH + 1 ) + 1;
        int windowHeight = borders.top + borders.bottom + 2 * inset + height * (Tile.HEIGHT + 1 ) + 1;
        window.setSize( windowWidth, windowHeight );

		this.setVisible( true );
        window.setVisible( true );
    }




    /**
     * Put a robot in a room. This will put the robot at the requested
     *  position if possible. If not possible, because the requested
     *  position is outside the room, because there is a wall at the
     *  requested position, or because another robot is already at the
     *  requested position, then the new robot is not placed in the room
     *  at all. For example
	 *  <p><code>room.addRobot( someRobot, 3, 5 );</code></p>
     * @param robot The robot that is being put in this room.
     * @param col The horizontal coordinate (tile column) at which to
     *  place this robot.
     * @param row The vertical coordinate (tile row) at which to place
     *  this robot.
     */

    public void addRobot( Robot robot, int col, int row ) {
        
        if (  col >= 0  &&  col < width  &&  row >= 0  &&  row < height ) {
            if (  ! tiles[row][col].isWall  &&  tiles[row][col].occupant == null  ) {
                tiles[row][col].occupant = robot;
            }
        }

        this.repaint();
    }




    /**
     * Get a room's width. For example
	 *  <p><code>int w = room.getRoomWidth();</code></p>
     * @return The width of the room, in tiles.
     */

    public int getRoomWidth() {
        return width;
    }




    /**
     * Get a room's height. For example
	 *  <p><code>int h = room.getRoomHeight();</code></p>
     * @return The room's height, in tiles.
     */

    public int getRoomHeight() {
        return height;
    }




    /**
     * Move a robot from one tile to another.
     * @param oldCol The horizontal coordinate (tile column) of the tile the
     *  robot is moving from.
     * @param oldRow The vertical coordinate (tile row) of the tile the robot
     *  is moving from.
     * @param newCol The horizontal coordinate of the tile the robot is moving to.
     * @param newRow The vertical coordinate of the tile the robot is moving to.
     */

    protected void moveRobot( int oldCol, int oldRow, int newCol, int newRow ) {
        
        tiles[newRow][newCol].occupant = tiles[oldRow][oldCol].occupant;
        tiles[oldRow][oldCol].occupant = null;
        
        this.repaint();
    }




    /**
     * Find out whether a tile within a room is obstructed. A tile is obstructed
     *  if it contains a wall or robot.
     * @param col The horizontal coordinate (tile column) of the tile.
     * @param row The vertical coordinate (tile row) of the tile.
     * @return True if the specified tile is obstructed, false if it is not.
     */

    protected boolean isObstructed( int col, int row ) {
        return (  tiles[row][col].isWall  ||  tiles[row][col].occupant != null  );
    }
    



    /**
     * Change the color of a tile in a room.
     * @param col The horizontal coordinate (i.e., tile column) of the tile
     *  to change.
     * @param row The vertical coordinate (i.e., tile row) of the tile
     *  to change.
     * @param newColor The color to make the tile.
     */

    protected void setColor( int col, int row, Color newColor ) {

        if (  col >= 0  &&  col < width  &&  row >= 0  &&  row < height  ) {
            tiles[row][col].color = newColor;
            this.repaint();            
        }
    }




    /**
     * Find out what color a tile within a room has.
     * @param col The horizontal coordinate (i.e., tile column) of the tile.
     * @param row The vertical coordinate (i.e.,tile row) of the tile.
     * @return The color of the tile at the specified column and row. If the row
     *  or column are out of bounds for this room, then the color is assumed to
     *  be white.
     */

    protected Color getColor( int col, int row ) {

        if (  col >= 0  &&  col < width  &&  row >= 0  &&  row < height  ) {
            return tiles[row][col].color;            
        }
        else {
            return Color.white;
        }
    }




    /**
     * Redisplay a robot room. This method should never be called by client
     *  code, it is called automatically by the Java runtime system when the
     *  runtime system believes that a robot room needs to be redrawn.
     * @param context The graphics context in which to draw the room.
     */

    public void paint( Graphics context ) {


        // Draw the borders between tiles as a series of horizontal and vertical
        // lines. Note that line and tile positions take into account both the
        // size of a tile and the one-pixel lines in between them:

        int leftEdge = inset;										// The coordinate of the left edge of the room
        int rightEdge = leftEdge + width * ( Tile.WIDTH + 1 );		// The coordinate of right edge of room
        int topEdge = inset;										// The coordinate of the top edge of the room
        int bottomEdge = topEdge + height * ( Tile.HEIGHT + 1 );	// Coordinate of bottom edge of the room

        context.setColor( Color.black );

        for ( int row = 0; row < height; row++ ) {
            int vPos = topEdge + row * ( Tile.HEIGHT + 1 );
            context.drawLine( leftEdge, vPos, rightEdge, vPos );
        }

        context.drawLine( leftEdge, bottomEdge, rightEdge, bottomEdge );

        for ( int col = 0; col < width; col++ ) {
            int hPos = leftEdge + col * ( Tile.WIDTH + 1);
            context.drawLine( hPos, topEdge, hPos, bottomEdge );
        }

        context.drawLine( rightEdge, topEdge, rightEdge, bottomEdge );


        // Draw the tiles themselves:

        for ( int row = 0; row < height; row++ ) {
            for ( int col = 0; col < width; col++ ) {
                tiles[row][col].draw( context,
                                      leftEdge + 1 + col*(Tile.WIDTH+1),
                                      topEdge + 1 + row*(Tile.HEIGHT+1) );
            }
        }
    }
    
}
