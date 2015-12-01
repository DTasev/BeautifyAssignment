package uk.ac.aber.beautify.filters.histogramEqualisationLAB;

import uk.ac.aber.beautify.filters.Filter;
import uk.ac.aber.beautify.filters.histogram.Histogram;
import uk.ac.aber.beautify.filters.histogram.normal.NormalHistogram;
import uk.ac.aber.beautify.filters.histogram.ShowHistogram;
import uk.ac.aber.beautify.filters.histogram.cumulative.CumulativeHistogram;
import uk.ac.aber.beautify.filters.histogram.equalisation.HistogramEqualiser;
import uk.ac.aber.beautify.utils.BeautifyUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Dimitar on 26/11/2015.
 */
@SuppressWarnings("ALL")
public class DbtLABHistogramEqualisation extends Filter {
    public static final int R = 0, L = 0;
    public static final int G = 1, A = 1;
    public static final int B = 2;

    public DbtLABHistogramEqualisation() {
        this.setName("Luminosity Histogram Equalisation Filter");
    }

    public BufferedImage filter(BufferedImage img) {
        BufferedImage outputImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        // initialise histograms
        Histogram nHist = new NormalHistogram(0, 100, 1);

        for (int u = 0; u < img.getWidth(); u++) {
            for (int v = 0; v < img.getHeight(); v++) {
                // Grab the converted LAB Values
                double[] labValues = grabLABValues(img, u, v);
                // creating histogram for only L channel
                nHist.addValue((int) labValues[L]);
            }
    }
    String[] channels = {"L Channel", "a Channel", "b Channel"};
    // show histogram
    //new ShowHistogram(nHist.getArray(), channels[L]);

    // Calculate Cumulative Histogram
    Histogram cHist = new CumulativeHistogram(nHist);

    // show cumulative histogram
    //new ShowHistogram(cHist.getArray(), channels[L]);

    // Equalisation on cumulative histogram
        Histogram eqHist = new HistogramEqualiser(nHist, img.getHeight() * img.getWidth());
        // New histogram to hold the values after they have been equalised and swapped
        Histogram eqNormHist = new NormalHistogram(0, 101, 1);

        for (int u = 0; u < img.getWidth(); u++) {
            for (int v = 0; v < img.getHeight(); v++) {
                double[] labValues = grabLABValues(img, u, v);

                // Get new pixel value from equalised histogram,
                // it holds what the new value for the pixel should be,
                // and set the current pixel's value to it
                labValues[L] = eqHist.getIndex((int) labValues[L]);
                // creating new histogram of equalised luminosity
                eqNormHist.addValue((int) labValues[L]);
                int[] rgbValues = BeautifyUtils.LABtoRGB(labValues);

                // Clamping RGB Values after transformation
                BeautifyUtils.clampRGB(rgbValues);
                outputImage.setRGB(u, v, ((rgbValues[0] & 0xff) << 16) | ((rgbValues[1] & 0xff) << 8) | (rgbValues[2] & 0xff));
            }
        }

        // creating cumulative histogram for equalised luminosity
        Histogram cumLHist = new CumulativeHistogram((NormalHistogram) eqNormHist);
        new ShowHistogram(cumLHist.getArray(), "Equalised Luminosity");

        return outputImage;
    }
}

