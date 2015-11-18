package uk.ac.aber.beautify.filters.grayscale;

import uk.ac.aber.beautify.core.BeautifyFilters;
import uk.ac.aber.beautify.filters.Filter;

public class DbtGrayscaleFilterSelect implements BeautifyFilters{

	@Override
	public Filter autoBeautify() {
		return new DbtGrayscaleFilter();
	}

	@Override
	public String getAuthor() {
		return "Dimitar Tasev - dbt@aber.ac.uk";
	}

}
