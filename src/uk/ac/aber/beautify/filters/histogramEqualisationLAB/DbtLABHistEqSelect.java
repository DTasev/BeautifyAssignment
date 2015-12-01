
package uk.ac.aber.beautify.filters.histogramEqualisationLAB;

import uk.ac.aber.beautify.core.BeautifyFilters;
import uk.ac.aber.beautify.filters.Filter;

public class DbtLABHistEqSelect implements BeautifyFilters{

    @Override
    public Filter autoBeautify() {
        return new DbtLABHistogramEqualisation();
    }

    @Override
    public String getAuthor() {
        return "Dimitar Tasev - dbt@aber.ac.uk";
    }

}
