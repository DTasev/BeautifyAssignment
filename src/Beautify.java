/**
 * The Beautify class is the main entry point into the Beautify system.  You can
 * leave BeautifyGUI and BeautifyKernel alone (feel free to have a peak at them 
 * but please don't fiddle!).  
 * 
 * You will need to make sure that you over-ride the line that sets the filterSet
 * within the BeautifyKernel.  At present it uses the DefaultBeautifyFilters()
 * class but you should replace this with your own implementation of the 
 * BeautifyFilters interface.
 * 
 */
import java.util.Scanner;

import uk.ac.aber.beautify.core.BeautifyKernel;
import uk.ac.aber.beautify.filters.DbtEnchancementFilterSelect;
import uk.ac.aber.beautify.filters.brightness.DbtBrightnessFilterSelect;
import uk.ac.aber.beautify.filters.grayscale.DbtGrayscaleFilterSelect;
import uk.ac.aber.beautify.filters.histogram.filter.DbtHistogramSelect;
import uk.ac.aber.beautify.filters.invert.DbtInvertFilterSelect;
import uk.ac.aber.beautify.gui.*;

public class Beautify {

	public static void main(String[] args) {

		BeautifyGUI gui = new BeautifyGUI();
		BeautifyKernel beautify = new BeautifyKernel(gui);

		Scanner in = new Scanner(System.in);
		

		String input = new String();
		

		while (input != "x") {
			System.out.println("Pick a filter:\n" 
					+ "ibr -> Increase Brightness\n"
					+ "inv -> Invert colours\n"
					+ "gray -> Grayscale\n"
					+ "hist -> histogram");
			input = in.next() + in.nextLine();
			switch (input) {
			case "ibr":
				beautify.setFilterSet(new DbtBrightnessFilterSelect());
				System.out.println("Brightness Filter Selected");
				break;
			case "inv":
				System.out.println("Invert Colour Filter Selected");
				beautify.setFilterSet(new DbtInvertFilterSelect());
				break;
			case "gray":
				System.out.println("Grayscale Filter Selected");
				beautify.setFilterSet(new DbtGrayscaleFilterSelect());
				break;
			case "hist":
				System.out.println("Creating histogram");
				beautify.setFilterSet(new DbtHistogramSelect());
				break;
			default:
				System.out.println("Creating histogram");
				beautify.setFilterSet(new DbtHistogramSelect());
				break;
			}
		}
	}
}
