package uk.ac.aber.beautify.filters.histogram.cumulative;

import uk.ac.aber.beautify.filters.histogram.Histogram;

/**
 * Created by Dimitar on 29/11/2015.
 */
public class CumulativeHistogram {
    private Histogram h;
    private int[] array;

    public CumulativeHistogram(Histogram h){
        this.h = h;
        calculateCumulativeHist();
    }



    private void calculateCumulativeHist() {
        this.array = h.getArray();
        for(int i = 1; i < array.length; i++){
            array[i] += array[i-1];
        }
    }

    public int[] getArray() {
        return array;
    }
}
