package uk.ac.aber.beautify.filters.invert;

import java.awt.image.BufferedImage;

import uk.ac.aber.beautify.filters.Filter;
import uk.ac.aber.beautify.utils.BeautifyUtils;

public class DbtInvertFilter extends Filter {
	/**
	 * Need to provide a name for this filter so that it appears in the GUI!
	 * 
	 */
	public DbtInvertFilter() {
		this.setName("Invert Filter");
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

		// Loop through the image adjusting the brightness of each pixel according the average shift we have
		// previously recorded and store the resulting values in the output image.
		for (int u = 0; u < img.getWidth(); u++) {
			for (int v = 0; v < img.getHeight(); v++) {
				int[] rgbValues = grabRGBValues(img, u, v);
								
				invertRGB(rgbValues);
				
				BeautifyUtils.clampRGB(rgbValues);
				// Set the pixel values in the output image to be the brightness enhanced values
				outputImage.setRGB(u, v, ((rgbValues[0] & 0xff) << 16) | ((rgbValues[1] & 0xff) << 8) | (rgbValues[2] & 0xff));
			}
		}
				
		return outputImage;		// Spit back our enhanced image!
	}

	private void invertRGB(int[] rgbVals) {
		for(int i = 0; i < 3; i++){
			rgbVals[i] = 255 - rgbVals[i];
		}
	}	
}
