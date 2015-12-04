/**
 * The Beautify class is the main entry point into the Beautify system.  You can
 * leave BeautifyGUI and BeautifyKernel alone (feel free to have a peak at them
 * but please don't fiddle!).
 * <p>
 * You will need to make sure that you over-ride the line that sets the filterSet
 * within the BeautifyKernel.  At present it uses the DefaultBeautifyFilters()
 * class but you should replace this with your own implementation of the
 * BeautifyFilters interface.
 */

import uk.ac.aber.beautify.core.BeautifyKernel;
import uk.ac.aber.beautify.filters.brightness.DbtBrightnessFilterSelect;
import uk.ac.aber.beautify.filters.contrastAdjustment.DbtContrastAdjSelect;
import uk.ac.aber.beautify.filters.grayscale.DbtGrayscaleFilterSelect;
import uk.ac.aber.beautify.filters.histogram.kateEqualisation.KateSelect;
import uk.ac.aber.beautify.filters.histogramEqualisation.DbtHistEqSelect;
import uk.ac.aber.beautify.filters.histogramEqualisationLAB.DbtLABHistEqSelect;
import uk.ac.aber.beautify.filters.invert.DbtInvertFilterSelect;
import uk.ac.aber.beautify.filters.labEqualiseAndRGBContrastAdjust.DbtLABeqAndCASelect;
import uk.ac.aber.beautify.filters.labEqualiseAndRGBContrastAdjust.DbtLABeqAndCASelectImage;
import uk.ac.aber.beautify.gui.BeautifyGUI;

import java.util.Scanner;

public class Beautify {

    public static void main(String[] args) {

        BeautifyGUI gui = new BeautifyGUI();
        BeautifyKernel beautify = new BeautifyKernel(gui);

        Scanner in = new Scanner(System.in);

        String input = new String();

        int count = 0; // print menu every 5 times
        beautify.setFilterSet(new DbtLABeqAndCASelect());
        while (input != "x") {
            if (count % 5 == 0)
                printMenu();
            count++;
            input = in.next() + in.nextLine();
            input = input.toLowerCase();

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
                case "contr":
                    System.out.println("Contrast Adjustment Filter Selected");
                    beautify.setFilterSet((new DbtContrastAdjSelect()));
                    break;
                case "heq":
                    System.out.println("Histogram Equalisation");
                    beautify.setFilterSet(new DbtHistEqSelect());
                    break;
                case "labheq":
                    System.out.println("Lab Histogram Equalisation");
                    beautify.setFilterSet(new DbtLABHistEqSelect());
                    break;
                case "kate":
                    System.out.println("Kate Histogram Equalisation");
                    beautify.setFilterSet(new KateSelect());
                    break;
                case "labcontr":
                    System.out.println("Lab Equalisation and RGB Contrast Adjustment");
                    beautify.setFilterSet(new DbtLABeqAndCASelect());
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }

    private static void printMenu() {
        System.out.println("Pick a filter:\n"
                + "ibr -> Increase Brightness\n"
                + "inv -> Invert colours\n"
                + "gray -> Grayscale\n"
                + "contr -> contrast adjustment\n"
                + "heq -> histogram equalisation on RGB\n"
                + "labheq -> histogram equalisation on L channel in Lab\n"
                + "kate -> kate equalisation\n"
                + "labcontr -> lab equalise and rgb contrast adjustment");
    }
}
