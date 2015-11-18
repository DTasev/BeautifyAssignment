package uk.ac.aber.beautify.filters;

import uk.ac.aber.beautify.core.BeautifyFilters;

public class DbtEnchancementFilterSelect implements BeautifyFilters{

	@Override
	public Filter autoBeautify() {
		return new DbtEnchancementFilter();
	}

	@Override
	public String getAuthor() {
		return "Dimitar Tasev - dbt@aber.ac.uk";
	}

}
