
package uk.ac.aber.beautify.filters.labEqualiseAndRGBContrastAdjust;

import uk.ac.aber.beautify.core.BeautifyFilters;
import uk.ac.aber.beautify.filters.Filter;

public class DbtLABeqAndCASelectImage implements BeautifyFilters{

    @Override
    public Filter autoBeautify() {
        return new DbtLABeqAndContrastAdjustmentImage();
    }

    @Override
    public String getAuthor() {
        return "Dimitar Tasev - dbt@aber.ac.uk";
    }

}
