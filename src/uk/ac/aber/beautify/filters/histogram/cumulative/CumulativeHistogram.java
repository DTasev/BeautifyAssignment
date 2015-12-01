package uk.ac.aber.beautify.filters.histogram.cumulative;

import uk.ac.aber.beautify.filters.histogram.Histogram;
import uk.ac.aber.beautify.filters.histogram.normal.NormalHistogram;

/**
 * Created by Dimitar on 29/11/2015.
 */
public class CumulativeHistogram extends NormalHistogram implements Histogram{
    private Histogram h;

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
    @Override
    public int getMinRange(){
        return h.getMinRange();
    }
    @Override
    public int getMaxRange(){
        return h.getMaxRange();
    }
}
