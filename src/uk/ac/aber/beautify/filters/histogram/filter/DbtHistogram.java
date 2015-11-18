package uk.ac.aber.beautify.filters.histogram.filter;

import java.awt.image.BufferedImage;

import uk.ac.aber.beautify.filters.Filter;
import uk.ac.aber.beautify.filters.histogram.Histogram;
import uk.ac.aber.beautify.filters.histogram.ShowHistogram;
import uk.ac.aber.beautify.utils.BeautifyUtils;

public class DbtHistogram extends Filter {

	/**
	 * Need to provide a name for this filter so that it appears in the GUI!
	 * 
	 */
	public DbtHistogram() {
		this.setName("Histogram");
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
		
		Histogram r = new Histogram(0, 255, 1);
		Histogram g = new Histogram(0, 255, 1);
		Histogram b = new Histogram(0, 255, 1);
		
		for (int u = 0; u < img.getWidth(); u++) {
			for (int v = 0; v < img.getHeight(); v++) {
				// Grab the converted HSV Values
				int[] rgbValues = grabRGBValues(img, u, v);
				r.addValue(rgbValues[0]);
				g.addValue(rgbValues[1]);
				b.addValue(rgbValues[2]);
				
				
				
				int[] rgbVals = rgbValues;
				// Set the pixel values in the output image to be the brightness enhanced values
				outputImage.setRGB(u, v, ((rgbVals[0] & 0xff) << 16) | ((rgbVals[1] & 0xff) << 8) | (rgbVals[2] & 0xff));
			}
		}		
		ShowHistogram aa = new ShowHistogram(r.getArray());
		ShowHistogram ab = new ShowHistogram(g.getArray());
		ShowHistogram ac = new ShowHistogram(b.getArray());
		return outputImage;		// Spit back our enhanced image!
	}	
}
