package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Ship extends Polygon implements KeyListener {
	 private Point[] shipPoints;
	 private int stepSize = 3;
	 private boolean forward;
	 private boolean right;
	 private boolean left;
	 private boolean down;
	 private int rotate;
	
	public Ship(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
		this.shipPoints = inShape;
		
	}
	
	void Paint(Graphics brush) {
		
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
	}
	
	public void move() {
		if(forward) { 
			position.setX(position.getX() - stepSize * Math.cos(Math.toRadians(rotation)));
			position.setY(position.getY() - stepSize * Math.sin(Math.toRadians(rotation)));
		}
		if(left) { 
			rotate(-2);
			
		}
		if(right) { 
			rotate(2);
		}
//		if(down) { 
//			position.setX(position.getX());
//			position.setY(position.getY() + stepSize);
//		}
		
	}
	
	public Point[] getPArray() {
		return shipPoints;
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
		
	}

	

	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	//Addition from Colin
	public void setPosition(int x, int y) {
		position.setX(x);
		position.setY(y);
	}
}
