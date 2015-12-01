package uk.ac.aber.beautify.filters.histogram;

/**
 * Created by Dimitar on 1/12/2015.
 */
public interface Histogram {
    int R = 0, L = 0;
    int G = 1, A = 1;
    int B = 2;

    void addValue(int value);
    int getMinRange();
    int getMaxRange();
    int getSteps();
    int[] getArray();
    int getIndex(int i);
    int getArraySize();
    String toString();
    String printArray();
}
