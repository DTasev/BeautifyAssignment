package uk.ac.aber.beautify.filters.histogram;

public class Histogram {
	private int minRange;
	private int maxRange;
	private int step;
	private int[] array;

	public Histogram(int minRange, int maxRange, int step) {
		this.minRange = minRange;
		this.maxRange = maxRange;
		this.step = step;
		int arraySize = (int) Math.ceil(((maxRange + 1) / step));
		// maxRange +1 to account for start value; 
		//+1 at the end to round up
		//System.out.println(toString() + " current array size: " + arraySize);
		this.array = new int[arraySize];
		populateArray();
		/*
		 * array = populate with steps -> if == array[i] -> holder[i] ++;
		 */

		/*
		 * 0 - 255 -> 256, 1 step = 256 places => 0, 1, 2, 3 .., 2 steps 256/2 =
		 * 128 => 0, 2, 4, 6 .., 3 steps = 256/3 = 85.3 => 0, 3, 6, .. .., 0.5
		 * steps = 256/0.5 => 0, 0.5, 1, 1.5, 2, 2.5, .., 0.3 steps = 256/0.3 =>
		 */
	}

	private void populateArray() {
		int currentVal = minRange;
		for (int i = 0; i < array.length; i++) {
			array[i] = currentVal;
		}
		
	}

	public void addValue(int value) {
		array[value]++;
	}

	public int getMinRange() {
		return minRange;
	}

	public void setMinRange(int minRange) {
		this.minRange = minRange;
	}

	public int getMaxRange() {
		return maxRange;
	}

	public void setMaxRange(int maxRange) {
		this.maxRange = maxRange;
	}

	public int getSteps() {
		return step;
	}

	public void setSteps(int steps) {
		this.step = steps;
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
