package org.vcpl.triton.counterParty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.vcpl.triton.counterParty.entity.SoftPolicyFilterMasterEntity;

import java.util.List;

public interface SoftPolicyFilterMasterRepository extends JpaRepository<SoftPolicyFilterMasterEntity,Long> {

    @Query(value = "SELECT * FROM soft_policy_filters WHERE type = ?1",nativeQuery = true)
    List<SoftPolicyFilterMasterEntity> findByType(String id);
}
