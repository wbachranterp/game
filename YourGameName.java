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
	private int direction = 180;

	public YourGameName() {
		super("Asteroid", 800, 600);
		this.setFocusable(true);
		this.requestFocus();
		this.addKeyListener(this);
		Point[] shipShape = { new Point(0, 8), new Point(-6, 16), new Point(-12, 12), new Point(-8, 0),
				new Point(0, -8), new Point(8, 0), new Point(12, 12), new Point(6, 16) };

		ship = new Ship(shipShape, new Point(360, 300), direction);

	}

	public void paint(Graphics brush) {
		brush.setColor(Color.black);
		brush.fillRect(0, 0, width, height);

		// sample code for printing message for debugging
		// counter is incremented and this message printed
		// each time the canvas is repainted
		counter++;
		brush.setColor(Color.red);
		brush.drawString("Counter is " + counter, 10, 10);
//		keyPressed(this);

		ship.move();
		ship.Paint(brush);

	}

	public static void main(String[] args) {
		YourGameName a = new YourGameName();
		a.repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		ship.keyPressed(e);

	}

	@Override
	public void keyReleased(KeyEvent e) {
		ship.keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
