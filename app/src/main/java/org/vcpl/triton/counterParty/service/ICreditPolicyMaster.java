package org.vcpl.triton.counterParty.service;

import org.vcpl.triton.counterParty.data.CreditPolicyMasterData;
import org.vcpl.triton.counterParty.entity.CreditPolicyMasterEntity;

import java.util.List;

public interface ICreditPolicyMaster {

    List<CreditPolicyMasterEntity> getAllCreditPolicyMaster();

    CreditPolicyMasterEntity saveCreditPolicyMaster(CreditPolicyMasterData creditPolicyMasterData);

    CreditPolicyMasterEntity updateCreditPolicyMaster(CreditPolicyMasterData creditPolicyMasterData, long id);

    String deleteCreditPolicyMaster(long id);
}
