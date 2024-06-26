import java.util.Random;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;

public class SepiaFX implements ImageFX {

	private static final int WIDTH = 70;		// width of the image
	private static final int HEIGHT = 60;		// height of the image

	private GameWindow gw;

	private int x;
	private int y;

	private BufferedImage spriteImage;		// image for sprite effect
	private BufferedImage copyImage;		// copy of image

	Graphics2D g2;

	int time, timeChange;				// to control when the image is grayed
	boolean originalImage, sepiaImage;


	public SepiaFX (int x, int y,GameWindow gw) {
		this.gw = gw;

		Random random = new Random();
		this.x=x;
		this.y=y;

		time = 0;				// range is 0 to 40
		timeChange = 1;				// set to 1
		originalImage = true;
		sepiaImage = false;

		spriteImage = ImageManager.loadBufferedImage("code/images/doublecoins.png");
		copyImage = ImageManager.copyImage(spriteImage);		
							//  make a copy of the original image
		copyToSepia();

	}

	
    public void setLocation(){
        x=70;
    }

	public void remove(){
        x=-70;
    }

	private int toSepia (int pixel) {

  		int alpha, red, green, blue, gray;
		int newPixel;

		alpha = (pixel >> 24) & 255;
		red = (pixel >> 16) & 255;
		green = (pixel >> 8) & 255;
		blue = pixel & 255;

		int newRed = (int) (0.393 * red + 0.769 * green + 0.189 * blue);
		int newGreen = (int) (0.349 * red + 0.686 * green + 0.168 * blue);
		int newBlue = (int) (0.272 * red + 0.534 * green + 0.131 * blue);

		newRed = Math.min(255, newRed);
/*

		if (newRed > 255)
			newRed = 255;
*/
		newGreen = Math.min(255, newGreen);
		newBlue = Math.min(255, newBlue);

		// Set red, green, and blue channels

		newPixel = newBlue | (newGreen << 8) | (newRed << 16) | (alpha << 24);

		return newPixel;
	}


	private void copyToSepia() {
		int imWidth = copyImage.getWidth();
		int imHeight = copyImage.getHeight();

    		int [] pixels = new int[imWidth * imHeight];
    		copyImage.getRGB(0, 0, imWidth, imHeight, pixels, 0, imWidth);

		for (int i=0; i<pixels.length; i++) {
			pixels[i] = toSepia(pixels[i]);
		}
  
    		copyImage.setRGB(0, 0, imWidth, imHeight, pixels, 0, imWidth);
	}	


	public void draw (Graphics2D g2) {

		if (originalImage) {			// draw original (already in colour)
			g2.drawImage(spriteImage, x, y, WIDTH, HEIGHT, null);
		}
		else
		if (sepiaImage) {			// draw copy (already converted to sepia)
			g2.drawImage(copyImage, x, y, WIDTH, HEIGHT, null);
		}
	}


	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, WIDTH, HEIGHT);
	}


	public void update() {				// modify time and change the effect if necessary
	
		time = time + timeChange;

		if (time < 4) {			// original image shown for 20 units of time
			originalImage = true;
			sepiaImage = false;
		}
		else
		if (time < 8) {			// sepia image shown for 20 units of time
			originalImage = false;
			sepiaImage = true;
		}
		else {		
			time = 0;
		}
	}

}