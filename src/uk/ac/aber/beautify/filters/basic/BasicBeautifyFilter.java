package uk.ac.aber.beautify.filters.basic;

import java.awt.image.BufferedImage;

import uk.ac.aber.beautify.filters.Filter;
public class BasicBeautifyFilter extends Filter {

	/**
	 * Need to provide a name for this filter so that it appears in the GUI!
	 * 
	 */
	public BasicBeautifyFilter() {
		this.setName("Basic Beautify");
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
				
		int rawValue = 0;
		long[] averageVal = {0, 0, 0};
		
		// Let's collect some basic statistics about the image, namely, the average intensity value for each colour
		// channel.  We will then use this to adjust the brightness of each channel.
		for (int u = 0; u < img.getWidth(); u++) {
			for (int v = 0; v < img.getHeight(); v++) {
				// Grab the pixel values
				rawValue = img.getRGB(u, v);
				
				// Grab the intensity values of each colour channel
				int[] rgbVals = {(rawValue & 0xff0000) >> 16, (rawValue & 0x00ff00) >> 8, (rawValue & 0x0000ff)};
				
				// Record the cumulative sum of intensities
				for (int i = 0; i < 3; i++) 
					averageVal[i] = averageVal[i] + rgbVals[i];	
			
			}
		}
		
		// The averageVal array will store the difference between the ideal average value (128) and the actual
		// average value for each colour channel.  This difference will then be added to each pixel to obtain
		// a naive brightness enhanced image.
		for (int i = 0; i < 3; i++)
			averageVal[i] = (128) - (averageVal[i] / (img.getWidth() * img.getHeight()));
		
		// Loop through the image adjusting the brightness of each pixel according the average shift we have
		// previously recorded and store the resulting values in the output image.
		for (int u = 0; u < img.getWidth(); u++) {
			for (int v = 0; v < img.getHeight(); v++) {
				// Grab the pixel values
				rawValue = img.getRGB(u, v);
				
				int[] rgbVals = {(rawValue & 0xff0000) >> 16, (rawValue & 0x00ff00) >> 8, (rawValue & 0x0000ff)};
															
				for (int i = 0; i < 3; i++) 				
					rgbVals[i] = rgbVals[i] + (int) averageVal[i];	// Adjust the brightness for this channel
				
				// We need to clamp the pixel values to make sure that we stay in the range [0, 255]
				//rgbVals = BeautifyUtils.clamp(rgbVals);
				//!Commented because method is changed to supprot HSV

				// Set the pixel values in the output image to be the brightness enhanced values
				outputImage.setRGB(u, v, ((rgbVals[0] & 0xff) << 16) | ((rgbVals[1] & 0xff) << 8) | (rgbVals[2] & 0xff));
			}
		}
				
		return outputImage;		// Spit back our enhanced image!
	}
	
}
