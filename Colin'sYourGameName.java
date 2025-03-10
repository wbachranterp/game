package game;

/*
CLASS: YourGameNameoids
DESCRIPTION: Extending Game, YourGameName is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.

*/
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.JOptionPane;

class YourGameName extends Game implements KeyListener {
	static int counter = 3;
	private Ship ship;
	private Asteroid[] asteroidArray; 
	private int direction = 180;
	private int asteroidDirection; 
	Random random; 

	public YourGameName() {
		super("Asteroid", 800, 600);
		this.setFocusable(true);
		this.requestFocus();
		this.addKeyListener((java.awt.event.KeyListener) this);
		Point[] shipShape = { new Point(0, 8), new Point(-6, 16), new Point(-12, 12), new Point(-8, 0),
				new Point(0, -8), new Point(8, 0), new Point(12, 12), new Point(6, 16) };

		Point[] asteroidShape = {
				new Point(0, 0), new Point(0, -8), new Point(8, -8), new Point(8, 0)
		};
		random = new Random(); 
		
		asteroidDirection = random.nextInt(360); 
		Asteroid asteroid1 = new Asteroid(asteroidShape, new Point(120, 330), asteroidDirection);
		asteroidDirection = random.nextInt(360);  
		Asteroid asteroid2 = new Asteroid(asteroidShape, new Point(0, 100), asteroidDirection);
		asteroidDirection = random.nextInt(360); 
		Asteroid asteroid3 = new Asteroid(asteroidShape, new Point(200, 170), asteroidDirection);
		asteroidDirection = random.nextInt(360);
		Asteroid asteroid4 = new Asteroid(asteroidShape, new Point(80, 150), asteroidDirection);
		asteroidDirection = random.nextInt(360); 
		Asteroid asteroid5 = new Asteroid(asteroidShape, new Point(50, 75), asteroidDirection);
		asteroidDirection = random.nextInt(360); 
		Asteroid asteroid6 = new Asteroid(asteroidShape, new Point(25, 50), asteroidDirection);
		asteroidDirection = random.nextInt(360); 
		Asteroid asteroid7 = new Asteroid(asteroidShape, new Point(90, 550), asteroidDirection);
		asteroidDirection = random.nextInt(360); 
		Asteroid asteroid8 = new Asteroid(asteroidShape, new Point(400, 380), asteroidDirection);
		
		asteroidArray = new Asteroid[] {asteroid1, asteroid2, asteroid3, 
				asteroid4, asteroid5, asteroid6, asteroid7, asteroid8}; 
		
		
		ship = new Ship(shipShape, new Point(360, 300), direction);

	}

	public void paint(Graphics brush) {
		checkEndGame(); 
		brush.setColor(Color.black);
		brush.fillRect(0, 0, width, height);

		// sample code for printing message for debugging
		// counter is incremented and this message printed
		// each time the canvas is repainted
		brush.setColor(Color.red);
		brush.drawString("Counter is " + counter, 10, 10);
		//keyPressed(this);

		
		ship.move();
		ship.Paint(brush);

		for(int i = 0; i < asteroidArray.length; i++) {
			asteroidArray[i].move(); 
			asteroidArray[i].Paint(brush);
			if(ship.collides(asteroidArray[i])) {
				counter--; 
				ship.setPosition(360, 300); 
			}
		}
	}

	public static void main(String[] args) {
		YourGameName a = new YourGameName();
		a.repaint();
	}

	public void checkEndGame() {
		if(counter == 0) {
			JOptionPane.showMessageDialog(this, "Game Over! You have no lives left.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
	        System.exit(0); 
		}
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
