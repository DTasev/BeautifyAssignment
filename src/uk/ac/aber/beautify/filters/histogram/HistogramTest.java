package uk.ac.aber.beautify.filters.histogram;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class HistogramTest {
	Histogram h;
	int minRange = 0;
	int maxRange = 255;
	double step = 1;
	int arraySize = (int) Math.ceil(((maxRange + 1) / step));
	
	double[] array;
	
	@Before
	public void setUp(){
		h = new Histogram(minRange, maxRange, step);
		array = new double[arraySize];
	}
	
	@Test
	public void testArraySize(){
		for(double i = 1.0; i > 0; i = i - 0.01){
			//double x = Math.round(i * 1000d) / 1000d;
			double x = i;
			h = new Histogram(minRange, maxRange, x);
			double size = Math.ceil(((maxRange + 1) / x));
			assertEquals(size, h.getArraySize(), 0.000);
		}
	}
	@Test
	public void testAddValue(){
		Random n = new Random();
		
		for (int i = 0; i < 20000000; i++) {
			//int a = n.nextInt(((maxRange - minRange) + 1) + minRange);
			int a = n.nextInt(256);
			h.addValue(a);
		}
		fail(h.printHolder());
	}
	
	/*
	 * tests the values of the array, they should be minRange -> maxRange
	 */
	public void testeste(){
		h = new Histogram(minRange, maxRange, 1);
		fail(h.printArray());
	}

}
