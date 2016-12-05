package iP;


import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;

import processing.core.PApplet;


public class IsabelPong extends PApplet 
{
	
	public static final double DEFAULT_PADDLE_HEIGHT = 120;
	public static final double PADDLE_WIDTH = 10;
	public static final double PADDLE_MAX_SPEED = 12;
	public static final double PADDLE_ACCELERATION = 3;
	public static final double PADDLE_DECCELERATION = 1.5;
	public static final double PADDLE_FRICTION = 0.2;
	public static final double PADDLE_BOUNCE = 1;
	
	public static final double BALL_WIDTH = 20;
	public static final double BALL_STARTING_SPEED = 4;
	public static final double BALL_MAX_SPEED = 20;
	
	public static final int PADDLE_HIT_TIMER = 250;
	
	public Rectangle.Double paddle1;
	public Rectangle.Double paddle2;
	public Rectangle.Double ball;
	
	public long paddle1hit;
	public long paddle2hit;
	
	public int score1 = 0;
	public int score2 = 0;
	
	public Point.Double dPaddle1 = new Point.Double(0, 0);
	public Point.Double dPaddle2 = new Point.Double(0, 0);
	public Point.Double dBall = new Point.Double(0, 0);
	
	public boolean wPressed = false;
	public boolean sPressed = false;
	public boolean aPressed = false;
	public boolean dPressed = false;
	public boolean upPressed = false;
	public boolean downPressed = false;
	public boolean leftPressed = false;
	public boolean rightPressed = false;
	
	public Random random = new Random();
	public boolean point = false;
	
	public Color cBall;
	public Color c1;
	public Color c2;

	public void setup() {
		size(displayWidth, displayHeight);
		frame.setResizable(true);
		background(0, 0, 0);
		
		paddle1 = new Rectangle.Double(30, (getHeight() - DEFAULT_PADDLE_HEIGHT) / 2, PADDLE_WIDTH, DEFAULT_PADDLE_HEIGHT);
		paddle2 = new Rectangle.Double(getWidth() - 30 - PADDLE_WIDTH, (getHeight() - DEFAULT_PADDLE_HEIGHT) / 2, PADDLE_WIDTH, DEFAULT_PADDLE_HEIGHT);
		ball = new Rectangle.Double((getWidth() - BALL_WIDTH) / 2, (getHeight() - BALL_WIDTH) / 2, BALL_WIDTH, BALL_WIDTH);
		
		resetPoint();
		startPoint();
	}

	public void draw() {
		background(0, 0, 0);
		
		ball();
		paddle1();
		paddle2();
		move();
		collision();
		score();
		
		stroke(c2);
		strokeWeight(4);
		line(2 * getWidth() / 5, 0, 2 * getWidth() / 5, getHeight());
		stroke(c1);
		line(3 * getWidth() / 5, 0, 3 * getWidth() / 5, getHeight());
	}
	
	public void score() {
		fill(c1);
		textSize(48);
		textAlign(LEFT, TOP);
		text(score1, 10, 10);
		
		fill(c2);
		textAlign(RIGHT, TOP);
		text(score2, getWidth() - 10, 10);
	}
	
	public void ball() {
		fill(cBall);
		stroke(cBall);
		ellipse((float) ball.getCenterX(), (float) ball.getCenterY(), (float) ball.getWidth(), (float) ball.getHeight());
	}
	
	public void paddle1() {
		fill(c1);
		stroke(c2);
		rect((float) paddle1.getX(), (float) paddle1.getY(), (float) paddle1.getWidth(), (float) paddle1.getHeight(), (float) paddle1.getWidth() / 2);
	}
	
	public void paddle2() {
		fill(c2);
		stroke(c1);
		rect((float) paddle2.getX(), (float) paddle2.getY(), (float) paddle2.getWidth(), (float) paddle2.getHeight(), (float) paddle2.getWidth() / 2);
	}
	
	public void move() {
		
		if (wPressed)
			dPaddle1.y = dPaddle1.getY() - PADDLE_ACCELERATION;			
		if (sPressed)
			dPaddle1.y = dPaddle1.getY() + PADDLE_ACCELERATION;
		if (aPressed)
			dPaddle1.x = dPaddle1.getX() - PADDLE_ACCELERATION;
		if (dPressed)
			dPaddle1.x = dPaddle1.getX() + PADDLE_ACCELERATION;
		if (upPressed)
			dPaddle2.y = dPaddle2.getY() - PADDLE_ACCELERATION;
		if (downPressed)
			dPaddle2.y = dPaddle2.getY() + PADDLE_ACCELERATION;
		if (leftPressed)
			dPaddle2.x = dPaddle2.getX() - PADDLE_ACCELERATION;
		if (rightPressed)
			dPaddle2.x = dPaddle2.getX() + PADDLE_ACCELERATION;

		ballRegulate();
		paddleRegulate();
		
		paddle1.x += dPaddle1.getX();
		paddle1.y += dPaddle1.getY();
		paddle2.x += dPaddle2.getX();
		paddle2.y += dPaddle2.getY();
		
		moveBall();
	}
	
	public void moveBall() {
		if (point) {
			ball.x += dBall.getX();
			ball.y += dBall.getY();
		}
	}
	
	public void paddleRegulate() {
		
		if (dPaddle1.x > PADDLE_DECCELERATION) {
			dPaddle1.x -= PADDLE_DECCELERATION;
		} else if (dPaddle1.x < - PADDLE_DECCELERATION) {
			dPaddle1.x += PADDLE_DECCELERATION;
		} else {
			dPaddle1.x = 0;
		}
		
		if (dPaddle1.y > PADDLE_DECCELERATION) {
			dPaddle1.y -= PADDLE_DECCELERATION;
		} else if (dPaddle1.y < - PADDLE_DECCELERATION) {
			dPaddle1.y += PADDLE_DECCELERATION;
		} else {
			dPaddle1.y = 0;
		}
		
		if (dPaddle2.x > PADDLE_DECCELERATION) {
			dPaddle2.x -= PADDLE_DECCELERATION;
		} else if (dPaddle2.x < - PADDLE_DECCELERATION) {
			dPaddle2.x += PADDLE_DECCELERATION;
		} else {
			dPaddle2.x = 0;
		}
		
		if (dPaddle2.y > PADDLE_DECCELERATION) {
			dPaddle2.y -= PADDLE_DECCELERATION;
		} else if (dPaddle2.y < - PADDLE_DECCELERATION) {
			dPaddle2.y += PADDLE_DECCELERATION;
		} else {
			dPaddle2.y = 0;
		}
		
		double paddle1Speed = dPaddle1.distance(0, 0);
		double paddle2Speed = dPaddle2.distance(0, 0);
		
		if (paddle1Speed > PADDLE_MAX_SPEED) {
			dPaddle1.x = PADDLE_MAX_SPEED * dPaddle1.getX() / paddle1Speed;
			dPaddle1.y = PADDLE_MAX_SPEED * dPaddle1.getY() / paddle1Speed;
		}
		
		if (paddle2Speed > PADDLE_MAX_SPEED) {
			dPaddle2.x = PADDLE_MAX_SPEED * dPaddle2.getX() / paddle2Speed;
			dPaddle2.y = PADDLE_MAX_SPEED * dPaddle2.getY() / paddle2Speed;
		}
	}
	
	public void ballRegulate() {
		
		double ballSpeed = dBall.distance(0, 0);
		
		if (ballSpeed > BALL_MAX_SPEED) {
			dBall.x = BALL_MAX_SPEED * dBall.getX() / ballSpeed;
			dBall.y = BALL_MAX_SPEED * dBall.getY() / ballSpeed;
		}
	}
	
	public void collision() {
		
		if (ball.getMinX() < 0) {
			score2();
		} else if (ball.getMaxX() > getWidth()) {
			score1();
		}
		
		if (ball.getMinY() < 0) {
			ball.y = 0;
			bounceVertical();
			moveBall();
		} else if (ball.getMaxY() > getHeight()) {
			ball.y = getHeight() - ball.getHeight();
			bounceVertical();
			moveBall();
		}
		
		if (paddle1.getMinY() < 0) {
			paddle1.y = 0;
			dPaddle1.y *= - PADDLE_BOUNCE;
		} else if (paddle1.getMaxY() > getHeight()) {
			paddle1.y = getHeight() - paddle1.getHeight();
			dPaddle1.y *= - PADDLE_BOUNCE;
		}
		
		if (paddle1.getMinX() < 0) {
			paddle1.x = 0;
			dPaddle1.x *= - PADDLE_BOUNCE;
		} else if (paddle1.getMaxX() > 2 * getWidth() / 5) {
			paddle1.x = 2 * getWidth() / 5 - paddle1.getWidth();
			dPaddle1.x *= - PADDLE_BOUNCE;
		}
		
		if (paddle2.getMinY() < 0) {
			paddle2.y = 0;
			dPaddle2.y *= - PADDLE_BOUNCE;
		} else if (paddle2.getMaxY() > getHeight()) {
			paddle2.y = getHeight() - paddle2.getHeight();
			dPaddle2.y *= - PADDLE_BOUNCE;
		}
		
		if (paddle2.getMinX() < 3 * getWidth() / 5) {
			paddle2.x = 3 * getWidth() / 5;
			dPaddle2.x *= - PADDLE_BOUNCE;
		} else if (paddle2.getMaxX() > getWidth()) {
			paddle2.x = getWidth() - paddle2.getWidth();
			dPaddle2.x *= - PADDLE_BOUNCE;
		}
		
		if (ball.intersects(paddle1) && millis() > paddle1hit + PADDLE_HIT_TIMER) {
			paddle1hit = millis();
			randomColors();
			
			if (ball.getMaxY() > paddle1.getMinY() + ball.getWidth() / 2 && ball.getMinY() < paddle1.getMaxY() - ball.getWidth() / 2 ) {
				bounceHorizontal(dPaddle1.getX());
				dBall.y += PADDLE_FRICTION * dPaddle1.getY() + 3 * (random.nextDouble() - 0.5);
				moveBall();
			} else {
				bounceVertical(dPaddle1.getY());
				dBall.x += PADDLE_FRICTION * dPaddle1.getX();
				moveBall();
			}
		}
		
		if (ball.intersects(paddle2) && millis() > paddle2hit + PADDLE_HIT_TIMER) {
			paddle2hit = millis();
			randomColors();
			
			if (ball.getMaxY() > paddle2.getMinY() + ball.getWidth() / 2 && ball.getMinY() < paddle2.getMaxY() - ball.getWidth() / 2) {
				bounceHorizontal(dPaddle2.getX());
				dBall.y += PADDLE_FRICTION * dPaddle2.getY() + 3 * (random.nextDouble() - 0.5);
				moveBall();
			} else {
				bounceVertical(dPaddle2.getY());
				dBall.x += PADDLE_FRICTION * dPaddle2.getX();
				moveBall();
			}
		}
	}
	
	public void bounceHorizontal() {
		bounceHorizontal(0);
	}
	
	public void bounceVertical() {
		bounceVertical(0);
	}
	
	public void bounceHorizontal(double reflect) {
		dBall.x =  PADDLE_BOUNCE * (2 * reflect - dBall.getX());
	}
	
	public void bounceVertical(double reflect) {
		dBall.y = PADDLE_BOUNCE * (2 * reflect - dBall.getY());
	}
	
	public void score1() {
		score1 ++;
		resetPoint();
		
		dBall.x = - dBall.getX();
	}
	
	public void score2() {
		score2 ++;
		resetPoint();
	}
	
	public void resetPoint() {
		ball.x = (getWidth() - ball.getWidth()) / 2;
		ball.y = (getHeight() - ball.getHeight()) / 2;
		
		double direction = random.nextDouble() - 0.5;
		dBall.x = BALL_STARTING_SPEED * Math.cos(direction);
		dBall.y = BALL_STARTING_SPEED * Math.sin(direction);
		
		paddle1hit = millis();
		paddle2hit = millis();
		
		randomColors();
	}
	
	public void startPoint() {
		point = true;
	}
	
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			wPressed = true;
			break;
		case KeyEvent.VK_S:
			sPressed = true;
			break;
		case KeyEvent.VK_A:
			aPressed = true;
			break;
		case KeyEvent.VK_D:
			dPressed = true;
			break;
		case KeyEvent.VK_UP:
			upPressed = true;
			break;
		case KeyEvent.VK_DOWN:
			downPressed = true;
			break;
		case KeyEvent.VK_LEFT:
			leftPressed = true;
			break;
		case KeyEvent.VK_RIGHT:
			rightPressed = true;
			break;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			wPressed = false;
			break;
		case KeyEvent.VK_S:
			sPressed = false;
			break;
		case KeyEvent.VK_A:
			aPressed = false;
			break;
		case KeyEvent.VK_D:
			dPressed = false;
			break;
		case KeyEvent.VK_UP:
			upPressed = false;
			break;
		case KeyEvent.VK_DOWN:
			downPressed = false;
			break;
		case KeyEvent.VK_LEFT:
			leftPressed = false;
			break;
		case KeyEvent.VK_RIGHT:
			rightPressed = false;
			break;
		}
	}
	
	public void randomColors() {
		cBall = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
		c1 = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
		c2 = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
	}
	
	public void fill(Color c) {
		fill(c.getRed(), c.getGreen(), c.getBlue());
	}
	
	public void stroke(Color c) {
		stroke(c.getRed(), c.getGreen(), c.getBlue());
	}

	public static void main(String _args[]) {
		PApplet.main(new String[] { isabelpong.IsabelPong.class.getName() });
	}
}
