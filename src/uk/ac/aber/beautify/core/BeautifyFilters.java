/**
 * The BeautifyFilters interface contains all the method signatures called by the Beautify
 * GUI.  Each method returns a Filter to perform the appropriate filtering.
 * 
 * @author Harry Strange
 */
package uk.ac.aber.beautify.core;

import uk.ac.aber.beautify.filters.Filter;

public interface BeautifyFilters {
	
	/**
	 * This method is called when the Auto Beautify button (magic wand) is pressed.  The
	 * filter used in this method should automatically adjust aspects such as brightness
	 * and contrast, exposure, etc. to "beautify" the image!
	 * 
	 * @return Filter - the filter used to automatically beautify an image.
	 */
	public Filter autoBeautify();
	
	
	/**
	 * When implementing this interface, use this method to return your name as a String.
	 * 
	 * @return String - your name, easy!
	 */
	public String getAuthor();
	
}
