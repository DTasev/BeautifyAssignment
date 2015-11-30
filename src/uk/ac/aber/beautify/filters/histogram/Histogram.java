package uk.ac.aber.beautify.filters.histogram;

public class Histogram {
	public static final int RGB = 1;
	public static final int HSV = 2;
	public static final int XYZ = 3;
	public static final int LAB = 4;

	private int minRange;
	private int maxRange;
	private int step;
	private int[] array;

	public Histogram(int type, int step){

		switch(type){
			case RGB:
				// min 0, max 255, ints
				this.minRange = 0;
				this.maxRange = 255;
				this.step = step;
				createArray(step);
				break;
			case HSV:
				// min 0, max 1, doubles
				System.err.println("Doesn't exist yet");
				break;
			case XYZ:
				this.minRange = 0;
				this.maxRange = 100;
				this.step = step;
				createArray(step);
				break;
			case LAB:
				//min -128, max 128, doubles
				this.minRange = -128;
				this.maxRange = 128;
				this.step = step;
				createArray(step);
				break;
		}
	}
	public Histogram(int minRange, int maxRange, int step) {
		this.minRange = minRange;
		this.maxRange = maxRange;
		this.step = step;
		createArray(step);
		/*
		 * array = populate with steps -> if == array[i] -> holder[i] ++;
		 */

		/*
		 * 0 - 255 -> 256, 1 step = 256 places => 0, 1, 2, 3 .., 2 steps 256/2 =
		 * 128 => 0, 2, 4, 6 .., 3 steps = 256/3 = 85.3 => 0, 3, 6, .. .., 0.5
		 * steps = 256/0.5 => 0, 0.5, 1, 1.5, 2, 2.5, .., 0.3 steps = 256/0.3 =>
		 */
	}

	public void addValue(int value) {
		array[value]++;
	}

	public double getMinRange() {
		return minRange;
	}

	public double getMaxRange() {
		return maxRange;
	}

	public double getSteps() {
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
	private void createArray(int step){
		this.step = step;
		int arraySize = (int) Math.ceil(((maxRange + 1) / step));
		// maxRange +1 to account for start value;
		// +1 at the end to round up
		// System.out.println("DEBUG: " + toString() + " current array size: " + arraySize);
		this.array = new int[arraySize];
	}
	public int[] getArray() {
		return array;
	}

	public void setArray(int[] array) {
		this.array = array;
	}

	public int getArraySize() {
		return array.length;
	}

	public String toString() {
		return "Current minRange: " + minRange + " | maxRange: " + maxRange
				+ " | steps: " + step;
	}
	public String printArray(){
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			str.append("member ").append(i).append(": ").append(array[i]).append('\n');
		}
		return str.toString();
	}

}
