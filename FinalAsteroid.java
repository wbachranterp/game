package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 * Class: Asteroid
 *
 * Description: The Asteroid class represents the asteroid objects that will appear on the screen and act as obstacles for this ship
 * Includes a Paint method that handles how the asteroid appears on the screen, a move method that controls its
 * movement, and a setStartPoint method that assigns random starting positions to the asteroids on the borders
 * of the game canvas.
 *
 * Usage: The Asteroid class extends the polygon class and its instantation is very similar.
 * Asteroids are instantiated in the FinalAsteroidShooter class with its constructor and then stored
 * in an array of asteroids to use for the game.
 */

public class Asteroid extends Polygon implements GameElements {

	/**
	 * An array of Points that defines the asteroids shape
	*/
	private Point[] asteroidPoints; 

	/**
	 * Represents the speed of the asteroids across the screen
	 */
	private final int stepSize = 2; 

	/**
	 * Random object for generating random positions for asteroids
	 */
	private Random random; 


	/**
	 *Constructs an Asteroid object with a shape, position, and rotation
	 *
	 *@param inShape The array of points representing the shape of the asteroid
	 *@param inPosition The starting position of the asteroid object
	 *@param inRotation The rotation of the asteroid object
	 */

	public Asteroid(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation); 
		this.asteroidPoints = inShape; 
		random = new Random(); 
	}

	/**
	 * Paints the asteroid objects onto the canvas of the game,
	 * the asteroids will be colored gray

	 *@param brush The Graphics object that paints the asteroid
	 */

	public void Paint(Graphics brush) {
		Point[] getPoints = this.getPoints();
		
		
		int[] xArray = new int[getPoints.length];
		int[] yArray = new int[getPoints.length];
		
		for(int i = 0; i < getPoints.length; i++) {
			xArray[i] = (int)getPoints[i].getX();
			yArray[i] = (int)getPoints[i].getY();
		}
		brush.setColor(Color.GRAY);
		brush.drawPolygon(xArray, yArray, getPoints.length);
		brush.fillPolygon(xArray, yArray, getPoints.length);
	}

	/**
	 * Moves the asteroids around the canvas based on a stepSize increment multiplied by sin or cos.
	 * Handles out of bounds where asteroids reset to starting positions if they exceed the canvas.
	 */
	
	public void move() {
		if(position.getX() <= 0 || position.getX() >= 800 || 
				position.getY() <= 0 || position.getY() >= 600) {
			setStartPoint(); 
		}
		else {
			position.setX(position.getX() - stepSize 
					* Math.cos(Math.toRadians(rotation)));
			position.setY(position.getY() - stepSize 
					* Math.sin(Math.toRadians(rotation)));
		}
	}

	/**
	 * Sets the start point to a random position on the bounds of the canvas.
	 * Randomly picks from 4 different starting x locations with random y positions for each set x position.
	 */

	public void setStartPoint() {
		int counter = random.nextInt(4); 
		
		if(counter == 0) {
			position.setX(20);
			position.setY(random.nextInt(580));
		}
		else if(counter == 1) {
			position.setX(780);
			position.setY(random.nextInt(580));
		}
		else if(counter == 2) {
			position.setX(random.nextInt(780)); 
			position.setY(20); 
		}
		else {
			position.setX(random.nextInt(780));
			position.setY(580);
		}
	}
}
