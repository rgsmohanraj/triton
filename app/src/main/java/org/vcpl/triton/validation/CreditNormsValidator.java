package org.vcpl.triton.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreditNormsValidator {

    @Autowired
    private CreditNormsLoader creditNormsLoader;

    public String validateCreditNorms(String json)
    {
       return creditNormsLoader.checkSupportedParameters(json);
    }
}
