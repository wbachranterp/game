package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import game.Ship.ShipGun;

public class Ship extends Polygon implements KeyListener {
	private Point[] shipPoints;
	private int stepSize = 3;
	private boolean forward;
	private boolean right;
	private boolean left;
	private int rotate;
	private boolean shoot;
	private ShipGun shipGun;
	private ArrayList<ShipGun> bullets = new ArrayList<>();

	public Ship(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
		this.shipPoints = inShape;

	}

	void Paint(Graphics brush) {

		Point[] getPoints = this.getPoints();

		int[] xArray = new int[getPoints.length];
		int[] yArray = new int[getPoints.length];

		for (int i = 0; i < getPoints.length; i++) {
			xArray[i] = (int) getPoints[i].getX();
			yArray[i] = (int) getPoints[i].getY();
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

	public void move() {
		if (forward) {
			if (position.getX() > 800) {
				position.setX(770);
			}
			if (position.getX() < 0) {
				position.setX(30);
			}
			if (position.getY() > 570) {
				position.setY(530);
			}
			if (position.getY() < 0) {
				position.setY(30);
			}

			position.setX(position.getX() - stepSize * Math.cos(Math.toRadians(rotation)));
			position.setY(position.getY() - stepSize * Math.sin(Math.toRadians(rotation)));
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

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_UP) {
			forward = true;
		}
		if (keyCode == KeyEvent.VK_LEFT) {
			left = true;

		}
		if (keyCode == KeyEvent.VK_RIGHT) {
			right = true;
		}

		if (keyCode == KeyEvent.VK_SPACE) {
			shoot = true;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_UP) {
			forward = false;
		}
		if (keyCode == KeyEvent.VK_LEFT) {
			left = false;
		}
		if (keyCode == KeyEvent.VK_RIGHT) {
			right = false;
		}
		if (keyCode == KeyEvent.VK_SPACE) {
			shoot = false;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void setPosition(int x, int y) {
		position.setX(x);
		position.setY(y);
	}

	public void setStepSize() {
		stepSize += 2;
	}

	private void fireBullet() {
		Point[] points = this.getPoints();
		Point bulletOrigin = points[0];

		Point[] bulletShape = { new Point(-2, 4), new Point(-2, -4), new Point(2, -4), new Point(2, 4) };
		ShipGun bullet = new ShipGun(bulletShape, new Point(bulletOrigin.getX(), bulletOrigin.getY()), rotation);
		shipGun = bullet;
		bullets.add(bullet);
		
	}

	
	public ShipGun getShipGun() {
		return shipGun;
	}
	
	public ArrayList<ShipGun> getBullets() {
		return bullets;
	}
	
	
	
	
	
	
	
	//SHIPGUN CLASS
	protected class ShipGun extends Polygon {
		private int bulletSpeed = 20;

		public ShipGun(Point[] inShape, Point inPosition, double inRotation) {
			super(inShape, inPosition, inRotation);
			// TODO Auto-generated constructor stub
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
			position.setX(position.getX() - bulletSpeed * Math.cos(Math.toRadians(rotation)));
			position.setY(position.getY() - bulletSpeed * Math.sin(Math.toRadians(rotation)));

		}

		boolean outOfBounds() {
			return position.getX() < -10 || position.getX() > 810 || position.getY() < -10 || position.getY() > 610;
		}

	}
class Boost extends Polygon implements KeyListener {
		
		private Point[] boostPoints; 
		private boolean boost; 
		private Ship ship; 
		
		
		
		
		
	//BOOST CLASS
	protected Boost(Point[] inShape, Point inPosition, double inRotation, Ship ship) {
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

//SHIP CLASS
public double getXPosition() {
	return position.getX(); 
}

public double getYPosition() {
	return position.getY(); 
}

}
