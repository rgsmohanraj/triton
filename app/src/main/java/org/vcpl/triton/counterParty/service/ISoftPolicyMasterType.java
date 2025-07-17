package org.vcpl.triton.counterParty.service;

import org.vcpl.triton.counterParty.entity.SoftPolicyMasterTypeEntity;

import java.util.List;
import java.util.Optional;

public interface ISoftPolicyMasterType {
    List<SoftPolicyMasterTypeEntity> getsoftPolicyMasterType();

    SoftPolicyMasterTypeEntity createSoftPolicyMasterType(SoftPolicyMasterTypeEntity softPolicyMasterTypeEntity);

    Optional<SoftPolicyMasterTypeEntity> getSoftPolicyMasterTypeById(Long spTypeId);

    SoftPolicyMasterTypeEntity updateDocumentTypeById(Long spTypeId, SoftPolicyMasterTypeEntity softPolicyMasterTypeEntity);
}
