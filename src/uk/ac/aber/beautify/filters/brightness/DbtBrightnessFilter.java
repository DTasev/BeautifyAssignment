package uk.ac.aber.beautify.filters.brightness;

import java.awt.image.BufferedImage;

import uk.ac.aber.beautify.filters.Filter;
import uk.ac.aber.beautify.utils.BeautifyUtils;

public class DbtBrightnessFilter extends Filter {
	/**
	 * Need to provide a name for this filter so that it appears in the GUI!
	 * 
	 */
	private double brightnessValue = 0.067890;

	public DbtBrightnessFilter() {
		this.setName("Brightness Filter");
	}

	/**
	 * This is the basic image enhancement algorithm. You will need to provide
	 * an improvement over this method - that is, create a slightly more
	 * advanced algorithm that takes aspects such as image statistics into
	 * account. When providing results of your implementation you will need to
	 * compare your results against the results of running this basic filter.
	 * 
	 */
	public BufferedImage filter(BufferedImage img) {
		// We will store the processed image in this variable and return it at
		// the end of the function
		BufferedImage outputImage = new BufferedImage(img.getWidth(),
				img.getHeight(), BufferedImage.TYPE_INT_RGB);

		// Loop through the image adjusting the brightness of each pixel
		// according the average shift we have
		// previously recorded and store the resulting values in the output
		// image.
		for (int u = 0; u < img.getWidth(); u++) {
			for (int v = 0; v < img.getHeight(); v++) {
				int[] rgbValues = grabRGBValues(img, u, v);

				/*
				 * Option 1
				 * RGB COnvert range from 0..255 to 0..1, change brightness, convert from
				 * 0..1 to 0..255, change pixels 
				 */
				double[] d_rgbValues =	{ 
						rgbValues[0] / 255.0,
						rgbValues[1] / 255.0, 
						rgbValues[2] / 255.0 
					};
				
				d_rgbValues[0] = d_rgbValues[0] + brightnessValue;
				d_rgbValues[1] = d_rgbValues[1] + brightnessValue;
				d_rgbValues[2] = d_rgbValues[2] + brightnessValue;

				rgbValues[0] = (int) Math.round(d_rgbValues[0] * 255);
				rgbValues[1] = (int) Math.round(d_rgbValues[1] * 255);
				rgbValues[2] = (int) Math.round(d_rgbValues[2] * 255);
				/*
				 * Option 2
				 * RGB > HSV, increase value, HSV > RGB, change pixels
				 */
				BeautifyUtils.clampRGB(rgbValues);
				// Set the pixel values in the output image to be the brightness
				// enhanced values
				outputImage.setRGB(u, v, ((rgbValues[0] & 0xff) << 16)
						| ((rgbValues[1] & 0xff) << 8) | (rgbValues[2] & 0xff));
			}
		}

		return outputImage; // Spit back our enhanced image!
	}
}
