package game;

import java.awt.Color;
import java.awt.Graphics;

public class Ship extends Polygon {
	 private Point[] shipPoints;
	
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
		brush.setColor(Color.white);
		brush.drawPolygon(xArray, yArray, getPoints.length);
	}
	
	
	
	public Point[] getPArray() {
		return shipPoints;
	}
}
