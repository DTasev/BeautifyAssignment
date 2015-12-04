package uk.ac.aber.beautify.filters.labEqualiseAndRGBContrastAdjust;

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
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

/**
 * Created by Dimitar on 26/11/2015.
 */
@SuppressWarnings("ALL")
public class DbtLABeqAndContrastAdjustment extends Filter {
    public static final int R = 0, L = 0;
    public static final int G = 1, A = 1;
    public static final int B = 2;

    BufferedImage img;
    Raster inputRaster;

    public DbtLABeqAndContrastAdjustment() {
        this.setName("Luminosity Histogram Equalisation And RGB Contrast Adjustment Filter");
    }

    public BufferedImage filter(BufferedImage img) {
        this.img = img;
        BufferedImage outputImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        WritableRaster labRaster;
        WritableRaster contrAdjRaster;
        WritableRaster outputRaster = img.getRaster();
        inputRaster = img.getData();

        /*
        create 2 pictures, store them, compare every pixel, if no difference cry, if difference no cry
         */
        outputRaster = new AverageFilter(outputImage, outputRaster).runRaster();
        outputRaster = contrastAdjustment(outputRaster);
        outputRaster = labEqualisation(outputRaster);
        outputImage.setData(outputRaster);
        // creating cumulative histogram for equalised luminosity
        //Histogram cumLHist = new CumulativeHistogram((NormalHistogram)eqNormHist);
        //new ShowHistogram(cumLHist.getArray(), "Equalised Luminosity");

        return outputImage;
    }

    private WritableRaster contrastAdjustment(WritableRaster outputRaster) {

        Histogram r = new NormalHistogram(0, 255, 1);
        Histogram g = new NormalHistogram(0, 255, 1);
        Histogram b = new NormalHistogram(0, 255, 1);

        WritableRaster outRaster = outputRaster;

        for (int u = 0; u < img.getWidth(); u++){
            for (int v = 0; v < img.getHeight(); v++) {
                // Grab the converted HSV Values
                int[] rgbValues = grabRGBValues(outRaster, u, v);
                r.addValue(rgbValues[0]);
                g.addValue(rgbValues[1]);
                b.addValue(rgbValues[2]);
            }
        }

        new ShowHistogram(r.getArray(), "Normal Histogram");
        Histogram cmR = new CumulativeHistogram(r);
        Histogram cmG = new CumulativeHistogram(g);
        Histogram cmB = new CumulativeHistogram(b);

        new ShowHistogram(cmR.getArray(), "Cumulative Histogram");

        // set q(LOW) and q(HIGH) ranges
        float qlow = 0.05f;
        float qhigh = 0.05f;

        // containers for p(LOW) and p(HIGH) for each channel
        int[] plow = new int[3];
        int[] phigh = new int[3];

        // this is the calculation used to find p(LOW)
        int mlow = (int) (img.getHeight() * img.getWidth() * qlow);
        // this is the calculation used to find p(HIGH)
        int mhigh = (int) (img.getHeight() * img.getWidth() * (1 - qhigh));

        // Get the red channel cumulative histogram
        int[] rch = cmR.getArray();
        // Find p(LOW) and p(HIGH) for Red channel
        plow[R] = getP(rch, mlow, true);

        phigh[R] = getP(rch, mhigh, false);

        // Get the green channel cumulative histogram
        int[] gch = cmG.getArray();
        // Find p(LOW) and p(HIGH) for Green channel
        plow[G] = getP(gch, mlow, true);

        phigh[G] = getP(gch, mhigh, false);

        // Get the blue channel cumulative histogram
        int[] bch = cmB.getArray();
        // Find p(LOW) and p(HIGH) for Blue channel
        plow[B] = getP(bch, mlow, true);

        phigh[B] = getP(bch, mhigh, false);

        // p(MIN) and p(HIGH) for RGB are 0 and 255 respectively
        int pmin = 0;
        int pmax = 255;

        for (int u = 0; u < img.getWidth(); u++) {
            for (int v = 0; v < img.getHeight(); v++) {
                int[] rgbValues = grabRGBValues(outRaster, u, v);
                // red pixel
                for (int i = 0; i < 3; i++) {
                    if (rgbValues[i] <= plow[i]) {
                        // if lower than p(LOW) for the channel, set to p(MIN)
                        rgbValues[i] = pmin;
                    } else if (rgbValues[i] >= phigh[i]) {
                        // if higher than p(HIGH) for the channel, set to p(HIGH)
                        rgbValues[i] = pmax;
                    } else {
                        // else http://i.imgur.com/Ja14kzW.png / http://i.imgur.com/uoEvldp.png
                        rgbValues[i] = pmin + ((rgbValues[i] - plow[i]) * (pmax - pmin) / (phigh[i] - plow[i]));
                    }
                }

                BeautifyUtils.clampRGB(rgbValues);
                outRaster.setPixel(u, v, rgbValues);
            }
        }
        return outRaster;
    }

    private WritableRaster labEqualisation(WritableRaster outputRaster) {
        Histogram normalHist = new NormalHistogram(0, 100, 1);
        WritableRaster outRaster = outputRaster;

        for (int u = 0; u < img.getWidth(); u++) {
            for (int v = 0; v < img.getHeight(); v++) {
                // Grab the converted LAB Values
                double[] labValues = grabLABValues(outRaster, u, v);
                // creating histogram for only L channel
                normalHist.addValue((int) labValues[L]);
            }
        }
        Histogram cumulativeHist = new CumulativeHistogram(normalHist);
        Histogram equalisedHist = new HistogramEqualiser(cumulativeHist, img.getHeight() * img.getWidth());

        for (int u = 0; u < img.getWidth(); u++) {
            for (int v = 0; v < img.getHeight(); v++) {
                double[] labValues = grabLABValues(outRaster, u, v);

                // Get new pixel value from equalised histogram,
                // it holds what the new value for the pixel should be,
                // and set the current pixel's value to it
                labValues[L] = equalisedHist.getIndex((int) labValues[L]);

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

