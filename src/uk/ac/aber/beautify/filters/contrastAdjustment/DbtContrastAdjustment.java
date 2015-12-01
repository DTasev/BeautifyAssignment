package uk.ac.aber.beautify.filters.contrastAdjustment;

import uk.ac.aber.beautify.filters.Filter;
import uk.ac.aber.beautify.filters.histogram.normal.NormalHistogram;
import uk.ac.aber.beautify.filters.histogram.cumulative.CumulativeHistogram;

import java.awt.image.BufferedImage;

/**
 * Created by Dimitar on 26/11/2015.
 */
public class DbtContrastAdjustment extends Filter {
    // create cumulative histogram, from histogram
    // q = qhigh = qlow = 0.1
    // p(low) = m*n*q(low)
    // p(high) = m*q*(1-q(high))
    // if p <= p(low) set to p(min)
    // else if p > p(low) && p < p(high) set to p(min) + (p-p(low)*(p(max)-p(min)/p(high)-p(low))
    // else if p >= p(high) set to p(max)
    // remap pixels
    /*
    do histogram/cumulative histogram on channel that will be changed
    convert to hsv
    auto contrast on saturation *might work*
        OR
    auto contrast on value

     */
    public static final int R = 0;
    public static final int G = 1;
    public static final int B = 2;

    public DbtContrastAdjustment() {
        this.setName("Contrast Adjustment Filter");
    }

    public BufferedImage filter(BufferedImage img) {
        BufferedImage outputImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        NormalHistogram r = new NormalHistogram(0, 255, 1);
        NormalHistogram g = new NormalHistogram(0, 255, 1);
        NormalHistogram b = new NormalHistogram(0, 255, 1);
        for (int u = 0; u < img.getWidth(); u++) {
            for (int v = 0; v < img.getHeight(); v++) {
                // Grab the converted HSV Values
                int[] rgbValues = grabRGBValues(img, u, v);
                r.addValue(rgbValues[0]);
                g.addValue(rgbValues[1]);
                b.addValue(rgbValues[2]);

            }
        }

        CumulativeHistogram cmR = new CumulativeHistogram(r);
        CumulativeHistogram cmG = new CumulativeHistogram(g);
        CumulativeHistogram cmB = new CumulativeHistogram(b);

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
                int[] rgbValues = grabRGBValues(img, u, v);
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

