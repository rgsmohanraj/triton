package org.vcpl.triton.counterParty.service;

import org.vcpl.triton.counterParty.data.SoftPolicyFilterMasterData;
import org.vcpl.triton.counterParty.entity.SoftPolicyFilterMasterEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ISoftPolicyFilterMasterService {
    List<SoftPolicyFilterMasterEntity> getAllFilters();

    SoftPolicyFilterMasterEntity getsoftPolicyFilterMasterById(long id);

    List<SoftPolicyFilterMasterEntity> findBySoftPolicyFilterType(String id);

    SoftPolicyFilterMasterEntity saveSoftPolicyFilterMaster(SoftPolicyFilterMasterData softPolicyFilterMasterData);

    SoftPolicyFilterMasterEntity updateSoftPolicyFilterMaster(SoftPolicyFilterMasterData softPolicyFilterMasterData, long id);

    String deleteSoftPolicyFilter(long id);
}
