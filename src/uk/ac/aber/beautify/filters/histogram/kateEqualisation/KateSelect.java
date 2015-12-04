package uk.ac.aber.beautify.filters.histogram.kateEqualisation;

import uk.ac.aber.beautify.core.BeautifyFilters;
import uk.ac.aber.beautify.filters.Filter;
import uk.ac.aber.beautify.filters.histogramEqualisation.DbtHistogramEqualisation;

public class KateSelect implements BeautifyFilters{

    @Override
    public Filter autoBeautify() {
        return new KateEqualise();
    }

    @Override
    public String getAuthor() {
        return "Dimitar Tasev - dbt@aber.ac.uk";
    }

}
