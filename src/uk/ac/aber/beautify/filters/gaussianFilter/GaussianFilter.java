package uk.ac.aber.beautify.filters.gaussianFilter;

import uk.ac.aber.beautify.filters.Filter;

import java.awt.image.*;

/**
 * Created by Dimitar on 1/12/2015.
 */
public class GaussianFilter {
    WritableRaster outputRaster;
    Raster inputRaster;
    BufferedImage img;

    double[][] K = {{0.111, 0.111, 0.111},
            {0.111, 0.111, 0.111},
            {0.111, 0.111, 0.111}};

    /*double[][] K = {{-1.0, 0.0, 1.0},
            {-1.0, 0.0, 1.0},
            {-1.0, 0.0, 1.0}};*/
    /*double[][] K = {{1.0, 1.0, 1.0}, {0.0,0.0,0.0}, {-1.0, -1.0, -1.0}};*/
    int filterWidth = (K.length - 1) / 2;

    public GaussianFilter(BufferedImage img) {
        outputRaster = img.getRaster();
        inputRaster = img.getData();
        this.img = img;
        run();
    }
    public GaussianFilter(BufferedImage img, WritableRaster inputRaster){
        outputRaster = img.getRaster();
        this.inputRaster = inputRaster;
        this.img = img;
    }

    private void operation(){
        for (int u = 0; u < img.getWidth(); u++) {
            for (int v = 0; v < img.getHeight(); v++) {
                double[] inputPixels = new double[3];
                double[] sum = {0.0, 0.0, 0.0};
                for (int i = -filterWidth; i <= filterWidth; i++) {
                    for (int j = -filterWidth; j <= filterWidth; j++) {
                        // Image coordinates
                        inputRaster.getPixel(Math.max(Math.min(u + i, inputRaster.getWidth() - 1), 0),
                                Math.max(Math.min(v + j, inputRaster.getHeight() - 1), 0), inputPixels);
                        int[] rgbValues = Filter.grabRGBValues(img, u, v);

                        for (int k = 0; k < 3; k++) {
                            // Filter coordinates
                            sum[k] += inputPixels[k] * K[i + filterWidth][j + filterWidth];
                        }
                    }
                }

                double[] outputPixels = new double[3];
                for (int i = 0; i < 3; i++) {
                    outputPixels[i] = Math.max(Math.min(sum[i], 255), 0);// / (K.length * K.length);
                }
                outputRaster.setPixel(u, v, outputPixels);
            }
        }
    }
    public WritableRaster runRaster(){
        operation();
        return outputRaster;
    }


    public BufferedImage run() {
        img.setData(outputRaster);
        return img;
    }
}
