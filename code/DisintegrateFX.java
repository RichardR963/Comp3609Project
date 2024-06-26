import java.util.Random;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;

public class DisintegrateFX implements ImageFX {

	private static final int WIDTH = 70;		// width of the image
	private static final int HEIGHT = 70;		// height of the image

	private GameWindow gw;

	private int x;
	private int y;

	private BufferedImage spriteImage;		// image for sprite effect
	private BufferedImage copy;			// copy of image

	Graphics2D g2;

	int time, timeChange;				// to control when the image is grayed


	public DisintegrateFX (int x, int y,GameWindow gw) {
		this.gw = gw;

		Random random = new Random();
		this.x=x;
		this.y=y;
		this.gw=gw;

		time = 0;				// range is 0 to 70
		timeChange = 1;				// how to increment time in game loop

		spriteImage = ImageManager.loadBufferedImage("code/images/invincible.png");
		copy = ImageManager.copyImage(spriteImage);		
							//  make a copy of the original image

	}

	public void setLocation(){
        x=70;
    }

	public void remove(){
        x=-70;
    }

  	public void eraseImageParts(BufferedImage im, int interval) {

    		int imWidth = im.getWidth();
    		int imHeight = im.getHeight();

    		int [] pixels = new int[imWidth * imHeight];
    		im.getRGB(0, 0, imWidth, imHeight, pixels, 0, imWidth);

		for (int i = 0; i < pixels.length; i = i + interval) {
      			pixels[i] = 0;    // make transparent (or black if no alpha)
		}
  
    		im.setRGB(0, 0, imWidth, imHeight, pixels, 0, imWidth);
  	}


	public void draw (Graphics2D g2) {

		if (time == 5)
			eraseImageParts(copy, 11);
		else
		if (time == 10)
			eraseImageParts(copy, 7);
		else
		if (time == 15)
			eraseImageParts(copy, 5);
		else
		if (time == 20)
			eraseImageParts(copy, 3);
		else
		if (time == 25)
			eraseImageParts(copy, 2);
		else
		if (time == 30)
			eraseImageParts(copy, 1);
		else
		if (time == 35)
			copy = ImageManager.copyImage(spriteImage);

		g2.drawImage(copy, x, y, WIDTH, HEIGHT, null);

	}


	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, WIDTH, HEIGHT);
	}


	public void update() {				// modify time
	
		time = time + timeChange;

		if (time > 35)			
			time = 0;
	}

}