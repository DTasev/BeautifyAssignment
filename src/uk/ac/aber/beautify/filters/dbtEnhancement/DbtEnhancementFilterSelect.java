package uk.ac.aber.beautify.filters.dbtEnhancement;

import uk.ac.aber.beautify.core.BeautifyFilters;
import uk.ac.aber.beautify.filters.Filter;

public class DbtEnhancementFilterSelect implements BeautifyFilters{

	@Override
	public Filter autoBeautify() {
		return new DbtEnhancementFilter();
	}

	@Override
	public String getAuthor() {
		return "Dimitar Tasev - dbt@aber.ac.uk";
	}

}
