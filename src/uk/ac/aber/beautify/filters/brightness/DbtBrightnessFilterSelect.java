package uk.ac.aber.beautify.filters.brightness;

import uk.ac.aber.beautify.core.BeautifyFilters;
import uk.ac.aber.beautify.filters.Filter;

public class DbtBrightnessFilterSelect implements BeautifyFilters {

	@Override
	public Filter autoBeautify() {
		return new DbtBrightnessFilter();
	}

	@Override
	public String getAuthor() {
		return "Dimitar Tasev - dbt@aber.ac.uk";
	}

}
