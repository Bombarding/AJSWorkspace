import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class Main
{

	private static final String MODE_NONE = "None";
	private static final String MODE_CONTROLLER = "Controller";
	private static final String MODE_RECEIVER = "Real Time Receiver";
	private static final String MODE_RECEIVERDELAYED = "Delayed Receiver";

	/**
	 * All the buttons to interact with the robotic arm
	 */
	private static JButton incAx1, decAx1, incAx2, decAx2, incAx3, decAx3,
			paintCircle;

	/**
	 * x/y +/- controls
	 */
	private static JButton yInc, yDec, xInc, xDec;

	/**
	 * the command control button
	 */
	private static JButton commandMode;

	/**
	 * Current mode the robotic arm is running in
	 */
	private static String currentCommandMode = MODE_NONE;

	/**
	 * manages server/client calls
	 */
	private Timer commandTimer;


	private static Timer serverTimer;


	/**
	 * Displays the current angles of the robotic arm
	 */
	private static JLabel degAx1, degAx2, degAx3, worldCoordinateX,
			worldCoordinateY;

	/**
	 * manages each of the three robotic arm segments
	 */
	private static RoboticArm roboticArm;

	/**
	 * takes care of drawing the robotic arm
	 */
	private static RobotDisplay displayPanel;

	private static boolean isMousePressed;

	private static JButton currentButton;

	private static class RobotDisplay extends JPanel
	{
		public void paintComponent( Graphics g )
		{
			super.paintComponent( g );
			g.setColor( Color.BLACK );
			g.fillRect( 0, 0, 600, 600 );
			roboticArm.onDraw( g );
		}
	}

	/**
	 * Called whenever an axis button is pressed
	 */
	private static class OnAxisButtonPress implements ActionListener
	{
		public void actionPerformed( ActionEvent e )
		{
			JButton button = (JButton) e.getSource();
			currentButton = button;
			if ( button.equals( incAx1 ) )
			{
				roboticArm.incrementLink1();
			}
			if ( button.equals( incAx2 ) )
			{
				roboticArm.incrementLink2();
			}
			if ( button.equals( incAx3 ) )
			{
				roboticArm.incrementLink3();
			}
			if ( button.equals( decAx1 ) )
			{
				roboticArm.decrementLink1();
			}
			if ( button.equals( decAx2 ) )
			{
				roboticArm.decrementLink2();
			}
			if ( button.equals( decAx3 ) )
			{
				roboticArm.decrementLink3();
			}
			if ( button.equals( paintCircle ) )
			{
				roboticArm.paintCircle();
				paintCircle.setText( roboticArm.isPainting() ? "Painting: On"
						: "Painting: Off" );
			}
			roboticArm.onTranslate();
			updateLabels();
			displayPanel.repaint();
		}
	}

	/**
	 * Called whenever an axis button is pressed
	 */
	private static class OnWorldControlButtonPress implements ActionListener
	{
		public void actionPerformed( ActionEvent e )
		{
			JButton button = (JButton) e.getSource();
			currentButton = button;
			if ( button.equals( xInc ) )
			{
				roboticArm.incrementGlobalX();
			}
			if ( button.equals( xDec ) )
			{
				roboticArm.decrementGlobalX();
			}
			if ( button.equals( yInc ) )
			{
				roboticArm.incrementGlobalY();
			}
			if ( button.equals( yDec ) )
			{
				roboticArm.decrementGlobalY();
			}
			roboticArm.onWorldTranslate();
			updateLabels();
			displayPanel.repaint();
		}
	}


	private static void setButtonState(boolean state){

		incAx1.setEnabled( state );
		decAx1.setEnabled( state );
		incAx2.setEnabled( state );
		decAx2.setEnabled( state );
		incAx3.setEnabled( state );
		decAx3.setEnabled( state );
		paintCircle.setEnabled( state );
		yInc.setEnabled( state );
		yDec.setEnabled( state );
		xInc.setEnabled( state );
		xDec.setEnabled( state );
	}

	private static void updateLabels()
	{
		int[] coords = roboticArm.getWorldCoordinates();
		worldCoordinateX.setText( coords[0] + "" );
		worldCoordinateY.setText( coords[1] + "" );

		degAx1.setText( "" + roboticArm.getLink1Angle() );
		degAx2.setText( "" + roboticArm.getLink2Angle() );
		degAx3.setText( "" + roboticArm.getLink3Angle() );
	}

	public static void main( String[] args )
	{

		isMousePressed = false;

		int delay = 50;
		ActionListener taskPerformer = new ActionListener()
		{
			public void actionPerformed( ActionEvent evt )
			{
				if ( isMousePressed )
				{
					currentButton.doClick();
				}
			}
		};
		new Timer( delay, taskPerformer ).start();

		roboticArm = new RoboticArm( 300, 600 );

		displayPanel = new RobotDisplay();
		displayPanel.setBounds( 0, 0, 600, 600 );

		OnAxisButtonPress onAxisButtonPress = new OnAxisButtonPress();
		OnWorldControlButtonPress onWorldControlButtonPress = new OnWorldControlButtonPress();

		/**
		 * Create axis editing buttons
		 */
		incAx1 = new JButton( "+" );
		incAx1.addActionListener( onAxisButtonPress );
		decAx1 = new JButton( "-" );
		decAx1.addActionListener( onAxisButtonPress );

		incAx2 = new JButton( "+" );
		incAx2.addActionListener( onAxisButtonPress );
		decAx2 = new JButton( "-" );
		decAx2.addActionListener( onAxisButtonPress );

		incAx3 = new JButton( "+" );
		incAx3.addActionListener( onAxisButtonPress );
		decAx3 = new JButton( "-" );
		decAx3.addActionListener( onAxisButtonPress );

		paintCircle = new JButton( "Painting: Off" );
		paintCircle.addActionListener( onAxisButtonPress );

		/**
		 * Create x/y +/- editing buttons
		 */
		yInc = new JButton( "+Y" );
		yInc.addActionListener( onWorldControlButtonPress );
		yDec = new JButton( "-Y" );
		yDec.addActionListener( onWorldControlButtonPress );
		xInc = new JButton( "+X" );
		xInc.addActionListener( onWorldControlButtonPress );
		xDec = new JButton( "-X" );
		xDec.addActionListener( onWorldControlButtonPress );

		/**
		 * Create axis degree labels
		 */
		degAx1 = new JLabel( "" );
		degAx2 = new JLabel( "" );
		degAx3 = new JLabel( "" );
		worldCoordinateX = new JLabel( "" );
		worldCoordinateY = new JLabel( "" );

		/**
		 * Create control panel, add buttons
		 */
		JPanel controlPanel = new JPanel();
		controlPanel.setBackground( Color.WHITE );
		controlPanel.setBorder( new EmptyBorder( 10, 10, 10, 10 ) );
		controlPanel.setLayout( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		controlPanel.add( new JLabel( "Axis 1", SwingConstants.CENTER ), c );
		c.gridx = 1;
		c.gridy = 1;
		controlPanel.add( incAx1, c );
		c.gridx = 2;
		c.gridy = 1;
		controlPanel.add( degAx1, c );
		c.gridx = 3;
		c.gridy = 1;
		controlPanel.add( decAx1, c );

		c.gridx = 0;
		c.gridy = 2;
		controlPanel.add( new JLabel( "Axis 2", SwingConstants.CENTER ), c );
		c.gridx = 1;
		c.gridy = 2;
		controlPanel.add( incAx2, c );
		c.gridx = 2;
		c.gridy = 2;
		controlPanel.add( degAx2, c );
		c.gridx = 3;
		c.gridy = 2;
		controlPanel.add( decAx2, c );

		c.gridx = 0;
		c.gridy = 3;
		controlPanel.add( new JLabel( "Axis 3", SwingConstants.CENTER ), c );
		c.gridx = 1;
		c.gridy = 3;
		controlPanel.add( incAx3, c );
		c.gridx = 2;
		c.gridy = 3;
		controlPanel.add( degAx3, c );
		c.gridx = 3;
		c.gridy = 3;
		controlPanel.add( decAx3, c );

		c.gridx = 0;
		c.gridy = 7;
		controlPanel.add( new JLabel( "X Axis", SwingConstants.CENTER ), c );
		c.gridx = 1;
		c.gridy = 7;
		controlPanel.add( xDec, c );
		c.gridx = 2;
		c.gridy = 7;
		controlPanel.add( worldCoordinateX, c );
		c.gridx = 3;
		c.gridy = 7;
		controlPanel.add( xInc, c );

		c.gridx = 0;
		c.gridy = 8;
		controlPanel.add( new JLabel( "Y Axis", SwingConstants.CENTER ), c );
		c.gridx = 1;
		c.gridy = 8;
		controlPanel.add( yDec, c );
		c.gridx = 2;
		c.gridy = 8;
		controlPanel.add( worldCoordinateY, c );
		c.gridx = 3;
		c.gridy = 8;
		controlPanel.add( yInc, c );

		c.gridwidth = 4;
		c.gridx = 0;
		c.gridy = 0;
		controlPanel.add( new JLabel( "Local Control", SwingConstants.CENTER ),
				c );
		c.gridy = 4;
		controlPanel.add( paintCircle, c );

		c.gridwidth = 4;
		c.gridx = 0;
		c.gridy = 5;
		controlPanel.add( new JLabel( "World Control", SwingConstants.CENTER ),
				c );

		c.gridwidth = 4;
		c.gridx = 0;
		c.gridy = 9;
		controlPanel.add(
				new JLabel( "Controller Mode", SwingConstants.CENTER ), c );

		ActionListener serverTask = new ActionListener()
		{
			public void actionPerformed( ActionEvent evt )
			{
				if ( currentCommandMode == MODE_CONTROLLER )
				{
					roboticArm.postToServer();
				}
				if ( currentCommandMode == MODE_RECEIVER || currentCommandMode == MODE_RECEIVERDELAYED)
				{
					roboticArm.checkServer();
					roboticArm.onTranslate();
					displayPanel.repaint();
				}
			}
		};
		
		serverTimer = new Timer( 100, serverTask );
		serverTimer.start();


		commandMode = new JButton( currentCommandMode );
		commandMode.addActionListener( new ActionListener()
		{

			@Override
			public void actionPerformed( ActionEvent e )
			{
				if ( currentCommandMode == MODE_RECEIVER )
				{
					currentCommandMode = MODE_RECEIVERDELAYED;
					serverTimer.setDelay(2000);
				} else if ( currentCommandMode == MODE_CONTROLLER )
				{
					currentCommandMode = MODE_RECEIVER;
					serverTimer.setDelay(100);
				} else if ( currentCommandMode == MODE_NONE )
				{
					currentCommandMode = MODE_CONTROLLER;
					serverTimer.setDelay(100);
				} else if(currentCommandMode == MODE_RECEIVERDELAYED){
					currentCommandMode = MODE_NONE;
					serverTimer.setDelay(10000);
				}
				setButtonState( currentCommandMode != MODE_RECEIVER );
				commandMode.setText( currentCommandMode );
			}
		} );

		c.gridwidth = 4;
		c.gridx = 0;
		c.gridy = 10;
		controlPanel.add( commandMode, c );

		/**
		 * Create the overall panel, and incorporate
		 *   all the sub-panels for this project
		 */
		JPanel panes = new JPanel();
		panes.setLayout( new BorderLayout() );
		panes.add( displayPanel, BorderLayout.CENTER );
		panes.add( controlPanel, BorderLayout.EAST );

		/**
		 * Additional items needed to setup the window
		 */
		JFrame window = new JFrame( "Team Ares - Project 3" );
		window.addMouseListener( new MouseAdapter()
		{
			@Override
			public void mousePressed( MouseEvent e )
			{
				isMousePressed = true;
			}

			@Override
			public void mouseReleased( MouseEvent e )
			{
				isMousePressed = false;
			}
		} );
		window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		window.setContentPane( panes );
		window.setSize( 830, 630 );
		window.setLocation( 100, 100 );
		window.setVisible( true );
		window.setResizable( false );
		currentButton = incAx1;


	}

}
