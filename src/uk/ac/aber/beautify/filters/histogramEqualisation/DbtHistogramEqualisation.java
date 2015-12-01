package uk.ac.aber.beautify.filters.histogramEqualisation;

import uk.ac.aber.beautify.filters.Filter;
import uk.ac.aber.beautify.filters.histogram.Histogram;
import uk.ac.aber.beautify.filters.histogram.normal.NormalHistogram;
import uk.ac.aber.beautify.filters.histogram.ShowHistogram;
import uk.ac.aber.beautify.filters.histogram.cumulative.CumulativeHistogram;
import uk.ac.aber.beautify.filters.histogram.equalisation.HistogramEqualiser;
import uk.ac.aber.beautify.utils.BeautifyUtils;

import java.awt.image.BufferedImage;

/**
 * Created by Dimitar on 26/11/2015.
 */
@SuppressWarnings("ALL")
public class DbtHistogramEqualisation extends Filter {
    public static final int R = 0;
    public static final int G = 1;
    public static final int B = 2;

    public DbtHistogramEqualisation() {
        this.setName("Histogram Equalisation Filter");
    }

    public BufferedImage filter(BufferedImage img) {
        BufferedImage outputImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        Histogram[] nHist = new NormalHistogram[3];

        // initialise histograms
        for (int i = 0; i < 3; i++) {
            nHist[i] = new NormalHistogram(0, 255, 1);
        }

        for (int u = 0; u < img.getWidth(); u++) {
            for (int v = 0; v < img.getHeight(); v++) {
                // Grab the converted HSV Values
                int[] rgbValues = grabRGBValues(img, u, v);
                for (int i = 0; i < 3; i++) {
                    nHist[i].addValue(rgbValues[i]);
                }
            }
        }
        new ShowHistogram(nHist[0].getArray(), "RED");

        // Calculate Cumulative Histogram for Red, Green and Blue Channel
        Histogram[] cHist = new CumulativeHistogram[3];
        for (int i = 0; i < 3; i++) {
            cHist[i] = new CumulativeHistogram(nHist[i]);
        }

        String[] channels = {"R Channel", "G Channel", "B Channel"};
        // show cumulative histograms
        for (int i = 0; i < 3; i++) {
            new ShowHistogram(cHist[i].getArray(), channels[i]);
        }
        // Histogram Equalisation on cumulative histogram of Red, Green and Blue channels
        Histogram[] eqHist = new HistogramEqualiser[3];
        for (int i = 0; i < 3; i++) {
            eqHist[i] = new HistogramEqualiser(cHist[i], img.getHeight() * img.getWidth());
        }

        for (int i = 0; i < 3; i++) {
            nHist[i] = new NormalHistogram(0, 255, 1);
        }
        for (int u = 0; u < img.getWidth(); u++) {
            for (int v = 0; v < img.getHeight(); v++) {
                // get current pixel values
                int[] rgbValues = grabRGBValues(img, u, v);

                for (int i = 0; i < 3; i++) {
                    // Get new pixel value from equalised histogram
                    rgbValues[i] = eqHist[i].getIndex(rgbValues[i]);
                    // add to histogram to show equalised
                    nHist[i].addValue(rgbValues[i]);
                }

                // clamping RGB before putting into picture
                BeautifyUtils.clampRGB(rgbValues);
                outputImage.setRGB(u, v, ((rgbValues[0] & 0xff) << 16) | ((rgbValues[1] & 0xff) << 8) | (rgbValues[2] & 0xff));
            }
        }
        new ShowHistogram(nHist[0].getArray(), "Equalised red");
        new ShowHistogram(nHist[1].getArray(), "Equalised green");
        new ShowHistogram(nHist[2].getArray(), "Equalised blue");
        return outputImage;
    }
}

