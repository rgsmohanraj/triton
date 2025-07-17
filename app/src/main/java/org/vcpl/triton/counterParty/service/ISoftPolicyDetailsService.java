package org.vcpl.triton.counterParty.service;



import org.vcpl.triton.counterParty.data.SoftPolicyDetailsData;
import org.vcpl.triton.counterParty.data.SoftPolicyDetailsMasterData;
import org.vcpl.triton.counterParty.entity.SoftPolicyDetailsEntity;

import javax.script.ScriptException;
import java.util.Collection;
import java.util.List;

public interface ISoftPolicyDetailsService {
    String deleteSoftPolicyDetails(long id);

    String softPolicyFilter(SoftPolicyDetailsMasterData softPolicyDetailsMasterData, Long appId) throws ScriptException;

    List<Long> saveSoftPolicyDetails(SoftPolicyDetailsMasterData softPolicyDetailsMasterData);
    String getSoftPolicyById(long id);
    List<SoftPolicyDetailsEntity> getAllSoftPolicyDetails();
    List<Long> updateSoftPolicyDetails(SoftPolicyDetailsMasterData softPolicyDetailsMasterData);
}
