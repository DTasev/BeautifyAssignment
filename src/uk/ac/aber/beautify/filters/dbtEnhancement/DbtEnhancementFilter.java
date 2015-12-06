package uk.ac.aber.beautify.filters.dbtEnhancement;

import uk.ac.aber.beautify.filters.Filter;
import uk.ac.aber.beautify.filters.averageFilter.AverageFilter;
import uk.ac.aber.beautify.filters.gaussianFilter.GaussianFilter;
import uk.ac.aber.beautify.filters.histogram.Histogram;
import uk.ac.aber.beautify.filters.histogram.ShowHistogram;
import uk.ac.aber.beautify.filters.histogram.cumulative.CumulativeHistogram;
import uk.ac.aber.beautify.filters.histogram.equalisation.HistogramEqualiser;
import uk.ac.aber.beautify.filters.histogram.normal.NormalHistogram;
import uk.ac.aber.beautify.utils.BeautifyUtils;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 * Created by Dimitar on 26/11/2015.
 */
@SuppressWarnings("ALL")
public class DbtEnhancementFilter extends Filter {
	public static final int RED = 0, LUM = 0;
	public static final int GREEN = 1;
	public static final int BLUE = 2;

	BufferedImage img;

	public DbtEnhancementFilter() {
		this.setName("Luminosity Histogram Equalisation And RGB Contrast Adjustment Filter");
	}

	public BufferedImage filter(BufferedImage img) {
		this.img = img;
		BufferedImage outputImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		WritableRaster outputRaster = img.getRaster();

		//calculateHistogram(outputRaster, "Pre enhancement");
		// perform contrast adjustment on image
		outputRaster = contrastAdjustment(outputRaster);
		// perform LAB equalisation on image
		outputRaster = labEqualisation(outputRaster);

		// perform filtering function on image
		outputRaster = new AverageFilter(outputImage, outputRaster).runRaster();

		//calculateHistogram(outputRaster, "Post enhancement");
		// set new image raster
		outputImage.setData(outputRaster);
		return outputImage;
	}

	private void calculateHistogram(WritableRaster outputRaster, String text) {
		Histogram redNormHist = new NormalHistogram(0, 255, 1);
		Histogram gnh = new NormalHistogram(0, 255, 1);
		Histogram bnh = new NormalHistogram(0, 255, 1);

		WritableRaster outRaster = outputRaster;

		for (int u = 0; u < img.getWidth(); u++) {
			for (int v = 0; v < img.getHeight(); v++) {
				// get RGB values from raster
				int[] rgbValues = grabRGBValues(outRaster, u, v);

				// create histograms of the RGB values
				redNormHist.addValue(rgbValues[RED]);
				gnh.addValue(rgbValues[GREEN]);
				bnh.addValue(rgbValues[BLUE]);
			}
		}

		new ShowHistogram(redNormHist.getArray(), text);
		new ShowHistogram(gnh.getArray(), text);
		new ShowHistogram(bnh.getArray(), text);
	}

	private WritableRaster contrastAdjustment(WritableRaster outputRaster) {

		// declare and initialise histograms
		Histogram redNormHist = new NormalHistogram(0, 255, 1);
		Histogram greenNormHist = new NormalHistogram(0, 255, 1);
		Histogram blueNormHist = new NormalHistogram(0, 255, 1);

		WritableRaster outRaster = outputRaster;

		for (int u = 0; u < img.getWidth(); u++){
			for (int v = 0; v < img.getHeight(); v++) {
				// get RGB values from raster
				int[] rgbValues = grabRGBValues(outRaster, u, v);

				// create histograms of the RGB values
				redNormHist.addValue(rgbValues[RED]);
				greenNormHist.addValue(rgbValues[GREEN]);
				blueNormHist.addValue(rgbValues[BLUE]);
			}
		}

		// declare and initialise cumulative histograms
		Histogram redCumuHist = new CumulativeHistogram(redNormHist);
		Histogram greenCumuHist = new CumulativeHistogram(greenNormHist);
		Histogram blueCumuHist = new CumulativeHistogram(blueNormHist);

		// set q(LOW) and q(HIGH) ranges
		float qlow = 0.1f;
		float qhigh = 0.1f;

		// containers for p(LOW) and p(HIGH) for each channel
		int[] plow = new int[3];
		int[] phigh = new int[3];

		// this is the calculation used to find p(LOW)
		int mlow = (int) (img.getHeight() * img.getWidth() * qlow);
		// this is the calculation used to find p(HIGH)
		int mhigh = (int) (img.getHeight() * img.getWidth() * (1 - qhigh));

		// Get the red channel cumulative histogram
		int[] rch = redCumuHist.getArray();

		// Find p(LOW) and p(HIGH) for Red channel
		plow[RED] = getP(rch, mlow, true);
		phigh[RED] = getP(rch, mhigh, false);

		// Get the green channel cumulative histogram
		int[] gch = greenCumuHist.getArray();

		// Find p(LOW) and p(HIGH) for Green channel
		plow[GREEN] = getP(gch, mlow, true);
		phigh[GREEN] = getP(gch, mhigh, false);

		// Get the blue channel cumulative histogram
		int[] bch = blueCumuHist.getArray();

		// Find p(LOW) and p(HIGH) for Blue channel
		plow[BLUE] = getP(bch, mlow, true);
		phigh[BLUE] = getP(bch, mhigh, false);

		// p(MIN) and p(HIGH) for RGB are 0 and 255 respectively
		int pmin = 0;
		int pmax = 255;

		for (int u = 0; u < img.getWidth(); u++) {
			for (int v = 0; v < img.getHeight(); v++) {
				int[] rgbValues = grabRGBValues(outRaster, u, v);
				// loop through channels, and set the pixels to appropriate values
				for (int i = 0; i < 3; i++) {
					// if lower than p(LOW) for the channel, set to p(MIN)
					if (rgbValues[i] <= plow[i]) {
						rgbValues[i] = pmin;
					// if higher than p(HIGH) for the channel, set to p(HIGH)
					} else if (rgbValues[i] >= phigh[i]) {
						rgbValues[i] = pmax;
					// else, link contains a picture of the formula, for easier reading
					// http://i.imgur.com/Ja14kzW.png / http://i.imgur.com/uoEvldp.png
					} else {
						rgbValues[i] = pmin + ((rgbValues[i] - plow[i]) * (pmax - pmin) / (phigh[i] - plow[i]));
					}
				}

				// clamp RGB values to be in 0-255 range
				BeautifyUtils.clampRGB(rgbValues);
				outRaster.setPixel(u, v, rgbValues);
			}
		}
		return outRaster;
	}

	private WritableRaster labEqualisation(WritableRaster outputRaster) {
		// declare and initialise the histogram
		Histogram normalHist = new NormalHistogram(0, 100, 1);
		WritableRaster outRaster = outputRaster;

		for (int u = 0; u < img.getWidth(); u++) {
			for (int v = 0; v < img.getHeight(); v++) {
				// Grab the converted LAB Values
				double[] labValues = grabLABValues(outRaster, u, v);
				// creating histogram for only L channel
				normalHist.addValue((int) labValues[LUM]);
			}
		}
		//new ShowHistogram(normalHist.getArray(), "Luminosity Hist");
		Histogram cumulativeHist = new CumulativeHistogram(normalHist);
		Histogram equalisedHist = new HistogramEqualiser(cumulativeHist, img.getHeight() * img.getWidth());

		for (int u = 0; u < img.getWidth(); u++) {
			for (int v = 0; v < img.getHeight(); v++) {
				double[] labValues = grabLABValues(outRaster, u, v);

				// Get new pixel value from equalised histogram,
				// it holds what the new value for the pixel should be,
				// and set the current pixel's value to it
				labValues[LUM] = equalisedHist.getIndex((int) labValues[LUM]);

				int[] rgbValues = BeautifyUtils.LABtoRGB(labValues);

				// Clamping RGB Values after transformation
				BeautifyUtils.clampRGB(rgbValues);
				outRaster.setPixel(u, v, rgbValues);
			}
		}
		return outRaster;
	}

	/**
	 * If boolean parameter is true it will calculate PLOW,
	 * if boolean parameter is false it will calculate PHIGH
	 *
	 * @param ch
	 * @param m
	 * @param low
	 * @return
	 */
	private int getP(int[] ch, int m, boolean low) {
		if (low) {
			for (int i = 0; i < 255; i++) {
				if (ch[i] >= m)
					return i;
			}
		} else {
			for (int i = 255; i > 0; i--) {
				if (ch[i] <= m)
					return i;
			}
		}
		// should never be reached
		return -1;
	}
}

