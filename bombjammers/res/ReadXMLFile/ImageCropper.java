package src.testParseXML;

/* ImageCropper.java */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
 
import javax.imageio.ImageIO;
 
public class ImageCropper {
 
	public static void main(String[] args) {
		try {
			BufferedImage originalImgage = ImageIO.read(new File("./res/test.png"));
			
			System.out.println("Original Image Dimension: "+originalImgage.getWidth()+"x"+originalImgage.getHeight());
 
			BufferedImage SubImgage = originalImgage.getSubimage(64, 64, 64, 64);
			System.out.println("Cropped Image Dimension: "+SubImgage.getWidth()+"x"+SubImgage.getHeight());
 
			File outputfile = new File("./res/croppedImage.png");
			ImageIO.write(SubImgage, "png", outputfile);
 
			System.out.println("Image cropped successfully: "+outputfile.getPath());
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}