package uk.ac.aber.beautify.core;

import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import uk.ac.aber.beautify.filters.Filter;
import uk.ac.aber.beautify.gui.*;

public class BeautifyKernel implements ActionListener {

	private BeautifyGUI gui;
	
	private BeautifyFilters filterSet;
	
	private BufferedImage originalImage;
	private BufferedImage displayImage;
	
	public BeautifyKernel(BeautifyGUI gui) {
		this.gui = gui;
		
		gui.addActionListener(this);
	}
	
	public void loadImage(String filename) {
		boolean errorFound = false;
		try {
			originalImage = ImageIO.read(new File(filename));
		} catch (Exception e) {
			errorFound = true;
		}
		
		if (originalImage == null | errorFound) {
			JOptionPane.showMessageDialog(gui, "Invalid image file");
		} else {
			displayImage = originalImage;
			
			// Now update the GUI
			showImage();
		}
	}
	
	public void setFilterSet(BeautifyFilters filterSet) {
		this.filterSet = filterSet;
		gui.setTitle("Beautify! - " + filterSet.getAuthor());
	}
	
	private void runFilter(Filter filter) {
		BufferedImage copy = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		copy.setData(originalImage.getData());
		
		displayImage = filter.filter(copy);
		gui.setFilterName(filter.getName());
		showImage();
	}
	
	private void resetImage() {
		displayImage = originalImage;
		gui.setFilterName("Original");
		showImage();
	}
		
	public void showImage() {
		gui.setImage(displayImage);
	}

	private void processFilterRequest(int selectedFilter) {
		// First of all we need to check that there is actually an image loaded...
		
		if (originalImage != null) {
			switch (selectedFilter) {
				case 0:	resetImage();
						break;
						
				case 1: runFilter(filterSet.autoBeautify());
						break;
					
				default:resetImage();
			}
		} else {
			JOptionPane.showMessageDialog(gui, "No image loaded!");
		}
				
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if (gui.getActionSource(e) == BeautifyGUI.ACTION_ORIGINAL_BUTTON) {
			processFilterRequest(0);
		}
		if (gui.getActionSource(e) == BeautifyGUI.ACTION_BEAUTIFY_BUTTON) {
			processFilterRequest(1);
		}
		if (gui.getActionSource(e) == BeautifyGUI.ACTION_LOAD_BUTTON) {
			File file = gui.getLoadFile();
			
			
			if (file != null) {
				loadImage(file.getAbsolutePath());	
			}
		}
		if (gui.getActionSource(e) == BeautifyGUI.ACTION_SAVE_BUTTON) {
			// Check that there is an image loaded...
			if (originalImage != null) {
			File file = gui.getSaveFile();
			
			if (file != null) {
				// TODO: Implement
				
				String filePath = file.getAbsolutePath();
				
				if (!filePath.endsWith(".jpg") || !filePath.endsWith(".JPG") || !filePath.endsWith(".jpeg") || !filePath.endsWith(".JPEG"))
				    filePath += ".jpg";
				
				try {
					ImageIO.write(displayImage, "JPG", new File(filePath));		
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			} else {
				JOptionPane.showMessageDialog(gui, "No image loaded so can't save.");
			}
		}
	}
}
