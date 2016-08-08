package C2_Assignmentwk1.makeGrayBatch;

/**
 * GrayScaleConverter
 * Batch processes several images, and creates and saves new images that are grayscale versions of each image.
 * Convert any number of images to a gray scale version by setting all color components of each pixel to the same value.
 * 
 */
import edu.duke.*;
import java.io.*;

public class GrayScaleConverter {
	
        // converts each pixel in the image to gray-scale
	public ImageResource makeGray(ImageResource inImage) {
		//make blank image of the same size
		ImageResource outImage = new ImageResource(inImage.getWidth(), inImage.getHeight());

		for (Pixel pixel: outImage.pixels()) {
			//look at the corresponding pixel in inImage, calculate average and set it to all pixel values
			Pixel inPixel = inImage.getPixel(pixel.getX(), pixel.getY());
			int average = (inPixel.getRed() + inPixel.getBlue() + inPixel.getGreen())/3;
			pixel.setRed(average);
			pixel.setGreen(average);
			pixel.setBlue(average);
		}
		
		return outImage;
	}
	
        // select each file from the directory and call makeGray for that
	public void selectAndConvert () {
		DirectoryResource dr = new DirectoryResource();
		for (File f : dr.selectedFiles()) {
			ImageResource inImage = new ImageResource(f);
			ImageResource gray = makeGray(inImage);
			gray.draw();
		}
	}

	public void testGray() {
		ImageResource ir = new ImageResource();
		ImageResource gray = makeGray(ir);
		gray.draw();
	}
}
