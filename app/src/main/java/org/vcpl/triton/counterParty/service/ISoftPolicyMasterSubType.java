package org.vcpl.triton.counterParty.service;

import org.springframework.http.ResponseEntity;
import org.vcpl.triton.counterParty.entity.SoftPolicyMasterSubTypeEntity;

import java.util.List;
import java.util.Optional;

public interface ISoftPolicyMasterSubType {
    List<SoftPolicyMasterSubTypeEntity> getAllSoftPolicyMasterSubType();

    SoftPolicyMasterSubTypeEntity createSoftPolicyMasterSubType(Long spTypeId, SoftPolicyMasterSubTypeEntity softPolicyMasterSubTypeEntity);

    Optional<SoftPolicyMasterSubTypeEntity> getSoftPolicyMasterSubTypeById(Long spSubTypeId);

    SoftPolicyMasterSubTypeEntity updateSoftPolicyMasterSubType(Long spSubTypeId, SoftPolicyMasterSubTypeEntity softPolicyMasterSubTypeEntity);

    ResponseEntity<Object> deleteSoftPolicyMasterSubTypeById(long spSubTypeId);
}
