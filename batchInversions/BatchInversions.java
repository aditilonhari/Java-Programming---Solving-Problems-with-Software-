package C2_Assignmentwk1;
import edu.duke.*;
import java.io.*;

/**
 * BatchInversions
 * Create new images that are photographic negatives (or inverted images) of selected images and save these new images with new filenames. 
 *
 */
public class BatchInversions {
    
    // returns a new image that is the inverse of the original image.
    ImageResource makeInversion(ImageResource inImage){
        ImageResource outImage = new ImageResource(inImage.getWidth(), inImage.getHeight());
        for(Pixel pixel : outImage.pixels()){
            Pixel inPixel = inImage.getPixel(pixel.getX(), pixel.getY());
            pixel.setRed(255 - inPixel.getRed());
            pixel.setGreen(255 - inPixel.getGreen());
            pixel.setBlue(255 - inPixel.getBlue());    
        }
        
        return outImage;
    }
    
    // handles the batch processing of files. It allows the user to select several files and display the images.
    void selectAndConvert(){
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles()){
            ImageResource inFile = new ImageResource(f);
            ImageResource inverted = makeInversion(inFile);
            String fname = inFile.getFileName();
            String newName = "inverted-"+ fname;
            inverted.setFileName(newName);
            inverted.draw();
            inverted.save();
        }
    }
}
