
package uk.ac.aber.beautify.filters.histogramEqualisationHSV;

import uk.ac.aber.beautify.core.BeautifyFilters;
import uk.ac.aber.beautify.filters.Filter;

public class DbtHSVHistEqSelect implements BeautifyFilters{

    @Override
    public Filter autoBeautify() {
        return new DbtHSVHistogramEqualisation();
    }

    @Override
    public String getAuthor() {
        return "Dimitar Tasev - dbt@aber.ac.uk";
    }

}
