package uk.ac.aber.beautify.filters.histogram.filter;

import uk.ac.aber.beautify.filters.Filter;
import uk.ac.aber.beautify.filters.histogram.Histogram;
import uk.ac.aber.beautify.filters.histogram.ShowHistogram;
import uk.ac.aber.beautify.filters.histogram.cumulative.CumulativeHistogram;

import java.awt.image.BufferedImage;

/**
 * Created by Dimitar on 29/11/2015.
 */
public class DbtCumulative extends Filter {

    /**
     * Need to provide a name for this filter so that it appears in the GUI!
     */
    public DbtCumulative() {
        this.setName("Histogram");
    }

    /**
     * This is the basic image enhancement algorithm.  You will need to provide an improvement over
     * this method - that is, create a slightly more advanced algorithm that takes aspects such as
     * image statistics into account.  When providing results of your implementation you will need
     * to compare your results against the results of running this basic filter.
     */
    public BufferedImage filter(BufferedImage img) {
        // We will store the processed image in this variable and return it at the end of the function
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


                // Set the pixel values in the output image to be the brightness enhanced values
                //outputImage.setRGB(u, v, ((rgbValues[0] & 0xff) << 16) | ((rgbValues[1] & 0xff) << 8) | (rgbValues[2] & 0xff));
            }
        }
        ShowHistogram cumulativeHistogram = new ShowHistogram(new CumulativeHistogram(r).getArray(), "Red Cumulative Histogram");
        ShowHistogram cumulativeHistogram2 = new ShowHistogram(new CumulativeHistogram(g).getArray(), "Green Cumulative Histogram");
        ShowHistogram cumulativeHistogram3 = new ShowHistogram(new CumulativeHistogram(b).getArray(), "Blue Cumulative Histogram");
        return outputImage;        // Spit back our enhanced image!
    }
}
