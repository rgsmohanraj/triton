package org.vcpl.triton.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CollateralValidator {
    @Autowired
    private CollateralLoader collateralLoader;

    public String validateCollateral(String json)
    {
        return collateralLoader.checkSupportedParameters(json);
    }
}
