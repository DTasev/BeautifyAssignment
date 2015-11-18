/**
 * This is an example implementation of the BeautifyFilters interface that you can use as
 * a guide for your implementation.  The purpose of this class is to allow Beautify access
 * to your different filter implementations.
 * 
 * NOTE: Please make sure that you override the line in Beautify.java that sets the 
 * 		 Beautify filter set.  That is, you will want to replace the line:
 * 	
 * 				beautify.setFilterSet(new DefaultBeautifyFilters());
 * 
 * 		 with:
 * 
 * 				beautify.setFilterSet(new WhateverYouHaveCalledYourBeautifyFiltersImplementation());
 * 
 * 		 Without this step you will only ever see the default beautify filter selection.  Please do
 * 		 not simply over-write the DefaultBeautifyFilters code, you need to provide your own
 * 		 implementation of the interface!
 * 
 * @author Harry Strange
 */
package uk.ac.aber.beautify.core;

import uk.ac.aber.beautify.filters.Filter;
import uk.ac.aber.beautify.filters.basic.BasicBeautifyFilter;

public class DefaultBeautifyFilters implements BeautifyFilters {

	/**
	 * For your implementation of the autoBeautify method you should return your main auto enhancement
	 * filter.  That filter can of course call and use sub-filters, but you should return from the
	 * autoBeautify method the main (top-level) Filter.
	 * 
	 */
	@Override
	public Filter autoBeautify() {
		// I don't want to give too much away, so I am simply returning a NullFilter() for this method
		// More details regarding how to "Auto Beautify" will be given during lectures.
		return new BasicBeautifyFilter();
	}
	
	/**
	 * This is how you should override the getAuthor() method.  Please make sure you do, so that I know
	 * whose filters I am looking at when testing your awesome programs out!
	 */
	public String getAuthor() {
		return "Harry Strange";
	}
	
}
