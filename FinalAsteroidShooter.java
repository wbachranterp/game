package game;


import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

/**
 * CLASS: AsteroidShooter
 * DESCRIPTION: Extending Game, AsteroidShooter is all in the paint method. AsteroidShooter() is the main
 * construction method where all of the objects in the game are instantiated. It also has a paint method which paints the
 * objects onto the canvas that were constructed, and it has a checkendgame method which checks if the counter has hit 0 which
 * will then lead to the game ending
 * USAGE: Intialize it in the main method followed by repaint for the game to run.
*/

class AsteroidShooter extends Game implements KeyListener {
	/**
	 *A counter that counts the lives of the ship
	*/
	static int counter = 3;
	private Ship ship;
	/**
	 *An array that stores all the asteroid objects useds in the game
	*/
	private Asteroid[] asteroidArray; 
	/**
	 *Holds inital direction of the ship for easy access/editing
	*/
	private int direction = 0;
	/**
	 *Holds intial direwction of the asteroid
	*/
	private int asteroidDirection; 
	/**
	 *Random object used for all random functions in the class
	*/
	private Random random; 
	private PointElement pointElement; 
	/**
	 *An ArrayList of ShipGun classes that stores all the bullets used
  	 *in the game. ShipGun is an inner class of Ship.
	*/
	ArrayList<Ship.ShipGun> bullets; 
	/**
	 *Initial score is set to 0
	*/
	private int score = 0; 
	/**
	 *Decleration for a booster inner class of Ship.
	*/
	private Ship.Boost booster; 

	/**
	 *Main construction method of the class, handles construction of all the polygon subclasses(Asteroid, Ship, PointElement)
  	 *Creates the gameBoard for the game with the super construtor from game.
    	 *Adds the keylistener for keybaord reponsiveness
	*/
	public AsteroidShooter() {
		
		//Creates gameBoard
		super("Asteroid", 800, 600);
		this.setFocusable(true);
		this.requestFocus();
		this.addKeyListener((java.awt.event.KeyListener) this);
		random = new Random(); 
		
		//Initializes Ship
		Point[] shipShape = { new Point(0, 0), new Point(8, 6), 
				new Point(4, 12), new Point(-8, 8), new Point(-16, 0),
				new Point(-8, -8), new Point(4, -12), new Point(8, -6) };
		ship = new Ship(shipShape, new Point(360, 300), direction);
		
		//Initializes eight asteroids and stores them in an array
		Point[] asteroidShape = { new Point(0, 0), new Point(-20, -10),
				new Point(-30, -20), new Point(-10, -30), new Point(0, -20),    
			    new Point(20, -30), new Point(30, -20), new Point(10, -10),   
			    new Point(0, 0)  
		};
		asteroidDirection = random.nextInt(360); 
		Asteroid asteroid1 = new Asteroid(asteroidShape, new Point(120, 330), 
				asteroidDirection);
		asteroidDirection = random.nextInt(360);  
		Asteroid asteroid2 = new Asteroid(asteroidShape, new Point(0, 100), 
				asteroidDirection);
		asteroidDirection = random.nextInt(360); 
		Asteroid asteroid3 = new Asteroid(asteroidShape, new Point(200, 170), 
				asteroidDirection);
		asteroidDirection = random.nextInt(360);
		Asteroid asteroid4 = new Asteroid(asteroidShape, new Point(80, 150), 
				asteroidDirection);
		asteroidDirection = random.nextInt(360); 
		Asteroid asteroid5 = new Asteroid(asteroidShape, new Point(50, 75), 
				asteroidDirection);
		asteroidDirection = random.nextInt(360); 
		Asteroid asteroid6 = new Asteroid(asteroidShape, new Point(25, 50), 
				asteroidDirection);
		asteroidDirection = random.nextInt(360); 
		Asteroid asteroid7 = new Asteroid(asteroidShape, new Point(90, 550), 
				asteroidDirection);
		asteroidDirection = random.nextInt(360); 
		Asteroid asteroid8 = new Asteroid(asteroidShape, new Point(400, 380), 
				asteroidDirection);
		
		asteroidArray = new Asteroid[] {asteroid1, asteroid2, asteroid3, 
				asteroid4, asteroid5, asteroid6, asteroid7, asteroid8}; 
		
		//Initializes green point objects
		Point[] pointElementShape = {
				new Point(0, 0), new Point(0, -8), new Point(8, -8), 
				new Point(8, 0)
		};
		pointElement = new PointElement(pointElementShape, 
				new Point(random.nextInt(600) + 100, random.nextInt(400) + 100),
				0); 
		
		//Initializes Booster objects
		Point[] boosterShape = {
				new Point(-5, 0), new Point(5, 0), new Point(0, -10), 
				new Point(-5, 0)
		};
		booster = new Ship(shipShape, new Point(360, 300), direction).new 
				Boost(boosterShape, new Point(-5,5), direction, ship);
		
		//Initializes Bullets
		bullets = ship.getBullets();
	}
	/**
	 * Paints the ship object ands its movement, the asteroids from the asteroid array, the booster objct,
  	 * and the paint element object constructed in the AsteroidShooter method.
    	 * Calls the collides method to check for objects colliding into eachother on the board
      	 * and updating the objects based on their respective function

	 *@param brush The Graphics object that paints the asteroid
	 */
	public void paint(Graphics brush) {
		brush.setColor(Color.black);
		brush.fillRect(0, 0, width, height);

		// sample code for printing message for debugging
		// counter is incremented and this message printed
		// each time the canvas is repainted
		brush.setColor(Color.red);
		brush.drawString("Lives: " + counter + " Score: " + score, 10, 10);
		//keyPressed(this);

		
		booster.Paint(brush);
		booster.boost(); 
		
		pointElement.Paint(brush);
		
		if(ship.collides(pointElement)) {
			pointElement.setRandPosition(); 
			score+= 5; 
		}
		
		if(booster.returnBoost()) {
			ship.setStepSize(5); 
		}
		else {
			ship.setStepSize(3);
		}
		
		ship.move();
		ship.Paint(brush);

		for(int i = 0; i < asteroidArray.length; i++) {
			asteroidArray[i].move(); 
			asteroidArray[i].Paint(brush);
			
			if(ship.collides(asteroidArray[i])) {
				counter--; 
				ship.setPosition(360, 300); 
				if(counter == 0) {
					new Object() {
						public void endGame() {
							JOptionPane.showMessageDialog(AsteroidShooter.this, 
									"Game Over! You have no lives "
									+ "left. \n Score: " + score, "Game Over", 
									JOptionPane.INFORMATION_MESSAGE);
					        	System.exit(0); 
						}
					}.endGame();
				}
			}
					
			bullets.removeIf(bullet -> {
		    	boolean collided = false;
		    	for (Asteroid asteroid : asteroidArray) {
			        if (bullet.collides(asteroid)) {
			            asteroid.setStartPoint();
			            score++;
			            collided = true;
			            break; 
			        }
				   }
				    return collided; 
		});
		}
	}

	public static void main(String[] args) {
		AsteroidShooter a = new AsteroidShooter();
		a.repaint();
	}
	
	/**
	 * KeyPressed method implemented from KeyListener, checks for keyboad key pressed
  	 * which is used in other methods.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		ship.keyPressed(e);
		booster.keyPressed(e); 
	}
	/**
	 * KeyReleased method implemented from KeyListener, checks for keyboad key released
  	 * which is used in other methods.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		ship.keyReleased(e);
		booster.keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
