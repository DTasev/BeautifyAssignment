package uk.ac.aber.beautify.filters.histogram.kateEqualisation;

        import uk.ac.aber.beautify.filters.Filter;
        import uk.ac.aber.beautify.filters.histogram.ShowHistogram;
        import uk.ac.aber.beautify.utils.BeautifyUtils;

        import java.awt.image.BufferedImage;

/**
 * Created by Dimitar on 26/11/2015.
 */

public class KateEqualise extends Filter {
    public static final int R = 0;
    public static final int G = 1;
    public static final int B = 2;

    public KateEqualise() {
        this.setName("Kate Equalisation Filter");
    }

    public BufferedImage filter(BufferedImage image) {
        BufferedImage outputImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        int rawValue = 0;
        int maxVal = 256;
        int[] histR = new int[256];
        int[] histG = new int[256];
        int[] histB = new int[256];
        int[] cumHistR = new int[256];
        int[] cumHistG = new int[256];
        int[] cumHistB = new int[256];
        int[] equalHistR = new int[256];
        int[] equalHistG = new int[256];
        int[] equalHistB = new int[256];

        for (int i = 0; i < maxVal; i++) {
            histR[i] = 0;
            histG[i] = 0;
            histB[i] = 0;
            cumHistR[i] = 0;
            cumHistG[i] = 0;
            cumHistB[i] = 0;
            equalHistR[i] = 0;
            equalHistG[i] = 0;
            equalHistB[i] = 0;
        }

        for (int u = 0; u < image.getWidth(); u++) {
            for (int v = 0; v < image.getHeight(); v++) {
                // Grab the pixel values
                rawValue = image.getRGB(u, v);
                int[] rawVals = {(rawValue & 0xff0000) >> 16,
                        (rawValue & 0x00ff00) >> 8, (rawValue & 0x0000ff)};

                histR[rawVals[0]]++;
                histG[rawVals[1]]++;
                histB[rawVals[2]]++;

            }
        }

        cumHistR[0] = histR[0];
        cumHistG[0] = histG[0];
        cumHistB[0] = histB[0];

        for (int i = 1; i < maxVal; i++) {
            cumHistR[i] = histR[i] + cumHistR[i - 1];
            cumHistG[i] = histG[i] + cumHistG[i - 1];
            cumHistB[i] = histB[i] + cumHistB[i - 1];
        }
        new ShowHistogram(cumHistR, "Kate Hist R");
        new ShowHistogram(cumHistG, "Kate Hist G");
        new ShowHistogram(cumHistB, "Kate Hist B");

        for (int i = 0; i < maxVal; i++) {
            equalHistR[i] = cumHistR[i] * 255
                    / (image.getWidth() * image.getHeight());
            equalHistG[i] = cumHistG[i] * 255
                    / (image.getWidth() * image.getHeight());
            equalHistB[i] = cumHistB[i] * 255
                    / (image.getWidth() * image.getHeight());
        }

        for (int u = 0; u < image.getWidth(); u++) {
            for (int v = 0; v < image.getHeight(); v++) {
                rawValue = image.getRGB(u, v);
                int[] rawVals = {(rawValue & 0xff0000) >> 16,
                        (rawValue & 0x00ff00) >> 8, (rawValue & 0x0000ff)};

                rawVals[0] = equalHistR[rawVals[0]];
                rawVals[1] = equalHistG[rawVals[1]];
                rawVals[2] = equalHistB[rawVals[2]];


                BeautifyUtils.clampRGB(rawVals);
                outputImage.setRGB(u, v, ((rawVals[0] & 0xff) << 16) | ((rawVals[1] & 0xff) << 8) | (rawVals[2] & 0xff));
            }
        }

        return outputImage;
    }
}



