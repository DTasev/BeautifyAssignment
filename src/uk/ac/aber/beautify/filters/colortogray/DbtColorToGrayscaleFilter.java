package uk.ac.aber.beautify.filters.colortogray;

import java.awt.image.BufferedImage;

import uk.ac.aber.beautify.filters.Filter;
import uk.ac.aber.beautify.utils.BeautifyUtils;

public class DbtColorToGrayscaleFilter extends Filter {

	/**
	 * Need to provide a name for this filter so that it appears in the GUI!
	 * 
	 */
	public DbtColorToGrayscaleFilter() {
		this.setName("Greyscale Filter");
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
				
//		float[] averageVal = {0, 0, 0};
		
		// Let's collect some basic statistics about the image, namely, the average intensity value for each colour
		// channel.  We will then use this to adjust the brightness of each channel.
		/*for (int u = 0; u < img.getWidth(); u++) {
			for (int v = 0; v < img.getHeight(); v++) {

				float[] hsvValues = grabHSVValues(img, u, v);
				
				// Record the cumulative sum of intensities
				for (int i = 0; i < 3; i++) 
					averageVal[i] = averageVal[i] + hsvValues[i];	
			}
		}
*/		
		// The averageVal array will store the difference between the ideal average value (128) and the actual
		// average value for each colour channel.  This difference will then be added to each pixel to obtain
		// a naive brightness enhanced image.
//		for (int i = 0; i < 3; i++)
//			averageVal[i] = ((float)0.8) - (averageVal[i] / (img.getWidth() * img.getHeight()));
//		
//		averageVal[2] = ((float)0.4) - (averageVal[2] / (img.getWidth() * img.getHeight()));
		// Loop through the image adjusting the brightness of each pixel according the average shift we have
		// previously recorded and store the resulting values in the output image.
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

	private int[] grabRGBValues(float[] hsvValues) {
		int[] rgbVals = BeautifyUtils.HSVtoRGB(hsvValues);
		return rgbVals;
	}

	private float[] grabHSVValues(BufferedImage img, int u, int v) {
		// Grab the pixel values in BINARY
		int rawValue = img.getRGB(u, v);
		// Convert the pixel values from BINARY to Decimal Values
		int[] rgbVals = {(rawValue & 0xff0000) >> 16, (rawValue & 0x00ff00) >> 8, (rawValue & 0x0000ff)};
		// Convert the Decimal RGB values to float point HSV
		float[] hsvVals = BeautifyUtils.RGBtoHSV(rgbVals);
		//Color.RGBtoHSB(rgbVals[0], rgbVals[1], rgbVals[2], hsvVals);
		
		
		return hsvVals;
	}
	
}
