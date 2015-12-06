package uk.ac.aber.beautify.filters.kateGaussian;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

/**
 * Created by Dimitar on 3/12/2015.
 */
public class kateGaussianFilter {

    public kateGaussianFilter() {

    }

    public WritableRaster gaussianFilter(BufferedImage image) {
        BufferedImage filteredImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        WritableRaster outputRaster = filteredImage.getRaster();
        Raster inputRaster = image.getData();

        double[][] K = {{0.111, 0.111, 0.111},
                {0.111, 0.111, 0.111},
                {0.111, 0.111, 0.111}};


        int filterWidth = (K.length -1) /2;

        for(int u = 0; u < inputRaster.getWidth(); u++) {
            for (int v = 0; v < inputRaster.getHeight(); v++){
                double[] inputPixels = new double[3];

                double[] sum = {0.0, 0.0, 0.0};

                for (int i = -filterWidth; i <= filterWidth; i++){
                    for (int j = -filterWidth; j <= filterWidth; j++) {

                        inputRaster.getPixel(Math.max(Math.min(u + i,  inputRaster.getWidth() - 1),  0),
                                Math.max(Math.min(v + j, inputRaster.getHeight()- 1),  0), inputPixels);

                        sum[0] += inputPixels[0] * K[i + filterWidth] [j + filterWidth];
                        sum[1] += inputPixels[1] * K[i + filterWidth] [j + filterWidth];
                        sum[2] += inputPixels[2] * K[i + filterWidth] [j + filterWidth];
                    }
                }
                double[] outputPixels = new double[3];
                outputPixels[0] = sum[0];
                outputPixels[1] = sum[1];
                outputPixels[2] = sum[2];

                outputRaster.setPixel(u, v, outputPixels);
            }
        }
        return outputRaster;
    }
}