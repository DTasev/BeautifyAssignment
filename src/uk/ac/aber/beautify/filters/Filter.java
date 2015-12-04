/**
 * The Filter class provides the abstract superclass that all filters used within the Beautify
 * system should extend from.  The filters are the meat of the program and the meat of the Filter
 * class is the filter() method.  The filter() method takes an ImageProcessor as input and returns
 * a filtered version.
 * 
 * You will want to inherit from this class for each filter that you implement.  Then, once you 
 * have implemented your filters make sure that they are linked into your implementation of the
 * BeautifyFilters interface so that they are accessible from the GUI.
 * 
 * @author Harry Strange
 */
package uk.ac.aber.beautify.filters;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

import uk.ac.aber.beautify.utils.BeautifyUtils;

public abstract class Filter {
	
	// The name of the filter.  Why not call it something catchy like, "Really Cool Filter"?
	private String name;
	
	
	/**
	 * This is the heart of the filtering system.  The filter method takes in an ImageProcessor
	 * object (the image to be filtered), processes that image, and then returns the filtered
	 * version.
	 *
	 * @param 	ip - the image to be filtered
	 * @return  ImageProcessor - the processed version of the image
	 */
	public BufferedImage filter(BufferedImage ip) {
		return ip;
	}

	
	/**
	 * getName() is generally called by the GUI to display the name of this filter in the tooltip 
	 * text and also below the filtered image.
	 * 
	 * @return	String - the name of the filter
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * Do we really need a Javadoc to explain what this method does?
	 * 
	 * @param name - the name of the filter
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	// Methods to help easily convert colour values
	
	/**
	 * 
	 * @param hsvValues -> pass a float[] of hsvValues and get the RGB values back
	 * @return returns an int[] with the rgb values, without the alpha channel
	 */
	public int[] grabRGBValues(float[] hsvValues) {
		int[] rgbVals = BeautifyUtils.HSVtoRGB(hsvValues);
		return rgbVals;
	}

	public float[] grabHSVValues(BufferedImage img, int u, int v) {
		int[] rgbVals = grabRGBValues(img, u, v);
		// Convert the Decimal RGB values to float point HSV
		float[] hsvVals = BeautifyUtils.RGBtoHSV(rgbVals);
		//Color.RGBtoHSB(rgbVals[0], rgbVals[1], rgbVals[2], hsvVals);
		
		
		return hsvVals;
	}

	public static int[] grabRGBValues(BufferedImage img, int u, int v) {
		// Grab the pixel values in BINARY
		int rawValue = img.getRGB(u, v);
		// Convert the pixel values from BINARY to Decimal Values
		int[] rgbVals = {(rawValue & 0xff0000) >> 16, (rawValue & 0x00ff00) >> 8, (rawValue & 0x0000ff)};
		return rgbVals;
	}
	public static int[] grabRGBValues(Raster inputRaster, int u, int v){
		int[] inputPixels = new int[3];
		inputRaster.getPixel(u, v, inputPixels);
		return inputPixels;
	}
	public double[] grabLABValues(Raster inputRaster, int u, int v) {
		return BeautifyUtils.RGBtoLAB(grabRGBValues(inputRaster, u, v));
	}
	public double[] grabLABValues(BufferedImage img, int u, int v) {
		return BeautifyUtils.RGBtoLAB(grabRGBValues(img, u, v));
	}

}
