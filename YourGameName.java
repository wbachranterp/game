package game;

/*
CLASS: YourGameNameoids
DESCRIPTION: Extending Game, YourGameName is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.

*/
import java.awt.*;
import java.awt.event.*;

class YourGameName extends Game implements KeyListener {
	static int counter = 0;
	private Ship ship;

	public YourGameName() {
		super("Asteroid", 800, 600);
		this.setFocusable(true);
		this.requestFocus();
		Point[] shipShape = { new Point(0, 0), new Point(0, 10), new Point(5, 15), new Point(10, 10),
				new Point(10, 0) };
		
		ship = new Ship(shipShape, new Point(300,400), 0);

	}

	public void paint(Graphics brush) {
		brush.setColor(Color.black);
		brush.fillRect(0, 0, width, height);

		// sample code for printing message for debugging
		// counter is incremented and this message printed
		// each time the canvas is repainted
		counter++;
		brush.setColor(Color.white);
		brush.drawString("Counter is " + counter, 10, 10);
		
		ship.Paint(brush);

	}

	public static void main(String[] args) {
		YourGameName a = new YourGameName();
		a.repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
