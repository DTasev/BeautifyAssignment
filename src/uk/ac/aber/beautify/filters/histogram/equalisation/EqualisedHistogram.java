package uk.ac.aber.beautify.filters.histogram.equalisation;

import uk.ac.aber.beautify.filters.histogram.Histogram;

import java.awt.image.BufferedImage;

/**
 * Created by Dimitar on 30/11/2015.
 */
public class EqualisedHistogram {
    private Histogram h;
    private int[] array;
    private int pixelCount;

    public int[] getArray() {
        return array;
    }
    public int getIndex(int i){
        return array[i];
    }

    public EqualisedHistogram(Histogram h, int pixelCount) {

        this.h = h;
        this.pixelCount = pixelCount;
        this.array = new int[h.getArraySize()];
        calculateEqualisation();
    }

    private void calculateEqualisation() {
        int[] histArray = h.getArray();
        for (int i = 0; i < h.getArraySize(); i++) {
            array[i] = histArray[i] * (255)/pixelCount;
        }
        printArray();

    }
    private void printArray(){
        for (int i = 0; i < 255; i++) {
            System.out.println(i + " -> " + array[i]);
        }
    }

}
