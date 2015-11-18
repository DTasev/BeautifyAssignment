package uk.ac.aber.beautify.filters.invert;

import uk.ac.aber.beautify.core.BeautifyFilters;
import uk.ac.aber.beautify.filters.Filter;

public class DbtInvertFilterSelect implements BeautifyFilters {

	@Override
	public Filter autoBeautify() {
		return new DbtInvertFilter();
	}

	@Override
	public String getAuthor() {
		return "Dimitar Tasev - dbt@aber.ac.uk";
	}

}
