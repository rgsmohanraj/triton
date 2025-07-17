package org.vcpl.triton.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DueDiligenceValidator {

    @Autowired
    private  DueDiligenceLoader dueDiligenceLoader;

    public String validateDueDiligence(String json)
    {
        return dueDiligenceLoader.checkSupportedParameters(json);
    }
}
