package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
/**
 * Class: Ship
 *
 * Description: 
 *
 * Usage: 
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
	 * Moves the Ship around the canvas based on a stepSize increment multiplied by sin or cos.
	 * Handles out of bounds where asteroids reset to starting positions if they exceed the canvas.
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
	
	
	public Point[] getPArray() {
		return shipPoints;
	}

	public void setPosition(int x, int y) {
		position.setX(x);
		position.setY(y);
	}
	
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
	
	public ShipGun getShipGun() {
		return shipGun;
	}
	
	public ArrayList<ShipGun> getBullets() {
		return bullets;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	public void setStepSize(int x) {
		stepSize = x; 
	}
	
	public double getXPosition() {
		return position.getX(); 
	}
	
	public double getYPosition() {
		return position.getY(); 
	}
	
	//SHIPGUN INNERCLASS
	protected class ShipGun extends Polygon {
		private int bulletSpeed = 20;

		public ShipGun(Point[] inShape, Point inPosition, double inRotation) {
			super(inShape, inPosition, inRotation);
		}

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
			
			
		void move() {
			position.setX(position.getX() - bulletSpeed 
					* Math.cos(Math.toRadians(rotation)));
			position.setY(position.getY() - bulletSpeed 
					* Math.sin(Math.toRadians(rotation)));

		}

		boolean outOfBounds() {
			return position.getX() < -10 || position.getX() > 810 
					|| position.getY() < -10 || position.getY() > 610;
		}

	}
	
	//BOOST INNERCLASS
	public class Boost extends Polygon implements KeyListener {
		
		private Point[] boostPoints; 
		private boolean boost; 
		private Ship ship; 
		
		public Boost(Point[] inShape, Point inPosition, double inRotation, Ship ship) {
			super(inShape, inPosition, inRotation);
			this.boostPoints = inShape;
			this.ship = ship; 
		}
		
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
		
		@Override
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			
			if(keyCode == KeyEvent.VK_Z) {
				boost = true;
			}
		}

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
		
		public boolean returnBoost() {
			return boost; 
		}
	}
}
