
package uk.ac.aber.beautify.filters.labEqualiseAndRGBContrastAdjust;

import uk.ac.aber.beautify.core.BeautifyFilters;
import uk.ac.aber.beautify.filters.Filter;

public class DbtLABeqAndCASelect implements BeautifyFilters{

    @Override
    public Filter autoBeautify() {
        return new DbtLABeqAndContrastAdjustment();
    }

    @Override
    public String getAuthor() {
        return "Dimitar Tasev - dbt@aber.ac.uk";
    }

}
