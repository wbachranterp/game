package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
/**
 * Class: Ship
 *
 * Description: Main ship class that contains two inner classes: Boost and ShipGun.
 * Represents the player's ship in the game
 * The ship's position, movement, and visual elements are managed by this class
 * The ship shooting and boosting are also controlled by this class as Boost and ShipGun
 * are inner classes of it.
 * Contains keyboard responsiveness for movement with KeyPressed and KeyReleased methods.
 * Contains a constrcutor, paint method, move method, and position setting method.
 * Usage: Constrcuted in FinalAsteroidShooter and then painted.
 */
public class Ship extends Polygon implements KeyListener, GameElements {
	/**
	 * An array of Points that defines ships shape
	*/
	private Point[] shipPoints;
	/**
	 * An int that defines the ships intial speed
	*/
	private int stepSize = 3;
	/**
	 * A boolean to use with keyboard responsivness to the up arrow key
	*/
	private boolean forward;
	/**
	 * A boolean to use with keyboard responsivness to the right arrow key
	*/
	private boolean right;
	/**
	 * A boolean to use with keyboard responsivness to the left arrow key
	*/
	private boolean left;
	/**
	 * A boolean to use with keyboard responsivness to the down arrow key
	*/
	private boolean down;
	/**
	 * An int to store the ships intial rotation
	*/
	private int rotate;
	/**
	 * A boolean to use with keyboard responsivness to the spacebar key
	*/
	private boolean shoot;
	
	private ShipGun shipGun;
	/**
	 * An ArrayList of ShipGun(which are the bullets it shoots) to store
  	 * so the ship can shoot a large amount of bullets
	*/
	private ArrayList<ShipGun> bullets = new ArrayList<>();
	
	/**
	 *Constructs a Ship object with a shape, position, and rotation
	 *
	 *@param inShape The array of points representing the shape of the Ship
	 *@param inPosition The starting position of the Ship object
	 *@param inRotation The rotation of the Ship object
	 */
	
	public Ship(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
		this.shipPoints = inShape;
	}
	/**
	 * Paints the Ship objects onto the canvas of the game,
	 * the Ship will be colored red.

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
		brush.setColor(Color.RED);
		brush.drawPolygon(xArray, yArray, getPoints.length);
		brush.fillPolygon(xArray, yArray, getPoints.length);
		
		brush.setColor(Color.white);
		for (int i = 0; i < bullets.size(); i++) {
			ShipGun bullet = bullets.get(i);
			bullet.move();
			bullet.Paint(brush); }
	}
	
	/**
	 * Moves the Ship around the canvas based on a stepSize increment multiplied by sin or cos while the forward key is held
	 * Handles out of bounds by setting the position of the ship to just before the border if it tries to cross
	 * If the left boolean is true, rotate is called so the ship rotates left
  	 * If the right boolean is true, rotate is called so the ship rotates right
    	 * If the shoot boolean is true, an ArrayList of ShipGun objects is painted moving out of the tip of the ship
      	 * If the ShipGun objects go out of bounds they are removed from the ArrayList and the ArrayList is deincremented to
	 * increase performance.
	 */
	public void move() {
		if(forward) { 
			if(position.getX() >= 800) {
				position.setX(799);
				position.setY(position.getY() - stepSize 
						* Math.sin(Math.toRadians(rotation)));
			}
			else if(position.getX() <= 0) {
				position.setX(1);
				position.setY(position.getY() - stepSize 
						* Math.sin(Math.toRadians(rotation)));
			}
			else if(position.getY() >= 570) {
				position.setX(position.getX() - stepSize 
						* Math.cos(Math.toRadians(rotation)));
				position.setY(569);
			}
			else if(position.getY() <= 0) {
				position.setX(position.getX() - stepSize 
						* Math.cos(Math.toRadians(rotation)));
				position.setY(1);
			}
			else {
				position.setX(position.getX() - stepSize 
						* Math.cos(Math.toRadians(rotation)));
				position.setY(position.getY() - stepSize 
						* Math.sin(Math.toRadians(rotation)));
			}
		}
		if (left) {
			rotate(-4);

		}
		if (right) {
			rotate(4);
		}
		if (shoot) {
			fireBullet();
		}
		
		for(int i = 0; i < bullets.size(); i++) {
			if(bullets.get(i).outOfBounds()) {
				bullets.remove(i);
				i--;
			}
		}
	}
	
	/**
	 *Returns the array of shipPoints that defines the polygon that represents the Ship
	 */
	public Point[] getPArray() {
		return shipPoints;
	}
	
	/**
	 * Sets the position of a polygon to a new set of coordinates, used for 
	 * preventing the ship from going out of bounds, resetting the ship when it gets hit, resetting asteroids
	 * when they get hit.
	 */
	
	public void setPosition(int x, int y) {
		position.setX(x);
		position.setY(y);
	}
	/**
	 * KeyPressed method implemented from KeyListener, checks for keyboad key pressed
  	 * When the key is pressed it sets the instance variable boolean to true so the correct movement/shooting can occur
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if(keyCode == KeyEvent.VK_UP) {
			forward = true;
		}
		if(keyCode == KeyEvent.VK_LEFT) {
			left = true;
			
		}
		if(keyCode == KeyEvent.VK_RIGHT) {
			right = true;
		}	
		if(keyCode == KeyEvent.VK_DOWN) {
			down = true;
		}
		if (keyCode == KeyEvent.VK_SPACE) {
			shoot = true;
		}
	}
	/**
	 * KeyReleased method implemented from KeyListener, checks for keyboad key released
  	 * When the key is released it sets the according instance variable boolean to false 
    	 * so the correct movement/shooting can stop.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if(keyCode == KeyEvent.VK_UP) {
			forward = false;
		}
		if(keyCode == KeyEvent.VK_LEFT) {
			left = false;
		}
		if(keyCode == KeyEvent.VK_RIGHT) {
			right = false;
		}
		if(keyCode == KeyEvent.VK_DOWN) {
			down = false;
		}
		if (keyCode == KeyEvent.VK_SPACE) {
			shoot = false;
		}
	}
	/**
	 *Responsible for creating and launching a new ShipGun object from the ship object.
  	 *Retreives the current position of the ship and sets the ShipGun origin to the origin of the ship
    	 *Handles construction of the ShipGun based on array of Points. Adds a ShipGun object to the bullets ArrayList
      	 *to store each bullet that will be fired from the ship
	*/
	private void fireBullet() {
		Point[] points = this.getPoints();
		Point bulletOrigin = points[0];

		Point[] bulletShape = { new Point(-2, 4), new Point(-2, -4), 
				new Point(2, -4), new Point(2, 4) };
		ShipGun bullet = new ShipGun(bulletShape, new Point(bulletOrigin.getX(),
				bulletOrigin.getY()), rotation);
		shipGun = bullet;
		bullets.add(bullet);
	}
	/**
	 *Returns ShipGun so the current ShipGun in the ArrayList can be accessed, specifically for checking collision.
	*/
	public ShipGun getShipGun() {
		return shipGun;
	}
	/**
	 *Returns the entire ShipGun ArrayList so it can be iterated and the current bullet can be found
	*/
	public ArrayList<ShipGun> getBullets() {
		return bullets;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	/**
	 *Setter for stepSize so the speed of the ship can be changed if its boosting
	*/
	public void setStepSize(int x) {
		stepSize = x; 
	}
	/**
	 *Getter for the polygons x position so that the exact curent location on the canvas can be found
  	 *Specifically useful for movement and collison checking
	*/
	public double getXPosition() {
		return position.getX(); 
	}
	/**
	 *Getter for the polygons y position so that the exact curent location on the canvas can be found
  	 *Specifically useful for movement and collison checking
	*/
	public double getYPosition() {
		return position.getY(); 
	}
	
	/**
	 * Inner Class: ShipGun
	 *
	 * Description: Represents the bullets that shoot out of the ship. Useful as an inner class
  	 * because it the bullets follow the movement and rotation of the ship and originate from the ship object
    	 * Contains a constructor for the bullets themself, a paint method to draw the bullets on the canvas, a move method
      	 * to shoot the bullets across the screen, and a outOfBounds method so that bullets are removed once they leave the
	 * screen to boost overall performance
  	 *
	 * Usage: ShipGun's are instantiated in the fireBullet() method of the Ship class where they
  	 * are then stored for shooting from the ship.
	 */
	protected class ShipGun extends Polygon {
		private int bulletSpeed = 20;

		/**
		 *Constructs a ShipGun object with a shape, position, and rotation
		 *
		 *@param inShape The array of points representing the shape of the ShipGun object
		 *@param inPosition The starting position of the ShipGun object
		 *@param inRotation The rotation of the ShipGun object
		 */
		public ShipGun(Point[] inShape, Point inPosition, double inRotation) {
			super(inShape, inPosition, inRotation);
		}
		/**
		 * Paints the ShipGun objects onto the canvas of the game,
		 * the ShipGun's will be white
	
		 *@param brush The Graphics object that paints the asteroid
		 */
		void Paint(Graphics brush) {
			Point[] getPoints = this.getPoints();

			int[] xArray = new int[getPoints.length];
			int[] yArray = new int[getPoints.length];

			for (int i = 0; i < getPoints.length; i++) {
				xArray[i] = (int) getPoints[i].getX();
				yArray[i] = (int) getPoints[i].getY();
			}

			brush.setColor(Color.WHITE);
			brush.drawPolygon(xArray, yArray, getPoints.length);
			brush.fillPolygon(xArray, yArray, getPoints.length);


		}
			
		/**
		 *Moves the bullets across the screen based on the bulletspeed integer * cos or sin.
		 */
		void move() {
			position.setX(position.getX() - bulletSpeed 
					* Math.cos(Math.toRadians(rotation)));
			position.setY(position.getY() - bulletSpeed 
					* Math.sin(Math.toRadians(rotation)));

		}
		/**
		 * Checks if a ShipGun object has gone outside the borders of the game and records it in
   	 	 * a boolean, useful for removing ShipGun objects that left the screen.
		 */
		boolean outOfBounds() {
			return position.getX() < -10 || position.getX() > 810 
					|| position.getY() < -10 || position.getY() > 610;
		}

	}
	
	/**
	 * Inner Class: Boost
	 *
	 * Description: Represents the ship's temporay speed boost abillity. It benefits from being an innerclass because
  	 * it depends on the ships current location as well as it's movement. It has access to the Ship's stepsize
    	 * directly because it is an innerclass, so it can easily speed up the ship while the boost key is held
      	 * and then slow it down back to normal speed once the key is released. The boost also produces 
	 * a visual effect coming out of the ship. The ship has a constructor extended from
	 * polygon with a ship as an additional parameter so it knows which ship to stem from, 
  	 * a paint method to paint the booster on the canvas, keyboard responsiveness methods, and a boost method
  	 * that actually boosts the ship.
  	 *
	 * Usage: Boost is instantiated in FinalAsteroidShooter as a inner class of Ship.
	 */
	public class Boost extends Polygon implements KeyListener {
		/**
		 * Array of points that represents the shape of the booster
		 */
		private Point[] boostPoints; 
		/**
	 	*Boolean that stores whether the ship should be boosting or not
		*/
		private boolean boost; 
		/**
	 	*Ship variable to store the current ship that is being checked
		*/
		private Ship ship; 
		/**
		 *Constructs a Boost object with a shape, position, rotation, and current ship
		 *
		 *@param inShape The array of points representing the shape of the asteroid
	 	 *@param inPosition The starting position of the asteroid object
		 *@param inRotation The rotation of the asteroid object
   		 *@param ship The current ship being boosted
	 	 */
		public Boost(Point[] inShape, Point inPosition, double inRotation, Ship ship) {
			super(inShape, inPosition, inRotation);
			this.boostPoints = inShape;
			this.ship = ship; 
		}
		/**
	 	* Paints the boost objects onto the canvas of the game,
	 	* the boost will be orange

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
			brush.setColor(Color.ORANGE);
			brush.drawPolygon(xArray, yArray, getPoints.length);
			brush.fillPolygon(xArray, yArray, getPoints.length);
		}
		/**
	 	* KeyPressed method implemented from KeyListener, checs if z is pressed so the 
   		* boost can begin
	 	*/
		@Override
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			
			if(keyCode == KeyEvent.VK_Z) {
				boost = true;
			}
		}
		/**
	 	* KeyReleased method implemented from KeyListener, checs if z is released so the 
   		* boost can conclude.
	 	*/
		@Override
		public void keyReleased(KeyEvent e) {
			int keyCode = e.getKeyCode();
			
			if(keyCode == KeyEvent.VK_Z) {
				boost = false;
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		/**
	 	* If the boost variable is true. The boost visual effect is set to be put on the ship
	 	*/
		public void boost() {
			if(boost) {
				position.setX(ship.getXPosition());
		        position.setY(ship.getYPosition());
			}
			else {
				position.setX(-15); 
				position.setY(-15);
			}
		}
		/**
	 	* returns true if the ship is boosting and false if it is not.
	 	*/
		public boolean returnBoost() {
			return boost; 
		}
	}
}
