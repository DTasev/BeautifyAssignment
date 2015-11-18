package uk.ac.aber.beautify.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {


	private String filterName = "Original";
	
	private BufferedImage img;
	private BufferedImage logo;
	private BufferedImage background;
	
	private int imgWidth, imgHeight;
	
	private Font openSans;
	
	private static final String BACKGROUND_PATH = "assets/starburst.png";
	private static final String LOGO_PATH = "assets/logo.png";
	private static final String OPEN_SANS_PATH = "assets/OpenSans-Semibold.ttf";
	
	private static final int LOGO_WIDTH = 250;
	private static final int LOGO_HEIGHT = 113;
	
	private static final int BORDER_SIZE = 80;
	
	public ImagePanel() {
		img = null;
		
		try {
			openSans = Font.createFont(Font.TRUETYPE_FONT, new File(OPEN_SANS_PATH)).deriveFont(14.0f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
              //register the font
            ge.registerFont(openSans);
          
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			background = ImageIO.read(new File(BACKGROUND_PATH));
			
		    logo = ImageIO.read(new File(LOGO_PATH));
		    
		    Image smallLogo = logo.getScaledInstance(LOGO_WIDTH, LOGO_HEIGHT, Image.SCALE_SMOOTH);

		    // width and height are of the toolkit image
		    logo = new BufferedImage(smallLogo.getWidth(null), smallLogo.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		    Graphics g = logo.getGraphics();
		    g.drawImage(smallLogo, 0, 0, null);
		    g.dispose();
		    
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void setFilterName(String name) {
		this.filterName = name;
		
		this.repaint();
	}
	
	
	public void setImage(BufferedImage img) {
		this.img = img;
		imgWidth = img.getWidth();
		imgHeight = img.getHeight();
		
		this.repaint();
	}
	
	
	public void paintComponent(Graphics g) {
		// Set antialiasing to on
		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC));
		g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
		g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
		
		g2.clearRect(0, 0, this.getWidth(), this.getHeight());
		g2.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
		
		float scaleFactor = Math.min((float) (this.getWidth() - BORDER_SIZE) / (float) imgWidth, (float) (this.getHeight() - BORDER_SIZE) / (float) imgHeight);
	
		g2.setFont(openSans);
		
		FontMetrics fontMetrics = g2.getFontMetrics();
		
		if (img != null) {
			g2.drawImage(img, (int) ((this.getWidth() / 2) - ((img.getWidth() * scaleFactor) / 2)), (int) ((this.getHeight() / 2) - ((img.getHeight() * scaleFactor) / 2)), (int) (img.getWidth() * scaleFactor), (int) (img.getHeight() * scaleFactor), null);
			g2.setColor(Color.WHITE);
			g2.drawRect((int) ((this.getWidth() / 2) - ((img.getWidth() * scaleFactor) / 2)), (int) ((this.getHeight() / 2) - ((img.getHeight() * scaleFactor) / 2)), (int) (img.getWidth() * scaleFactor), (int) (img.getHeight() * scaleFactor));
			g2.drawString(filterName, (int) ((this.getWidth() / 2) - (fontMetrics.stringWidth(filterName) / 2)), ((this.getHeight() / 2) + ((img.getHeight() * scaleFactor) / 2)) + 25);
		} else {
			g2.setColor(Color.GRAY);
			g2.drawString("No Image Loaded", (int) ((this.getWidth() / 2) - (fontMetrics.stringWidth("No Image Loaded") / 2)), ((this.getHeight() / 2)));
			
		}
		
		g2.drawImage(logo, 15, 15, LOGO_WIDTH, LOGO_HEIGHT, null);
		
		
	}
}
