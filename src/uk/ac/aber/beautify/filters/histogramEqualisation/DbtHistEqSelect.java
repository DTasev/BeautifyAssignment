package uk.ac.aber.beautify.filters.histogramEqualisation;

import uk.ac.aber.beautify.core.BeautifyFilters;
import uk.ac.aber.beautify.filters.Filter;

public class DbtHistEqSelect implements BeautifyFilters{

    @Override
    public Filter autoBeautify() {
        return new DbtHistogramEqualisation();
    }

    @Override
    public String getAuthor() {
        return "Dimitar Tasev - dbt@aber.ac.uk";
    }

}
