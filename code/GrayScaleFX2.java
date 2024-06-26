import java.util.Random;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;

public class GrayScaleFX2 implements ImageFX {

	private static final int WIDTH = 50;		// width of the image
	private static final int HEIGHT = 70;		// height of the image

	private GameWindow gw;

	private int x;
	private int y;

	private BufferedImage spriteImage;		// image for sprite effect
	private BufferedImage copyImage;		// copy of image

	Graphics2D g2;

	int time, timeChange;				// to control when the image is grayed
	boolean originalImage, grayImage;


	public GrayScaleFX2 (int x, int y,GameWindow gw) {
		this.gw = gw;

		Random random = new Random();
		this.x=x;
		this.y=y;

		time = 0;				// range is 0 to 40
		timeChange = 1;				// set to 1
		originalImage = true;
		grayImage = false;

		spriteImage = ImageManager.loadBufferedImage("code/images/speed.png");
		copyImage = ImageManager.copyImage(spriteImage);		
							//  make a copy of the original image
		copyToGray();

	}

	public void setLocation(){
        x=70;
    }

	public void remove(){
        x=-70;
    }

	private int toGray (int pixel) {

  		int alpha, red, green, blue, gray;
		int newPixel;

		alpha = (pixel >> 24) & 255;
		red = (pixel >> 16) & 255;
		green = (pixel >> 8) & 255;
		blue = pixel & 255;

		// Calculate the value for gray

		gray = (int) (0.2126 * red + 0.7152 * green + 0.0722 * blue);

		// Set red, green, and blue channels to gray

		red = green = blue = gray;

		newPixel = blue | (green << 8) | (red << 16) | (alpha << 24);

		return newPixel;
	}


	private void copyToGray() {
		int imWidth = copyImage.getWidth();
		int imHeight = copyImage.getHeight();

    		int [] pixels = new int[imWidth * imHeight];
    		copyImage.getRGB(0, 0, imWidth, imHeight, pixels, 0, imWidth);

		for (int i=0; i<pixels.length; i++) {
			pixels[i] = toGray(pixels[i]);
		}
  
    		copyImage.setRGB(0, 0, imWidth, imHeight, pixels, 0, imWidth);
	}	


	public void draw (Graphics2D g2) {

		if (originalImage) {			// draw original (already in colour)
			g2.drawImage(spriteImage, x, y, WIDTH, HEIGHT, null);
		}
		else
		if (grayImage) {			// draw copy (already in grayscale)
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
			grayImage = false;
		}
		else
		if (time < 8) {			// gray scale image shown for 20 units of time
			originalImage = false;
			grayImage = true;
		}
		else {		
			time = 0;
		}
	}

}