package org.vcpl.triton.counterParty.service;

import org.vcpl.triton.counterParty.data.CreditPolicyDetailsMasterData;
import org.vcpl.triton.counterParty.entity.CreditPolicyDetailsEntity;

import java.util.Collection;
import java.util.List;

public interface ICreditPolicyDetailsService {


    String findByCpCreditPolicyDetails(long id);

    String creditPolicyFilter(CreditPolicyDetailsMasterData creditPolicyDetailsMasterData, long appId);

    List<Long> saveCreditPolicyDetails(CreditPolicyDetailsMasterData creditPolicyDetailsMasterData);

    List<Long> updateCreditPolicyDetails(CreditPolicyDetailsMasterData creditPolicyDetailsMasterData);
}
