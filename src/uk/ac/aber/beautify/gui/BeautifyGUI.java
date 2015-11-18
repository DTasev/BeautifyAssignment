package uk.ac.aber.beautify.gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.*;

public class BeautifyGUI extends JFrame {

	private ImagePanel imagePanel;
	
	private JButton originalButton;
	private JButton beautifyButton;
	private JButton loadImageButton;
	private JButton saveImageButton;
	
	private JFileChooser fileChooser;
	
	public static final int ACTION_NULL = -1;
	public static final int ACTION_ORIGINAL_BUTTON = 0;
	public static final int ACTION_BEAUTIFY_BUTTON = 1;
	public static final int ACTION_LOAD_BUTTON = 2;
	public static final int ACTION_SAVE_BUTTON = 3;
	
	public static final String ICON_BEAUTIFY_PATH = "assets/autobeautify.png";
	public static final String ICON_LOAD_PATH = "assets/load.png";
	public static final String ICON_SAVE_PATH = "assets/save.png";
	public static final String ICON_RESET_PATH = "assets/reset.png";
	
	public static final Color COLOR_COMBO_BG = new Color(41, 41, 41);
	public static final Color COLOR_COMBO_FG = new Color(210, 210, 210);
	
	public BeautifyGUI () {
		super("Beautify!");
		
		this.setSize(new Dimension(625, 775));
		
		imagePanel= new ImagePanel();
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		mainPanel.add(imagePanel, BorderLayout.CENTER);
		
		originalButton = new JButton();
		ImageIcon originalIcon = new ImageIcon(ICON_RESET_PATH);
		originalButton.setIcon(originalIcon);
		originalButton.setOpaque(false);
		originalButton.setBorder(BorderFactory.createEmptyBorder());
		originalButton.setToolTipText("Reset to Original Image");
		
		beautifyButton = new JButton();
		ImageIcon beautifyIcon = new ImageIcon(ICON_BEAUTIFY_PATH);
		beautifyButton.setIcon(beautifyIcon);
		beautifyButton.setOpaque(false);
		beautifyButton.setBorder(BorderFactory.createEmptyBorder());
		beautifyButton.setToolTipText("AutoBeautify!");

		loadImageButton = new JButton();
		ImageIcon loadIcon = new ImageIcon(ICON_LOAD_PATH);
		loadImageButton.setIcon(loadIcon);
		loadImageButton.setOpaque(false);
		loadImageButton.setBorder(BorderFactory.createEmptyBorder());
		loadImageButton.setToolTipText("Load Image");
		
		saveImageButton = new JButton();
		ImageIcon saveIcon = new ImageIcon(ICON_SAVE_PATH);
		saveImageButton.setIcon(saveIcon);
		saveImageButton.setOpaque(false);
		saveImageButton.setBorder(BorderFactory.createEmptyBorder());
		saveImageButton.setToolTipText("Save Current Image");
				
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(COLOR_COMBO_BG);

		
		buttonPanel.add(originalButton);
		buttonPanel.add(Box.createHorizontalStrut(15));
		buttonPanel.add(beautifyButton);
		buttonPanel.add(Box.createHorizontalStrut(15));
		buttonPanel.add(loadImageButton);
		buttonPanel.add(Box.createHorizontalStrut(5));
		buttonPanel.add(saveImageButton);
		
		JPanel lowerPanel = new JPanel(new BorderLayout());
		lowerPanel.setBackground(COLOR_COMBO_BG);
		
		lowerPanel.add(Box.createVerticalStrut(10), BorderLayout.NORTH);
		lowerPanel.add(buttonPanel, BorderLayout.CENTER);
		lowerPanel.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);
		
		mainPanel.add(lowerPanel, BorderLayout.SOUTH);
		
		fileChooser = new JFileChooser();
		
		CustomFileTypeFilter jpgFilter = new CustomFileTypeFilter(".jpg", "JPG Image");
		CustomFileTypeFilter pngFilter = new CustomFileTypeFilter(".png", "PNG Image");
		fileChooser.addChoosableFileFilter(jpgFilter);
		fileChooser.addChoosableFileFilter(pngFilter);
		fileChooser.setFileFilter(jpgFilter);
		
		this.getContentPane().add(mainPanel);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	public void addActionListener(ActionListener listener) {
		originalButton.addActionListener(listener);
		beautifyButton.addActionListener(listener);
		loadImageButton.addActionListener(listener);
		saveImageButton.addActionListener(listener);
	}
	
	public void setImage(BufferedImage img) {
		imagePanel.setImage(img);
	}
	
	public void setFilterName(String name) {
		imagePanel.setFilterName(name);
	}
	
	public File getSaveFile() {
		int returnVal = fileChooser.showSaveDialog(this);
		
		File file = null;
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			
		}
		
		return file;
	}
	
	public File getLoadFile() {
		int returnVal = fileChooser.showOpenDialog(this);
		
		File file = null;
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			
		}
		
		return file;
	}
		
	public int getActionSource(ActionEvent e) {
		if (originalButton.equals((JButton) e.getSource()))
			return ACTION_ORIGINAL_BUTTON;
		if (beautifyButton.equals((JButton) e.getSource())) 
			return ACTION_BEAUTIFY_BUTTON;
		if (loadImageButton.equals((JButton) e.getSource()))
			return ACTION_LOAD_BUTTON;
		if (saveImageButton.equals((JButton) e.getSource()))
			return ACTION_SAVE_BUTTON;
		
		return ACTION_NULL;
	}
	
}
