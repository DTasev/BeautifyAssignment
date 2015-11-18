package uk.ac.aber.beautify.filters.grayscale;

import java.awt.image.BufferedImage;

import uk.ac.aber.beautify.filters.Filter;
import uk.ac.aber.beautify.utils.BeautifyUtils;

public class DbtGrayscaleFilter extends Filter {

	/**
	 * Need to provide a name for this filter so that it appears in the GUI!
	 * 
	 */
	public DbtGrayscaleFilter() {
		this.setName("Enchance Filter");
	}
	
	/**
	 * This is the basic image enhancement algorithm.  You will need to provide an improvement over
	 * this method - that is, create a slightly more advanced algorithm that takes aspects such as
	 * image statistics into account.  When providing results of your implementation you will need
	 * to compare your results against the results of running this basic filter.
	 * 
	 */
	public BufferedImage filter(BufferedImage img) {
		// We will store the processed image in this variable and return it at the end of the function
		BufferedImage outputImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		for (int u = 0; u < img.getWidth(); u++) {
			for (int v = 0; v < img.getHeight(); v++) {
				// Grab the converted HSV Values
				float[] hsvValues = grabHSVValues(img, u, v);
//				hsvValues[0] -= 0.2; // change hue
				hsvValues[1] = (float)0.0; // change saturation
				//hsvValues[2] += 0.1; // change value/b
//				for (int i = 0; i < 3; i++) 				
//					hsvValues[i] = hsvValues[i] + (float)averageVal[i];	// Adjust the brightness for this channel
//				
				// We need to clamp the pixel values to make sure that we stay in the range [0, 1]
				//hsvValues = BeautifyUtils.clamp(hsvValues);				

				int[] rgbVals = grabRGBValues(hsvValues);
				// Set the pixel values in the output image to be the brightness enhanced values
				outputImage.setRGB(u, v, ((rgbVals[0] & 0xff) << 16) | ((rgbVals[1] & 0xff) << 8) | (rgbVals[2] & 0xff));
			}
		}				
		return outputImage;		// Spit back our enhanced image!
	}	
}
