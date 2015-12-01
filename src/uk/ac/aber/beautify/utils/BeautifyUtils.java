package uk.ac.aber.beautify.utils;

public abstract class BeautifyUtils {
	
    /**
     * sRGB to XYZ conversion matrix
     */
    public static double[][] M   = {{0.4124, 0.3576,  0.1805},
                             {0.2126, 0.7152,  0.0722},
                             {0.0193, 0.1192,  0.9505}};

    /**
     * XYZ to sRGB conversion matrix
     */
    public static double[][] Mi  = {{ 3.2406, -1.5372, -0.4986},
                             {-0.9689,  1.8758,  0.0415},
                             { 0.0557, -0.2040,  1.0570}};

    /**
     * XYZ White reference 
     */
    public static double[] whitepoint_D65 = {95.0429, 100.0, 108.8900};
    
	public static int[] clampRGB(int[] values){
		for (int i = 0; i < values.length; i++) {
			values[i] = Math.min(Math.max(0, values[i]), 255);
		}
		
		return values;
	}
    public static double[] clampHSV(double[] values) {
		for (int i = 0; i < values.length; i++) {
			values[i] = Math.min(Math.max(0, values[i]), 1);
		}

		return values;
	}
    public static double[] clampLAB(double[] values){
    	for (int i = 0; i < values.length; i++) {
			values[i] = Math.min(Math.max(0, values[i]), 100);
    	}
    	return values;
    }

	public static double[] RGBtoHSVDouble(int[] rgb){
		int H = 0, S = 1, V = 2;

		double r = rgb[0] / 255.0;
		double g = rgb[1] / 255.0;
		double b = rgb[2] / 255.0;

		//double H = 0, S = 0, V = 0;
		double[] hsv = new double[3];

		double minRGB = Math.min(r, Math.min(g, b));
		double maxRGB = Math.max(r, Math.max(g, b));

		double deltaMAX = maxRGB-minRGB;

		hsv[V] = maxRGB;

		if(deltaMAX == 0){ // this is gray
			hsv[H] = hsv[S] = 0.0;
		}else{
			hsv[S] = deltaMAX / maxRGB;

			double deltaR = (((maxRGB - r) / 6.0) + (deltaMAX /2.0)) / deltaMAX;
			double deltaG = (((maxRGB - g) / 6.0) + (deltaMAX /2.0)) / deltaMAX;
			double deltaB = (((maxRGB - b) / 6.0) + (deltaMAX /2.0)) / deltaMAX;

			if(r == maxRGB)
				hsv[H] = deltaB - deltaG;
			else if(g == maxRGB)
				hsv[H] = (1.0 / 3.0) + deltaR - deltaB;
			else if(b == maxRGB)
				hsv[H] = (2.0 / 3.0) + deltaG - deltaR;

			if(hsv[H] < 0)
				hsv[H] += 1.0;
			if(hsv[H] > 1)
				hsv[H] -= 1.0;
		}
		return hsv;
	}
	
	/**
	 * The RGBtoHSV function is taken from the book Principles of Digital Image Processing by 
	 * Burger and Burge.
	 * 
	 * @author Wilhelm Burger
	 * @author Mark J Burge
	 * @param rgb
	 * @return
	 */
	public static float[] RGBtoHSV(int[] rgb) {
		int R = rgb[0];
		int G = rgb[1];
		int B = rgb[2];
		
		float H = 0, S = 0, V = 0;
		
		float cMax = 255.0f;
		
		int cHi = Math.max(R, Math.max(G, B));
		int cLo = Math.min(R, Math.min(G, B));
		int cRng = cHi - cLo;
		
		V = cHi / cMax;
		
		if (cHi > 0)	S = (float) cRng / cHi;
		
		if (cRng > 0) {
			float rr = (float) (cHi - R) / cRng;
			float gg = (float) (cHi - G) / cRng;
			float bb = (float) (cHi - B) / cRng;
			
			float hh;
			
			if (R == cHi)		hh = bb - gg;
			else if (G == cHi)	hh = rr - bb + 2.0f;
			else 				hh = gg - rr + 4.0f;
			
			if (hh < 0)			hh = hh + 6;
			
			H = hh / 6;
		}
		
		float[] HSV = new float[3];
		HSV[0] = H; HSV[1] = S; HSV[2] = V;
		
		return HSV;
	}
	
	/**
	 * The HSVtoRGB function is taken from the book Principles of Digital Image Processing by 
	 * Burger and Burge.
	 * 
	 * @author Wilhelm Burger
	 * @author Mark J Burge
	 * @param hsv
	 * @return
	 */
	public static int[] HSVtoRGB(float[] hsv) {
		float h = hsv[0];
		float s = hsv[1];
		float v = hsv[2];
		
		float rr = 0, gg = 0, bb = 0;
		
		float hh = (6 * h) % 6;
		
		int c1 = (int) hh;
		float c2 = hh - c1;
		
		float x = (1 - s) * v;
		float y = (1 - (s * c2)) * v;
		float z = (1 - (s * (1 - c2))) * v;
		
		switch (c1) {
			case 0:	rr = v; gg = z; bb = x; break;
			case 1: rr = y; gg = v; bb = x; break;
			case 2: rr = x; gg = v; bb = z; break;
			case 3: rr = x; gg = y; bb = v; break;
			case 4: rr = z; gg = x; bb = v; break;
			case 5: rr = v; gg = x; bb = y; break;
		}
		
		int N = 256;
		
		int r = Math.min(Math.round(rr * N), N-1);
		int g = Math.min(Math.round(gg * N), N-1);
		int b = Math.min(Math.round(bb * N), N-1);
		
		int[] rgb = new int[3];
		
		rgb[0] = r; rgb[1] = g; rgb[2] = b;
		
		return rgb;
	}

	/*
	 * The method for transforming was taken from https://en.wikipedia.org/wiki/SRGB#The_reverse_transformation
	 * and http://www.brucelindbloom.com/index.html?Eqn_RGB_XYZ_Matrix.html
	 */
	public static double[] RGBtoXYZ(int[] rgb) {
	      double[] result = new double[3];

	      // convert 0..255 into 0..1
	      double r = rgb[0] / 255.0;
	      double g = rgb[1] / 255.0;
	      double b = rgb[2] / 255.0;

	      // assume sRGB
	      // Convert Red,Green,Blue
	      if (r <= 0.04045) {
	        r = r / 12.92;
	      }
	      else {
	        r = Math.pow(((r + 0.055) / 1.055), 2.4);
	      }
	      if (g <= 0.04045) {
	        g = g / 12.92;
	      }
	      else {
	        g = Math.pow(((g + 0.055) / 1.055), 2.4);
	      }
	      if (b <= 0.04045) {
	        b = b / 12.92;
	      }
	      else {
	        b = Math.pow(((b + 0.055) / 1.055), 2.4);
	      }

	      r *= 100.0;
	      g *= 100.0;
	      b *= 100.0;

	      // [X Y Z] = [r g b][M]
	      result[0] = (r * M[0][0]) + (g * M[0][1]) + (b * M[0][2]);
	      result[1] = (r * M[1][0]) + (g * M[1][1]) + (b * M[1][2]);
	      result[2] = (r * M[2][0]) + (g * M[2][1]) + (b * M[2][2]);

	      return result;
	    }
	
	/**
	 * The algorithm was taken from http://www.brucelindbloom.com/index.html?Eqn_XYZ_to_Lab.html
	 */
	public static int[] XYZtoRGB(double[] xyz) {
      int[] result = new int[3];

      double x = xyz[0] / 100.0;
      double y = xyz[1] / 100.0;
      double z = xyz[2] / 100.0;

      // [r g b] = [X Y Z][Mi]
      double r = (x * Mi[0][0]) + (y * Mi[0][1]) + (z * Mi[0][2]);
      double g = (x * Mi[1][0]) + (y * Mi[1][1]) + (z * Mi[1][2]);
      double b = (x * Mi[2][0]) + (y * Mi[2][1]) + (z * Mi[2][2]);

      // Convert X back to sRGB
      if (r > 0.0031308) {
        r = ((1.055 * Math.pow(r, 1.0 / 2.4)) - 0.055);
      }
      else {
        r = (r * 12.92);
      }
      // Convert Y back to RGB
      if (g > 0.0031308) {
        g = ((1.055 * Math.pow(g, 1.0 / 2.4)) - 0.055);
      }
      else {
        g = (g * 12.92);
      }
      // Convert Z back to RGB
      if (b > 0.0031308) {
        b = ((1.055 * Math.pow(b, 1.0 / 2.4)) - 0.055);
      }
      else {
        b = (b * 12.92);
      }

      r = (r < 0) ? 0 : r;
      g = (g < 0) ? 0 : g;
      b = (b < 0) ? 0 : b;

      // convert 0..1 into 0..255
      result[0] = (int) Math.round(r * 255);
      result[1] = (int) Math.round(g * 255);
      result[2] = (int) Math.round(b * 255);

      return result;
    }
	/**
	 * The algorithm for the transformation was taken from https://en.wikipedia.org/wiki/Lab_color_space#CIELAB-CIEXYZ_conversions
	 */
	public static double[] XYZtoLAB(double[] xyzValues) {

	      double x = xyzValues[0] / whitepoint_D65[0];
	      double y = xyzValues[1] / whitepoint_D65[1];
	      double z = xyzValues[2] / whitepoint_D65[2];

	      if (x > 0.008856) {
	        x = Math.pow(x, 1.0 / 3.0);
	      }
	      else {
	        x = (7.787 * x) + (16.0 / 116.0);
	      }
	      if (y > 0.008856) {
	        y = Math.pow(y, 1.0 / 3.0);
	      }
	      else {
	        y = (7.787 * y) + (16.0 / 116.0);
	      }
	      if (z > 0.008856) {
	        z = Math.pow(z, 1.0 / 3.0);
	      }
	      else {
	        z = (7.787 * z) + (16.0 / 116.0);
	      }

	      double[] result = new double[3];

	      result[0] = (116.0 * y) - 16.0;
	      result[1] = 500.0 * (x - y);
	      result[2] = 200.0 * (y - z);
	      
	      return result;
    }
	/**
	 * The algorithm for the transformation was taken from https://en.wikipedia.org/wiki/Lab_color_space#CIELAB-CIEXYZ_conversions
	 */
	 public static double[] LABtoXYZ(double[] lab){
		 double[] result = new double[3];

	      double y = (lab[0] + 16.0) / 116.0;
	      double y3 = Math.pow(y, 3.0);
	      double x = (lab[1] / 500.0) + y;
	      double x3 = Math.pow(x, 3.0);
	      double z = y - (lab[2] / 200.0);
	      double z3 = Math.pow(z, 3.0);

	      if (y3 > 0.008856) {
	        y = y3;
	      }
	      else {
	        y = (y - (16.0 / 116.0)) / 7.787;
	      }
	      if (x3 > 0.008856) {
	        x = x3;
	      }
	      else {
	        x = (x - (16.0 / 116.0)) / 7.787;
	      }
	      if (z3 > 0.008856) {
	        z = z3;
	      }
	      else {
	        z = (z - (16.0 / 116.0)) / 7.787;
	      }

	      result[0] = x * whitepoint_D65[0];
	      result[1] = y * whitepoint_D65[1];
	      result[2] = z * whitepoint_D65[2];

	      return result;
	 }
	 public static double[] RGBtoLAB(int[] rgb){
		 double[] res = BeautifyUtils.XYZtoLAB(BeautifyUtils.RGBtoXYZ(rgb));
		 return res;
	 }
	 public static int[] LABtoRGB(double[] lab){
		 int[] res = BeautifyUtils.XYZtoRGB(BeautifyUtils.LABtoXYZ(lab));
		 return res;
	 }
}
