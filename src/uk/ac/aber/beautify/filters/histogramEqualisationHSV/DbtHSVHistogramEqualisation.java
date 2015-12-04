package uk.ac.aber.beautify.filters.histogramEqualisationHSV;

import uk.ac.aber.beautify.filters.Filter;
import uk.ac.aber.beautify.filters.histogram.Histogram;
import uk.ac.aber.beautify.filters.histogram.ShowHistogram;
import uk.ac.aber.beautify.filters.histogram.cumulative.CumulativeHistogram;
import uk.ac.aber.beautify.filters.histogram.equalisation.HistogramEqualiser;
import uk.ac.aber.beautify.filters.histogram.normal.NormalHistogram;
import uk.ac.aber.beautify.utils.BeautifyUtils;

import java.awt.image.BufferedImage;

/**
 * Created by Dimitar on 26/11/2015.
 */
@SuppressWarnings("ALL")
public class DbtHSVHistogramEqualisation extends Filter {
    public static final int R = 0, L = 0;
    public static final int G = 1, A = 1;
    public static final int B = 2;

    public DbtHSVHistogramEqualisation() {
        this.setName("HSV Histogram Equalisation Filter");
    }

    public BufferedImage filter(BufferedImage img) {
        BufferedImage outputImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        // initialise histograms
        Histogram[] nHist = new NormalHistogram[3];
        for (int i = 0; i < 3; i++) {
            nHist[i] = new NormalHistogram(0, 100, 1);
        }
        for (int u = 0; u < img.getWidth(); u++) {
            for (int v = 0; v < img.getHeight(); v++) {
                // Grab the converted LAB Values
                float[] hsvValues = grabHSVValues(img, u, v);
                // creating histogram for only L channel
                for (int i = 0; i < 3; i++) {
                    nHist[i].addValue((int) (hsvValues[i] * 100));
                }
            }
        }
        String[] channels = {"H Channel", "S Channel", "V Channel"};
        // show histogram
        for (int i = 0; i < 3; i++) {
            new ShowHistogram(nHist[i].getArray(), channels[i]);
        }


        // Calculate Cumulative Histogram
        Histogram[] cHist = new CumulativeHistogram[3];
        for (int i = 0; i < 3; i++) {
            cHist[i] = new CumulativeHistogram(nHist[i]);
        }

        // show cumulative histogram
        for (int i = 0; i < 3; i++) {
            new ShowHistogram(cHist[i].getArray(), channels[i]);
        }


        // Equalisation on cumulative histogram
        Histogram[] eqHist = new HistogramEqualiser[3];
        for (int i = 0; i < 3; i++) {
            eqHist[i] = new HistogramEqualiser(nHist[i], img.getHeight() * img.getWidth());
        }

        // New histogram to hold the values after they have been equalised and swapped
        Histogram eqNormHist = new NormalHistogram(0, 101, 1);

        for (int u = 0; u < img.getWidth(); u++) {
            for (int v = 0; v < img.getHeight(); v++) {
                float[] hsvValues = grabHSVValues(img, u, v);

                // Get new pixel value from equalised histogram,
                // it holds what the new value for the pixel should be,
                // and set the current pixel's value to it
                for(int i = 1; i < 2; i++) // just for S and V
                {
                    hsvValues[i] = eqHist[i].getIndex((int) (hsvValues[L]*100))/100f;
                }

                // creating new histogram of equalised luminosity
                eqNormHist.addValue((int) (hsvValues[L]*100));
                int[] rgbValues = BeautifyUtils.HSVtoRGB(hsvValues);

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

