package uk.ac.aber.beautify.filters.histogram.filter;

import uk.ac.aber.beautify.core.BeautifyFilters;
import uk.ac.aber.beautify.filters.Filter;

public class DbtCumulativeSelect implements BeautifyFilters{

    @Override
    public Filter autoBeautify() {
        return new DbtCumulative();
    }

    @Override
    public String getAuthor() {
        return "Dimitar Tasev - dbt@aber.ac.uk";
    }

}
