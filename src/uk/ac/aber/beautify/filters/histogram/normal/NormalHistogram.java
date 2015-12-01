package uk.ac.aber.beautify.filters.histogram.normal;

import uk.ac.aber.beautify.filters.histogram.Histogram;

public class NormalHistogram implements Histogram {

    private int minRange;
    private int maxRange;
    private int step;

    // only variable necessary in the inheritance
    protected int[] array;

    public NormalHistogram(int minRange, int maxRange, int step) {
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.step = step;
        createArray(step);

    }

    public NormalHistogram() {
    }

    @Override
    public void addValue(int value) {
        array[value]++;
    }

    @Override
    public int getMinRange() {
        return minRange;
    }

    @Override
    public int getMaxRange() {
        return maxRange;
    }

    @Override
    public int getSteps() {
        return step;
    }

    /**
     * @WARNING: Will delete any data currently in the array
     * A new array will be created.
     * @param steps
     */
    public void setSteps(int steps) {
        this.step = steps;
    }

    private void createArray(int step) {
        /*
		 * array = populate with steps -> if == array[i] -> holder[i] ++;
		 */

		/*
		 * 0 - 255 -> 256, 1 step = 256 places => 0, 1, 2, 3 .., 2 steps 256/2 =
		 * 128 => 0, 2, 4, 6 .., 3 steps = 256/3 = 85.3 => 0, 3, 6, .. .., 0.5
		 * steps = 256/0.5 => 0, 0.5, 1, 1.5, 2, 2.5, .., 0.3 steps = 256/0.3 =>
		 */
        this.step = step;
        int arraySize = (int) Math.ceil(((maxRange + 1) / step));
        // maxRange +1 to account for start value;
        // +1 at the end to round up
        // System.out.println("DEBUG: " + toString() + " current array size: " + arraySize);
        this.array = new int[arraySize];
    }

    @Override
    public int[] getArray() {
        return array;
    }

    @Override
    public int getIndex(int i) {
        return array[i];
    }

    public void setArray(int[] array) {
        this.array = array;
    }

    @Override
    public int getArraySize() {
        return array.length;
    }

    @Override
    public String toString() {
        return "Current minRange: " + minRange + " | maxRange: " + maxRange
                + " | steps: " + step;
    }

    @Override
    public String printArray() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            str.append("member ").append(i).append(": ").append(array[i]).append('\n');
        }
        return str.toString();
    }

}
