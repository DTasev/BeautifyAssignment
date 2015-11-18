package uk.ac.aber.beautify.gui;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class CustomFileTypeFilter extends FileFilter {
	private String desc;
	private String ext;
	
	public CustomFileTypeFilter(String ext, String desc) {
		this.ext = ext;
		this.desc = desc;
	}
	
	public boolean accept(File file) {
		if (file.isDirectory())	return true;
		
		return file.getName().endsWith(ext);
	}
	
	public String getDescription() {
		return desc + String.format(" (%s)", ext);
	}
	

}
