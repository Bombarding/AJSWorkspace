import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

public class RoboticArm
{
	private int globalX, globalY;

	private RoboticLink link1, link2, link3;

	private ArrayList<PaintBrush> brushes;

	private boolean isPainting;

	public RoboticArm( int globalX, int globalY )
	{
		link1 = new RoboticLink( globalX, globalY, 150, 270 );
		link2 = new RoboticLink( globalX, globalY, 100, 0 );
		link3 = new RoboticLink( globalX, globalY, 75, 0 );

		isPainting = false;
		brushes = new ArrayList<PaintBrush>();

		this.onTranslate();
	}

	public void incrementLink1()
	{
		link1.incrementAngle();
	}

	public void incrementLink2()
	{
		link2.incrementAngle();
	}

	public void incrementLink3()
	{
		link3.incrementAngle();
	}

	public void decrementLink1()
	{
		link1.decrementAngle();
	}

	public void decrementLink2()
	{
		link2.decrementAngle();
	}

	public void decrementLink3()
	{
		link3.decrementAngle();
	}

	public void paintCircle()
	{
		isPainting = !isPainting;
	}

	public boolean isPainting()
	{
		return this.isPainting;
	}

	public int getLink1Angle()
	{
		return link1.getAngle();
	}

	public int getLink2Angle()
	{
		return link2.getAngle();
	}

	public int getLink3Angle()
	{
		return link3.getAngle();
	}

	public int[] getWorldCoordinates()
	{
		int coords[] =
		{ globalX, globalY };
		return coords;
	}

	public void incrementGlobalX()
	{
		++globalX;
	}

	public void incrementGlobalY()
	{
		++globalY;
	}

	public void decrementGlobalX()
	{
		--globalX;
	}

	public void decrementGlobalY()
	{
		--globalY;
	}

	public void checkServer()
	{
		JSONObject json;
		try
		{
			json = JSONReader
					.readJsonFromUrl( "http://ec2-54-68-238-238.us-west-2.compute.amazonaws.com/get.php" );
			link1.setLocalAngle( json.getInt( "d1" ) );
			link2.setLocalAngle( json.getInt( "d2" ) );
			link3.setLocalAngle( json.getInt( "d3" ) );
			isPainting = (json.getInt("isPainting") == 0) ? false : true;
		} catch ( IOException | JSONException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// check server with: http://ec2-54-68-238-238.us-west-2.compute.amazonaws.com/show.php
		//http://ec2-54-68-238-238.us-west-2.compute.amazonaws.com/get.php
	}

	public void postToServer()
	{
		//http://ec2-54-68-238-238.us-west-2.compute.amazonaws.com/add.php?x=103&y=203&d1=10&d2=20&d3=30
		JSONObject json;
		try
		{
			json = JSONReader
					.readJsonFromUrl( "http://ec2-54-68-238-238.us-west-2.compute.amazonaws.com/add.php?d1="
							+ link1.getAngle()
							+ "&d2="
							+ link2.getAngle()
							+ "&d3=" + link3.getAngle()
							+ "&isPainting=" + (isPainting ? 1 : 0) );
		} catch ( IOException | JSONException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Called after any values are changed for one of the robotic links
	 */
	public void onTranslate()
	{
		link1.setGlobalPosition( new Point( 300, 600 ), 0 );
		link1.onTranslate();
		link2.setGlobalPosition( link1.getEndPointGlobal(), link1.getAngle() );
		link2.onTranslate();
		link3.setGlobalPosition( link2.getEndPointGlobal(), link1.getAngle()
				+ link2.getAngle() );
		link3.onTranslate();

		globalX = link3.getEndPointGlobal().x;
		globalY = link3.getEndPointGlobal().y;

		if ( isPainting )
		{
			brushes.add( new PaintBrush( link3.getEndPointGlobal(), 5,
					Color.RED ) );
		}
	}

	/**
	 * Called when a global position is manually changed
	 */
	public void onWorldTranslate()
	{
		int maxLength = link1.getLength() + link2.getLength()
				+ link3.getLength();

		int dX = globalX - 300;
		int dY = globalY - 600;

		int maxX = (int) (dX * Math.cos( link1.getAngleRadians() ));
		int maxY = (int) (dY * Math.sin( link1.getAngleRadians() ));
		int proposedLength = (int) Math.sqrt( Math.pow( maxX, 2 )
				+ Math.pow( maxY, 2 ) );

		if ( proposedLength < maxLength )
		{
			System.out.println( "robot arm will reach here" );

			// compensate for world coordinate system
			int glbX = globalX - 300, glbY = -(600 - globalY);

			// get lengths of each link
			double link1Len = link1.getLength();
			double link2Len = link2.getLength();
			double link3Len = link3.getLength();

			// total distance to point
			double totalDistance = Math.sqrt( Math.pow( glbX, 2 )
					+ Math.pow( glbY, 2 ) );

			double deltaDist = 0, deltaLinks = 0;

			int delta = 0;

			/**
			 * Length to world point
			 */
			if ( Math.abs( totalDistance - link1Len ) < link2Len - link3Len )
			{
				deltaDist = 2 * Math.pow( link1Len, 2 )
						- Math.pow( link2Len - link3Len, 2 );
				deltaLinks = 2 * link1Len * link1Len;
				delta = (int) Math.ceil( Math.toDegrees( Math.acos( deltaDist
						/ deltaLinks ) ) );
			}

			double l = Math.sqrt( Math.pow( totalDistance, 2 )
					+ Math.pow( link1Len, 2 ) - 2 * totalDistance * link1Len
					* Math.cos( Math.toRadians( delta ) ) );

			/**
			 * calculate each angle
			 */
			deltaDist = Math.pow( link1Len, 2 ) + Math.pow( l, 2 )
					- Math.pow( totalDistance, 2 );
			deltaLinks = 2 * link1Len * l;
			double omega = Math.toDegrees( Math.acos( deltaDist / deltaLinks ) );
			if ( Double.isNaN( omega ) )
			{
				omega = Math.toDegrees( Math.acos( Math.round( deltaDist
						/ deltaLinks ) ) );
			}

			deltaDist = Math.pow( link2Len, 2 ) + Math.pow( l, 2 )
					- Math.pow( link3Len, 2 );
			deltaLinks = 2 * link2Len * l;
			double alpha = Math.toDegrees( Math.acos( deltaDist / deltaLinks ) );
			if ( Double.isNaN( alpha ) )
			{
				alpha = Math.toDegrees( Math.acos( Math.round( deltaDist
						/ deltaLinks ) ) );
			}

			deltaDist = Math.pow( link2Len, 2 ) + Math.pow( link3Len, 2 )
					- Math.pow( l, 2 );
			deltaLinks = 2 * link2Len * link3Len;
			double beta = Math.toDegrees( Math.acos( deltaDist / deltaLinks ) );
			if ( Double.isNaN( beta ) )
			{
				beta = Math.toDegrees( Math.acos( Math.round( deltaDist
						/ deltaLinks ) ) );
			}

			double thetaLink1Len = Math.toDegrees( Math.atan2( glbY, glbX ) )
					- delta;

			double thetaLink2Len = ((180 - alpha) - omega);
			double thetaLink3Len = 180 - beta;

			link1.setLocalAngle( (int) Math.floor( thetaLink1Len ) );
			link2.setLocalAngle( (int) Math.floor( thetaLink2Len ) );
			link3.setLocalAngle( (int) Math.floor( thetaLink3Len ) );

			link1.setGlobalPosition( new Point( 300, 600 ), 0 );
			link1.onTranslate();
			link2.setGlobalPosition( link1.getEndPointGlobal(),
					link1.getAngle() );
			link2.onTranslate();
			link3.setGlobalPosition( link2.getEndPointGlobal(),
					link1.getAngle() + link2.getAngle() );
			link3.onTranslate();

			if ( isPainting )
			{
				brushes.add( new PaintBrush( link3.getEndPointGlobal(), 5,
						Color.RED ) );
			}

		} else
		{
			System.out.println( "robot arm will not reach here" );
		}
	}

	/**
	 * Draws each of the robotic links
	 * 
	 * @param g
	 *            Graphics
	 */
	public void onDraw( Graphics g )
	{
		for ( PaintBrush p : this.brushes )
		{
			p.onDraw( g );
		}
		link1.onDraw( g );
		link2.onDraw( g );
		link3.onDraw( g );
	}
}
