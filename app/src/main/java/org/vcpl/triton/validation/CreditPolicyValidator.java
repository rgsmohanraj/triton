package org.vcpl.triton.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreditPolicyValidator {
    @Autowired
    private CreditPolicyLoader creditPolicyLoader;

    public String validateCreditPolicy(String json)
    {
        return creditPolicyLoader.checkSupportedParameters(json);
    }
}
