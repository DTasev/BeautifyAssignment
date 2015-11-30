package uk.ac.aber.beautify.filters.contrastAdjustment;

import uk.ac.aber.beautify.core.BeautifyFilters;
import uk.ac.aber.beautify.filters.Filter;
import uk.ac.aber.beautify.filters.histogram.filter.DbtCumulative;

public class DbtContrastAdjSelect implements BeautifyFilters{

    @Override
    public Filter autoBeautify() {
        return new DbtContrastAdjustment();
    }

    @Override
    public String getAuthor() {
        return "Dimitar Tasev - dbt@aber.ac.uk";
    }

}
