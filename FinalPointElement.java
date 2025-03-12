package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;


/**
 *CLASS: PointElement
 *DESCRIPTION: Extending Polygon, PointElement is all in the paint method. PointElement contains a constructor for
 *the pointelement object being ceated, a paint method to show it on the canvas, and a move method to randomly change its
 *position to a new one when the ship hits it.
*/


public class PointElement extends Polygon implements GameElements {
	/**
	 * Random object for generating random positions for asteroids
	 */
	private Random random; 


	/**
	 *Constructs a PointElement object with a shape, position, and rotation
	 *
	 *@param inShape The array of points representing the shape of the PointElement
	 *@param inPosition The starting position of the PointElement object
	 *@param inRotation The rotation of the PointElement object
	 */

	public PointElement(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation); 
		random = new Random(); 
	}
	/**
	 * Paints the PointElement objects onto the canvas of the game,
	 * the asteroids will be colored green

	 *@param brush The Graphics object that paints the PointElement
	 */
	public void Paint(Graphics brush) {
		Point[] getPoints = this.getPoints();
		
		
		int[] xArray = new int[getPoints.length];
		int[] yArray = new int[getPoints.length];
		
		for(int i = 0; i < getPoints.length; i++) {
			xArray[i] = (int)getPoints[i].getX();
			yArray[i] = (int)getPoints[i].getY();
		}
		brush.setColor(Color.GREEN);
		brush.drawPolygon(xArray, yArray, getPoints.length);
		brush.fillPolygon(xArray, yArray, getPoints.length);
	}
	/**
	 * Moves the PointElement objects to a random location on the canvas
	 */
	public void move() {
		position.setX(random.nextInt(600) + 100);
		position.setY(random.nextInt(400) + 100);
	}
}
