package uk.ac.aber.beautify.filters.histogram.filter;

import uk.ac.aber.beautify.core.BeautifyFilters;
import uk.ac.aber.beautify.filters.Filter;

public class DbtHistogramSelect implements BeautifyFilters{

	@Override
	public Filter autoBeautify() {
		return new DbtHistogram();
	}

	@Override
	public String getAuthor() {
		return "Dimitar Tasev - dbt@aber.ac.uk";
	}

}
