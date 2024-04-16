import javax.swing.JPanel;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
   A component that displays all the game entities
*/

public class GamePanel extends JPanel
		       implements Runnable {
   
	private static int NUM_ALIENS = 3;

	private SoundManager soundManager;
	
	private boolean isRunning;
	private boolean isPaused;

	private Thread gameThread;

	private BufferedImage image;
 	//private Image backgroundImage;

	// private Background background;
	private HashMap<String, Background> backgrounds;
	private MainCharacter mc;
	private ArrayList<Obstacles> obstacles;
	private ArrayList<PowerUps> powerUps;
	private Random random;

	public GamePanel () {
		
		isRunning = false;
		isPaused = false;
		soundManager = SoundManager.getInstance();
		random = new Random();

		image = new BufferedImage (400, 400, BufferedImage.TYPE_INT_RGB);  // this is the screen
	}


	public void createGameEntities() {

		// background = new Background(this, "images/Scroll-Background.png", 96);
		backgrounds = new HashMap<>();
		obstacles = new ArrayList<>();
		powerUps = new ArrayList<>();
		loadBackGrounds();
		mc = new MainCharacter(this);
		for (int i = 1; i <= 3; i++) {
			obstacles.add(new Laser(random.nextInt(0, 400), mc, this));
		}
	

	}


	public void run () {
		try {
			isRunning = true;
			while (isRunning) {
				if (!isPaused)
					gameUpdate();
				gameRender();
				Thread.sleep (50);	
			}
		}
		catch(InterruptedException e) {}
	}


	public void gameUpdate() {
/*
		for (int i=0; i<NUM_ALIENS; i++) {
			aliens[i].move();
		}

		imageFX.update();
		imageFX2.update();

		animation.update();
		animation2.update();
		animation3.update();
*/		Background test = backgrounds.get("level1");
		test.move(1);
		
		if(mc != null) mc.update();

		for (Obstacles ob : obstacles) {
			ob.update();
		}
	}


	public void updateBat (int direction) {

		if (isPaused)
			return;

		// if (background != null) {
		// 	background.move(direction);
		// }

		// if (bat != null) {
		// 	bat.move(direction);
		// }

	}


	public void gameRender() {

		// draw the game objects on the image

		Graphics2D imageContext = (Graphics2D) image.getGraphics();

		// background.draw(imageContext);
		Background test = backgrounds.get("level1");
		test.draw(imageContext);
		
		if(mc != null) mc.draw(imageContext);
		for (Obstacles ob : obstacles) {
			ob.draw();
		}

		Graphics2D g2 = (Graphics2D) getGraphics();	// get the graphics context for the panel
		g2.drawImage(image, 0, 0, 400, 400, null);

		imageContext.dispose();
		g2.dispose();
	}


	public void startGame() {				// initialise and start the game thread 

		if (gameThread == null) {
			//soundManager.playClip ("background", true);
			createGameEntities();
			gameThread = new Thread (this);			
			gameThread.start();

			if(mc != null){
				mc.start();
			}
		}

	}


	public void startNewGame() {				// initialise and start a new game thread 
		
		isPaused = false;

		if (gameThread == null || !isRunning) {
			//soundManager.playClip ("background", true);
			createGameEntities();
			gameThread = new Thread (this);			
			gameThread.start();

			
		}
	}


	public void pauseGame() {				// pause the game (don't update game entities)
		if (isRunning) {
			if (isPaused)
				isPaused = false;
			else
				isPaused = true;
		}
	}


	public void endGame() {					// end the game thread
		isRunning = false;
		//soundManager.stopClip ("background");
	}

	// public boolean isOnBat (int x, int y) {
	// 	return bat.isOnBat(x, y);
	// }


	private void loadBackGrounds(){
		// load all backgrounds into hashmap
		Background b1 = new Background(this, "images/backgrounds/background.png", 5);
		backgrounds.put("level1", b1);
	}
}