package uk.ac.aber.beautify.filters.histogram.equalisation;

import uk.ac.aber.beautify.filters.histogram.Histogram;
import uk.ac.aber.beautify.filters.histogram.cumulative.CumulativeHistogram;
import uk.ac.aber.beautify.filters.histogram.normal.NormalHistogram;

/**
 * Created by Dimitar on 30/11/2015.
 */
public class HistogramEqualiser extends NormalHistogram implements Histogram{
    private Histogram h;

    /**
     *
     * @param h Histogram from which the equalised values will be calculated
     * @param pixelCount, Equal to image Height * image Width
     */
    public HistogramEqualiser(Histogram h, int pixelCount) {
        this.h = h;
        this.array = new int[h.getArraySize()];
        calculateEqualisation(pixelCount);
    }

    private void calculateEqualisation(int pixelCount) {
        int[] histArray = h.getArray();
        int maxRange = h.getMaxRange();
        for (int i = 0; i < h.getArraySize(); i++) {
            // array[i] = contains the new position for histArray[i]
            array[i] = histArray[i] * (maxRange) / pixelCount;
        }
    }
    @Override
    public int getMinRange(){
        return h.getMinRange();
    }
    @Override
    public int getMaxRange(){
        return h.getMaxRange();
    }
}
