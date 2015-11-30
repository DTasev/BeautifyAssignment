package uk.ac.aber.beautify.filters.histogramEqualisation;

import uk.ac.aber.beautify.filters.Filter;
import uk.ac.aber.beautify.filters.histogram.Histogram;
import uk.ac.aber.beautify.filters.histogram.ShowHistogram;
import uk.ac.aber.beautify.filters.histogram.cumulative.CumulativeHistogram;
import uk.ac.aber.beautify.filters.histogram.equalisation.EqualisedHistogram;
import uk.ac.aber.beautify.filters.histogram.filter.DbtCumulative;

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

            }
        }

        // show normal histograms
        new ShowHistogram(r.getArray(), "Red Channel");
        new ShowHistogram(g.getArray(), "Green Channel");
        new ShowHistogram(b.getArray(), "BlueChannel");
        // H EQ red
        EqualisedHistogram eqR = new EqualisedHistogram(r, img.getHeight()*img.getWidth());
        // H EQ green
        //EqualisedHistogram eqG = new EqualisedHistogram(g, img.getHeight()*img.getWidth());
        // H EQ blue
        //EqualisedHistogram eqB = new EqualisedHistogram(b, img.getHeight()*img.getWidth());
        // Show histograms
        new ShowHistogram(eqR.getArray(), "Equalised Red");
        //new ShowHistogram(eqG.getArray(), "Equalised Green");
        //new ShowHistogram(eqB.getArray(), "Equalised Blue");


        for (int u = 0; u < img.getWidth(); u++) {
            for (int v = 0; v < img.getHeight(); v++) {
                int[] rgbValues = grabRGBValues(img, u, v);
                // Get red from equalised histogram
                rgbValues[R] = eqR.getIndex(rgbValues[R]);
                // Get green from equalised histogram
                //rgbValues[G] = eqG.getIndex(rgbValues[G]);
                // Get blue from equalised histogram
                //rgbValues[B] = eqB.getIndex(rgbValues[B]);

                outputImage.setRGB(u, v, ((rgbValues[0] & 0xff) << 16) | ((rgbValues[1] & 0xff) << 8) | (rgbValues[2] & 0xff));
            }
        }
        return outputImage;
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

