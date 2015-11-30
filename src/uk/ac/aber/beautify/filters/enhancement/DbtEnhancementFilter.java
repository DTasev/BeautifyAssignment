package uk.ac.aber.beautify.filters.enhancement;

import java.awt.image.BufferedImage;

import uk.ac.aber.beautify.filters.Filter;
import uk.ac.aber.beautify.utils.BeautifyUtils;

public class DbtEnhancementFilter extends Filter {

	/**
	 * Need to provide a name for this filter so that it appears in the GUI!
	 * 
	 */
	public DbtEnhancementFilter() {
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
				
		double[] averageVal = new double[3];
		
//		int size = img.getHeight()*img.getWidth();
//		double[] storedLVal = new double[size];
		
		// Let's collect some basic statistics about the image, namely, the average intensity value for each colour
		// channel.  We will then use this to adjust the brightness of each channel.
		for (int u = 0; u < img.getWidth(); u++) {
			for (int v = 0; v < img.getHeight(); v++) {

				int[] rgbValues = grabRGBValues(img, u, v);
				
				double[] labValues = BeautifyUtils.XYZtoLAB(BeautifyUtils.RGBtoXYZ(rgbValues));
				
				// Record the current value of L in LAB Colour space
				//int pos = v*img.getHeight()+u;
				//System.out.println("ValPos: " + pos);
				//storedLVal[pos] = labValues[0];
				
				/*
				 * 00 01 02 03
				 * 1, 2, 3, 4
				 * 04 05 06 07
				 * 5, 6, 7, 8
				 */
				for(int i = 0; i < 3; i++)
					averageVal[i] = averageVal[i] + labValues[i];
			}
			
		}
		
		// The averageVal array will store the difference between the ideal average value (128) and the actual
		// average value for each colour channel.  This difference will then be added to each pixel to obtain
		// a naive brightness enhanced image.
		System.out.println("Pre-avg => AverageValues\nL: " + averageVal[0] + " a: " + averageVal[1] + " b: " + averageVal[2]);
		for (int i = 0; i < 3; i++)
			averageVal[i] = (averageVal[i] / (img.getWidth() * img.getHeight()));
		System.out.println("AverageValues\nL: " + averageVal[0] + " a: " + averageVal[1] + " b: " + averageVal[2]);

		// Loop through the image adjusting the brightness of each pixel according the average shift we have
		// previously recorded and store the resulting values in the output image.
		for (int u = 0; u < img.getWidth(); u++) {
			for (int v = 0; v < img.getHeight(); v++) {
				int[] rgbValues = grabRGBValues(img, u, v);
				
				double[] labValues = BeautifyUtils.XYZtoLAB(BeautifyUtils.RGBtoXYZ(rgbValues));
				
				if(labValues[0] < (averageVal[0]/3))
					labValues[0] += averageVal[0]/3;
				
				int[] rgbVals = BeautifyUtils.LABtoRGB(labValues);
				BeautifyUtils.clampRGB(rgbVals);
				// Set the pixel values in the output image to be the brightness enhanced values
				outputImage.setRGB(u, v, ((rgbVals[0] & 0xff) << 16) | ((rgbVals[1] & 0xff) << 8) | (rgbVals[2] & 0xff));
			}
		}
				
		return outputImage;		// Spit back our enhanced image!
	}	
}
