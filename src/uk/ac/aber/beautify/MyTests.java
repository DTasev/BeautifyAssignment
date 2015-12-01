package uk.ac.aber.beautify;

import uk.ac.aber.beautify.core.BeautifyFilters;
import uk.ac.aber.beautify.utils.BeautifyUtils;

import java.awt.*;

/**
 * Created by Dimitar on 30/11/2015.
 */
public class MyTests {
    public static void main(String[] args){
        int[] rgbVals = {123, 45, 78};
        float[] utilHsv = BeautifyUtils.RGBtoHSV(rgbVals);
        double[] myHsv = BeautifyUtils.RGBtoHSVDouble(rgbVals);
        float[] libHsv = new float[3];
                Color.RGBtoHSB(rgbVals[0], rgbVals[1], rgbVals[2], libHsv);
        System.out.println("myhsv-> " + myHsv[0] + " " + myHsv[1] + " " + myHsv[2]
                + "\n utilhsv-> " + utilHsv[0] + " " + utilHsv[1] + " " + utilHsv[2]
        +"\n libhsv-> " + libHsv[0] + " " + libHsv[1] + " " + libHsv[2]);
    }
}
