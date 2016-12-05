import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

/**
 * Represents one of connected links for a robotic arm
 */
public class RoboticLink
{

	int xOriginal[];
	int yOriginal[];

	/**
	 * The starting coordiantes of the arm's coordinates
	 */
	private Point globalPosition;

	int length;

	/**
	 * Angle in degrees
	 */
	int localAngle, globalAngle;

	private Polygon link;

	/**
	 * Create a new robotic link
	 * @param globalX - starting x
	 * @param globalY - starting y
	 * @param length - length of the robotic link
	 * @param angle - starting angle of robotic link
	 */
	public RoboticLink( int globalX, int globalY, int length, int angle )
	{
		xOriginal = new int[]
		{ 0, 5, length - 5, length, length - 5, 5 };
		yOriginal = new int[]
		{ 0, 10, 10, 0, -10, -10 };
		link = new Polygon( xOriginal, yOriginal, yOriginal.length );

		globalPosition = new Point( globalX, globalY );
		this.length = length;
		this.localAngle = angle;

		this.onTranslate();
	}
	
	/**
	 * get the length of this link
	 * @return int
	 */
	public int getLength(){
		return this.length;
	}
	

	public void setLocalAngle(int angle){
		this.localAngle = angle;
	}
	
	/**
	 * Increase the angle of this link by one
	 */
	public void incrementAngle()
	{
		++localAngle;
	}

	/**
	 * Decrease the angle of this link by one
	 */
	public void decrementAngle()
	{
		--localAngle;
	}

	/**
	 * Set the current global position
	 * @param p
	 */
	public void setGlobalPosition( Point p, int angle )
	{
		this.globalPosition = p;
		this.globalAngle = angle + this.localAngle;
	}

	/**
	 * Get the angle of this link in degrees
	 * @return int
	 */
	public int getAngle()
	{
		return this.localAngle;
	}
	
	/**
	 * Get the angle of this link in degrees
	 * @return double
	 */
	public double getAngleRadians()
	{
		return Math.toRadians(this.localAngle);
	}

	/**
	 * Get the endpoint of this robotic arm in Global Space
	 * @return Point
	 */
	public Point getEndPointGlobal()
	{
		Point endpoint = new Point();
		endpoint.x = (int) (globalPosition.x + this.length
				* Math.cos( Math.toRadians( this.globalAngle ) ));
		endpoint.y = (int) (globalPosition.y + this.length
				* Math.sin( Math.toRadians( this.globalAngle ) ));
		return endpoint;
	}

	/**
	 * Called whenever a coordinate within this scope is changed
	 *  to recalculate the position of the polygon
	 */
	public void onTranslate()
	{
		for ( int i = 0; i < xOriginal.length; i++ )
		{

			double temp_x1 = xOriginal[i]
					* Math.cos( Math.toRadians( globalAngle ) ) - yOriginal[i]
					* Math.sin( Math.toRadians( globalAngle ) );
			double temp_y1 = xOriginal[i]
					* Math.sin( Math.toRadians( globalAngle ) ) + yOriginal[i]
					* Math.cos( Math.toRadians( globalAngle ) );

			link.xpoints[i] = (int) (temp_x1 + globalPosition.x);
			link.ypoints[i] = (int) (temp_y1 + globalPosition.y);
		}
	}

	/**
	 * Draw this link and any subsequent attached link(s)
	 * @param g Graphics
	 */
	public void onDraw( Graphics g )
	{
		g.setColor( Color.WHITE );
		g.fillPolygon( link );
	}
}
