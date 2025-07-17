package org.vcpl.triton.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SoftPolicyValidator {
    @Autowired
    private SoftPolicyLoader softPolicyLoader;

    public String validateSoftPolicy(String json)
    {
        return softPolicyLoader.checkSupportedParameters(json);
    }
}
